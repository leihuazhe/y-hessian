package yunji.business.bug.dto;

import java.io.Serializable;

/**
 * @author Denim.leihz 2019-09-26 3:44 PM
 */
public class BillDetailForExpressReqParam implements Serializable {
    private String waybillCode;

    public String getWaybillCode() {
        return waybillCode;
    }

    public void setWaybillCode(String waybillCode) {
        this.waybillCode = waybillCode;
    }
}
