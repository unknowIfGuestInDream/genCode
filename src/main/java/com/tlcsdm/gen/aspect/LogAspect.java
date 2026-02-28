package com.tlcsdm.gen.aspect;

import com.tlcsdm.gen.base.BaseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

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
	@Pointcut("@annotation(com.tlcsdm.common.annotation.Log)")
	public void logPointCut() {
	}

	/**
	 * 拦截异常操作
	 * @param e 异常
	 */
	@AfterThrowing(value = "logPointCut()", throwing = "e")
	public void doAfterThrowing(Exception e) {
		log.error("日志切面拦截到异常", e);
	}

	/**
	 * 环绕处理
	 * @param pjd ProceedingJoinPoint
	 * @return 方法执行结果
	 * @throws Throwable
	 */
	@Around("logPointCut()")
	public Object doAround(ProceedingJoinPoint pjd) throws Throwable {
		Object result;
		try {
			result = pjd.proceed();
		}
		catch (Exception e) {
			return BaseUtils.failed(e.getMessage());
		}
		return result;
	}

}