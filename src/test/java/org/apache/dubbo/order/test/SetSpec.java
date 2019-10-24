package org.apache.dubbo.order.test;

import lombok.Data;
import org.apache.dubbo.order.OrderDetail;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * @author Denim.leihz 2019-10-15 2:08 PM
 */
@Data
public class SetSpec implements Serializable {

    private Set<OrderDetail> orderDetailSet;

    private Map<String, OrderDetail> orderDetailMap;

//    private Map<OrderKey, OrderDetail> orderDetailMap2;

//    private SetSpec[] specs;

//    private String[] names;

    private byte flag;
    private Byte byteFlagA;

    private short shortFlag;
    private Short shortFlagA;

    private int intFlag;
    private Integer integerFlag;

    private long longFlag;
    private Long longFlagA;

    private double doubles;
    private Double doublesA;

    private float floats;
    private Float floatsA;

    private char chars;
    private Character charsA;

    private boolean aBoolean;
    private Boolean aBooleanA;
}
