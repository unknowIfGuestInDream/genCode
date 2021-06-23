package com.newangels.gen.service.impl;

import com.newangels.gen.enums.DataBaseType;
import com.newangels.gen.factory.DataBaseFactory;
import com.newangels.gen.service.DataBaseProcedureService;
import com.newangels.gen.util.DBUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * mysql过程信息
 *
 * @author: TangLiang
 * @date: 2021/6/19 8:55
 * @since: 1.0
 */
@Service
public class MysqlProcedureServiceImpl implements DataBaseProcedureService {
    @Override
    public String selectProcedures(String name) {
        return "select name from mysql.proc where type = 'PROCEDURE'";
    }

    @Override
    public String loadProcedure(String name) {
        return "show create procedure " + name;
    }

    @Override
    public String loadProcedure(String name, DBUtil dbUtil) {
        String allProceduresSql = loadProcedure(name);
        //执行sql
        List<Map<String, Object>> list = dbUtil.executeQuery(allProceduresSql);
        //获取结果集
        return list.size() > 0 ? list.get(0).get("Create Procedure").toString() : "";
    }

    @Override
    public String selectArguments(String owner, String objectName) {
        return null;
    }

    @Override
    public String getJavaClass(String type) {
        return null;
    }

    @Override
    public String getRepositoryOutType(String type) {
        return null;
    }

    @Override
    public String getRepositoryOutTypeCode(String type) {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DataBaseFactory.register(DataBaseType.MYSQL, this);
        DataBaseFactory.register(DataBaseType.MYSQL8, this);
    }
}
