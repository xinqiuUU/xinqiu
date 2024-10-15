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

 Date: 15/10/2024 11:56:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bottompanel
-- ----------------------------
DROP TABLE IF EXISTS `bottompanel`;
CREATE TABLE `bottompanel`  (
  `bottom_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT 1,
  PRIMARY KEY (`bottom_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bottompanel
-- ----------------------------
INSERT INTO `bottompanel` VALUES (1, '客户服务', '帮助 & 联系', 1);
INSERT INTO `bottompanel` VALUES (2, '客户服务', '返回 & 退款', 1);
INSERT INTO `bottompanel` VALUES (3, '客户服务', '在线商店', 1);
INSERT INTO `bottompanel` VALUES (4, '客户服务', '条款 & 条件', 1);
INSERT INTO `bottompanel` VALUES (5, '公司', '可用的服务', 1);
INSERT INTO `bottompanel` VALUES (6, '公司', '最新的文章', 1);
INSERT INTO `bottompanel` VALUES (7, '社交媒体', 'QQ: 2921310632', 1);
INSERT INTO `bottompanel` VALUES (8, '社交媒体', '邮箱: 2921310632@qq.com', 1);
INSERT INTO `bottompanel` VALUES (9, '社交媒体', '电话号码: 18569380564', 1);
INSERT INTO `bottompanel` VALUES (10, '公司', '关于我们', 1);
INSERT INTO `bottompanel` VALUES (11, '社交媒体', 'QQ:6666666', 1);
INSERT INTO `bottompanel` VALUES (12, '公司', '最新消息', 1);

SET FOREIGN_KEY_CHECKS = 1;
