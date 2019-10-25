package com.yunji.dubbo.common.serialize.compatible.alibaba;

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