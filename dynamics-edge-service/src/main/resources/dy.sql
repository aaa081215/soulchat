/*
 Navicat Premium Data Transfer

 Source Server         : 106.14.13.61
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : 106.14.13.61:3306
 Source Schema         : soul_dynamics

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 17/05/2019 13:44:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dy
-- ----------------------------
DROP TABLE IF EXISTS `dy`;
CREATE TABLE `dy`  (
  `userid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `parentid` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `imgs` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `uptime` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dy
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
