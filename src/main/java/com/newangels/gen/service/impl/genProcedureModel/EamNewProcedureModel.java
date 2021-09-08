package com.newangels.gen.service.impl.genProcedureModel;

import com.newangels.gen.service.AbstractGenProcedureModel;
import com.newangels.gen.service.NameConventService;
import org.springframework.beans.factory.InitializingBean;

/**
 * Eam3期风格代码
 *
 * @author: TangLiang
 * @date: 2021/9/8 16:24
 * @since: 1.0
 */
public class EamNewProcedureModel extends AbstractGenProcedureModel implements InitializingBean {
    @Override
    protected String getFtlPackageName() {
        return "eamNew";
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

    }
}
