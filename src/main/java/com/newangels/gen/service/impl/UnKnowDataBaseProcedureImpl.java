package com.newangels.gen.service.impl;

import com.newangels.gen.enums.DataBaseType;
import com.newangels.gen.factory.DataBaseFactory;
import com.newangels.gen.service.DataBaseProcedureService;
import com.newangels.gen.util.DataSourceUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 未知数据源
 *
 * @author: TangLiang
 * @date: 2021/6/19 9:07
 * @since: 1.0
 */
@Service
public class UnKnowDataBaseProcedureImpl implements DataBaseProcedureService {
    @Override
    public String selectProcedures(String name) {
        throw new RuntimeException("未知数据源");
    }

    @Override
    public List<Map<String, Object>> selectProcedures(String name, DataSourceUtil dataSourceUtil) {
        throw new RuntimeException("未知数据源");
    }

    @Override
    public String loadProcedure(String name) {
        throw new RuntimeException("未知数据源");
    }

    @Override
    public String loadProcedure(String name, DataSourceUtil dataSourceUtil) {
        throw new RuntimeException("未知数据源");
    }

    @Override
    public String selectArguments(String owner, String objectName) {
        throw new RuntimeException("未知数据源");
    }

    @Override
    public String getJavaClass(String type) {
        throw new RuntimeException("未知数据源");
    }

    @Override
    public String getRepositoryOutType(String type) {
        throw new RuntimeException("未知数据源");
    }

    @Override
    public String getRepositoryOutTypeCode(String type) {
        throw new RuntimeException("未知数据源");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DataBaseFactory.register(DataBaseType.UNKNOW, this);
    }
}
