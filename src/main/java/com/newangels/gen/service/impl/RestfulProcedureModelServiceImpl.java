package com.newangels.gen.service.impl;

import com.newangels.gen.factory.GenProcedureModelFactory;
import com.newangels.gen.service.GenProcedureModelService;
import com.newangels.gen.util.GenProcedureModelType;
import org.springframework.stereotype.Service;

/**
 * RestFul风格代码
 *
 * @author: TangLiang
 * @date: 2021/6/20 13:00
 * @since: 1.0
 */
@Service
public class RestfulProcedureModelServiceImpl implements GenProcedureModelService {
    @Override
    public String getControllerCode(String moduleName) {
        return null;
    }

    @Override
    public String getServiceCode(String moduleName) {
        return null;
    }

    @Override
    public String getServiceImplCode(String moduleName) {
        return null;
    }

    @Override
    public String getRepositoryCode(String moduleName) {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        GenProcedureModelFactory.register(GenProcedureModelType.RESTFUL, this);
    }
}
