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

 Date: 15/10/2024 11:57:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_coupon
-- ----------------------------
DROP TABLE IF EXISTS `user_coupon`;
CREATE TABLE `user_coupon`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` int NULL DEFAULT NULL,
  `cid` int NULL DEFAULT NULL,
  `get_date` timestamp NULL DEFAULT NULL,
  `status` int NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_coupon
-- ----------------------------
INSERT INTO `user_coupon` VALUES (1, 14, 1, '2024-10-10 12:50:14', 0);
INSERT INTO `user_coupon` VALUES (2, 112, 2, NULL, 1);
INSERT INTO `user_coupon` VALUES (3, 40, 2, NULL, 1);
INSERT INTO `user_coupon` VALUES (4, 466, 4, '2024-10-12 05:28:27', 1);
INSERT INTO `user_coupon` VALUES (5, 715, 4, '2024-10-12 05:28:42', 1);
INSERT INTO `user_coupon` VALUES (6, 228, 4, '2024-10-12 05:28:43', 1);
INSERT INTO `user_coupon` VALUES (7, 996, 4, '2024-10-12 05:28:43', 1);
INSERT INTO `user_coupon` VALUES (8, 547, 4, '2024-10-12 05:28:43', 1);
INSERT INTO `user_coupon` VALUES (9, 83, 5, '2024-10-12 05:35:31', 1);
INSERT INTO `user_coupon` VALUES (10, 243, 5, '2024-10-12 05:35:52', 1);
INSERT INTO `user_coupon` VALUES (11, 553, 5, '2024-10-12 05:35:53', 1);
INSERT INTO `user_coupon` VALUES (12, 223, 5, '2024-10-12 05:35:53', 1);
INSERT INTO `user_coupon` VALUES (13, 686, 5, '2024-10-12 05:35:53', 1);
INSERT INTO `user_coupon` VALUES (14, 14, 6, '2024-10-12 05:46:58', 3);
INSERT INTO `user_coupon` VALUES (15, 14, 7, '2024-10-12 07:35:02', 0);
INSERT INTO `user_coupon` VALUES (16, 14, 8, '2024-10-12 07:54:45', 3);
INSERT INTO `user_coupon` VALUES (17, 14, 9, '2024-10-12 15:29:01', 3);
INSERT INTO `user_coupon` VALUES (18, 14, 10, '2024-10-12 15:47:34', 3);
INSERT INTO `user_coupon` VALUES (19, 14, 13, '2024-10-13 14:59:08', 3);
INSERT INTO `user_coupon` VALUES (20, 14, 16, '2024-10-14 15:42:14', 3);

SET FOREIGN_KEY_CHECKS = 1;
