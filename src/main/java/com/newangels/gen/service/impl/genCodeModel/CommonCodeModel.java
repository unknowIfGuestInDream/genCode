package com.newangels.gen.service.impl.genCodeModel;

import com.newangels.gen.base.BaseUtils;
import com.newangels.gen.enums.GenCodeModelType;
import com.newangels.gen.enums.JavaClass;
import com.newangels.gen.factory.AbstractGenCodeModelFactory;
import com.newangels.gen.service.AbstractGenCodeModel;
import com.newangels.gen.util.template.FreeMarkerUtil;
import freemarker.template.Configuration;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 大连常用模版
 *
 * @author: TangLiang
 * @date: 2021/9/8 16:24
 * @since: 1.0
 */
@Service
public class CommonCodeModel extends AbstractGenCodeModel {
    @Override
    protected String getFtlPackageName() {
        return "common";
    }

    @Override
    protected void dealOtherCode(String tableName, String tableDesc, String moduleName, String moduleDesc, String packageName, String author, boolean hasDelBatch, boolean hasExport, List<String> params, List<String> paramDescs, List<String> paramJavaClass, List<String> primarys, List<String> primaryDesc, List<String> primaryJavaClass, List<String> selParams, List<String> selParamDescs, List<String> selParamJavaClass, List<Integer> selType, List<String> insParams, List<String> insParamDescs, List<String> insParamJavaClass, List<String> updParams, List<String> updParamDescs, List<String> updParamJavaClass, Map<String, Object> objectMap) {
        StringJoiner storeParams = new StringJoiner(", ");
        StringJoiner gridParams = new StringJoiner(", ");
        StringJoiner insForm = new StringJoiner(", ");
        StringJoiner updForm = new StringJoiner(", ");
        StringJoiner selForm = new StringJoiner(", ");
        StringJoiner selExtraParams = new StringJoiner(",\n            ");
        dealStoreParam(storeParams, gridParams, params, paramDescs, primarys);
        dealSelFormAndParam(selForm, selExtraParams, moduleName, selParams, selParamDescs, selParamJavaClass, selType);
        dealInsFormPanel(insForm, insParams, insParamDescs, insParamJavaClass, primarys);
        dealUpdFormPanel(updForm, updParams, updParamDescs, updParamJavaClass, primarys);
        dealControllerPageHandler(moduleName, moduleDesc, packageName, objectMap);
        objectMap.put("common_storeParams", storeParams.toString());
        objectMap.put("common_gridParams", gridParams.toString());
        objectMap.put("common_insForm", insForm.toString());
        objectMap.put("common_updForm", updForm.toString());
        objectMap.put("common_selForm", selForm.toString());
        objectMap.put("common_selExtraParams", selExtraParams.toString());
    }

    @Override
    protected void dealExportUrl(List<String> selParams, List<String> selParamJavaClass, List<Integer> selType, boolean hasExport, Map<String, Object> objectMap) {
        if (!hasExport) {
            objectMap.put("common_exportParamUrl", "");
            return;
        }
        StringJoiner exportParamUrl = new StringJoiner(" +\n            ");
        for (int i = 0, length = selParams.size(); i < length; i++) {
            //java类型为date的前台获取值的方法为getSubmitValue
            JavaClass javaClass = JavaClass.fromCode(selParamJavaClass.get(i));
            boolean paramIsDate = javaClass == JavaClass.Date;
            //为区间查询时
            if (selType.get(i) == 2) {
                exportParamUrl.add("'START_" + (i == 0 ? "" : "&") + selParams.get(i) + "=' + encodeURIComponent(Ext.getCmp(\"" + selParams.get(i) + "\")." + (paramIsDate ? "getSubmitValue()" : "getValue()") + ")");
                exportParamUrl.add("'END_" + (i == 0 ? "" : "&") + selParams.get(i) + "=' + encodeURIComponent(Ext.getCmp(\"" + selParams.get(i) + "\")." + (paramIsDate ? "getSubmitValue()" : "getValue()") + ")");
            } else {
                exportParamUrl.add("'" + (i == 0 ? "" : "&") + selParams.get(i) + "=' + encodeURIComponent(Ext.getCmp(\"" + selParams.get(i) + "\")." + (paramIsDate ? "getSubmitValue()" : "getValue()") + ")");
            }
        }
        objectMap.put("common_exportParamUrl", exportParamUrl.toString());
    }

