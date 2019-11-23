package com.zavier.project.manager.manager;

import com.zavier.project.dal.entity.UserDO;
import com.zavier.project.dal.mapper.UserMapper;
import com.zavier.project.manager.bo.UserBO;
import com.zavier.project.manager.mapper.UserBOMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

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
}
