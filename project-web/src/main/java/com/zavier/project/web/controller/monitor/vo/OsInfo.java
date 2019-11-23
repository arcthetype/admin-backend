package com.zavier.project.web.controller.monitor.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OsInfo {

    private String osName;
    private String osVersion;
    private Integer osAvailableProcessors;
    private String osArch;
}
