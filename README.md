# simple-data-center

> 简单动态配置中心，自动更新远程的配置。目前使用zookeeper作为支持，后续接入其它db

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

### 第四步：使用上述model的地址，使用@Autowired自动注入就好，其值会动态根据zk的配置变化


## zk数据存储

- 节点：simple/datacenter/{service} 
- 数据：kv存储
```$xslt

name=xiaozhao
age=10

```