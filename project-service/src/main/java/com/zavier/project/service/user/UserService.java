package com.zavier.project.service.user;

import com.zavier.project.common.exp.CommonException;
import com.zavier.project.common.exp.ExceptionEnum;
import com.zavier.project.manager.bo.UserBO;
import com.zavier.project.manager.manager.PasswordManager;
import com.zavier.project.manager.manager.UserManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class UserService {

    private final UserManager userManager;

    private final PasswordManager passwordManager;

    public UserService(UserManager userManager, PasswordManager passwordManager) {
        this.userManager = userManager;
        this.passwordManager = passwordManager;
    }

    public boolean checkUserPassword(UserBO userBO) {
        String userName = userBO.getUserName();
        String encryptPassword = passwordManager.encryptPassword(userBO.getPassword());
        UserBO user = userManager.getUserByUserName(userName);
        if (user == null) {
            log.error("userName:{} does not exist", userBO.getUserName());
            throw new CommonException(ExceptionEnum.USER_NAME_NOT_EXIST);
        }
        log.info("encryptPassword:{}", encryptPassword);
        return Objects.equals(user.getPassword(), encryptPassword);
    }
}
