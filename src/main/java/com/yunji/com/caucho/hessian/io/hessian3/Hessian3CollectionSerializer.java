package com.yunji.com.caucho.hessian.io.hessian3;

import com.yunji.com.caucho.hessian.io.AbstractHessianOutput;
import com.yunji.com.caucho.hessian.io.CollectionSerializer;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Denim.leihz 2019-10-24 4:03 PM
 */
public class Hessian3CollectionSerializer extends CollectionSerializer {

    @Override
    public void writeObject(Object obj, AbstractHessianOutput out)
            throws IOException {
        /*if (out.addRef(obj))
            return;*/

        Collection list = (Collection) obj;

        Class cl = obj.getClass();
        boolean hasEnd = out.writeListBegin(list.size(), obj.getClass().getName());

        Iterator iter = list.iterator();
        while (iter.hasNext()) {
            Object value = iter.next();

            out.writeObject(value);
        }

        if (hasEnd)
            out.writeListEnd();
    }
}