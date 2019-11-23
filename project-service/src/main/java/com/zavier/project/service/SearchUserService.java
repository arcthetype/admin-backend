package com.zavier.project.service;

import com.zavier.project.dal.entity.UserDO;
import com.zavier.project.manager.manager.UserManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchUserService {

    private final UserManager userManager;

    public SearchUserService(UserManager userManager) {
        this.userManager = userManager;
    }

    public List<UserDO> listAll() {
        List<UserDO> users = userManager.listAllUser();
        return users;
    }
}
