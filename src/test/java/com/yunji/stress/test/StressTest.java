package com.yunji.stress.test;

import com.alibaba.fastjson.JSON;
import com.yunji.dubbo.common.serialize.streaming.alibaba.Hessian2StreamingObjectInput;
import com.yunji.dubbo.common.serialize.util.CodecContext;
import com.yunji.gateway.jsonserializer.JsonSerializer;
import com.yunji.gateway.metadata.OptimizedService;
import com.yunji.gateway.metadata.OptimizedStruct;
import com.yunji.gateway.metadata.tag.Method;
import com.yunji.util.MixUtils;
import gateway.test.BasicTest;
import org.apache.dubbo.common.utils.ReflectUtils;
import org.apache.dubbo.order.MultiRequest;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * @author Denim.leihz 2019-09-09 7:16 PM
 */
public class StressTest extends BasicTest {
    @Test
    public void test() throws Exception {
        testFor1kHessian3();
        testFor1kHessian2();
    }

    /**
     * 4948 ms
     * Object Array 测试
     */
    @Test
    public void testFor1kHessian3() throws Exception {
        //测试次数 -> 10万次
        int count = 10_000;
        String requestJson = MixUtils.readResource("stress_1k_2.json");
        //反序列化的 byte 数组
        byte[] bytes = getHessian3bytes(requestJson);

        String desc = ReflectUtils.getDesc(new Class[]{MultiRequest.class});
        OptimizedService optimizedService = serviceMap.get(interfaceName);
        Method method = optimizedService.getMethodMap().get("judgeOrders2");

        OptimizedStruct req = optimizedService.getOptimizedStructs().get(method.request.namespace + "." + method.request.name);
        JsonSerializer jsonSerializer = new JsonSerializer(optimizedService, req);

        CodecContext.getContext().setUseJsonDecoder(true);

        for (int i = 0; i < 10000; i++) {
            //序列化
            hessian3Serializer(requestJson, desc, jsonSerializer);
            //反序列化
            String json = hessian3DeSerializer(bytes, jsonSerializer);

            if (i % 5000 == 0) {
                System.out.println(json);
            }
        }

        long st = System.nanoTime();
        for (int i = 0; i < count; i++) {
            //序列化
            hessian3Serializer(requestJson, desc, jsonSerializer);
            //反序列化
            hessian3DeSerializer(bytes, jsonSerializer);
        }
        long et = System.nanoTime();
        long period = et - st;
        BigDecimal periodB = BigDecimal.valueOf(period);
        System.out.println("Hessian3 serializer Cost: " + period / 1000000 + " ms, avg per cost: " + periodB.divide(BigDecimal.valueOf(count), RoundingMode.HALF_UP).setScale(4, RoundingMode.HALF_UP) + " ns");
    }

    @Test
    public void testFor1kHessian2() throws Exception {
        String fastJson = MixUtils.readResource("stress_1k_2_fastjson.json");
        //测试次数 -> 10万次
        int count = 10_000;
        //测试hessian2场景
        MultiRequest request = JSON.parseObject(fastJson, MultiRequest.class);

        for (int i = 0; i < 10000; i++) {
            byte[] serialize = hessian2Utils.serialize(request);
            Object obj = hessian2Utils.deserialize(serialize);
            if (i % 5000 == 0) {
                System.out.println(obj);
            }
        }

        long st1 = System.nanoTime();
        for (int i = 0; i < count; i++) {
            MultiRequest request1 = JSON.parseObject(fastJson, MultiRequest.class);
            byte[] serialize = hessian2Utils.serialize(request1);
            hessian2Utils.deserialize(serialize);
        }
        long et1 = System.nanoTime();
        BigDecimal periodB = BigDecimal.valueOf(et1 - st1);
        System.out.println("Hessian2 serializer Cost: " + (et1 - st1) / 1000000 + " ms, avg per cost: " + periodB.divide(BigDecimal.valueOf(count), RoundingMode.HALF_UP).setScale(4, RoundingMode.HALF_UP));


    }

    //    @Test
    public void testFor1kDeSerializer(byte[] bytes, int count) throws Exception {
        testForHessian3DeSerializer(bytes, count);
    }

