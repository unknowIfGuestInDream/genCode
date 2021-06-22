package com.newangels.gen.service.impl;

import com.newangels.gen.enums.DataBaseType;
import com.newangels.gen.factory.DataBaseFactory;
import com.newangels.gen.service.DataBaseProcedureService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
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

    Map<String, String> map = new ConcurrentHashMap<>(32);
    Map<String, String> dataTypeMap = new ConcurrentHashMap<>(8);
    Map<String, String> dataTypeOutMap = new ConcurrentHashMap<>(8);

    @Override
    public String selectProcedures(String name) {
        String sql = "select distinct name From user_source where type = 'PROCEDURE'";
        if (StringUtils.isNotEmpty(name)) {
            sql += " and NAME like '%" + name + "%'";
        }
        return sql;
    }

    @Override
    public String loadProcedure(String name) {
        String sql = "SELECT * FROM USER_SOURCE where type = 'PROCEDURE'";
        if (StringUtils.isNotEmpty(name)) {
            sql += " and NAME = '" + name + "'";
        }
        sql += " ORDER BY LINE";
        return sql;
    }

    @Override
    public String selectArguments(String owner, String objectName) {
        return "select * from SYS.ALL_ARGUMENTS t where t.OWNER = '" + owner + "' and t.OBJECT_NAME = '" + objectName + "' ORDER BY POSITION";
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

        dataTypeMap.put("VARCHAR2", "OracleTypes.VARCHAR");
        dataTypeMap.put("REF CURSOR", "OracleTypes.CURSOR");
        dataTypeMap.put("BLOB", "OracleTypes.BLOB");
        dataTypeMap.put("NUMBER", "OracleTypes.NUMERIC");

        dataTypeOutMap.put("VARCHAR2", "String");
        dataTypeOutMap.put("REF CURSOR", "Object");
        dataTypeOutMap.put("BLOB", "Blob");
        dataTypeOutMap.put("NUMBER", "Double");
        DataBaseFactory.register(DataBaseType.ORACLE, this);
    }
}
