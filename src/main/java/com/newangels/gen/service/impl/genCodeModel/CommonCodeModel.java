package com.newangels.gen.service.impl.genCodeModel;

import com.newangels.gen.enums.GenCodeModelType;
import com.newangels.gen.factory.AbstractGenCodeModelFactory;
import com.newangels.gen.service.AbstractGenCodeModel;
import com.newangels.gen.util.template.FreeMarkerUtil;
import freemarker.template.Configuration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    public void afterPropertiesSet() throws Exception {
        AbstractGenCodeModelFactory.register(GenCodeModelType.COMMON, this);
    }

    @Override
    protected void dealOtherCode(String tableName, String tableDesc, String moduleName, String moduleDesc, String packageName, String author, boolean hasDelBatch, List<String> primarys, List<String> primaryDesc, List<String> primaryJavaClass, List<String> selParams, List<String> selParamDescs, List<String> selParamJavaClass, List<Integer> selType, List<String> insParams, List<String> insParamDescs, List<String> insParamJavaClass, List<String> updParams, List<String> updParamDescs, List<String> updParamJavaClass, Map<String, Object> objectMap) {
        //TODO store  formPanel  gridpanel等
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
}
