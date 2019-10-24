package com.yunji.multi;

//import com.yunji.frameworks.annotation.processor.GenServiceMetadata;
import com.yunji.frameworks.meta.GenServiceMetadata;
import org.apache.dubbo.hello.HelloRequest;
import org.apache.dubbo.hello.HelloResponse;

import java.util.List;

/**
 * @author Denim.leihz 2019-08-22 8:44 PM
 */
@GenServiceMetadata
public interface MultiParameterService {

    String getName(String name, Integer code);


    HelloResponse sayHello(String name, int code, HelloRequest request);


    HelloResponse getByParams(String applyId, String storeCode);


    HelloResponse getList(List<FmsVO> fmsVOList, String storeCode);

    String detail(int id);

    String detail(int id, String name);


}
