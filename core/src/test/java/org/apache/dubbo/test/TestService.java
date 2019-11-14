package org.apache.dubbo.test;

import com.yunji.frameworks.meta.GenServiceMetadata;
import org.apache.dubbo.common.DataResponse;

import java.util.List;

/**
 * @author Denim.leihz 2019-08-06 3:44 PM
 */
@GenServiceMetadata
public interface TestService {

    TestResponse getDataById(int orderId);


    TestResponse2 getDataMap();


    TestResponse3 getDataList();


    DataResponse<List<TestResponse3.Country>> countries();
}
