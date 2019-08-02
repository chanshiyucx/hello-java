package com.chanshiyu.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class HttpAspect {

    @Pointcut("execution(public * com.chanshiyu.controller.Buyer.BuyerProductController.*(..))")
    public void log() {}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // url
        log.info("url={}", request.getRequestURL());

        // method
        log.info("method={}", request.getMethod());

        // ip
        log.info("ip={}", request.getRemoteAddr());

        // 类方法
        log.info("class_path={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

        // 参数
        log.info("args={}", joinPoint.getArgs());
    }

    @After("log()")
    public void doAfter() {
        log.info("doAfter");
    }

    @AfterReturning(returning = "object", pointcut = "log()")
    public void afterReturning(Object object) {
        log.info("response={}", object);
    }
}
