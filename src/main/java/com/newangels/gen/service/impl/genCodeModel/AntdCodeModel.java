package com.newangels.gen.service.impl.genCodeModel;

import com.newangels.gen.enums.GenCodeModelType;
import com.newangels.gen.factory.AbstractGenCodeModelFactory;
import com.newangels.gen.service.AbstractGenCodeModel;
import com.newangels.gen.service.NameConventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Eam3期风格代码
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
    protected void dealGetCode(String tableName, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, NameConventService nameConvent, Map<String, Object> objectMap) {

    }

    @Override
    protected void dealSelCode(String tableName, List<String> selParams, List<String> selParamTypes, List<String> selParamDescs, List<Integer> selType, NameConventService nameConvent, Map<String, Object> objectMap) {

    }

    @Override
    protected void dealSelWithPageCode(String tableName, List<String> selParams, List<String> selParamTypes, List<String> selParamDescs, List<Integer> selType, NameConventService nameConvent, Map<String, Object> objectMap) {

    }

    @Override
    protected void dealInsCode(String tableName, List<String> insParams, List<String> insParamTypes, List<String> insParamDescs, NameConventService nameConvent, Map<String, Object> objectMap) {

    }

    @Override
    protected void dealUpdCode(String tableName, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, List<String> updParams, List<String> updParamTypes, List<String> updParamDescs, NameConventService nameConvent, Map<String, Object> objectMap) {

    }

    @Override
    protected void dealSaveCode(String tableName, List<String> insParams, List<String> insParamTypes, List<String> insParamDescs, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, List<String> updParams, List<String> updParamTypes, List<String> updParamDescs, NameConventService nameConvent, Map<String, Object> objectMap) {

    }

    @Override
    protected void dealDelCode(String tableName, List<String> primarys, List<String> primaryTypes, List<String> primaryDesc, NameConventService nameConvent, Map<String, Object> objectMap) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AbstractGenCodeModelFactory.register(GenCodeModelType.ANTD, this);
    }
}
