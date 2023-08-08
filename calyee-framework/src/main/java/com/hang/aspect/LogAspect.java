package com.hang.aspect;

import com.alibaba.fastjson.JSON;
import com.hang.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName LogAspect
 * @Description TODO
 * @Author QiuLiHang
 * @DATE 2023/8/8 11:23
 * @Version 1.0
 */
@Component
@Aspect
@Slf4j
public class LogAspect {
    // 切点
    @Pointcut("@annotation(com.hang.annotation.SystemLog)")
    public void pt(){

    }
    // 环绕通知
    @Around("pt()")                                    // 被增强的方法对象
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object ret;// 结果就是目标结果执行完之后的返回值
        // 我们需要最终处理 所以是try finally
        try {
            HandleBefore(joinPoint);
            ret = joinPoint.proceed();
            HandleAfter(ret);
        } finally {
            // 结束后换行                    系统换行符号
            log.info("=======End=======" + System.lineSeparator());
        }
        return ret;
    }

    private void HandleBefore(ProceedingJoinPoint joinPoint) {
        // ThreadLocal 获取
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 获取被增强方法上注解对象
        SystemLog systemLog = getSystemLog(joinPoint);
        // 通过AOP
        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName   : {}",systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}",request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(),
                ((MethodSignature) joinPoint.getSignature()).getName());
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSON(joinPoint.getArgs()));

    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        SystemLog systemLog = methodSignature.getMethod().getAnnotation(SystemLog.class);
        return systemLog;
    }

    private void HandleAfter(Object ret) {
        // 打印出参
        log.info("Response       : {}", JSON.toJSON(ret));
    }

}
