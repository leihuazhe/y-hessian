package com.yunji.flurry.jsonserializer;

import com.yunji.flurry.metadata.tag.DataType;
import com.yunji.flurry.metadata.tag.Service;
import com.yunji.flurry.util.Constants;
import org.apache.dubbo.common.utils.Assert;
import org.apache.dubbo.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author Denim.leihz 2019-07-23 6:06 PM
 */
public class MetadataUtil {
    private static Logger logger = LoggerFactory.getLogger(MetadataUtil.class);

    private static ExecutorService executorService = Executors.newFixedThreadPool(5);


    /**
     * 根据配置信息获取到需要引用的服务信息,支持10个级别
     *
     * @param properties 配置信息 WHITE_SERVICES_KEY
     * @return 需要引用的服务接口 list
     */
    public static Set<String> getReferService(Properties properties) {
        Assert.notNull(properties, "ConfigServer 获取到的外部化配置信息为空，请检查并配置相关信息.");
        String baseKey = Constants.WHITE_SERVICES_KEY;
        String whiteStr = properties.getProperty(baseKey);
        char ch = ',';

        if (StringUtils.isNotEmpty(whiteStr)) {
            Set<String> list = new HashSet<>();
            char c;
            int ix = 0, len = whiteStr.length();
            for (int i = 0; i < len; i++) {
                c = whiteStr.charAt(i);
                if (c == ch) {
                    list.add(whiteStr.substring(ix, i));
                    ix = i + 1;
                }
            }

            if (ix >= 0) {
                list.add(whiteStr.substring(ix));
            }
            return list;
        } else {
//            throw new IllegalArgumentException("White list String is empty. Please specify the list string on config server.");
            logger.warn("White list String is empty. Please specify the list string on config server.");
            //返回空
            return new HashSet<>();
        }
    }


    public static byte dataType2Byte(DataType type) {
        switch (type.kind) {
            case BOOLEAN:
                return TType.BOOL;

            case BYTE:
                return TType.BYTE;

            case DOUBLE:
                return TType.DOUBLE;

            case SHORT:
                return TType.I16;

            case INTEGER:
                return TType.I32;

            case LONG:
                return TType.I64;

            case STRING:
                return TType.STRING;

            case STRUCT:
                return TType.STRUCT;

            case MAP:
                return TType.MAP;

            case SET:
                return TType.SET;

            case LIST:
                return TType.LIST;

            case ENUM:
                return TType.I32;

            case VOID:
                return TType.VOID;

            case DATE:
                return TType.I64;

            case BIGDECIMAL:
                return TType.STRING;

            case BINARY:
                return TType.STRING;

            default:
                break;
        }

        return TType.STOP;
    }

    public static String getDataKindType(DataType dataType) {
        String qualifiedName = dataType.qualifiedName;
        if (qualifiedName != null) {
            return qualifiedName;
        }
        DataType.KIND kind = dataType.kind;

        switch (kind) {
            case VOID:
                return "java.lang.Void";
            case BOOLEAN:
                return "java.lang.Boolean";
            case BYTE:
                return "java.lang.Byte";
            case SHORT:
                return "java.lang.Short";
            case INTEGER:
                return "java.lang.Integer";
            case LONG:
                return "java.lang.Long";
            case DOUBLE:
                return "java.lang.Double";
            case STRING:
                return "java.lang.String";
            case BINARY:
                return null;
            case MAP:
                return "java.util.Map";
            case LIST:
                return "java.util.List";
            case SET:
                return "java.util.Set";
            case ENUM:
                return "java.lang.Enum";
            case STRUCT:
                return null;
            case DATE:
                return "java.util.Date";
            case BIGDECIMAL:
                return "java.math.BigDecimal";

            default:
                return null;
        }
    }


    public static String getServiceKey(Service service) {
        return getServiceKey(service.getName(), service.getMeta().version);
    }

    public static String getServiceFullNameKey(Service service) {
        return getServiceKey(service.getNamespace() + "." + service.getName(), service.getMeta().version);
    }

    public static String getServiceKey(String name, String version) {
        return name + ":" + version;
    }
}
