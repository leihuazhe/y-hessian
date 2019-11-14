package com.yunji.dubbo.common.serialize.compatible;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.serialize.ObjectInput;
import com.alibaba.dubbo.common.serialize.ObjectOutput;
import com.alibaba.dubbo.common.serialize.Serialization;
import com.alibaba.dubbo.common.serialize.support.hessian.Hessian2ObjectInput;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
        return "x-application/hessian2_compatible";
    }

    //原生Dubbo Provider 完成请求后，通过此方法将 response 写回,
    //这里自定义的主要目的是,将回写的内容全部采用 com.yunji 包的hessian2进行序列化
    //这个协议去掉了 addRef 这个操作.
    /*
        @Override
        protected Serialization getSerialization(Channel channel) {
            Serialization serialization = CodecSupport.getSerialization(channel.getUrl());
            if (serialization instanceof Hessian2Serialization) {
                return hessian2CompatibleSerialization;
            }
            return serialization;
        }
     */
    public ObjectOutput serialize(URL url, OutputStream out) throws IOException {
        return new Hessian2CompatibleObjectOutput(out);
    }

    /**
     * 网关直接通过此协议将hessian2 buffer 反序列化为 json
     */
    public ObjectInput deserialize(URL url, InputStream is) throws IOException {
        return new Hessian2ObjectInput(is);
    }

}