package gateway.test;

import com.google.common.base.Joiner;
import org.apache.commons.io.IOUtils;
import org.apache.dubbo.common.utils.ClassUtils;
import org.apache.dubbo.common.utils.ReflectUtils;
import org.apache.dubbo.order.OmsOrderRequest;
import org.apache.dubbo.order.OrderRequest;
import org.junit.Test;

import java.io.IOException;

/**
 * -Dconfig_env=local -Ddiamond_server_host=tdiamond.yunjiweidian.com -Xbootclasspath/a:/Users/maple/yunji/erlang/transmittable-thread-local-2.1.1.jar -javaagent:/Users/maple/yunji/erlang/transmittable-thread-local-2.1.1.jar -javaagent:/Users/maple/yunji/erlang/erlang-trace-agent-1.0.0/erlang-trace-agent-1.0.0.jar
 *
 * @author Denim.leihz 2019-09-09 7:16 PM
 */
public class MethodCallJsonReaderTest extends BasicTest {
    /**
     * Object Array 测试
     *
     * @throws IOException
     */
    @Test
    public void test1() throws Exception {
        String requestJson = Joiner.on("\n").join(IOUtils.readLines(
                ClassUtils.getClassLoader().getResourceAsStream("method_call_1.json")));

        String desc = ReflectUtils.getDesc(new Class[]{OrderRequest.class, OmsOrderRequest.class});

        methodCallTest("judgeOrders", requestJson, desc);
    }

    /**
     * 方法调用的一些异常测试
     */
    @Test
    public void test2() throws Exception {
        String requestJson = Joiner.on("\n").join(IOUtils.readLines(
                ClassUtils.getClassLoader().getResourceAsStream("method_call_2_ex.json")));

        String desc = ReflectUtils.getDesc(new Class[]{OrderRequest.class, OmsOrderRequest.class});

        methodCallTest("judgeOrders", requestJson, desc);
    }


}
