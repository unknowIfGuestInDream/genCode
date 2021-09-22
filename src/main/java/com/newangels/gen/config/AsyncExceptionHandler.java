package com.newangels.gen.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 异步线程池异常处理
 *
 * @author: TangLiang
 * @date: 2020/8/18 13:43
 * @since: 1.0
 */
@Slf4j
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        log.error("Async method has uncaught exception, params:{}" + Arrays.toString(params));
        log.error("Exception message - " + ex.getMessage());
        log.error("Method name - " + method.getName());
        log.error("Exception :{}", ex);
    }
}

