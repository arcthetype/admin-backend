package com.zavier.project.web.controller.user;

import com.zavier.project.common.util.BeanUtil;
import com.zavier.project.common.util.StringUtil;
import com.zavier.project.common.util.ValidatorUtil;
import com.zavier.project.manager.bo.UserBO;
import com.zavier.project.service.user.SearchUserService;
import com.zavier.project.service.user.SignInService;
import com.zavier.project.service.user.SignUpService;
import com.zavier.project.web.res.Result;
import com.zavier.project.web.vo.user.UserVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
public class UserController {
    private final SearchUserService searchUserService;

    private final SignUpService signUpService;

    private final SignInService signInService;

    public UserController(SearchUserService searchUserService, SignUpService signUpService, SignInService signInService) {
        this.searchUserService = searchUserService;
        this.signUpService = signUpService;
        this.signInService = signInService;
    }

    @GetMapping("listAll")
    public Result<List<UserVO>> listAllUsers() {
        List<UserBO> users = searchUserService.listAll();
        List<UserVO> collect = users.stream().map(u -> {
            UserVO userVO = new UserVO();
            userVO.setUserName(u.getUserName());
            return userVO;
        }).collect(Collectors.toList());
        return Result.wrapSuccessResult(collect);
    }

    @PostMapping("signUp")
    public Result signUp(@RequestBody UserVO userVO) {
        String errMsg = ValidatorUtil.validate(userVO);
        if (StringUtil.isNotBlank(errMsg)) {
            return Result.wrapErrorResult(errMsg);
        }
        boolean userNameExisted = signUpService.userNameExisted(userVO.getUserName());
        if (userNameExisted) {
            return Result.wrapErrorResult("用户名称已存在");
        }

        signUpService.singUp(BeanUtil.copyProperties(userVO, UserBO.class));
        return Result.wrapSuccessResult(true);
    }

    @PostMapping("signIn")
    public Result signIn(@RequestBody UserVO userVO) {
        String errMsg = ValidatorUtil.validate(userVO);
        if (StringUtil.isNotBlank(errMsg)) {
            return Result.wrapErrorResult(errMsg);
        }
        signInService.signIn(BeanUtil.copyProperties(userVO, UserBO.class));
        return Result.wrapSuccessResult(true);
    }
}
