package org.apache.dubbo.hello;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Denim.leihz 2019-07-09 2:24 PM
 */
public class HelloResponse implements Serializable {

    private int id;

    private String name;

    private Map<String, Object> attachments;

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

    public Map<String, Object> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, Object> attachments) {
        this.attachments = attachments;
    }
}
