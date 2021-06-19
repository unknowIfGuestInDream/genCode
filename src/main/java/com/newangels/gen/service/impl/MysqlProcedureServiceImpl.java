package com.newangels.gen.service.impl;

import com.newangels.gen.service.DataBaseProcedureService;
import com.newangels.gen.util.DataBaseFactory;
import com.newangels.gen.util.DataBaseType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MysqlProcedureServiceImpl implements DataBaseProcedureService {
    @Override
    public List<Map<String, Object>> selectProcedure(String NAME) {
        return null;
    }

    @Override
    public List<Map<String, Object>> selectArguments(String OWNER, String OBJECT_NAME) {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DataBaseFactory.register(DataBaseType.MYSQL, this);
        DataBaseFactory.register(DataBaseType.MYSQL8, this);
    }
}
