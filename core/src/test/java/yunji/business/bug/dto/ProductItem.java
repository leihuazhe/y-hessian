package yunji.business.bug.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author haze
 * @date created at 2019-09-07 16:33
 */
@Data
@AllArgsConstructor
public class ProductItem implements Serializable {
    /**
     * 商品名
     */
    private String name;
    /**
     * 数量
     */
    private Integer count;
}
