package com.zavier.project.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zavier.project.common.exp.ExceptionEnum;
import com.zavier.project.manager.security.JwtTokenUtil;
import com.zavier.project.web.res.Result;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@Slf4j
@Component
public class AdminInterceptor implements HandlerInterceptor {

    private final JwtTokenUtil jwtTokenUtil;

    private List<String> needLoginUrlPrefix = Lists.newArrayList("/monitor", "/user");

    public AdminInterceptor(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        boolean needLogin = false;
        for (String loginUrlPrefix : needLoginUrlPrefix) {
            if (requestURI.startsWith(loginUrlPrefix)) {
                needLogin = true;
            }
        }
        if (!needLogin) {
            return true;
        }

        String token = jwtTokenUtil.getJwtTokenFromRequest(request);
        try {
            boolean isValid = jwtTokenUtil.validateToken(token);
            if (!isValid) {
                writeErrorMessage(response, ExceptionEnum.TOKEN_INVALID);
                return false;
            }
        } catch (ExpiredJwtException e) {
            writeErrorMessage(response, ExceptionEnum.TOKEN_EXPIRED);
            return false;
        } catch (Exception e) {
            writeErrorMessage(response, ExceptionEnum.TOKEN_INVALID);
            return false;
        }
        return true;
    }

    private void writeErrorMessage(HttpServletResponse response, ExceptionEnum exceptionEnum) throws IOException {
        // 处理中文乱码问题
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        String res = JSON.toJSONString(Result.wrapErrorResult(exceptionEnum));
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(res.getBytes(Charset.forName("utf-8")));
        outputStream.flush();
    }
}
