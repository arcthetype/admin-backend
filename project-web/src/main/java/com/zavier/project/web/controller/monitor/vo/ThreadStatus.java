package com.zavier.project.web.controller.monitor.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ThreadStatus {

    private Integer threadCount;

    private Long startedThreadCount;

    private List<ThreadInfo> threadInfoList;
}
