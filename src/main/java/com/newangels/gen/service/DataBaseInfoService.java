package com.newangels.gen.service;

import java.util.List;
import java.util.Map;

/**
 * 数据源管理
 *
 * @author: TangLiang
 * @date: 2021/6/19 13:16
 * @since: 1.0
 */
public interface DataBaseInfoService {

    /**
     * 查询数据源
     */
    List<Map<String, Object>> selectDataBaseInfo();

    /**
     * 加载数据源
     *
     * @param ID ID
     */
    List<Map<String, Object>> loadDataBaseInfo(String ID);

    /**
     * 新增数据源
     *
     * @param NAME     数据源名称
     * @param URL      路径
     * @param DRIVER   驱动名
     * @param USERNAME 用户
     * @param PASSWORD 密码
     */
    int insertDataBaseInfo(String NAME, String URL, String DRIVER, String USERNAME, String PASSWORD);

    /**
     * 修改数据源
     *
     * @param ID       ID
     * @param NAME     数据源名称
     * @param URL      路径
     * @param DRIVER   驱动名
     * @param USERNAME 用户
     * @param PASSWORD 密码
     */
    int updateDataBaseInfo(String ID, String NAME, String URL, String DRIVER, String USERNAME, String PASSWORD);

    /**
     * 删除数据源
     *
     * @param ID ID
     */
    int deleteDataBaseInfo(String ID);
}
