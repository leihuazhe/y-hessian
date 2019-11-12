package flurry.test.api;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * @author Denim.leihz 2019-07-09 2:24 PM
 */
public class Hello implements Serializable {

    private int id;

    private String name;

    private Map<Long, String> attachments;

    private Timestamp timestamp;

    private Date date;

    private BigEnum bigEnum;

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

    public Map<Long, String> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<Long, String> attachments) {
        this.attachments = attachments;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigEnum getBigEnum() {
        return bigEnum;
    }

    public void setBigEnum(BigEnum bigEnum) {
        this.bigEnum = bigEnum;
    }

    @Override
    public String toString() {
        return "Hello{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", attachments=" + attachments +
                '}';
    }
}
