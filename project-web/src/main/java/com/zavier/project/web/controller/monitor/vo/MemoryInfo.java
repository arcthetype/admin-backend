package com.zavier.project.web.controller.monitor.vo;

import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true)
public class MemoryInfo {

    private Long initMemory;
    private Long maxMemory;
    private Long usedMemory;

    public Long getInitMemory() {
        return convertToMb(initMemory);
    }

    public Long getMaxMemory() {
        return convertToMb(maxMemory);
    }

    public Long getUsedMemory() {
        return convertToMb(usedMemory);
    }

    private Long convertToMb(Long num) {
        return num /  1_000_000;
    }
}
