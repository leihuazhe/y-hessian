package com.yunji.gateway.jsonserializer;

import com.yunji.dubbo.common.serialize.compatible.Hessian3Input;
import com.yunji.dubbo.common.serialize.compatible.Hessian3ObjectInput;
import com.yunji.dubbo.common.serialize.streaming.Hessian2StreamingObjectOutput;
import com.yunji.gateway.metadata.OptimizedService;
import com.yunji.gateway.metadata.OptimizedStruct;
import com.alibaba.dubbo.common.serialize.ObjectInput;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class JsonSerializer implements BeanSerializer<String> {

    private final OptimizedStruct optimizedStruct;
    private final OptimizedService optimizedService;


    public JsonSerializer(OptimizedService optimizedService,
                          OptimizedStruct optimizedStruct) {

        this.optimizedStruct = optimizedStruct;
        this.optimizedService = optimizedService;
    }

    /**
     * hessian2 buffer -> json
     */
    @Override
    public String read(ObjectInput in) throws IOException {
        JsonWriter writer = new JsonWriter();
        read((Hessian3ObjectInput) in, writer);
        return writer.toString();
    }


    /**
     * json -> hessian2 buffer
     */
    @Override
    public byte[] write(String input) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Hessian2StreamingObjectOutput out = new Hessian2StreamingObjectOutput(os);
        JsonReader jsonReader = new JsonReader(optimizedStruct, optimizedService, out);
        new JsonParser(input, jsonReader).parseJsValue();
        out.flushBuffer();
        return os.toByteArray();
    }

    @Override
    public void validate(String input) throws IOException {

    }

    @Override
    public String toString(String input) {
        return input;
    }


    private void read(Hessian3ObjectInput output, JsonCallback writer) throws IOException {
        Hessian3Input cmH2i = output.getCmH3i();
        cmH2i.setJsonCallback(writer);
        cmH2i.readObject();
    }

}
