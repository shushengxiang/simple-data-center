package com.example.model;

import com.simple.data.center.client.annotation.DataCenter;
import com.simple.data.center.client.annotation.DataCenterField;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author shushengxiang
 * @Date 2020/4/3 16:16
 **/
@Slf4j
@Data
@DataCenter("user")
public class User {

    @DataCenterField
    String name;

    @DataCenterField
    String age;

}
