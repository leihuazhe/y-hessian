package gateway.test.api;

import java.io.Serializable;

/**
 * @author Denim.leihz 2019-07-08 10:18 PM
 */
public class OrderDetail implements Serializable {

    private String orderNo;

    private int detailSeq;

    private double Amount;

    private String remark;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getDetailSeq() {
        return detailSeq;
    }

    public void setDetailSeq(int detailSeq) {
        this.detailSeq = detailSeq;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderNo='" + orderNo + '\'' +
                ", detailSeq=" + detailSeq +
                ", Amount=" + Amount +
                ", remark='" + remark + '\'' +
                '}';
    }
}
