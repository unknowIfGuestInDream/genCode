package com.newangels.gen.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 存储过程模板
 *
 * @author: TangLiang
 * @date: 2021/6/20 10:12
 * @since: 1.0
 */
public enum GenProcedureModelType {
    /**
     * 常用模板
     */
    RESTFUL("1"),
    /**
     * 未知模版
     */
    UNKNOW("UNKNOW");

    private String typeName;

    GenProcedureModelType(String typeName) {
        this.typeName = typeName;
    }

    //跟据后缀获取文件类型枚举变量
    public static GenProcedureModelType fromTypeName(String typeName) {
        for (GenProcedureModelType type : GenProcedureModelType.values()) {
            if (StringUtils.isNotEmpty(typeName) && type.getTypeName().equals(typeName)) {
                return type;
            }
        }
        return UNKNOW;
    }

    public String getTypeName() {
        return this.typeName;
    }
}
