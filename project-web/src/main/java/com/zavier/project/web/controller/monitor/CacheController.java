package com.zavier.project.web.controller.monitor;

import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.zavier.project.web.controller.monitor.vo.CacheStatusInfo;
import com.zavier.project.web.res.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("cache")
public class CacheController {

    private final CacheManager cacheManager;

    public CacheController(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @GetMapping("statistics")
    public Result<List<CacheStatusInfo>> statistics() {
        Collection<String> cacheNames = cacheManager.getCacheNames();
        List<CacheStatusInfo> cacheStats = new ArrayList<>();
        cacheNames.forEach(cacheName -> {
            Cache cache = cacheManager.getCache(cacheName);
            Object nativeCache = cache.getNativeCache();
            if (nativeCache instanceof com.github.benmanes.caffeine.cache.Cache) {
                com.github.benmanes.caffeine.cache.Cache caffeineCache = (com.github.benmanes.caffeine.cache.Cache) nativeCache;
                CacheStats stats = caffeineCache.stats();

                CacheStatusInfo status = new CacheStatusInfo()
                        .setAverageLoadPenalty(stats.averageLoadPenalty())
                        .setHitCount(stats.hitCount())
                        .setHitRate(stats.hitRate())
                        .setEvictionCount(stats.evictionCount())
                        .setEvictionWeight(stats.evictionWeight())
                        .setLoadFailureCount(stats.loadFailureCount())
                        .setLoadFailureRate(stats.loadFailureRate())
                        .setLoadSuccessCount(stats.loadSuccessCount())
                        .setMissCount(stats.missCount())
                        .setMissRate(stats.missRate())
                        .setLoadCount(stats.loadCount())
                        .setTotalLoadTime(stats.totalLoadTime())
                        .setRequestCount(stats.requestCount());
                cacheStats.add(status);
            }
        });
        return Result.wrapSuccessResult(cacheStats);
    }
}
