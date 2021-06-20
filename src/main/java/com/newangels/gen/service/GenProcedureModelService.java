package com.newangels.gen.service;

import org.springframework.beans.factory.InitializingBean;

/**
 * 存储过程代码生成模板
 *
 * @author: TangLiang
 * @date: 2021/6/20 10:15
 * @since: 1.0
 */
public interface GenProcedureModelService extends InitializingBean {

    /**
     * 控制层代码生成
     *
     * @param moduleName 模块名
     */
    String getControllerCode(String moduleName);

    /**
     * 控制层代码生成
     *
     * @param moduleName 模块名
     */
    String getServiceCode(String moduleName);

    /**
     * 控制层代码生成
     *
     * @param moduleName 模块名
     */
    String getServiceImplCode(String moduleName);

    /**
     * 控制层代码生成
     *
     * @param moduleName 模块名
     */
    String getRepositoryCode(String moduleName);
}
