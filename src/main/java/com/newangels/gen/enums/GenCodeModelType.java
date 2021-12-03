package com.newangels.gen.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 后台代码模板
 *
 * @author: TangLiang
 * @date: 2021/6/20 10:12
 * @since: 1.0
 */
public enum GenCodeModelType {
    /**
     * 大连常用模版
     */
    COMMON("1", "大连常用模版"),

    /**
     * 大连demo模版
     */
    DEMO("2", "大连demo模版"),

    /**
     * 大连ANTD模版
     */
    ANTD("3", "大连ANTD模版");

    /**
     * 模版编码/名称
     */
    private String code;

    /**
     * 模版描述
     */
    private String desc;

    GenCodeModelType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    //跟据模板编码获取模板
    public static GenCodeModelType fromCode(String typeName) {
        for (GenCodeModelType type : GenCodeModelType.values()) {
            if (StringUtils.isNotEmpty(typeName) && type.getCode().equals(typeName)) {
                return type;
            }
        }
        return COMMON;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
