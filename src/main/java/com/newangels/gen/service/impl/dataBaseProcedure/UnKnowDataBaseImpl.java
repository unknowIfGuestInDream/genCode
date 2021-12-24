package com.newangels.gen.service.impl.dataBaseProcedure;

import com.newangels.gen.enums.DataBaseType;
import com.newangels.gen.exception.UnSupportedDataSourceException;
import com.newangels.gen.factory.DataBaseProcedureFactory;
import com.newangels.gen.factory.DataBaseTableFactory;
import com.newangels.gen.service.DataBaseProcedureService;
import com.newangels.gen.service.DataBaseTableService;
import com.newangels.gen.util.dataSource.DataSourceUtil;
import lombok.NonNull;
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
public class UnKnowDataBaseImpl implements DataBaseProcedureService, DataBaseTableService {

    @Override
    public String selectProcedures(String name) {
        throw new UnSupportedDataSourceException("不支持当前数据库代码生成");
    }

    @Override
    public List<Map<String, Object>> selectProcedures(String name, DataSourceUtil dataSourceUtil) {
        throw new UnSupportedDataSourceException("不支持当前数据库代码生成");
    }

    @Override
    public String loadProcedure(String name) {
        throw new UnSupportedDataSourceException("不支持当前数据库代码生成");
    }

    @Override
    public String loadProcedure(String name, DataSourceUtil dataSourceUtil) {
        throw new UnSupportedDataSourceException("不支持当前数据库代码生成");
    }

    @Override
    public String selectArguments(String owner, String objectName) {
        throw new UnSupportedDataSourceException("不支持当前数据库代码生成");
    }

    @Override
    public String getJavaClass(String type) {
        throw new UnSupportedDataSourceException("不支持当前数据库代码生成");
    }

    @Override
    public String getRepositoryOutType(String type) {
        throw new UnSupportedDataSourceException("不支持当前数据库代码生成");
    }

    @Override
    public String getRepositoryOutTypeCode(String type) {
        throw new UnSupportedDataSourceException("不支持当前数据库代码生成");
    }

    @Override
    public String loadTable(@NonNull String name) {
        throw new UnSupportedDataSourceException("暂时不支持当前数据库表信息查询");
    }

    @Override
    public Map<String, Object> loadTable(@NonNull String name, @NonNull DataSourceUtil dataSourceUtil) {
        throw new UnSupportedDataSourceException("暂时不支持当前数据库表信息查询");
    }

    @Override
    public String selectTables(String name, String schema) {
        throw new UnSupportedDataSourceException("暂时不支持当前数据库表信息查询");
    }

    @Override
    public List<Map<String, Object>> selectTables(String name, String schema, DataSourceUtil dataSourceUtil) {
        throw new UnSupportedDataSourceException("暂时不支持当前数据库表信息查询");
    }

    @Override
    public String selectTableInfo(@NonNull String name, String schema) {
        throw new UnSupportedDataSourceException("暂时不支持当前数据库表信息查询");
    }

    @Override
    public List<Map<String, Object>> selectTableInfo(@NonNull String name, String schema, @NonNull DataSourceUtil dataSourceUtil) {
        throw new UnSupportedDataSourceException("暂时不支持当前数据库表信息查询");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DataBaseProcedureFactory.register(DataBaseType.UNKNOW, this);
        DataBaseTableFactory.register(DataBaseType.UNKNOW, this);
    }
}
