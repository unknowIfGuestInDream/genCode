package com.newangels.gen.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ErrorHandler;

/**
 * 定时任务线程池错误处理
 *
 * @author: TangLiang
 * @date: 2021/1/4 8:42
 * @since: 1.0
 */
@Slf4j
public class ScheduErrorHandler implements ErrorHandler {
    @Override
    public void handleError(Throwable throwable) {
        log.error("Unexpected error occurred in scheduled task" + throwable);
    }
}
