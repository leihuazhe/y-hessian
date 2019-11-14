package org.apache.dubbo.order;

import java.io.Serializable;

/**
 * @author Denim.leihz 2019-10-23 5:19 PM
 */
public class MultiRequest implements Serializable {

    private OrderRequest orderRequest;
    private OmsOrderRequest omsOrderRequest;


    public OrderRequest getOrderRequest() {
        return orderRequest;
    }

    public void setOrderRequest(OrderRequest orderRequest) {
        this.orderRequest = orderRequest;
    }

    public OmsOrderRequest getOmsOrderRequest() {
        return omsOrderRequest;
    }

    public void setOmsOrderRequest(OmsOrderRequest omsOrderRequest) {
        this.omsOrderRequest = omsOrderRequest;
    }
}
