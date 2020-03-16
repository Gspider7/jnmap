package com.jia.jnmap.config;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.eviction.EvictionStrategy;
import org.infinispan.manager.DefaultCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * infinispan缓存配置
 * 参考：https://infinispan.org/documentation/
 *
 * @author xutao@gaffassen.com
 * @version 1.0.0
 * @date 2020-03-16 11:47
 */
@Configuration
public class InfinispanCacheConfig {

    @Autowired
    public DefaultCacheManager cacheManager;


    @Bean
    public DefaultCacheManager getDefaultCacheManager() {
        return new DefaultCacheManager();
    }


    @Bean(name = "jnmapCache")
    public Cache getJnmapCache() {
        org.infinispan.configuration.cache.Configuration config = new ConfigurationBuilder()
                .memory().size(1024)
                .persistence().passivation(false).addSingleFileStore().location("")
                .eviction().strategy(EvictionStrategy.LRU)
                .build();

        cacheManager.defineConfiguration("jnmapCache", config);
        return cacheManager.getCache("jnmapCache");
    }
}
