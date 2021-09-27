package com.newangels.gen.service.impl.tableToProcedure;

import com.newangels.gen.enums.DataBaseType;
import com.newangels.gen.factory.AbstractTableToProcedureFactory;
import com.newangels.gen.service.AbstractTableToProcedure;
import com.newangels.gen.service.NameConventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * mysql表生成存储过程
 *
 * @author: TangLiang
 * @date: 2021/9/12 0:04
 * @since: 1.0
 */
@Service
public class MysqlTableToProc extends AbstractTableToProcedure {

    @Override
    protected String getFtlPackageName() {
        return "mysql";
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
    public void afterPropertiesSet() throws Exception {
        AbstractTableToProcedureFactory.register(DataBaseType.MYSQL, this);
        AbstractTableToProcedureFactory.register(DataBaseType.MYSQL8, this);
    }
}
