package com.zavier.project.service.user;

import com.zavier.project.manager.bo.UserBO;
import com.zavier.project.manager.manager.UserManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchUserService {

    private final UserManager userManager;

    public SearchUserService(UserManager userManager) {
        this.userManager = userManager;
    }

    public List<UserBO> listAll() {
        List<UserBO> users = userManager.listAllUser();
        return users;
    }
}
