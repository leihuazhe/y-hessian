package org.apache.dubbo.test;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Denim.leihz 2019-08-14 2:03 PM
 */
public class TestResponse implements Serializable {

    private String info;

    private Date date;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TestResponse(String info, Date date) {
        this.info = info;
        this.date = date;
    }
}
