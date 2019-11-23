package com.zavier.project.web.controller.monitor;

import com.zavier.project.web.controller.monitor.vo.ThreadPoolStatus;
import com.zavier.project.web.res.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("thread")
public class ThreadAdminController {

    private final ThreadPoolTaskExecutor coreThreadPoolTaskExecutor;

    public ThreadAdminController(ThreadPoolTaskExecutor coreThreadPoolTaskExecutor) {
        this.coreThreadPoolTaskExecutor = coreThreadPoolTaskExecutor;
    }

    @GetMapping("threadStatus")
    public Result<ThreadPoolStatus> threadStatus() {
        final ThreadPoolExecutor coreThreadPoolExecutor = coreThreadPoolTaskExecutor.getThreadPoolExecutor();

        ThreadPoolStatus threadPoolStatus = new ThreadPoolStatus()
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
        return Result.wrapSuccessResult(threadPoolStatus);
    }
}
