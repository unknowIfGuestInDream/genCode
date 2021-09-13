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
    RESTFUL("1", "大连模版restful风格"),
    /**
     * 大连常用模板demo模版
     */
    DEMO("2", "大连模版demo风格"),

    /**
     * 大连常用模板EAM模版
     */
    EAM("3", "大连模版EAM风格"),

    /**
     * 大连常用模板EAM3期模版
     */
    EAM3("4", "大连模版EAM3期风格");

    /**
     * 模版编码/名称
     */
    private String code;

    /**
     * 模版描述
     */
    private String desc;

    GenProcedureModelType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    //跟据模板编码获取模板
    public static GenProcedureModelType fromCode(String typeName) {
        for (GenProcedureModelType type : GenProcedureModelType.values()) {
            if (StringUtils.isNotEmpty(typeName) && type.getCode().equals(typeName)) {
                return type;
            }
        }
        return EAM3;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
