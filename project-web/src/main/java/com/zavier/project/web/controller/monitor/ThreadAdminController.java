package com.zavier.project.web.controller.monitor;

import com.google.common.collect.Lists;
import com.zavier.project.web.controller.monitor.vo.ThreadPoolStatus;
import com.zavier.project.web.res.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("monitor/thread")
public class ThreadAdminController {

    private final ThreadPoolTaskExecutor coreThreadPoolTaskExecutor;

    private final ThreadPoolTaskExecutor minorThreadPoolTaskExecutor;

    public ThreadAdminController(ThreadPoolTaskExecutor coreThreadPoolTaskExecutor, ThreadPoolTaskExecutor minorThreadPoolTaskExecutor) {
        this.coreThreadPoolTaskExecutor = coreThreadPoolTaskExecutor;
        this.minorThreadPoolTaskExecutor = minorThreadPoolTaskExecutor;
    }

    @GetMapping("threadStatus")
    public Result<List<ThreadPoolStatus>> threadStatus() {
        final ThreadPoolExecutor coreThreadPoolExecutor = coreThreadPoolTaskExecutor.getThreadPoolExecutor();
        ThreadPoolStatus coreThreadPoolStatus = getThreadPoolStatus(coreThreadPoolExecutor);
        coreThreadPoolStatus.setThreadPoolName("coreThreadPoolTaskExecutor");

        ThreadPoolExecutor minorThreadPoolExecutor = minorThreadPoolTaskExecutor.getThreadPoolExecutor();
        ThreadPoolStatus minorThreadPoolStatus = getThreadPoolStatus(minorThreadPoolExecutor);
        minorThreadPoolStatus.setThreadPoolName("minorThreadPoolTaskExecutor");

        return Result.wrapSuccessResult(Lists.newArrayList(coreThreadPoolStatus, minorThreadPoolStatus));
    }

    private ThreadPoolStatus getThreadPoolStatus(ThreadPoolExecutor coreThreadPoolExecutor) {
        return new ThreadPoolStatus()
                .setCorePoolSize(coreThreadPoolExecutor.getCorePoolSize())
                .setKeepAliveTime(coreThreadPoolExecutor.getKeepAliveTime(TimeUnit.SECONDS))
                .setMaximumPoolSize(coreThreadPoolExecutor.getMaximumPoolSize())
                .setPoolSize(coreThreadPoolExecutor.getPoolSize())
                .setLargestPoolSize(coreThreadPoolExecutor.getLargestPoolSize())
                .setActiveCount(coreThreadPoolExecutor.getActiveCount())
                .setTaskCount(coreThreadPoolExecutor.getTaskCount())
                .setCompletedTaskCount(coreThreadPoolExecutor.getCompletedTaskCount())
                .setInQueueSize(coreThreadPoolExecutor.getQueue().size())
                .setRemainingCapacity(coreThreadPoolExecutor.getQueue().remainingCapacity());
    }
}
