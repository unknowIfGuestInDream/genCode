package com.newangels.gen.service.impl.dataBaseTable;

import com.newangels.gen.enums.DataBaseType;
import com.newangels.gen.exception.UnSupportedDataSourceException;
import com.newangels.gen.factory.DataBaseTableFactory;
import com.newangels.gen.service.DataBaseTableService;
import com.newangels.gen.util.dataSource.DataSourceUtil;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
        throw new UnSupportedDataSourceException("暂时不支持mysql数据库表信息查询");
    }

    @Override
    public Map<String, Object> loadTable(@NonNull String name, @NonNull DataSourceUtil dataSourceUtil) {
        List<Map<String, Object>> list = dataSourceUtil.executeQuery(loadTable(name));
        return list.size() > 0 ? list.get(0) : new HashMap<>();
    }

    @Override
    public String selectTables(String name, String schema) {
        String sql = "select * from information_schema.tables where table_schema = '" + schema + "' ";
        if (StringUtils.isNotEmpty(name)) {
            sql += "and table_Name like '%" + name + "%'";
        }
        return sql;
    }

    @Override
    public List<Map<String, Object>> selectTables(String name, String schema, DataSourceUtil dataSourceUtil) {
        return dataSourceUtil.executeQuery(selectTables(name, schema));
    }

    //show full columns from daily_person;
    @Override
    public String selectTableInfo(@NonNull String name) {
        throw new UnSupportedDataSourceException("暂时不支持mysql数据库表信息查询");
    }

    @Override
    public List<Map<String, Object>> selectTableInfo(@NonNull String name, @NonNull DataSourceUtil dataSourceUtil) {
        return dataSourceUtil.executeQuery(selectTableInfo(name));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DataBaseTableFactory.register(DataBaseType.MYSQL, this);
        DataBaseTableFactory.register(DataBaseType.MYSQL8, this);
    }

}
