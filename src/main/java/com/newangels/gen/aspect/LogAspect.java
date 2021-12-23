package com.newangels.gen.aspect;

import com.newangels.gen.base.BaseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 日志处理
 *
 * @author: TangLiang
 * @date: 2021/4/16 13:47
 * @since: 1.0
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LogAspect {

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.newangels.gen.annotation.Log)")
    public void logPointCut() {
    }

    /**
     * 拦截异常操作
     *
     * @param e 异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(Exception e) {
        log.error("异常信息:{}", e.getMessage());
        e.printStackTrace();
    }

    /**
     * 环绕处理
     *
     * @param pjd ProceedingJoinPoint
     * @return Map<String, Object>
     * @throws Throwable
     */
    @Around("logPointCut()")
    public Map<String, Object> doAround(ProceedingJoinPoint pjd) throws Throwable {
        Map<String, Object> result;
        try {
            result = (Map<String, Object>) pjd.proceed();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return BaseUtils.failed(e.getMessage());
        }
        return result;
    }

}