package com.yunji.flurry.jsonserializer;


import com.yunji.flurry.metadata.OptimizedService;
import com.yunji.flurry.metadata.OptimizedStruct;
import com.yunji.flurry.metadata.tag.DataType;
import com.yunji.flurry.metadata.tag.Field;
import com.yunji.flurry.metadata.tag.Struct;

import com.yunji.com.caucho.hessian.io.Hessian2Output;
import com.yunji.dubbo.common.serialize.streaming.YHessianStreamingObjectOutput;
import com.yunji.dubbo.common.serialize.util.Offset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

import static com.yunji.flurry.jsonserializer.JsonSerializationUtil.*;
import static com.yunji.flurry.util.Constants.*;

/**
 * @author Denim.leihz 2019-07-08 8:30 PM
 */
public class JsonReader implements JsonCallback {
    private final Logger logger = LoggerFactory.getLogger(JsonReader.class);

    private final OptimizedStruct optimizedStruct;
    private final OptimizedService optimizedService;

    private final Hessian2Output cmH2o;

    private StackNode current;


    private int level = 0;

    private boolean skip = false;

    private int skipDepth = 0;

    private Stack<StackNode> history = new Stack<>();
    /**
     * keep a minimum StackNode Pool
     */
    private List<StackNode> nodePool = new ArrayList<>(64);

    JsonReader(OptimizedStruct optimizedStruct, OptimizedService optimizedService,
               YHessianStreamingObjectOutput out) {
        this.optimizedStruct = optimizedStruct;
        this.optimizedService = optimizedService;
        this.cmH2o = out.getmH2o();
    }


    @Override
    public void onStartObject() throws IOException {
        level++;
        if (level == 0)
            return;

        if (skip) {
            skipDepth++;
            return;
        }

        if (current == null) {
            initAndPushDataType();
            return;
        }

        assert current.dataType.kind == DataType.KIND.STRUCT || current.dataType.kind == DataType.KIND.MAP;
        switch (current.dataType.kind) {
            case STRUCT:
                //Just like writeObject(Object obj, AbstractHessianOutput out) in hessian2
                writeStruct();
                break;
            case MAP:
                DataType.KIND kind = current.dataType.keyType.kind;
                asserts(isValidMapKeyType(kind), "Map key only support basic type,current type: " + kind);
                writeMapBegin();
                break;
            default:
                logAndThrowTException();
        }
    }


    @Override
    public void onEndObject() throws IOException {
        level--;
        if (level == -1) return;

        if (skip) {
            skipDepth--;
            return;
        }
        assert current.dataType.kind == DataType.KIND.STRUCT || current.dataType.kind == DataType.KIND.MAP;

        switch (current.dataType.kind) {
            case STRUCT:
                cmH2o.writeInt(-1);
                break;
            case MAP:
                cmH2o.writeMapEnd();
                break;
            default:
                logAndThrowTException();
        }

    }

    /**
     * ??????????????????????????????????????????, ???????????????0???
     */
    @Override
    public void onStartArray() throws IOException {
        level++;
        if (skip) {
            skipDepth++;
            return;
        }
        DataType.KIND kind = current.dataType.kind;
        asserts(isCollectionKind(kind), "Current DataType " + kind + " is not a collection type.");

        switch (current.dataType.kind) {
            case LIST:
            case SET:
                writeCollectionBegin();
                break;
            case ARRAY:
                writeArrayBegin(current.dataType);
                break;
            default:
                logAndThrowTException();
        }

    }


    @Override
    public void onEndArray() throws IOException {
        level--;
        if (skip) {
            skipDepth--;
            return;
        }
        DataType.KIND kind = current.dataType.kind;
        asserts(isCollectionKind(kind), "Current DataType " + kind + " is not a collection type.");

        switch (current.dataType.kind) {
            case LIST:
            case SET:
            case ARRAY:
                cmH2o.writeListEnd();
                break;
            default:
                break;
        }
    }

