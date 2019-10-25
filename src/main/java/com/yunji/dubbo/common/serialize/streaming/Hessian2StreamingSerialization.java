package com.yunji.dubbo.common.serialize.streaming;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.serialize.ObjectInput;
import org.apache.dubbo.common.serialize.ObjectOutput;
import org.apache.dubbo.common.serialize.Serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 支持流式的序列化协议,主要供原生的dubbo服务增强,能够接收并处理流式模式.
 * <p>
 * 网关请求时使用此协议,provider 返回时使用 compatible 协议
 *
 * @author Denim.leihz 2019-10-16 11:19 AM
 */
public class Hessian2StreamingSerialization implements Serialization {

    public static final byte ID = 30;

    public byte getContentTypeId() {
        return ID;
    }

    public String getContentType() {
        return "x-application/hessian2_streaming";
    }

    public ObjectOutput serialize(URL url, OutputStream out) throws IOException {
        return new Hessian2StreamingObjectOutput(out);
    }

    /**
     * 给 provider 进行解码时用到
     */
    public ObjectInput deserialize(URL url, InputStream is) throws IOException {
        return new Hessian2StreamingObjectInput(is);
    }

}