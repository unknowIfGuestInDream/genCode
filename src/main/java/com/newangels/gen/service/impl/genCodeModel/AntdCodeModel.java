package com.newangels.gen.service.impl.genCodeModel;

import com.newangels.gen.enums.GenCodeModelType;
import com.newangels.gen.factory.AbstractGenCodeModelFactory;
import com.newangels.gen.service.AbstractGenCodeModel;
import freemarker.template.Configuration;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 大连ANTD模版
 *
 * @author: TangLiang
 * @date: 2021/9/8 16:24
 * @since: 1.0
 */
@Service
public class AntdCodeModel extends AbstractGenCodeModel {
    @Override
    protected String getFtlPackageName() {
        return "antd";
    }

    @Override
    protected void dealCommonCode(String tableName, String tableDesc, String moduleName, String moduleDesc, String packageName, String author, boolean hasDelBatch, List<String> primarys, Map<String, Object> objectMap) {
        super.dealCommonCode(tableName, tableDesc, moduleName, moduleDesc, packageName, author, hasDelBatch, primarys, objectMap);
        //antd前台默认不支持批量删除
        objectMap.replace("hasDelBatch", true, false);
        //antd table默认只有一个主键
        objectMap.put("antd_primary", primarys.size() > 0 ? primarys.get(0) : "");
    }

    @Override
    protected void dealOtherCode(String tableName, String tableDesc, String moduleName, String moduleDesc, String packageName, String author, boolean hasDelBatch, List<String> params, List<String> paramDescs, List<String> paramJavaClass, List<String> primarys, List<String> primaryDesc, List<String> primaryJavaClass, List<String> selParams, List<String> selParamDescs, List<String> selParamJavaClass, List<Integer> selType, List<String> insParams, List<String> insParamDescs, List<String> insParamJavaClass, List<String> updParams, List<String> updParamDescs, List<String> updParamJavaClass, Map<String, Object> objectMap) {
        StringJoiner storeParams = new StringJoiner(", ");
        StringJoiner tableParams = new StringJoiner(", ");

        dealTableParam(storeParams, tableParams, params, paramDescs, primarys);
    }

    private void dealTableParam(StringJoiner storeParams, StringJoiner tableParams, List<String> params, List<String> paramDescs, List<String> primarys) {
        for (int i = 0, length = params.size(); i < length; i++) {
            //store参数
            storeParams.add("'" + params.get(i) + "'");
            //主键不显示在table中
            if (primarys.contains(params.get(i))) {
                continue;
            }
            tableParams.add("{\n" +
                    "                text: '" + paramDescs.get(i) + "',\n" +
                    "                dataIndex: '" + params.get(i) + "',\n" +
                    "                flex: 1,\n" +
                    "                minWidth: 150\n" +
                    "            }");
            tableParams.add("{\n" +
                    "      title: '"+paramDescs.get(i)+"',\n" +
                    "      dataIndex: '" + params.get(i) + "',\n" +
                    "      width: 150,\n" +
                    "      hideInSearch: true\n" +
                    "      hideInTable: false\n" +
                    "    }");
        }
    }

    @Override
    protected Map<String, Object> getResult(String driver, Map<String, Object> objectMap, Configuration configuration) {
        String module = objectMap.get("module").toString();
        Map<String, Object> result = super.getResult(driver, objectMap, configuration);
        result.put("index.tsx", getIndex(configuration, objectMap));
        result.put("service.ts", getServiceTs(configuration, objectMap));
        result.put("Update" + module + ".tsx", getUpdateTsx(configuration, objectMap));
        return result;
    }

    @Override
    protected List<String> getTabList(Map<String, Object> objectMap) {
        String module = objectMap.get("module").toString();
        return new ArrayList<>(Arrays.asList("controller", "service", "serviceImpl", "index.tsx", "service.ts", "Update" + module + ".tsx", "BaseUtils", "BaseSqlCriteria"));
    }

    /**
     * 获取index.tsx
     */
    private String getIndex(Configuration configuration, Map<String, Object> objectMap) {
        return getFtlModel(configuration, objectMap, "index.ftl");
    }

    /**
     * 获取service.ts
     */
    private String getServiceTs(Configuration configuration, Map<String, Object> objectMap) {
        return getFtlModel(configuration, objectMap, "service.ftl");
    }

    /**
     * 获取update.tsx
     */
    private String getUpdateTsx(Configuration configuration, Map<String, Object> objectMap) {
        return getFtlModel(configuration, objectMap, "update.ftl");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AbstractGenCodeModelFactory.register(GenCodeModelType.ANTD, this);
    }
}
