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

 Date: 12/10/2024 16:32:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `uid` int NULL DEFAULT NULL,
  `ordertime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `totalprice` decimal(10, 2) NULL DEFAULT NULL,
  `status` int NOT NULL,
  `refund_reasons` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `addressee` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `emailee` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `address` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `postage` decimal(10, 2) NULL DEFAULT NULL,
  `delivery_company` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `idx_orders_ordertime`(`ordertime` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 87 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
ALTER TABLE `orders`ADD COLUMN `id` INT;
ALTER TABLE `orders`DROP COLUMN `id`;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, 5, '2024-09-23 21:11:53', 73342.25, 2, NULL, 'e', 'e@qq.com', '19911110005', '甘肃省兰州市城关区0000', 10.65, '邮政');
INSERT INTO `orders` VALUES (2, 1, '2024-06-28 15:15:24', 5443.00, -1, '找到更好的价格', 'a', 'a@qq.com', '19911110001', '湖南省衡阳市珠晖区酃湖乡顺枫公寓B1号湖南工学院顺枫公寓', 72.37, '顺丰');
INSERT INTO `orders` VALUES (3, 1, '2024-06-28 15:23:39', 10999.00, 3, NULL, 'a', 'a@qq.com', '19911110001', '内蒙古自治区呼和浩特市新城区555', 72.37, '顺丰');
INSERT INTO `orders` VALUES (4, 4, '2024-06-28 16:35:23', 10001.00, -1, '个人原因不需要了', 'a', 'd@qq.com', '19911110004', '福建省福州市鼓楼区66', 72.37, '顺丰');
INSERT INTO `orders` VALUES (5, 1, '2024-06-29 11:26:25', 1998.00, 3, NULL, 'a', 'a@qq.com', '19911110001', '湖南省衡阳市珠晖区酃湖乡顺枫公寓B1号湖南工学院顺枫公寓', 72.37, '顺丰');
INSERT INTO `orders` VALUES (6, 1, '2024-06-29 11:39:03', 12000.00, -1, NULL, 'a', 'a@qq.com', '19911110001', '湖南省衡阳市珠晖区酃湖乡顺枫公寓B1号湖南工学院顺枫公寓', 72.37, '顺丰');
INSERT INTO `orders` VALUES (7, 17, '2024-01-29 18:57:38', 1000.00, 2, NULL, 'a', '1@qq.com', '19988881111', '湖南省衡阳市', 72.37, '顺丰');
INSERT INTO `orders` VALUES (8, 17, '2024-02-29 18:57:38', 1000.00, 2, NULL, 'a', '1@qq.com', '19988881111', '湖南省衡阳市', 72.37, '顺丰');
INSERT INTO `orders` VALUES (9, 17, '2024-04-29 18:57:38', 1000.00, 2, NULL, 'a', '1@qq.com', '19988881111', '湖南省衡阳市', 72.37, '顺丰');
INSERT INTO `orders` VALUES (10, 14, '2024-04-29 18:57:38', 1000.00, 0, '', 'a', '2921310632@qq.com', '18569380123', '黑龙江省哈尔滨市道里区1111111111111', 72.37, '顺丰');
INSERT INTO `orders` VALUES (11, 14, '2024-04-29 18:57:38', 1000.00, 2, NULL, 'a', '2921310632@qq.com', '18569380123', '湖北省武汉市江岸区22222', 72.37, '韵达');
INSERT INTO `orders` VALUES (12, 14, '2024-04-29 18:57:38', 1000.00, -3, '重复下单', 'a', '2921310632@qq.com', '18569380123', '湖南省衡阳市', 72.37, '顺丰');
INSERT INTO `orders` VALUES (13, 14, '2024-04-29 18:57:38', 1000.00, 3, NULL, 'a', '2921310632@qq.com', '18569380123', '湖南省衡阳市', 72.37, '顺丰');
INSERT INTO `orders` VALUES (14, 14, '2024-04-29 18:57:38', 1000.00, -1, NULL, 'a', '2921310632@qq.com', '18569380123', '湖南省衡阳市', 72.37, '顺丰');
INSERT INTO `orders` VALUES (15, 14, '2024-04-29 18:57:38', 1000.00, -3, NULL, 'a', '2921310632@qq.com', '18569380123', '湖南省衡阳市', 72.37, '顺丰');
INSERT INTO `orders` VALUES (16, 14, '2024-04-29 18:57:38', 1000.00, -3, NULL, 'a', '2921310632@qq.com', '18569380123', '湖南省衡阳市', 72.37, '顺丰');
INSERT INTO `orders` VALUES (35, 14, '2024-09-28 21:34:43', 11113.37, -1, '误操作下单', 'xinqiu', '2921310632@qq.com', '18569380564', '广东省广州市荔湾区', 113.63, NULL);
INSERT INTO `orders` VALUES (36, 14, '2024-09-29 09:33:23', 7441.70, 2, NULL, 'xinqiu', '2921310632@qq.com', '18569380564', '浙江省金华市浦江县3333', 108.54, NULL);
INSERT INTO `orders` VALUES (37, 14, '2024-09-29 10:03:42', 7503.60, 2, NULL, 'xinqiu', '2921310632@qq.com', '18569380564', '广东省广州市荔湾区0000', 170.44, NULL);
INSERT INTO `orders` VALUES (38, 14, '2024-09-29 10:21:18', 7550.28, 2, NULL, 'xinqiu', '2921310632@qq.com', '18569380564', '新疆维吾尔自治区乌鲁木齐市天山区0000', 217.12, NULL);
INSERT INTO `orders` VALUES (39, 14, '2024-09-29 11:22:06', 3947.73, -3, '误操作下单', 'xinqiu', '2921310632@qq.com', '18569380564', '福建省福州市鼓楼区1111', 281.15, NULL);
INSERT INTO `orders` VALUES (40, 14, '2024-09-29 11:24:31', 3741.43, -3, '质量问题', 'xinqiu', '2921310632@qq.com', '18569380564', '内蒙古自治区呼和浩特市新城区', 74.85, NULL);
INSERT INTO `orders` VALUES (41, 14, '2024-09-29 11:29:43', 3947.73, -3, '下错订单', 'xinqiu', '2921310632@qq.com', '18569380564', '福建省福州市鼓楼区', 281.15, NULL);
INSERT INTO `orders` VALUES (55, 14, '2024-09-29 19:15:05', 7473.73, -3, '需要时间太长', 'xinqiu', '2921310632@qq.com', '18569380564', '福建省福州市鼓楼区', 140.57, NULL);
INSERT INTO `orders` VALUES (56, 14, '2024-09-29 21:12:35', 14706.23, 3, NULL, 'xinqiu', '2921310632@qq.com', '18569380564', '宁夏回族自治区银川市兴庆区', 39.91, NULL);
INSERT INTO `orders` VALUES (57, 14, '2024-09-29 21:21:06', 7473.73, 3, NULL, 'xinqiu', '2921310632@qq.com', '18569380564', '福建省福州市鼓楼区', 140.57, NULL);
INSERT INTO `orders` VALUES (58, 14, '2024-09-29 21:29:14', 7427.93, 3, NULL, 'xinqiu', '2921310632@qq.com', '18569380564', '湖北省武汉市江岸区', 94.77, NULL);
INSERT INTO `orders` VALUES (59, 14, '2024-09-29 21:32:36', 7427.93, -3, '服务态度不好', 'xinqiu', '2921310632@qq.com', '18569380564', '湖北省武汉市江岸区', 94.77, NULL);
INSERT INTO `orders` VALUES (60, 14, '2024-10-03 11:45:22', 7503.60, 3, NULL, 'xinqiu', '2921310632@qq.com', '18569380564', '广东省广州市荔湾区', 170.44, NULL);
INSERT INTO `orders` VALUES (61, 14, '2024-10-03 11:49:19', 7389.55, -3, '更换了其他品牌', 'xinqiu', '2921310632@qq.com', '18569380564', '辽宁省沈阳市和平区333', 56.39, NULL);
INSERT INTO `orders` VALUES (62, 14, '2024-10-03 13:09:49', 11070.71, -3, '找到更好的价格', 'xinqiu', '2921310632@qq.com', '18569380564', '甘肃省兰州市城关区000000', 70.97, NULL);
INSERT INTO `orders` VALUES (63, 14, '2024-10-03 13:14:27', 3947.73, -3, NULL, 'xinqiu', '2921310632@qq.com', '18569380564', '福建省福州市鼓楼区', 281.15, NULL);
INSERT INTO `orders` VALUES (68, 14, '2024-10-06 20:32:39', 1999.50, 3, NULL, 'xin', '2921310632@qq.com', '18569380564', '北京市北京市东城区', 0.50, NULL);
INSERT INTO `orders` VALUES (79, 14, '2024-10-07 11:39:17', 186.83, -3, NULL, 'xin', '2921310632@qq.com', '18569380564', '安徽省安庆市潜山县', 185.83, NULL);
INSERT INTO `orders` VALUES (80, 14, '2024-10-08 16:59:41', 7389.55, -3, NULL, 'xin', '2921310632@qq.com', '18569380564', '辽宁省沈阳市和平区', 56.39, '韵达');
INSERT INTO `orders` VALUES (81, 14, '2024-10-08 19:20:58', 8184.83, 3, NULL, 'xin', '2921310632@qq.com', '18569380564', '安徽省安庆市潜山县', 185.83, '韵达');
INSERT INTO `orders` VALUES (82, 14, '2024-10-08 19:23:13', 14090.92, 1, NULL, 'xin', '2921310632@qq.com', '18569380564', '安徽省安庆市潜山县', 92.92, NULL);
INSERT INTO `orders` VALUES (83, 14, '2024-10-08 19:50:31', 7453.84, 1, NULL, 'xin', '2921310632@qq.com', '18569380564', '湖南省长沙市芙蓉区', 120.68, NULL);
INSERT INTO `orders` VALUES (84, 14, '2024-10-09 19:34:27', 7453.84, -3, '重复下单', 'xinxin', '2921310632@qq.com', '18569380564', '湖南省长沙市芙蓉区', 120.68, NULL);
INSERT INTO `orders` VALUES (85, 14, '2024-10-09 19:41:46', 10712.78, 1, NULL, 'xinxin', '2921310632@qq.com', '18569380564', '安徽省安庆市潜山县', 46.46, NULL);
INSERT INTO `orders` VALUES (86, 14, '2024-10-09 19:52:46', 18375.48, 2, NULL, 'xinxin', '2921310632@qq.com', '18569380564', '甘肃省兰州市城关区', 42.58, '顺丰');
INSERT INTO `orders` VALUES (87, 14, '2024-10-12 15:11:42', 6712.78, 1, NULL, 'xinxin', '2921310632@qq.com', '18569380564', '安徽省安庆市潜山县', 46.46, NULL);
INSERT INTO `orders` VALUES (88, 14, '2024-10-12 15:14:32', 1540.96, 1, NULL, 'xinxin', '2921310632@qq.com', '18569380564', '安徽省安庆市潜山县', 185.83, NULL);
INSERT INTO `orders` VALUES (89, 14, '2024-10-12 15:24:08', 2942.74, 1, NULL, 'xinxin', '2921310632@qq.com', '18569380564', '河北省石家庄市长安区', 23.70, NULL);
INSERT INTO `orders` VALUES (90, 14, '2024-10-12 15:30:16', 2875.66, 2, NULL, 'xinxin', '2921310632@qq.com', '18569380564', '黑龙江省哈尔滨市道里区', 190.16, '圆通');

SET FOREIGN_KEY_CHECKS = 1;
