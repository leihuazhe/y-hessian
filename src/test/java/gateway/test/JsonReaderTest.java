package gateway.test;

import com.google.common.base.Joiner;
import org.apache.commons.io.IOUtils;
import org.apache.dubbo.common.utils.ClassUtils;
import org.apache.dubbo.common.utils.ReflectUtils;
import org.apache.dubbo.order.OmsOrderRequest;
import org.apache.dubbo.order.OrderRequest;
import org.apache.dubbo.order.test.SetSpec;
import org.junit.Test;

import java.io.IOException;

/**
 * -Dconfig_env=local -Ddiamond_server_host=tdiamond.yunjiweidian.com -Xbootclasspath/a:/Users/maple/yunji/erlang/transmittable-thread-local-2.1.1.jar -javaagent:/Users/maple/yunji/erlang/transmittable-thread-local-2.1.1.jar -javaagent:/Users/maple/yunji/erlang/erlang-trace-agent-1.0.0/erlang-trace-agent-1.0.0.jar
 *
 * @author Denim.leihz 2019-09-09 7:16 PM
 */
public class JsonReaderTest extends BasicTest {


    @Test
    public void test1() throws Exception {
        String requestJson = Joiner.on("\n").join(IOUtils.readLines(
                ClassUtils.getClassLoader().getResourceAsStream("request3.json")));

        String desc = ReflectUtils.getDesc(new Class[]{OrderRequest.class});

        methodCallTest("createOrder", requestJson, desc);
    }

    /**
     * Set 测试
     *
     * @throws IOException
     */
    @Test
    public void test2() throws IOException {
        String requestJson = Joiner.on("\n").join(IOUtils.readLines(
                ClassUtils.getClassLoader().getResourceAsStream("set.json")));


        basicTest("forSetSpec", requestJson);

    }

    /**
     * Map 测试
     *
     * @throws IOException
     */
    @Test
    public void test3() throws IOException {
        String requestJson = Joiner.on("\n").join(IOUtils.readLines(
                ClassUtils.getClassLoader().getResourceAsStream("map.json")));

        basicTest("forSetSpec", requestJson);
    }

    /**
     * String Array 测试
     *
     * @throws IOException
     */
    @Test
    public void test4() throws IOException {
        String requestJson = Joiner.on("\n").join(IOUtils.readLines(
                ClassUtils.getClassLoader().getResourceAsStream("string_array.json")));

        basicTest("forSetSpec", requestJson);
    }

    /**
     * Object Array 测试
     *
     * @throws IOException
     */
    @Test
    public void test5() throws IOException {
        String requestJson = Joiner.on("\n").join(IOUtils.readLines(
                ClassUtils.getClassLoader().getResourceAsStream("object_array.json")));

        basicTest("forSetSpec", requestJson);
    }


    @Test
    public void test6() throws Exception {
        String requestJson = Joiner.on("\n").join(IOUtils.readLines(
                ClassUtils.getClassLoader().getResourceAsStream("byte_test.json")));

        String desc = ReflectUtils.getDesc(new Class[]{SetSpec.class});

        methodCallTest("forSetSpec", requestJson, desc);
    }


}
