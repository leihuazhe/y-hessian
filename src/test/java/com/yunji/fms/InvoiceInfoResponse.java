package com.yunji.fms;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Huhan
 * @title: InvoiceInfoResponse
 * @description: 开票结果回传接口响应
 * @date 2019/6/26 15:31
 */
@Data
public class InvoiceInfoResponse implements Serializable {
    /** 是否成功标识 **/
    public static final String SUCCESS_FLAG = "success";

    public static final String FAIL_FLAG = "failure";

    /** 响应码**/
    public static final String SUCCESS_CODE = "0";

    public static final String FAIL_CODE = "1";

    /** 接口错误信息 **/
    public static final String SERVICE_VALID = "接口请求成功";

    public static final String SERVICE_ERROR = "服务内部错误";

    /**
     * 是否成功标识（成功：success 失败：failure）
     */
    private String flag;

    /**
     * 响应码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 业务错误码
     */
    private String subCode;

    /**
     * 业务错误信息
     */
    private String subMessage;

    /**
     * 成功的开票申请ID
     */
    private List<String> successInfos;

    /**
     * 失败信息列表
     */
    private List<ErrorInfo> errorInfos;

    @Data
    @AllArgsConstructor
    public static class ErrorInfo implements Serializable{
        /**
         * 失败的开票申请ID
         */
        private String errorApplyId;

        /**
         * 错误代码
         */
        private String errorCode;

        /**
         * 错误原因
         */
        private String errorMessage;
    }

    public static InvoiceInfoResponse buildFailure(String message){
        InvoiceInfoResponse response = new InvoiceInfoResponse();
        response.setFlag(FAIL_FLAG);
        response.setCode(FAIL_CODE);
        response.setMessage(message);
        return response;
    }

    public static InvoiceInfoResponse buildSuccess(String subCode, String subMessage){
        InvoiceInfoResponse response = new InvoiceInfoResponse();
        response.setFlag(SUCCESS_FLAG);
        response.setCode(SUCCESS_CODE);
        response.setMessage(SERVICE_VALID);
        response.setSubCode(subCode);
        response.setSubMessage(subMessage);
        return response;
    }

    public static InvoiceInfoResponse buildSuccess(String subCode, String subMessage, List<ErrorInfo> errorInfos){
        InvoiceInfoResponse response = buildSuccess(subCode, subMessage);
        response.setErrorInfos(errorInfos);
        return response;
    }

    public static InvoiceInfoResponse buildSuccess(String subCode, String subMessage, List<ErrorInfo> errorInfos, List<String> successInfos){
        InvoiceInfoResponse response = buildSuccess(subCode, subMessage, errorInfos);
        response.setSuccessInfos(successInfos);
        return response;
    }
}
