package com.newangels.gen.service.impl;

import com.newangels.gen.enums.DataBaseType;
import com.newangels.gen.factory.DataBaseFactory;
import com.newangels.gen.service.DataBaseProcedureService;
import com.newangels.gen.util.DataSourceUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * mysql过程信息
 *
 * @author: TangLiang
 * @date: 2021/6/19 8:55
 * @since: 1.0
 */
@Service
public class MysqlProcedureServiceImpl implements DataBaseProcedureService {

    //数据库对java类型映射
    Map<String, String> map = new ConcurrentHashMap<>(32);
    //存储过程出参对应值
    Map<String, String> dataTypeMap = new ConcurrentHashMap<>(8);
    //存储过程出参结果集获取值
    Map<String, String> dataTypeOutMap = new ConcurrentHashMap<>(8);

    @Override
    public String selectProcedures(String name) {
        return "select name as NAME, modified as LAST_UPDATE_TIME from mysql.proc where type = 'PROCEDURE' and db <> 'sys'";
    }

    @Override
    public List<Map<String, Object>> selectProcedures(String name, DataSourceUtil dataSourceUtil) {
        return dataSourceUtil.executeQuery(selectProcedures(name));
    }

    @Override
    public String loadProcedure(String name) {
        return "show create procedure " + name;
    }

    @Override
    public String loadProcedure(String name, DataSourceUtil dataSourceUtil) {
        String allProceduresSql = loadProcedure(name);
        //执行sql
        List<Map<String, Object>> list = dataSourceUtil.executeQuery(allProceduresSql);
        //获取结果集
        return list.size() > 0 ? list.get(0).get("Create Procedure").toString() : "";
    }

    @Override
    public String selectArguments(String owner, String objectName) {
        return "select PARAMETER_NAME as ARGUMENT_NAME, DATA_TYPE, PARAMETER_MODE as IN_OUT from information_schema.PARAMETERS t where t.ROUTINE_TYPE = 'PROCEDURE' and t.SPECIFIC_NAME = '" + objectName + "' ORDER BY ORDINAL_POSITION";
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
        map.put("VARCHAR", "String");
        map.put("INT", "int");
        map.put("BIGINT", "int");
        map.put("TINYINT", "int");
        map.put("TIMESTAMP", "Date");

        dataTypeMap.put("VARCHAR2", "OracleTypes.VARCHAR");
        dataTypeMap.put("REF CURSOR", "OracleTypes.CURSOR");
        dataTypeMap.put("BLOB", "OracleTypes.BLOB");
        dataTypeMap.put("NUMBER", "OracleTypes.NUMERIC");

        dataTypeOutMap.put("VARCHAR2", "String");
        dataTypeOutMap.put("REF CURSOR", "Object");
        dataTypeOutMap.put("BLOB", "Blob");
        dataTypeOutMap.put("NUMBER", "Double");
        DataBaseFactory.register(DataBaseType.MYSQL, this);
        DataBaseFactory.register(DataBaseType.MYSQL8, this);
    }
}
