package com.example;

import com.simple.data.center.client.annotation.EnableDataCenter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description
 * @Author shushengxiang
 * @Date 2020/4/3 16:14
 **/
@SpringBootApplication
@EnableDataCenter("com.example")
public class ExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class);
    }

}
