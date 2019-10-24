package gateway.test.api;

import java.io.Serializable;
import java.util.List;

/**
 * @author Denim.leihz 2019-07-08 10:17 PM
 */
public class OrderRequest implements Serializable {

    private String orderNo;

    private int productCount;

    private double totalAmount;

    private String storeId;

    private List<OrderDetail> orderDetialList;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public List<OrderDetail> getOrderDetialList() {
        return orderDetialList;
    }

    public void setOrderDetialList(List<OrderDetail> orderDetialList) {
        this.orderDetialList = orderDetialList;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "orderNo='" + orderNo + '\'' +
                ", productCount=" + productCount +
                ", totalAmount=" + totalAmount +
                ", storeId='" + storeId + '\'' +
                ", orderDetialList=" + orderDetialList +
                '}';
    }
}
