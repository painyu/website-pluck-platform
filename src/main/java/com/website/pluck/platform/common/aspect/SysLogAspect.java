package com.website.pluck.platform.common.aspect;

import com.google.gson.Gson;
import com.website.pluck.platform.common.annotation.SysLog;
import com.website.pluck.platform.common.utils.HttpContextUtils;
import com.website.pluck.platform.common.utils.IPUtils;
import com.website.pluck.platform.modules.sys.model.SysUser;
import com.website.pluck.platform.modules.sys.service.SysLogService;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class SysLogAspect {
    @Autowired
    private SysLogService sysLogService;

    @Pointcut("@annotation(com.website.pluck.platform.common.annotation.SysLog)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = point.proceed();
        long time = System.currentTimeMillis() - beginTime;
        this.saveSysLog(point, time);
        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        com.website.pluck.platform.modules.sys.model.SysLog sysLogModel = new com.website.pluck.platform.modules.sys.model.SysLog();
        SysLog syslog = method.getAnnotation(SysLog.class);
        if (syslog != null) {
            sysLogModel.setOperation(syslog.value());
        }

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLogModel.setMethod(className + "." + methodName + "()");
        Object[] args = joinPoint.getArgs();

        try {
            String params = (new Gson()).toJson(args);
            sysLogModel.setParams(params);
        } catch (Exception var13) {
        }

        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        sysLogModel.setIp(IPUtils.getIpAddr(request));
        String username = ((SysUser) SecurityUtils.getSubject().getPrincipal()).getUsername();
        sysLogModel.setUsername(username);
        sysLogModel.setTime(time);
        sysLogModel.setCreateDate(new Date());
        this.sysLogService.save(sysLogModel);
    }
}
