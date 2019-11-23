package com.zavier.project.service.user;

import com.zavier.project.common.constant.AdminConstants;
import com.zavier.project.common.util.CollectionUtil;
import com.zavier.project.dal.entity.UserDO;
import com.zavier.project.dal.mapper.UserMapper;
import com.zavier.project.manager.bo.UserBO;
import com.zavier.project.manager.manager.PasswordManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SignUpService {

    private final PasswordManager passwordManager;

    private final UserMapper userMapper;

    public SignUpService(PasswordManager passwordManager, UserMapper userMapper) {
        this.passwordManager = passwordManager;
        this.userMapper = userMapper;
    }

    /**
     * 注册
     *
     * @param userBO
     */
    public void singUp(UserBO userBO) {
        String encryptPassword = passwordManager.encryptPassword(userBO.getPassword());
        UserDO userDO = new UserDO()
                .setGmtCreate(new Date())
                .setGmtModified(new Date())
                .setCreator(AdminConstants.SYSTEM_UID)
                .setModifier(AdminConstants.SYSTEM_UID)
                .setUserName(userBO.getUserName())
                .setPassword(encryptPassword);
        userMapper.insert(userDO);
    }

    /**
     * 用户名称是否已存在
     *
     * @param username
     * @return
     */
    public boolean userNameExisted(String username) {
        List<UserDO> userDOS = userMapper.selectList(userMapper.findByUserNameWrapper(username));
        return CollectionUtil.isNotEmpty(userDOS);
    }
}
