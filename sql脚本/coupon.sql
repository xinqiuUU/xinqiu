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

 Date: 15/10/2024 11:56:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for coupon
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon`  (
  `cid` int NOT NULL AUTO_INCREMENT,
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `start_date` timestamp NULL DEFAULT NULL,
  `end_date` timestamp NULL DEFAULT NULL,
  `quantity` int NULL DEFAULT 0,
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coupon
-- ----------------------------
INSERT INTO `coupon` VALUES (1, 'xinqiu', '折扣券', '7折', '2024-10-10 00:00:00', '2024-10-10 23:59:59', 100);
INSERT INTO `coupon` VALUES (2, 'one', '折扣券', '1折', '2024-10-10 00:00:00', '2024-10-11 00:00:00', 2);
INSERT INTO `coupon` VALUES (3, 'XINQIU', '折扣卷', '4折', '2024-10-11 16:00:00', '2024-10-12 15:59:59', 0);
INSERT INTO `coupon` VALUES (4, 'XINQIU', '折扣卷', '5折', '2024-10-11 16:00:00', '2024-10-12 15:59:59', 5);
INSERT INTO `coupon` VALUES (5, 'XINQIU', '折扣卷', '4折', '2024-10-11 16:00:00', '2024-10-12 15:59:59', 5);
INSERT INTO `coupon` VALUES (6, 'XINQIU', '折扣卷', '4折', '2024-10-11 16:00:00', '2024-10-12 15:59:59', 5);
INSERT INTO `coupon` VALUES (7, 'XINQIU', '折扣卷', '7折', '2024-10-11 16:00:00', '2024-10-12 15:59:59', 5);
INSERT INTO `coupon` VALUES (8, 'XINQIU', '折扣卷', '4折', '2024-10-11 16:00:00', '2024-10-12 15:59:59', 5);
INSERT INTO `coupon` VALUES (9, 'XINQIU', '折扣卷', '5折', '2024-10-11 16:00:00', '2024-10-12 15:59:59', 5);
INSERT INTO `coupon` VALUES (10, 'XINQIU', '折扣卷', '3折', '2024-10-12 00:00:00', '2024-10-12 23:59:59', 5);
INSERT INTO `coupon` VALUES (11, 'XINQIU', '折扣卷', '6折', '2024-10-13 00:00:00', '2024-10-13 23:59:59', 5);
INSERT INTO `coupon` VALUES (12, 'XINQIU', '折扣券', '7折', '2024-10-13 00:00:00', '2024-10-13 23:59:59', 5);
INSERT INTO `coupon` VALUES (13, 'XINQIU', '折扣券', '6折', '2024-10-13 00:00:00', '2024-10-13 23:59:59', 5);
INSERT INTO `coupon` VALUES (14, 'XINQIU', '折扣券', '7折', '2024-10-13 00:00:00', '2024-10-13 23:59:59', 5);
INSERT INTO `coupon` VALUES (15, 'XINQIU', '折扣券', '3折', '2024-10-13 00:00:00', '2024-10-13 23:59:59', 5);
INSERT INTO `coupon` VALUES (16, 'XINQIU', '折扣券', '7折', '2024-10-14 00:00:00', '2024-10-14 23:59:59', 5);

SET FOREIGN_KEY_CHECKS = 1;
