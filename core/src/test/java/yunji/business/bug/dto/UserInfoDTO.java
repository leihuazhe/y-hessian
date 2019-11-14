package yunji.business.bug.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 收件人/发件人信息
 *
 * @author haze
 * @date created at 2019-09-06 15:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO implements Serializable {
    /**
     * 发货地信息,重要,根据此字段查询面单余额
     */
    private AddressDTO address;
    /**
     * 手机电话
     */
    private String mobile;
    /**
     * 姓名
     */
    private String name;
    /**
     * 固定电话
     */
    private String phone;
}