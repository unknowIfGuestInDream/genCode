package com.newangels.gen.service.impl;

import com.newangels.gen.factory.DataBaseFactory;
import com.newangels.gen.service.DataBaseProcedureService;
import com.newangels.gen.util.DataBaseType;
import org.springframework.stereotype.Service;

/**
 * 未知数据源
 *
 * @author: TangLiang
 * @date: 2021/6/19 9:07
 * @since: 1.0
 */
@Service
public class UnKnowDataBaseProcedureImpl implements DataBaseProcedureService {
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
    public void afterPropertiesSet() throws Exception {
        DataBaseFactory.register(DataBaseType.UNKNOW, this);
    }
}
