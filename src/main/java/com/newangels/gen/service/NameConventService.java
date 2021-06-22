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
     * 根据存储过程名称获取过程类型
     *
     * @param procedureName 存储过程名称
     */
    String getName(String procedureName);

    /**
     * 获取请求协议
     *
     * @param procedureName 存储过程名称
     */
    String getMappingType(String procedureName);

    /**
     * 结果集参数key值
     *
     * @param name 名称
     */
    String getResultName(String name);
}
