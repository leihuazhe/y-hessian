package org.apache.dubbo.hello;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * @author Denim.leihz 2019-07-09 2:24 PM
 */
public class HelloRequest implements Serializable {

    private int id;

    private String name;

//    private Timestamp timestamp;

//    private Date date;

//    private BigEnum bigEnum;

    private Map<String, String> attachments;

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

    public Map<String, String> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, String> attachments) {
        this.attachments = attachments;
    }

//    public Timestamp getTimestamp() {
//        return timestamp;
//    }
//
//    public void setTimestamp(Timestamp timestamp) {
//        this.timestamp = timestamp;
//    }
//
//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
//
//    public BigEnum getBigEnum() {
//        return bigEnum;
//    }
//
//    public void setBigEnum(BigEnum bigEnum) {
//        this.bigEnum = bigEnum;
//    }

    @Override
    public String toString() {
        return "HelloRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", attachments=" + attachments +
                '}';
    }
}
