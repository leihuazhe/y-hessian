package com.yunji.dubbo.common.serialize.streaming;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.serialize.ObjectInput;
import com.alibaba.dubbo.common.serialize.ObjectOutput;
import com.alibaba.dubbo.common.serialize.Serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * YHessianStreamingSerialization 自定义协议
 * <p>
 * 支持流式的序列化协议,主要供 原生的dubbo服务增强,能够接收并处理流式模式.
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
     * 网关使用此协议序列化,原生Dubbo不会使用这个方法
     */
    public ObjectOutput serialize(URL url, OutputStream out) throws IOException {
        return new Hessian2StreamingObjectOutput(out);
    }

    /**
     * Dubbo Provider 使用此协议 反序列化
     */
    public ObjectInput deserialize(URL url, InputStream is) throws IOException {
        return new Hessian2StreamingObjectInput(is);
    }

}