    @Override
    public void onStartField(String name) throws IOException {
        if (skip) {
            return;
        }
        if (level == 0) {
            if ("body".equals(name) || "request".equals(name)) {
                initAndPushDataType();
            } else {
                skip = true;
                skipDepth = 0;
            }
        } else { // level > 0, not skip
            if (current.dataType.kind == DataType.KIND.MAP) {
                assert isValidMapKeyType(current.dataType.keyType.kind);

                if (current.dataType.keyType.kind == DataType.KIND.STRING) {
                    cmH2o.writeString(name);
                } else {
                    writeIntField(name, current.dataType.keyType.kind);
                }

                DataType valueType = current.dataType.valueType;
                OptimizedStruct optimizedStruct = optimizedService.getOptimizedStruct(valueType.qualifiedName);
                push(current.dataType.valueType, optimizedStruct, name);

            } else if (current.dataType.kind == DataType.KIND.STRUCT) {
                Field field = current.optimizedStruct.fieldMap.get(name);

                if (field == null) {
                    skip = true;
                    skipDepth = 0;
                    logger.debug("field(" + name + ") not found. just skip");
                    return;
                } else {
                    skip = false;
                }
                //Fixme support ????????????
                cmH2o.writeInt(field.getTag());

                DataType valueType = field.dataType;
                OptimizedStruct optimizedStruct = optimizedService.getOptimizedStruct(valueType.qualifiedName);
                push(valueType, optimizedStruct, name);
            } else {
                logAndThrowTException("field " + name + " type " + toString(current.dataType) + " not compatible with json object");
            }
        }
    }

    @Override
    public void onStartField(int index) throws IOException {
        if (skip) {
            return;
        }
        DataType.KIND kind = current.dataType.kind;
        asserts(isCollectionKind(kind), "Current DataType " + kind + " is not a collection type.");

        DataType valueType = current.dataType.valueType;
        OptimizedStruct optimizedStruct = (valueType.kind == DataType.KIND.STRUCT) ?
                optimizedService.getOptimizedStruct(valueType.qualifiedName) : null;
        push(valueType, optimizedStruct, null);
    }

    @Override
    public void onEndField() throws IOException {
        if (skip) {
            if (skipDepth == 0) {
                skip = false;
            }
            return;
        }
        String fieldName = current.fieldName;

        // level = 0 will having no current dataType
        if (level > 0) {
            StackNode parent = peek();
            asserts(parent != null, "StackNode parent should not be null.");
            assert parent != null;

            switch (parent.dataType.kind) {
                case SET:
                case LIST:
                    if (current.isNull) {
                        logAndThrowTException("SET/LIST can't support null value");
                    }
                    break;
                case MAP:
                    if (current.isNull) {
                    }
                    break;
                case STRUCT:
                    if (current.isNull) {
                    }
                    break;
            }

            pop();
        }

    }

    @Override
    public void onBoolean(boolean value) throws IOException {
        if (skip) {
            return;
        }
        if (current.dataType.kind != DataType.KIND.BOOLEAN) {
            logAndThrowTException();
        }
        //9.27?????? json ??? true/false ?????????(?????? "true"/"false")
        cmH2o.writeBoolean(value);
    }

    @Override
    public void onNumber(double value) throws IOException {
        DataType.KIND currentType = current.dataType.kind;
        if (skip) {
            return;
        }

        switch (currentType) {
            case SHORT:
                cmH2o.writeInt((int) value);
                break;
            case INTEGER:
            case ENUM:
                cmH2o.writeInt((int) value);
                break;
            case LONG:
                cmH2o.writeLong((long) value);
                break;
            case DOUBLE:
                cmH2o.writeDouble(value);
                break;
            case BIGDECIMAL:
                cmH2o.writeString(String.valueOf(value));
                break;
            case BYTE:
                cmH2o.writeInt((int) value);
                break;
            default:
                if (BIGDECIMAL_CLASS_TYPE.equals(current.dataType.qualifiedName)) {
                    writeBigDecimal(String.valueOf(value));
                    break;
                }
                if (TIMESTAMP_CLASS_TYPE.equals(current.dataType.qualifiedName)) {
                    cmH2o.writeUTCDate((long) value);
                    break;
                }
                throw new IOException("Field:" + current.fieldName + ", DataType(" + current.dataType.kind
                        + ") for " + current.dataType.qualifiedName + " is not a Number");

        }
    }


    @Override
    public void onNumber(long value) throws IOException {

    }

    @Override
    public void onNull() throws IOException {
        if (skip) {
            return;
        }
        cmH2o.writeNull();
    }

