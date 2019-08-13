package com.chanshiyu.aspect;

import com.chanshiyu.constant.RedisConstatnt;
import com.chanshiyu.enums.ResultEnum;
import com.chanshiyu.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class HttpAspect {

    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public HttpAspect(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Pointcut("execution(public * com.chanshiyu.controller.Seller.Seller*.*(..))" +
    "&& !execution(public * com.chanshiyu.controller.Seller.SellerUserController.*(..))")
    public void verify() {}

    @Before("verify()")
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


        // token
        String token = request.getHeader("Authorization");
        log.info("token={}", token);
        if (StringUtils.isEmpty(token)) {
            log.warn("【登录校验】token 不存在");
            throw new SellException(ResultEnum.USER_NOT_LOGIN);
        }
        String tokenValue = stringRedisTemplate.opsForValue().get(String.format(RedisConstatnt.TOKEN_PREFIX, token));
        if (StringUtils.isEmpty(tokenValue)) {
            log.warn("【登录校验】token 已失效");
            throw new SellException(ResultEnum.TOKEN_NOT_ACTIVE);
        }
    }

    @After("verify()")
    public void doAfter() {
        log.info("doAfter");
    }

    @AfterReturning(returning = "object", pointcut = "verify()")
    public void afterReturning(Object object) {
        log.info("response={}", object);
    }
}
