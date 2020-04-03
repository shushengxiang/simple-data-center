package com.simple.data.center.client.registry;

import com.simple.data.center.client.annotation.DataCenter;
import com.simple.data.center.client.annotation.EnableDataCenter;
import com.simple.data.center.client.listener.impl.ZKListener;
import com.simple.data.center.client.model.DataCenterMetadata;
import com.simple.utils.ClassScaner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Description
 * @Author shushengxiang
 * @Date 2020/4/3 14:21
 **/
@Slf4j
public class DataCenterRegistry implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry,
                                        BeanNameGenerator importBeanNameGenerator) {
        log.info("Start DataCenterRegistry registerBeanDefinitions");

        String[] basePackages = getBasePackages(importingClassMetadata);
        Set<Class<?>> classes = ClassScaner.scan(basePackages, DataCenter.class);

        if (CollectionUtils.isEmpty(classes)){
            return;
        }

        Set<DataCenterMetadata> dataCenterMetadataSet = new HashSet<>();
        classes.forEach( clazz -> {
            dataCenterMetadataSet.add(
                    DataCenterMetadata.builder().clazz(clazz).id(clazz.getAnnotation(DataCenter.class).value()).build());
        });

        BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(ZKListener.class)
                .addPropertyValue("dataCenterMetadataSet", dataCenterMetadataSet)
                .getBeanDefinition();

        registry.registerBeanDefinition(ZKListener.class.getName(),beanDefinition);
    }

    private String[] getBasePackages(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(EnableDataCenter.class.getCanonicalName() );
        return  (String[]) attributes.get( "basePackages" );
    }
}
