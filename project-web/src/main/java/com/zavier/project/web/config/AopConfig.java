package com.zavier.project.web.config;

import com.alibaba.fastjson.JSON;
import com.zavier.project.common.exp.CommonException;
import com.zavier.project.web.res.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.CloseableThreadContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Configuration
public class AopConfig {

    @Pointcut("execution(* com.zavier.project.web.controller..*.*(..))")
    public void exceptionWrapper(){}

    @Around("exceptionWrapper()")
    public Object around(ProceedingJoinPoint p) {
        Object o = null;
        String traceId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        try (final CloseableThreadContext.Instance ctc = CloseableThreadContext.put("traceId", traceId)) {
            o = p.proceed();
        } catch (CommonException e) {
            // 自定义异常自己处理日志问题
            Signature signature = p.getSignature();
            Class returnType = ((MethodSignature) signature).getReturnType();
            if (returnType == Result.class) {
                return Result.wrapErrorResult(e.getMessage());
            }
        } catch (Throwable e) {
            // 非自定义异常统一记录异常日志
            // ** json处理httpServletRequest时会报错 **
            List<Object> argList = Arrays.stream(p.getArgs()).filter(arg -> !(arg instanceof HttpServletRequest))
                    .collect(Collectors.toList());
            log.error("class:{}, method:{}, param:{} error",
                    p.getSignature().getDeclaringTypeName(),
                    p.getSignature().getName(),
                    JSON.toJSONString(argList), e);
            Signature signature =  p.getSignature();
            Class returnType = ((MethodSignature)signature).getReturnType();
            if (returnType == Result.class) {
                return Result.wrapErrorResult("系统繁忙，请稍后再试");
            }
            throw new RuntimeException(e);
        }
        return o;
    }
}