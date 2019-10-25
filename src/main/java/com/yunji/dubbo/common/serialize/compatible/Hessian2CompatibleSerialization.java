package com.yunji.dubbo.common.serialize.compatible;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.serialize.ObjectInput;
import org.apache.dubbo.common.serialize.ObjectOutput;
import org.apache.dubbo.common.serialize.Serialization;
import org.apache.dubbo.common.serialize.hessian2.Hessian2ObjectInput;
import org.apache.dubbo.common.serialize.hessian2.Hessian2ObjectOutput;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.yunji.gateway.util.GateConstants.STREAMING_SERIALIZATION_CUSTOM;
import static org.apache.dubbo.remoting.Constants.SERIALIZATION_KEY;

/**
 * 兼容 hessian2 协议用的给网关用的序列化协议
 *
 * @author Denim.leihz 2019-10-16 11:19 AM
 */
public class Hessian2CompatibleSerialization implements Serialization {

    public static final byte ID = 2;

    public byte getContentTypeId() {
        return ID;
    }

    public String getContentType() {
        return "x-application/hessian2";
    }

    /**
     * API 网关再调用的时候不会使用此协议的 serialize
     */
    public ObjectOutput serialize(URL url, OutputStream out) throws IOException {
        if (STREAMING_SERIALIZATION_CUSTOM.equals(url.getParameter(SERIALIZATION_KEY))) {
            throw new UnsupportedOperationException("Hessian2CompatibleSerialization not support in api gateway.");
        }
        return new Hessian2ObjectOutput(out);
    }

    /**
     * 网关直接通过此协议将hessian2 buffer 反序列化为 json
     */
    public ObjectInput deserialize(URL url, InputStream is) throws IOException {
        if (STREAMING_SERIALIZATION_CUSTOM.equals(url.getParameter(SERIALIZATION_KEY))) {
            return new Hessian3ObjectInput(is);
        }
        return new Hessian2ObjectInput(is);
    }
}
