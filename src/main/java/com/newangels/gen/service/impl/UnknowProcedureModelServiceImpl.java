package com.newangels.gen.service.impl;

import com.newangels.gen.factory.GenProcedureModelFactory;
import com.newangels.gen.service.GenProcedureModelService;
import com.newangels.gen.util.GenProcedureModelType;
import org.springframework.stereotype.Service;

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
    public String getControllerCode(String moduleName) {
        return "";
    }

    @Override
    public String getServiceCode(String moduleName) {
        return "";
    }

    @Override
    public String getServiceImplCode(String moduleName) {
        return "";
    }

    @Override
    public String getRepositoryCode(String moduleName) {
        return "";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        GenProcedureModelFactory.register(GenProcedureModelType.UNKNOW, this);
    }
}
