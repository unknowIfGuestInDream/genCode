package com.newangels.gen.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 定时任务线程池
 *
 * @author: TangLiang
 * @date: 2021/1/4 8:40
 * @since: 1.0
 */
@Configuration
@EnableScheduling
@ConditionalOnProperty(prefix = "schedule", name = "enabled", havingValue = "true")
public class SchedulConfig {

    @Value("${schedule.poolSize}")//设置核心线程数
    private int poolSize;
    @Value("${schedule.removeOnCancelPolicy}") //设置最大线程数
    private Boolean removeOnCancelPolicy;
    @Value("${schedule.threadNamePrefix}") //设置队列容量
    private String threadNamePrefix;

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        // 定时任务执行线程池核心线程数
        taskScheduler.setPoolSize(poolSize);
        taskScheduler.setErrorHandler(new ScheduErrorHandler());
        taskScheduler.setRemoveOnCancelPolicy(removeOnCancelPolicy);
        taskScheduler.setThreadNamePrefix(threadNamePrefix);
        return taskScheduler;
    }
}