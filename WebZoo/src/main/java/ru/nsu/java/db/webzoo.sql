/*
Navicat MySQL Data Transfer

Source Server         : HomeServer
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : webzoo

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2015-05-22 19:49:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for animals
-- ----------------------------
DROP TABLE IF EXISTS `animals`;
CREATE TABLE `animals` (
  `id_anim` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `kindId` int(5) unsigned NOT NULL,
  `comingDate` date NOT NULL,
  PRIMARY KEY (`id_anim`),
  KEY `namesIndex` (`name`) USING HASH,
  KEY `kindId` (`kindId`),
  CONSTRAINT `kindKey` FOREIGN KEY (`kindId`) REFERENCES `kinds` (`id_kind`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for areals
-- ----------------------------
DROP TABLE IF EXISTS `areals`;
CREATE TABLE `areals` (
  `id_areal` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `arealName` varchar(255) NOT NULL,
  `climateId` int(2) unsigned NOT NULL,
  PRIMARY KEY (`id_areal`),
  KEY `climateId` (`climateId`),
  CONSTRAINT `climateId` FOREIGN KEY (`climateId`) REFERENCES `climates` (`id_clim`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for climates
-- ----------------------------
DROP TABLE IF EXISTS `climates`;
CREATE TABLE `climates` (
  `id_clim` int(2) unsigned NOT NULL AUTO_INCREMENT,
  `climateName` varchar(255) NOT NULL,
  PRIMARY KEY (`id_clim`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for groups
-- ----------------------------
DROP TABLE IF EXISTS `groups`;
CREATE TABLE `groups` (
  `id_group` int(1) unsigned NOT NULL AUTO_INCREMENT,
  `nameGroup` varchar(255) NOT NULL,
  PRIMARY KEY (`id_group`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for kinds
-- ----------------------------
DROP TABLE IF EXISTS `kinds`;
CREATE TABLE `kinds` (
  `id_kind` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `nameKind` varchar(255) NOT NULL,
  `groupId` int(1) unsigned NOT NULL,
  `arealId` int(5) unsigned NOT NULL,
  PRIMARY KEY (`id_kind`),
  KEY `arealId` (`arealId`),
  KEY `groupId` (`groupId`),
  CONSTRAINT `arealKey` FOREIGN KEY (`arealId`) REFERENCES `areals` (`id_areal`),
  CONSTRAINT `groupKey` FOREIGN KEY (`groupId`) REFERENCES `groups` (`id_group`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
