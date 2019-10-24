package com.yunji.dubbo.common.serialize.hessian3.compatible;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.serialize.ObjectInput;
import org.apache.dubbo.common.serialize.ObjectOutput;
import org.apache.dubbo.common.serialize.Serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Denim.leihz 2019-10-16 11:19 AM
 */
public class Hessian3Serialization implements Serialization {

    public static final byte ID = 30;

    public byte getContentTypeId() {
        return ID;
    }

    public String getContentType() {
        return "x-application/hessian3";
    }

    public ObjectOutput serialize(URL url, OutputStream out) throws IOException {
        return new Hessian3ObjectOutput(out);
    }

    public ObjectInput deserialize(URL url, InputStream is) throws IOException {
        return new Hessian3ObjectInput(is);
    }

}