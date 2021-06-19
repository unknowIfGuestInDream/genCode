package com.newangels.gen.service.impl;

import com.newangels.gen.service.DataBaseProcedureService;
import com.newangels.gen.util.DataBaseFactory;
import com.newangels.gen.util.DataBaseType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * mariaDb过程信息
 *
 * @author: TangLiang
 * @date: 2021/6/19 8:55
 * @since: 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MariaDbProcedureServiceImpl implements DataBaseProcedureService {
    @Override
    public String selectProcedure(String NAME) {
        return null;
    }

    @Override
    public String selectArguments(String OWNER, String OBJECT_NAME) {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DataBaseFactory.register(DataBaseType.MARIADB, this);
    }
}
