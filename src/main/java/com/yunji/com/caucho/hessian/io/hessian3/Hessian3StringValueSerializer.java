package com.yunji.com.caucho.hessian.io.hessian3;

import com.yunji.com.caucho.hessian.io.AbstractHessianOutput;
import com.yunji.com.caucho.hessian.io.StringValueSerializer;

import java.io.IOException;

/**
 * @author Denim.leihz 2019-10-24 4:17 PM
 */
public class Hessian3StringValueSerializer extends StringValueSerializer {

    @Override
    public void writeObject(Object obj, AbstractHessianOutput out)
            throws IOException {
        if (obj == null)
            out.writeNull();
        else {
            /*if (out.addRef(obj))
                return;
             */

            Class cl = obj.getClass();

            int ref = out.writeObjectBegin(cl.getName());

            if (ref < -1) {
                out.writeString("value");
                out.writeString(obj.toString());
                out.writeMapEnd();
            } else {
                if (ref == -1) {
                    out.writeInt(1);
                    out.writeString("value");
                    out.writeObjectBegin(cl.getName());
                }

                out.writeString(obj.toString());
            }
        }
    }
}