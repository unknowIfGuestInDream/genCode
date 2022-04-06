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
    COMMON("1", "大连常用规范", 1),

    /**
     * EAM3期常用规范
     */
    EAM3("2", "EAM3期规范", 1);

    private String code;
    private String desc;
    /**
     * 状态 1启用 0禁用
     */
    private int status;

    NameConventType(String code, String desc, int status) {
        this.code = code;
        this.desc = desc;
        this.status = status;
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

    public int getStatus() {
        return this.status;
    }
}
