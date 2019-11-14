package org.apache.dubbo.hello;

import java.io.Serializable;

/**
 * @author Denim.leihz 2019-08-05 4:10 PM
 */

public class HelloBean implements Serializable {

    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public HelloBean(Integer id, String name) {
        this.id = id;
        this.name = name;
    }*/

    public HelloBean() {
    }
}
