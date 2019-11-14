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

import static com.yunji.flurry.util.Constants.STREAMING_SERIALIZATION_CUSTOM;
import static org.apache.dubbo.remoting.Constants.SERIALIZATION_KEY;

/**
 * 兼容 hessian2 协议用的给网关用的序列化协议
 * <p>
 * 这个协议不会显示的使用，而是在 CodeSupport 中 static 静态的进行初始化,然后处理 provider 返回时,
 * 根据 请求头的 byte id 为 2 来进行选择。为了避免被原生的 Hessian2Serialization 覆盖,根据加载顺序使用名称 a_y_hessian_compatible
 *
 * @author Denim.leihz 2019-10-16 11:19 AM
 */
public class YHessianCompatibleSerialization implements Serialization {

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
            throw new UnsupportedOperationException("YHessianCompatibleSerialization serialize method is not support in api gateway.");
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
