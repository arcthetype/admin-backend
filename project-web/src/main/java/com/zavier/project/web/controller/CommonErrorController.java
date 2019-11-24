package com.zavier.project.web.controller;

import com.alibaba.fastjson.JSON;
import com.zavier.project.web.res.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 404等异常处理
 */
@Slf4j
@RestController
public class CommonErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    private final ErrorAttributes errorAttributes;

    public CommonErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @RequestMapping(ERROR_PATH)
    public Result error(HttpServletRequest request, Model model) {
        Map<String, Object> errorMap = errorAttributes.getErrorAttributes(new ServletWebRequest(request), false);
        log.error("error: {}", JSON.toJSONString(errorMap));
        return Result.wrapErrorResult(errorMap.get("message").toString());
    }
}