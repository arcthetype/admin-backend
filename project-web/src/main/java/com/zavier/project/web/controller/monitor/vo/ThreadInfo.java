package com.zavier.project.web.controller.monitor.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ThreadInfo {
    private Long threadId;
    private String threadName;
    private String threadState;
    private String threadLockName;
    private String threadLockOwnerName;
    private Long threadCpuTime;
    private int stackDepth;
}
