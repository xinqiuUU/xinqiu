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

 Date: 15/10/2024 11:56:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `detail_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `pid` int NULL DEFAULT NULL,
  `amount` int NULL DEFAULT NULL,
  `smallprice` decimal(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`detail_id`) USING BTREE,
  INDEX `idx_pid`(`pid` ASC) USING BTREE,
  INDEX `idx_order_detail_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_order_detail_pid`(`pid` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 166 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES (1, 1, 1, 10, 60000.58);
INSERT INTO `order_detail` VALUES (2, 1, 1, 1, 6000.58);
INSERT INTO `order_detail` VALUES (3, 1, 2, 6, 60000.58);
INSERT INTO `order_detail` VALUES (4, 1, 2, 3, 60000.58);
INSERT INTO `order_detail` VALUES (5, 1, 3, 2, 60000.58);
INSERT INTO `order_detail` VALUES (6, 1, 3, 2, 60000.58);
INSERT INTO `order_detail` VALUES (7, 1, 4, 1, 60000.58);
INSERT INTO `order_detail` VALUES (8, 1, 4, 1, 60000.58);
INSERT INTO `order_detail` VALUES (9, 2, 5, 1, 60000.58);
INSERT INTO `order_detail` VALUES (10, 2, 5, 20, 60000.58);
INSERT INTO `order_detail` VALUES (11, 1, 15, 6, 21999.48);
INSERT INTO `order_detail` VALUES (12, 1, 12, 13, 47665.54);
INSERT INTO `order_detail` VALUES (13, 1, 14, 1, 3666.58);
INSERT INTO `order_detail` VALUES (14, 16, 11, 1, 4444.00);
INSERT INTO `order_detail` VALUES (15, 15, 12, 1, 999.00);
INSERT INTO `order_detail` VALUES (16, 14, 2, 1, 2000.00);
INSERT INTO `order_detail` VALUES (17, 13, 13, 1, 999.00);
INSERT INTO `order_detail` VALUES (18, 12, 3, 1, 5000.00);
INSERT INTO `order_detail` VALUES (19, 11, 14, 1, 999.00);
INSERT INTO `order_detail` VALUES (20, 10, 14, 1, 999.00);
INSERT INTO `order_detail` VALUES (26, 18, 12, 6, 21999.48);
INSERT INTO `order_detail` VALUES (27, 18, 4, 4, 14666.32);
INSERT INTO `order_detail` VALUES (28, 18, 14, 2, 7333.16);
INSERT INTO `order_detail` VALUES (29, 18, 31, 2, 7333.16);
INSERT INTO `order_detail` VALUES (30, 18, 1, 1, 3666.58);
INSERT INTO `order_detail` VALUES (31, 19, 12, 6, 21999.48);
INSERT INTO `order_detail` VALUES (32, 19, 4, 4, 14666.32);
INSERT INTO `order_detail` VALUES (33, 19, 14, 2, 7333.16);
INSERT INTO `order_detail` VALUES (34, 19, 31, 2, 7333.16);
INSERT INTO `order_detail` VALUES (35, 19, 1, 1, 3666.58);
INSERT INTO `order_detail` VALUES (36, 20, 12, 6, 21999.48);
INSERT INTO `order_detail` VALUES (37, 20, 4, 4, 14666.32);
INSERT INTO `order_detail` VALUES (38, 20, 14, 2, 7333.16);
INSERT INTO `order_detail` VALUES (39, 20, 31, 2, 7333.16);
INSERT INTO `order_detail` VALUES (40, 20, 1, 1, 3666.58);
INSERT INTO `order_detail` VALUES (41, 21, 12, 6, 21999.48);
INSERT INTO `order_detail` VALUES (42, 21, 4, 4, 14666.32);
INSERT INTO `order_detail` VALUES (43, 21, 14, 2, 7333.16);
INSERT INTO `order_detail` VALUES (44, 21, 31, 2, 7333.16);
INSERT INTO `order_detail` VALUES (45, 21, 1, 1, 3666.58);
INSERT INTO `order_detail` VALUES (46, 22, 12, 6, 21999.48);
INSERT INTO `order_detail` VALUES (47, 22, 4, 4, 14666.32);
INSERT INTO `order_detail` VALUES (48, 22, 14, 2, 7333.16);
INSERT INTO `order_detail` VALUES (49, 22, 31, 2, 7333.16);
INSERT INTO `order_detail` VALUES (50, 22, 1, 1, 3666.58);
INSERT INTO `order_detail` VALUES (51, 23, 12, 6, 21999.48);
INSERT INTO `order_detail` VALUES (52, 23, 4, 4, 14666.32);
INSERT INTO `order_detail` VALUES (53, 23, 14, 2, 7333.16);
INSERT INTO `order_detail` VALUES (54, 23, 31, 2, 7333.16);
INSERT INTO `order_detail` VALUES (55, 23, 1, 1, 3666.58);
INSERT INTO `order_detail` VALUES (56, 24, 12, 6, 21999.48);
INSERT INTO `order_detail` VALUES (57, 24, 4, 4, 14666.32);
INSERT INTO `order_detail` VALUES (58, 24, 14, 2, 7333.16);
INSERT INTO `order_detail` VALUES (59, 24, 31, 2, 7333.16);
INSERT INTO `order_detail` VALUES (60, 24, 1, 1, 3666.58);
INSERT INTO `order_detail` VALUES (61, 25, 12, 6, 21999.48);
INSERT INTO `order_detail` VALUES (62, 25, 4, 4, 14666.32);
INSERT INTO `order_detail` VALUES (63, 25, 14, 2, 7333.16);
INSERT INTO `order_detail` VALUES (64, 25, 31, 2, 7333.16);
INSERT INTO `order_detail` VALUES (65, 25, 1, 1, 3666.58);
INSERT INTO `order_detail` VALUES (66, 26, 12, 6, 21999.48);
INSERT INTO `order_detail` VALUES (67, 26, 4, 4, 14666.32);
INSERT INTO `order_detail` VALUES (68, 26, 14, 2, 7333.16);
INSERT INTO `order_detail` VALUES (69, 26, 31, 2, 7333.16);
INSERT INTO `order_detail` VALUES (70, 26, 1, 1, 3666.58);
INSERT INTO `order_detail` VALUES (71, 27, 12, 6, 21999.48);
INSERT INTO `order_detail` VALUES (72, 27, 4, 4, 14666.32);
INSERT INTO `order_detail` VALUES (73, 27, 14, 2, 7333.16);
INSERT INTO `order_detail` VALUES (74, 27, 31, 2, 7333.16);
INSERT INTO `order_detail` VALUES (75, 27, 1, 1, 3666.58);
INSERT INTO `order_detail` VALUES (76, 28, 31, 2, 7333.16);
INSERT INTO `order_detail` VALUES (77, 28, 1, 1, 3666.58);
INSERT INTO `order_detail` VALUES (78, 29, 31, 2, 7333.16);
INSERT INTO `order_detail` VALUES (79, 29, 1, 1, 3666.58);
INSERT INTO `order_detail` VALUES (80, 30, 31, 2, 7333.16);
INSERT INTO `order_detail` VALUES (81, 30, 1, 1, 3666.58);
INSERT INTO `order_detail` VALUES (82, 31, 31, 2, 7333.16);
INSERT INTO `order_detail` VALUES (83, 31, 1, 1, 3666.58);
INSERT INTO `order_detail` VALUES (84, 32, 31, 2, 7333.16);
INSERT INTO `order_detail` VALUES (85, 32, 1, 1, 3666.58);
INSERT INTO `order_detail` VALUES (86, 33, 31, 2, 7333.16);
INSERT INTO `order_detail` VALUES (87, 33, 1, 1, 3666.58);
INSERT INTO `order_detail` VALUES (88, 34, 31, 2, 7333.16);
INSERT INTO `order_detail` VALUES (89, 34, 1, 1, 3666.58);
INSERT INTO `order_detail` VALUES (90, 35, 31, 2, 7333.16);
INSERT INTO `order_detail` VALUES (91, 35, 1, 1, 3666.58);
INSERT INTO `order_detail` VALUES (92, 36, 15, 2, 7333.16);
INSERT INTO `order_detail` VALUES (93, 37, 15, 2, 7333.16);
INSERT INTO `order_detail` VALUES (94, 38, 13, 2, 7333.16);
INSERT INTO `order_detail` VALUES (95, 39, 3, 1, 3666.58);
INSERT INTO `order_detail` VALUES (96, 40, 4, 1, 3666.58);
INSERT INTO `order_detail` VALUES (97, 41, 5, 1, 3666.58);
INSERT INTO `order_detail` VALUES (111, 55, 3, 2, 7333.16);
INSERT INTO `order_detail` VALUES (112, 56, 4, 2, 7333.16);
INSERT INTO `order_detail` VALUES (113, 56, 14, 2, 7333.16);
INSERT INTO `order_detail` VALUES (114, 57, 4, 2, 7333.16);
INSERT INTO `order_detail` VALUES (115, 58, 12, 2, 7333.16);
INSERT INTO `order_detail` VALUES (116, 59, 15, 2, 7333.16);
INSERT INTO `order_detail` VALUES (117, 60, 1, 1, 3666.58);
INSERT INTO `order_detail` VALUES (118, 60, 12, 1, 3666.58);
INSERT INTO `order_detail` VALUES (119, 61, 12, 2, 7333.16);
INSERT INTO `order_detail` VALUES (120, 62, 12, 3, 10999.74);
INSERT INTO `order_detail` VALUES (121, 63, 12, 1, 3666.58);
INSERT INTO `order_detail` VALUES (126, 68, 38, 1, 1999.00);
INSERT INTO `order_detail` VALUES (137, 79, 39, 1, 1.00);
INSERT INTO `order_detail` VALUES (138, 80, 19, 2, 7333.16);
INSERT INTO `order_detail` VALUES (139, 81, 40, 1, 7999.00);
INSERT INTO `order_detail` VALUES (140, 82, 40, 2, 13998.00);
INSERT INTO `order_detail` VALUES (141, 83, 10, 2, 7333.16);
INSERT INTO `order_detail` VALUES (142, 84, 19, 2, 7333.16);
INSERT INTO `order_detail` VALUES (143, 85, 1, 2, 3333.16);
INSERT INTO `order_detail` VALUES (144, 85, 36, 1, 3666.58);
INSERT INTO `order_detail` VALUES (145, 85, 17, 1, 3666.58);
INSERT INTO `order_detail` VALUES (146, 86, 19, 1, 3666.58);
INSERT INTO `order_detail` VALUES (147, 86, 15, 2, 7333.16);
INSERT INTO `order_detail` VALUES (148, 86, 7, 1, 3666.58);
INSERT INTO `order_detail` VALUES (149, 86, 5, 1, 3666.58);
INSERT INTO `order_detail` VALUES (150, 87, 12, 2, 1333.16);
INSERT INTO `order_detail` VALUES (151, 87, 1, 1, 1666.58);
INSERT INTO `order_detail` VALUES (152, 87, 15, 1, 3666.58);
INSERT INTO `order_detail` VALUES (153, 88, 10, 1, 3666.58);
INSERT INTO `order_detail` VALUES (154, 89, 3, 2, 7333.16);
INSERT INTO `order_detail` VALUES (155, 90, 40, 1, 6999.00);
INSERT INTO `order_detail` VALUES (156, 91, 40, 1, 6999.00);
INSERT INTO `order_detail` VALUES (157, 91, 41, 3, 8064.00);
INSERT INTO `order_detail` VALUES (158, 92, 41, 2, 5376.00);
INSERT INTO `order_detail` VALUES (159, 93, 41, 2, 5376.00);
INSERT INTO `order_detail` VALUES (160, 94, 41, 1, 2688.00);
INSERT INTO `order_detail` VALUES (161, 95, 15, 2, 7333.16);
INSERT INTO `order_detail` VALUES (162, 96, 41, 1, 2688.00);
INSERT INTO `order_detail` VALUES (163, 97, 1, 1, 1666.58);
INSERT INTO `order_detail` VALUES (164, 98, 1, 1, 1666.58);
INSERT INTO `order_detail` VALUES (165, 99, 15, 1, 3666.58);
INSERT INTO `order_detail` VALUES (166, 100, 42, 2, 316.00);

SET FOREIGN_KEY_CHECKS = 1;
