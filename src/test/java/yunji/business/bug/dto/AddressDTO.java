package yunji.business.bug.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author haze
 * @date created at 2019-09-06 15:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO implements Serializable {
    private String provinceName;
    private String cityName;
    private String areaName;
    /**
     * 街道
     */
    private String townName;
    private String detailAddress;
}
