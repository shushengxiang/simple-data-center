package com.simple.data.center.client.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "simple.datacenter.zk")
public class ZKProperties {

    private String address;

}
