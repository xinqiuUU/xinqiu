/*
 Navicat Premium Data Transfer

 Source Server         : loaclhost
 Source Server Type    : MySQL
 Source Server Version : 80036
 Source Host           : localhost:3306
 Source Schema         : shopping

 Target Server Type    : MySQL
 Target Server Version : 80036
 File Encoding         : 65001

 Date: 15/10/2024 11:56:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_limits
-- ----------------------------
DROP TABLE IF EXISTS `admin_limits`;
CREATE TABLE `admin_limits`  (
  `limits_id` int NOT NULL AUTO_INCREMENT,
  `role` int NOT NULL,
  `menu_id` int NOT NULL,
  PRIMARY KEY (`limits_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_limits
-- ----------------------------
INSERT INTO `admin_limits` VALUES (1, 1, 1);
INSERT INTO `admin_limits` VALUES (2, 0, 2);
INSERT INTO `admin_limits` VALUES (3, 0, 3);
INSERT INTO `admin_limits` VALUES (4, 0, 4);
INSERT INTO `admin_limits` VALUES (5, 0, 5);
INSERT INTO `admin_limits` VALUES (6, 0, 6);
INSERT INTO `admin_limits` VALUES (7, 0, 7);
INSERT INTO `admin_limits` VALUES (8, 0, 8);
INSERT INTO `admin_limits` VALUES (9, 0, 9);
INSERT INTO `admin_limits` VALUES (10, 0, 10);
INSERT INTO `admin_limits` VALUES (11, 0, 11);
INSERT INTO `admin_limits` VALUES (12, 0, 12);
INSERT INTO `admin_limits` VALUES (13, 1, 13);
INSERT INTO `admin_limits` VALUES (14, 0, 14);
INSERT INTO `admin_limits` VALUES (15, 0, 15);
INSERT INTO `admin_limits` VALUES (16, 0, 16);
INSERT INTO `admin_limits` VALUES (17, 0, 17);
INSERT INTO `admin_limits` VALUES (18, 0, 18);
INSERT INTO `admin_limits` VALUES (19, 0, 19);
INSERT INTO `admin_limits` VALUES (20, 0, 20);
INSERT INTO `admin_limits` VALUES (21, 0, 21);
INSERT INTO `admin_limits` VALUES (22, 0, 22);

SET FOREIGN_KEY_CHECKS = 1;
