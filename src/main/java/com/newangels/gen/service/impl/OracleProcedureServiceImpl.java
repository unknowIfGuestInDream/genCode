package com.newangels.gen.service.impl;

import com.newangels.gen.service.DataBaseProcedureService;
import com.newangels.gen.util.DataBaseFactory;
import com.newangels.gen.util.DataBaseType;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * oracle过程信息
 *
 * @author: TangLiang
 * @date: 2021/6/19 8:46
 * @since: 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@RequiredArgsConstructor
public class OracleProcedureServiceImpl implements DataBaseProcedureService {
    private final JdbcTemplate genJdbcTemplate;

    @Override
    public List<Map<String, Object>> selectProcedure(String NAME) {
        return genJdbcTemplate.queryForList("SELECT * FROM USER_SOURCE WHERE NAME = ?", NAME);
    }

    @Override
    public List<Map<String, Object>> selectArguments(String OWNER, String OBJECT_NAME) {
        return genJdbcTemplate.queryForList("select * from SYS.ALL_ARGUMENTS t where t.OWNER = ? and t.OBJECT_NAME = ?", OWNER, OBJECT_NAME);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DataBaseFactory.register(DataBaseType.ORACLE, this);
    }
}
