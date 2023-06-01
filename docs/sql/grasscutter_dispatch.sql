/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50739 (5.7.39)
 Source Host           : localhost:3306
 Source Schema         : grasscutter_dispatch

 Target Server Type    : MySQL
 Target Server Version : 50739 (5.7.39)
 File Encoding         : 65001

 Date: 01/06/2023 18:34:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dispatch_user
-- ----------------------------
DROP TABLE IF EXISTS `dispatch_user`;
CREATE TABLE `dispatch_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'uid',
  `create_player_id` int(11) DEFAULT '0' COMMENT '指定首次进入游戏服务器创建的游戏id',
  `password` varchar(100) DEFAULT '' COMMENT '密码',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phone` varchar(11) DEFAULT '' COMMENT '手机号码',
  `token` text COMMENT '游戏服务器验证账户信息token',
  `session_key` text COMMENT '自动登录会话key',
  `locale` varchar(80) DEFAULT NULL COMMENT '游戏服务器国际化',
  `ban_start_time` int(11) DEFAULT '0' COMMENT '账户开始禁用时间',
  `ban_end_time` int(11) DEFAULT '0' COMMENT '账户结束禁用时间',
  `ban` char(1) DEFAULT '0' COMMENT '禁用账户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

-- ----------------------------
-- Records of dispatch_user
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
