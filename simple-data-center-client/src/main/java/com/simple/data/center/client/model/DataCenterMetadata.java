package com.simple.data.center.client.model;

import lombok.Builder;
import lombok.Data;

/**
 * @Description
 * @Author shushengxiang
 * @Date 2020/4/3 14:49
 **/
@Data
@Builder
public class DataCenterMetadata {

    private String className;

    private String id;

    private Class<?> clazz;


}
