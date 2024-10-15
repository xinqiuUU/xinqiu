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

 Date: 15/10/2024 11:57:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for product_pic
-- ----------------------------
DROP TABLE IF EXISTS `product_pic`;
CREATE TABLE `product_pic`  (
  `ppid` int NOT NULL AUTO_INCREMENT,
  `pid` int NOT NULL,
  `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `is_primary` int NULL DEFAULT NULL,
  PRIMARY KEY (`ppid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 75 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_pic
-- ----------------------------
INSERT INTO `product_pic` VALUES (3, 3, 'https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/3.png', 1);
INSERT INTO `product_pic` VALUES (10, 1, 'https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/2.png', 1);
INSERT INTO `product_pic` VALUES (12, 1, 'https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/2.png', 0);
INSERT INTO `product_pic` VALUES (13, 1, 'https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/2.png', 0);
INSERT INTO `product_pic` VALUES (15, 3, 'https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/1.png', 0);
INSERT INTO `product_pic` VALUES (16, 5, 'https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ%E6%88%AA%E5%9B%BE20240712104750.png', 0);
INSERT INTO `product_pic` VALUES (19, 14, 'https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/c.png', 0);
INSERT INTO `product_pic` VALUES (20, 15, 'https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/y.png', 0);
INSERT INTO `product_pic` VALUES (21, 6, 'https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/JEEP%E8%BF%90%E5%8A%A8%E6%89%8B%E8%A1%A8.png', 0);
INSERT INTO `product_pic` VALUES (22, 7, 'https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/OPPOD23.png', 0);
INSERT INTO `product_pic` VALUES (23, 8, 'https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ%E6%88%AA%E5%9B%BE20240712104750.png', 0);
INSERT INTO `product_pic` VALUES (25, 10, 'https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ%E6%88%AA%E5%9B%BE20240712104808.png', 0);
INSERT INTO `product_pic` VALUES (26, 11, 'https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ%E6%88%AA%E5%9B%BE20240712104813.png', 0);
INSERT INTO `product_pic` VALUES (29, 16, 'https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ%E6%88%AA%E5%9B%BE20240712132559.png', 0);
INSERT INTO `product_pic` VALUES (30, 17, 'https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/ROG%E6%8E%8C%E6%9C%BA.png', 0);
INSERT INTO `product_pic` VALUES (31, 18, 'https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/huaweiMate60pro.jpg', 0);
INSERT INTO `product_pic` VALUES (38, 2, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712104758.png', 1);
INSERT INTO `product_pic` VALUES (39, 13, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712132610.png', 1);
INSERT INTO `product_pic` VALUES (40, 21, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712114925.png', 1);
INSERT INTO `product_pic` VALUES (41, 22, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712104813.png', 1);
INSERT INTO `product_pic` VALUES (42, 23, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712114915.png', 1);
INSERT INTO `product_pic` VALUES (43, 24, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712132112.png', 1);
INSERT INTO `product_pic` VALUES (44, 25, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712115004.png', 1);
INSERT INTO `product_pic` VALUES (45, 38, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20241006164344.png', 1);
INSERT INTO `product_pic` VALUES (46, 39, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/c.jpg', 1);
INSERT INTO `product_pic` VALUES (47, 37, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712114915.png', 1);
INSERT INTO `product_pic` VALUES (48, 36, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712115004.png', 1);
INSERT INTO `product_pic` VALUES (49, 35, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712132151.png', 1);
INSERT INTO `product_pic` VALUES (50, 34, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712114902.png', 1);
INSERT INTO `product_pic` VALUES (51, 33, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20241006164344.png', 1);
INSERT INTO `product_pic` VALUES (52, 32, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712103000.png', 1);
INSERT INTO `product_pic` VALUES (53, 26, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712103000.png', 1);
INSERT INTO `product_pic` VALUES (54, 26, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712104750.png', 0);
INSERT INTO `product_pic` VALUES (55, 26, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712104758.png', 0);
INSERT INTO `product_pic` VALUES (56, 26, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712104808.png', 0);
INSERT INTO `product_pic` VALUES (57, 27, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712132142.png', 1);
INSERT INTO `product_pic` VALUES (58, 27, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712132151.png', 0);
INSERT INTO `product_pic` VALUES (59, 27, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712132212.png', 0);
INSERT INTO `product_pic` VALUES (60, 28, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712114838.png', 1);
INSERT INTO `product_pic` VALUES (61, 28, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712114902.png', 0);
INSERT INTO `product_pic` VALUES (62, 30, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712132559.png', 1);
INSERT INTO `product_pic` VALUES (63, 29, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712132617.png', 1);
INSERT INTO `product_pic` VALUES (64, 31, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712115004.png', 1);
INSERT INTO `product_pic` VALUES (65, 19, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712132212.png', 1);
INSERT INTO `product_pic` VALUES (66, 20, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20241006164344.png', 1);
INSERT INTO `product_pic` VALUES (67, 12, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712132610.png', 1);
INSERT INTO `product_pic` VALUES (68, 12, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712132617.png', 0);
INSERT INTO `product_pic` VALUES (69, 12, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20241006164344.png', 0);
INSERT INTO `product_pic` VALUES (70, 4, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20241006164344.png', 1);
INSERT INTO `product_pic` VALUES (71, 9, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20240712132617.png', 1);
INSERT INTO `product_pic` VALUES (72, 40, ' https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20241008191130.png', 1);
INSERT INTO `product_pic` VALUES (73, 41, 'https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20241012205249.png', 1);
INSERT INTO `product_pic` VALUES (74, 42, 'https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/QQ截图20241013213403.png', 1);

SET FOREIGN_KEY_CHECKS = 1;
