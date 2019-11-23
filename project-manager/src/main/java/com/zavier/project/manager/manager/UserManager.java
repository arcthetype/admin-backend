package com.zavier.project.manager.manager;

import com.zavier.project.dal.entity.UserDO;
import com.zavier.project.dal.mapper.UserMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserManager {
    private final UserMapper userMapper;

    public UserManager(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Cacheable(value = "user")
    public List<UserDO> listAllUser() {
        List<UserDO> users = userMapper.selectList(null);
        return users;
    }
}
