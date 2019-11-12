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
package com.yunji.dubbo.common.serialize.util;

import com.yunji.com.caucho.hessian.io.*;
import com.yunji.com.caucho.hessian.io.hessian3.*;
import com.yunji.com.caucho.hessian.io.java8.*;

import javax.management.ObjectName;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import static com.yunji.com.caucho.hessian.io.java8.Java8TimeSerializer.create;

public class Hessian3SerializerFactory extends SerializerFactory {
    private static final Logger log
            = Logger.getLogger(Hessian3SerializerFactory.class.getName());


    public static final SerializerFactory SERIALIZER_FACTORY = new Hessian3SerializerFactory();

    private Hessian3SerializerFactory() {
    }

    static {
        _staticSerializerMap = new HashMap();
        _staticDeserializerMap = new HashMap();
        _staticTypeMap = new HashMap();

        addBasic3(void.class, "void", BasicSerializer.NULL);

        addBasic3(Boolean.class, "boolean", BasicSerializer.BOOLEAN);
        addBasic3(Byte.class, "byte", BasicSerializer.BYTE);
        addBasic3(Short.class, "short", BasicSerializer.SHORT);
        addBasic3(Integer.class, "int", BasicSerializer.INTEGER);
        addBasic3(Long.class, "long", BasicSerializer.LONG);
        addBasic3(Float.class, "float", BasicSerializer.FLOAT);
        addBasic3(Double.class, "double", BasicSerializer.DOUBLE);
        addBasic3(Character.class, "char", BasicSerializer.CHARACTER_OBJECT);
        addBasic3(String.class, "string", BasicSerializer.STRING);
        addBasic3(Object.class, "object", BasicSerializer.OBJECT);
        addBasic3(Date.class, "date", BasicSerializer.DATE);

        addBasic3(boolean.class, "boolean", BasicSerializer.BOOLEAN);
        addBasic3(byte.class, "byte", BasicSerializer.BYTE);
        addBasic3(short.class, "short", BasicSerializer.SHORT);
        addBasic3(int.class, "int", BasicSerializer.INTEGER);
        addBasic3(long.class, "long", BasicSerializer.LONG);
        addBasic3(float.class, "float", BasicSerializer.FLOAT);
        addBasic3(double.class, "double", BasicSerializer.DOUBLE);
        addBasic3(char.class, "char", BasicSerializer.CHARACTER);

        addBasic3(boolean[].class, "[boolean", BasicSerializer.BOOLEAN_ARRAY);
        addBasic3(byte[].class, "[byte", BasicSerializer.BYTE_ARRAY);
        addBasic3(short[].class, "[short", BasicSerializer.SHORT_ARRAY);
        addBasic3(int[].class, "[int", BasicSerializer.INTEGER_ARRAY);
        addBasic3(long[].class, "[long", BasicSerializer.LONG_ARRAY);
        addBasic3(float[].class, "[float", BasicSerializer.FLOAT_ARRAY);
        addBasic3(double[].class, "[double", BasicSerializer.DOUBLE_ARRAY);
        addBasic3(char[].class, "[char", BasicSerializer.CHARACTER_ARRAY);
        addBasic3(String[].class, "[string", BasicSerializer.STRING_ARRAY);
        addBasic3(Object[].class, "[object", BasicSerializer.OBJECT_ARRAY);

        _staticSerializerMap.put(Class.class, new Hessian3ClassSerializer());

        _staticDeserializerMap.put(Number.class, new BasicDeserializer(BasicSerializer.NUMBER));

        _staticSerializerMap.put(BigDecimal.class, new Hessian3StringValueSerializer());
        try {
            _staticDeserializerMap.put(BigDecimal.class,
                    new StringValueDeserializer(BigDecimal.class));
            _staticDeserializerMap.put(BigInteger.class,
                    new BigIntegerDeserializer());
        } catch (Throwable e) {
        }

        _staticSerializerMap.put(File.class, new Hessian3StringValueSerializer());
        try {
            _staticDeserializerMap.put(File.class,
                    new StringValueDeserializer(File.class));
        } catch (Throwable e) {
        }

        _staticSerializerMap.put(ObjectName.class, new Hessian3StringValueSerializer());
        try {
            _staticDeserializerMap.put(ObjectName.class,
                    new StringValueDeserializer(ObjectName.class));
        } catch (Throwable e) {
        }

        _staticSerializerMap.put(java.sql.Date.class, new Hessian3SqlDateSerializer());
        _staticSerializerMap.put(java.sql.Time.class, new Hessian3SqlDateSerializer());
        _staticSerializerMap.put(java.sql.Timestamp.class, new Hessian3SqlDateSerializer());

        _staticSerializerMap.put(InputStream.class,
                new InputStreamSerializer());
        _staticDeserializerMap.put(InputStream.class,
                new InputStreamDeserializer());

        try {
            _staticDeserializerMap.put(java.sql.Date.class,
                    new SqlDateDeserializer(java.sql.Date.class));
            _staticDeserializerMap.put(java.sql.Time.class,
                    new SqlDateDeserializer(java.sql.Time.class));
            _staticDeserializerMap.put(java.sql.Timestamp.class,
                    new SqlDateDeserializer(java.sql.Timestamp.class));
        } catch (Throwable e) {
            e.printStackTrace();
        }

        // hessian/3bb5
        try {
            Class stackTrace = StackTraceElement.class;

            _staticDeserializerMap.put(stackTrace, new StackTraceElementDeserializer());
        } catch (Throwable e) {
        }

        try {
            if (isJava8()) {
                _staticSerializerMap.put(Class.forName("java.time.LocalTime"), create(LocalTimeHandle.class));
                _staticSerializerMap.put(Class.forName("java.time.LocalDate"), create(LocalDateHandle.class));
                _staticSerializerMap.put(Class.forName("java.time.LocalDateTime"), create(LocalDateTimeHandle.class));

                _staticSerializerMap.put(Class.forName("java.time.Instant"), create(InstantHandle.class));
                _staticSerializerMap.put(Class.forName("java.time.Duration"), create(DurationHandle.class));
                _staticSerializerMap.put(Class.forName("java.time.Period"), create(PeriodHandle.class));

                _staticSerializerMap.put(Class.forName("java.time.Year"), create(YearHandle.class));
                _staticSerializerMap.put(Class.forName("java.time.YearMonth"), create(YearMonthHandle.class));
                _staticSerializerMap.put(Class.forName("java.time.MonthDay"), create(MonthDayHandle.class));

                _staticSerializerMap.put(Class.forName("java.time.OffsetDateTime"), create(OffsetDateTimeHandle.class));
                _staticSerializerMap.put(Class.forName("java.time.ZoneOffset"), create(ZoneOffsetHandle.class));
                _staticSerializerMap.put(Class.forName("java.time.OffsetTime"), create(OffsetTimeHandle.class));
                _staticSerializerMap.put(Class.forName("java.time.ZonedDateTime"), create(ZonedDateTimeHandle.class));
            }
        } catch (Throwable t) {
            log.warning(String.valueOf(t.getCause()));
        }
    }

