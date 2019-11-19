package com.zavier.project.web.controller;

import com.zavier.project.dal.entity.User;
import com.zavier.project.service.SearchUserService;
import com.zavier.project.service.dto.UserDTO;
import com.zavier.project.service.mapper.UserDTOMapper;
import com.zavier.project.web.res.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private final SearchUserService searchUserService;

    public UserController(SearchUserService searchUserService) {
        this.searchUserService = searchUserService;
    }

    @GetMapping("listAll")
    public Result<List<UserDTO>> listAllUsers() {
        List<User> users = searchUserService.listAll();
        List<UserDTO> userDTOS = UserDTOMapper.INSTANCE.areaToAreaDtoList(users);
        return Result.wrapSuccessResult(userDTOS);
    }
}
