package com.newangels.gen.enums;

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
     * 大连常用模板restful风格向
     */
    RESTFUL("1"),
    /**
     * 大连常用模板demo模版
     */
    DEMO("2"),

    /**
     * 大连常用模板EAM模版
     */
    EAM("3"),

    /**
     * 大连常用模板EAM3期模版
     */
    EAM3("4"),

    /**
     * 未知模版
     */
    @Deprecated
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
        return EAM3;
    }

    public String getTypeName() {
        return this.typeName;
    }
}
