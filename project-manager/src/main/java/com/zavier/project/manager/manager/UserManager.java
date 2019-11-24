package com.zavier.project.manager.manager;

import com.zavier.project.common.util.CollectionUtil;
import com.zavier.project.dal.entity.UserDO;
import com.zavier.project.dal.mapper.UserMapper;
import com.zavier.project.manager.bo.UserBO;
import com.zavier.project.manager.mapper.UserBOMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class UserManager {
    private final UserMapper userMapper;

    public UserManager(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Cacheable(value = "user", key = "#root.methodName")
    public List<UserBO> listAllUser() {
        List<UserDO> users = userMapper.selectList(null);
        return UserBOMapper.INSTANCE.userToUserBOList(users);
    }

    @Cacheable(value = "user")
    public UserBO getUserByUserName(String userName) {
        List<UserDO> userDOS = userMapper.selectList(userMapper.findByUserNameWrapper(userName));
        if (CollectionUtil.isEmpty(userDOS)) {
            return null;
        }
        return UserBOMapper.INSTANCE.userToUserBO(userDOS.get(0));
    }

}
