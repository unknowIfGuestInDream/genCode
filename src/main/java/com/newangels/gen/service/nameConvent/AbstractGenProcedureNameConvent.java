package com.newangels.gen.service.nameConvent;

import java.util.List;

/**
 * 存储过程生成代码命名规范
 *
 * @author: TangLiang
 * @date: 2021/9/11 23:31
 * @since: 1.0
 */
public abstract class AbstractGenProcedureNameConvent {

    /**
     * 根据存储过程名称获取方法前缀名称
     *
     * @param procedureName 存储过程名称
     */
    public abstract String getName(String procedureName);

    /**
     * 结果集参数key值
     *
     * @param name 名称
     */
    public abstract String getResultName(String name);

    /**
     * 存储过程生成方法排序
     *
     * @param procedureNameList 存储过程集合
     */
    public abstract void sortMethod(List<String> procedureNameList);
}
