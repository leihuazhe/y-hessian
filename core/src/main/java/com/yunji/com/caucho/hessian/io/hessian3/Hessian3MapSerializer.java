package com.yunji.com.caucho.hessian.io.hessian3;

import com.yunji.com.caucho.hessian.io.AbstractHessianOutput;
import com.yunji.com.caucho.hessian.io.MapSerializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Denim.leihz 2019-10-24 2:58 PM
 */
public class Hessian3MapSerializer extends MapSerializer {

    @Override
    public void writeObject(Object obj, AbstractHessianOutput out)
            throws IOException {
        /*if (out.addRef(obj))
            return;
         */

        Map map = (Map) obj;

        Class cl = obj.getClass();

        if (cl.equals(HashMap.class)
                || !_isSendJavaType
                || !(obj instanceof java.io.Serializable))
            out.writeMapBegin(null);
        else
            out.writeMapBegin(obj.getClass().getName());

        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();

            out.writeObject(entry.getKey());
            out.writeObject(entry.getValue());
        }
        out.writeMapEnd();
    }
}