    @Override
    public void onString(String value) throws IOException {
        if (skip) {
            return;
        }

        switch (current.dataType.kind) {
            case ENUM:
                writeEnum(current.dataType.qualifiedName, value);
                break;
            case BOOLEAN:
                cmH2o.writeBoolean(Boolean.parseBoolean(value));
                break;
            case DOUBLE:
                cmH2o.writeDouble(Double.parseDouble(value));
                break;
            case BIGDECIMAL:
                writeBigDecimal(String.valueOf(value));
//                cmH2o.writeString(value);
                break;
            case INTEGER:
                cmH2o.writeInt(Integer.parseInt(value));
                break;
            case LONG:
                cmH2o.writeLong(Long.parseLong(value));
                break;
            case SHORT:
                cmH2o.writeInt(Integer.parseInt(value));
                break;

            case DATE:
                long time;
                try {
                    time = Long.parseLong(value);
                } catch (NumberFormatException e) {
                    throw new IOException("Field DATE ??????????????????????????????????????????????????? " + value);
                }
                cmH2o.writeUTCDate(time);
                break;
            default:
                if (BIGDECIMAL_CLASS_TYPE.equals(current.dataType.qualifiedName)) {
                    writeBigDecimal(String.valueOf(value));
                    break;
                }
                if (DATE_CLASS_TYPE.equals(current.dataType.qualifiedName)) {
                    cmH2o.writeUTCDate(JsonSerializationUtil.parseDateString(value));
                    break;
                }
                if (current.dataType.kind != DataType.KIND.STRING) {
                    throw new IOException("Field:" + current.fieldName + ", Not a real String!");
                }
                cmH2o.writeString(value);
        }
    }


    /**
     * onColon ":"
     */
    @Override
    public void onColon() {

    }

    @Override
    public int markIndex() {
        return 0;
    }

    @Override
    public void copyObjectJson(Offset offset) {

    }


    // only used in startField
    private void push(final DataType dataType,
                      final OptimizedStruct optimizedStruct, String fieldName) {

        StackNode node = nodePool.size() > 0 ? nodePool.remove(nodePool.size() - 1) : new StackNode();
        node.init(dataType, optimizedStruct, fieldName);
        history.push(node);

        this.current = node;
    }


    // only used in endField
    private StackNode pop() {
        StackNode old = history.pop();
        nodePool.add(old);

        return this.current = (history.size() > 0) ? history.peek() : null;
    }

    private StackNode peek() {
        return history.size() <= 1 ? null : history.get(history.size() - 2);
    }

    private String toString(DataType type) {
        StringBuilder sb = new StringBuilder();
        sb.append(type.kind.toString());
        switch (type.kind) {
            case STRUCT:
                sb.append("(").append(type.qualifiedName).append(")");
                break;
            case LIST:
            case SET:
                sb.append("[").append(toString(type.valueType)).append("]");
                break;
            case MAP:
                sb.append("[").append(toString(type.keyType)).append(",").append(toString(type.valueType)).append("]");
                break;
        }
        return sb.toString();
    }

    private void writeStruct() throws IOException {
        Struct struct = current.optimizedStruct.struct;
        if (struct == null) {
            logger.error("optimizedStruct not found");
            logAndThrowTException();
        }
        if (struct.namespace != null) {
            int ref = cmH2o.writeObjectBegin(struct.namespace + "." + struct.name);
            if (ref < -1) {
                writeObject10(struct);

            } else {
                if (ref == -1) {
                    writeDefinition20(struct.fields);
                    cmH2o.writeObjectBegin(struct.namespace + "." + struct.name);
                }
            }
        }
    }

    private void writeIntField(String value, DataType.KIND kind) throws IOException {
        switch (kind) {
            case SHORT:
            case INTEGER:
                cmH2o.writeInt(Integer.valueOf(value));
                break;
            case LONG:
                cmH2o.writeLong(Long.valueOf(value));
                break;
            default:
                logAndThrowTException();
        }
    }

    private void writeMapBegin() throws IOException {
        cmH2o.writeMapBegin(null);
    }


    /**
     * ??? -1,?????????????????? onEnd ?????????????????????
     */
    private void writeCollectionBegin() throws IOException {
        cmH2o.writeListBegin(-1, null);
    }

