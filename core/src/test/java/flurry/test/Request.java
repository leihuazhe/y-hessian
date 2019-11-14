package flurry.test;

import org.apache.dubbo.order.OmsOrderRequest;
import org.apache.dubbo.order.OrderRequest;

import java.io.Serializable;

/**
 * @author Denim.leihz 2019-10-22 7:06 PM
 */
public class Request implements Serializable {

    private OmsOrderRequest args1;

    private OrderRequest args0;


    public OmsOrderRequest getArgs1() {
        return args1;
    }

    public void setArgs1(OmsOrderRequest args1) {
        this.args1 = args1;
    }

    public OrderRequest getArgs0() {
        return args0;
    }

    public void setArgs0(OrderRequest args0) {
        this.args0 = args0;
    }
}
