package com.newangels.gen.service.impl;

import com.newangels.gen.enums.DataBaseType;
import com.newangels.gen.exception.UnSupportedDataSourceException;
import com.newangels.gen.factory.DataBaseTableFactory;
import com.newangels.gen.service.DataBaseTableService;
import com.newangels.gen.util.DataSourceUtil;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * mysql表信息
 *
 * @author: TangLiang
 * @date: 2021/7/14 14:01
 * @since: 1.0
 */
@Service
public class MysqlTableServiceImpl implements DataBaseTableService {

    @Override
    public String loadTable(@NonNull String name) {
        throw new UnSupportedDataSourceException("暂时不支持当前数据库表信息查询");
    }

    @Override
    public Map<String, Object> loadTable(@NonNull String name, @NonNull DataSourceUtil dataSourceUtil) {
        throw new UnSupportedDataSourceException("暂时不支持当前数据库表信息查询");
    }

    @Override
    public String selectTables(String name) {
        throw new UnSupportedDataSourceException("暂时不支持当前数据库表信息查询");
    }

    @Override
    public List<Map<String, Object>> selectTables(String name, DataSourceUtil dataSourceUtil) {
        throw new UnSupportedDataSourceException("暂时不支持当前数据库表信息查询");
    }

    @Override
    public String selectTableInfo(@NonNull String name) {
        throw new UnSupportedDataSourceException("暂时不支持当前数据库表信息查询");
    }

    @Override
    public List<Map<String, Object>> selectTableInfo(@NonNull String name, @NonNull DataSourceUtil dataSourceUtil) {
        throw new UnSupportedDataSourceException("暂时不支持当前数据库表信息查询");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DataBaseTableFactory.register(DataBaseType.MYSQL, this);
        DataBaseTableFactory.register(DataBaseType.MYSQL8, this);
    }

}
