package com.zavier.project.manager.local;

import com.zavier.project.dal.entity.User;
import com.zavier.project.dal.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserManager {
    private final UserMapper userMapper;

    public UserManager(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<User> listAllUser() {
        List<User> users = userMapper.selectList(null);
        return users;
    }
}
