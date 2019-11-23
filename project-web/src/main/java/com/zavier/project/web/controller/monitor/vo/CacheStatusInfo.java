package com.zavier.project.web.controller.monitor.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CacheStatusInfo {
    private Long loadSuccessCount;

    /**
     * 加载新值所花费的平均时间
     */
    private Double averageLoadPenalty;

    /**
     * 缓存逐出的数量
     */
    private Long evictionCount;

    /**
     * 点击数与请求数的比率
     */
    private Long hitCount;
    private Double hitRate;
    private Long evictionWeight;
    private Long loadCount;
    private Long loadFailureCount;
    private Double loadFailureRate;
    private Long totalLoadTime;

    /**
     * 未命中的数量
     */
    private Long missCount;
    private Double missRate;
    private Long requestCount;

}
