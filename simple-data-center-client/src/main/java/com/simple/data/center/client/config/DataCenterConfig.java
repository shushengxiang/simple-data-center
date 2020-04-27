package com.simple.data.center.client.config;

import com.simple.data.center.client.listener.AbstractListener;
import com.simple.data.center.client.listener.Listener;
import com.simple.data.center.client.listener.impl.RedisListener;
import com.simple.data.center.client.listener.impl.ZKListener;
import com.simple.data.center.client.properties.ZKProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @Description
 * @Author shushengxiang
 * @Date 2020/4/3 16:24
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties(ZKProperties.class)
public class DataCenterConfig {

    @Bean
    @ConditionalOnProperty(prefix = "simple.datacenter",name = "type")
    public ApplicationListener<ContextRefreshedEvent> dataCenterVoteListener(AbstractListener listener){
        return contextRefreshedEvent -> {
            if(contextRefreshedEvent.getApplicationContext().getParent() == null){
                //root application context 没有parent
                log.info("Start execute the dataCenterVoteListener");
                try {
                    listener.init();
                } catch (Exception e) {
                    log.error("Occur an error in init dataCenterVoteListener",e);
                }
            }
        };
    }

    @Bean
    @ConditionalOnProperty(prefix = "simple.datacenter",name="type",havingValue = "zk")
    public ZKListener zkListener(BeanFactory beanFactory){
        ZKListener zkListener = new ZKListener();
        zkListener.setBeanFactory(beanFactory);
        return zkListener;
    }

    @Bean
    @ConditionalOnProperty(prefix = "simple.datacenter",name="type",havingValue = "redis")
    public StringRedisTemplate redisTemplate(RedisConnectionFactory factory){
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    @Bean
    @ConditionalOnProperty(prefix = "simple.datacenter",name="type",havingValue = "redis")
    public RedisListener redisListener(BeanFactory factory,StringRedisTemplate stringRedisTemplate){
        RedisListener redisListener = new RedisListener();
        redisListener.setRedisTemplate(stringRedisTemplate);
        redisListener.setBeanFactory(factory);
        return redisListener;
    }

}
