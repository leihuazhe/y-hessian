package com.yunji.fms;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Huhan
 * @title: InvoiceInfoRequest
 * @description: 开票结果回传请求参数
 * @date 2019/6/26 15:56
 */
@Data
public class InvoiceInfoRequest implements Serializable {
    /**
     * 开票申请ID
     */
    private String applyId;

    /**
     * 开票申请状态。1已开票,2已作废,3已驳回
     */
    private String status;

    /**
     * 开票方式。0电子发票；1纸质发票
     */
    private String billingKind;

    /**
     * PDF文件。开票申请状态为1且开票方式为0时必填 ，文件大小小于5M
     * 以base64编码格式传入，需要转换为文件
     */
    private String pdfFile;

    /**
     * 本地临时存储路径
     */
    private String pdfFilePath;

    /**
     * 发票代码。开票申请状态为1时必填
     */
    private String invoiceCode;

    /**
     * 发票号码。开票申请状态为1时必填
     */
    private String invoiceNo;

    /**
     * 运单号。开票申请状态为1且开票方式为1时必填
     */
    private String logisticsNumber;

    /**
     * 物流公司代码.开票申请状态为1且开票方式为1时必填
     */
    private String logisticsCompanyNo;

    /**
     * 驳回原因.开篇申请状态3时必填，最大长度100
     */
    private String rejectReason;
}
