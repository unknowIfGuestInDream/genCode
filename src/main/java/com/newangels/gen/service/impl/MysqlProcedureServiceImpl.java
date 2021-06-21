package com.newangels.gen.service.impl;

import com.newangels.gen.factory.DataBaseFactory;
import com.newangels.gen.service.DataBaseProcedureService;
import com.newangels.gen.util.DataBaseType;
import org.springframework.stereotype.Service;

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
        return null;
    }

    @Override
    public String loadProcedure(String name) {
        return null;
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
    public void afterPropertiesSet() throws Exception {
        DataBaseFactory.register(DataBaseType.MYSQL, this);
        DataBaseFactory.register(DataBaseType.MYSQL8, this);
    }
}
