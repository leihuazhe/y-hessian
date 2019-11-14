package org.apache.dubbo.hello;


//import com.yunji.frameworks.annotation.processor.GenServiceMetadata;

import com.yunji.frameworks.meta.GenServiceMetadata;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Denim.leihz 2019-07-08 10:17 PM
 */
@GenServiceMetadata
public interface HelloService {

    String sayHello(String hello);

    HelloResponse sayHello2(HelloRequest hello);

    String hello();

    void requestHello(HelloBean helloBean);

    void requestHello(int id);

    Map<String, String> helloForMap(String name);
}
