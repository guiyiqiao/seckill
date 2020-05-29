/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : seckill

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2020-05-28 16:26:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `goods`
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(11) NOT NULL,
  `goods_img` varchar(64) NOT NULL,
  `goods_detail` varchar(255) NOT NULL,
  `goods_stock` int(11) NOT NULL,
  `goods_price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('1', '测试1', 'hello', '测试1', '1000', '2.40');
INSERT INTO `goods` VALUES ('2', '测试2', 'hello', '测试2', '1000', '5.60');

-- ----------------------------
-- Table structure for `item`
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) NOT NULL,
  `item_stock` int(11) NOT NULL,
  `item_price` decimal(10,2) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `version` int(11) NOT NULL DEFAULT '1',
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0 未开始，1 已经开始，2已经结束',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES ('1', '1', '9', '2.00', '2020-05-20 20:50:55', '2020-05-24 20:50:55', '61', '1');
INSERT INTO `item` VALUES ('2', '2', '0', '4.50', '2020-05-21 11:47:57', '2020-05-27 11:48:01', '13', '1');

-- ----------------------------
-- Table structure for `item_order`
-- ----------------------------
DROP TABLE IF EXISTS `item_order`;
CREATE TABLE `item_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(64) NOT NULL,
  `user_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of item_order
-- ----------------------------
INSERT INTO `item_order` VALUES ('8', '739852288', '34', '1');
INSERT INTO `item_order` VALUES ('9', '296413118464', '35', '1');
INSERT INTO `item_order` VALUES ('10', '704724992', '1', '1');
INSERT INTO `item_order` VALUES ('11', '694239232', '2', '1');
INSERT INTO `item_order` VALUES ('12', '3412148224', '4', '1');
INSERT INTO `item_order` VALUES ('13', '7602257920', '6', '1');
INSERT INTO `item_order` VALUES ('14', '11783979008', '8', '1');
INSERT INTO `item_order` VALUES ('15', '15974088704', '10', '1');
INSERT INTO `item_order` VALUES ('16', '20168392704', '12', '1');
INSERT INTO `item_order` VALUES ('17', '24379473920', '14', '1');
INSERT INTO `item_order` VALUES ('18', '28561195008', '16', '1');
INSERT INTO `item_order` VALUES ('19', '32759693312', '18', '1');
INSERT INTO `item_order` VALUES ('20', '36941414400', '20', '1');
INSERT INTO `item_order` VALUES ('21', '41156689920', '22', '1');
INSERT INTO `item_order` VALUES ('22', '45338411008', '24', '1');
INSERT INTO `item_order` VALUES ('23', '49541103616', '26', '1');
INSERT INTO `item_order` VALUES ('24', '53718630400', '28', '1');
INSERT INTO `item_order` VALUES ('25', '57912934400', '30', '1');
INSERT INTO `item_order` VALUES ('26', '383590154240', '12', '2');
INSERT INTO `item_order` VALUES ('27', '1200467034112', '2', '1');

-- ----------------------------
-- Table structure for `order`
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` bigint(64) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `goods_id` int(11) DEFAULT NULL,
  `order_address` varchar(64) NOT NULL,
  `goods_count` int(11) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `order_status` tinyint(3) NOT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `pay_date` datetime DEFAULT NULL,
  `goods_name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of order
-- ----------------------------

-- ----------------------------
-- Table structure for `permit`
-- ----------------------------
DROP TABLE IF EXISTS `permit`;
CREATE TABLE `permit` (
  `id` int(11) NOT NULL,
  `permission` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of permit
-- ----------------------------

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of role
-- ----------------------------

-- ----------------------------
-- Table structure for `role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `email` varchar(25) DEFAULT NULL,
  `salt` varchar(64) NOT NULL,
  `telephone` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`user_name`),
  UNIQUE KEY `user_name_2` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=100001 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('100000', 'admin', '123', null, 'admin', null);

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_role
-- ----------------------------
