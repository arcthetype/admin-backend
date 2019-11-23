package com.zavier.project.web.controller.monitor.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ThreadPoolStatus {
    /**
     * 设置的核心线程数
     */
    private Integer corePoolSize;

    /**
     * 非核心线程空间存活时间
     */
    private Long keepAliveTime;

    /**
     * 设置的最大线程数
     */
    private Integer maximumPoolSize;

    /**
     * 当前任务队列中的任务数量
     */
    private Integer poolSize;

    /**
     * 曾经达到的最大线程数
     */
    private Integer largestPoolSize;

    /**
     * 当前正在执行任务的线程数
     */
    private Integer activeCount;

    /**
     * 当前的任务数（包括正在执行的和队列中等待的）
     */
    private Long taskCount;

    /**
     * 执行完成的任务数
     */
    private Long completedTaskCount;

    /**
     * 任务队列中的任务数量
     */
    private Integer inQueueSize;

    /**
     * 任务队列的当前剩余空间
     */
    private Integer remainingCapacity;
}
