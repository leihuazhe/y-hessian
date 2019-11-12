package flurry.test.api;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Denim.leihz 2019-07-09 2:24 PM
 */
@Data
public class HelloEnum implements Serializable {

    private int id;

    private String name;

    private BigEnum bigEnum;


}
