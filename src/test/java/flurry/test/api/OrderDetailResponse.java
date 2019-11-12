package flurry.test.api;

import java.io.Serializable;

/**
 * @author Denim.leihz 2019-07-08 10:20 PM
 */
public class OrderDetailResponse implements Serializable {

    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
