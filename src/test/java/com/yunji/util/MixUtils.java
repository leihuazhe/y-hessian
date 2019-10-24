package com.yunji.util;

import com.google.common.base.Joiner;
import org.apache.commons.io.IOUtils;
import org.apache.dubbo.common.utils.ClassUtils;

import java.io.IOException;

/**
 * @author Denim.leihz 2019-10-24 2:12 PM
 */
public class MixUtils {

    public static String readResource(String name) {
        try {
            return Joiner.on("\n").join(IOUtils.readLines(
                    ClassUtils.getClassLoader().getResourceAsStream(name)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
