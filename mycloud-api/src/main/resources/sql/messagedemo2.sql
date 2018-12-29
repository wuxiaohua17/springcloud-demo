/*
Navicat MySQL Data Transfer

Source Server         : docker-1
Source Server Version : 50722
Source Host           : 192.168.105.80:3306
Source Database       : messagedemo2

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-12-29 17:08:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `goods_id` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  `goods_name` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  `goods_price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `goods_stock` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `goods_sale` bigint(20) DEFAULT '0' COMMENT '销量',
  PRIMARY KEY (`goods_id`),
  UNIQUE KEY `UK_goods_goods_name` (`goods_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('1', '三星NOTE10', '1000.00', '5000', '2018-12-15 19:28:35', '6');
INSERT INTO `goods` VALUES ('2', '三星NOTE9', '55.50', '1000', null, null);
SET FOREIGN_KEY_CHECKS=1;
