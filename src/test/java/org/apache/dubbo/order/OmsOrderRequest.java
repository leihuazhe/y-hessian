package org.apache.dubbo.order;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author Denim.leihz 2019-09-23 12:31 PM
 */
@Data
@ToString
public class OmsOrderRequest implements Serializable {
    /**
     * 订单已部分发货
     */
    public static final Integer STATUS_HAD_PORTION_SEND = 0;

    /**
     * 订单已全部发货
     */
    public static final Integer STATUS_HAD_SEND = 1;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 物流发货状态.0:部分确认，１:确认完成
     */
    private Integer status;

    /**
     * 物流单列表
     */
    private List<LogisticsItem> logisticsList;


    /**
     * 物流单详情
     *
     * @author chengbowen
     */
    @Data
    @ToString
    public static class LogisticsItem implements Serializable {
        /**
         * 商品编码
         */
        private String skuNo;

        /**
         * 物流公司编号（参见附录物流编码映射）
         */
        private String logisticsCompanyNo;
    }
}