    /**
     * 生成extjs store组件field属性参数和gridPanel的items属性参数
     *
     * @param storeParams StringJoiner
     * @param gridParams  StringJoiner
     * @param params      表所有参数
     * @param paramDescs  参数描述
     * @param primarys    主键参数
     */
    private void dealStoreParam(StringJoiner storeParams, StringJoiner gridParams, List<String> params, List<String> paramDescs, List<String> primarys) {
        for (int i = 0, length = params.size(); i < length; i++) {
            //store参数
            storeParams.add("'" + params.get(i) + "'");
            //主键不显示在gridPanel中
            if (primarys.contains(params.get(i))) {
                continue;
            }
            gridParams.add("{\n" +
                    "                text: '" + paramDescs.get(i) + "',\n" +
                    "                dataIndex: '" + params.get(i) + "',\n" +
                    "                flex: 1,\n" +
                    "                minWidth: 150\n" +
                    "            }");
        }
    }

    /**
     * 返回extjs管理页formPanel items值和store查询条件extraParams属性值
     *
     * @param selForm           管理页formPanel的items值
     * @param selExtraParams    管理页store的extraParams值
     * @param moduleName        模块名
     * @param selParams         参数
     * @param selParamDescs     字段描述
     * @param selParamJavaClass 参数对应类对象
     * @param selType           查询类型(0精确/1模糊/2区间查询)
     */
    private void dealSelFormAndParam(StringJoiner selForm, StringJoiner selExtraParams, String moduleName, List<String> selParams, List<String> selParamDescs, List<String> selParamJavaClass, List<Integer> selType) {
        for (int i = 0, length = selParams.size(); i < length; i++) {
            //java类型为date的前台获取值的方法为getSubmitValue
            JavaClass javaClass = JavaClass.fromCode(selParamJavaClass.get(i));
            boolean paramIsDate = javaClass == JavaClass.Date;
            //为区间查询
            if (selType.get(i) == 2) {
                selExtraParams.add("'START_" + selParams.get(i) + "': Ext.getCmp('START_" + selParams.get(i) + "')." + (paramIsDate ? "getSubmitValue()" : "getValue()"));
                selExtraParams.add("'END_" + selParams.get(i) + "': Ext.getCmp('END_" + selParams.get(i) + "')." + (paramIsDate ? "getSubmitValue()" : "getValue()"));
                selForm.add("{\n" +
                        "                xtype: '" + getFormXtype(javaClass) + "',\n" +
                        "                id: 'START_" + selParams.get(i) + "',\n" +
                        "                fieldLabel: '开始" + selParamDescs.get(i) + "',\n" +
                        "                listeners: {\n" +
                        "                    specialKey: function (field, e) {\n" +
                        "                        if (e.getKey() === Ext.EventObject.ENTER) {\n" +
                        "                            _select" + moduleName + "();\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    change: function (combo, records) {\n" +
                        "                        Ext.getCmp('END_" + selParams.get(i) + "').setMinValue(Ext.getCmp('START_" + selParams.get(i) + "').getValue());" +
                        "                    },\n" +
                        "                }\n" +
                        "            }");
                selForm.add("{\n" +
                        "                xtype: '" + getFormXtype(javaClass) + "',\n" +
                        "                id: 'END_" + selParams.get(i) + "',\n" +
                        "                fieldLabel: '结束" + selParamDescs.get(i) + "',\n" +
                        "                listeners: {\n" +
                        "                    specialKey: function (field, e) {\n" +
                        "                        if (e.getKey() === Ext.EventObject.ENTER) {\n" +
                        "                            _select" + moduleName + "();\n" +
                        "                        }\n" +
                        "                    },\n" +
                        "                    change: function (combo, records) {\n" +
                        "                        Ext.getCmp('START_" + selParams.get(i) + "').setMaxValue(Ext.getCmp('END_" + selParams.get(i) + "').getValue());" +
                        "                    },\n" +
                        "                }\n" +
                        "            }");
            } else {
                selExtraParams.add("'" + selParams.get(i) + "': Ext.getCmp('" + selParams.get(i) + "')." + (paramIsDate ? "getSubmitValue()" : "getValue()"));
                selForm.add("{\n" +
                        "                xtype: '" + getFormXtype(javaClass) + "',\n" +
                        "                id: '" + selParams.get(i) + "',\n" +
                        "                fieldLabel: '" + selParamDescs.get(i) + "',\n" +
                        "                listeners: {\n" +
                        "                    specialKey: function (field, e) {\n" +
                        "                        if (e.getKey() === Ext.EventObject.ENTER) {\n" +
                        "                            _select" + moduleName + "();\n" +
                        "                        }\n" +
                        "                    }\n" +
                        "                }\n" +
                        "            }");
            }
        }
    }

