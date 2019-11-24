package com.zavier.project.manager.event.listener;

import com.zavier.project.common.constant.AdminConstants;
import com.zavier.project.dal.entity.LogInLogDO;
import com.zavier.project.dal.mapper.LogInLogMapper;
import com.zavier.project.manager.bo.UserBO;
import com.zavier.project.manager.event.LogInEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class UserEventListener {

    private final LogInLogMapper logInLogMapper;

    public UserEventListener(LogInLogMapper logInLogMapper) {
        this.logInLogMapper = logInLogMapper;
    }

    @EventListener
    public void onLogInEvent(LogInEvent logInEvent) {
        UserBO loginUser = logInEvent.getUserBO();
        LogInLogDO logInLogDO = new LogInLogDO()
                .setCreator(AdminConstants.SYSTEM_UID)
                .setModifier(AdminConstants.SYSTEM_UID)
                .setGmtCreate(new Date())
                .setGmtModified(new Date())
                .setLogInUserName(loginUser.getUserName())
                .setLogInTime(new Date(logInEvent.getTimestamp()))
                .setLogInIp(loginUser.getIp());
        logInLogMapper.insert(logInLogDO);
    }
}
