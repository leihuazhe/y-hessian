package com.yunji.dubbo.common.serialize.util;


import com.yunji.com.caucho.hessian.io.SerializerFactory;

/**
 * @author Denim.leihz 2019-10-28 8:56 PM
 */
public class Hessian2SerializerFactory extends SerializerFactory {

    public static final SerializerFactory SERIALIZER_FACTORY = new Hessian2SerializerFactory();

    private Hessian2SerializerFactory() {
    }

    @Override
    public ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

}
