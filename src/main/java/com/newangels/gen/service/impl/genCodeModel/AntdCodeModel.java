package com.newangels.gen.service.impl.genCodeModel;

import com.newangels.gen.enums.GenCodeModelType;
import com.newangels.gen.factory.AbstractGenCodeModelFactory;
import com.newangels.gen.service.AbstractGenCodeModel;
import com.newangels.gen.service.NameConventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    protected void dealOtherCode(String tableName, String tableDesc, String moduleName, String moduleDesc, String packageName, String author, boolean hasDelBatch, List<String> params, List<String> paramDescs, List<String> paramJavaClass, List<String> primarys, List<String> primaryDesc, List<String> primaryJavaClass, List<String> selParams, List<String> selParamDescs, List<String> selParamJavaClass, List<Integer> selType, List<String> insParams, List<String> insParamDescs, List<String> insParamJavaClass, List<String> updParams, List<String> updParamDescs, List<String> updParamJavaClass, Map<String, Object> objectMap) {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AbstractGenCodeModelFactory.register(GenCodeModelType.ANTD, this);
    }
}
