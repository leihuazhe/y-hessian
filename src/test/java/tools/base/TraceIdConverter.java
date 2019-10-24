package tools.base;

import redis.clients.jedis.Jedis;

import java.math.BigInteger;

/**
 * @author Denim.leihz 2019-08-12 5:56 PM
 */
public class TraceIdConverter {

    public static void main(String[] args) {
//        String id = "F90BEA131E2A333C";
        String id = "B7DE6FC28DD7E28D";
        long l1 = new BigInteger(id, 16).longValue();
        System.out.println(l1);

        Jedis jedis = new Jedis();


    }
}
