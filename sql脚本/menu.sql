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

 Date: 15/10/2024 11:56:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `menu_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `href` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `fontFamily` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `icon` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `spread` tinyint(1) NULL DEFAULT NULL,
  `parent_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '测试控制台', 'pages/console.html', 'ok-icon', '&#xe665;', 1, 0);
INSERT INTO `menu` VALUES (2, '控制台', 'pages/console1.html', 'ok-icon', '&#xe665;', 1, 0);
INSERT INTO `menu` VALUES (3, '用户管理', '', 'ok-icon', '&#xe66f;', 0, 0);
INSERT INTO `menu` VALUES (4, '用户列表', 'pages/member/user-list.html', 'layui-icon', '&#xe62e;', 0, 3);
INSERT INTO `menu` VALUES (5, '商品管理', '', 'ok-icon', '&#xe68a;', 0, 0);
INSERT INTO `menu` VALUES (6, '商品列表', 'pages/often/product.html', 'ok-icon', '&#xe62e;', 0, 5);
INSERT INTO `menu` VALUES (7, '用户评价管理', 'pages/often/product-appraise-list.html', 'ok-icon', '&#xe62e;', 0, 5);
INSERT INTO `menu` VALUES (8, '商品评分排行', 'pages/often/product-grade-list.html', 'ok-icon', '&#xe62e;', 0, 5);
INSERT INTO `menu` VALUES (9, '订单管理', '', 'ok-icon', '&#xe68a;', 0, 0);
INSERT INTO `menu` VALUES (10, '待支付订单管理', 'pages/member/order-list-unpaid.html', 'ok-icon', '&#xe62e;', 0, 9);
INSERT INTO `menu` VALUES (11, '订单发货管理', 'pages/member/order-list-delivery.html', 'ok-icon', '&#xe62e;', 0, 9);
INSERT INTO `menu` VALUES (12, '订单退款审核', 'pages/member/order-list-check.html', 'ok-icon', '&#xe62e;', 0, 9);
INSERT INTO `menu` VALUES (13, '测试一个测试菜单项目', '', 'ok-icon', '&#xe62e;', 0, 0);
INSERT INTO `menu` VALUES (14, '报表管理', '', 'ok-icon', '&#xe62c;', 0, 0);
INSERT INTO `menu` VALUES (15, '销量统计', '', 'ok-icon-shuju1', 'ok-icon-shuju1', 0, 14);
INSERT INTO `menu` VALUES (16, '营业额统计', '', 'ok-icon', 'ok-icon-shuju1', 0, 14);
INSERT INTO `menu` VALUES (17, '销量统计图', 'pages/reportforms/sales_statistics.html', 'ok-icon', '&#xe62e;', 0, 15);
INSERT INTO `menu` VALUES (18, '营业额统计图', 'pages/chart/chart1.html', 'ok-icon', '&#xe62e;', 0, 16);
INSERT INTO `menu` VALUES (19, '系统管理', '', 'ok-icon', '&#xe68a;', 0, 0);
INSERT INTO `menu` VALUES (20, '底部栏管理', 'pages/system/bottom.html', 'ok-icon', '&#xe62e;', 0, 19);
INSERT INTO `menu` VALUES (21, '屏蔽词', 'pages/system/shield.html', 'ok-icon', '&#xe62e;', 0, 19);
INSERT INTO `menu` VALUES (22, '客服中心', 'pages/chat/chat.html', 'ok-icon', '&#xe665;', 1, 0);

SET FOREIGN_KEY_CHECKS = 1;
