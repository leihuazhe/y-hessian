package com.yunji.addref.test;

import gateway.test.BasicTest;
import org.apache.dubbo.order.MultiRequest;
import org.apache.dubbo.order.OrderDetail;
import org.apache.dubbo.order.OrderRequest;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @author Denim.leihz 2019-09-09 7:16 PM
 */
public class AddRefTest extends BasicTest {

    @Test
    public void testAddRef() throws Exception {
        MultiRequest request = new MultiRequest();
        OrderRequest orderRequest = new OrderRequest();
        OrderDetail orderDetail = new OrderDetail();

        orderRequest.setOrderDetailList(new ArrayList<OrderDetail>() {{
            add(orderDetail);
            add(orderDetail);
            add(orderDetail);
        }});
        request.setOrderRequest(orderRequest);


        byte[] serialize = hessian2Utils.serialize(request);

        hessian2Utils.deserialize(serialize);
    }


}
