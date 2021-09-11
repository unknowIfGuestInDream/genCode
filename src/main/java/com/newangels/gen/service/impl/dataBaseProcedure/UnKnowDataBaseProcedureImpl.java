package com.newangels.gen.service.impl.dataBaseProcedure;

import com.newangels.gen.enums.DataBaseType;
import com.newangels.gen.exception.UnSupportedDataSourceException;
import com.newangels.gen.factory.DataBaseProcedureFactory;
import com.newangels.gen.factory.DataBaseTableFactory;
import com.newangels.gen.factory.DataBaseTableToProcFactory;
import com.newangels.gen.service.DataBaseProcedureService;
import com.newangels.gen.service.DataBaseTableService;
import com.newangels.gen.service.NameConventService;
import com.newangels.gen.service.TableToProcedureService;
import com.newangels.gen.util.dataSource.DataSourceUtil;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 未知数据源
 *
 * @author: TangLiang
 * @date: 2021/6/19 9:07
 * @since: 1.0
 */
@Service
@Deprecated
public class UnKnowDataBaseProcedureImpl implements DataBaseProcedureService, DataBaseTableService, TableToProcedureService {

    @Override
    public String selectProcedures(String name) {
        throw new UnSupportedDataSourceException("不支持当前数据库代码生成");
    }

    @Override
    public List<Map<String, Object>> selectProcedures(String name, DataSourceUtil dataSourceUtil) {
        throw new UnSupportedDataSourceException("不支持当前数据库代码生成");
    }

    @Override
    public String loadProcedure(String name) {
        throw new UnSupportedDataSourceException("不支持当前数据库代码生成");
    }

    @Override
    public String loadProcedure(String name, DataSourceUtil dataSourceUtil) {
        throw new UnSupportedDataSourceException("不支持当前数据库代码生成");
    }

    @Override
    public String selectArguments(String owner, String objectName) {
        throw new UnSupportedDataSourceException("不支持当前数据库代码生成");
    }

    @Override
    public String getJavaClass(String type) {
        throw new UnSupportedDataSourceException("不支持当前数据库代码生成");
    }

    @Override
    public String getRepositoryOutType(String type) {
        throw new UnSupportedDataSourceException("不支持当前数据库代码生成");
    }

    @Override
    public String getRepositoryOutTypeCode(String type) {
        throw new UnSupportedDataSourceException("不支持当前数据库代码生成");
    }

    @Override
    public String loadTable(@NonNull String name) {
        throw new UnSupportedDataSourceException("暂时不支持当前数据库表信息查询");
    }

    @Override
    public Map<String, Object> loadTable(@NonNull String name, @NonNull DataSourceUtil dataSourceUtil) {
        throw new UnSupportedDataSourceException("暂时不支持当前数据库表信息查询");
    }

    @Override
    public String selectTables(String name) {
        throw new UnSupportedDataSourceException("暂时不支持当前数据库表信息查询");
    }

    @Override
    public List<Map<String, Object>> selectTables(String name, DataSourceUtil dataSourceUtil) {
        throw new UnSupportedDataSourceException("暂时不支持当前数据库表信息查询");
    }

    @Override
    public String selectTableInfo(@NonNull String name) {
        throw new UnSupportedDataSourceException("暂时不支持当前数据库表信息查询");
    }

    @Override
    public List<Map<String, Object>> selectTableInfo(@NonNull String name, @NonNull DataSourceUtil dataSourceUtil) {
        throw new UnSupportedDataSourceException("暂时不支持当前数据库表信息查询");
    }

    @Override
    public String genGetProcedure(String tableName, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, NameConventService nameConvent) {
        throw new UnSupportedDataSourceException("暂时不支持当前数据库存储过程生成");
    }

    @Override
    public String genSelProcedure(String tableName, List<String> selParams, List<String> selParamTypes, List<String> selParamDescs, List<Integer> selType, NameConventService nameConvent) {
        throw new UnSupportedDataSourceException("暂时不支持当前数据库存储过程生成");
    }

    @Override
    public String genSelProcedureWithPage(String tableName, List<String> selParams, List<String> selParamTypes, List<String> selParamDescs, List<Integer> selType, NameConventService nameConvent) {
        throw new UnSupportedDataSourceException("暂时不支持当前数据库存储过程生成");
    }

    @Override
    public String genInsProcedure(String tableName, List<String> insParams, List<String> insParamTypes, List<String> insParamDescs, NameConventService nameConvent) {
        throw new UnSupportedDataSourceException("暂时不支持当前数据库存储过程生成");
    }

    @Override
    public String genUpdProcedure(String tableName, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, List<String> updParams, List<String> updParamTypes, List<String> updParamDescs, NameConventService nameConvent) {
        throw new UnSupportedDataSourceException("暂时不支持当前数据库存储过程生成");
    }

    @Override
    public String genSaveProcedure(String tableName, List<String> insParams, List<String> insParamTypes, List<String> insParamDescs, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, List<String> updParams, List<String> updParamTypes, List<String> updParamDescs, NameConventService nameConvent) {
        throw new UnSupportedDataSourceException("暂时不支持当前数据库存储过程生成");
    }

    @Override
    public String genDelProcedure(String tableName, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, NameConventService nameConvent) {
        throw new UnSupportedDataSourceException("暂时不支持当前数据库存储过程生成");
    }

    @Override
    public Map<String, Object> genProceduresByTable(String tableName, List<String> params, List<String> paramTypes, List<String> paramDescs, List<Integer> priParamIndex, List<Integer> selParamsIndex, List<Integer> selType, List<Integer> insParamIndex, List<Integer> updParamIndex, NameConventService nameConvent) {
        throw new UnSupportedDataSourceException("暂时不支持当前数据库生成存储过程");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DataBaseProcedureFactory.register(DataBaseType.UNKNOW, this);
        DataBaseTableFactory.register(DataBaseType.UNKNOW, this);
        DataBaseTableToProcFactory.register(DataBaseType.UNKNOW, this);
    }
}
