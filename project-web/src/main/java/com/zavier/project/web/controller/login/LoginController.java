package com.zavier.project.web.controller.login;

import com.zavier.project.common.exp.ExceptionEnum;
import com.zavier.project.common.util.BeanUtil;
import com.zavier.project.common.util.IpUtil;
import com.zavier.project.common.util.StringUtil;
import com.zavier.project.common.util.ValidatorUtil;
import com.zavier.project.manager.bo.UserBO;
import com.zavier.project.service.user.SearchUserService;
import com.zavier.project.service.user.SignInService;
import com.zavier.project.service.user.SignUpService;
import com.zavier.project.web.res.Result;
import com.zavier.project.web.vo.user.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("login")
public class LoginController {
    private final SearchUserService searchUserService;

    private final SignUpService signUpService;

    private final SignInService signInService;

    public LoginController(SearchUserService searchUserService, SignUpService signUpService, SignInService signInService) {
        this.searchUserService = searchUserService;
        this.signUpService = signUpService;
        this.signInService = signInService;
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
    public Result signIn(HttpServletRequest request, @RequestBody UserVO userVO) {

        String errMsg = ValidatorUtil.validate(userVO);
        if (StringUtil.isNotBlank(errMsg)) {
            return Result.wrapErrorResult(errMsg);
        }
        UserBO userBO = BeanUtil.copyProperties(userVO, UserBO.class);
        userBO.setIp(IpUtil.getIpAddr(request));
        signInService.signIn(userBO);
        return Result.wrapSuccessResult(true);
    }

    @GetMapping("unauthorizedUrl")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result unauthorized() {
        return Result.wrapErrorResult(ExceptionEnum.UNAUTHORIZED);
    }

    @GetMapping("needLogin")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result needLogin() {
        return Result.wrapErrorResult(ExceptionEnum.UNAUTHORIZED);
    }
}
