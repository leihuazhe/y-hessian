package gateway.test.utils;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.yunji.com.caucho.hessian.io.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Denim.leihz 2019-08-21 11:13 AM
 */
public class Hessian2Utils {
    private final static Logger logger = LoggerFactory.getLogger(Hessian2Utils.class);

    private SerializerFactory serializerFactory = new SerializerFactory();


    protected SerializerFactory getSerializerFactory() {
        return serializerFactory;
    }

    public byte[] serialize(Object obj) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        AbstractHessianOutput out = new Hessian2Output(os);

        out.setSerializerFactory(getSerializerFactory());
        try {
            out.writeObject(obj);
            out.close();
        } catch (Exception e) {
            logger.error("hessian serialize failed," + e);
        }
        return os.toByteArray();
    }

    public Object deserialize(byte[] bytes) {
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        AbstractHessianInput in = new Hessian2Input(is);

        in.setSerializerFactory(getSerializerFactory());
        Object value = null;
        try {
            value = in.readObject();
            in.close();
        } catch (IOException e) {
            logger.error("hessian deserialize failed, cause: " + e.getMessage(), e);
        }
        return value == null ? bytes : value;
    }


    /**
     * 针对方法调用
     */
    public Object[] deserialize2(byte[] bytes, Class<?>[] pts, Object[] args) {
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        com.yunji.com.caucho.hessian.io.AbstractHessianInput in = new com.yunji.com.caucho.hessian.io.Hessian2Input(is);
        in.setSerializerFactory(getSerializerFactory());
        try {
            int tag;
            while ((tag = in.readInt()) != -1) {
                args[tag] = in.readObject(pts[tag]);
            }
            in.close();
        } catch (IOException e) {
            logger.error("hessian deserialize failed, cause: " + e.getMessage(), e);
        }
        return args;
    }

    public String deserializeToJson(byte[] bytes, Class<?>[] pts, Object[] args) {
        return null;
    }
}
