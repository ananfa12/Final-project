-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.6.21-log


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema ccrm
--

CREATE DATABASE IF NOT EXISTS ccrm;
USE ccrm;

--
-- Definition of table `area_details`
--

DROP TABLE IF EXISTS `area_details`;
CREATE TABLE `area_details` (
  `area_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `area_name` varchar(45) NOT NULL,
  PRIMARY KEY (`area_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `area_details`
--

/*!40000 ALTER TABLE `area_details` DISABLE KEYS */;
INSERT INTO `area_details` (`area_id`,`area_name`) VALUES 
 (1,'Tzafon'),
 (2,'Merkaz'),
 (3,'Gush Dan'),
 (4,'Darom'),
 (5,'Ayosh');
/*!40000 ALTER TABLE `area_details` ENABLE KEYS */;


--
-- Definition of table `city_details`
--

DROP TABLE IF EXISTS `city_details`;
CREATE TABLE `city_details` (
  `city_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `city_name` varchar(45) NOT NULL,
  PRIMARY KEY (`city_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `city_details`
--

/*!40000 ALTER TABLE `city_details` DISABLE KEYS */;
INSERT INTO `city_details` (`city_id`,`city_name`) VALUES 
 (1,'Tel Aviv'),
 (2,'Haifa'),
 (3,'Akko'),
 (4,'Eilat'),
 (5,'Yamit'),
 (6,'Jerusalem'),
 (7,'Afula');
/*!40000 ALTER TABLE `city_details` ENABLE KEYS */;


--
-- Definition of table `city_in_area`
--

DROP TABLE IF EXISTS `city_in_area`;
CREATE TABLE `city_in_area` (
  `area_id` int(10) unsigned NOT NULL,
  `city_id` int(10) unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `city_in_area`
--

/*!40000 ALTER TABLE `city_in_area` DISABLE KEYS */;
INSERT INTO `city_in_area` (`area_id`,`city_id`) VALUES 
 (1,2),
 (1,3),
 (1,4),
 (2,4),
 (3,1),
 (3,5),
 (1,2),
 (1,3),
 (1,7),
 (2,6),
 (3,1),
 (4,4),
 (5,5);
/*!40000 ALTER TABLE `city_in_area` ENABLE KEYS */;


--
-- Definition of table `client_comment`
--

DROP TABLE IF EXISTS `client_comment`;
CREATE TABLE `client_comment` (
  `comm_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `comm_type` int(10) unsigned NOT NULL,
  `product_id` int(10) unsigned NOT NULL,
  `client_id` int(10) unsigned NOT NULL,
  `comm_text` varchar(200) NOT NULL,
  `buy_price` int(10) unsigned NOT NULL,
  `call_len` int(10) unsigned NOT NULL,
  `op_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`comm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `client_comment`
--

/*!40000 ALTER TABLE `client_comment` DISABLE KEYS */;
INSERT INTO `client_comment` (`comm_id`,`comm_type`,`product_id`,`client_id`,`comm_text`,`buy_price`,`call_len`,`op_id`) VALUES 
 (32,0,18,1,'your stuff stink',7000,23,1),
 (33,1,18,37,'Purchased',7000,1,1),
 (34,1,1,20,'Purchased',20,48,4);
/*!40000 ALTER TABLE `client_comment` ENABLE KEYS */;


--
-- Definition of table `client_details`
--

DROP TABLE IF EXISTS `client_details`;
CREATE TABLE `client_details` (
  `client_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `client_name` varchar(45) NOT NULL,
  `client_family` varchar(45) NOT NULL,
  `client_age` int(10) unsigned NOT NULL,
  `client_city_id` int(10) unsigned NOT NULL,
  `client_phone` varchar(20) NOT NULL,
  `last_approach` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `client_rank` int(10) unsigned NOT NULL DEFAULT '1',
  `client_interest` int(10) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `client_details`
--

/*!40000 ALTER TABLE `client_details` DISABLE KEYS */;
INSERT INTO `client_details` (`client_id`,`client_name`,`client_family`,`client_age`,`client_city_id`,`client_phone`,`last_approach`,`client_rank`,`client_interest`) VALUES 
 (1,'Moses','Cohen',28,4,'0508123400','2015-01-19 11:19:16',1,1),
 (2,'Nisim','Levi',30,1,'0508123401','0000-00-00 00:00:00',1,1),
 (18,'Avi','Halevi',34,6,'0508123402','0000-00-00 00:00:00',1,1),
 (19,'Dor','Aharon',55,7,'0508123403','0000-00-00 00:00:00',1,1),
 (20,'Ifat','Ayalon',26,2,'0508123404','2015-01-21 11:39:16',1,1),
 (21,'Efrat','Hazan',36,3,'0508123405','0000-00-00 00:00:00',1,1),
 (22,'Michal','Mizrachi',44,5,'0508123406','0000-00-00 00:00:00',1,1),
 (23,'Michael','Davidovich',63,2,'0508123407','0000-00-00 00:00:00',1,1),
 (24,'Hofit','Minishin',22,6,'0508123408','0000-00-00 00:00:00',1,1),
 (25,'Tsuf','Crown',33,1,'0508123409','0000-00-00 00:00:00',1,1),
 (26,'Eli','Brown',53,4,'0508123410','0000-00-00 00:00:00',1,1),
 (27,'Alex','Black',22,3,'0508123411','0000-00-00 00:00:00',1,1),
 (28,'Yoni','Winslow',23,7,'0508123412','0000-00-00 00:00:00',1,1),
 (29,'Avi','Bundy',23,2,'0508123413','0000-00-00 00:00:00',1,1),
 (30,'Avraham','Volkov',32,6,'0508123414','0000-00-00 00:00:00',1,1),
 (31,'Alon','Musa',22,4,'0508123415','0000-00-00 00:00:00',1,1),
 (32,'Ilan','Ziv',26,6,'0508123416','0000-00-00 00:00:00',1,1),
 (33,'Alona','Ziv',61,7,'0508123417','0000-00-00 00:00:00',1,1),
 (34,'Ilana','Cohen',23,2,'0508123418','0000-00-00 00:00:00',1,1),
 (35,'Ilona','Halevi',23,1,'0508123419','0000-00-00 00:00:00',1,1),
 (36,'Orna','Levi',23,3,'0508123420','0000-00-00 00:00:00',1,1),
 (37,'Shir','White',38,4,'0508123421','2015-01-19 11:19:44',1,1),
 (38,'Sharon','Amon',40,2,'0508123422','0000-00-00 00:00:00',1,1),
 (39,'Vered','Val',43,4,'0508123423','0000-00-00 00:00:00',1,1),
 (40,'Alon','Shuster',19,2,'0508123424','0000-00-00 00:00:00',1,1),
 (41,'Gidi','Gov',20,6,'0508123425','0000-00-00 00:00:00',1,1),
 (42,'Dima','Gov',32,4,'0508123426','0000-00-00 00:00:00',1,1),
 (43,'Alex','Bard',40,1,'0508123427','0000-00-00 00:00:00',1,1),
 (44,'Meni','Lenin',43,6,'0508123428','0000-00-00 00:00:00',1,1),
 (45,'Viki','Stalin',20,4,'0508123429','0000-00-00 00:00:00',1,1),
 (46,'Jana','Kennedy',20,5,'0508123430','0000-00-00 00:00:00',1,1),
 (47,'Marina','Rosevelt',23,3,'0508123431','0000-00-00 00:00:00',1,1),
 (48,'Ron','Washington',20,4,'0508123432','0000-00-00 00:00:00',1,1),
 (49,'Lina','Moore',30,6,'0508123433','0000-00-00 00:00:00',1,1),
 (50,'Alina','Jefferson',35,2,'0508123434','0000-00-00 00:00:00',1,1),
 (51,'Hani','Sror',28,3,'0501234321','0000-00-00 00:00:00',1,1),
 (52,'Gonen','Feldman',45,6,'0812332147','0000-00-00 00:00:00',1,1),
 (53,'Sivan','Gabay',23,4,'092316542','0000-00-00 00:00:00',1,1),
 (54,'Hana','Gur',43,5,'031231231','0000-00-00 00:00:00',1,1),
 (55,'Simon','Soro',31,7,'051235555','0000-00-00 00:00:00',1,1);
/*!40000 ALTER TABLE `client_details` ENABLE KEYS */;


--
-- Definition of table `field_details`
--

DROP TABLE IF EXISTS `field_details`;
CREATE TABLE `field_details` (
  `field_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `field_name` varchar(45) NOT NULL,
  PRIMARY KEY (`field_id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `field_details`
--

/*!40000 ALTER TABLE `field_details` DISABLE KEYS */;
INSERT INTO `field_details` (`field_id`,`field_name`) VALUES 
 (1,'Picknik'),
 (2,'Tools'),
 (3,'Kitchen tools'),
 (4,'Computers'),
 (5,'Shower'),
 (6,'Garden'),
 (7,'Musical_instruments'),
 (8,'TV'),
 (9,'Paint'),
 (10,'Light'),
 (11,'Art'),
 (12,'Chairs'),
 (13,'Bricks'),
 (14,'Stones'),
 (15,'Light Bolbs'),
 (16,'Science'),
 (65,'Bottles'),
 (66,'Plastic');
/*!40000 ALTER TABLE `field_details` ENABLE KEYS */;


--
-- Definition of table `list_details`
--

DROP TABLE IF EXISTS `list_details`;
CREATE TABLE `list_details` (
  `list_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `operation_id` int(10) unsigned NOT NULL,
  `salesman_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`list_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `list_details`
--

/*!40000 ALTER TABLE `list_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `list_details` ENABLE KEYS */;


--
-- Definition of table `list_to_client`
--

DROP TABLE IF EXISTS `list_to_client`;
CREATE TABLE `list_to_client` (
  `list_id` int(10) unsigned NOT NULL,
  `client_id` int(10) unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `list_to_client`
--

/*!40000 ALTER TABLE `list_to_client` DISABLE KEYS */;
/*!40000 ALTER TABLE `list_to_client` ENABLE KEYS */;


--
-- Definition of table `operation_details`
--

DROP TABLE IF EXISTS `operation_details`;
CREATE TABLE `operation_details` (
  `op_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `op_name` varchar(45) NOT NULL,
  `op_pattern_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`op_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `operation_details`
--

/*!40000 ALTER TABLE `operation_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `operation_details` ENABLE KEYS */;


--
-- Definition of table `pattern_details`
--

DROP TABLE IF EXISTS `pattern_details`;
CREATE TABLE `pattern_details` (
  `pattern_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pattern_product_id` int(10) unsigned NOT NULL,
  `pattern_slice_id` int(10) unsigned NOT NULL,
  `pattern_message` varchar(100) NOT NULL,
  `pattern_name` varchar(45) NOT NULL,
  PRIMARY KEY (`pattern_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `pattern_details`
--

/*!40000 ALTER TABLE `pattern_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `pattern_details` ENABLE KEYS */;


--
-- Definition of table `permission_user_field`
--

DROP TABLE IF EXISTS `permission_user_field`;
CREATE TABLE `permission_user_field` (
  `client_id` int(10) unsigned NOT NULL,
  `field_id` int(10) unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `permission_user_field`
--

/*!40000 ALTER TABLE `permission_user_field` DISABLE KEYS */;
INSERT INTO `permission_user_field` (`client_id`,`field_id`) VALUES 
 (42,4),
 (32,3),
 (28,1),
 (42,1),
 (37,6),
 (38,6),
 (39,6),
 (37,1),
 (37,0),
 (39,5),
 (37,0),
 (1,0),
 (1,1),
 (2,1),
 (3,1),
 (4,1),
 (5,1),
 (6,1),
 (7,1),
 (8,1),
 (9,1),
 (10,1),
 (11,1),
 (12,1),
 (13,1),
 (14,1),
 (15,1),
 (16,1),
 (17,1),
 (18,1),
 (19,1),
 (20,1),
 (21,1),
 (22,1),
 (23,1),
 (24,1),
 (25,1),
 (26,1),
 (25,1),
 (27,1),
 (28,1),
 (29,1),
 (30,1),
 (31,1),
 (32,1),
 (33,1),
 (34,1),
 (35,1),
 (1,2),
 (1,3),
 (2,2),
 (3,2),
 (4,2),
 (5,2),
 (6,2),
 (7,2),
 (8,2),
 (8,2),
 (9,2),
 (10,2),
 (11,2),
 (12,2),
 (13,2),
 (14,2),
 (15,2),
 (16,2),
 (17,2),
 (18,2),
 (19,2),
 (20,2),
 (21,2),
 (22,2),
 (23,2),
 (24,2),
 (25,2),
 (26,2),
 (27,2),
 (27,2),
 (28,2),
 (29,2),
 (30,2),
 (31,2),
 (32,2),
 (33,2),
 (34,2),
 (35,2),
 (10,3),
 (11,3),
 (12,3),
 (13,3),
 (14,3),
 (15,3),
 (16,3),
 (17,3),
 (18,3),
 (19,3),
 (20,3),
 (21,3),
 (22,3),
 (23,3),
 (24,3),
 (25,3),
 (26,3),
 (27,3),
 (28,3),
 (29,3),
 (30,3),
 (31,3),
 (32,3),
 (33,3),
 (34,3),
 (35,3),
 (20,4),
 (21,4),
 (22,4),
 (23,4),
 (24,4),
 (25,4),
 (26,4),
 (27,4),
 (28,4),
 (29,4),
 (30,4),
 (31,4),
 (32,4),
 (33,4),
 (34,4),
 (35,4),
 (24,5),
 (25,5),
 (26,5),
 (27,5),
 (28,5),
 (29,5),
 (30,5),
 (31,5),
 (32,5),
 (33,5),
 (34,5),
 (35,5),
 (30,6),
 (31,6),
 (32,6),
 (33,6),
 (34,6),
 (35,6),
 (1,3),
 (1,7),
 (2,6),
 (3,1),
 (4,1),
 (4,4),
 (5,5),
 (37,3),
 (37,7),
 (32,6),
 (1,9),
 (19,0);
/*!40000 ALTER TABLE `permission_user_field` ENABLE KEYS */;


--
-- Definition of table `product_details`
--

DROP TABLE IF EXISTS `product_details`;
CREATE TABLE `product_details` (
  `product_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `product_name` varchar(45) NOT NULL,
  `product_desc` varchar(100) NOT NULL,
  `product_img` varchar(45) NOT NULL,
  `product_price` int(10) unsigned NOT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `product_details`
--

/*!40000 ALTER TABLE `product_details` DISABLE KEYS */;
INSERT INTO `product_details` (`product_id`,`product_name`,`product_desc`,`product_img`,`product_price`) VALUES 
 (1,'Rachet 1/4','Rachet size 1/4','Socks',20),
 (2,'Rachet 1/2','Rachet size 1/2','default',20),
 (3,'Rachet 1/8','Rachet size 1/8','default',20),
 (4,'Rachet 1/16','Rachet  size 1/16','default',20),
 (5,'Rachet 5/8','Rachet size 5/8','default',20),
 (6,'Rachet 9/16','Rachet  9/16','default',20),
 (7,'Bit 1/4','Bit size 1/4','default',12),
 (8,'Bit 1/2','Bit size 1/2','default',12),
 (9,'Bit 1/8','Bit size 1/8','default',12),
 (10,'Bit 1/16','Bit size 1/16','default',12),
 (11,'Bit 5/8','Bit size 5/8','default',12),
 (12,'Bit 9/16','Bit size 9/16','default',12),
 (13,'Bit buxa','For bits','default',16),
 (14,'Piano','Yamaha','default',4999),
 (15,'Electric Guitar','Fender','default',1200),
 (16,'Acustic guitar','Yamaha','default',599),
 (17,'Bass Guitar','Gibson','default',2000),
 (18,'Violin','Vivaldi','default',7000),
 (19,'Toshiba TV','Toshiba 32 inch HD','default',1400),
 (20,'LG Laptop','LG 15.4 i5','default',2000),
 (21,'Lenovo Laptop','Lenovo 15.4 i5','default',2100),
 (22,'Computer Mouse','Logitech','Computer Mouse',40),
 (23,'Hammer','Good Hammer','Hammer',30),
 (24,'Lamp','Good Lamp','Lamp',12),
 (25,'Gloves','Hand Gloves','Gloves',23),
 (26,'Socks','Military Socks','Socks',8),
 (27,'Snickers','Sheos','Snickers',34);
/*!40000 ALTER TABLE `product_details` ENABLE KEYS */;


--
-- Definition of table `product_in_field`
--

DROP TABLE IF EXISTS `product_in_field`;
CREATE TABLE `product_in_field` (
  `product_id` int(10) unsigned NOT NULL,
  `field_id` int(10) unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `product_in_field`
--

/*!40000 ALTER TABLE `product_in_field` DISABLE KEYS */;
INSERT INTO `product_in_field` (`product_id`,`field_id`) VALUES 
 (0,0),
 (0,0),
 (4,2),
 (4,3),
 (4,1),
 (19,6),
 (1,2),
 (2,2),
 (3,2),
 (4,2),
 (5,2),
 (6,2),
 (7,2),
 (8,2),
 (9,2),
 (10,2),
 (11,2),
 (12,2),
 (13,2),
 (14,7),
 (15,7),
 (16,7),
 (17,7),
 (18,7),
 (19,8),
 (20,4),
 (21,4),
 (1,16),
 (1,17),
 (4,40),
 (1,1),
 (1,1);
/*!40000 ALTER TABLE `product_in_field` ENABLE KEYS */;


--
-- Definition of table `product_in_type`
--

DROP TABLE IF EXISTS `product_in_type`;
CREATE TABLE `product_in_type` (
  `product_id` int(10) unsigned NOT NULL,
  `type_id` int(10) unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `product_in_type`
--

/*!40000 ALTER TABLE `product_in_type` DISABLE KEYS */;
INSERT INTO `product_in_type` (`product_id`,`type_id`) VALUES 
 (0,0),
 (0,0),
 (4,5),
 (19,6);
/*!40000 ALTER TABLE `product_in_type` ENABLE KEYS */;


--
-- Definition of table `slice_details`
--

DROP TABLE IF EXISTS `slice_details`;
CREATE TABLE `slice_details` (
  `slice_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `slice_name` varchar(45) NOT NULL,
  `slice_min_age` int(10) unsigned NOT NULL,
  `slice_max_age` int(10) unsigned NOT NULL,
  `slice_area_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`slice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `slice_details`
--

/*!40000 ALTER TABLE `slice_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `slice_details` ENABLE KEYS */;


--
-- Definition of table `type_details`
--

DROP TABLE IF EXISTS `type_details`;
CREATE TABLE `type_details` (
  `type_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type_name` varchar(45) NOT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `type_details`
--

/*!40000 ALTER TABLE `type_details` DISABLE KEYS */;
INSERT INTO `type_details` (`type_id`,`type_name`) VALUES 
 (1,'newtype'),
 (2,'newtype2'),
 (3,'newtype3'),
 (4,'asd'),
 (5,'woot'),
 (6,''),
 (7,'hahaha'),
 (8,'x'),
 (9,'asda'),
 (10,'wer'),
 (11,'safdsa'),
 (12,'as'),
 (13,'type123');
/*!40000 ALTER TABLE `type_details` ENABLE KEYS */;


--
-- Definition of table `user_details`
--

DROP TABLE IF EXISTS `user_details`;
CREATE TABLE `user_details` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) NOT NULL,
  `user_pass` varchar(45) NOT NULL,
  `user_role` int(1) unsigned NOT NULL,
  `connected_now` int(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_details`
--

/*!40000 ALTER TABLE `user_details` DISABLE KEYS */;
INSERT INTO `user_details` (`user_id`,`user_name`,`user_pass`,`user_role`,`connected_now`) VALUES 
 (1,'a','1',4,0),
 (2,'b','2',1,0),
 (3,'c','3',2,0),
 (4,'d','4',3,1),
 (5,'e','5',3,0),
 (6,'f','6',3,0);
/*!40000 ALTER TABLE `user_details` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
