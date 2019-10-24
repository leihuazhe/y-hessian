package gateway.test.api;


/**
 * @author Denim.leihz 2019-07-08 10:17 PM
 */
public interface HelloService {

    String sayHello(String hello);

    String sayHello2(Hello hello);

    OrderResponse createOrder(OrderRequest request);

}
