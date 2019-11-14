package org.apache.dubbo.test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Denim.leihz 2019-08-14 2:48 PM
 */
public class TestResponse2 implements Serializable {

    private Map<String, String> countries;


    public TestResponse2(Map<String, String> countries) {
        this.countries = countries;
    }
}
