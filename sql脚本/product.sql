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

 Date: 15/10/2024 11:57:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `pid` int NOT NULL AUTO_INCREMENT,
  `aid` int NOT NULL,
  `pname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `firm` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `normprice` decimal(10, 2) NULL DEFAULT NULL,
  `realprice` decimal(10, 2) NULL DEFAULT NULL,
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int NULL DEFAULT 1,
  PRIMARY KEY (`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (1, 2, 'Apple/苹果 iPhone 13 Pro Max', 'iPhone 13 Pro Max 是苹果公司于2021年推出的旗舰智能手机，配备6.7英寸Super Retina XDR显示屏、A15仿生芯片和三摄系统，提供卓越的性能和拍摄效果。它支持5G网络、具备长续航电池、ProMotion技术以及ProRAW和ProRes视频录制功能，适合追求高性能和专业拍摄体验的用户。', '手机', '苹果公司', 3999.58, 1666.58, '2024-06-20 18:44:14', '2024-10-13 21:27:43', 1);
INSERT INTO `product` VALUES (2, 1, 'Huawei/华为Mate60 Pro+', '华为Mate60 Pro+ 是华为推出的一款旗舰智能手机，搭载了强大的麒麟芯片，具有出色的性能和先进的拍照功能。它配备了大容量电池、高刷新率屏幕和多种创新技术，旨在提供卓越的用户体验和高效的多任务处理能力。', '手机', '华为公司', 4999.58, 4666.58, '2024-06-20 18:48:47', '2024-10-13 21:27:43', 1);
INSERT INTO `product` VALUES (3, 3, 'vivo Y31S', 'vivo Y31S 是一款中端智能手机，搭载高通骁龙处理器，支持5G网络，提供流畅的性能体验。它配备了一块高分辨率显示屏和大容量电池，适合日常使用和娱乐活动。', '手机', 'vivo公司', 3999.58, 3666.58, '2024-06-20 18:50:21', '2024-10-12 20:58:40', 1);
INSERT INTO `product` VALUES (4, 4, 'OPPO A55', 'OPPO A55 是一款入门级智能手机，搭载联发科处理器，支持4G网络，提供稳定的日常性能。它配备了一块高清显示屏和5000mAh大容量电池，适合长时间使用和基本娱乐需求。', '手机', 'OPPO公司', 3999.58, 3666.58, '2024-06-20 18:51:29', '2024-06-22 17:10:19', 1);
INSERT INTO `product` VALUES (5, 5, 'iQOO Z9x', 'iQOO Z9x是一款由vivo旗下品牌iQOO推出的中端智能手机，搭载了高性能的处理器和大容量电池，为用户提供流畅的使用体验和持久的续航能力。', '手机', 'vivo公司', 3999.58, 3666.58, '2024-06-21 10:45:49', '2024-06-22 17:10:19', 1);
INSERT INTO `product` VALUES (7, 2, 'Apple/苹果 iPhone 13 Pro Max', 'iPhone 13 Pro Max 是苹果公司于2021年推出的旗舰智能手机，配备6.7英寸Super Retina XDR显示屏、A15仿生芯片和三摄系统，提供卓越的性能和拍摄效果。它支持5G网络、具备长续航电池、ProMotion技术以及ProRAW和ProRes视频录制功能，适合追求高性能和专业拍摄体验的用户。', '手机', '苹果公司', 3999.58, 3666.58, '2024-06-22 14:14:43', '2024-06-22 17:10:19', 1);
INSERT INTO `product` VALUES (8, 1, 'Huawei/华为Mate60 Pro+', '华为Mate60 Pro+ 是华为推出的一款旗舰智能手机，搭载了强大的麒麟芯片，具有出色的性能和先进的拍照功能。它配备了大容量电池、高刷新率屏幕和多种创新技术，旨在提供卓越的用户体验和高效的多任务处理能力。', '手机', '华为公司', 4999.58, 4666.58, '2024-06-22 14:14:45', '2024-06-22 17:10:19', 1);
INSERT INTO `product` VALUES (9, 3, 'vivo Y31S', 'vivo Y31S 是一款中端智能手机，搭载高通骁龙处理器，支持5G网络，提供流畅的性能体验。它配备了一块高分辨率显示屏和大容量电池，适合日常使用和娱乐活动。', '手机', 'vivo公司', 3999.58, 1666.58, '2024-06-22 14:14:48', '2024-10-12 20:31:20', 1);
INSERT INTO `product` VALUES (10, 4, 'OPPO A55', 'OPPO A55 是一款入门级智能手机，搭载联发科处理器，支持4G网络，提供稳定的日常性能。它配备了一块高清显示屏和5000mAh大容量电池，适合长时间使用和基本娱乐需求。', '手机', 'OPPO公司', 3999.58, 3666.58, '2024-06-22 14:14:50', '2024-06-22 17:10:19', 1);
INSERT INTO `product` VALUES (11, 5, 'iQOO Z9x', 'iQOO Z9x是一款由vivo旗下品牌iQOO推出的中端智能手机，搭载了高性能的处理器和大容量电池，为用户提供流畅的使用体验和持久的续航能力。', '手机', 'vivo公司', 3999.58, 3666.58, '2024-06-22 14:14:52', '2024-06-22 17:10:19', 1);
INSERT INTO `product` VALUES (12, 5, '111电子手表', 'iQOO Z9x是一款由vivo旗下品牌iQOO推出的中端智能手机，搭载了高性能的处理器和大容量电池，为用户提供流畅的使用体验和持久的续航能力。', '手机', 'vivo公司', 3999.58, 666.58, '2024-06-22 17:21:52', '2024-10-12 20:58:40', 1);
INSERT INTO `product` VALUES (13, 5, '222平板', 'iQOO Z9x是一款由vivo旗下品牌iQOO推出的中端智能手机，搭载了高性能的处理器和大容量电池，为用户提供流畅的使用体验和持久的续航能力。', '平板', 'vivo公司', 3999.58, 3666.58, '2024-06-22 17:21:52', '2024-06-22 17:21:52', 1);
INSERT INTO `product` VALUES (14, 5, '444电脑', 'iQOO Z9x是一款由vivo旗下品牌iQOO推出的中端智能手机，搭载了高性能的处理器和大容量电池，为用户提供流畅的使用体验和持久的续航能力。', '电脑', 'vivo公司', 3999.58, 3666.58, '2024-06-22 17:21:52', '2024-06-22 17:21:52', 1);
INSERT INTO `product` VALUES (15, 5, '666游戏机', 'iQOO Z9x是一款由vivo旗下品牌iQOO推出的中端智能手机，搭载了高性能的处理器和大容量电池，为用户提供流畅的使用体验和持久的续航能力。', '游戏机', 'vivo公司', 3999.58, 3666.58, '2024-06-22 17:21:52', '2024-06-22 17:21:52', 1);
INSERT INTO `product` VALUES (16, 1, 'Huawei/华为Mate60 Pro+', '华为Mate60 Pro+ 是华为推出的一款旗舰智能手机，搭载了强大的麒麟芯片，具有出色的性能和先进的拍照功能。它配备了大容量电池、高刷新率屏幕和多种创新技术，旨在提供卓越的用户体验和高效的多任务处理能力。', '手机', '华为公司', 4999.58, 4666.58, '2024-06-23 11:28:34', '2024-06-23 11:28:34', 1);
INSERT INTO `product` VALUES (17, 3, 'vivo Y31S', 'vivo Y31S 是一款中端智能手机，搭载高通骁龙处理器，支持5G网络，提供流畅的性能体验。它配备了一块高分辨率显示屏和大容量电池，适合日常使用和娱乐活动。', '手机', 'vivo公司', 3999.58, 3666.58, '2024-06-23 11:28:34', '2024-06-23 11:28:34', 1);
INSERT INTO `product` VALUES (18, 4, 'OPPO A55', 'OPPO A55 是一款入门级智能手机，搭载联发科处理器，支持4G网络，提供稳定的日常性能。它配备了一块高清显示屏和5000mAh大容量电池，适合长时间使用和基本娱乐需求。', '手机', 'OPPO公司', 3999.58, 3666.58, '2024-06-23 11:28:34', '2024-06-23 11:28:34', 1);
INSERT INTO `product` VALUES (19, 5, 'iQOO Z9x', 'iQOO Z9x是一款由vivo旗下品牌iQOO推出的中端智能手机，搭载了高性能的处理器和大容量电池，为用户提供流畅的使用体验和持久的续航能力。', '手机', 'vivo公司', 3999.58, 3666.58, '2024-06-23 11:28:34', '2024-10-12 20:58:40', 1);
INSERT INTO `product` VALUES (20, 1, 'Huawei/华为Mate60 Pro+', '华为Mate60 Pro+ 是华为推出的一款旗舰智能手机，搭载了强大的麒麟芯片，具有出色的性能和先进的拍照功能。它配备了大容量电池、高刷新率屏幕和多种创新技术，旨在提供卓越的用户体验和高效的多任务处理能力。', '手机', '华为公司', 4999.58, 4666.58, '2024-06-23 11:28:39', '2024-06-23 11:28:39', 1);
INSERT INTO `product` VALUES (21, 3, 'vivo Y31S', 'vivo Y31S 是一款中端智能手机，搭载高通骁龙处理器，支持5G网络，提供流畅的性能体验。它配备了一块高分辨率显示屏和大容量电池，适合日常使用和娱乐活动。', '手机', 'vivo公司', 3999.58, 3666.58, '2024-06-23 11:28:39', '2024-06-23 11:28:39', 1);
INSERT INTO `product` VALUES (22, 4, 'OPPO A55', 'OPPO A55 是一款入门级智能手机，搭载联发科处理器，支持4G网络，提供稳定的日常性能。它配备了一块高清显示屏和5000mAh大容量电池，适合长时间使用和基本娱乐需求。', '手机', 'OPPO公司', 3999.58, 3666.58, '2024-06-23 11:28:39', '2024-06-23 11:28:39', 1);
INSERT INTO `product` VALUES (23, 5, 'iQOO Z9x', 'iQOO Z9x是一款由vivo旗下品牌iQOO推出的中端智能手机，搭载了高性能的处理器和大容量电池，为用户提供流畅的使用体验和持久的续航能力。', '手机', 'vivo公司', 3999.58, 3666.58, '2024-06-23 11:28:39', '2024-06-23 11:28:39', 1);
INSERT INTO `product` VALUES (24, 1, 'Huawei/华为Mate60 Pro+', '华为Mate60 Pro+ 是华为推出的一款旗舰智能手机，搭载了强大的麒麟芯片，具有出色的性能和先进的拍照功能。它配备了大容量电池、高刷新率屏幕和多种创新技术，旨在提供卓越的用户体验和高效的多任务处理能力。', '手机', '华为公司', 4999.58, 4666.58, '2024-06-23 11:29:26', '2024-06-23 11:29:26', 1);
INSERT INTO `product` VALUES (25, 3, 'vivo Y31S', 'vivo Y31S 是一款中端智能手机，搭载高通骁龙处理器，支持5G网络，提供流畅的性能体验。它配备了一块高分辨率显示屏和大容量电池，适合日常使用和娱乐活动。', '手机', 'vivo公司', 3999.58, 3666.58, '2024-06-23 11:29:26', '2024-06-23 11:29:26', 1);
INSERT INTO `product` VALUES (26, 4, 'OPPO A55', 'OPPO A55 是一款入门级智能手机，搭载联发科处理器，支持4G网络，提供稳定的日常性能。它配备了一块高清显示屏和5000mAh大容量电池，适合长时间使用和基本娱乐需求。', '手机', 'OPPO公司', 3999.58, 3666.58, '2024-06-23 11:29:26', '2024-06-23 11:29:26', 1);
INSERT INTO `product` VALUES (27, 5, 'iQOO Z9x', 'iQOO Z9x是一款由vivo旗下品牌iQOO推出的中端智能手机，搭载了高性能的处理器和大容量电池，为用户提供流畅的使用体验和持久的续航能力。', '手机', 'vivo公司', 3999.58, 3666.58, '2024-06-23 11:29:26', '2024-06-23 11:29:26', 1);
INSERT INTO `product` VALUES (28, 2, 'Apple/苹果 iPhone 13 Pro Max', 'iPhone 13 Pro Max 是苹果公司于2021年推出的旗舰智能手机，配备6.7英寸Super Retina XDR显示屏、A15仿生芯片和三摄系统，提供卓越的性能和拍摄效果。它支持5G网络、具备长续航电池、ProMotion技术以及ProRAW和ProRes视频录制功能，适合追求高性能和专业拍摄体验的用户。', '手机', '苹果公司', 3999.58, 3666.58, '2024-06-23 11:40:04', '2024-06-23 11:40:04', 1);
INSERT INTO `product` VALUES (29, 1, 'Huawei/华为Mate60 Pro+', '华为Mate60 Pro+ 是华为推出的一款旗舰智能手机，搭载了强大的麒麟芯片，具有出色的性能和先进的拍照功能。它配备了大容量电池、高刷新率屏幕和多种创新技术，旨在提供卓越的用户体验和高效的多任务处理能力。', '手机', '华为公司', 4999.58, 4666.58, '2024-06-23 11:40:04', '2024-06-23 11:40:04', 1);
INSERT INTO `product` VALUES (30, 3, 'vivo Y31S', 'vivo Y31S 是一款中端智能手机，搭载高通骁龙处理器，支持5G网络，提供流畅的性能体验。它配备了一块高分辨率显示屏和大容量电池，适合日常使用和娱乐活动。', '手机', 'vivo公司', 3999.58, 3666.58, '2024-06-23 11:40:04', '2024-06-23 11:40:04', 1);
INSERT INTO `product` VALUES (31, 4, 'OPPO A55', 'OPPO A55 是一款入门级智能手机，搭载联发科处理器，支持4G网络，提供稳定的日常性能。它配备了一块高清显示屏和5000mAh大容量电池，适合长时间使用和基本娱乐需求。', '手机', 'OPPO公司', 3999.58, 3666.58, '2024-06-23 11:40:04', '2024-06-23 11:40:04', 1);
INSERT INTO `product` VALUES (32, 5, 'iQOO Z9x', 'iQOO Z9x是一款由vivo旗下品牌iQOO推出的中端智能手机，搭载了高性能的处理器和大容量电池，为用户提供流畅的使用体验和持久的续航能力。', '手机', 'vivo公司', 3999.58, 3666.58, '2024-06-23 11:40:04', '2024-06-23 11:40:04', 1);
INSERT INTO `product` VALUES (33, 2, 'Apple/苹果 iPhone 13 Pro Max', 'iPhone 13 Pro Max 是苹果公司于2021年推出的旗舰智能手机，配备6.7英寸Super Retina XDR显示屏、A15仿生芯片和三摄系统，提供卓越的性能和拍摄效果。它支持5G网络、具备长续航电池、ProMotion技术以及ProRAW和ProRes视频录制功能，适合追求高性能和专业拍摄体验的用户。', '手机', '苹果公司', 3999.58, 3666.58, '2024-06-23 11:40:13', '2024-06-23 11:40:13', 1);
INSERT INTO `product` VALUES (34, 1, 'Huawei/华为Mate60 Pro+', '华为Mate60 Pro+ 是华为推出的一款旗舰智能手机，搭载了强大的麒麟芯片，具有出色的性能和先进的拍照功能。它配备了大容量电池、高刷新率屏幕和多种创新技术，旨在提供卓越的用户体验和高效的多任务处理能力。', '手机', '华为公司', 4999.58, 4666.58, '2024-06-23 11:40:13', '2024-06-23 11:40:13', 1);
INSERT INTO `product` VALUES (35, 3, 'vivo Y31S', 'vivo Y31S 是一款中端智能手机，搭载高通骁龙处理器，支持5G网络，提供流畅的性能体验。它配备了一块高分辨率显示屏和大容量电池，适合日常使用和娱乐活动。', '手机', 'vivo公司', 3999.58, 3666.58, '2024-06-23 11:40:13', '2024-06-23 11:40:13', 1);
INSERT INTO `product` VALUES (36, 4, 'OPPO A55', 'OPPO A55 是一款入门级智能手机，搭载联发科处理器，支持4G网络，提供稳定的日常性能。它配备了一块高清显示屏和5000mAh大容量电池，适合长时间使用和基本娱乐需求。', '手机', 'OPPO公司', 3999.58, 3666.58, '2024-06-23 11:40:13', '2024-06-23 11:40:13', 1);
INSERT INTO `product` VALUES (37, 5, 'iQOO Z9x', 'iQOO Z9x是一款由vivo旗下品牌iQOO推出的中端智能手机，搭载了高性能的处理器和大容量电池，为用户提供流畅的使用体验和持久的续航能力。', '手机', 'vivo公司', 3999.58, 3666.58, '2024-06-23 11:40:13', '2024-06-23 11:40:13', 1);
INSERT INTO `product` VALUES (38, 1, 'Redmi K70', '<h1 title=\"Redmi K70E红米手机小米手机小米官方旗舰店新品上市红米k70小米学生电竞游戏手机\" data-spm-anchor-id=\"pc_detail.29232929.202204.i0.1f267dd6Aw2RVQ\">Redmi K70E红米手机小米手机小米官方旗舰店新品上市红米k70小米学生电竞游戏手机</h1>', '手机', '1', 1999.00, 1999.00, '2024-10-06 17:07:20', '2024-10-06 17:07:20', 1);
INSERT INTO `product` VALUES (39, 1, '6666', '<p>哈哈哈</p><p><br></p>', '手机', '1', 1.00, 1.00, '2024-10-06 20:34:08', '2024-10-12 20:58:45', 0);
INSERT INTO `product` VALUES (40, 1, '华硕天选4', '<h1 title=\"【爆款游戏本】华硕天选4 13代英特尔酷睿i7 15.6英寸游戏本笔记本电脑RTX4060显卡天选5 Pro学生设计电竞本\" data-spm-anchor-id=\"pc_detail.29232929/evo365560b447259.202204.i0.5bbf7dd69gSR2p\">【爆款游戏本】华硕天选4 13代英特尔酷睿i7 15.6英寸游戏本笔记本电脑RTX4060显卡天选5 Pro学生设计电竞本.</h1>', '电脑', '1', 7999.00, 6999.00, '2024-10-08 19:11:41', '2024-10-12 20:47:03', 1);
INSERT INTO `product` VALUES (41, 1, '七彩虹i5', '<h1><i><b>七彩虹i5 13600KF/RTX3080Ti/4060Ti高配主机i7组装机i9台式电脑。</b></i></h1>', '电脑', '七彩虹', 2688.00, 2688.00, '2024-10-12 20:57:41', '2024-10-12 20:57:41', 1);
INSERT INTO `product` VALUES (42, 1, '双人游游机', '<h1 title=\"10000+游戏】2024新款掌上游戏机充电宝可连电视超级玛丽拳皇童年经典怀旧街机儿童双人游游机送礼物二合一fc\" data-spm-anchor-id=\"pc_detail.29232929.202204.i0.2be97dd6NurteT\">10000+游戏】2024新款掌上游戏机充电宝可连电视超级玛丽拳皇童年经典怀旧街机儿童双人游游机送礼物二合一fc</h1>', '游戏机', '翼博数码', 158.00, 158.00, '2024-10-13 21:35:45', '2024-10-13 21:35:45', 1);

SET FOREIGN_KEY_CHECKS = 1;
