package com.simple.data.center.client.listener.impl;

import com.simple.data.center.client.listener.AbstractListener;
import com.simple.data.center.client.listener.Listener;
import com.simple.data.center.client.model.DataCenterMetadata;
import com.simple.data.center.client.properties.DataCenterProperties;
import com.simple.data.center.client.properties.ZKProperties;
import com.simple.data.center.client.registry.DataCenterRegistry;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * @Description
 * @Author shushengxiang
 * @Date 2020/4/3 14:50
 **/
@Slf4j
public class ZKListener extends AbstractListener implements Listener {

    @Autowired
    private ZKProperties zkProperties;

    @Override
    public void listen(DataCenterMetadata dataCenterMetadata) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client =
                CuratorFrameworkFactory.builder()
                        .connectString(zkProperties.getAddress())
                        .sessionTimeoutMs(5000)
                        .connectionTimeoutMs(5000)
                        .retryPolicy(retryPolicy)
                        .build();

        client.start();

        String path = new StringBuilder("/simple/datacenter/")
                .append(dataCenterMetadata.getId()).toString();

        try {
            byte[] bytes = client.getData().forPath(path);
            refresh(dataCenterMetadata, new String(bytes, "utf-8"));
        } catch (Exception e) {
            log.error("Occur an exception in getting data in zk", e);
            throw new RuntimeException(e.getMessage());
        }

        NodeCache watcher = new NodeCache(client, path);

        watcher.getListenable().addListener(() -> {
            String data = new String(watcher.getCurrentData().getData(), "utf-8");
            refresh(dataCenterMetadata, data);
        });

        try {
            watcher.start(true);
        } catch (Exception e) {
            log.error("Occur an exception in getting data in zk", e);
            throw new RuntimeException(e.getMessage());
        }

    }

}