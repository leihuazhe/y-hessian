
## 工程目录

### core
    
    - Y-hessian-lite 核心序列化/反序列化实现工程

### lite
    - Dubbo服务依赖此模块,可以同时支持 Hessian2 原生协议和流式协议

### flurry
    - 网关需要的流式化协议模块
    

Maven 模块:

- y-hessian-parent: Parent工程,管理依赖

- y-hessian-core: Hessian序列化核心包
 
- y-hessian-lite: Dubbo Provider 依赖以支持 流式序列化模式(兼容原生hessian2协议)

- y-hessian-flurry: Flurry 网关依赖的组件,提供元数据,流式序列化等功能.




## Maven dependency

> Yunji 自定义 hessian 流式化协议 => y-hessian-lite
```xml
<dependency>
    <groupId>com.yunji.flurry</groupId>
    <artifactId>y-hessian-lite</artifactId>
    <version>3.0.0-SNAPSHOT</version>
</dependency>
```

