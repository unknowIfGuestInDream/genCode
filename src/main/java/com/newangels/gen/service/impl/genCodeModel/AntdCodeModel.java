package com.newangels.gen.service.impl.genCodeModel;

import com.newangels.gen.base.BaseUtils;
import com.newangels.gen.enums.GenCodeModelType;
import com.newangels.gen.enums.JavaClass;
import com.newangels.gen.factory.AbstractGenCodeModelFactory;
import com.newangels.gen.service.AbstractGenCodeModel;
import freemarker.template.Configuration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

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
    protected void dealCommonCode(String tableName, String tableDesc, String moduleName, String moduleDesc, String packageName, String author, boolean hasDelBatch, boolean hasExport, boolean hasView, List<String> primarys, Map<String, Object> objectMap) {
        super.dealCommonCode(tableName, tableDesc, moduleName, moduleDesc, packageName, author, hasDelBatch, hasExport, hasView, primarys, objectMap);
        //antd前台默认不支持批量删除
        objectMap.replace("hasDelBatch", true, false);
        //antd table默认只有一个主键
        objectMap.put("antd_primary", primarys.size() > 0 ? primarys.get(0) : "");
    }

    @Override
    protected void dealOtherCode(String tableName, String tableDesc, String moduleName, String moduleDesc, String packageName, String author, boolean hasDelBatch, boolean hasExport, boolean hasView, List<String> params, List<String> paramDescs, List<String> paramJavaClass, List<String> primarys, List<String> primaryDesc, List<String> primaryJavaClass, List<String> selParams, List<String> selParamDescs, List<String> selParamJavaClass, List<Integer> selType, List<String> insParams, List<String> insParamDescs, List<String> insParamJavaClass, List<String> updParams, List<String> updParamDescs, List<String> updParamJavaClass, Map<String, Object> objectMap) {
        StringJoiner tableParams = new StringJoiner(", ");
        StringJoiner viewForm = new StringJoiner("\n          ");
        StringBuilder updateForm = new StringBuilder(params.size() * 100);
        StringJoiner dataSourceType = new StringJoiner("\n    ");
        dealTableParam(tableParams, params, paramDescs, paramJavaClass, primarys, selParams, selType);
        dealUpdateForm(updateForm, moduleName, params, paramDescs, paramJavaClass, primarys, insParams, updParams, objectMap);
        dealViewForm(viewForm, dataSourceType, moduleName, params, paramDescs, paramJavaClass, primarys, hasView);
        dealSelControllerDate(selParams, selParamJavaClass, selType, objectMap);
        objectMap.put("antd_tableParams", tableParams.toString());
        objectMap.put("antd_updateForm", updateForm.toString());
        objectMap.put("antd_viewForm", viewForm.toString());
        objectMap.put("antd_dataSourceType", dataSourceType.toString());
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
                exportParamUrl.add("'" + (i == 0 ? "" : "&") + selParams.get(i) + "=' + encodeURIComponent(" + (paramIsDate ? "(typeof (formProps.form?.getFieldValue('" + selParams.get(i) + "')) == 'string') ? formProps.form?.getFieldValue('" + selParams.get(i) + "') : formProps.form?.getFieldValue('" + selParams.get(i) + "').format('YYYY-MM-DD')" : "formProps.form?.getFieldValue('" + selParams.get(i) + "') ? formProps.form?.getFieldValue('" + selParams.get(i) + "') : ''") + ")");
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
            boolean paramIsDigit = (javaClass == JavaClass.Integer || javaClass == JavaClass.Double);
            String valueType = getValueType(javaClass);
            //是查询条件且查询类型为区间查询
            if (position > -1 && selType.get(position) == 2) {
                //非数字和日期类型
                if (!paramIsDate && !paramIsDigit) {
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
                    //是日期类型或数字类型
                    tableParams.add("{\n" +
                            "      title: '" + paramDescs.get(i) + "',\n" +
                            "      dataIndex: '" + params.get(i) + "',\n" +
                            (paramIsDate ? "      valueType: 'date',\n" : "") +
                            "      hideInTable: false,\n" +
                            "      hideInSearch: true\n" +
                            "    }, {\n" +
                            "      title: '" + paramDescs.get(i) + "',\n" +
                            "      dataIndex: '" + params.get(i) + "',\n" +
                            "      valueType: '" + (paramIsDate ? "dateRange" : "digitRange") + "',\n" +
                            "      order: 3,\n" +
                            "      //initialValue: [" + (paramIsDate ? "getMonthFirstDay(), getDate()" : "1, 10") + "],\n" +
                            "      search: {\n" +
                            "        transform: (value) => {\n" +
                            "          return {\n" +
                            "            START_" + params.get(i) + ": value[0],\n" +
                            "            END_" + params.get(i) + ": value[1],\n" +
                            "          };\n" +
                            "        },\n" +
                            "      },\n" +
                            "      hideInTable: true,\n" +
                            "    }");
                }
            } else {
                tableParams.add("{\n" +
                        "      title: '" + paramDescs.get(i) + "',\n" +
                        "      dataIndex: '" + params.get(i) + "',\n" +
                        (paramIsDate ? "      valueType: 'date',\n" : "") +
                        "      width: 150,\n" +
                        "      hideInSearch: " + (position > -1 ? "false" : "true") + ",\n" +
                        "      hideInTable: false\n" +
                        "    }");
            }
        }
    }

    /**
     * 处理控制层查询中日期类型区间查询的处理
     */
    private void dealSelControllerDate(List<String> selParams, List<String> selParamJavaClass, List<Integer> selType, Map<String, Object> objectMap) {
        StringJoiner selControllerDate = new StringJoiner("\n");
        for (int i = 0, length = selParams.size(); i < length; i++) {
            //判断参数类型
            JavaClass javaClass = JavaClass.fromCode(selParamJavaClass.get(i));
            boolean paramIsDate = javaClass == JavaClass.Date;
            //为日期类型且是区间查询
            if (paramIsDate && selType.get(i) == 2) {
                selControllerDate.add("        END_" + selParams.get(i) + " = BaseUtils.addOneDay(END_" + selParams.get(i) + ");");
            }
        }
        objectMap.put("antd_selControllerDate", selControllerDate.toString());
    }

    /**
     * 处理新增修改页的Form代码
     *
     * @param updateForm     StringJoiner
     * @param moduleName     模块名
     * @param params         参数
     * @param paramDescs     参数描述
     * @param paramJavaClass 参数对应java类
     * @param primarys       主键参数
     * @param insParams      新增参数
     * @param updParams      修改参数
     * @param objectMap      代码模版值
     */
    private void dealUpdateForm(StringBuilder updateForm, String moduleName, List<String> params, List<String> paramDescs, List<String> paramJavaClass, List<String> primarys, List<String> insParams, List<String> updParams, Map<String, Object> objectMap) {
        StringJoiner antd_initForm = new StringJoiner("\n      ");
        //主键数量
        int priNum = 0;
        for (int i = 0, length = params.size(); i < length; i++) {
            if (primarys.contains(params.get(i))) {
                priNum++;
                continue;
            }
            boolean paramIsIns = insParams.contains(params.get(i));
            boolean paramIsUpd = updParams.contains(params.get(i));
            JavaClass javaClass = JavaClass.fromCode(paramJavaClass.get(i));
            if ((i + priNum) % 2 == 0) {
                updateForm.append("      <ProForm.Group>\n");
            }
            //非新增非修改时处理
            if (paramIsIns || paramIsUpd) {
                updateForm.append("        <" + getProFormType(javaClass) + "\n" +
                        "          label=\"" + paramDescs.get(i) + "\"\n" +
                        "          width=\"lg\"\n" +
                        "          name=\"" + params.get(i) + "\"\n" +
                        getProFormDisabled(moduleName, paramIsIns, paramIsUpd) +
                        getProFormOther(javaClass, params.get(i), antd_initForm) +
                        "          rules={[\n" +
                        "            {\n" +
                        "              required: false,\n" +
                        "              message: '" + paramDescs.get(i) + "为必填项'\n" +
                        "            }\n" +
                        "          ]}\n" +
                        "        />\n");
            }
            //非新增非修改时对布局的处理
            if (i != length - 1 && !paramIsIns && !paramIsUpd) {
                priNum--;
                continue;
            }
            if (i == length - 1 || (i + priNum) % 2 == 1) {
                updateForm.append("      </ProForm.Group>\n");
            }
        }
        objectMap.put("antd_initForm", antd_initForm.toString());
    }

    /**
     * 处理查看页的代码
     *
     * @param viewForm       StringJoiner
     * @param dataSourceType 查看页DataSourceType
     * @param params         参数
     * @param paramDescs     参数描述
     * @param paramJavaClass 参数对应java类
     * @param primarys       主键参数
     */
    private void dealViewForm(StringJoiner viewForm, StringJoiner dataSourceType, String moduleName, List<String> params, List<String> paramDescs, List<String> paramJavaClass, List<String> primarys, boolean hasView) {
        if (!hasView) {
            return;
        }
        for (int i = 0, length = params.size(); i < length; i++) {
            //为主键不显示
            if (primarys.contains(params.get(i))) {
                continue;
            }
            dataSourceType.add(params.get(i) + "?: string;");
            viewForm.add("<Descriptions.Item label=\"" + paramDescs.get(i) + "\">{" + BaseUtils.toLowerCase4Index(moduleName) + "." + params.get(i) + "}</Descriptions.Item>");
        }
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

    /**
     * 据对应java类获取ProForm类型
     *
     * @param javaClass 参数对应java类
     */
    private String getProFormType(JavaClass javaClass) {
        if (javaClass == JavaClass.String) {
            return "ProFormText";
        } else if (javaClass == JavaClass.Double) {
            return "ProFormDigit";
        } else if (javaClass == JavaClass.Integer) {
            return "ProFormDigit";
        } else if (javaClass == JavaClass.Date) {
            return "ProFormDatePicker";
        }
        return "ProFormText";
    }

    /**
     * 据对应java类返回各自属性
     *
     * @param javaClass 参数对应java类
     */
    private String getProFormOther(JavaClass javaClass, String param, StringJoiner initForm) {
        if (javaClass == JavaClass.String) {
            return "";
        } else if (javaClass == JavaClass.Double) {
            initForm.add("      formObj.setFieldsValue({" + param + ": 0.00});");
            return "          //min={0}\n" +
                    "          //max={99999}\n" +
                    //"          initialValue={0.00}\n" +
                    "          fieldProps={{ precision: 2 }}// 小数位数\n";
        } else if (javaClass == JavaClass.Integer) {
            initForm.add("      formObj.setFieldsValue({" + param + ": 0});");
            return "          //min={0}\n" +
                    "          //max={99999}\n" +
                    //"          initialValue={0}\n" +
                    "          fieldProps={{ precision: 0 }}// 小数位数\n";
        } else if (javaClass == JavaClass.Date) {
            return "";
        }
        return "";
    }

    /**
     * 返回字段的disabled属性
     *
     * @param moduleName 模块名
     * @param paramIsIns 是新增字段
     * @param paramIsUpd 是修改字段
     */
    private String getProFormDisabled(String moduleName, boolean paramIsIns, boolean paramIsUpd) {
        if (paramIsIns && paramIsUpd) {
            //既是新增又是修改字段
            return "";
        } else if (paramIsIns && !paramIsUpd) {
            //是新增不是修改字段
            return "          disabled={" + BaseUtils.toLowerCase4Index(moduleName) + "}\n";
        } else if (!paramIsIns && paramIsUpd) {
            //是修改不是新增字段
            return "          disabled={" + BaseUtils.toLowerCase4Index(moduleName) + "}\n";
        } else {
            //不是新增不是修改字段
            return "          disabled";
        }
    }

    @Override
    protected Map<String, Object> getResult(String driver, Map<String, Object> objectMap, Configuration configuration) {
        String module = objectMap.get("module").toString();
        Map<String, Object> result = super.getResult(driver, objectMap, configuration);
        result.put("index.tsx", getIndex(configuration, objectMap));
        result.put(BaseUtils.toLowerCase4Index(module) + ".ts", getServiceTs(configuration, objectMap));
        result.put("Update" + module + ".tsx", getUpdateTsx(configuration, objectMap));
        result.put("View" + module + ".tsx", getViewTsx(configuration, objectMap));
        result.put("request.tx", getRequestTx(configuration, objectMap));
        return result;
    }

    @Override
    protected List<String> getTabList(Map<String, Object> objectMap) {
        String module = objectMap.get("module").toString();
        boolean hasView = Boolean.parseBoolean(objectMap.get("hasView").toString());
        List<String> list = new ArrayList<>();
        list.add("controller");
        list.add("service");
        list.add("serviceImpl");
        list.add("index.tsx");
        list.add(BaseUtils.toLowerCase4Index(module) + ".ts");
        list.add("Update" + module + ".tsx");
        if (hasView) {
            list.add("View" + module + ".tsx");
        }
        list.add("request.tx");
        list.add("BaseUtils");
        list.add("BaseSqlCriteria");
        return list;
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

    /**
     * 获取view.tsx
     */
    private String getViewTsx(Configuration configuration, Map<String, Object> objectMap) {
        return getFtlModel(configuration, objectMap, "view.ftl");
    }

    /**
     * 获取request.ts
     */
    private String getRequestTx(Configuration configuration, Map<String, Object> objectMap) {
        return getFtlModel(configuration, objectMap, "request.ftl");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AbstractGenCodeModelFactory.register(GenCodeModelType.ANTD, this);
    }
}
