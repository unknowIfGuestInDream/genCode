package com.newangels.gen.service.impl;

import com.newangels.gen.service.DataBaseProcedureService;
import com.newangels.gen.util.DataBaseFactory;
import com.newangels.gen.util.DataBaseType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author: TangLiang
 * @date: 2021/6/19 9:07
 * @since: 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@RequiredArgsConstructor
public class UnKnowDataBaseProcedureImpl implements DataBaseProcedureService {
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
        DataBaseFactory.register(DataBaseType.UNKNOW, this);
    }
}
