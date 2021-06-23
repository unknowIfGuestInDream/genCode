package com.newangels.gen.service;

import org.springframework.beans.factory.InitializingBean;

/**
 * 命名规范
 *
 * @author: TangLiang
 * @date: 2021/6/21 10:28
 * @since: 1.0
 */
public interface NameConventService extends InitializingBean {

    /**
     * 根据存储过程名称获取方法前缀名称
     *
     * @param procedureName 存储过程名称
     */
    String getName(String procedureName);

    /**
     * 结果集参数key值
     *
     * @param name 名称
     */
    String getResultName(String name);
}