    /**
     * 生成extjs新增页的formPanel items属性参数
     *
     * @param insForm           StringJoiner
     * @param insParams         新增参数
     * @param insParamDescs     新增参数描述
     * @param insParamJavaClass 新增参数对应java类型
     * @param primarys          主键参数
     */
    private void dealInsFormPanel(StringJoiner insForm, List<String> insParams, List<String> insParamDescs, List<String> insParamJavaClass, List<String> primarys) {
        for (int i = 0, length = insParams.size(); i < length; i++) {
            //主键不显示在formPanel中
            if (primarys.contains(insParams.get(i))) {
                continue;
            }
            insForm.add("{\n" +
                    getExtForm(insParamJavaClass.get(i)) +
                    "                name: '" + insParams.get(i) + "',\n" +
                    "                fieldLabel: '" + insParamDescs.get(i) + "',\n" +
                    "                allowBlank: true\n" +
                    "            }");
        }
    }

    /**
     * 生成extjs修改页的formPanel items属性参数
     *
     * @param updForm           StringJoiner
     * @param updParams         修改参数
     * @param updParamDescs     修改参数描述
     * @param updParamJavaClass 修改参数对应java类型
     * @param primarys          主键参数
     */
    private void dealUpdFormPanel(StringJoiner updForm, List<String> updParams, List<String> updParamDescs, List<String> updParamJavaClass, List<String> primarys) {
        //修改页主键在formPanel中隐藏，由于前台设置了为主键时不会成为修改字段，在这里加上隐藏主键字段
        for (String primary : primarys) {
            updForm.add("{\n" +
                    "                xtype: 'hidden',\n" +
                    "                name: '" + primary + "'\n" +
                    "            }");
        }
        for (int i = 0, length = updParams.size(); i < length; i++) {
            updForm.add("{\n" +
                    getExtForm(updParamJavaClass.get(i)) +
                    "                name: '" + updParams.get(i) + "',\n" +
                    "                fieldLabel: '" + updParamDescs.get(i) + "',\n" +
                    "                allowBlank: true\n" +
                    "            }");
        }
    }

    /**
     * 根据java类型返回extjs的新增修改formpanel
     *
     * @param paramJavaClass java类型
     */
    private String getExtForm(String paramJavaClass) {
        JavaClass javaClass = JavaClass.fromCode(paramJavaClass);
        if (javaClass == JavaClass.String) {
            return "                xtype: '" + getFormXtype(javaClass) + "',\n";
        } else if (javaClass == JavaClass.Double) {
            return "                xtype: '" + getFormXtype(javaClass) + "',\n" +
                    "                allowDecimals: true,\n" +
                    "                decimalPrecision: 2,\n";
        } else if (javaClass == JavaClass.Integer) {
            return "                xtype: '" + getFormXtype(javaClass) + "',\n" +
                    "                allowDecimals: false,\n";
        } else if (javaClass == JavaClass.Date) {
            return "                xtype: '" + getFormXtype(javaClass) + "',\n" +
                    "                format: 'Y-m-d',\n" +
                    "                submitFormat: 'Y-m-d',\n";
        }
        return "                xtype: '" + getFormXtype(javaClass) + "',\n";
    }

