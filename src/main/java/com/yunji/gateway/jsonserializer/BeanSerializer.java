package com.yunji.gateway.jsonserializer;



import com.alibaba.dubbo.common.serialize.ObjectInput;

import java.io.IOException;


/**
 * 通用编解码器接口
 *
 * @author Denim.leihz 2019-07-08 8:43 PM
 */
public interface BeanSerializer<T> {

    /**
     * 反序列化方法, 从Thrift协议格式中转换回PoJo
     *
     * @param iproto
     * @return
     */
    T read(ObjectInput iproto) throws IOException;

    /**
     * 序列化方法, 把PoJo转换为Thrift协议格式
     */
    byte[] write(T bean) throws Exception;

    /**
     * PoJo校验方法
     *
     * @param bean
     */
    void validate(T bean) throws IOException;

    /**
     * 输出对人友好的信息
     *
     * @param bean
     * @return
     */
    String toString(T bean);
}

