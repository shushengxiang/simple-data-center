package com.simple.data.center.client.listener.impl;

import com.simple.data.center.client.listener.AbstractListener;
import com.simple.data.center.client.listener.Listener;
import com.simple.data.center.client.model.DataCenterMetadata;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.*;

@Slf4j
public class RedisListener extends AbstractListener implements Listener {

    @Getter
    @Setter
    private StringRedisTemplate redisTemplate;

    @Override
    public void listen(DataCenterMetadata dataCenterMetadata) {
        String cacheKey = new StringBuilder("simple:datacenter:")
                .append(dataCenterMetadata.getId()).toString();
        String data = redisTemplate.opsForValue().get(cacheKey);
        refresh(dataCenterMetadata,data);

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            String data1 = redisTemplate.opsForValue().get(cacheKey);
            refresh(dataCenterMetadata, data1);
        }, 1, 1, TimeUnit.MINUTES);

    }
}
