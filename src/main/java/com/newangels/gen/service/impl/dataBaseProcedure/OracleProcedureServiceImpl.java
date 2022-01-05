package com.newangels.gen.service.impl.dataBaseProcedure;

import com.newangels.gen.enums.DataBaseType;
import com.newangels.gen.factory.DataBaseProcedureFactory;
import com.newangels.gen.service.DataBaseProcedureService;
import com.newangels.gen.util.dataSource.DataSourceUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * oracle过程信息
 *
 * @author: TangLiang
 * @date: 2021/6/19 8:46
 * @since: 1.0
 */
@Service
public class OracleProcedureServiceImpl implements DataBaseProcedureService {

    //数据库对java类型映射
    private final Map<String, String> map = new ConcurrentHashMap<>(8);
    //存储过程出参对应值
    private final Map<String, String> dataTypeMap = new ConcurrentHashMap<>(8);
    //存储过程出参结果集获取值
    private final Map<String, String> dataTypeOutMap = new ConcurrentHashMap<>(8);

    @Override
    public String selectProcedures(String name) {
        String sql = "SELECT OBJECT_NAME as NAME, LAST_DDL_TIME as LAST_UPDATE_TIME, STATUS FROM USER_OBJECTS WHERE OBJECT_TYPE = 'PROCEDURE' ";
        if (StringUtils.isNotEmpty(name)) {
            sql += " AND OBJECT_NAME like '%" + name.toUpperCase() + "%'";
        }
        sql += "UNION ALL SELECT T.OBJECT_NAME || '.' || PROCNAME AS NAME, T.LAST_DDL_TIME AS LAST_UPDATE_TIME, T.STATUS FROM (\n" +
                "SELECT OBJECT_NAME,LAST_DDL_TIME,STATUS FROM USER_OBJECTS WHERE OBJECT_TYPE = 'PACKAGE') T\n" +
                "LEFT JOIN (SELECT U.PACKAGE_NAME, U.OBJECT_NAME AS PROCNAME FROM USER_ARGUMENTS U\n" +
                "GROUP BY U.OBJECT_NAME, U.PACKAGE_NAME\n" +
                "ORDER BY U.PACKAGE_NAME, U.OBJECT_NAME) P\n" +
                "ON T.OBJECT_NAME = P.PACKAGE_NAME";
        if (StringUtils.isNotEmpty(name)) {
            sql += " WHERE P.PROCNAME LIKE '%" + name.toUpperCase() + "%'";
        }
        return sql;
    }

    @Override
    public List<Map<String, Object>> selectProcedures(String name, DataSourceUtil dataSourceUtil) {
        return dataSourceUtil.executeQuery(selectProcedures(name));
    }

    @Override
    public String loadProcedure(String name) {
        String sql = "SELECT * FROM USER_SOURCE WHERE 1 = 1";
        //处理包下的存储过程
        int start = name.indexOf(".");
        if (start >= 0) {
            name = name.substring(0, start);
            sql += " AND TYPE = 'PACKAGE'";
        } else {
            sql += " AND TYPE = 'PROCEDURE'";
        }
        if (StringUtils.isNotEmpty(name)) {
            sql += " AND NAME = '" + name + "'";
        }
        sql += " ORDER BY LINE";
        return sql;
    }

    @Override
    public String loadProcedure(String name, DataSourceUtil dataSourceUtil) {
        String allProceduresSql = loadProcedure(name);
        //执行sql
        List<Map<String, Object>> list = dataSourceUtil.executeQuery(allProceduresSql);
        //获取结果集
        StringJoiner sj = new StringJoiner("");
        list.forEach(l -> sj.add(l.get("TEXT").toString()));
        return sj.toString();
    }

    @Override
    public String selectArguments(String owner, String objectName) {
        //处理包下的存储过程
        int start = objectName.indexOf(".");
        if (start >= 0) {
            objectName = objectName.substring(start).replaceFirst(".", "");
        }
        return "select ARGUMENT_NAME, DATA_TYPE, IN_OUT from SYS.ALL_ARGUMENTS t where t.OWNER = '" + owner + "' and t.OBJECT_NAME = '" + objectName + "' ORDER BY POSITION";
    }

    @Override
    public String getJavaClass(String type) {
        return map.getOrDefault(type.toUpperCase(), "String");
    }

    @Override
    public String getRepositoryOutType(String type) {
        return dataTypeMap.getOrDefault(type.toUpperCase(), "OracleTypes.VARCHAR");
    }

    @Override
    public String getRepositoryOutTypeCode(String type) {
        return dataTypeOutMap.getOrDefault(type.toUpperCase(), "String");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        map.put("VARCHAR2", "String");
        map.put("CLOB", "String");
        map.put("NUMBER", "Double");
        map.put("BLOB", "InputStream");
        map.put("DATE", "Date");

        dataTypeMap.put("VARCHAR2", "OracleTypes.VARCHAR");
        dataTypeMap.put("REF CURSOR", "OracleTypes.CURSOR");
        dataTypeMap.put("BLOB", "OracleTypes.BLOB");
        dataTypeMap.put("NUMBER", "OracleTypes.NUMERIC");
        dataTypeMap.put("DATE", "OracleTypes.DATE");

        dataTypeOutMap.put("VARCHAR2", "String");
        dataTypeOutMap.put("REF CURSOR", "Object");
        dataTypeOutMap.put("BLOB", "Blob");
        dataTypeOutMap.put("NUMBER", "Double");
        dataTypeOutMap.put("DATE", "Timestamp");
        DataBaseProcedureFactory.register(DataBaseType.ORACLE, this);
    }
}
