package com.yunji.flurry.util;

public interface Constants {

    String STREAMING_SERIALIZATION_CUSTOM = "y_hessian";
    /**
     * 注册中心环境变量
     */
    String REGISTRY_URL_KEY = "dubbo.registry.address";

    /**
     * 网关白名单配置
     */
    String WHITE_SERVICES_KEY = "gateway.white.service.list";

    //默认值
    String DEFAULT_REGISTRY_URL = "127.0.0.1:2181";


    //Metadata type
    String BIGDECIMAL_CLASS_TYPE = "java.math.BigDecimal";

    String TIMESTAMP_CLASS_TYPE = "java.sql.Timestamp";

    String DATE_CLASS_TYPE = "java.util.Date";
}

