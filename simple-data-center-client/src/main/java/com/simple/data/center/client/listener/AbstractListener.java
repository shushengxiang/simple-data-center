package com.simple.data.center.client.listener;

import com.simple.data.center.client.annotation.DataCenter;
import com.simple.data.center.client.annotation.DataCenterField;
import com.simple.data.center.client.model.DataCenterMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * @Description
 * @Author shushengxiang
 * @Date 2020/4/3 15:57
 **/
@Slf4j
public abstract class AbstractListener implements Listener,BeanFactoryAware,ApplicationContextAware {

    protected BeanFactory beanFactory;

    protected ApplicationContext applicationContext;

    public void refresh(DataCenterMetadata dataCenterMetadata, String data){
        log.info("refresh data {} for this datacenter {}",data,dataCenterMetadata);

        Object bean = beanFactory.getBean(dataCenterMetadata.getClazz());

        Properties properties = new Properties();
        try {
            properties.load(new StringReader(data));
        } catch (IOException e) {
            log.error("",e);
        }


        Field[] declaredFields =
                bean.getClass().getDeclaredFields();

        if (declaredFields == null ||
                declaredFields.length == 0){
            return;
        }

        for (Field field:declaredFields){
            DataCenterField dataCenterField = field.getAnnotation(DataCenterField.class);
            if (dataCenterField == null){
                continue;
            }

            String name = StringUtils.isEmpty(dataCenterField.value()) ?
                    field.getName():dataCenterField.value();
            Object value = properties.get(name);

            field.setAccessible(true);
            try {
                field.set(bean,value);
            } catch (IllegalAccessException e) {
                log.error("",e);
            }
        }
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
