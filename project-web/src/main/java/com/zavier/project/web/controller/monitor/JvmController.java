package com.zavier.project.web.controller.monitor;

import com.zavier.project.web.controller.monitor.vo.MemoryInfo;
import com.zavier.project.web.controller.monitor.vo.OsInfo;
import com.zavier.project.web.controller.monitor.vo.ThreadStatus;
import com.zavier.project.web.res.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("jvm")
public class JvmController {

    /**
     * JVM OS Info
     * @return
     */
    @GetMapping("osInfo")
    public Result<OsInfo> getOsInfo() {
        OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
        OsInfo osInfo = new OsInfo()
                .setOsName(os.getName())
                .setOsVersion(os.getVersion())
                .setOsAvailableProcessors(os.getAvailableProcessors())
                .setOsArch(os.getArch());
        return Result.wrapSuccessResult(osInfo);
    }

    /**
     * VM Memory Info
     * @return
     */
    @GetMapping("memoryInfo")
    public Result<MemoryInfo> memoryInfo() {
        ManagementFactory.getMemoryMXBean().gc();
        MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        MemoryInfo memoryInfo = new MemoryInfo()
                .setInitMemory(heapMemoryUsage.getInit())
                .setMaxMemory(heapMemoryUsage.getMax())
                .setUsedMemory(heapMemoryUsage.getUsed());
        return Result.wrapSuccessResult(memoryInfo);
    }

    /**
     * JVM Thread Info
     * @return
     */
    @GetMapping("threadInfo")
    public Result<ThreadStatus> threadInfo() {
        ThreadStatus threadStatus = new ThreadStatus();

        ThreadMXBean tm = ManagementFactory.getThreadMXBean();
        tm.setThreadContentionMonitoringEnabled(true);
        threadStatus.setThreadCount(tm.getThreadCount());
        threadStatus.setStartedThreadCount(tm.getTotalStartedThreadCount());

        long[] threadIds = tm.getAllThreadIds();
        ThreadInfo[] threadInfoArray = tm.getThreadInfo(threadIds, Integer.MAX_VALUE);

        long[][] threadArray = new long[threadInfoArray.length][2];
        for (int i = 0; i < threadInfoArray.length; i++) {
            long threadId = threadInfoArray[i].getThreadId();
            long cpuTime = tm.getThreadCpuTime(threadInfoArray[i].getThreadId()) / (1000 * 1000 * 1000);
            threadArray[i][0] = threadId;
            threadArray[i][1] = cpuTime;
        }

        long [] temp = new long[2];
        for (int j = 0; j < threadArray.length - 1; j++) {
            for (int k = j + 1; k < threadArray.length; k++) {
                if (threadArray[j][1] < threadArray[k][1]) {
                    temp = threadArray[j];
                    threadArray[j] = threadArray[k];
                    threadArray[k] = temp;
                }
            }
        }

        List<com.zavier.project.web.controller.monitor.vo.ThreadInfo> threadInfoList = new ArrayList<>(threadArray.length);
        for (int t = 0; t < threadArray.length; t ++) {
            ThreadInfo ti = tm.getThreadInfo(threadArray[t][0], Integer.MAX_VALUE);
            if (ti == null) {
                continue;
            }
            com.zavier.project.web.controller.monitor.vo.ThreadInfo threadInfo = new com.zavier.project.web.controller.monitor.vo.ThreadInfo()
                    .setThreadId(threadArray[t][0])
                    .setThreadName(ti.getThreadName())
                    .setThreadState(ti.getThreadState().name())
                    .setThreadLockName(ti.getLockName())
                    .setThreadLockOwnerName(ti.getLockOwnerName())
                    .setThreadCpuTime(threadArray[t][1])
                    .setStackDepth(ti.getStackTrace().length);
            threadInfoList.add(threadInfo);
        }
        threadStatus.setThreadInfoList(threadInfoList);

        return Result.wrapSuccessResult(threadStatus);
    }
}
