package com.yunji.mock;

/**
 * @author Denim.leihz 2019-09-02 7:52 PM
 */
public class SmsCodeVerifyDTO extends BaseSmsCode {

    private String smsCode;

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }


    public static class Builder {

        private SmsCodeVerifyDTO target;

        public Builder() {
            target = new SmsCodeVerifyDTO();
        }

        public Builder requestIp(String requestIp) {
            target.setRequestIp(requestIp);
            return this;
        }

        public Builder countryCode(String countryCode) {
            target.setCountryCode(countryCode);
            return this;
        }

        public Builder phone(String phone) {
            target.setPhone(phone);
            return this;
        }

        public Builder channel(String channel) {
            target.setChannel(channel);
            return this;
        }

        public Builder smsCode(String smsCode) {
            target.setSmsCode(smsCode);
            return this;
        }

        public SmsCodeVerifyDTO build() {
            return target;
        }

    }
}
