package com.newangels.gen.service.impl.tableToProcedure;

import com.newangels.gen.enums.DataBaseType;
import com.newangels.gen.exception.UnSupportedDataSourceException;
import com.newangels.gen.factory.AbstractTableToProcedureFactory;
import com.newangels.gen.service.AbstractTableToProcedure;
import com.newangels.gen.service.NameConventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * mariadb表生成存储过程
 *
 * @author: TangLiang
 * @date: 2021/9/12 0:02
 * @since: 1.0
 */
@Service
public class MariaDbTableToProc extends AbstractTableToProcedure {

    @Override
    protected String getFtlPackageName() {
        return "mariaDb";
    }

    @Override
    protected void dealGetProcedure(String tableName, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, NameConventService nameConvent, Map<String, Object> objectMap) {
        throw new UnSupportedDataSourceException("暂时不支持mariaDb数据库存储过程生成");
    }

    @Override
    protected void dealSelProcedure(String tableName, List<String> selParams, List<String> selParamTypes, List<String> selParamDescs, List<Integer> selType, NameConventService nameConvent, Map<String, Object> objectMap) {
        throw new UnSupportedDataSourceException("暂时不支持mariaDb数据库存储过程生成");
    }

    @Override
    protected void dealSelWithPageProcedure(String tableName, List<String> selParams, List<String> selParamTypes, List<String> selParamDescs, List<Integer> selType, NameConventService nameConvent, Map<String, Object> objectMap) {
        throw new UnSupportedDataSourceException("暂时不支持mariaDb数据库存储过程生成");
    }

    @Override
    protected void dealInsProcedure(String tableName, List<String> insParams, List<String> insParamTypes, List<String> insParamDescs, NameConventService nameConvent, Map<String, Object> objectMap) {
        throw new UnSupportedDataSourceException("暂时不支持mariaDb数据库存储过程生成");
    }

    @Override
    protected void dealUpdProcedure(String tableName, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, List<String> updParams, List<String> updParamTypes, List<String> updParamDescs, NameConventService nameConvent, Map<String, Object> objectMap) {
        throw new UnSupportedDataSourceException("暂时不支持mariaDb数据库存储过程生成");
    }

    @Override
    protected void dealSaveProcedure(String tableName, List<String> insParams, List<String> insParamTypes, List<String> insParamDescs, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, List<String> updParams, List<String> updParamTypes, List<String> updParamDescs, NameConventService nameConvent, Map<String, Object> objectMap) {
        throw new UnSupportedDataSourceException("暂时不支持mariaDb数据库存储过程生成");
    }

    @Override
    protected void dealDelProcedure(String tableName, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, NameConventService nameConvent, Map<String, Object> objectMap) {
        throw new UnSupportedDataSourceException("暂时不支持mariaDb数据库存储过程生成");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AbstractTableToProcedureFactory.register(DataBaseType.MARIADB, this);
    }
}
