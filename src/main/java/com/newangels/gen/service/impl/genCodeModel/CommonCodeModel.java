package com.newangels.gen.service.impl.genCodeModel;

import com.newangels.gen.enums.GenCodeModelType;
import com.newangels.gen.factory.AbstractGenCodeModelFactory;
import com.newangels.gen.service.AbstractGenCodeModel;
import com.newangels.gen.util.template.FreeMarkerUtil;
import freemarker.template.Configuration;
import org.springframework.stereotype.Service;

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
    protected Map<String, Object> getResult(String driver, Map<String, Object> objectMap, Configuration configuration) {
        Map<String, Object> result = super.getResult(driver, objectMap, configuration);
        result.put("base.js", FreeMarkerUtil.getTemplateContent(configuration, objectMap, "common/basejs.ftl"));
        return result;
    }

    @Override
    protected List<String> getTabList() {
        List<String> list = super.getTabList();
        list.add("base.js");
        return list;
    }
}
