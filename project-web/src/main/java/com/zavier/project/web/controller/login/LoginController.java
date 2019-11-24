package com.zavier.project.web.controller.login;

import com.zavier.project.common.util.BeanUtil;
import com.zavier.project.common.util.IpUtil;
import com.zavier.project.common.util.StringUtil;
import com.zavier.project.common.util.ValidatorUtil;
import com.zavier.project.manager.bo.UserBO;
import com.zavier.project.manager.security.JwtTokenUtil;
import com.zavier.project.service.user.SignInService;
import com.zavier.project.service.user.SignUpService;
import com.zavier.project.web.res.Result;
import com.zavier.project.web.vo.user.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("login")
public class LoginController {

    private final SignUpService signUpService;

    private final SignInService signInService;

    private final JwtTokenUtil jwtTokenUtil;

    public LoginController(SignUpService signUpService, SignInService signInService, JwtTokenUtil jwtTokenUtil) {
        this.signUpService = signUpService;
        this.signInService = signInService;
        this.jwtTokenUtil = jwtTokenUtil;
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
    public Result<String> signIn(HttpServletRequest request, @RequestBody UserVO userVO) {
        String errMsg = ValidatorUtil.validate(userVO);
        if (StringUtil.isNotBlank(errMsg)) {
            return Result.wrapErrorResult(errMsg);
        }
        UserBO userBO = BeanUtil.copyProperties(userVO, UserBO.class);
        userBO.setIp(IpUtil.getIpAddr(request));
        String token = signInService.signIn(userBO);
        return Result.wrapSuccessResult(token);
    }

    @GetMapping("isLogin")
    public Result<Boolean> isLogin(HttpServletRequest request) {
        String token = jwtTokenUtil.getJwtTokenFromRequest(request);
        if (StringUtil.isBlank(token)) {
            return Result.wrapSuccessResult(false);
        }
        Boolean tokenExpired = null;
        try {
            tokenExpired = jwtTokenUtil.isTokenExpired(token);
        } catch (Exception e) {
            return Result.wrapSuccessResult(false);
        }
        if (tokenExpired) {
            return Result.wrapSuccessResult(false);
        }
        return Result.wrapSuccessResult(true);
    }
}
