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
public class YHessianStreamingSerialization implements Serialization {

    public static final byte ID = 30;

    public byte getContentTypeId() {
        return ID;
    }

    public String getContentType() {
        return "x-application/y_hessian_streaming";
    }

    /**
     * 网关将请求序列化时会使用这个接口
     */
    public ObjectOutput serialize(URL url, OutputStream out) throws IOException {
        return new YHessianStreamingObjectOutput(out);
    }

    /**
     * 如果使用网关,则不会使用该 deserialize 对返回结果进行反序列化.
     */
    public ObjectInput deserialize(URL url, InputStream is) throws IOException {
        return new YHessianStreamingObjectInput(is);
    }

}