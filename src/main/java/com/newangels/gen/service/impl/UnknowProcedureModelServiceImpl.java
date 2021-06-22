package com.newangels.gen.service.impl;

import com.newangels.gen.enums.GenProcedureModelType;
import com.newangels.gen.factory.GenProcedureModelFactory;
import com.newangels.gen.service.DataBaseProcedureService;
import com.newangels.gen.service.GenProcedureModelService;
import com.newangels.gen.service.NameConventService;
import com.newangels.gen.util.DBUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 未知风格代码
 *
 * @author: TangLiang
 * @date: 2021/6/20 13:00
 * @since: 1.0
 */
@Service
public class UnknowProcedureModelServiceImpl implements GenProcedureModelService {
    @Override
    public String getControllerCode(String moduleName, String packageName) {
        return "";
    }

    @Override
    public String getServiceCode(String moduleName, String packageName) {
        return "";
    }

    @Override
    public String getServiceImplCode(String moduleName, String packageName) {
        return "";
    }

    @Override
    public String getRepositoryCode(String moduleName, String packageName) {
        return "";
    }

    @Override
    public Map<String, Object> genCode(String moduleName, String packageName, String userName, List<String> procedureNameList, NameConventService nameConvent, DataBaseProcedureService dbProcedure, DBUtil dbUtil) {
        return new HashMap<>();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        GenProcedureModelFactory.register(GenProcedureModelType.UNKNOW, this);
    }
}
