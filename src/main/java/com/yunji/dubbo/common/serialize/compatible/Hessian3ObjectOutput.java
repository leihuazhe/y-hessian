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
package com.yunji.dubbo.common.serialize.compatible;

import com.yunji.com.caucho.hessian.io.Hessian2Output;
import com.yunji.dubbo.common.serialize.util.Hessian3SerializerFactory;
import org.apache.dubbo.common.serialize.ObjectOutput;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Hessian2 Object output.
 */
public class Hessian3ObjectOutput implements ObjectOutput {
    private final Hessian2Output mH3o;

    public Hessian2Output getmH3o() {
        return mH3o;
    }

    public Hessian3ObjectOutput(OutputStream os) {
        mH3o = new Hessian2Output(os);
        mH3o.setSerializerFactory(Hessian3SerializerFactory.SERIALIZER_FACTORY);
    }

    @Override
    public void writeBool(boolean v) throws IOException {
        mH3o.writeBoolean(v);
    }

    @Override
    public void writeByte(byte v) throws IOException {
        mH3o.writeInt(v);
    }

    @Override
    public void writeShort(short v) throws IOException {
        mH3o.writeInt(v);
    }

    @Override
    public void writeInt(int v) throws IOException {
        mH3o.writeInt(v);
    }

    @Override
    public void writeLong(long v) throws IOException {
        mH3o.writeLong(v);
    }

    @Override
    public void writeFloat(float v) throws IOException {
        mH3o.writeDouble(v);
    }

    @Override
    public void writeDouble(double v) throws IOException {
        mH3o.writeDouble(v);
    }

    @Override
    public void writeBytes(byte[] b) throws IOException {
        mH3o.writeBytes(b);
    }

    @Override
    public void writeBytes(byte[] b, int off, int len) throws IOException {
        mH3o.writeBytes(b, off, len);
    }

    @Override
    public void writeUTF(String v) throws IOException {
        mH3o.writeString(v);
    }

    @Override
    public void writeObject(Object obj) throws IOException {
        mH3o.writeObject(obj);
    }

    @Override
    public void flushBuffer() throws IOException {
        mH3o.flushBuffer();
    }
}
