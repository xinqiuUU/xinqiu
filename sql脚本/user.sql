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

 Date: 15/10/2024 11:57:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `uid` int NOT NULL AUTO_INCREMENT,
  `uname` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `pwd` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sex` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `age` int NULL DEFAULT NULL,
  `email` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `tel` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT 1,
  `role` int NULL DEFAULT 1,
  `logins` int NULL DEFAULT NULL,
  `province` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `city` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `county` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `head_photo` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'a', 'c2f0789e6ad28c3f6f85da1fb9828d79', 'man', 25, 'a@qq.com', '19911110001', 1, 1, 1, '湖南省', '衡阳市', '详细地址1', '2024-06-23 21:32:06', '2024-10-13 21:19:35', NULL);
INSERT INTO `user` VALUES (2, 'b', 'c2f0789e6ad28c3f6f85da1fb9828d79', 'man', 25, 'b@qq.com', '19911110002', 1, 1, 1, '湖南省', '衡阳市', '详细地址2', '2024-06-23 21:32:06', '2024-10-13 21:27:13', NULL);
INSERT INTO `user` VALUES (3, 'c', 'c2f0789e6ad28c3f6f85da1fb9828d79', 'woman', 25, 'c@qq.com', '19911110003', 0, 1, 1, '湖南省', '益阳市', '详细地址3', '2024-06-23 21:32:06', '2024-10-13 21:19:31', NULL);
INSERT INTO `user` VALUES (4, 'd', 'c2f0789e6ad28c3f6f85da1fb9828d79', 'woman', 35, 'd@qq.com', '19911110004', 1, 1, 1, '湖北省', '武汉市', '详细地址4', '2024-06-23 21:32:06', '2024-06-23 21:32:06', NULL);
INSERT INTO `user` VALUES (5, 'e', 'c2f0789e6ad28c3f6f85da1fb9828d79', 'woman', 35, 'e@qq.com', '19911110005', 1, 1, 1, '湖北省', '武汉市', '详细地址5', '2024-06-23 21:32:06', '2024-09-24 21:47:20', NULL);
INSERT INTO `user` VALUES (6, 'f', 'c2f0789e6ad28c3f6f85da1fb9828d79', 'woman', 35, 'f@qq.com', '19911110006', 1, 1, 1, '湖北省', '黄石市', '详细地址6', '2024-06-23 21:32:06', '2024-06-23 21:32:06', NULL);
INSERT INTO `user` VALUES (7, 'g', 'c2f0789e6ad28c3f6f85da1fb9828d79', 'woman', 16, 'g@qq.com', '19911110007', 1, 1, 1, '山东省', '青岛市', '详细地址7', '2024-06-23 21:32:06', '2024-06-23 21:32:06', NULL);
INSERT INTO `user` VALUES (8, 'h', 'c2f0789e6ad28c3f6f85da1fb9828d79', 'man', 16, 'h@qq.com', '19911110008', 1, 1, 1, '山东省', '青岛市', '详细地址8', '2024-06-23 21:32:06', '2024-06-23 21:32:06', NULL);
INSERT INTO `user` VALUES (9, 'i', 'c2f0789e6ad28c3f6f85da1fb9828d79', 'man', 50, 'i@qq.com', '19911110009', 1, 1, 1, '山东省', '淄博市', '详细地址9', '2024-06-23 21:32:06', '2024-06-23 21:32:06', NULL);
INSERT INTO `user` VALUES (10, 'j', 'c2f0789e6ad28c3f6f85da1fb9828d79', 'man', 50, 'j@qq.com', '19911110010', 1, 1, 1, '山东省', '济宁市', '详细地址10', '2024-06-23 21:32:06', '2024-06-23 21:32:06', NULL);
INSERT INTO `user` VALUES (11, 'b', 'c2f0789e6ad28c3f6f85da1fb9828d79', 'man', 25, 'bb@qq.com', '19958821698', 1, 1, 1, '湖南省', '衡阳市', '详细地址2', '2024-06-23 21:41:16', '2024-06-23 21:41:16', NULL);
INSERT INTO `user` VALUES (12, NULL, '$2a$10$yNUHTWeBXaXw4hm5Nutl3uK54MHkVGlGpoKIW4.3kg5Fc8AeiC9/a', NULL, NULL, '30@qq.com', NULL, 1, 1, NULL, NULL, NULL, NULL, '2024-09-24 17:22:07', '2024-09-24 17:22:07', NULL);
INSERT INTO `user` VALUES (13, 'qqq', '$2a$10$Eu3GxiryGXsfUnh5KUkv9uQqBnvbdb82jcc8AjqFnIxWkGnT8HTAe', NULL, NULL, '31@qq.com', NULL, 1, 1, NULL, NULL, NULL, NULL, '2024-09-25 09:48:59', '2024-10-08 15:10:49', NULL);
INSERT INTO `user` VALUES (14, 'xinxin', '$2a$10$SBWWGBN.uYi7rzk3w1RO4uyEkVp8r1KDg5uSFprcA9uzxRelmewAS', NULL, NULL, '2921310632@qq.com', '18569380564', 1, 1, NULL, NULL, NULL, NULL, '2024-09-26 11:05:41', '2024-10-09 19:59:42', 'https://xin-qiu.oss-cn-beijing.aliyuncs.com/product/qiu.png');

SET FOREIGN_KEY_CHECKS = 1;
