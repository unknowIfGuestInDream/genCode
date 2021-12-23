package com.newangels.gen.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步线程池配置
 *
 * @author: TangLiang
 * @date: 2020/7/10 9:17
 * @since: 1.0
 */
@Configuration
@EnableAsync
@ConditionalOnProperty(prefix = "thread", name = "enabled", havingValue = "true")
public class AsyncConfig implements AsyncConfigurer {

    /**
     * 设置核心线程数
     */
    @Value("${thread.corePoolSize}")
    private int corePoolSize;

    /**
     * 设置最大线程数
     */
    @Value("${thread.maxPoolSize}")
    private int maxPoolSize;

    /**
     * 设置队列容量
     */
    @Value("${thread.queueCapacity}")
    private int queueCapacity;

    /**
     * 设置线程活跃时间（秒）
     */
    @Value("${thread.keepAliveSeconds}")
    private int keepAliveSeconds;

    /**
     * 设置默认线程名称
     */
    @Value("${thread.threadNamePrefix}")
    private String threadNamePrefix;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix(threadNamePrefix);
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }

}
