package org.apache.dubbo.order.test;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Denim.leihz 2019-10-15 4:12 PM
 */
@Data
public class OrderKey implements Serializable {

    private int id;

    private String name;


}
