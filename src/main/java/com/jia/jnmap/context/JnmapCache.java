package com.jia.jnmap.context;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

import java.io.IOException;

/**
 * @author xutao
 * @date 2020-03-18 00:40
 */
public class JnmapCache {

    private static EmbeddedCacheManager cacheManager = null;

    public static EmbeddedCacheManager getDefaultCacheManager() {
        if (cacheManager != null) return cacheManager;
        synchronized (JnmapCache.class) {
            if (cacheManager != null) return cacheManager;

            try {
                cacheManager = new DefaultCacheManager("infinispan.xml");
            } catch (IOException e) {
                throw new RuntimeException("infinispan缓存管理器初始化失败", e);
            }
        }
        return cacheManager;
    }

    public static Cache getTestCache() {

        return getDefaultCacheManager().getCache("test");
    }
}
