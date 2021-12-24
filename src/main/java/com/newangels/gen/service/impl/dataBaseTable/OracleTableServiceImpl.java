package com.newangels.gen.service.impl.dataBaseTable;

import com.newangels.gen.enums.DataBaseType;
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
 * oracle表信息
 *
 * @author: TangLiang
 * @date: 2021/7/14 9:40
 * @since: 1.0
 */
@Service
public class OracleTableServiceImpl implements DataBaseTableService {
    //https://docs.oracle.com/en/database/oracle/oracle-database/12.2/refrn/ALL_TABLES.html#GUID-6823CD28-0681-468E-950B-966C6F71325D
    //https://docs.oracle.com/en/database/oracle/oracle-database/12.2/refrn/ALL_OBJECTS.html#GUID-AA6DEF8B-F04F-482A-8440-DBCB18F6C976
    @Override
    public String loadTable(@NonNull String name) {
        return "select P.CREATED, P.LAST_DDL_TIME, T.TABLE_NAME, T.TABLESPACE_NAME, T.CLUSTER_NAME, T.PCT_FREE, T.PCT_USED, T.INI_TRANS, T.MAX_TRANS, T.INITIAL_EXTENT, T.NEXT_EXTENT, T.MIN_EXTENTS, T.MAX_EXTENTS, T.PCT_INCREASE, T.FREELISTS, T.FREELIST_GROUPS, T.LOGGING, T.BACKED_UP, T.NUM_ROWS, T.BLOCKS, T.EMPTY_BLOCKS, T.AVG_SPACE, T.CHAIN_CNT, T.AVG_ROW_LEN, T.AVG_SPACE_FREELIST_BLOCKS, T.NUM_FREELIST_BLOCKS, T.DEGREE, T.INSTANCES, T.CACHE, T.TABLE_LOCK, T.SAMPLE_SIZE, T.LAST_ANALYZED, T.PARTITIONED, T.TEMPORARY, T.SECONDARY, T.NESTED, T.BUFFER_POOL, T.FLASH_CACHE, T.CELL_FLASH_CACHE, T.ROW_MOVEMENT, T.USER_STATS, T.DURATION, T.CLUSTER_OWNER, T.DEPENDENCIES, T.COMPRESSION, T.READ_ONLY, T.RESULT_CACHE\n" +
                " FROM USER_OBJECTS P\n" +
                " LEFT JOIN USER_TABLES T\n" +
                " ON T.TABLE_NAME = P.OBJECT_NAME\n" +
                " WHERE P.OBJECT_TYPE = 'TABLE'\n" +
                " AND P.OBJECT_NAME = '" + name.toUpperCase() + "'";
    }

    //TODO 是否能提到接口层？
    @Override
    public Map<String, Object> loadTable(@NonNull String name, @NonNull DataSourceUtil dataSourceUtil) {
        List<Map<String, Object>> list = dataSourceUtil.executeQuery(loadTable(name));
        return list.size() > 0 ? list.get(0) : new HashMap<>();
    }

    @Override
    public String selectTables(String name, String schema) {
        String sql = "SELECT P.TABLE_NAME, P.COMMENTS, T.LAST_DDL_TIME FROM USER_TAB_COMMENTS P LEFT JOIN USER_OBJECTS T ON T.OBJECT_NAME = P.TABLE_NAME WHERE P.TABLE_TYPE = 'TABLE' ";
        if (StringUtils.isNotEmpty(name)) {
            sql += "AND P.TABLE_NAME like '%" + name.toUpperCase() + "%'";
        }
        return sql;
    }

    @Override
    public List<Map<String, Object>> selectTables(String name, String schema, DataSourceUtil dataSourceUtil) {
        return dataSourceUtil.executeQuery(selectTables(name, schema));
    }

    @Override
    public String selectTableInfo(@NonNull String name, String schema) {
        String sql = "SELECT T.COLUMN_NAME, T.DATA_TYPE, T.DATA_LENGTH, T.NULLABLE, T.COLUMN_ID, T.DATA_DEFAULT, T.NUM_DISTINCT, T.NUM_NULLS, T.LAST_ANALYZED, T.AVG_COL_LEN, P.COMMENTS FROM USER_TAB_COLUMNS T LEFT JOIN USER_COL_COMMENTS P ON T.TABLE_NAME = P.TABLE_NAME AND T.COLUMN_NAME = P.COLUMN_NAME ";
        if (StringUtils.isNotEmpty(name)) {
            sql += "WHERE T.TABLE_NAME = '" + name.toUpperCase() + "' ";
        }
        sql += "ORDER BY T.COLUMN_ID";
        return sql;
    }

    @Override
    public List<Map<String, Object>> selectTableInfo(@NonNull String name, String schema, @NonNull DataSourceUtil dataSourceUtil) {
        return dataSourceUtil.executeQuery(selectTableInfo(name, schema));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DataBaseTableFactory.register(DataBaseType.ORACLE, this);
    }

}
