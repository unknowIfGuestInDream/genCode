package com.newangels.gen.service.impl;

import com.newangels.gen.factory.DataBaseFactory;
import com.newangels.gen.service.DataBaseProcedureService;
import com.newangels.gen.util.DataBaseType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * oracle过程信息
 *
 * @author: TangLiang
 * @date: 2021/6/19 8:46
 * @since: 1.0
 */
@Service
public class OracleProcedureServiceImpl implements DataBaseProcedureService {

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
    public void afterPropertiesSet() throws Exception {
        DataBaseFactory.register(DataBaseType.ORACLE, this);
    }
}
