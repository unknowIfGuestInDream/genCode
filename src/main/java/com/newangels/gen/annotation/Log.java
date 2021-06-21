package com.newangels.gen.annotation;

import java.lang.annotation.*;

/**
 * 日志注解
 *
 * @author: TangLiang
 * @date: 2021/4/16 13:44
 * @since: 1.0
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块
     */
    String title() default "";

    /**
     * 操作类型
     */
    String operateType() default "";
}
