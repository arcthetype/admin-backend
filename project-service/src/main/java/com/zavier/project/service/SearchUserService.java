package com.zavier.project.service;

import com.zavier.project.dal.entity.User;
import com.zavier.project.manager.local.UserManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchUserService {

    private final UserManager userManager;

    public SearchUserService(UserManager userManager) {
        this.userManager = userManager;
    }

    public List<User> listAll() {
        List<User> users = userManager.listAllUser();
        return users;
    }
}
