package com.yunji.multi;

import java.io.Serializable;

/**
 * @author Denim.leihz 2019-08-26 12:40 PM
 */
public class FmsVO implements Serializable {

    private String applyId;

    private String dataType;

    private String data;

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FmsVO{" +
                "applyId='" + applyId + '\'' +
                ", dataType='" + dataType + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
