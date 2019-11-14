package org.apache.dubbo.order;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author Denim.leihz 2019-07-08 10:17 PM
 */
@Data
@ToString
public class OrderRequest implements Serializable {

    public static final Integer code = 200;

    private String orderNo;

    private int productCount;

    private double totalAmount;

    private String storeId;

    private Boolean flag;

    private OrderStatus status;

    private List<OrderDetail> orderDetailList;

//    private OrderRequest[] requests;
}