    /**
     * 根据java类型返回xtype的值
     *
     * @param javaClass java类型
     */
    private String getFormXtype(JavaClass javaClass) {
        if (javaClass == JavaClass.String) {
            return "textfield";
        } else if (javaClass == JavaClass.Double) {
            return "numberfield";
        } else if (javaClass == JavaClass.Integer) {
            return "numberfield";
        } else if (javaClass == JavaClass.Date) {
            return "datefield";
        }
        return "textfield";
    }

    /**
     * 生成控制层跳页路径
     *
     * @param module      模块名称
     * @param moduleDesc  模块描述
     * @param packageName 包名
     * @param objectMap   参数容器
     */
    private void dealControllerPageHandler(String module, String moduleDesc, String packageName, Map<String, Object> objectMap) {
        String lowModule = BaseUtils.toLowerCase4Index(module);
        String frontPackage = packageName.substring(packageName.lastIndexOf(".") + 1);
        String pageHander = "\n    /**\n" +
                "     * " + moduleDesc + "管理页\n" +
                "     */\n" +
                "    @GetMapping(\"/manage" + module + "\")\n" +
                "    public ModelAndView manage" + module + "() {\n" +
                "        return new ModelAndView(\"pages/" + frontPackage + "/" + lowModule + "/manage" + module + "\");\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * " + moduleDesc + "新增页\n" +
                "     */\n" +
                "    @GetMapping(\"/preInsert" + module + "\")\n" +
                "    public ModelAndView preInsert" + module + "() {\n" +
                "        return new ModelAndView(\"pages/" + frontPackage + "/" + lowModule + "/preInsert" + module + "\");\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * " + moduleDesc + "修改页\n" +
                "     */\n" +
                "    @GetMapping(\"/preUpdate" + module + "\")\n" +
                "    public ModelAndView preUpdate" + module + "() {\n" +
                "        return new ModelAndView(\"pages/" + frontPackage + "/" + lowModule + "/preUpdate" + module + "\");\n" +
                "    }\n";
        objectMap.put("pageHander", pageHander);
    }

    @Override
    protected Map<String, Object> getResult(String driver, Map<String, Object> objectMap, Configuration configuration) {
        String module = objectMap.get("module").toString();
        Map<String, Object> result = super.getResult(driver, objectMap, configuration);
        result.put("base.js", FreeMarkerUtil.getTemplateContent(configuration, objectMap, "common/basejs.ftl"));
        result.put("manage" + module + ".html", getExtjsManage(configuration, objectMap));
        result.put("preInsert" + module + ".html", getExtjsPreInsert(configuration, objectMap));
        result.put("preUpdate" + module + ".html", getExtjsPreUpdate(configuration, objectMap));
        return result;
    }

    @Override
    protected List<String> getTabList(Map<String, Object> objectMap) {
        String module = objectMap.get("module").toString();
        return new ArrayList<>(Arrays.asList("controller", "service", "serviceImpl", "manage" + module + ".html", "preInsert" + module + ".html", "preUpdate" + module + ".html", "base.js", "BaseUtils", "BaseSqlCriteria"));
    }

    /**
     * 获取管理页
     */
    private String getExtjsManage(Configuration configuration, Map<String, Object> objectMap) {
        return getFtlModel(configuration, objectMap, "manage.ftl");
    }

    /**
     * 获取新增页
     */
    private String getExtjsPreInsert(Configuration configuration, Map<String, Object> objectMap) {
        return getFtlModel(configuration, objectMap, "preInsert.ftl");
    }

    /**
     * 获取修改页
     */
    private String getExtjsPreUpdate(Configuration configuration, Map<String, Object> objectMap) {
        return getFtlModel(configuration, objectMap, "preUpdate.ftl");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AbstractGenCodeModelFactory.register(GenCodeModelType.COMMON, this);
    }
}
