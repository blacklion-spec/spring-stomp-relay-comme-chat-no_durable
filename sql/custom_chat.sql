/*
Navicat MySQL Data Transfer

Source Server         : 本地连接
Source Server Version : 50731
Source Host           : 127.0.0.1:3306
Source Database       : custom_chat

Target Server Type    : MYSQL
Target Server Version : 50731
File Encoding         : 65001

Date: 2022-08-14 22:30:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `USER_KEY` varchar(255) NOT NULL COMMENT 'USER的全局唯一识别号',
  `USER_NAME` varchar(10) NOT NULL COMMENT '用户名',
  `PASSWORD` varchar(255) NOT NULL,
  `CREATE_TIME` datetime NOT NULL,
  `IS_BAN` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`USER_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('4fb33c82-2fcd-472a-9c23-9b9f76e5778c', 'marui', '123456', '2022-08-13 00:38:13', '\0');
INSERT INTO `user` VALUES ('e64c433b-9f8b-4f9d-a274-0c48a89fd254', 'pxx', '123456', '2022-08-13 01:04:36', '\0');
