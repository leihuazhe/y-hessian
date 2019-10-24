package gateway.test;

import com.alibaba.dubbo.common.utils.ReflectUtils;
import com.google.common.base.Joiner;
import com.yunji.gateway.jsonserializer.JsonSerializer;
import com.yunji.gateway.metadata.OptimizedService;
import com.yunji.gateway.metadata.OptimizedStruct;
import com.yunji.gateway.metadata.tag.Method;
import com.yunji.gateway.metadata.tag.Service;
import gateway.test.utils.Hessian2Utils;
import gateway.test.utils.Hessian3Utils;
import org.apache.commons.io.IOUtils;
import org.apache.dubbo.common.utils.ClassUtils;
import org.junit.Before;

import javax.xml.bind.JAXB;
import java.io.IOException;
import java.io.StringReader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Denim.leihz 2019-10-15 3:54 PM
 */
public class BasicTest {

    protected static String interfaceName = "org.apache.dubbo.order.OrderService";

    protected ConcurrentMap<String, OptimizedService> serviceMap = new ConcurrentHashMap<>();


    protected Hessian2Utils hessian2Utils = new Hessian2Utils();
    protected Hessian3Utils hessian3Utils = new Hessian3Utils();

    @Before
    public void prepare() throws Exception {
        //metadata
        String metaString = Joiner
                .on("\n")
                .join(IOUtils.readLines(
                        ClassUtils.getClassLoader()
                                .getResourceAsStream(interfaceName + ".xml")
                        )
                );
        OptimizedService optimizedService;
        try (StringReader reader = new StringReader(metaString)) {
            Service service = JAXB.unmarshal(reader, Service.class);
            optimizedService = new OptimizedService(service);
        }
        serviceMap.put(interfaceName, optimizedService);
    }


    protected void basicTest(String methodName, String requestJson) throws IOException {


        OptimizedService optimizedService = serviceMap.get(interfaceName);
        Method method = optimizedService.getMethodMap().get(methodName);
        OptimizedStruct req = optimizedService.getOptimizedStructs().get(method.request.namespace + "." + method.request.name);
        JsonSerializer jsonSerializer = new JsonSerializer(optimizedService, req);

        byte[] write = jsonSerializer.write(requestJson);

        Object deserialize = hessian2Utils.deserialize(write);

        System.out.println("\n............ 结果展示 BEGIN............ \n");

        System.out.println(deserialize.toString());

        System.out.println("\n............ 结果展示 END............ \n");
    }


    protected void methodCallTest(String methodName, String requestJson, String desc) throws Exception {
        OptimizedService optimizedService = serviceMap.get(interfaceName);
        Method method = optimizedService.getMethodMap().get(methodName);
        OptimizedStruct req = optimizedService.getOptimizedStructs().get(method.request.namespace + "." + method.request.name);
        JsonSerializer jsonSerializer = new JsonSerializer(optimizedService, req);

        byte[] write = jsonSerializer.write(requestJson);

        //====
        Class<?>[] pts = ReflectUtils.desc2classArray(desc);
        Object[] args = new Object[pts.length];

        Object[] deserializes = hessian3Utils.deserialize2(write, pts, args);

        System.out.println("\n............ 结果展示 BEGIN............ \n");


        for (Object deserialize : deserializes) {
            if (deserialize == null) {
                System.out.println(" null");
                continue;
            }
            System.out.println(deserialize.toString());
            System.out.println();
        }
        System.out.println("\n............ 结果展示 END............ \n");
    }

    protected Object methodCallStressTest(String requestJson, String desc, OptimizedService optimizedService, Method method) throws Exception {
        OptimizedStruct req = optimizedService.getOptimizedStructs().get(method.request.namespace + "." + method.request.name);
        JsonSerializer jsonSerializer = new JsonSerializer(optimizedService, req);
        byte[] write = jsonSerializer.write(requestJson);
        //====
        Class<?>[] pts = org.apache.dubbo.common.utils.ReflectUtils.desc2classArray(desc);
        Object[] args = new Object[pts.length];
        return hessian3Utils.deserialize2(write, pts, args);
    }
}
