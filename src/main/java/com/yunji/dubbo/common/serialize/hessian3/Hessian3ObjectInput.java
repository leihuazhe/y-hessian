/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yunji.dubbo.common.serialize.hessian3;

import com.alibaba.dubbo.common.serialize.ObjectInput;
import com.yunji.com.caucho.hessian.io.Hessian2Input;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * Hessian2 Object input.
 */
public class Hessian3ObjectInput implements ObjectInput {
    private final Hessian2Input mH3i;

    public Hessian3ObjectInput(InputStream is) {
        mH3i = new Hessian2Input(is);
        mH3i.setSerializerFactory(Hessian3SerializerFactory.SERIALIZER_FACTORY);
    }

    @Override
    public boolean readBool() throws IOException {
        return mH3i.readBoolean();
    }

    @Override
    public byte readByte() throws IOException {
        return (byte) mH3i.readInt();
    }

    @Override
    public short readShort() throws IOException {
        return (short) mH3i.readInt();
    }

    @Override
    public int readInt() throws IOException {
        return mH3i.readInt();
    }

    @Override
    public long readLong() throws IOException {
        return mH3i.readLong();
    }

    @Override
    public float readFloat() throws IOException {
        return (float) mH3i.readDouble();
    }

    @Override
    public double readDouble() throws IOException {
        return mH3i.readDouble();
    }

    @Override
    public byte[] readBytes() throws IOException {
        return mH3i.readBytes();
    }

    @Override
    public String readUTF() throws IOException {
        return mH3i.readString();
    }

    @Override
    public Object readObject() throws IOException {
        return mH3i.readObject();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T readObject(Class<T> cls) throws IOException,
            ClassNotFoundException {
        return (T) mH3i.readObject(cls);
    }

    @Override
    public <T> T readObject(Class<T> cls, Type type) throws IOException, ClassNotFoundException {
        return readObject(cls);
    }

    public Hessian2Input getCmH2i() {
        return mH3i;
    }

}
