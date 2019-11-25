package com.zavier.project.service.user;

import com.zavier.project.common.exp.CommonException;
import com.zavier.project.common.exp.ExceptionEnum;
import com.zavier.project.manager.bo.UserBO;
import com.zavier.project.manager.event.LogInEvent;
import com.zavier.project.manager.security.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
        // 校验密码
        boolean userPassword = userService.checkUserPassword(userBO);
        if (!userPassword) {
            log.info("password error");
            throw new CommonException(ExceptionEnum.USER_NAME_PASSWORD_ERROR);
        }

        // 生成token
        UserBO userAndRoles = userService.listUserAndRoles(userBO.getUserName());
        String token = generateUserToken(userAndRoles);

        // 发送登录事件
        LogInEvent logInEvent = new LogInEvent(this, userBO);
        applicationEventPublisher.publishEvent(logInEvent);
        return token;
    }

    private String generateUserToken(UserBO userBO) {
        String roles = userBO.getRoles();
        Map<String, Object> map = new HashMap<>();
        map.put("roles", roles);
        return jwtTokenUtil.generateToken(userBO, map);
    }
}
