package com.tlcsdm.gen.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * java类
 *
 * @author: TangLiang
 * @date: 2021/6/20 10:12
 * @since: 1.0
 */
public enum JavaClass {

	/**
	 * varchar
	 */
	String("String"),
	/**
	 * int
	 */
	Integer("Integer"),
	/**
	 * number
	 */
	Double("Double"),
	/**
	 * 日期
	 */
	Date("Date"),
	/**
	 * Blob
	 */
	InputStream("InputStream");

	private String code;

	JavaClass(String code) {
		this.code = code;
	}

	// 跟据规范编码获取规范
	public static JavaClass fromCode(String typeName) {
		for (JavaClass type : JavaClass.values()) {
			if (StringUtils.isNotEmpty(typeName) && type.getCode().equals(typeName)) {
				return type;
			}
		}
		return String;
	}

	public String getCode() {
		return this.code;
	}

}
