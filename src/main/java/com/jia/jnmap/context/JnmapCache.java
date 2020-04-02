package com.jia.jnmap.context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jia.jnmap.entity.VulnMatchRule;
import com.jia.jnmap.mapper.VulnerabilityMapper;
import com.jia.jnmap.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xutao
 * @date 2020-03-18 00:40
 */
@Slf4j
@Component
public class JnmapCache {

    @Resource
    private VulnerabilityMapper vulnerabilityMapper;

    private static EmbeddedCacheManager cacheManager = null;

    private static final ReentrantLock vulnMatchRuleCacheLock = new ReentrantLock();

    private static Cache<String, String> vulnMatchRuleCache = null;

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

    public Cache<String, String> getVulnMatchRuleCache() {
        if (vulnMatchRuleCache != null) return vulnMatchRuleCache;

        vulnMatchRuleCacheLock.lock();
        try {
            if (vulnMatchRuleCache != null) return vulnMatchRuleCache;

            Cache<String, String> temp = getDefaultCacheManager().getCache("vuln_match_rule");
            temp.clear();

            int pageSize = 10000;
            int page = 1;
            while (true) {
                List<VulnMatchRule> matchRuleList = vulnerabilityMapper.scanByPage(pageSize, (page - 1) * pageSize);
                Map<String, String> matchRuleMap = new HashMap<>();

                matchRuleList.forEach(matchRule -> {
                    String id = matchRule.getId();
                    try {
                        String serialized = JacksonUtil.writeValueAsString(matchRule);
                        matchRuleMap.put(id, serialized);
                    } catch (JsonProcessingException e) {
                        log.error("序列化VulnMatchRule异常，id: {}", id, e);
                    }
                });
                temp.putAll(matchRuleMap);

                if (matchRuleList.size() == 0) break;
                page += 1;
            }

            vulnMatchRuleCache = temp;
        } finally {
            vulnMatchRuleCacheLock.unlock();
        }
        return vulnMatchRuleCache;
    }
}
