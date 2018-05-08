-- MySQL dump 10.13  Distrib 5.7.20, for Win64 (x86_64)
--
-- Host: localhost    Database: fm
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
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coursename` varchar(100) COLLATE utf8_bin NOT NULL,
  `type` int(5) DEFAULT NULL,
  `like` int(11) DEFAULT NULL,
  `location` varchar(45) COLLATE utf8_bin NOT NULL,
  `order` int(11) DEFAULT NULL,
  `isdisplay` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (13,'1.mp3',58,0,'1525770107838.mp3',1,1),(14,'2.mp3',58,0,'1525770107868.mp3',2,1),(15,'3.mp3',58,0,'1525770107875.mp3',3,1),(16,'4.mp3',58,0,'1525770107882.mp3',4,1),(17,'5.mp3',59,0,'1525770107890.mp3',1,1),(18,'6.mp3',59,0,'1525770107895.mp3',2,1),(19,'7.mp3',59,0,'1525770107902.mp3',3,1),(20,'8.mp3',59,0,'1525770107909.mp3',4,1),(21,'9.mp3',61,0,'1525770107915.mp3',1,1),(22,'10.mp3',61,0,'1525770107921.mp3',2,1),(23,'11.mp3',61,0,'1525770107927.mp3',3,1),(24,'12.mp3',61,0,'1525770107934.mp3',4,1),(25,'13.mp3',62,0,'1525770107940.mp3',1,1),(26,'14.mp3',62,0,'1525770107946.mp3',2,1),(27,'15.mp3',62,0,'1525770107952.mp3',3,1),(28,'16.mp3',62,0,'1525770107958.mp3',4,1),(29,'17.mp3',62,0,'1525770107964.mp3',5,1),(30,'18.mp3',62,0,'1525770107968.mp3',6,1),(31,'19.mp3',63,0,'1525770107973.mp3',1,1),(32,'20.mp3',63,0,'1525770107979.mp3',2,1),(33,'21.mp3',63,0,'1525770107985.mp3',3,1),(34,'22.mp3',63,0,'1525770107991.mp3',4,1),(35,'p.015-p.016.mp3',0,0,'1525782163833.mp3',0,1),(36,'p.021-p.022.mp3',0,0,'1525782300592.mp3',0,1);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager`
--

DROP TABLE IF EXISTS `manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) COLLATE utf8_bin NOT NULL,
  `password` varchar(45) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager`
--

LOCK TABLES `manager` WRITE;
/*!40000 ALTER TABLE `manager` DISABLE KEYS */;
INSERT INTO `manager` VALUES (1,'wang','wanghaiqi');
/*!40000 ALTER TABLE `manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `types`
--

DROP TABLE IF EXISTS `types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `typename` varchar(45) COLLATE utf8_bin NOT NULL,
  `typelevel` int(11) DEFAULT NULL,
  `parenttype` int(11) DEFAULT NULL,
  `iconlocation` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `isdisplay` tinyint(4) DEFAULT NULL,
  `order` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `types`
--

LOCK TABLES `types` WRITE;
/*!40000 ALTER TABLE `types` DISABLE KEYS */;
INSERT INTO `types` VALUES (41,'一年级',1,0,NULL,1,1),(42,'二年级',1,0,NULL,1,2),(43,'三年级',1,0,NULL,1,3),(44,'四年级',1,0,NULL,1,4),(45,'五年级',1,0,NULL,1,5),(46,'六年级',1,0,NULL,1,6),(47,'七年级',1,0,NULL,1,7),(58,'活力早餐',2,41,'1525776607898.jpg',1,1),(59,'早餐音乐',2,41,'1525776440626.jpg',1,2),(60,'阳春白雪',2,41,NULL,0,3),(61,'英语学习',2,41,'1525783466670.jpg',1,3),(62,'音乐鉴赏',2,41,'1525777084591.jpg',1,4),(63,'少儿英语',2,42,'1525777235906.jpg',1,1),(64,'早餐音乐',2,42,NULL,1,2),(65,'诗歌鉴赏',2,42,'1525778087581.jpg',1,3),(66,'喜剧小品',2,42,NULL,1,4),(67,'早餐音乐',2,43,'1525777273171.jpg',1,1),(68,'诗歌鉴赏',2,43,'1525777027592.jpg',1,2),(69,'少儿英语',2,43,'1525777851138.jpg',1,3),(70,'音乐欣赏',2,43,'1525778207431.jpg',1,4),(71,'早餐音乐',2,44,'1525777041805.jpg',1,1),(72,'英语学习',2,44,'1525777763321.jpg',1,2),(73,'BBC Radio1',2,44,'1525777297017.jpg',1,3),(74,'古诗词鉴赏',2,44,NULL,1,4),(75,'早餐音乐',2,45,'1525777336953.jpg',1,1),(76,'音乐鉴赏',2,45,NULL,1,2),(77,'格莱美',2,45,'1525783627292.jpg',1,3),(78,'古典音乐',2,45,NULL,1,4),(79,'古诗词鉴赏',2,45,NULL,1,5),(80,'少儿英语',2,45,NULL,1,6),(81,'摇滚乐',2,45,NULL,1,7),(82,'BBC',2,45,NULL,1,8),(83,'早餐音乐',2,46,NULL,1,1),(84,'古诗词鉴赏',2,46,'1525777459490.jpg',1,3),(85,'活力早餐',2,46,NULL,1,2),(86,'BBC',2,46,'1525777520481.jpg',1,4),(87,'活力早餐',2,47,NULL,1,1),(88,'早餐音乐',2,47,NULL,1,2),(89,'BBC',2,47,'1525777470090.jpg',1,3),(90,'音乐欣赏',2,47,'1525777673505.jpg',1,4);
/*!40000 ALTER TABLE `types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` varchar(45) COLLATE utf8_bin NOT NULL,
  `wechattoken` varchar(45) COLLATE utf8_bin NOT NULL,
  `time` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('1','1233121','0000-00-00');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userlike`
--

DROP TABLE IF EXISTS `userlike`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userlike` (
  `userid` int(11) DEFAULT NULL,
  `likeid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userlike`
--

LOCK TABLES `userlike` WRITE;
/*!40000 ALTER TABLE `userlike` DISABLE KEYS */;
/*!40000 ALTER TABLE `userlike` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-08 20:49:55
