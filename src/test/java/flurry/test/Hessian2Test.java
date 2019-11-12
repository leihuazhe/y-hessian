package flurry.test;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.google.gson.Gson;
import flurry.test.api.BigEnum;
import flurry.test.api.Hello;
import flurry.test.api.OrderDetail;
import flurry.test.api.OrderRequest;
import flurry.test.utils.Hessian2Utils;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;


/**
 * @author Denim.leihz 2019-07-09 2:40 PM
 */
public class Hessian2Test {
    private final static Logger logger = LoggerFactory.getLogger(Hessian2Test.class);

    private static Gson gson = new Gson();

    private Hessian2Utils hessian2Utils = new Hessian2Utils();


    @Test
    public void test1() {
        OrderRequest request = new OrderRequest();
        List<OrderDetail> orderDetailList = new ArrayList<>();

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setAmount(12.3);
        orderDetail.setOrderNo("1023");

        OrderDetail orderDetail3 = new OrderDetail();
        orderDetail3.setAmount(10.3);

        orderDetailList.add(orderDetail);
        orderDetailList.add(orderDetail3);

        request.setOrderDetialList(orderDetailList);
        //serialize
        byte[] serializeObj = hessian2Utils.serialize(request);
        //deserialize
        Object deserialize = hessian2Utils.deserialize(serializeObj);

        System.out.println("\n............ 结果展示 BEGIN............ \n");

        System.out.println(deserialize.toString());

        System.out.println("\n............ 结果展示 END............ \n");
    }

    @Test
    public void testAddRef() {
        OrderRequest request = new OrderRequest();
        List<OrderDetail> orderDetailList = new ArrayList<>();

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setAmount(12.3);
        orderDetail.setOrderNo("1023");

        request.setOrderDetialList(orderDetailList);
        request.setOrderDetialList(orderDetailList);


        //serialize
        byte[] serializeObj = hessian2Utils.serialize(request);
        //deserialize
        Object deserialize = hessian2Utils.deserialize(serializeObj);

        System.out.println("\n............ 结果展示 BEGIN............ \n");

        System.out.println(deserialize.toString());

        System.out.println("\n............ 结果展示 END............ \n");
    }


    public void test2() {
        Hello hello = new Hello();
        hello.setId(1023);
        hello.setName("Maple");
        Map<Long, String> attachments = new HashMap<>();
        attachments.put(1L, "20190711");
        attachments.put(2L, "20190712");
        hello.setAttachments(attachments);

        hello.setBigEnum(BigEnum.MAPLE);
        hello.setTimestamp(new Timestamp(System.currentTimeMillis()));
        hello.setDate(new Date());

        String s = gson.toJson(hello);

        System.out.println(s);

        byte[] serializeObj = hessian2Utils.serialize(hello);


        Object deserialize = hessian2Utils.deserialize(serializeObj);

        System.out.println(deserialize.toString());

    }

    @Test
    public void test4() {
        OrderRequest request = new OrderRequest();
        List<OrderDetail> orderDetailList = new ArrayList<>();

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setAmount(12.3);
        orderDetail.setOrderNo("1023");
        orderDetail.setBigDecimal(BigDecimal.valueOf(12.41));

        orderDetailList.add(orderDetail);

        request.setOrderDetialList(orderDetailList);
        //serialize
        byte[] serializeObj = hessian2Utils.serialize(request);
        //deserialize
        Object deserialize = hessian2Utils.deserialize(serializeObj);

        System.out.println("\n............ 结果展示 BEGIN............ \n");

        System.out.println(deserialize.toString());

        System.out.println("\n............ 结果展示 END............ \n");
    }

    public static void test3() {
        BigEnum maple = BigEnum.MAPLE;

        boolean anEnum = BigEnum.class.isEnum();

        System.out.println(anEnum);

    }
}
