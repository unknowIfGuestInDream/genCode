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
     * 常用模板
     */
    COMMON("1"),
    /**
     * 未知模版
     */
    UNKNOW("UNKNOW");

    private String typeName;

    NameConventType(String typeName) {
        this.typeName = typeName;
    }

    //跟据后缀获取文件类型枚举变量
    public static NameConventType fromTypeName(String typeName) {
        for (NameConventType type : NameConventType.values()) {
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