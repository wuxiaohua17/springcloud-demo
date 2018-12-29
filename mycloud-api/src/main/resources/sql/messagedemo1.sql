/*
Navicat MySQL Data Transfer

Source Server         : docker-1
Source Server Version : 50722
Source Host           : 192.168.105.80:3306
Source Database       : messagedemo1

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-12-29 17:08:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `order_id` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  `order_amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `account_id` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  `goods_id` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  `goods_num` int(11) NOT NULL DEFAULT '1',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '1' COMMENT '1待出库2待支付3已完成',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('1', '100.00', 'zhangsan', '1', '2', '2018-12-15 19:26:28', '0');
INSERT INTO `orders` VALUES ('2', '100.00', 'zhangsan', '1', '1', '2018-12-15 19:27:06', '0');
INSERT INTO `orders` VALUES ('3', '3.00', 'zhangsan', '1', '3', '2018-12-15 19:28:33', '3');
SET FOREIGN_KEY_CHECKS=1;
