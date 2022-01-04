package com.newangels.gen.service.impl.genCodeModel;

import com.newangels.gen.enums.GenCodeModelType;
import com.newangels.gen.enums.JavaClass;
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
    protected void dealCommonCode(String tableName, String tableDesc, String moduleName, String moduleDesc, String packageName, String author, boolean hasDelBatch, boolean hasExport, List<String> primarys, Map<String, Object> objectMap) {
        super.dealCommonCode(tableName, tableDesc, moduleName, moduleDesc, packageName, author, hasDelBatch, hasExport, primarys, objectMap);
        //antd前台默认不支持批量删除
        objectMap.replace("hasDelBatch", true, false);
        //antd table默认只有一个主键
        objectMap.put("antd_primary", primarys.size() > 0 ? primarys.get(0) : "");
    }

    @Override
    protected void dealOtherCode(String tableName, String tableDesc, String moduleName, String moduleDesc, String packageName, String author, boolean hasDelBatch, boolean hasExport, List<String> params, List<String> paramDescs, List<String> paramJavaClass, List<String> primarys, List<String> primaryDesc, List<String> primaryJavaClass, List<String> selParams, List<String> selParamDescs, List<String> selParamJavaClass, List<Integer> selType, List<String> insParams, List<String> insParamDescs, List<String> insParamJavaClass, List<String> updParams, List<String> updParamDescs, List<String> updParamJavaClass, Map<String, Object> objectMap) {
        StringJoiner tableParams = new StringJoiner(", ");
        StringJoiner updateForm = new StringJoiner("\n      ");
        dealTableParam(tableParams, params, paramDescs, paramJavaClass, primarys, selParams, selType);
        dealUpdateForm(updateForm, params, paramDescs, paramJavaClass, insParams, updParams, objectMap);
        objectMap.put("antd_tableParams", tableParams.toString());
        objectMap.put("antd_updateForm", tableParams.toString());
    }

    @Override
    protected void dealExportUrl(List<String> selParams, List<String> selParamJavaClass, List<Integer> selType, boolean hasExport, Map<String, Object> objectMap) {
        if (!hasExport) {
            objectMap.put("antd_exportParamUrl", "");
            return;
        }
        StringJoiner exportParamUrl = new StringJoiner(" +\n                  ");
        for (int i = 0, length = selParams.size(); i < length; i++) {
            //java类型为date的前台获取值的方法为getSubmitValue
            JavaClass javaClass = JavaClass.fromCode(selParamJavaClass.get(i));
            boolean paramIsDate = javaClass == JavaClass.Date;
            //为区间查询时
            if (selType.get(i) == 2) {
                exportParamUrl.add("'" + (i == 0 ? "" : "&") + "START_" + selParams.get(i) + "=' + encodeURIComponent(" + (paramIsDate ? "(typeof (formProps.form?.getFieldValue('START_" + selParams.get(i) + "')) == 'string') ? formProps.form?.getFieldValue('START_" + selParams.get(i) + "') : formProps.form?.getFieldValue('START_" + selParams.get(i) + "').format('YYYY-MM-DD')" : "formProps.form?.getFieldValue('START_" + selParams.get(i) + "')") + ")");
                exportParamUrl.add("'&END_" + selParams.get(i) + "=' + encodeURIComponent(" + (paramIsDate ? "(typeof (formProps.form?.getFieldValue('END_" + selParams.get(i) + "')) == 'string') ? formProps.form?.getFieldValue('END_" + selParams.get(i) + "') : formProps.form?.getFieldValue('END_" + selParams.get(i) + "').format('YYYY-MM-DD')" : "formProps.form?.getFieldValue('END_" + selParams.get(i) + "')") + ")");
            } else {
                exportParamUrl.add("'" + (i == 0 ? "" : "&") + selParams.get(i) + "=' + encodeURIComponent(" + (paramIsDate ? "(typeof (formProps.form?.getFieldValue('" + selParams.get(i) + "')) == 'string') ? formProps.form?.getFieldValue('" + selParams.get(i) + "') : formProps.form?.getFieldValue('" + selParams.get(i) + "').format('YYYY-MM-DD')" : "formProps.form?.getFieldValue('" + selParams.get(i) + "')") + ")");
            }
        }
        objectMap.put("antd_exportParamUrl", exportParamUrl.toString());
    }

    /**
     * index.tsx页table的代码
     *
     * @param tableParams    StringJoiner
     * @param params         参数
     * @param paramDescs     参数描述
     * @param paramJavaClass 参数对应java类
     * @param primarys       主键参数
     * @param selParams      查询参数
     * @param selType        查询类型
     */
    private void dealTableParam(StringJoiner tableParams, List<String> params, List<String> paramDescs, List<String> paramJavaClass, List<String> primarys, List<String> selParams, List<Integer> selType) {
        for (int i = 0, length = params.size(); i < length; i++) {
            //主键不显示在table中
            if (primarys.contains(params.get(i))) {
                continue;
            }
            int position = selParams.indexOf(params.get(i));
            //判断java类型是否为Date
            JavaClass javaClass = JavaClass.fromCode(paramJavaClass.get(i));
            boolean paramIsDate = javaClass == JavaClass.Date;
            String valueType = getValueType(javaClass);
            //是查询条件且查询类型为区间查询
            if (position > -1 && selType.get(position) == 2) {
                tableParams.add("{\n" +
                        "      title: '开始" + paramDescs.get(i) + "',\n" +
                        "      dataIndex: 'START_" + params.get(i) + "',\n" +
                        "      valueType: '" + valueType + "',\n" +
                        "      hideInTable: true" + (paramIsDate ? "," : "") + "  //在Protable中隐藏，不显示\n" +
                        (paramIsDate ? "      initialValue: moment(moment().year() + '-01-01').format('YYYY-MM-DD') //设置默认值\n" : "") +
                        "    }, {\n" +
                        "      title: '结束" + paramDescs.get(i) + "',\n" +
                        "      dataIndex: 'END_" + params.get(i) + "',\n" +
                        "      valueType: '" + valueType + "',\n" +
                        "      hideInTable: true" + (paramIsDate ? "," : "") + "\n" +
                        (paramIsDate ? "      initialValue: moment(moment().year() + '-12-31').format('YYYY-MM-DD') //设置默认值\n" : "") +
                        "    }");
            } else {
                tableParams.add("{\n" +
                        "      title: '" + paramDescs.get(i) + "',\n" +
                        "      dataIndex: '" + params.get(i) + "',\n" +
                        (paramIsDate ? "      valueType: 'date',\n" : "") +
                        "      width: 150,\n" +
                        "      hideInSearch: " + (position > -1 ? "true" : "false") + ",\n" +
                        "      hideInTable: false\n" +
                        "    }");
            }
        }
    }

    /**
     * 处理新增修改页的Form代码
     *
     * @param updateForm
     * @param params
     * @param paramDescs
     * @param paramJavaClass
     * @param insParams
     * @param updParams
     * @param objectMap
     */
    private void dealUpdateForm(StringJoiner updateForm, List<String> params, List<String> paramDescs, List<String> paramJavaClass, List<String> insParams, List<String> updParams, Map<String, Object> objectMap) {

    }

    /**
     * 根据对应java类获取valueType的值
     *
     * @param javaClass 参数对应java类
     */
    private String getValueType(JavaClass javaClass) {
        if (javaClass == JavaClass.String) {
            return "text";
        } else if (javaClass == JavaClass.Double) {
            return "digit";
        } else if (javaClass == JavaClass.Integer) {
            return "digit";
        } else if (javaClass == JavaClass.Date) {
            return "date";
        }
        return "select";
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
