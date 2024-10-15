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

 Date: 15/10/2024 11:57:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for reply_module
-- ----------------------------
DROP TABLE IF EXISTS `reply_module`;
CREATE TABLE `reply_module`  (
  `reply_module_id` int NOT NULL AUTO_INCREMENT,
  `module_tile` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `module_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`reply_module_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reply_module
-- ----------------------------
INSERT INTO `reply_module` VALUES (1, '商品质量或描述不符', '尊敬的顾客，很抱歉给您带来不好的购物体验。请您详细描述问题或拍摄照片发送给我们，我们会尽快为您处理退换货事宜，给您带来更好的购物体验。');
INSERT INTO `reply_module` VALUES (2, '服务态度或物流延误', '亲爱的顾客，非常抱歉由于我们的服务或物流配送出现了问题，给您带来了不便和困扰。我们会立即核实情况并改进服务质量，如果您愿意，我们也可以为您补偿或提供优惠，期待您的理解和支持。');
INSERT INTO `reply_module` VALUES (3, '售后处理不及时', '亲爱的顾客，对于您的反馈我们深感抱歉。我们会加强售后团队的培训和管理，确保未来能够更及时地处理类似问题。请您给我们一个机会重新为您服务，我们将竭尽全力为您解决问题。');
INSERT INTO `reply_module` VALUES (4, '其他不满意', '尊敬的顾客，感谢您的反馈。我们真诚地接受您的意见，并会立即采取措施改进。如果方便的话，希望您能联系我们详细描述问题，我们将尽全力解决并补偿您的损失。');
INSERT INTO `reply_module` VALUES (5, '后续处理结果', '尊敬的顾客，我们已经核实了您的订单信息并进行了处理，给您带来了不便我们深感抱歉。如有任何问题或进一步需要，随时联系我们，我们会继续关注您的订单并确保您的满意度。');

SET FOREIGN_KEY_CHECKS = 1;
