package flurry.test.api;

import java.io.Serializable;
import java.util.List;

/**
 * @author Denim.leihz 2019-07-08 10:17 PM
 */
public class OrderResponse implements Serializable {

    private String orderNo;


    private double totalAmount;


    private List<OrderDetailResponse> orderDetailResponses;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderDetailResponse> getOrderDetailResponses() {
        return orderDetailResponses;
    }

    public void setOrderDetailResponses(List<OrderDetailResponse> orderDetailResponses) {
        this.orderDetailResponses = orderDetailResponses;
    }
}
