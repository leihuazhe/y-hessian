package yunji.business.bug;

import com.yunji.frameworks.meta.GenServiceMetadata;
import yunji.business.bug.dto.BillDetailForExpressReqParam;
import yunji.business.bug.dto.OPCommonResp;

/**
 * @author Denim.leihz 2019-09-26 3:42 PM
 */
@GenServiceMetadata
public interface IOpenPlatformExpressGateway {


    OPCommonResp detail(BillDetailForExpressReqParam reqPram);

}
