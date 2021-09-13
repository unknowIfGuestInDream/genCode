package com.newangels.gen.service.impl.genProcedureModel;

import com.newangels.gen.enums.GenProcedureModelType;
import com.newangels.gen.factory.AbstractGenProcedureModelFactory;
import com.newangels.gen.service.AbstractGenProcedureModel;
import com.newangels.gen.service.DataBaseProcedureService;
import com.newangels.gen.service.NameConventService;
import com.newangels.gen.util.dataSource.DataSourceUtil;
import freemarker.template.Configuration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Eam3期风格代码
 *
 * @author: TangLiang
 * @date: 2021/9/8 16:24
 * @since: 1.0
 */
@Service
public class EamNewProcedureModel extends AbstractGenProcedureModel {
    @Override
    protected String getFtlPackageName() {
        return "eamNew";
    }

    @Override
    protected Map<String, Object> dealProcdure(String moduleName, String packageName, String userName, List<String> procedureNameList, NameConventService nameConvent, DataBaseProcedureService dbProcedure, DataSourceUtil dataSourceUtil, Configuration configuration) {
        Map<String, Object> result = super.dealProcdure(moduleName, packageName, userName, procedureNameList, nameConvent, dbProcedure, dataSourceUtil, configuration);
        //ant design pro规范
        String controllerMethod = result.get("controllerMethod").toString()
                .replace("V_PAGESIZE", "pageSize")
                .replace("V_PAGE", "current");
        result.replace("controllerMethod", controllerMethod);
        return result;
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
        AbstractGenProcedureModelFactory.register(GenProcedureModelType.EAM3, this);
    }
}
