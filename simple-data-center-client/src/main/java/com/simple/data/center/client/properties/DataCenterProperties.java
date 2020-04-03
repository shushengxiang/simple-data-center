package com.simple.data.center.client.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description
 * @Author shushengxiang
 * @Date 2020/4/3 15:37
 **/
@Data
@ConfigurationProperties(prefix = "simple.datacenter")
public class DataCenterProperties {

    private String zkAddress;

}
