package com.yunji.mock;

import java.io.Serializable;

/**
 * @author Denim.leihz 2019-09-02 7:51 PM
 */
public class BaseSmsCode implements Serializable {

    private String phone;
    private String countryCode;
    private String requestIp;
    private String channel; //需要自定义所属渠道，注意：发送验证码和获取验证码需要一直，否则验证不通过

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
