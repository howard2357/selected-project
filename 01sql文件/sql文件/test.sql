/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2018-05-16 19:40:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for qqnum
-- ----------------------------
DROP TABLE IF EXISTS `qqnum`;
CREATE TABLE `qqnum` (
  `id` int(11) NOT NULL,
  `usernum` varchar(50) DEFAULT NULL,
  `mark` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qqnum
-- ----------------------------


-- ----------------------------
-- Table structure for userfriend
-- ----------------------------
DROP TABLE IF EXISTS `userfriend`;
CREATE TABLE `userfriend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usernum` varchar(50) DEFAULT NULL,
  `friendnum` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userfriend
-- ----------------------------


-- ----------------------------
-- Table structure for userinformation
-- ----------------------------
DROP TABLE IF EXISTS `userinformation`;
CREATE TABLE `userinformation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `usernum` varchar(50) DEFAULT NULL,
  `sex` varchar(5) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `birth` varchar(50) DEFAULT NULL,
  `sign` varchar(50) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `portrait` varchar(500) DEFAULT NULL,
  `port` varchar(11) DEFAULT NULL,
  `ip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinformation
-- ----------------------------
