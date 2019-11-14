package org.apache.dubbo.test;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Denim.leihz 2019-08-14 2:48 PM
 */
public class TestResponse3 implements Serializable {

    public static class Country implements Serializable{
        String code;
        String name;

        public Country(String code, String name) {
            this.code = code;
            this.name = name;
        }
    }

    private List<Country> countries;


    public TestResponse3(List<Country> countries) {
        this.countries = countries;
    }
}
