package com.zavier.project.service.user;

import com.zavier.project.common.exp.CommonException;
import com.zavier.project.common.exp.ExceptionEnum;
import com.zavier.project.manager.bo.UserBO;
import com.zavier.project.manager.event.LogInEvent;
import com.zavier.project.manager.manager.PasswordManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SignInService {

    private final PasswordManager passwordManager;

    private final ApplicationEventPublisher applicationEventPublisher;

    public SignInService(PasswordManager passwordManager, ApplicationEventPublisher applicationEventPublisher) {
        this.passwordManager = passwordManager;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void signIn(UserBO userBO) {
        Subject currentUser = SecurityUtils.getSubject();
        // 判断是否认证
        if (!currentUser.isAuthenticated()) {
            // 要进行登录的 Token
            String encryptPassword = passwordManager.encryptPassword(userBO.getPassword());
            UsernamePasswordToken token = new UsernamePasswordToken(userBO.getUserName(), encryptPassword);
            try {
                currentUser.login(token);
            } catch (UnknownAccountException uae) {
                log.info("Username Not Found!");
                throw new CommonException(ExceptionEnum.USER_NAME_PASSWORD_ERROR);
            } catch (IncorrectCredentialsException ice) {
                log.info("Invalid Credentials!");
                throw new CommonException(ExceptionEnum.USER_NAME_PASSWORD_ERROR);
            } catch (LockedAccountException lae) {
                log.info("Your Account is Locked!");
                throw new CommonException(ExceptionEnum.USER_ACCOUNT_LOCKED);
            } catch (AuthenticationException ae) {
                log.info("Unexpected Error!");
                throw new CommonException(ExceptionEnum.LOGIN_ERROR);
            }
            LogInEvent logInEvent = new LogInEvent(this, userBO);
            applicationEventPublisher.publishEvent(logInEvent);
        }
    }
}
