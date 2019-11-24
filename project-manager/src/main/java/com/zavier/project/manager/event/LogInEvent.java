package com.zavier.project.manager.event;

import com.zavier.project.manager.bo.UserBO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class LogInEvent extends ApplicationEvent {

    @Getter
    private UserBO userBO;

    public LogInEvent(Object source, UserBO userBO) {
        super(source);
        this.userBO = userBO;
    }

}
