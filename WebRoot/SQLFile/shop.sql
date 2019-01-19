/*
Navicat MySQL Data Transfer

Source Server         : 201810620306丁培良
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : shop

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2019-01-19 16:06:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for classify
-- ----------------------------
DROP TABLE IF EXISTS `classify`;
CREATE TABLE `classify` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of classify
-- ----------------------------
INSERT INTO `classify` VALUES ('1', '兰蔻');
INSERT INTO `classify` VALUES ('2', '娇兰');
INSERT INTO `classify` VALUES ('3', '雅诗兰黛');

-- ----------------------------
-- Table structure for cosmeticform
-- ----------------------------
DROP TABLE IF EXISTS `cosmeticform`;
CREATE TABLE `cosmeticform` (
  `cosmetic_number` char(255) NOT NULL,
  `cosmetic_name` char(255) DEFAULT NULL,
  `cosmetic_made` char(255) DEFAULT NULL,
  `cosmetic_price` float DEFAULT NULL,
  `cosmetic_mess` char(255) DEFAULT NULL,
  `cosmetic_pic` char(255) NOT NULL,
  `id` int(11) DEFAULT NULL,
  PRIMARY KEY (`cosmetic_number`,`cosmetic_pic`),
  KEY `id` (`id`),
  CONSTRAINT `id` FOREIGN KEY (`id`) REFERENCES `classify` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cosmeticform
-- ----------------------------
INSERT INTO `cosmeticform` VALUES ('EsteeLauder1001', '雅诗兰黛眼霜', 'EsteeLauder', '389', '功效：去黑眼圈', 'est1.jpg', '3');
INSERT INTO `cosmeticform` VALUES ('lan_a10002', '兰蔻乳液', '法国Lancome', '588', '乳液功效：补水 保湿', 'lan2.jpg', '1');
INSERT INTO `cosmeticform` VALUES ('lan_a1001', '兰蔻清莹柔肤水', '法国Lancome', '229', '化妆品净含量：400g', 'lan1.jpg', '1');

-- ----------------------------
-- Table structure for orderform
-- ----------------------------
DROP TABLE IF EXISTS `orderform`;
CREATE TABLE `orderform` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `logname` char(255) DEFAULT NULL,
  `mess` char(255) DEFAULT NULL,
  `sum` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orderform
-- ----------------------------
INSERT INTO `orderform` VALUES ('1', 'dpl', '1:(lan_a10002,兰蔻乳液,法国Lancome,588.0)2:(lan_a10002,兰蔻乳液,法国Lancome,588.0)', '1176');
INSERT INTO `orderform` VALUES ('2', 'dpl', '1:(lan_a10002,兰蔻乳液,法国Lancome,588.0)', '588');
INSERT INTO `orderform` VALUES ('3', 'dpl', '1:(lan_a10002,兰蔻乳液,法国Lancome,588.0)', '588');
INSERT INTO `orderform` VALUES ('4', 'dpl', '1:(EsteeLauder1001,雅诗兰黛眼霜,EsteeLauder,389)', '389');
INSERT INTO `orderform` VALUES ('5', 'dpl', '1:(EsteeLauder1001,雅诗兰黛眼霜,EsteeLauder,389.0)', '389');
INSERT INTO `orderform` VALUES ('6', 'dpl', '1:(lan_a10002,兰蔻乳液,法国Lancome,588)', '588');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `logname` char(255) NOT NULL,
  `password` char(255) DEFAULT NULL,
  `phone` char(255) DEFAULT NULL,
  `address` char(255) DEFAULT NULL,
  `realname` char(255) DEFAULT NULL COMMENT '存储姓名',
  PRIMARY KEY (`logname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('ddddd', '123456789', '17895264876', '山东滨州', '颠沛流离');
INSERT INTO `user` VALUES ('dpl', '123456', '17862078760', '山东滨州', '小丁');
INSERT INTO `user` VALUES ('dpl12', '123456', '17862028760', '山东滨州', '打破了');
INSERT INTO `user` VALUES ('dpl123', '123456', '17862078760', '山东滨州', '丁丁');
INSERT INTO `user` VALUES ('dpll19', '12345', '17862078759', '山东济南', '小布丁');
INSERT INTO `user` VALUES ('dpppp', '123456', '17862028760', '山东滨州', '打破了');
