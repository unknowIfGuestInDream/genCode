package com.newangels.gen.service.impl.genProcedureModel;

import com.newangels.gen.enums.GenProcedureModelType;
import com.newangels.gen.factory.AbstractGenProcedureModelFactory;
import com.newangels.gen.service.AbstractGenProcedureModel;
import com.newangels.gen.service.NameConventService;
import org.springframework.stereotype.Service;

/**
 * EAM项目风格代码
 *
 * @author: TangLiang
 * @date: 2021/9/8 16:24
 * @since: 1.0
 */
@Service
public class EamProcedureModel extends AbstractGenProcedureModel {
    @Override
    protected String getFtlPackageName() {
        return "eam";
    }

    @Override
    protected String getMappingType(String procedureName, NameConventService nameConvent) {
        if ("select".equals(nameConvent.getName(procedureName)) || "load".equals(nameConvent.getName(procedureName))) {
            return "GetMapping";
        }
        return "PostMapping";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AbstractGenProcedureModelFactory.register(GenProcedureModelType.EAM, this);
    }
}
