package yunji.business.bug.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 快递公司查询:
 * 面单详情
 *
 * @author haze
 * @date created at 2019-09-06 15:48
 */
@Data
public class BillDetailDTO implements Serializable {

    private String sellerId;
    private String cpCode;
    private String branchCode;
    private String waybillCode;
    //todo   private String   msgVersion
    private String segmentCode;
    /**
     * 发件人信息
     */
    private UserInfoDTO sender;
    /**
     * 收件人信息
     */
    private UserInfoDTO consignee;

    /**
     * 面单状态（-2:回收；-1：取消；2：新分配或更新）
     */
    private Integer status;
    /**
     * 三段码
     */
    private String sanDuanMa;
    /**
     * 商品信息列表
     */
    private List<ProductItem> itemList;
    /**
     * 重量
     */
    private BigDecimal weight;
    /**
     * 体积
     */
    private BigDecimal volume;

//todo    private String productType;
    /**
     * 订单渠道
     */
    private String orderChannel;
    /**
     * 附加服务列表
     */
//todo    private List<AdditionalService> additionalServiceList;
    /**
     * 面单创建时间
     */
    private Date createTime;


}
