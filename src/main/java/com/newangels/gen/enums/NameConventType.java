package com.newangels.gen.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 命名规范
 *
 * @author: TangLiang
 * @date: 2021/6/20 10:12
 * @since: 1.0
 */
public enum NameConventType {
    /**
     * 常用规范
     */
    COMMON("1", "大连常用规范"),

    /**
     * EAM3期常用规范
     */
    EAM3("2", "EAM3期规范");

    private String code;
    private String desc;

    NameConventType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    //跟据规范编码获取规范
    public static NameConventType fromCode(String typeName) {
        for (NameConventType type : NameConventType.values()) {
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
