package com.jia.jnmap.config;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.cache.StorageType;
import org.infinispan.manager.DefaultCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * infinispan本地缓存配置（已弃用）
 * 参考：
 *      https://infinispan.org/docs/stable/titles/embedding/embedding.html
 *      https://infinispan.org/docs/stable/titles/configuring/configuring.html#cache_configuration
 *
 * @author xutao@gaffassen.com
 * @version 1.0.0
 * @date 2020-03-16 11:47
 */
public class InfinispanCacheConfig {

    @Autowired
    public DefaultCacheManager cacheManager;


    @Bean
    public DefaultCacheManager getDefaultCacheManager() {
        return new DefaultCacheManager();
    }


    @Bean(name = "jnmapCache")
    public Cache getJnmapCache() {
        // springboot获取项目根目录
        String projectHome = System.getProperty("user.dir");

        // infinispan本地缓存配置
        org.infinispan.configuration.cache.Configuration config = new ConfigurationBuilder()
                .memory().storageType(StorageType.OBJECT).size(1024)
                .persistence().passivation(false)           // 持久化配置
                .addSingleFileStore().shared(false).location(projectHome + "/infinispan")    // 使用本地存储
//                .eviction().size(1024000).strategy(EvictionStrategy.LRU)                // 缓存淘汰机制：最少使用
                .build();

        cacheManager.defineConfiguration("jnmapCache", config);
        return cacheManager.getCache("jnmapCache");
    }
}
