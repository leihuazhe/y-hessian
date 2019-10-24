package gateway.test;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.yunji.gateway.jsonserializer.JsonSerializer;
import com.yunji.gateway.metadata.OptimizedService;
import com.yunji.gateway.metadata.OptimizedStruct;
import com.yunji.gateway.metadata.tag.Method;
import gateway.test.utils.Hessian2Utils;
import gateway.test.utils.Hessian3Utils;
import org.apache.commons.io.IOUtils;
import org.apache.dubbo.common.utils.ClassUtils;
import org.apache.dubbo.common.utils.ReflectUtils;
import org.apache.dubbo.order.OmsOrderRequest;
import org.apache.dubbo.order.OrderRequest;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Denim.leihz 2019-09-09 7:16 PM
 */
public class StressMethodCallJsonReaderTest extends BasicTest {

    /**
     * 4948 ms
     * Object Array 测试
     */
    @Test
    public void test1() throws Exception {
        String requestJson = Joiner.on("\n").join(IOUtils.readLines(
//                ClassUtils.getClassLoader().getResourceAsStream("stress_test2.json")));
                ClassUtils.getClassLoader().getResourceAsStream("stress_1k.json")));

        String desc = ReflectUtils.getDesc(new Class[]{OrderRequest.class, OmsOrderRequest.class});

        OptimizedService optimizedService = serviceMap.get(interfaceName);
        Method method = optimizedService.getMethodMap().get("judgeOrders");

        for (int i = 0; i < 100; i++) {
            methodCallStressTest(requestJson, desc, optimizedService, method);
        }

        long st = System.currentTimeMillis();

        for (int i = 0; i < 100000; i++) {
            methodCallStressTest(requestJson, desc, optimizedService, method);
        }

        long et = System.currentTimeMillis();

        System.out.println("Stream serializer Cost: " + (et - st) + " ms");
    }

    /**
     * 83205ms
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        String requestJson = Joiner.on("\n").join(IOUtils.readLines(
                ClassUtils.getClassLoader().getResourceAsStream("stress_1k.json")));

        Request request = JSON.parseObject(requestJson, Request.class);

        for (int i = 0; i < 100; i++) {
            byte[] serialize = hessian2Utils.serialize(request);
            Object deserialize = hessian2Utils.deserialize(serialize);
        }

        long st = System.currentTimeMillis();

        for (int i = 0; i < 100000; i++) {
            byte[] serialize = hessian2Utils.serialize(request);
            Object deserialize = hessian2Utils.deserialize(serialize);
        }
        long et = System.currentTimeMillis();
        System.out.println("normal serializer Cost: " + (et - st) + " ms");
    }
}