    public void testForHessian3Serializer(String requestJson, String desc, JsonSerializer jsonSerializer) throws Exception {
        methodCallStressTest(requestJson, desc, jsonSerializer);
    }


    public void testForHessian3DeSerializer(byte[] bytes, int count) throws Exception {
        //测试hessian3场景
        OptimizedService optimizedService = serviceMap.get(interfaceName);
        Method method = optimizedService.getMethodMap().get("judgeOrders2");

        OptimizedStruct req = optimizedService.getOptimizedStructs().get(method.request.namespace + "." + method.request.name);
        JsonSerializer jsonSerializer = new JsonSerializer(optimizedService, req);


        CodecContext.getContext().setUseJsonDecoder(true);
        //预热
        for (int i = 0; i < 100; i++) {
            Hessian2StreamingObjectInput hessian3ObjectInput = new Hessian2StreamingObjectInput(new ByteArrayInputStream(bytes));
            String json = jsonSerializer.read(hessian3ObjectInput);
            System.out.println(json);
        }

        long st = System.nanoTime();
        for (int i = 0; i < count; i++) {
            Hessian2StreamingObjectInput hessian3ObjectInput = new Hessian2StreamingObjectInput(new ByteArrayInputStream(bytes));
            jsonSerializer.read(hessian3ObjectInput);
        }
        long et = System.nanoTime();
        long period = et - st;
        BigDecimal periodB = BigDecimal.valueOf(period);
        System.out.println("Hessian3 serializer Cost: " + period + " ns, avg per cost: " + periodB.divide(BigDecimal.valueOf(count), RoundingMode.HALF_UP).setScale(4, RoundingMode.HALF_UP) + " ms");
    }

    public void testForHessian2(String requestJson, int count) {
        //测试hessian2场景
        Object req = JSON.parse(requestJson);
        MultiRequest request = JSON.parseObject(requestJson, MultiRequest.class);

        for (int i = 0; i < 100; i++) {

            byte[] serialize = hessian2Utils.serialize(request);
            Object obj = hessian2Utils.deserialize(serialize);
            System.out.println(obj);
        }

        long st1 = System.nanoTime();
        for (int i = 0; i < count; i++) {
            MultiRequest request1 = JSON.parseObject(requestJson, MultiRequest.class);
            byte[] serialize = hessian2Utils.serialize(request1);
            hessian2Utils.deserialize(serialize);
        }
        long et1 = System.nanoTime();
        BigDecimal periodB = BigDecimal.valueOf(et1 - st1);
        System.out.println("Hessian2 serializer Cost: " + (et1 - st1) + " ns, avg per cost: " + periodB.divide(BigDecimal.valueOf(count), RoundingMode.HALF_UP).setScale(4, RoundingMode.HALF_UP));
    }

    public Object methodCallStressTest(String requestJson, String desc, JsonSerializer jsonSerializer) throws Exception {
        byte[] write = jsonSerializer.write(requestJson);
        //====
        Class<?>[] pts = ReflectUtils.desc2classArray(desc);
        Object[] args = new Object[pts.length];
        return hessian3Utils.deserialize2(write, pts, args);
    }


    private byte[] getHessian3bytes(String requestJson) throws Exception {
        String desc = ReflectUtils.getDesc(new Class[]{MultiRequest.class});
        OptimizedService optimizedService = serviceMap.get(interfaceName);
        Method method = optimizedService.getMethodMap().get("judgeOrders2");
        OptimizedStruct req = optimizedService.getOptimizedStructs().get(method.request.namespace + "." + method.request.name);
        JsonSerializer jsonSerializer = new JsonSerializer(optimizedService, req);
        //预热
        Object[] args = (Object[]) methodCallStressTest(requestJson, desc, jsonSerializer);

        if (args.length == 1) {
            return hessian2Utils.serialize(args[0]);
        } else {
            return null;
        }
    }

    private Object hessian3Serializer(String requestJson, String desc, JsonSerializer jsonSerializer) throws Exception {
        return jsonSerializer.write(requestJson);
    }

    private String hessian3DeSerializer(byte[] bytes, JsonSerializer jsonSerializer) throws Exception {
        Hessian2StreamingObjectInput hessian3ObjectInput = new Hessian2StreamingObjectInput(new ByteArrayInputStream(bytes));
        return jsonSerializer.read(hessian3ObjectInput);
    }
}
