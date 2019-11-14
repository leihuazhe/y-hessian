package com.yunji.fms;

/**
 * @author Denim.leihz 2019-08-26 12:55 PM
 */

import java.util.List;

/**
 * @author Huhan
 * @title: IPopInvoiceQueryService
 * @description: POP商家订单开票优化, 对接开放平台
 * @date 2019/6/26 15:22
 */
public interface IPopInvoiceQueryService {

    /**
     * POP商家回传发票信息
     */
    InvoiceInfoResponse uploadInvoiceCreateResult(List<InvoiceInfoRequest> invoiceInfoRequests, String storeCode);
}
