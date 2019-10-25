package gateway.test.utils;

import com.yunji.com.caucho.hessian.io.SerializerFactory;
import com.yunji.dubbo.common.serialize.util.Hessian3SerializerFactory;

/**
 * @author Denim.leihz 2019-08-21 11:13 AM
 */
public class Hessian3Utils extends Hessian2Utils {

    public SerializerFactory getSerializerFactory() {
        return Hessian3SerializerFactory.SERIALIZER_FACTORY;
    }
}
