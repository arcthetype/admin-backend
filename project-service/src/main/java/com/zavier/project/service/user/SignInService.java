package com.zavier.project.service.user;

import com.zavier.project.common.exp.CommonException;
import com.zavier.project.common.exp.ExceptionEnum;
import com.zavier.project.manager.bo.UserBO;
import com.zavier.project.manager.manager.PasswordManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SignInService {

    private final PasswordManager passwordManager;

    public SignInService(PasswordManager passwordManager) {
        this.passwordManager = passwordManager;
    }

    public void signIn(UserBO userBO) {
        Subject currentUser = SecurityUtils.getSubject();
        // 判断是否认证
        if (!currentUser.isAuthenticated()) {
            // 要进行登录的 Token
            String encryptPassword = passwordManager.encryptPassword(userBO.getPassword());
            UsernamePasswordToken token = new UsernamePasswordToken(userBO.getUserName(), encryptPassword);
            token.setRememberMe(true);
            try {
                // 登录-使用 Realm 校验要登录的 Token
                currentUser.login(token);
            } catch (UnknownAccountException uae) {
                log.info("Username Not Found!");
                throw new CommonException(ExceptionEnum.USER_NAME_NOT_EXIST);
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
        }
    }
}
