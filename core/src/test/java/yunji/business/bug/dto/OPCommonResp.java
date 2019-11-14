package yunji.business.bug.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author haze
 * @date created at 2019/9/25 12:15 下午
 */
@Data
public class OPCommonResp implements Serializable {
    private Integer code;
    private String subCode;
    private String message;
    private String subMessage;
    private Object data;

    public static OPCommonResp success(Object data) {
        OPCommonResp dto = new OPCommonResp();
        dto.setCode(1);
        dto.setMessage("请求成功");
        dto.setData(data);
        return dto;
    }

    public static OPCommonResp success() {
        OPCommonResp dto = new OPCommonResp();
        dto.setCode(1);
        dto.setMessage("请求成功");
        return dto;
    }
}
