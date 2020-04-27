# simple-data-center

> 简单动态配置中心，自动更新远程的配置。目前使用zookeeper/redis作为存储支持，后续接入其它db

## 使用

### 第一步：依赖

```
          <dependency>
               <groupId>com.simple</groupId>
               <artifactId>simple-data-center-client</artifactId>
               <version>1.0-SNAPSHOT</version>
           </dependency>
```

### 第二步：加入@EnableDataCenter注解

```$xslt

    @SpringBootApplication
    @EnableDataCenter("com.example")
    public class ExampleApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(ExampleApplication.class);
        }
    
    }

```

### 第三步：创建model存储数据

```$xslt
   
   @Data
   @DataCenter("user")
   public class User {
   
       @DataCenterField
       String name;
   
       @DataCenterField
       String age;
   
   }
   
```

### 第四步：在yml配置zk或者redis地址

```$xslt

simple:
  datacenter:
    type: redis # zk时需要下面zk的配置，redis时需要spring redis的配置
    zk:
      address: 192.168.3.117:2281

spring:
  redis:
    host: 192.168.1.174
    password: jredis123456
    port: 6384
    database: 11
    lettuce:
      pool:
        min-idle: 0
        max-idle: 8
        max-wait: 1ms
        max-active: 8
      shutdown-timeout: 100ms

```

### 第五步：使用上述model的地址，使用@Autowired自动注入就好，其值会动态根据db的配置变化

## 数据

- 值：

kv存储
```$xslt

name=xiaozhao
age=10

```

## zk数据存储

- 节点：simple/datacenter/{service} 

## redis数据存储

- key: simple:datacenter:service