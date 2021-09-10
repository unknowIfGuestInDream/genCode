package com.newangels.gen.service.impl.nameConvent;

import com.newangels.gen.enums.NameConventType;
import com.newangels.gen.factory.NameConventFactory;
import com.newangels.gen.service.NameConventService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 未知命名规范
 * 默认返回过程后缀名
 *
 * @author: TangLiang
 * @date: 2021/6/21 10:34
 * @since: 1.0
 */
@Service
@Deprecated
public class UnKnowNameConventServiceImpl implements NameConventService {
    @Override
    public String getName(String procedureName) {
        return procedureName.substring(procedureName.lastIndexOf("_") + 1).toLowerCase();
    }

    @Override
    public String getResultName(String name) {
        return name;
    }

    @Override
    public void sortMethod(List<String> procedureNameList) {

    }

    @Override
    public String getProcName(String tableName, int procType) {
        return tableName;
    }

    @Override
    public String getProcOutParamName(String name) {
        return name;
    }

    @Override
    public String genProcInParamName(String paramName, String paramType) {
        return "V_" + paramName;
    }

    @Override
    public String genSelProcInParam(String param, String paramType, String paramDesc, Integer selType) {
        return genProcInParamName(param, paramType) + " IN " + paramType + ", --" + paramDesc;
    }

    @Override
    public String genSelProcSqlWhere(String param, String paramType, Integer selType) {
        return " " + param + " = " + genProcInParamName(param, paramType);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        NameConventFactory.register(NameConventType.UNKNOW, this);
    }
}
