package com.website.pluck.platform.common.aspect;

import com.website.pluck.platform.common.exception.RRException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
@Slf4j
@Aspect
@Configuration
public class RedisAspect {
    @Value("${spring.redis.open: false}")
    private boolean open;

    @Around("execution(* com.website.pluck.platform.common.utils.RedisUtils.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        if (this.open) {
            try {
                result = point.proceed();
            } catch (Exception var4) {
                log.error("redis error", var4);
                throw new RRException("Redis服务异常");
            }
        }

        return result;
    }
}
