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
    public String selectProcedure(String NAME) {
        String sql = "SELECT * FROM USER_SOURCE";
        if (StringUtils.isNotEmpty(NAME)) {
            sql += " WHERE NAME = " + NAME;
        }
        return sql;
    }

    @Override
    public String selectArguments(String OWNER, String OBJECT_NAME) {
        return "select * from SYS.ALL_ARGUMENTS t where t.OWNER = " + OWNER + " and t.OBJECT_NAME = " + OBJECT_NAME;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DataBaseFactory.register(DataBaseType.ORACLE, this);
    }
}
