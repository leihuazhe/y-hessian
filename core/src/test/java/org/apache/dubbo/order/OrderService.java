package org.apache.dubbo.order;

//import com.yunji.frameworks.annotation.processor.GenServiceMetadata;

import com.yunji.frameworks.meta.GenServiceMetadata;
import org.apache.dubbo.order.test.SetSpec;

/**
 * @author Denim.leihz 2019-07-08 10:17 PM
 */
//@Metadata(group = "order")
@GenServiceMetadata
public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    OrderResponse forSetSpec(SetSpec setSpec);

    OrderResponse createOmsOrder(OmsOrderRequest request);


    OrderResponse judgeOrders(OrderRequest orderRequest, OmsOrderRequest omsOrderRequest);

    OrderResponse judgeOrders2(MultiRequest request);

}
