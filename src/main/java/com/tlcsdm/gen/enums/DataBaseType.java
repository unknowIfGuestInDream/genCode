package com.tlcsdm.gen.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 根据传参驱动判断连接数据库
 *
 * @author: TangLiang
 * @date: 2021/6/19 8:46
 * @since: 1.0
 */
public enum DataBaseType {

	/**
	 * oracle驱动
	 */
	ORACLE("oracle.jdbc.OracleDriver"),
	/**
	 * mysql5驱动
	 */
	MYSQL("com.mysql.jdbc.Driver"),
	/**
	 * mysql8驱动
	 */
	MYSQL8("com.mysql.cj.jdbc.Driver"),
	/**
	 * mariadb驱动
	 */
	MARIADB("org.mariadb.jdbc.Driver"),
	/**
	 * sqlserver驱动
	 */
	SQLSERVER("com.microsoft.sqlserver.jdbc.SQLServerDriver"),
	/**
	 * 未知驱动
	 */
	UNKNOW("UNKNOW");

	private String typeName;

	DataBaseType(String typeName) {
		this.typeName = typeName;
	}

	// 跟据后缀获取文件类型枚举变量
	public static DataBaseType fromTypeName(String typeName) {
		for (DataBaseType type : DataBaseType.values()) {
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