    @Override
    public ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    @Override
    protected Serializer getDefaultSerializer(Class cl) {
        if (_defaultSerializer != null)
            return _defaultSerializer;

        if (!Serializable.class.isAssignableFrom(cl)
                && !_isAllowNonSerializable) {
            throw new IllegalStateException("Serialized class " + cl.getName() + " must implement java.io.Serializable");
        }

        return new Hessian3JavaSerializer(cl, _loader);
    }

    @Override
    protected Deserializer getDefaultDeserializer(Class cl) {
        return new Hessian3JavaDeserializer(cl);
    }

    @Override
    public void setSendCollectionType(boolean isSendType) {
        if (_collectionSerializer == null)
            _collectionSerializer = new Hessian3CollectionSerializer();

        _collectionSerializer.setSendJavaType(isSendType);

        if (_mapSerializer == null)
            _mapSerializer = new Hessian3MapSerializer();

        _mapSerializer.setSendJavaType(isSendType);
    }

    @Override
    public Serializer getSerializer(Class cl)
            throws HessianProtocolException {
        Serializer serializer;

        serializer = (Serializer) _staticSerializerMap.get(cl);
        if (serializer != null) {
            return serializer;
        }

        if (_cachedSerializerMap != null) {
            serializer = (Serializer) _cachedSerializerMap.get(cl);
            if (serializer != null) {
                return serializer;
            }
        }

        for (int i = 0;
             serializer == null && _factories != null && i < _factories.size();
             i++) {
            AbstractSerializerFactory factory;

            factory = (AbstractSerializerFactory) _factories.get(i);

            serializer = factory.getSerializer(cl);
        }

        if (serializer != null) {

        } else if (isZoneId(cl))
            serializer = ZoneIdSerializer.getInstance();
        else if (isEnumSet(cl))
            serializer = EnumSetSerializer.getInstance();
        else if (Hessian3JavaSerializer.getWriteReplace(cl) != null)
            serializer = new JavaSerializer(cl, _loader);

        else if (HessianRemoteObject.class.isAssignableFrom(cl))
            serializer = new RemoteSerializer();

        else if (Map.class.isAssignableFrom(cl)) {
            if (_mapSerializer == null)
                _mapSerializer = new Hessian3MapSerializer();

            serializer = _mapSerializer;
        } else if (Collection.class.isAssignableFrom(cl)) {
            if (_collectionSerializer == null) {
                _collectionSerializer = new Hessian3CollectionSerializer();
            }

            serializer = _collectionSerializer;
        } else if (cl.isArray()) {
            serializer = new Hessian3ArraySerializer();
        } else if (Throwable.class.isAssignableFrom(cl)) {
            serializer = new ThrowableSerializer(cl, getClassLoader());
        } else if (InputStream.class.isAssignableFrom(cl)) {
            serializer = new InputStreamSerializer();
        } else if (Iterator.class.isAssignableFrom(cl)) {
            serializer = IteratorSerializer.create();
        } else if (Enumeration.class.isAssignableFrom(cl)) {
            serializer = EnumerationSerializer.create();
        } else if (Calendar.class.isAssignableFrom(cl)) {
            serializer = CalendarSerializer.create();
        } else if (Locale.class.isAssignableFrom(cl)) {
            serializer = LocaleSerializer.create();
        } else if (Enum.class.isAssignableFrom(cl)) {
            serializer = new Hessian3EnumSerializer(cl);
        }

        if (serializer == null) {
            serializer = getDefaultSerializer(cl);
        }

        if (_cachedSerializerMap == null) {
            _cachedSerializerMap = new ConcurrentHashMap(8);
        }

        _cachedSerializerMap.put(cl, serializer);

        return serializer;
    }

    private static void addBasic3(Class cl, String typeName, int type) {
        _staticSerializerMap.put(cl, new Hessian3BasicSerializer(type));

        Deserializer deserializer = new BasicDeserializer(type);
        _staticDeserializerMap.put(cl, deserializer);
        _staticTypeMap.put(typeName, deserializer);
    }
}
