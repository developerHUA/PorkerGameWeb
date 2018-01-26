-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: porkergame
-- ------------------------------------------------------
-- Server version	5.7.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `createDate` timestamp NULL DEFAULT NULL,
  `diamond` int(11) NOT NULL,
  `invitationCode` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lastLoginTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `openid` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `headimgurl` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `token` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `sex` int(11) DEFAULT NULL,
  `unionid` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (5,'华人科技','2018-01-18 02:36:08',8,'sd2DF2','2018-01-18 09:21:19','832','https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1516187460743&di=ef754fc0920231f52baa84ab471d49fa&imgtype=0&src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201408%2F25%2F20140825212121_NePFj.jpeg','db940602c8abb2ab55059491b065e579',1,'7421'),(6,'啦啦啦','2018-01-19 09:43:36',8,NULL,'2018-01-19 09:43:36','23421','HTTP:WWW.BAIDU.COM','ccd6f27fba324995c3066d918cdde2a8',2,'8207'),(7,'啦啦sd啦','2018-01-19 09:44:08',8,NULL,'2018-01-19 09:45:49','22421','HTTP:WWW.BAIDU.COM','71c94fba68afe9a6f4b7b19fee929a17',2,'8107'),(8,'维他命','2018-01-24 09:48:15',8,NULL,'2018-01-25 10:50:36','os4AHwVOQIwis152bgGtYRyDGz-0','http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eop9DchMSyc3ojXvibzwy36vWMibxQswXdCCSldeQhm1dHAQD4dCummNe0PKrEBCQNSiaB6rl9yPj16Q/132','ec203ec1a960048d987e302710c1864a',1,'o7OThsmLET8gsS0iHb4S4no1kQpA'),(9,'　','2018-01-26 02:45:42',8,NULL,'2018-01-26 06:48:49','os4AHwaMKOWS7ZwulcUU0YaqWQx0','http://wx.qlogo.cn/mmopen/vi_32/KO92S369q64mjjKiaaIiaialVffO5lc6eKDqaZHiamV8EmmJWpHicaOlKhrQCx607EUDhjUGQgrpZU1cklSTvXINnzQ/132','4b27d90d18de2581db90b6b6889a89ae',0,'o7OThssxTSBWabpdWMPrg1Duaphw');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-26 15:21:14
