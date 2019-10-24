package org.apache.dubbo.order;

/**
 * @author Denim.leihz 2019-10-15 9:25 AM
 */
public enum OrderStatus {

    DOING(1, "正在进行"),
    FINISHED(2, "完成");

    private int id;
    private String name;

    OrderStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
