package com.newangels.gen.service.impl.tableToProcedure;

import com.newangels.gen.enums.DataBaseType;
import com.newangels.gen.factory.AbstractTableToProcedureFactory;
import com.newangels.gen.service.AbstractTableToProcedure;
import com.newangels.gen.service.NameConventService;
import com.newangels.gen.util.ProcTypes;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * oracle表生成存储过程
 *
 * @author: TangLiang
 * @date: 2021/9/10 14:02
 * @since: 1.0
 */
public class OracleTableToProc extends AbstractTableToProcedure implements InitializingBean {

    @Override
    protected String getFtlPackageName() {
        return "oracle";
    }

    @Override
    protected void dealGetProcedure(String tableName, String tableDesc, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, NameConventService nameConvent, Map<String, Object> objectMap) {
        //查询条件
        StringBuilder sqlWhere = new StringBuilder();
        //存储过程入参
        StringJoiner inParams = new StringJoiner("\n");
        for (int i = 0, length = primarys.size(); i < length; i++) {
            if (i == 0) {
                sqlWhere.append(" \nWHERE");
            } else {
                sqlWhere.append(" \nAND");
            }
            inParams.add(nameConvent.genSelProcInParam(primarys.get(i), primaryTypes.get(i), primaryDesc.get(i), 0));
            sqlWhere.append(nameConvent.genSelProcSqlWhere(primarys.get(i), primaryTypes.get(i), 0));
        }
        //添加出参字段, 出参类型和出参信息
        inParams.add(nameConvent.getProcOutParamName("message") + " OUT VARCHAR2, --成功执行信息为：‘success’，失败执行信息为错误信息");
        inParams.add(nameConvent.getProcOutParamName("result") + " OUT SYS_REFCURSOR --成功返回结果集");
        //存储过程名称
        String procName = nameConvent.getProcName(tableName, ProcTypes.GET);
    }

    @Override
    protected void dealSelProcedure(String tableName, String tableDesc, List<String> selParams, List<String> selParamTypes, List<String> selParamDescs, List<Integer> selType, NameConventService nameConvent, Map<String, Object> objectMap) {
    }

    @Override
    protected void dealSelWithPageProcedure(String tableName, String tableDesc, List<String> selParams, List<String> selParamTypes, List<String> selParamDescs, List<Integer> selType, NameConventService nameConvent, Map<String, Object> objectMap) {
    }

    @Override
    protected void dealInsProcedure(String tableName, String tableDesc, List<String> insParams, List<String> insParamTypes, List<String> insParamDescs, NameConventService nameConvent, Map<String, Object> objectMap) {
    }

    @Override
    protected void dealUpdProcedure(String tableName, String tableDesc, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, List<String> updParams, List<String> updParamTypes, List<String> updParamDescs, NameConventService nameConvent, Map<String, Object> objectMap) {
    }

    @Override
    protected void dealSaveProcedure(String tableName, String tableDesc, List<String> insParams, List<String> insParamTypes, List<String> insParamDescs, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, List<String> updParams, List<String> updParamTypes, List<String> updParamDescs, NameConventService nameConvent, Map<String, Object> objectMap) {
    }

    @Override
    protected void dealDelProcedure(String tableName, String tableDesc, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, NameConventService nameConvent, Map<String, Object> objectMap) {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AbstractTableToProcedureFactory.register(DataBaseType.ORACLE, this);
    }
}
