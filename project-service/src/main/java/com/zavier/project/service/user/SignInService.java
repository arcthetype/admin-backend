package com.zavier.project.service.user;

import com.zavier.project.common.exp.CommonException;
import com.zavier.project.common.exp.ExceptionEnum;
import com.zavier.project.manager.bo.UserBO;
import com.zavier.project.manager.event.LogInEvent;
import com.zavier.project.manager.security.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * 登录
 */
@Slf4j
@Service
public class SignInService {

    private final JwtTokenUtil jwtTokenUtil;

    private final UserService userService;

    private final ApplicationEventPublisher applicationEventPublisher;

    public SignInService(JwtTokenUtil jwtTokenUtil, UserService userService, ApplicationEventPublisher applicationEventPublisher) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public String signIn(UserBO userBO) {
        boolean userPassword = userService.checkUserPassword(userBO);
        if (!userPassword) {
            log.info("password error");
            throw new CommonException(ExceptionEnum.USER_NAME_PASSWORD_ERROR);
        }
        String token = jwtTokenUtil.generateToken(userBO);
        LogInEvent logInEvent = new LogInEvent(this, userBO);
        applicationEventPublisher.publishEvent(logInEvent);
        return token;
    }
}
