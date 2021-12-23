/*
Navicat MySQL Data Transfer

Source Server         : 42.192.10.174
Source Server Version : 50724
Source Host           : 42.192.10.174:3306
Source Database       : gen

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2021-07-14 16:43:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for database_info
-- ----------------------------
DROP TABLE IF EXISTS `database_info`;
CREATE TABLE `database_info` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(20) NOT NULL COMMENT '数据源名称',
  `URL` varchar(255) NOT NULL COMMENT '路径',
  `DRIVER` varchar(80) NOT NULL COMMENT '驱动名',
  `USERNAME` varchar(20) NOT NULL COMMENT '用户名',
  `PASSWORD` varchar(20) NOT NULL COMMENT '密码',
  `UPDATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  `CREATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;
