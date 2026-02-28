package com.tlcsdm.gen.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 重写线程池打印线程信息日志
 *
 * @author: 唐 亮
 * @date: 2021/12/18 16:27
 * @since: 1.0
 */
@Slf4j
public class VisibleThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

	private void showThreadPoolInfo(String prefix) {
		ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();
		log.info("{}, {},taskCount [{}], completedTaskCount [{}], activeCount [{}], queueSize [{}]",
				this.getThreadNamePrefix(), prefix, threadPoolExecutor.getTaskCount(),
				threadPoolExecutor.getCompletedTaskCount(), threadPoolExecutor.getActiveCount(),
				threadPoolExecutor.getQueue().size());
	}

	@Override
	public void execute(Runnable task) {
		showThreadPoolInfo("1. do execute");
		super.execute(task);
	}

	@Override
	public Future<?> submit(Runnable task) {
		showThreadPoolInfo("1. do submit");
		return super.submit(task);
	}

	@Override
	public <T> Future<T> submit(Callable<T> task) {
		showThreadPoolInfo("2. do submit");
		return super.submit(task);
	}

	@Override
	public CompletableFuture<Void> submitCompletable(Runnable task) {
		showThreadPoolInfo("1. do submitCompletable");
		return super.submitCompletable(task);
	}

	@Override
	public <T> CompletableFuture<T> submitCompletable(Callable<T> task) {
		showThreadPoolInfo("2. do submitCompletable");
		return super.submitCompletable(task);
	}

}
