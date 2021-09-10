package com.newangels.gen.service.impl.tableToProcedure;

import com.newangels.gen.enums.DataBaseType;
import com.newangels.gen.exception.UnSupportedDataSourceException;
import com.newangels.gen.factory.DataBaseTableToProcFactory;
import com.newangels.gen.service.NameConventService;
import com.newangels.gen.service.TableToProcedureService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * sqlserver表生成存储过程
 *
 * @author: TangLiang
 * @date: 2021/7/23 10:37
 * @since: 1.0
 */
@Service
@Deprecated
public class SqlServerTableToProcServiceImpl implements TableToProcedureService {
    @Override
    public String genGetProcedure(String tableName, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, NameConventService nameConvent) {
        throw new UnSupportedDataSourceException("暂时不支持sqlserver数据库存储过程生成");
    }

    @Override
    public String genSelProcedure(String tableName, List<String> selParams, List<String> selParamTypes, List<String> selParamDescs, List<Integer> selType, NameConventService nameConvent) {
        throw new UnSupportedDataSourceException("暂时不支持sqlserver数据库存储过程生成");
    }

    @Override
    public String genSelProcedureWithPage(String tableName, List<String> selParams, List<String> selParamTypes, List<String> selParamDescs, List<Integer> selType, NameConventService nameConvent) {
        throw new UnSupportedDataSourceException("暂时不支持sqlserver数据库存储过程生成");
    }

    @Override
    public String genInsProcedure(String tableName, List<String> insParams, List<String> insParamTypes, List<String> insParamDescs, NameConventService nameConvent) {
        throw new UnSupportedDataSourceException("暂时不支持sqlserver数据库存储过程生成");
    }

    @Override
    public String genUpdProcedure(String tableName, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, List<String> updParams, List<String> updParamTypes, List<String> updParamDescs, NameConventService nameConvent) {
        throw new UnSupportedDataSourceException("暂时不支持sqlserver数据库存储过程生成");
    }

    @Override
    public String genSaveProcedure(String tableName, List<String> insParams, List<String> insParamTypes, List<String> insParamDescs, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, List<String> updParams, List<String> updParamTypes, List<String> updParamDescs, NameConventService nameConvent) {
        throw new UnSupportedDataSourceException("暂时不支持sqlserver数据库存储过程生成");
    }

    @Override
    public String genDelProcedure(String tableName, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, NameConventService nameConvent) {
        throw new UnSupportedDataSourceException("暂时不支持sqlserver数据库存储过程生成");
    }

    @Override
    public Map<String, Object> genProceduresByTable(String tableName, List<String> params, List<String> paramTypes, List<String> paramDescs, List<Integer> priParamIndex, List<Integer> selParamsIndex, List<Integer> selType, List<Integer> insParamIndex, List<Integer> updParamIndex, NameConventService nameConvent) {
        throw new UnSupportedDataSourceException("暂时不支持sqlserver数据库生成存储过程");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DataBaseTableToProcFactory.register(DataBaseType.SQLSERVER, this);
    }
}