    /**
     * ?????????
     */
    private void writeArrayBegin(DataType dataType) throws IOException {
        DataType valueType = dataType.valueType;

        if (valueType != null) {
            switch (valueType.kind) {
                case STRUCT:
                    String qualifiedName = valueType.qualifiedName;
                    String arrayType = getArrayType(qualifiedName);
                    cmH2o.writeListBegin(-1, "[" + arrayType);
                    break;
                case STRING:
                    cmH2o.writeListBegin(-1, "[string");
                    break;
                default:
                    break;
            }
        } else {
            logAndThrowTException("DataType valueType  is null.");
        }
    }

    /**
     * Get ?????????????????????
     *
     * @param name ????????????
     * @return Real type.
     */
    private String getArrayType(String name) {
        switch (name) {
            case "java.lang.String":
                return "string";
            case "java.lang.Object":
                return "object";
            case "java.util.Date":
                return "date";
            default:
                return name;
        }
    }

    private void asserts(boolean flag, String msg) throws IOException {
        if (!flag) {
            logAndThrowTException(msg);
        }
    }

    private void logAndThrowTException() throws IOException {
        String fieldName = current == null ? "" : current.fieldName;

        StackNode peek = peek();
        assert peek != null;

        String struct = current == null ? "" : current.optimizedStruct == null ? (peek.optimizedStruct == null ? "" : peek.optimizedStruct.struct.name) : current.optimizedStruct.struct.name;
        IOException ex = new IOException("JsonError, please check:"
                + struct + "." + fieldName);
        logger.error(ex.getMessage(), ex);
        throw ex;
    }

    private void logAndThrowTException(String msg) throws IOException {
        IOException ex = new IOException("JsonError:" + msg);
        logger.error(ex.getMessage(), ex);
        throw ex;
    }

    private void writeEnum(String enumType, String name)
            throws IOException {
        int ref = cmH2o.writeObjectBegin(enumType);

        if (ref < -1) {
            cmH2o.writeString("name");
            cmH2o.writeString(name);
            cmH2o.writeMapEnd();
        } else {
            if (ref == -1) {
                cmH2o.writeClassFieldLength(1);
                cmH2o.writeString("name");
                cmH2o.writeObjectBegin(enumType);
            }
            cmH2o.writeString(name);
        }
    }

    private void writeBigDecimal(String value) throws IOException {
        int ref = cmH2o.writeObjectBegin(BIGDECIMAL_CLASS_TYPE);
        if (ref < -1) {
            cmH2o.writeString("value");
            cmH2o.writeString(value);
            cmH2o.writeMapEnd();
        } else {
            if (ref == -1) {
                cmH2o.writeInt(1);
                cmH2o.writeString("value");
                cmH2o.writeObjectBegin(BIGDECIMAL_CLASS_TYPE);
            }
            cmH2o.writeString(value);
        }
    }

    private void writeObject10(Struct struct) throws IOException {
        logAndThrowTException("Source method [writeObject10]:Specified  struct " + struct.namespace + "." + struct.name +
                " got occurred when written, caused: Streaming serializer not support writeObject10.");
    }

    private void writeDefinition20(List<Field> fields)
            throws IOException {

        int fieldSize = fields.size();
        cmH2o.writeClassFieldLength(fieldSize);

        for (Field field : fields) {
            cmH2o.writeString(field.getName());
        }
    }


    /**
     * Init DataType then push data type to the stack.
     */
    private void initAndPushDataType() {
        DataType initDataType = new DataType();
        initDataType.setKind(DataType.KIND.STRUCT);
        initDataType.qualifiedName = optimizedStruct.struct.name;
        push(initDataType, optimizedStruct, "start");
    }

    /**
     * ???????????????????????????????????????, ???????????? immutable ?????????  mutable?????????????????????????????????????????????StackNode
     * ????????? = json????????????????????????????????????????????????
     */
    static class StackNode {

        private DataType dataType;
        /**
         * optimizedStruct if dataType.kind==STRUCT
         */
        private OptimizedStruct optimizedStruct;
        /**
         * the field name
         */
        private String fieldName;
        /**
         * ???????????????Null.
         */
        boolean isNull = false;

        StackNode() {
        }

        void init(final DataType dataType,
                  final OptimizedStruct optimizedStruct,
                  String fieldName) {

            this.dataType = dataType;
            this.optimizedStruct = optimizedStruct;
            this.fieldName = fieldName;
            this.isNull = false;
        }
    }
}