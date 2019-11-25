package com.zavier.project.web.controller.user;

import com.zavier.project.common.exp.ExceptionEnum;
import com.zavier.project.common.util.StringUtil;
import com.zavier.project.manager.bo.MenuBO;
import com.zavier.project.manager.security.JwtTokenUtil;
import com.zavier.project.service.user.UserService;
import com.zavier.project.web.res.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    private final JwtTokenUtil jwtTokenUtil;

    private final UserService userService;

    public UserController(JwtTokenUtil jwtTokenUtil, UserService userService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @GetMapping("listMenus")
    public Result<List<MenuBO>> listUserMenus(HttpServletRequest request) {
        String token = jwtTokenUtil.getJwtTokenFromRequest(request);
        if (StringUtil.isBlank(token)) {
            return Result.wrapErrorResult(ExceptionEnum.TOKEN_NOT_EXIST);
        }
        String userName;
        try {
            userName = jwtTokenUtil.getUsernameFromToken(token);
        } catch (Exception e) {
            return Result.wrapErrorResult(ExceptionEnum.TOKEN_INVALID);
        }
        List<MenuBO> menuBOList = userService.listFirstLevelWithChildrenMenus(userName);
        return Result.wrapSuccessResult(menuBOList);
    }
}
