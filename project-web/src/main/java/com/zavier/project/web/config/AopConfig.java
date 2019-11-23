package com.zavier.project.web.config;

import com.alibaba.fastjson.JSON;
import com.zavier.project.web.res.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Aspect
@Configuration
public class AopConfig {

    @Pointcut("execution(* com.zavier.project.web.controller..*.*(..))")
    public void exceptionWrapper(){}

    @Around("exceptionWrapper()")
    public Object around(ProceedingJoinPoint p) {
        Object o = null;
        try {
            o = p.proceed();
        } catch (Throwable e) {
            log.info("class:{}, method:{}, param:{} error",
                    p.getSignature().getDeclaringTypeName(),
                    p.getSignature().getName(),
                    JSON.toJSONString(p.getArgs()), e);
            Signature signature =  p.getSignature();
            Class returnType = ((MethodSignature)signature).getReturnType();
            if (returnType == Result.class) {
                return Result.wrapErrorResult(e.getMessage());
            }
            throw new RuntimeException(e);
        }
        return o;
    }
}