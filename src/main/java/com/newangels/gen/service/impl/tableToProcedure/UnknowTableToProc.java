package com.newangels.gen.service.impl.tableToProcedure;

import com.newangels.gen.enums.DataBaseType;
import com.newangels.gen.exception.UnSupportedDataSourceException;
import com.newangels.gen.factory.AbstractTableToProcedureFactory;
import com.newangels.gen.service.AbstractTableToProcedure;
import com.newangels.gen.service.NameConventService;
import freemarker.template.Configuration;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 未知数据源表生成存储过程
 *
 * @author: TangLiang
 * @date: 2021/9/13 20:29
 * @since: 1.0
 */
@Service
public class UnknowTableToProc extends AbstractTableToProcedure {

    @Override
    protected String getFtlPackageName() {
        return "unknow";
    }

    @Override
    protected void dealGetProcedure(String tableName, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, NameConventService nameConvent, Map<String, Object> objectMap) {

    }

    @Override
    protected void dealSelProcedure(String tableName, List<String> selParams, List<String> selParamTypes, List<String> selParamDescs, List<Integer> selType, NameConventService nameConvent, Map<String, Object> objectMap) {

    }

    @Override
    protected void dealSelWithPageProcedure(String tableName, List<String> selParams, List<String> selParamTypes, List<String> selParamDescs, List<Integer> selType, NameConventService nameConvent, Map<String, Object> objectMap) {

    }

    @Override
    protected void dealInsProcedure(String tableName, List<String> insParams, List<String> insParamTypes, List<String> insParamDescs, NameConventService nameConvent, Map<String, Object> objectMap) {

    }

    @Override
    protected void dealUpdProcedure(String tableName, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, List<String> updParams, List<String> updParamTypes, List<String> updParamDescs, NameConventService nameConvent, Map<String, Object> objectMap) {

    }

    @Override
    protected void dealSaveProcedure(String tableName, List<String> insParams, List<String> insParamTypes, List<String> insParamDescs, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, List<String> updParams, List<String> updParamTypes, List<String> updParamDescs, NameConventService nameConvent, Map<String, Object> objectMap) {

    }

    @Override
    protected void dealDelProcedure(String tableName, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, NameConventService nameConvent, Map<String, Object> objectMap) {

    }

    @Override
    public Map<String, Object> genProceduresByTable(String tableName, String tableDesc, List<String> params, List<String> paramTypes, List<String> paramDescs, List<Integer> priParamIndex, List<Integer> selParamsIndex, List<Integer> selType, List<Integer> insParamIndex, List<Integer> updParamIndex, List<Integer> orderParamIndex, List<String> orderParamTypes, NameConventService nameConvent, Configuration configuration) {
        throw new UnSupportedDataSourceException("未知数据库, 不支持当前数据库生成存储过程");
    }

    @Override
    public Map<String, Object> genAutoInsKey(String tableName, String primaryKey, Configuration configuration) {
        Map<String, Object> result = new HashMap<>(4);
        result.put("autoInsKey", "未知数据库, 不支持当前数据库生成自增主键");
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AbstractTableToProcedureFactory.register(DataBaseType.UNKNOW, this);
    }
}
