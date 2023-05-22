/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50739 (5.7.39)
 Source Host           : localhost:3306
 Source Schema         : grasscutter_auth

 Target Server Type    : MySQL
 Target Server Version : 50739 (5.7.39)
 File Encoding         : 65001

 Date: 22/05/2023 13:01:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for auth_user
-- ----------------------------
DROP TABLE IF EXISTS `auth_user`;
CREATE TABLE `auth_user` (
  `id` varchar(32) NOT NULL,
  `user_name` varchar(30) DEFAULT NULL COMMENT '用户账号',
  `nick_name` varchar(30) DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(100) DEFAULT '' COMMENT '密码',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phone` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像地址',
  `status` char(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `login_ip` varchar(50) DEFAULT '' COMMENT '最后登陆IP',
  `login_time` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `create_by_id` varchar(32) DEFAULT NULL COMMENT '创建id',
  `create_by_name` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by_id` varchar(32) DEFAULT NULL COMMENT '更新id',
  `update_by_name` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

-- ----------------------------
-- Records of auth_user
-- ----------------------------
BEGIN;
INSERT INTO `auth_user` (`id`, `user_name`, `nick_name`, `password`, `email`, `phone`, `sex`, `avatar`, `status`, `login_ip`, `login_time`, `create_by_id`, `create_by_name`, `create_time`, `update_by_id`, `update_by_name`, `update_time`, `remarks`, `del_flag`) VALUES ('1510539584287346688', 'admin', '管理员', '$2a$10$jvVGvS6QOtD.x3UwuZRSgepL.zKul6NRg2kVnRVDUdeN2FErIQgNy', '111@qq.com', '15611111111', '0', '', '0', '', NULL, '1510539584287346688', 'admin', '2020-03-13 20:56:44', '1510539584287346688', 'admin', '2022-08-26 15:26:17', '全栈工程师', '0');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
