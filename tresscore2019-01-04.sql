-- MySQL dump 10.13  Distrib 5.5.28, for Win32 (x86)
--
-- Host: localhost    Database: tresscore
-- ------------------------------------------------------
-- Server version	5.5.28

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
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity` (
  `ACTIVITY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `createdBy` varchar(255) DEFAULT NULL,
  `crtdDtTime` datetime DEFAULT NULL,
  `genericStatus` varchar(255) DEFAULT NULL,
  `optLock` datetime DEFAULT NULL,
  `upDtTime` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `creationDate` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `weight` varchar(255) DEFAULT NULL,
  `task` int(11) DEFAULT NULL,
  `user` int(11) DEFAULT NULL,
  `dueDate` datetime DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ACTIVITY_ID`),
  KEY `FKA126572FE0C2A6D2` (`task`),
  KEY `FKA126572F314EB70B` (`user`),
  CONSTRAINT `FKA126572F314EB70B` FOREIGN KEY (`user`) REFERENCES `users` (`userId`),
  CONSTRAINT `FKA126572FE0C2A6D2` FOREIGN KEY (`task`) REFERENCES `task` (`taskId`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` VALUES (1,'Ange Mutesi','2018-11-25 15:02:12','active',NULL,'2018-12-19 11:44:43','ange','2018-11-25 15:02:12','road construction ','Approved','Medium',2,2,'2018-11-30 02:00:00','2018-11-30 02:00:00','2018-11-28 02:00:00','Normal'),(2,'Ange Mutesi','2018-12-13 10:32:52','active',NULL,'2018-12-14 10:11:48','ange','2018-12-13 10:32:52','User login form design plus back end','Rejected','Short',2,2,NULL,NULL,NULL,'Normal'),(3,'Ange Mutesi','2018-12-13 17:06:12','active',NULL,'2018-12-19 11:50:12','ange','2018-12-13 17:06:12','Creating sign up form','planned','Medium',2,2,NULL,NULL,NULL,'Normal'),(4,'Ange Mutesi','2018-12-17 11:11:29','active',NULL,'2018-12-17 17:01:27','ange','2018-12-17 11:11:29','Design','Rejected','Short',2,2,NULL,NULL,NULL,'Vfp'),(5,'Emma Sibo','2018-12-17 17:39:08','active',NULL,'2018-12-19 09:09:19','rep','2018-12-17 17:39:08','Roading cleaning','Completed','Short',1,3,'2018-12-26 10:48:24',NULL,'2018-12-19 10:48:10','Vfp'),(6,'Ange Mutesi','2018-12-18 11:13:21','active',NULL,'2018-12-18 18:28:42','ange','2018-12-18 11:13:21','Usercategory form design','Approved','Medium',2,2,NULL,NULL,NULL,'Vfp'),(7,'Emma Sibo','2018-12-18 11:16:48','active',NULL,'2018-12-19 13:04:20','rep','2018-12-18 11:16:48','Water distribution during road clearance','Completed','Short',1,3,'2018-12-26 11:43:52',NULL,'2018-12-19 11:43:45','Vfp'),(8,'Alain Muhire','2018-12-19 14:59:57','active',NULL,'2018-12-19 15:18:40','alain','2018-12-19 14:59:57','Land preparation','done','Medium',3,5,'2018-12-26 15:08:38',NULL,'2018-12-19 15:07:02','Normal'),(9,'Alain Muhire','2018-12-19 15:02:12','active',NULL,'2018-12-19 15:23:26','alain','2018-12-19 15:02:12','Excavation','done','Medium',3,5,'2018-12-26 15:10:03',NULL,'2018-12-19 15:07:02','Vfp'),(10,'Alain Muhire','2018-12-19 15:03:18','active',NULL,'2018-12-19 15:05:32','alain','2018-12-19 15:03:18','Digging foundation holes','Rejected','Short',3,5,NULL,NULL,NULL,'Normal'),(11,'Alain Muhire','2018-12-19 15:03:18','active',NULL,'2018-12-19 15:03:18','Alain Muhire','2018-12-19 15:03:18','Making walls','Not Started','Long',3,5,NULL,NULL,NULL,'Normal'),(12,'Alain Muhire','2018-12-19 15:03:18','active',NULL,'2018-12-19 15:03:18','Alain Muhire','2018-12-19 15:03:18','Making roof','Not Started','Medium',3,5,NULL,NULL,NULL,'Normal'),(13,'Alain Muhire','2018-12-19 15:03:18','active',NULL,'2018-12-19 15:03:18','Alain Muhire','2018-12-19 15:03:18','Painting walls','Not Started','Medium',3,5,NULL,NULL,NULL,'Vfp'),(14,'Uwera Aline','2019-01-04 15:36:17','active',NULL,'2019-01-04 15:41:58','Aline','2019-01-04 15:36:17','User sign up form','Completed','Short',4,24,'2019-01-11 15:38:28',NULL,'2019-01-04 15:38:10','Normal'),(15,'Uwera Aline','2019-01-04 15:36:17','active',NULL,'2019-01-04 15:36:17','Uwera Aline','2019-01-04 15:36:17','Login form design ','Not Started','Short',4,24,NULL,NULL,NULL,'Normal');
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activitycomment`
--

DROP TABLE IF EXISTS `activitycomment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activitycomment` (
  `commentActId` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdBy` varchar(255) DEFAULT NULL,
  `crtdDtTime` datetime DEFAULT NULL,
  `genericStatus` varchar(255) DEFAULT NULL,
  `optLock` datetime DEFAULT NULL,
  `upDtTime` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `activity` int(11) DEFAULT NULL,
  `comment` int(11) DEFAULT NULL,
  PRIMARY KEY (`commentActId`),
  KEY `FK1C8F8C304A5AFDE6` (`activity`),
  KEY `FK1C8F8C30C462ABF6` (`comment`),
  CONSTRAINT `FK1C8F8C304A5AFDE6` FOREIGN KEY (`activity`) REFERENCES `activity` (`ACTIVITY_ID`),
  CONSTRAINT `FK1C8F8C30C462ABF6` FOREIGN KEY (`comment`) REFERENCES `comment` (`commentId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activitycomment`
--

LOCK TABLES `activitycomment` WRITE;
/*!40000 ALTER TABLE `activitycomment` DISABLE KEYS */;
INSERT INTO `activitycomment` VALUES (1,'ange','2018-12-16 14:14:03','active',NULL,NULL,NULL,2,1),(2,'ange','2018-12-16 16:39:27','active',NULL,'2018-12-16 16:39:27','ange',2,2),(3,'ange','2018-12-16 20:19:43','active',NULL,'2018-12-16 20:19:43','ange',2,5),(6,'ange','2018-12-17 08:22:04','active',NULL,'2018-12-17 08:22:04','ange',2,8),(7,NULL,'2018-12-19 09:31:42','active',NULL,NULL,NULL,7,9);
/*!40000 ALTER TABLE `activitycomment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `board` (
  `boardId` int(11) NOT NULL AUTO_INCREMENT,
  `createdBy` varchar(255) DEFAULT NULL,
  `crtdDtTime` datetime DEFAULT NULL,
  `genericStatus` varchar(255) DEFAULT NULL,
  `optLock` datetime DEFAULT NULL,
  `upDtTime` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `boardName` varchar(255) DEFAULT NULL,
  `creationDate` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `board` int(11) DEFAULT NULL,
  `institution` int(11) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`boardId`),
  UNIQUE KEY `boardName` (`boardName`),
  KEY `FK3D5FEC6AA7FBEA8` (`institution`),
  KEY `FK3D5FEC635A31B04` (`board`),
  CONSTRAINT `FK3D5FEC635A31B04` FOREIGN KEY (`board`) REFERENCES `board` (`boardId`),
  CONSTRAINT `FK3D5FEC6AA7FBEA8` FOREIGN KEY (`institution`) REFERENCES `institution` (`institutionId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` VALUES (1,NULL,NULL,'active',NULL,NULL,NULL,'CEO',NULL,'Chief executif officer Board',NULL,1,'active'),(2,NULL,NULL,'active',NULL,NULL,NULL,'Business Admin',NULL,'Concern institution Administration',NULL,1,'active'),(3,'rep','2018-11-02 17:16:35','active',NULL,'2018-11-02 17:16:35','rep','Finance Office',NULL,'In charge of institution Administration',2,4,'active'),(4,'Leonard','2018-11-25 13:54:49','active',NULL,'2018-11-25 13:54:49','Leonard','Procurement',NULL,'in charge of company materials',2,12,'active'),(5,'Leonard','2018-11-25 13:59:59','active',NULL,'2018-11-25 13:59:59','Leonard','it',NULL,'fhdfh',1,4,'active'),(6,'sda','2019-01-04 14:16:01','active',NULL,'2019-01-04 14:16:01','sda','ict',NULL,'Board for managing company ressourse',5,17,'active');
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cell`
--

DROP TABLE IF EXISTS `cell`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cell` (
  `cellId` int(11) NOT NULL AUTO_INCREMENT,
  `Code` varchar(255) DEFAULT NULL,
  `cellName_en` varchar(255) DEFAULT NULL,
  `cellName_fr` varchar(255) DEFAULT NULL,
  `cellName_rw` varchar(255) DEFAULT NULL,
  `sector` int(11) DEFAULT NULL,
  PRIMARY KEY (`cellId`),
  KEY `FK1FFD82B7AD1894` (`sector`),
  CONSTRAINT `FK1FFD82B7AD1894` FOREIGN KEY (`sector`) REFERENCES `sector` (`sectorId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cell`
--

LOCK TABLES `cell` WRITE;
/*!40000 ALTER TABLE `cell` DISABLE KEYS */;
INSERT INTO `cell` VALUES (1,'RKRSTR01','Rukiri','Rukiri','Rukiri',1),(2,'KVGSTR01','Kivugiza','Kivugiza','Kivugiza',2);
/*!40000 ALTER TABLE `cell` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `commentId` int(11) NOT NULL AUTO_INCREMENT,
  `createdBy` varchar(255) DEFAULT NULL,
  `crtdDtTime` datetime DEFAULT NULL,
  `genericStatus` varchar(255) DEFAULT NULL,
  `optLock` datetime DEFAULT NULL,
  `upDtTime` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`commentId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,NULL,'2018-12-16 14:13:09','active',NULL,NULL,NULL,'please you can not plan only one activity in one week'),(2,'ange','2018-12-16 16:39:27','active',NULL,NULL,NULL,'Yes I will try to make it clean by expanding this activity'),(3,'ange','2018-12-16 19:58:31','active',NULL,NULL,NULL,'What I have said above I want to add that I will also design signup form'),(4,'ange','2018-12-16 19:58:31','active',NULL,NULL,NULL,'What I have said above I want to add that I will also design signup form'),(5,'ange','2018-12-16 20:19:43','active',NULL,NULL,NULL,'What I have said above I will also implement signup form '),(8,'ange','2018-12-17 08:22:04','active',NULL,NULL,NULL,'Hello Ange I want to remind you that I have added Registration form in my weekly plan'),(9,NULL,'2018-12-19 09:28:42','active',NULL,NULL,NULL,'please try to make this activity clear');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact` (
  `contactId` int(11) NOT NULL AUTO_INCREMENT,
  `createdBy` varchar(255) DEFAULT NULL,
  `crtdDtTime` datetime DEFAULT NULL,
  `genericStatus` varchar(255) DEFAULT NULL,
  `optLock` datetime DEFAULT NULL,
  `upDtTime` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `contactDetails` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `user` int(11) DEFAULT NULL,
  `pobox` varchar(255) DEFAULT NULL,
  `institution` int(11) DEFAULT NULL,
  PRIMARY KEY (`contactId`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `phone` (`phone`),
  UNIQUE KEY `pobox` (`pobox`),
  KEY `FK9BEFBC00314EB70B` (`user`),
  KEY `FK9BEFBC00AA7FBEA8` (`institution`),
  CONSTRAINT `FK9BEFBC00314EB70B` FOREIGN KEY (`user`) REFERENCES `users` (`userId`),
  CONSTRAINT `FK9BEFBC00AA7FBEA8` FOREIGN KEY (`institution`) REFERENCES `institution` (`institutionId`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` VALUES (4,'rep','2018-10-01 00:00:00','active',NULL,'2019-01-04 11:50:37','Tres',NULL,'Emile@gmail.com','0722886392',1,NULL,NULL),(5,'rep','2018-10-05 00:00:00','active',NULL,'2018-10-22 19:09:53','rep','Airtel','Alain@gmail.com','0736107905',5,NULL,NULL),(6,'rep','2018-10-10 00:00:00','active',NULL,'2018-10-22 19:10:38','rep','MTN','Alice@gmail.com','0782234029',7,NULL,NULL),(7,'rep','2018-10-31 23:40:32','active',NULL,'2018-10-31 23:40:32','rep','Airtel','fab@gmail.com','0739289209',6,NULL,NULL),(11,'rep','2018-10-25 00:00:00','active',NULL,'2018-11-01 14:49:37','rep','TIGO','sibo2530@gmail.com','0722886304',4,NULL,NULL),(13,'rep','2018-11-01 16:15:10','active',NULL,'2018-11-01 16:15:10','rep','Airtel','emmzzo2018@gmail.com','0733402901',4,NULL,NULL),(17,'admin','2018-11-05 23:46:04','active',NULL,'2018-11-05 23:46:04','admin','RwandaTel','sibo4025@gmail.com','073020201',8,NULL,NULL),(18,'admin','2018-11-06 01:34:57','active',NULL,'2018-11-06 01:41:39','admin','TIGO','emmzzo254@gmail.com','0722994592',8,NULL,NULL),(19,'admin','2018-11-06 01:55:24','active',NULL,'2018-11-06 01:55:24','admin','MTN','emmzzo2520@gmail.com','0783324029',8,NULL,NULL),(20,'rep','2018-11-06 16:08:16','active',NULL,'2018-11-09 11:19:54','rep','MTN','sibo2040@gmail.com','0786023402',11,NULL,NULL),(21,'admin','2018-11-06 16:26:28','active',NULL,'2018-11-06 16:26:28','admin','TIGO','emmzzo2540@gmail.com','0722786903',12,NULL,NULL),(22,'rep','2018-11-09 11:20:44','active',NULL,'2018-11-13 11:48:22','Tres','MTN','sibo254@gmail.com','0785029901',3,NULL,NULL),(23,'Tres','2018-11-13 10:15:38','active',NULL,'2018-11-13 10:15:38','Tres','TIGO','karangwa@gmail.com','07225054401',12,NULL,NULL),(24,'Tres','2018-11-13 10:56:25','active',NULL,'2018-11-13 10:56:25','Tres','MTN','egide@gmail.com','078454588',9,NULL,NULL),(25,'Tres','2018-11-12 00:00:00','active',NULL,'2018-11-13 11:48:57','Tres','MTN','sibo2540@gmail.com','0784008801',3,NULL,NULL),(26,'Tres','2018-11-25 13:29:19','active',NULL,'2018-11-25 13:29:19','Tres',NULL,'auca@gmail.com','074884993',NULL,'2018',10),(27,'admin','2018-11-25 13:40:45','active',NULL,'2018-11-25 13:40:45','admin','MTN','leonambo@gmail.com','0788409447',19,NULL,NULL),(28,'Tres','2018-12-28 09:49:59','active',NULL,'2018-12-28 09:49:59','Tres','MTN','keza@gmail.com','0784994004',2,NULL,NULL),(29,'admin','2019-01-04 12:58:55','active',NULL,'2019-01-04 12:58:55','admin',NULL,'juniorngango@gmail.com','0784994494',17,NULL,NULL),(30,'admin','2019-01-04 13:02:23','active',NULL,'2019-01-04 13:02:23','admin',NULL,'bellypatric@gmail.com','0783993393',20,NULL,NULL),(31,'admin','2019-01-04 13:24:14','active',NULL,'2019-01-04 13:24:14','admin',NULL,'Sda2019@gmail.com','078484994',22,NULL,NULL),(32,'Tres','2019-01-04 13:48:48','active',NULL,'2019-01-04 13:48:48','Tres',NULL,'teteri@gmail.com','+(250)783993386',10,NULL,NULL),(33,'sda','2019-01-04 14:28:29','active',NULL,'2019-01-04 14:28:29','sda',NULL,'Ntwali@gmail.com','+(225)89024502',23,NULL,NULL),(34,'sda','2019-01-04 14:31:32','active',NULL,'2019-01-04 14:31:32','sda',NULL,'aline@gmail.com','+(250)782234029',24,NULL,NULL);
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country` (
  `countryId` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `countryName_en` varchar(255) DEFAULT NULL,
  `countryName_fr` varchar(255) DEFAULT NULL,
  `countryName_rw` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`countryId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES (1,'AFR01','Rwanda','Rwanda','Rwanda'),(2,'AFR02','UGANDA','UGANDA','UGANDA'),(3,'AFR03','TANZANIA','TANZANIE','TANZANIYA');
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `district`
--

DROP TABLE IF EXISTS `district`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `district` (
  `districtId` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `districtName_en` varchar(255) DEFAULT NULL,
  `districtName_fr` varchar(255) DEFAULT NULL,
  `districtName_rw` varchar(255) DEFAULT NULL,
  `province` int(11) DEFAULT NULL,
  PRIMARY KEY (`districtId`),
  KEY `FK151397AE9A0B6E28` (`province`),
  CONSTRAINT `FK151397AE9A0B6E28` FOREIGN KEY (`province`) REFERENCES `province` (`provenceId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `district`
--

LOCK TABLES `district` WRITE;
/*!40000 ALTER TABLE `district` DISABLE KEYS */;
INSERT INTO `district` VALUES (1,'NYSTR01','Nyarugenge','Nyarugenge','Nyarugenge',1),(2,'GSBSTR02','Gasabo','Gasabo','Gasabo',1);
/*!40000 ALTER TABLE `district` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documents`
--

DROP TABLE IF EXISTS `documents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `documents` (
  `DocId` int(11) NOT NULL AUTO_INCREMENT,
  `createdBy` varchar(255) DEFAULT NULL,
  `crtdDtTime` datetime DEFAULT NULL,
  `genericStatus` varchar(255) DEFAULT NULL,
  `optLock` datetime DEFAULT NULL,
  `upDtTime` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `documentLoc` varchar(255) DEFAULT NULL,
  `fileSize` bigint(20) DEFAULT NULL,
  `originalFileName` varchar(255) DEFAULT NULL,
  `sysFilename` varchar(255) DEFAULT NULL,
  `validDocCode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DocId`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documents`
--

LOCK TABLES `documents` WRITE;
/*!40000 ALTER TABLE `documents` DISABLE KEYS */;
INSERT INTO `documents` VALUES (29,NULL,'2018-11-05 10:43:39',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',14239,'upload.PNG','5ae32af4-cba8-4a96-90c9-e1d198f6ec5f.upload.PNG','PROFILEIMAGE'),(30,NULL,'2018-11-05 10:45:57',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',34523,'us.png','75d0f9dd-4bd2-43a2-abac-f92b93b1b566.us.png','PROFILEIMAGE'),(31,NULL,'2018-11-05 11:39:51',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',26667,'SampleForm.PNG','b663443e-f96b-4c7e-a0f7-4496e5b0d76d.SampleForm.PNG','PROFILEIMAGE'),(32,NULL,'2018-11-05 11:53:47',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',34523,'us.png','7e9db7b5-f907-47a3-b44c-dfd2f0ae6ee2.us.png','PROFILEIMAGE'),(33,NULL,'2018-11-05 11:56:31',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',49288,'CROP_1532024686389.jpg','3cacb105-b32c-4a2c-bd89-16f97bf4ea08.CROP_1532024686389.jpg','PROFILEIMAGE'),(34,NULL,'2018-11-05 11:57:51',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',34523,'us.png','2d3c5442-78eb-4341-b484-43c3ebbbda05.us.png','PROFILEIMAGE'),(35,NULL,'2018-11-05 11:58:43',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',49288,'CROP_1532024686389.jpg','88ed8874-be96-4981-9b32-9dd0966787f1.CROP_1532024686389.jpg','PROFILEIMAGE'),(36,NULL,'2018-11-06 16:11:20',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',34523,'us.png','91cd9cb4-050e-47ef-abad-a8aa814569e3.us.png','PROFILEIMAGE'),(37,NULL,'2018-11-06 16:39:09',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',49288,'CROP_1532024686389.jpg','d2c33c17-316c-459b-a5b0-fd6ae2581889.CROP_1532024686389.jpg','PROFILEIMAGE'),(38,NULL,'2018-11-08 19:21:17',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',34523,'us.png','685a6acf-b4ec-48c7-b889-a2bdf0bbdc0c.us.png','PROFILEIMAGE'),(39,NULL,'2018-11-08 19:33:38',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',90864,'mifotra.PNG','a0d5cb2b-0ba4-4ffe-ae6c-2ad8600099ca.mifotra.PNG','PROFILEIMAGE'),(40,NULL,'2018-11-11 15:11:25',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',34523,'us.png','5d9b19ae-bfb7-42c4-bc9d-26bdec219259.us.png','PROFILEIMAGE'),(41,NULL,'2018-11-11 23:47:21',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',49288,'CROP_1532024686389.jpg','88756e5b-455f-4934-aa11-5b758558543c.CROP_1532024686389.jpg','PROFILEIMAGE'),(42,NULL,'2018-11-12 00:02:48',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',34523,'us.png','7728b48c-da65-42de-9cdf-7a528c6decd5.us.png','PROFILEIMAGE'),(43,NULL,'2018-11-12 00:04:48',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',34523,'us.png','fa01d13d-924a-4fe0-9980-97aee5abba05.us.png','PROFILEIMAGE'),(44,NULL,'2018-11-12 08:46:40',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',49288,'CROP_1532024686389.jpg','d224445c-cfe1-4ac8-8d1d-4aa1e0bd915b.CROP_1532024686389.jpg','PROFILEIMAGE'),(45,NULL,'2018-11-12 08:49:59',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',34523,'us.png','ca721859-47f7-4213-a4fe-d6affaf93167.us.png','PROFILEIMAGE'),(46,NULL,'2018-11-12 08:54:28',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',34523,'us.png','b21268a3-4886-4981-8e5b-50ffa01dcbe4.us.png','PROFILEIMAGE'),(47,NULL,'2018-11-12 08:57:46',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',34523,'us.png','707ddb2b-c790-4ee1-a577-ed654a2b7b81.us.png','PROFILEIMAGE'),(48,NULL,'2018-11-12 09:00:52',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',34523,'us.png','b1243e2e-1c57-4fc6-abf8-17cec9656281.us.png','PROFILEIMAGE'),(49,NULL,'2018-11-12 09:09:22',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',34523,'us.png','85e7e410-877f-4850-a66d-42f1ddbd65b4.us.png','PROFILEIMAGE'),(50,NULL,'2018-11-12 09:11:21',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',20115,'Capture.PNG','64989e34-902f-4de0-af1b-4fa69f4e7e86.Capture.PNG','PROFILEIMAGE'),(51,NULL,'2018-11-12 09:12:17',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',20115,'Capture.PNG','cfa4a611-a19f-49ec-b153-5fccfb469e6f.Capture.PNG','PROFILEIMAGE'),(52,NULL,'2018-11-12 09:24:08',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',20115,'Capture.PNG','a49e0d8b-1975-46f9-a93e-daad5bf8745c.Capture.PNG','PROFILEIMAGE'),(53,NULL,'2018-11-12 09:25:33',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',20115,'Capture.PNG','37509bd2-9e92-4bc4-8795-21421fe216d9.Capture.PNG','PROFILEIMAGE'),(54,NULL,'2018-11-12 09:26:54',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',34523,'us.png','0ff8b9ad-68d2-4732-a8a4-52355c80a586.us.png','PROFILEIMAGE'),(55,NULL,'2018-11-12 09:29:07',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',49288,'CROP_1532024686389.jpg','5ffa6d26-b91d-49e3-b81a-5136b775045b.CROP_1532024686389.jpg','PROFILEIMAGE'),(56,NULL,'2018-11-12 09:31:46',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',34523,'us.png','4b830af2-847b-4938-b301-41403a50f9b7.us.png','PROFILEIMAGE'),(57,NULL,'2018-11-12 09:45:58',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',34523,'us.png','de968da8-65ff-44cb-b602-399c12ab2470.us.png','PROFILEIMAGE'),(58,NULL,'2018-11-12 09:47:07',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',40232,'strat.PNG','a608aea3-264a-403c-91a9-3eb01179ae99.strat.PNG','PROFILEIMAGE'),(59,NULL,'2018-11-12 09:54:01',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',34523,'us.png','8ab6aa55-cec5-4c38-a1da-164813f7ba10.us.png','PROFILEIMAGE'),(60,NULL,'2018-11-12 09:59:48',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',20115,'Capture.PNG','325f8588-88ff-42c8-b6f9-0e4f11459a5d.Capture.PNG','PROFILEIMAGE'),(61,NULL,'2018-11-12 10:01:04',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',90864,'mifotra.PNG','a6cd3847-5442-4ac7-bde4-dbf829413dee.mifotra.PNG','PROFILEIMAGE'),(62,NULL,'2018-11-12 10:01:30',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',20115,'Capture.PNG','66aa8313-03cf-4270-a68b-35c7e6482649.Capture.PNG','PROFILEIMAGE'),(63,NULL,'2018-11-12 10:04:11',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',39821,'country.PNG','3a402f34-55eb-49c1-9835-bb40296da407.country.PNG','PROFILEIMAGE'),(64,NULL,'2018-11-12 10:04:57',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',51798,'dob.png','894cd2e7-a4a0-453a-9f22-777341239acc.dob.png','PROFILEIMAGE'),(65,NULL,'2018-11-12 10:06:20',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',34523,'us.png','b1b1804e-2a93-4b08-bcf8-34631a507f98.us.png','PROFILEIMAGE'),(66,NULL,'2018-11-12 10:07:19',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',56161,'dash.PNG','802be8c3-3c0c-422a-a335-aeb09f34eab7.dash.PNG','PROFILEIMAGE'),(67,NULL,'2018-11-12 10:08:51',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',51798,'dob.png','a5557d82-ba67-44cf-bf37-7066ec215d45.dob.png','PROFILEIMAGE'),(68,NULL,'2018-11-12 10:10:29',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',56161,'dash.PNG','08f11577-af7b-46c2-9089-ac5eadbc0269.dash.PNG','PROFILEIMAGE'),(69,NULL,'2018-11-12 10:11:31',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',34523,'us.png','82bd0aef-dbb1-4562-8983-fdd6a399a1ae.us.png','PROFILEIMAGE'),(70,NULL,'2018-11-12 10:15:15',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',34523,'us.png','343e7679-680e-48fb-a612-2f4133b2d662.us.png','PROFILEIMAGE'),(71,NULL,'2018-11-13 18:45:23',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',56161,'dash.PNG','ec984450-0090-4057-8e53-941197305456.dash.PNG','PROFILEIMAGE'),(72,NULL,'2018-11-13 18:46:19',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',34523,'us.png','0f373ff2-4e73-4bba-a098-0917ac3423f8.us.png','PROFILEIMAGE'),(73,NULL,'2018-11-13 18:49:41',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',56161,'dash.PNG','560f069b-c2e7-4db6-ade1-0b60e1316845.dash.PNG','PROFILEIMAGE'),(74,NULL,'2018-11-13 18:50:26',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',34523,'us.png','f4269a7d-36f0-49bc-8568-c26ca5f3776f.us.png','PROFILEIMAGE'),(75,NULL,'2018-11-13 18:51:04',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',267652,'homePage.PNG','d319096e-728a-478f-acb8-dabec499ae88.homePage.PNG','PROFILEIMAGE'),(76,NULL,'2018-11-13 18:51:39',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',34523,'us.png','3b36ae2f-b4e6-4f4b-9577-a656717ad5bf.us.png','PROFILEIMAGE'),(77,NULL,'2018-11-25 14:39:14',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',34523,'us.png','d0d805cc-7be1-4a19-ba83-f8df38cd0cf2.us.png','PROFILEIMAGE'),(78,NULL,'2018-11-25 14:44:56',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',178953,'recoverkey.pdf','8541620c-b147-4d9f-a3bd-c8351cc7432b.recoverkey.pdf','PROFILEIMAGE'),(79,NULL,'2018-12-02 12:47:59',NULL,NULL,NULL,NULL,'C:\\\\Vfp_Document\\\\',64185,'AdminDashboard.PNG','b80e5633-1bdf-4042-b2d9-a7082a989426.AdminDashboard.PNG','PROFILEIMAGE'),(80,NULL,'2018-12-11 22:21:43',NULL,NULL,NULL,NULL,'E:\\Tressdaressources\\TresProject\\VFPProject_Dev_Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\VfpProject_Dev\\Vfp_Document\\\\',10147,'player1.png','f3a7d180-f9f5-4c7d-bc06-58d36bb14c89.player1.png','PROFILEIMAGE'),(81,NULL,'2018-12-11 22:26:58',NULL,NULL,NULL,NULL,'E:\\Tressdaressources\\TresProject\\VFPProject_Dev_Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\VfpProject_Dev\\Vfp_Document\\\\',27300,'SunriseLogo.jpg','827815d9-ccb7-45c3-b01f-afe15a850125.SunriseLogo.jpg','PROFILEIMAGE'),(82,NULL,'2018-12-12 12:23:57',NULL,NULL,NULL,NULL,'E:\\Tressdaressources\\TresProject\\VFPProject_Dev_Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\VfpProject_Dev\\Vfp_Document\\\\',139085,'BugeseraLogo.png','7774456c-b000-4c23-87b4-b25c7f135014.BugeseraLogo.png','PROFILEIMAGE'),(83,NULL,'2018-12-13 12:34:18',NULL,NULL,NULL,NULL,'E:\\Tressdaressources\\TresProject\\VFPProject_Dev_Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\VfpProject_Dev\\Vfp_Document\\\\',16033,'KireheLog.jpg','f2587665-b13e-4035-880b-246c9b917fda.KireheLog.jpg','PROFILEIMAGE'),(84,NULL,'2018-12-16 14:23:46',NULL,NULL,NULL,NULL,'E:\\Tressdaressources\\TresProject\\VFPProject_Dev_Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\VfpProject_Dev\\Vfp_Document\\\\',16033,'KireheLog.jpg','7c5a7379-9ef3-4518-a77c-23182450e934.KireheLog.jpg','PROFILEIMAGE'),(85,NULL,'2018-12-17 11:59:30',NULL,NULL,NULL,NULL,'E:\\Tressdaressources\\TresProject\\VFPProject_Dev_Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\VfpProject_Dev\\Vfp_Document\\\\',348712,'WBU_SDA_Emmanuel_051018t (1).xlsx','cd7cbc0a-2dc7-4ab1-835d-0241fa20b487.WBU_SDA_Emmanuel_051018t (1).xlsx','ActivityFiles'),(86,NULL,'2018-12-17 12:12:01',NULL,NULL,NULL,NULL,'E:\\Tressdaressources\\TresProject\\VFPProject_Dev_Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\VfpProject_Dev\\Vfp_Document\\\\',348824,'WBU_SDA_Emmanuel_051018t.xlsx','086d365d-a686-48f8-861f-3ed03afe2ccc.WBU_SDA_Emmanuel_051018t.xlsx','ActivityFiles'),(87,NULL,'2018-12-17 14:21:22',NULL,NULL,NULL,NULL,'E:\\Tressdaressources\\TresProject\\VFPProject_Dev_Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\VfpProject_Dev\\Vfp_Document\\\\',147778,'Software Requirements Specification.docx','00bb12f9-24de-459e-b92c-2af5d88724ea.Software Requirements Specification.docx','ActivityFiles'),(88,NULL,'2018-12-17 15:11:46',NULL,NULL,NULL,NULL,'E:\\Tressdaressources\\TresProject\\VFPProject_Dev_Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\VfpProject_Dev\\Vfp_Document\\\\',13777,'pattern.PNG','7f419949-f244-46c0-b823-b168132fc107.pattern.PNG','ActivityFiles'),(89,NULL,'2018-12-17 17:46:26',NULL,NULL,NULL,NULL,'E:\\Tressdaressources\\TresProject\\VFPProject_Dev_Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\VfpProject_Dev\\Vfp_Document\\\\',56023,'empty.png','de1a9403-82e2-415b-9f1d-8e82eb624340.empty.png','PROFILEIMAGE'),(90,NULL,'2018-12-17 18:16:01',NULL,NULL,NULL,NULL,'E:\\Tressdaressources\\TresProject\\VFPProject_Dev_Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\VfpProject_Dev\\Vfp_Document\\\\',942431,'DSC_0172.JPG','b2887d3b-9f84-4c77-9f24-dd3bbc345803.DSC_0172.JPG','PROFILEIMAGE'),(93,NULL,'2018-12-18 11:19:21',NULL,NULL,NULL,NULL,'E:\\Tressdaressources\\TresProject\\VFPProject_Dev_Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\VfpProject_Dev\\Vfp_Document\\\\',2098,'MarinesLogo.png','d0399c5f-c452-45f7-b2ec-144652740706.MarinesLogo.png','PROFILEIMAGE'),(95,NULL,'2018-12-19 09:09:06',NULL,NULL,NULL,NULL,'E:\\Tressdaressources\\TresProject\\VFPProject_Dev_Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\VfpProject_Dev\\Vfp_Document\\\\',34407,'championview.PNG','86209926-83fe-4a05-896f-06c3e65975e4.championview.PNG','ActivityFiles'),(96,NULL,'2018-12-19 13:04:07',NULL,NULL,NULL,NULL,'E:\\Tressdaressources\\TresProject\\VFPProject_Dev_Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\VfpProject_Dev\\Vfp_Document\\\\',19256,'UserAcc.PNG','2644221d-aac7-46bb-8f0a-503ed8996fe3.UserAcc.PNG','ActivityFiles'),(97,NULL,'2018-12-19 15:23:02',NULL,NULL,NULL,NULL,'E:\\Tressdaressources\\TresProject\\VFPProject_Dev_Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\VfpProject_Dev\\Vfp_Document\\\\',65978,'adminDBD.PNG','48b6ad94-23ff-4f0e-9bc3-334e2e37fa33.adminDBD.PNG','ActivityFiles'),(98,NULL,'2018-12-28 10:49:25',NULL,NULL,NULL,NULL,'E:\\Tressdaressources\\TresProject\\VFPProject_Dev_Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\VfpProject_Dev\\Vfp_Document\\\\',34523,'us.png','0564ee39-6371-4297-8ded-8bda34e2a17e.us.png','PROFILEIMAGE'),(99,NULL,'2019-01-02 10:49:23',NULL,NULL,NULL,NULL,'E:\\Tressdaressources\\TresProject\\VFPProject_Dev_Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\VfpProject_Dev\\Vfp_Document\\\\',34523,'us.png','c63b2388-8735-416a-8652-f4b5dfb7dda0.us.png','PROFILEIMAGE'),(100,NULL,'2019-01-02 11:06:14',NULL,NULL,NULL,NULL,'E:\\Tressdaressources\\TresProject\\VFPProject_Dev_Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\VfpProject_Dev\\Vfp_Document\\\\',12990,'AmagajuLogo.jpg','b2a15d29-8a5b-4f32-83a6-4fa45c1fcc91.AmagajuLogo.jpg','PROFILEIMAGE'),(101,NULL,'2019-01-02 12:18:21',NULL,NULL,NULL,NULL,'E:\\Tressdaressources\\TresProject\\VFPProject_Dev_Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\VfpProject_Dev\\Vfp_Document\\\\',21221,'aprLogo.jpg','585b8e59-9b1a-498f-9d7d-f2f2bfb442b5.aprLogo.jpg','PROFILEIMAGE'),(102,NULL,'2019-01-02 12:19:54',NULL,NULL,NULL,NULL,'E:\\Tressdaressources\\TresProject\\VFPProject_Dev_Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\VfpProject_Dev\\Vfp_Document\\\\',7417,'player2.png','ff6f2422-8711-4ce7-80fe-1f047ff5944b.player2.png','PROFILEIMAGE'),(103,NULL,'2019-01-02 12:26:52',NULL,NULL,NULL,NULL,'E:\\Tressdaressources\\TresProject\\VFPProject_Dev_Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\VfpProject_Dev\\Vfp_Document\\\\',8282,'logos1.png','8fbeb555-e38f-43a6-9b7c-2addd984b6dc.logos1.png','PROFILEIMAGE'),(104,NULL,'2019-01-04 14:09:59',NULL,NULL,NULL,NULL,'E:\\Tressdaressources\\TresProject\\VFPProject_Dev_Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\VfpProject_Dev\\Vfp_Document\\\\',21221,'aprLogo.jpg','2e9733cf-03b5-4923-9721-2febc40c8c35.aprLogo.jpg','INSTITUTIONLOGO'),(105,NULL,'2019-01-04 14:38:51',NULL,NULL,NULL,NULL,'E:\\Tressdaressources\\TresProject\\VFPProject_Dev_Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\VfpProject_Dev\\Vfp_Document\\\\',65978,'adminDBD.PNG','51ce9fa3-8c96-41e4-aea2-9188433d6e2f.adminDBD.PNG','PROFILEIMAGE'),(106,NULL,'2019-01-04 15:39:52',NULL,NULL,NULL,NULL,'E:\\Tressdaressources\\TresProject\\VFPProject_Dev_Workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\VfpProject_Dev\\Vfp_Document\\\\',348712,'WBU_SDA_Emmanuel_051018t (1).xlsx','29268c55-4c98-4b4c-a331-f508fb520e82.WBU_SDA_Emmanuel_051018t (1).xlsx','ActivityFiles');
/*!40000 ALTER TABLE `documents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluation`
--

DROP TABLE IF EXISTS `evaluation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evaluation` (
  `EavaluationId` int(11) NOT NULL AUTO_INCREMENT,
  `EvaluationDate` datetime DEFAULT NULL,
  `EvaluationMarks` int(11) DEFAULT NULL,
  `EvaluationOverAllMarks` int(11) DEFAULT NULL,
  `EvaluationType` varchar(255) DEFAULT NULL,
  `ACTIVITYID` int(11) DEFAULT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `crtdDtTime` datetime DEFAULT NULL,
  `genericStatus` varchar(255) DEFAULT NULL,
  `optLock` datetime DEFAULT NULL,
  `upDtTime` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `details` varchar(255) DEFAULT NULL,
  `decision` varchar(255) DEFAULT NULL,
  `activity` int(11) DEFAULT NULL,
  PRIMARY KEY (`EavaluationId`),
  KEY `FKA9FDE35C272A8341` (`ACTIVITYID`),
  KEY `FKA9FDE35C4A5AFDE6` (`activity`),
  CONSTRAINT `FKA9FDE35C272A8341` FOREIGN KEY (`ACTIVITYID`) REFERENCES `activity` (`ACTIVITY_ID`),
  CONSTRAINT `FKA9FDE35C4A5AFDE6` FOREIGN KEY (`activity`) REFERENCES `activity` (`ACTIVITY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluation`
--

LOCK TABLES `evaluation` WRITE;
/*!40000 ALTER TABLE `evaluation` DISABLE KEYS */;
/*!40000 ALTER TABLE `evaluation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `institution`
--

DROP TABLE IF EXISTS `institution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `institution` (
  `institutionId` int(11) NOT NULL AUTO_INCREMENT,
  `createdBy` varchar(255) DEFAULT NULL,
  `crtdDtTime` datetime DEFAULT NULL,
  `genericStatus` varchar(255) DEFAULT NULL,
  `optLock` datetime DEFAULT NULL,
  `upDtTime` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `institutionAddress` varchar(255) DEFAULT NULL,
  `institutionLogo` varchar(255) DEFAULT NULL,
  `institutionName` varchar(255) DEFAULT NULL,
  `institutionRegDate` datetime DEFAULT NULL,
  `institutionType` varchar(255) DEFAULT NULL,
  `country` int(11) DEFAULT NULL,
  `institution` int(11) DEFAULT NULL,
  `institutionRepresenative_userId` int(11) DEFAULT NULL,
  `village` int(11) DEFAULT NULL,
  `instLogo` varchar(255) DEFAULT NULL,
  `branch` int(11) DEFAULT NULL,
  `request` int(11) DEFAULT NULL,
  PRIMARY KEY (`institutionId`),
  KEY `FK98934D98C5457E64` (`country`),
  KEY `FK98934D98AA7FBEA8` (`institution`),
  KEY `FK98934D985DA6EDA4` (`institutionRepresenative_userId`),
  KEY `FK98934D9889F81890` (`village`),
  KEY `FK98934D987CC6AB15` (`request`),
  KEY `FK98934D9823053E92` (`branch`),
  CONSTRAINT `FK98934D9823053E92` FOREIGN KEY (`branch`) REFERENCES `institution` (`institutionId`),
  CONSTRAINT `FK98934D985DA6EDA4` FOREIGN KEY (`institutionRepresenative_userId`) REFERENCES `users` (`userId`),
  CONSTRAINT `FK98934D987CC6AB15` FOREIGN KEY (`request`) REFERENCES `institutionregistrationrequest` (`instRegReqstId`),
  CONSTRAINT `FK98934D9889F81890` FOREIGN KEY (`village`) REFERENCES `village` (`villageId`),
  CONSTRAINT `FK98934D98AA7FBEA8` FOREIGN KEY (`institution`) REFERENCES `institution` (`institutionId`),
  CONSTRAINT `FK98934D98C5457E64` FOREIGN KEY (`country`) REFERENCES `country` (`countryId`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `institution`
--

LOCK TABLES `institution` WRITE;
/*!40000 ALTER TABLE `institution` DISABLE KEYS */;
INSERT INTO `institution` VALUES (1,'admin',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Tres',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,'rep','2018-10-17 14:34:29','active',NULL,'2018-10-17 14:34:29','rep',NULL,NULL,NULL,'2018-10-17 14:34:29',NULL,1,NULL,3,NULL,NULL,NULL,NULL),(3,'rep','2018-10-17 14:34:29','active',NULL,'2018-10-17 14:34:29','rep',NULL,NULL,NULL,'2018-10-17 14:34:29',NULL,1,NULL,3,NULL,NULL,NULL,NULL),(4,'rep','2018-10-17 14:34:29','active',NULL,'2018-10-17 14:34:29','rep','Kigali',NULL,'BK','2018-10-17 14:34:29','Private',1,NULL,3,1,NULL,NULL,NULL),(5,'rep','2018-10-17 14:34:29','active',NULL,'2018-10-17 14:34:29','rep',NULL,NULL,NULL,'2018-10-17 14:34:29',NULL,2,NULL,3,1,NULL,NULL,NULL),(6,'rep','2018-10-17 14:34:29','active',NULL,'2018-10-17 14:34:29','rep','Kampali',NULL,'Pepsi','2018-10-17 14:34:29','Private',2,NULL,3,1,NULL,NULL,NULL),(7,'rep','2018-10-17 14:34:29','active',NULL,'2018-10-17 14:34:29','rep',NULL,NULL,NULL,'2018-10-17 14:34:29',NULL,1,NULL,3,1,NULL,NULL,NULL),(8,'rep','2018-10-17 14:34:29','active',NULL,'2018-10-17 14:34:29','rep',NULL,NULL,NULL,'2018-10-17 14:34:29',NULL,1,NULL,3,1,NULL,NULL,NULL),(9,'rep','2018-10-17 14:34:29','active',NULL,'2018-10-17 14:34:29','rep','Kigali',NULL,'Innovis Telocom','2018-10-17 14:34:29','Private',1,NULL,3,1,NULL,NULL,NULL),(10,'Tres','2018-11-25 13:29:19','active',NULL,'2018-11-25 13:29:19','Tres','Kigali-Rwanda',NULL,'Auca','2018-11-25 13:29:19','Private',1,NULL,17,NULL,NULL,NULL,NULL),(11,'Leonard','2018-11-25 13:46:16','active',NULL,'2018-11-25 13:46:16','Leonard','Tanzanie-kirwa',NULL,'Mtv ltd','2018-11-25 13:46:16','Public',3,NULL,19,NULL,NULL,NULL,NULL),(12,'Leonard','2018-11-25 13:46:16','active',NULL,'2018-11-25 13:46:16','Leonard','Tanzanie-kirwa',NULL,'Mtv ltd','2018-11-25 13:46:16','Private',3,NULL,19,NULL,NULL,NULL,NULL),(13,'Leonard','2018-11-25 13:46:16','active',NULL,'2018-11-25 13:46:16','Leonard','Tanzanie-kirwa',NULL,'Tigo','2018-11-25 13:46:16','Private',1,NULL,19,NULL,NULL,NULL,NULL),(14,'Leonard','2018-11-25 13:46:16','active',NULL,'2018-11-25 13:46:16','Leonard','Kigali-Rwanda',NULL,'Tigo','2018-11-25 13:46:16','Private',1,NULL,19,NULL,NULL,NULL,NULL),(15,'Leonard','2018-11-25 13:46:16','active',NULL,'2018-11-25 13:46:16','Leonard','Kigali-Rwanda',NULL,'Tigo','2018-11-25 13:46:16','Private',1,NULL,19,NULL,NULL,NULL,NULL),(16,'admin','2018-12-17 17:19:11','active',NULL,'2018-12-17 17:19:11','admin',NULL,NULL,NULL,'2018-12-17 17:19:11','HeadQuoter',NULL,NULL,NULL,NULL,'waiting',NULL,7),(17,'admin','2019-01-04 14:06:46','active',NULL,'2019-01-04 14:06:46','admin',NULL,NULL,NULL,'2019-01-04 14:06:46','HeadQuoter',NULL,NULL,NULL,NULL,'2e9733cf-03b5-4923-9721-2febc40c8c35.aprLogo.jpg',NULL,8);
/*!40000 ALTER TABLE `institution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `institutionescaletepolicy`
--

DROP TABLE IF EXISTS `institutionescaletepolicy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `institutionescaletepolicy` (
  `policyId` int(11) NOT NULL AUTO_INCREMENT,
  `createdBy` varchar(255) DEFAULT NULL,
  `crtdDtTime` datetime DEFAULT NULL,
  `genericStatus` varchar(255) DEFAULT NULL,
  `optLock` datetime DEFAULT NULL,
  `upDtTime` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `Status` varchar(255) DEFAULT NULL,
  `reschduleTime` int(11) DEFAULT NULL,
  `institution` int(11) DEFAULT NULL,
  `LongMarks` double DEFAULT NULL,
  `mediumgMarks` double DEFAULT NULL,
  `shortMarks` double DEFAULT NULL,
  `planPeriod` int(11) DEFAULT NULL,
  `variation` int(11) DEFAULT NULL,
  PRIMARY KEY (`policyId`),
  KEY `FK6C3961C0AA7FBEA8` (`institution`),
  CONSTRAINT `FK6C3961C0AA7FBEA8` FOREIGN KEY (`institution`) REFERENCES `institution` (`institutionId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `institutionescaletepolicy`
--

LOCK TABLES `institutionescaletepolicy` WRITE;
/*!40000 ALTER TABLE `institutionescaletepolicy` DISABLE KEYS */;
INSERT INTO `institutionescaletepolicy` VALUES (1,'Tres','2018-12-17 17:20:31','active',NULL,'2018-12-17 17:20:31',NULL,NULL,5,1,4,3,2,7,2),(2,'sda','2019-01-04 14:14:17','active',NULL,'2019-01-04 14:14:17',NULL,NULL,7,17,10,4,2,7,20);
/*!40000 ALTER TABLE `institutionescaletepolicy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `institutionregistrationrequest`
--

DROP TABLE IF EXISTS `institutionregistrationrequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `institutionregistrationrequest` (
  `instRegReqstId` int(11) NOT NULL AUTO_INCREMENT,
  `createdBy` varchar(255) DEFAULT NULL,
  `crtdDtTime` datetime DEFAULT NULL,
  `genericStatus` varchar(255) DEFAULT NULL,
  `optLock` datetime DEFAULT NULL,
  `upDtTime` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `instRegReqstDate` datetime DEFAULT NULL,
  `instRegReqstStatus` varchar(255) DEFAULT NULL,
  `instRegReqstType` varchar(255) DEFAULT NULL,
  `institution` int(11) DEFAULT NULL,
  `institutionAddress` varchar(255) DEFAULT NULL,
  `institutionName` varchar(255) DEFAULT NULL,
  `country` int(11) DEFAULT NULL,
  `institutionRepresenative_userId` int(11) DEFAULT NULL,
  `village` int(11) DEFAULT NULL,
  PRIMARY KEY (`instRegReqstId`),
  KEY `FK2EDAB2DEAA7FBEA8` (`institution`),
  KEY `FK2EDAB2DEC5457E64` (`country`),
  KEY `FK2EDAB2DE5DA6EDA4` (`institutionRepresenative_userId`),
  KEY `FK2EDAB2DE89F81890` (`village`),
  CONSTRAINT `FK2EDAB2DE5DA6EDA4` FOREIGN KEY (`institutionRepresenative_userId`) REFERENCES `users` (`userId`),
  CONSTRAINT `FK2EDAB2DE89F81890` FOREIGN KEY (`village`) REFERENCES `village` (`villageId`),
  CONSTRAINT `FK2EDAB2DEAA7FBEA8` FOREIGN KEY (`institution`) REFERENCES `institution` (`institutionId`),
  CONSTRAINT `FK2EDAB2DEC5457E64` FOREIGN KEY (`country`) REFERENCES `country` (`countryId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `institutionregistrationrequest`
--

LOCK TABLES `institutionregistrationrequest` WRITE;
/*!40000 ALTER TABLE `institutionregistrationrequest` DISABLE KEYS */;
INSERT INTO `institutionregistrationrequest` VALUES (1,NULL,NULL,NULL,NULL,NULL,NULL,'2018-11-25 00:00:00','acepted',NULL,10,NULL,NULL,NULL,NULL,NULL),(2,NULL,NULL,NULL,NULL,NULL,NULL,'2018-11-25 00:00:00','acepted',NULL,11,NULL,NULL,NULL,NULL,NULL),(3,'Leonard','2018-11-25 13:46:16','active',NULL,'2018-11-25 13:46:16','Leonard','2018-11-25 13:46:16','pending',NULL,12,NULL,NULL,NULL,NULL,NULL),(4,'Leonard','2018-11-25 13:46:16','active',NULL,'2018-11-25 13:46:16','Leonard','2018-11-25 13:46:16','pending',NULL,13,NULL,NULL,NULL,NULL,NULL),(5,'Leonard','2018-11-25 13:46:16','active',NULL,'2018-11-25 13:46:16','Leonard','2018-11-25 13:46:16','pending',NULL,14,NULL,NULL,NULL,NULL,NULL),(6,NULL,NULL,'desactive',NULL,NULL,NULL,'2018-11-25 00:00:00','rejected',NULL,15,NULL,NULL,NULL,NULL,NULL),(7,'Tres','2018-12-17 17:13:58','active',NULL,'2018-12-17 17:19:11','admin','2018-12-16 00:00:00','acepted','HeadQuoter',NULL,'Masoro','Auca',1,17,NULL),(8,'sda','2019-01-04 14:01:00','active',NULL,'2019-01-04 14:06:46','admin','2019-01-03 00:00:00','acepted','HeadQuoter',NULL,'Kabale street','Kabare Trading ltd',2,22,NULL);
/*!40000 ALTER TABLE `institutionregistrationrequest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `listofmenu`
--

DROP TABLE IF EXISTS `listofmenu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `listofmenu` (
  `menuId` int(11) NOT NULL AUTO_INCREMENT,
  `createdBy` varchar(255) DEFAULT NULL,
  `crtdDtTime` datetime DEFAULT NULL,
  `genericStatus` varchar(255) DEFAULT NULL,
  `optLock` datetime DEFAULT NULL,
  `upDtTime` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `creationDate` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `iconImage` varchar(255) DEFAULT NULL,
  `menuColor` varchar(255) DEFAULT NULL,
  `urlCode` varchar(255) DEFAULT NULL,
  `urlName` varchar(255) DEFAULT NULL,
  `listOfMenu` int(11) DEFAULT NULL,
  `menuGroup` int(11) DEFAULT NULL,
  PRIMARY KEY (`menuId`),
  UNIQUE KEY `urlCode` (`urlCode`),
  UNIQUE KEY `urlName` (`urlName`),
  KEY `FK84E9DE347E35E9B0` (`listOfMenu`),
  KEY `FK84E9DE3431BBBA18` (`menuGroup`),
  CONSTRAINT `FK84E9DE3431BBBA18` FOREIGN KEY (`menuGroup`) REFERENCES `menugroup` (`menuGroupId`),
  CONSTRAINT `FK84E9DE347E35E9B0` FOREIGN KEY (`listOfMenu`) REFERENCES `listofmenu` (`menuId`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listofmenu`
--

LOCK TABLES `listofmenu` WRITE;
/*!40000 ALTER TABLE `listofmenu` DISABLE KEYS */;
INSERT INTO `listofmenu` VALUES (1,'admin','2018-10-10 21:32:29','active','2018-10-10 21:32:29','2018-10-10 21:32:29','admin','2018-10-10 21:32:29','Manage MenuGroup','glyphicon glyphicon-asterisk','green2',NULL,'/menu/ViewMenuGroup.xhtml',1,1),(2,'admin','2018-10-10 21:49:39','active','2018-10-10 21:49:39','2018-10-10 21:49:39','admin','2018-10-10 21:49:39','List of Menu','glyphicon glyphicon-minus','blue2',NULL,'/menu/ViewListOfMenu.xhtml',2,1),(3,'admin','2018-10-10 21:51:04','active','2018-10-10 21:51:04','2018-10-10 21:51:04','admin','2018-10-10 21:51:04','Menu Assignment','glyphicon glyphicon-asterisk','green2',NULL,'/menu/menuAssignmentForm.xhtml',3,1),(4,'admin','2018-10-10 22:05:52','active',NULL,'2018-10-10 22:05:52','admin','2018-10-10 22:05:52','Institution Profile','glyphicon glyphicon-minus','blue2','001','/menu/ViewInstitutionProfile.xhtml',NULL,2),(5,'admin','2018-10-10 22:15:05','active',NULL,'2018-10-10 22:15:05','admin','2018-10-10 22:15:05','Institution Management','glyphicon glyphicon-asterisk','green2','004','/menu/manageInstitutionRegistration.xhtml',NULL,3),(6,'admin','2018-10-10 22:15:05','active',NULL,'2018-10-10 22:15:05','admin','2018-10-10 22:15:05','User Category','glyphicon glyphicon-minus','blue2','005','/menu/ListOfUserCategory.xhtml',NULL,13),(7,'admin','2018-10-10 22:15:05','active',NULL,'2018-10-10 22:15:05','admin','2018-10-10 22:15:05','User Management','glyphicon glyphicon-asterisk','green2','006','/menu/ViewUsersList.xhtml',NULL,4),(8,'admin','2018-10-10 22:15:05','active',NULL,'2018-10-10 22:15:05','admin','2018-10-10 22:15:05','User Contact','glyphicon glyphicon-minus','blue2','007','/menu/ViewUsersContacts.xhtml',NULL,4),(9,'admin','2018-10-10 22:15:05','active',NULL,'2018-10-10 22:15:05','admin','2018-10-10 22:15:05','User Profile','glyphicon glyphicon-asterisk','green2','008','/menu/ViewProfile.xhtml',NULL,5),(10,'admin','2018-10-10 22:15:05','active',NULL,'2018-10-10 22:15:05','admin','2018-10-10 22:15:05','Activity Management','glyphicon glyphicon-minus','blue2','009','/menu/Activity.xhtml',NULL,6),(11,'admin','2018-10-10 22:15:05','active',NULL,'2018-10-10 22:15:05','admin','2018-10-10 22:15:05','Task Management','glyphicon glyphicon-asterisk','green2','010','/menu/Task.xhtml',NULL,7),(12,'admin','2018-10-10 22:15:05','active',NULL,'2018-10-10 22:15:05','admin','2018-10-10 22:15:05','Manage Activities','glyphicon glyphicon-minus','blue2','011','/menu/ManageActivity.xhtml',NULL,7),(13,'admin','2018-10-10 22:15:05','active',NULL,'2018-10-10 22:15:05','admin','2018-10-10 22:15:05','Strategic Plan Management','glyphicon glyphicon-asterisk','green2','012','/menu/StrategicPlan.xhtml',NULL,4),(17,'admin','2018-10-10 22:30:51','active',NULL,'2018-10-10 22:30:51','admin','2018-10-10 22:30:51','Supervisor Report','glyphicon glyphicon-minus','blue2','014','/menu/generalReport.xhtml',NULL,8),(20,'admin','2018-10-10 22:39:26','active',NULL,'2018-10-10 22:39:26','admin','2018-10-10 22:39:26','Representative Report','glyphicon glyphicon-asterisk','green2','016','/menu/MdReportForm.xhtml',NULL,10),(21,'admin','2018-10-10 22:39:26','active',NULL,'2018-10-10 22:39:26','admin','2018-10-10 22:39:26','Staff Report','glyphicon glyphicon-minus','blue2','017','/menu/StaffReport.xhtml',NULL,9),(25,'admin','2018-10-12 09:03:36','active',NULL,'2018-10-12 09:03:36','admin','2018-10-12 09:03:36','Manage Task','glyphicon glyphicon-asterisk','green2','018','/menu/ManageTask.xhtml',NULL,4),(26,'admin','2018-10-12 09:18:16','active',NULL,'2018-10-12 09:18:16','admin','2018-10-12 09:18:16','Manage Payment Records','glyphicon glyphicon-minus','blue2','019','/menu/ViewPaymentRecords.xhtml',NULL,11),(27,'admin','2018-10-17 14:54:43','active',NULL,'2018-10-17 14:54:43','admin','2018-10-17 14:54:43','Manage Board','glyphicon glyphicon-asterisk','green2','022','/menu/boardOrganigram.xhtml',NULL,4),(28,'admin','2018-10-19 22:52:29','active',NULL,'2018-10-19 22:52:29','admin','2018-10-19 22:52:29','Check Notification','glyphicon glyphicon-cloud','blue2','023','/menu/Notification.xhtml',NULL,12),(31,'admin','2018-10-20 19:41:27','active',NULL,'2018-10-20 19:41:27','admin','2018-10-20 19:41:27','User Management','glyphicon glyphicon-user','green2','024','/menu/ListOfUsers.xhtml',NULL,14);
/*!40000 ALTER TABLE `listofmenu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loginhistoric`
--

DROP TABLE IF EXISTS `loginhistoric`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loginhistoric` (
  `historicId` int(11) NOT NULL AUTO_INCREMENT,
  `createdBy` varchar(255) DEFAULT NULL,
  `crtdDtTime` datetime DEFAULT NULL,
  `genericStatus` varchar(255) DEFAULT NULL,
  `optLock` datetime DEFAULT NULL,
  `upDtTime` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `IpAddress` varchar(255) DEFAULT NULL,
  `logOutTime` datetime DEFAULT NULL,
  `loginTimeIn` datetime DEFAULT NULL,
  `users` int(11) DEFAULT NULL,
  PRIMARY KEY (`historicId`),
  KEY `FKA90EB6C837BE5948` (`users`),
  CONSTRAINT `FKA90EB6C837BE5948` FOREIGN KEY (`users`) REFERENCES `users` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=1014 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loginhistoric`
--

LOCK TABLES `loginhistoric` WRITE;
/*!40000 ALTER TABLE `loginhistoric` DISABLE KEYS */;
INSERT INTO `loginhistoric` VALUES (158,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-22 18:37:10',NULL,'DESKTOP-BCD89LB/192.168.42.87',NULL,'2018-10-22 18:37:27',3),(159,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-22 18:58:40',NULL,'DESKTOP-BCD89LB/192.168.42.87',NULL,'2018-10-22 18:58:55',3),(160,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-22 19:08:32',NULL,'DESKTOP-BCD89LB/192.168.42.87',NULL,'2018-10-22 19:08:45',3),(161,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-22 19:40:46',NULL,'DESKTOP-BCD89LB/192.168.42.87',NULL,'2018-10-22 19:41:00',3),(162,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-22 19:48:29',NULL,'DESKTOP-BCD89LB/192.168.42.87',NULL,'2018-10-22 19:48:42',3),(163,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-22 19:58:31',NULL,'DESKTOP-BCD89LB/192.168.42.87',NULL,'2018-10-22 19:58:49',3),(164,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-22 20:12:45',NULL,'DESKTOP-BCD89LB/192.168.42.87',NULL,'2018-10-22 20:12:58',3),(165,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-22 20:20:44',NULL,'DESKTOP-BCD89LB/192.168.42.87',NULL,'2018-10-22 20:21:00',3),(166,'Hitimana Emile',NULL,NULL,NULL,'2018-10-22 20:28:53',NULL,'DESKTOP-BCD89LB/192.168.42.87',NULL,'2018-10-22 20:29:14',1),(167,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-22 20:31:09',NULL,'DESKTOP-BCD89LB/192.168.42.87',NULL,'2018-10-22 20:31:16',3),(168,'Kabera Nepo',NULL,NULL,NULL,'2018-10-22 20:46:44',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-10-22 20:47:00',4),(169,'Kabera Nepo',NULL,NULL,NULL,'2018-10-22 21:06:13',NULL,'DESKTOP-BCD89LB/192.168.42.46',NULL,'2018-10-22 21:06:29',4),(170,'Kabera Nepo',NULL,NULL,NULL,'2018-10-22 21:12:25',NULL,'DESKTOP-BCD89LB/192.168.42.46',NULL,'2018-10-22 21:13:20',4),(171,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-22 21:15:01',NULL,'DESKTOP-BCD89LB/192.168.42.46',NULL,'2018-10-22 21:15:11',3),(172,'Kabera Nepo',NULL,NULL,NULL,'2018-10-22 21:17:01',NULL,'DESKTOP-BCD89LB/192.168.42.46',NULL,'2018-10-22 21:17:12',4),(173,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-23 13:04:20',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-10-23 13:04:39',3),(174,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-24 11:06:11',NULL,'DESKTOP-BCD89LB/192.168.42.202',NULL,'2018-10-24 11:06:33',3),(175,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-25 19:29:56',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-10-25 19:35:44',3),(176,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-27 19:35:06',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-10-27 19:35:50',3),(177,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-27 20:38:17',NULL,'DESKTOP-BCD89LB/192.168.42.154',NULL,'2018-10-27 20:39:22',3),(178,'Kabera Nepo',NULL,NULL,NULL,'2018-10-27 20:44:18',NULL,'DESKTOP-BCD89LB/192.168.42.154',NULL,'2018-10-27 20:44:28',4),(179,'Ange Mutesi',NULL,NULL,NULL,'2018-10-27 21:41:06',NULL,'DESKTOP-BCD89LB/192.168.42.154',NULL,'2018-10-27 21:41:39',2),(180,'Kabera Nepo',NULL,NULL,NULL,'2018-10-27 22:11:12',NULL,'DESKTOP-BCD89LB/192.168.42.154',NULL,'2018-10-27 22:11:21',4),(181,'Kabera Nepo',NULL,NULL,NULL,'2018-10-28 08:15:02',NULL,'DESKTOP-BCD89LB/192.168.1.106',NULL,'2018-10-28 08:15:26',4),(182,'Hitimana Emile',NULL,NULL,NULL,'2018-10-28 09:41:37',NULL,'DESKTOP-BCD89LB/192.168.1.106',NULL,'2018-10-28 09:41:57',1),(183,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-28 09:43:52',NULL,'DESKTOP-BCD89LB/192.168.1.106',NULL,'2018-10-28 09:44:45',3),(184,'Hitimana Emile',NULL,NULL,NULL,'2018-10-28 09:45:23',NULL,'DESKTOP-BCD89LB/192.168.1.106',NULL,'2018-10-28 09:45:31',1),(185,'Hitimana Emile',NULL,NULL,NULL,'2018-10-28 09:48:58',NULL,'DESKTOP-BCD89LB/192.168.1.106',NULL,'2018-10-28 09:49:12',1),(186,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-28 09:54:34',NULL,'DESKTOP-BCD89LB/192.168.1.106',NULL,'2018-10-28 09:54:42',3),(187,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-28 10:13:21',NULL,'DESKTOP-BCD89LB/192.168.1.106',NULL,'2018-10-28 10:13:39',3),(188,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-28 11:00:19',NULL,'DESKTOP-BCD89LB/192.168.1.106',NULL,'2018-10-28 11:00:44',3),(189,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-28 11:24:58',NULL,'DESKTOP-BCD89LB/192.168.1.106',NULL,'2018-10-28 11:25:13',3),(190,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-28 12:39:29',NULL,'DESKTOP-BCD89LB/192.168.1.106',NULL,'2018-10-28 12:40:00',3),(191,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-29 10:55:33',NULL,'DESKTOP-BCD89LB/192.168.42.77',NULL,'2018-10-29 10:56:27',3),(192,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-29 10:57:10',NULL,'DESKTOP-BCD89LB/192.168.42.77',NULL,'2018-10-29 10:57:27',3),(193,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-30 11:28:51',NULL,'DESKTOP-BCD89LB/192.168.42.19',NULL,'2018-10-30 11:29:09',3),(194,'Hitimana Emile',NULL,NULL,NULL,'2018-10-30 11:36:33',NULL,'DESKTOP-BCD89LB/192.168.42.19',NULL,'2018-10-30 11:36:56',1),(195,'Kabera Nepo',NULL,NULL,NULL,'2018-10-30 19:18:16',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-30 19:18:53',4),(196,'Kabera Nepo',NULL,NULL,NULL,'2018-10-30 19:29:28',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-30 19:29:56',4),(197,'Kabera Nepo',NULL,NULL,NULL,'2018-10-30 19:38:51',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-30 19:39:07',4),(198,'Kabera Nepo',NULL,NULL,NULL,'2018-10-30 20:05:08',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-30 20:05:41',4),(199,'Kabera Nepo',NULL,NULL,NULL,'2018-10-30 20:13:58',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-30 20:14:14',4),(200,'Kabera Nepo',NULL,NULL,NULL,'2018-10-30 20:20:02',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-30 20:20:38',4),(201,'Kabera Nepo',NULL,NULL,NULL,'2018-10-30 21:19:24',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-30 21:20:04',4),(202,'Kabera Nepo',NULL,NULL,NULL,'2018-10-30 21:40:12',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-30 21:40:27',4),(203,'Kabera Nepo',NULL,NULL,NULL,'2018-10-30 21:56:45',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-30 21:57:03',4),(204,'Kabera Nepo',NULL,NULL,NULL,'2018-10-30 22:03:12',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-30 22:03:29',4),(205,'Kabera Nepo',NULL,NULL,NULL,'2018-10-30 22:09:53',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-30 22:12:04',4),(206,'Kabera Nepo',NULL,NULL,NULL,'2018-10-30 22:18:51',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-30 22:19:16',4),(207,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-30 22:38:57',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-30 22:39:12',3),(208,'Kabera Nepo',NULL,NULL,NULL,'2018-10-30 22:40:18',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-30 22:40:27',4),(209,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-30 22:46:15',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-30 22:46:21',3),(210,'Kabera Nepo',NULL,NULL,NULL,'2018-10-30 22:52:48',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-30 22:53:05',4),(211,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-30 23:07:28',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-30 23:07:37',3),(212,'Kabera Nepo',NULL,NULL,NULL,'2018-10-30 23:08:05',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-30 23:08:13',4),(213,'Kabera Nepo',NULL,NULL,NULL,'2018-10-30 23:36:38',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-30 23:36:53',4),(214,'Kabera Nepo',NULL,NULL,NULL,'2018-10-30 23:56:28',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-30 23:57:43',4),(215,'Kabera Nepo',NULL,NULL,NULL,'2018-10-31 00:14:50',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-31 00:17:17',4),(216,'Kabera Nepo',NULL,NULL,NULL,'2018-10-31 00:19:33',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-31 00:19:42',4),(217,'Kabera Nepo',NULL,NULL,NULL,'2018-10-31 00:29:16',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-31 00:29:36',4),(218,'Kabera Nepo',NULL,NULL,NULL,'2018-10-31 00:39:23',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-31 00:39:40',4),(219,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-31 00:45:23',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-31 00:45:33',3),(220,'Kabera Nepo',NULL,NULL,NULL,'2018-10-31 01:16:39',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-31 01:17:11',4),(221,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-31 01:25:54',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-31 01:26:01',3),(222,'Kabera Nepo',NULL,NULL,NULL,'2018-10-31 01:35:19',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-31 01:35:28',4),(223,'Kabera Nepo',NULL,NULL,NULL,'2018-10-31 01:46:42',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-31 01:47:33',4),(224,'Kabera Nepo',NULL,NULL,NULL,'2018-10-31 02:00:49',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-31 02:01:04',4),(225,'Kabera Nepo',NULL,NULL,NULL,'2018-10-31 02:21:59',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-31 02:22:19',4),(226,'Kabera Nepo',NULL,NULL,NULL,'2018-10-31 02:29:55',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-31 02:30:16',4),(227,'Kabera Nepo',NULL,NULL,NULL,'2018-10-31 02:36:02',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-31 02:36:44',4),(228,'Kabera Nepo',NULL,NULL,NULL,'2018-10-31 02:43:42',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-31 02:43:55',4),(229,'Kabera Nepo',NULL,NULL,NULL,'2018-10-31 02:54:30',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-31 02:54:56',4),(230,'Kabera Nepo',NULL,NULL,NULL,'2018-10-31 04:30:24',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-10-31 04:30:48',4),(231,'Kabera Nepo',NULL,NULL,NULL,'2018-10-31 18:31:42',NULL,'DESKTOP-BCD89LB/192.168.42.246',NULL,'2018-10-31 18:32:05',4),(232,'Kabera Nepo',NULL,NULL,NULL,'2018-10-31 18:55:45',NULL,'DESKTOP-BCD89LB/192.168.42.246',NULL,'2018-10-31 18:56:03',4),(233,'Kabera Nepo',NULL,NULL,NULL,'2018-10-31 19:11:12',NULL,'DESKTOP-BCD89LB/192.168.42.246',NULL,'2018-10-31 19:12:37',4),(234,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-31 19:31:37',NULL,'DESKTOP-BCD89LB/192.168.42.246',NULL,'2018-10-31 19:33:24',3),(235,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-31 20:03:49',NULL,'DESKTOP-BCD89LB/192.168.42.26',NULL,'2018-10-31 20:04:02',3),(236,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-31 21:16:41',NULL,'DESKTOP-BCD89LB/192.168.42.123',NULL,'2018-10-31 21:16:57',3),(237,'Hitimana Emile',NULL,NULL,NULL,'2018-10-31 21:26:45',NULL,'DESKTOP-BCD89LB/192.168.42.123',NULL,'2018-10-31 21:26:57',1),(238,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-31 21:27:34',NULL,'DESKTOP-BCD89LB/192.168.42.123',NULL,'2018-10-31 21:27:44',3),(239,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-31 22:08:25',NULL,'DESKTOP-BCD89LB/192.168.42.181',NULL,'2018-10-31 22:10:13',3),(240,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-31 22:43:35',NULL,'DESKTOP-BCD89LB/192.168.42.181',NULL,'2018-10-31 22:44:46',3),(241,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-31 23:05:43',NULL,'DESKTOP-BCD89LB/192.168.42.181',NULL,'2018-10-31 23:07:25',3),(242,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-31 23:13:40',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-10-31 23:14:16',3),(243,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-31 23:40:05',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-10-31 23:40:26',3),(244,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-01 00:47:43',NULL,'DESKTOP-BCD89LB/192.168.42.125',NULL,'2018-11-01 00:50:01',3),(245,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-01 01:04:41',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-01 01:05:15',3),(246,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-01 01:06:38',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-01 01:06:52',3),(247,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-01 13:56:20',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-11-01 13:58:36',3),(248,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-01 13:59:17',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-11-01 14:03:57',3),(249,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-01 14:49:01',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-11-01 14:49:16',3),(250,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-01 15:08:17',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-11-01 15:08:33',3),(251,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-01 15:38:18',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-11-01 15:38:42',3),(252,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-01 15:46:38',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-11-01 15:46:52',3),(253,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-01 15:56:36',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-11-01 15:56:57',3),(254,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-01 16:13:36',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-11-01 16:13:53',3),(255,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-01 16:22:41',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-11-01 16:23:24',3),(256,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-01 16:55:28',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-11-01 16:56:13',3),(257,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-01 17:00:11',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-11-01 17:00:17',3),(258,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-01 17:19:59',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-11-01 17:20:14',3),(259,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-01 18:36:46',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-11-01 18:38:09',3),(260,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-01 18:49:16',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-11-01 18:49:37',3),(261,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-01 18:54:13',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-11-01 18:54:39',3),(262,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-01 19:40:42',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-11-01 19:41:06',3),(263,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-01 20:02:22',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-11-01 20:02:42',3),(264,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-01 20:25:57',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-11-01 20:26:13',3),(265,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-01 21:13:25',NULL,'DESKTOP-BCD89LB/192.168.1.143',NULL,'2018-11-01 21:13:54',3),(266,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-01 22:31:43',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-01 22:32:42',3),(267,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-01 22:40:15',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-01 22:40:32',3),(268,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-01 22:42:51',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-01 22:43:00',3),(269,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 08:08:57',NULL,'DESKTOP-BCD89LB/192.168.42.125',NULL,'2018-11-02 08:09:13',3),(270,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 08:28:19',NULL,'DESKTOP-BCD89LB/192.168.42.125',NULL,'2018-11-02 08:28:35',3),(271,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 08:36:03',NULL,'DESKTOP-BCD89LB/192.168.42.125',NULL,'2018-11-02 08:36:17',3),(272,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 08:43:12',NULL,'DESKTOP-BCD89LB/192.168.42.125',NULL,'2018-11-02 08:43:31',3),(273,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 08:53:21',NULL,'DESKTOP-BCD89LB/192.168.42.125',NULL,'2018-11-02 08:53:49',3),(274,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 09:02:49',NULL,'DESKTOP-BCD89LB/192.168.42.125',NULL,'2018-11-02 09:03:04',3),(275,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 09:08:19',NULL,'DESKTOP-BCD89LB/192.168.42.125',NULL,'2018-11-02 09:08:40',3),(276,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 09:12:52',NULL,'DESKTOP-BCD89LB/192.168.42.125',NULL,'2018-11-02 09:14:21',3),(277,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 09:33:46',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-02 09:33:59',3),(278,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 09:48:16',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-02 09:48:31',3),(279,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 10:01:49',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-02 10:02:04',3),(280,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 10:07:46',NULL,'DESKTOP-BCD89LB/192.168.42.17',NULL,'2018-11-02 10:10:51',3),(281,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 10:48:45',NULL,'DESKTOP-BCD89LB/192.168.42.164',NULL,'2018-11-02 10:49:03',3),(282,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 11:15:59',NULL,'DESKTOP-BCD89LB/192.168.42.164',NULL,'2018-11-02 11:16:30',3),(283,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 11:37:51',NULL,'DESKTOP-BCD89LB/192.168.42.164',NULL,'2018-11-02 11:38:12',3),(284,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 11:46:55',NULL,'DESKTOP-BCD89LB/192.168.42.164',NULL,'2018-11-02 11:47:09',3),(285,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 11:57:25',NULL,'DESKTOP-BCD89LB/192.168.42.164',NULL,'2018-11-02 11:58:45',3),(286,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 12:23:50',NULL,'DESKTOP-BCD89LB/192.168.42.164',NULL,'2018-11-02 12:24:10',3),(287,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 14:55:00',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-02 14:55:59',3),(288,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 15:13:35',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-02 15:13:49',3),(289,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 15:39:42',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-02 15:39:59',3),(290,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 15:49:19',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-02 15:49:32',3),(291,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 16:00:32',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-02 16:00:45',3),(292,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 16:08:44',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-02 16:09:00',3),(293,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 16:27:13',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-02 16:27:27',3),(294,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 16:36:03',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-02 16:37:21',3),(295,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 16:45:19',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-02 16:45:33',3),(296,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 16:53:34',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-02 16:53:48',3),(297,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 17:11:26',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-02 17:11:41',3),(298,'Hitimana Emile',NULL,NULL,NULL,'2018-11-02 17:22:33',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-02 17:22:41',1),(299,'Hitimana Emile',NULL,NULL,NULL,'2018-11-02 17:37:38',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-02 17:37:51',1),(300,'Hitimana Emile',NULL,NULL,NULL,'2018-11-02 18:27:43',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-02 18:28:15',1),(301,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 18:30:23',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-02 18:30:35',3),(302,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 18:50:47',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-02 18:51:29',3),(303,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 19:29:32',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-02 19:30:00',3),(304,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 19:39:19',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-02 19:39:39',3),(305,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 19:41:25',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-02 19:41:44',3),(306,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 19:57:45',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-02 19:58:12',3),(307,'Kabera Nepo',NULL,NULL,NULL,'2018-11-02 20:01:01',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-02 20:01:12',4),(308,'Kabera Nepo',NULL,NULL,NULL,'2018-11-02 20:01:33',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-02 20:01:42',4),(309,'Kabera Nepo',NULL,NULL,NULL,'2018-11-02 20:01:54',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-02 20:02:04',4),(310,'Ange Mutesi',NULL,NULL,NULL,'2018-11-02 20:03:26',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-02 20:03:35',2),(311,'Kabera Nepo',NULL,NULL,NULL,'2018-11-02 20:03:46',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-02 20:03:56',4),(312,'Hitimana Emile',NULL,NULL,NULL,'2018-11-02 20:04:30',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-02 20:04:44',1),(313,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-02 20:07:13',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-02 20:07:19',3),(314,'Kabera Nepo',NULL,NULL,NULL,'2018-11-02 20:08:28',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-02 20:08:37',4),(315,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-03 19:52:43',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-03 19:53:05',3),(316,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-03 20:08:33',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-03 20:08:50',3),(317,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-03 20:58:03',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-03 20:58:21',3),(318,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-03 21:15:34',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-03 21:15:49',3),(319,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-03 21:19:31',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-03 21:19:50',3),(320,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-03 21:38:26',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-03 21:39:04',3),(321,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-03 22:41:09',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-03 22:41:42',3),(322,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-03 22:55:14',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-03 22:55:28',3),(323,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-03 22:56:11',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-03 22:56:18',3),(324,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-03 23:53:10',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-03 23:53:27',3),(325,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-04 08:12:27',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 08:13:12',3),(326,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-04 08:34:53',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 08:35:33',3),(327,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-04 09:11:53',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 09:12:09',3),(328,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-04 09:32:15',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 09:32:28',3),(329,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-04 09:49:01',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 09:49:26',3),(330,'Hitimana Emile',NULL,NULL,NULL,'2018-11-04 09:56:10',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 09:56:24',1),(331,'Kabera Nepo',NULL,NULL,NULL,'2018-11-04 09:57:19',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 09:57:32',4),(332,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-04 09:59:59',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 10:00:07',3),(333,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-04 10:30:47',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 10:31:25',3),(334,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-04 10:32:44',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 10:34:31',3),(335,'Kabera Nepo',NULL,NULL,NULL,'2018-11-04 10:49:21',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 10:49:29',4),(336,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-04 10:50:35',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 10:50:41',3),(337,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-04 10:50:47',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 10:52:11',3),(338,'Kabera Nepo',NULL,NULL,NULL,'2018-11-04 10:56:24',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 10:56:38',4),(339,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-04 11:25:52',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 11:26:43',3),(340,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-04 11:50:22',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 11:50:42',3),(341,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-04 12:06:12',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 12:07:38',3),(342,'Kabera Nepo',NULL,NULL,NULL,'2018-11-04 12:10:17',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 12:10:26',4),(343,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-04 13:44:47',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 13:45:50',3),(344,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-04 13:47:06',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 13:47:15',3),(345,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-04 14:05:18',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 14:05:34',3),(346,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-04 14:12:17',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 14:12:25',3),(347,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-04 14:22:42',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 14:23:26',3),(348,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-04 14:42:59',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 14:43:12',3),(349,'Patrick Nshuti',NULL,NULL,NULL,'2018-11-04 14:59:51',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 15:00:04',3),(350,'Alain Muhire',NULL,NULL,NULL,'2018-11-04 15:03:35',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 15:08:57',5),(351,'Hitimana Emile',NULL,NULL,NULL,'2018-11-04 15:09:20',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 15:09:27',1),(352,'Hitimana Emile',NULL,NULL,NULL,'2018-11-04 15:24:53',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 15:25:18',1),(353,'Nshuti Patric',NULL,NULL,NULL,'2018-11-04 15:25:32',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 15:25:44',8),(354,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 16:03:30',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 16:03:45',3),(355,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 16:07:46',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 16:07:53',3),(356,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 16:08:51',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 16:08:57',3),(357,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 16:10:32',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 16:10:38',3),(358,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 16:18:51',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 16:20:29',3),(359,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 16:21:43',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 16:21:52',3),(360,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 16:26:11',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 16:26:17',3),(361,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 16:34:02',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 16:34:17',3),(362,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 16:41:25',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 16:41:40',3),(363,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 16:44:08',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 16:44:22',3),(364,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 16:47:00',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 16:47:07',3),(365,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 16:53:20',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 16:53:35',3),(366,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 17:10:43',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 17:10:57',3),(367,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 17:12:01',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 17:12:10',3),(368,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 17:50:02',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 17:50:17',3),(369,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 18:24:51',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 18:25:08',3),(370,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 18:47:37',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 18:48:08',3),(371,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 18:52:06',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 18:52:12',3),(372,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 19:01:28',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 19:01:39',3),(373,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 19:43:45',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 19:44:06',3),(374,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 19:57:18',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 19:57:34',3),(375,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 21:09:44',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 21:09:57',3),(376,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 21:12:36',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 21:12:44',3),(377,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 21:24:09',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 21:24:23',3),(378,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 21:49:09',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 21:50:26',3),(379,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 22:00:54',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 22:01:23',3),(380,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 22:37:12',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 22:37:31',3),(381,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 22:47:14',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 22:47:31',3),(382,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 23:03:18',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 23:03:49',3),(383,'Emma Sibo',NULL,NULL,NULL,'2018-11-04 23:05:00',NULL,'DESKTOP-BCD89LB/192.168.1.112',NULL,'2018-11-04 23:05:07',3),(384,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 09:59:14',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 09:59:30',3),(385,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 10:12:23',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 10:12:36',3),(386,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 10:14:16',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 10:14:25',3),(387,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 10:19:57',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 10:20:04',3),(388,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 10:26:32',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 10:26:49',3),(389,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 10:29:37',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 10:29:44',3),(390,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 10:38:10',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 10:38:23',3),(391,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 10:41:43',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 10:41:57',3),(392,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 10:43:56',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 10:44:02',3),(393,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 10:46:07',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 10:46:19',3),(394,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 10:57:01',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 10:57:24',3),(395,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 11:05:31',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 11:05:38',3),(396,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 11:11:18',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 11:11:25',3),(397,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 11:18:40',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 11:18:58',3),(398,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 11:35:01',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 11:37:44',3),(399,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 11:40:01',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 11:40:07',3),(400,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 11:52:32',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 11:52:47',3),(401,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 11:54:02',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 11:54:24',3),(402,'Hitimana Emile',NULL,NULL,NULL,'2018-11-05 11:55:33',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 11:55:41',1),(403,'Hitimana Emile',NULL,NULL,NULL,'2018-11-05 11:56:41',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 11:56:56',1),(404,'Hitimana Emile',NULL,NULL,NULL,'2018-11-05 11:57:58',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 11:58:05',1),(405,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 11:58:10',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 11:58:17',3),(406,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 11:58:51',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 11:59:02',3),(407,'Hitimana Emile',NULL,NULL,NULL,'2018-11-05 11:59:12',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 11:59:19',1),(408,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 12:13:26',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 12:14:25',3),(409,'Hitimana Emile',NULL,NULL,NULL,'2018-11-05 12:15:59',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 12:16:06',1),(410,'Hitimana Emile',NULL,NULL,NULL,'2018-11-05 12:21:19',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 12:21:33',1),(411,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 12:22:01',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 12:22:07',3),(412,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 12:32:39',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 12:32:57',3),(413,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 12:58:31',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 12:59:13',3),(414,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 13:06:55',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 13:07:33',3),(415,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 13:28:24',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 13:28:38',3),(416,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 13:34:58',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 13:35:13',3),(417,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 13:42:17',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 13:42:56',3),(418,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 13:51:52',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 13:52:10',3),(419,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 14:18:32',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 14:20:10',3),(420,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 14:47:43',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 14:48:02',3),(421,'Hitimana Emile',NULL,NULL,NULL,'2018-11-05 16:10:33',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 16:12:11',1),(422,'Emma Sibo',NULL,NULL,NULL,'2018-11-05 16:18:46',NULL,'DESKTOP-BCD89LB/192.168.42.180',NULL,'2018-11-05 16:18:54',3),(423,'Hitimana Emile',NULL,NULL,NULL,'2018-11-05 17:23:52',NULL,'DESKTOP-BCD89LB/192.168.42.126',NULL,'2018-11-05 17:24:18',1),(424,'Hitimana Emile',NULL,NULL,NULL,'2018-11-05 17:30:46',NULL,'DESKTOP-BCD89LB/192.168.42.126',NULL,'2018-11-05 17:33:46',1),(425,'Hitimana Emile',NULL,NULL,NULL,'2018-11-05 17:34:35',NULL,'DESKTOP-BCD89LB/192.168.42.126',NULL,'2018-11-05 17:34:52',1),(426,'Hitimana Emile',NULL,NULL,NULL,'2018-11-05 17:40:35',NULL,'DESKTOP-BCD89LB/192.168.42.126',NULL,'2018-11-05 17:41:20',1),(427,'Hitimana Emile',NULL,NULL,NULL,'2018-11-05 17:53:02',NULL,'DESKTOP-BCD89LB/192.168.42.126',NULL,'2018-11-05 17:53:22',1),(428,'Hitimana Emile',NULL,NULL,NULL,'2018-11-05 17:57:37',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-05 17:58:01',1),(429,'Hitimana Emile',NULL,NULL,NULL,'2018-11-05 18:02:20',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-05 18:02:44',1),(430,'Hitimana Emile',NULL,NULL,NULL,'2018-11-05 18:08:49',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-05 18:10:56',1),(431,'Hitimana Emile',NULL,NULL,NULL,'2018-11-05 18:17:44',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-05 18:18:54',1),(432,'Hitimana Emile',NULL,NULL,NULL,'2018-11-05 21:38:53',NULL,'DESKTOP-BCD89LB/192.168.42.7',NULL,'2018-11-05 21:39:16',1),(433,'Hitimana Emile',NULL,NULL,NULL,'2018-11-05 21:49:38',NULL,'DESKTOP-BCD89LB/192.168.42.7',NULL,'2018-11-05 21:50:37',1),(434,'Hitimana Emile',NULL,NULL,NULL,'2018-11-05 22:57:59',NULL,'DESKTOP-BCD89LB/192.168.42.7',NULL,'2018-11-05 22:58:14',1),(435,'Hitimana Emile',NULL,NULL,NULL,'2018-11-05 23:04:17',NULL,'DESKTOP-BCD89LB/192.168.42.7',NULL,'2018-11-05 23:04:31',1),(436,'Hitimana Emile',NULL,NULL,NULL,'2018-11-05 23:36:56',NULL,'DESKTOP-BCD89LB/192.168.42.7',NULL,'2018-11-05 23:37:13',1),(437,'Hitimana Emile',NULL,NULL,NULL,'2018-11-05 23:41:55',NULL,'DESKTOP-BCD89LB/192.168.42.7',NULL,'2018-11-05 23:42:47',1),(438,'Hitimana Emile',NULL,NULL,NULL,'2018-11-06 00:13:34',NULL,'DESKTOP-BCD89LB/192.168.42.7',NULL,'2018-11-06 00:13:54',1),(439,'Hitimana Emile',NULL,NULL,NULL,'2018-11-06 00:15:29',NULL,'DESKTOP-BCD89LB/192.168.42.7',NULL,'2018-11-06 00:15:38',1),(440,'Hitimana Emile',NULL,NULL,NULL,'2018-11-06 01:07:36',NULL,'DESKTOP-BCD89LB/192.168.42.7',NULL,'2018-11-06 01:08:05',1),(441,'Hitimana Emile',NULL,NULL,NULL,'2018-11-06 01:20:22',NULL,'DESKTOP-BCD89LB/192.168.42.7',NULL,'2018-11-06 01:21:05',1),(442,'Hitimana Emile',NULL,NULL,NULL,'2018-11-06 01:34:04',NULL,'DESKTOP-BCD89LB/192.168.42.248',NULL,'2018-11-06 01:34:48',1),(443,'Hitimana Emile',NULL,NULL,NULL,'2018-11-06 01:40:03',NULL,'DESKTOP-BCD89LB/192.168.42.248',NULL,'2018-11-06 01:41:23',1),(444,'Hitimana Emile',NULL,NULL,NULL,'2018-11-06 01:54:54',NULL,'DESKTOP-BCD89LB/192.168.42.248',NULL,'2018-11-06 01:55:15',1),(445,'Hitimana Emile',NULL,NULL,NULL,'2018-11-06 10:02:39',NULL,'DESKTOP-BCD89LB/192.168.1.126',NULL,'2018-11-06 10:03:00',1),(446,'Hitimana Emile',NULL,NULL,NULL,'2018-11-06 10:14:10',NULL,'DESKTOP-BCD89LB/192.168.1.126',NULL,'2018-11-06 10:14:44',1),(447,'Hitimana Emile',NULL,NULL,NULL,'2018-11-06 10:37:22',NULL,'DESKTOP-BCD89LB/192.168.1.126',NULL,'2018-11-06 10:37:36',1),(448,'Emma Sibo',NULL,NULL,NULL,'2018-11-06 10:41:13',NULL,'DESKTOP-BCD89LB/192.168.1.126',NULL,'2018-11-06 10:41:26',3),(449,'Emma Sibo',NULL,NULL,NULL,'2018-11-06 11:17:13',NULL,'DESKTOP-BCD89LB/192.168.1.126',NULL,'2018-11-06 11:17:56',3),(450,'Emma Sibo',NULL,NULL,NULL,'2018-11-06 14:53:26',NULL,'DESKTOP-BCD89LB/192.168.1.126',NULL,'2018-11-06 14:53:53',3),(451,'Emma Sibo',NULL,NULL,NULL,'2018-11-06 15:00:16',NULL,'DESKTOP-BCD89LB/192.168.1.126',NULL,'2018-11-06 15:03:20',3),(452,'Emma Sibo',NULL,NULL,NULL,'2018-11-06 15:29:02',NULL,'DESKTOP-BCD89LB/192.168.1.126',NULL,'2018-11-06 15:29:25',3),(453,'Emma Sibo',NULL,NULL,NULL,'2018-11-06 16:00:01',NULL,'DESKTOP-BCD89LB/192.168.1.126',NULL,'2018-11-06 16:00:19',3),(454,'Emma Sibo',NULL,NULL,NULL,'2018-11-06 16:07:57',NULL,'DESKTOP-BCD89LB/192.168.1.126',NULL,'2018-11-06 16:08:10',3),(455,'Emma Sibo',NULL,NULL,NULL,'2018-11-06 16:11:58',NULL,'DESKTOP-BCD89LB/192.168.1.126',NULL,'2018-11-06 16:12:07',3),(456,'Hitimana Emile',NULL,NULL,NULL,'2018-11-06 16:13:28',NULL,'DESKTOP-BCD89LB/192.168.1.126',NULL,'2018-11-06 16:14:09',1),(457,'Hitimana Emile',NULL,NULL,NULL,'2018-11-06 16:22:39',NULL,'DESKTOP-BCD89LB/192.168.1.126',NULL,'2018-11-06 16:22:57',1),(458,'Hitimana Emile',NULL,NULL,NULL,'2018-11-06 16:36:13',NULL,'DESKTOP-BCD89LB/192.168.1.126',NULL,'2018-11-06 16:36:36',1),(459,'Emma Sibo',NULL,NULL,NULL,'2018-11-06 16:37:55',NULL,'DESKTOP-BCD89LB/192.168.1.126',NULL,'2018-11-06 16:38:02',3),(460,'Emma Sibo',NULL,NULL,NULL,'2018-11-06 16:39:41',NULL,'DESKTOP-BCD89LB/192.168.1.126',NULL,'2018-11-06 16:39:53',3),(461,'Hitimana Emile',NULL,NULL,NULL,'2018-11-06 16:57:54',NULL,'DESKTOP-BCD89LB/192.168.1.126',NULL,'2018-11-06 16:58:10',1),(462,'Emma Sibo',NULL,NULL,NULL,'2018-11-06 16:58:33',NULL,'DESKTOP-BCD89LB/192.168.1.126',NULL,'2018-11-06 16:58:40',3),(463,'Emma Sibo',NULL,NULL,NULL,'2018-11-06 17:33:18',NULL,'DESKTOP-BCD89LB/192.168.1.126',NULL,'2018-11-06 17:34:08',3),(464,'Hitimana Emile',NULL,NULL,NULL,'2018-11-06 17:36:47',NULL,'DESKTOP-BCD89LB/192.168.1.126',NULL,'2018-11-06 17:37:00',1),(465,'Hitimana Emile',NULL,NULL,NULL,'2018-11-06 17:39:24',NULL,'DESKTOP-BCD89LB/192.168.1.126',NULL,'2018-11-06 17:39:40',1),(466,'Emma Sibo',NULL,NULL,NULL,'2018-11-07 11:52:25',NULL,'DESKTOP-BCD89LB/192.168.42.248',NULL,'2018-11-07 11:54:29',3),(467,'Emma Sibo',NULL,NULL,NULL,'2018-11-07 12:17:12',NULL,'DESKTOP-BCD89LB/192.168.42.248',NULL,'2018-11-07 12:18:11',3),(468,'Emma Sibo',NULL,NULL,NULL,'2018-11-07 12:34:31',NULL,'DESKTOP-BCD89LB/192.168.42.248',NULL,'2018-11-07 12:35:37',3),(469,'Emma Sibo',NULL,NULL,NULL,'2018-11-07 12:47:26',NULL,'DESKTOP-BCD89LB/192.168.42.248',NULL,'2018-11-07 12:47:40',3),(470,'Emma Sibo',NULL,NULL,NULL,'2018-11-07 13:16:52',NULL,'DESKTOP-BCD89LB/192.168.42.248',NULL,'2018-11-07 13:17:06',3),(471,'Emma Sibo',NULL,NULL,NULL,'2018-11-07 13:57:27',NULL,'DESKTOP-BCD89LB/192.168.42.248',NULL,'2018-11-07 14:00:06',3),(472,'Emma Sibo',NULL,NULL,NULL,'2018-11-07 15:26:20',NULL,'DESKTOP-BCD89LB/192.168.1.126',NULL,'2018-11-07 15:27:32',3),(473,'Emma Sibo',NULL,NULL,NULL,'2018-11-07 15:41:17',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-07 15:49:14',3),(474,'Hitimana Emile',NULL,NULL,NULL,'2018-11-08 18:52:11',NULL,'DESKTOP-BCD89LB/192.168.42.248',NULL,'2018-11-08 19:05:06',1),(475,'Emma Sibo',NULL,NULL,NULL,'2018-11-08 19:15:26',NULL,'DESKTOP-BCD89LB/192.168.42.248',NULL,'2018-11-08 19:15:37',3),(476,'Kabera Nepo',NULL,NULL,NULL,'2018-11-08 19:15:42',NULL,'DESKTOP-BCD89LB/192.168.42.248',NULL,'2018-11-08 19:16:01',4),(477,'Hitimana Emile',NULL,NULL,NULL,'2018-11-08 19:16:34',NULL,'DESKTOP-BCD89LB/192.168.42.248',NULL,'2018-11-08 19:16:47',1),(478,'Emma Sibo',NULL,NULL,NULL,'2018-11-08 19:17:39',NULL,'DESKTOP-BCD89LB/192.168.42.248',NULL,'2018-11-08 19:17:47',3),(479,'Kabera Nepo',NULL,NULL,NULL,'2018-11-08 19:19:11',NULL,'DESKTOP-BCD89LB/192.168.42.248',NULL,'2018-11-08 19:19:19',4),(480,'Emma Sibo',NULL,NULL,NULL,'2018-11-08 19:19:26',NULL,'DESKTOP-BCD89LB/192.168.42.248',NULL,'2018-11-08 19:19:37',3),(481,'Kabera Nepo',NULL,NULL,NULL,'2018-11-08 19:20:16',NULL,'DESKTOP-BCD89LB/192.168.42.248',NULL,'2018-11-08 19:20:23',4),(482,'Kabera Nepo',NULL,NULL,NULL,'2018-11-08 19:21:27',NULL,'DESKTOP-BCD89LB/192.168.42.248',NULL,'2018-11-08 19:21:40',4),(483,'Emma Sibo',NULL,NULL,NULL,'2018-11-08 20:33:57',NULL,'DESKTOP-BCD89LB/192.168.42.50',NULL,'2018-11-08 20:38:18',3),(484,'Hitimana Emile',NULL,NULL,NULL,'2018-11-08 21:04:18',NULL,'DESKTOP-BCD89LB/192.168.42.50',NULL,'2018-11-08 21:10:07',1),(485,'Hitimana Emile',NULL,NULL,NULL,'2018-11-08 22:41:01',NULL,'DESKTOP-BCD89LB/192.168.42.111',NULL,'2018-11-08 22:41:30',1),(486,'Hitimana Emile',NULL,NULL,NULL,'2018-11-08 23:56:26',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-08 23:56:45',1),(487,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 00:05:04',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-09 00:05:24',1),(488,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 00:16:12',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-09 00:16:31',1),(489,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 10:09:39',NULL,'DESKTOP-BCD89LB/192.168.42.101',NULL,'2018-11-09 10:10:51',1),(490,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 10:28:03',NULL,'DESKTOP-BCD89LB/192.168.42.234',NULL,'2018-11-09 10:29:18',1),(491,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 10:35:40',NULL,'DESKTOP-BCD89LB/192.168.42.234',NULL,'2018-11-09 10:35:55',1),(492,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 11:09:08',NULL,'DESKTOP-BCD89LB/192.168.42.234',NULL,'2018-11-09 11:09:25',1),(493,'Emma Sibo',NULL,NULL,NULL,'2018-11-09 11:14:35',NULL,'DESKTOP-BCD89LB/192.168.42.234',NULL,'2018-11-09 11:14:52',3),(494,'Emma Sibo',NULL,NULL,NULL,'2018-11-09 11:19:19',NULL,'DESKTOP-BCD89LB/192.168.42.234',NULL,'2018-11-09 11:19:34',3),(495,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 11:22:28',NULL,'DESKTOP-BCD89LB/192.168.42.234',NULL,'2018-11-09 11:22:37',1),(496,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 11:33:46',NULL,'DESKTOP-BCD89LB/192.168.42.234',NULL,'2018-11-09 11:34:38',1),(497,'Emma Sibo',NULL,NULL,NULL,'2018-11-09 12:49:44',NULL,'DESKTOP-BCD89LB/192.168.42.234',NULL,'2018-11-09 12:49:59',3),(498,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 12:51:11',NULL,'DESKTOP-BCD89LB/192.168.42.234',NULL,'2018-11-09 12:51:23',1),(499,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 13:02:44',NULL,'DESKTOP-BCD89LB/192.168.42.234',NULL,'2018-11-09 13:03:02',1),(500,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 13:12:36',NULL,'DESKTOP-BCD89LB/192.168.42.234',NULL,'2018-11-09 13:12:56',1),(501,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 13:21:21',NULL,'DESKTOP-BCD89LB/192.168.42.234',NULL,'2018-11-09 13:21:56',1),(502,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 13:34:14',NULL,'DESKTOP-BCD89LB/192.168.42.234',NULL,'2018-11-09 13:35:09',1),(503,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 13:46:06',NULL,'DESKTOP-BCD89LB/192.168.42.234',NULL,'2018-11-09 13:46:26',1),(504,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 14:00:55',NULL,'DESKTOP-BCD89LB/192.168.42.234',NULL,'2018-11-09 14:01:15',1),(505,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 14:14:52',NULL,'DESKTOP-BCD89LB/192.168.42.234',NULL,'2018-11-09 14:15:10',1),(506,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 14:27:27',NULL,'DESKTOP-BCD89LB/192.168.42.234',NULL,'2018-11-09 14:27:45',1),(507,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 14:41:53',NULL,'DESKTOP-BCD89LB/192.168.42.234',NULL,'2018-11-09 14:42:09',1),(508,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 14:48:06',NULL,'DESKTOP-BCD89LB/192.168.42.234',NULL,'2018-11-09 14:48:22',1),(509,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 15:11:14',NULL,'DESKTOP-BCD89LB/192.168.42.234',NULL,'2018-11-09 15:11:42',1),(510,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 15:40:42',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-09 15:41:01',1),(511,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 15:46:09',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-09 15:46:29',1),(512,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 15:52:53',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-09 15:53:11',1),(513,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 16:37:57',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-09 16:38:15',1),(514,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 16:52:29',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-09 16:52:49',1),(515,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 17:06:27',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-09 17:06:41',1),(516,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 17:21:38',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-09 17:21:57',1),(517,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 17:47:58',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-09 17:48:50',1),(518,'Hitimana Emile',NULL,NULL,NULL,'2018-11-09 17:59:03',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-09 17:59:48',1),(520,'Hitimana Emile',NULL,NULL,NULL,'2018-11-10 21:44:39',NULL,'DESKTOP-BCD89LB/192.168.42.201',NULL,'2018-11-10 21:45:44',1),(521,'Hitimana Emile',NULL,NULL,NULL,'2018-11-10 21:48:42',NULL,'DESKTOP-BCD89LB/192.168.42.201',NULL,'2018-11-10 21:48:51',1),(522,'Emma Sibo',NULL,NULL,NULL,'2018-11-10 21:49:51',NULL,'DESKTOP-BCD89LB/192.168.42.201',NULL,'2018-11-10 21:50:13',3),(523,'Hitimana Emile',NULL,NULL,NULL,'2018-11-10 22:00:12',NULL,'DESKTOP-BCD89LB/192.168.42.201',NULL,'2018-11-10 22:00:28',1),(524,'Emma Sibo',NULL,NULL,NULL,'2018-11-10 22:09:27',NULL,'DESKTOP-BCD89LB/192.168.42.201',NULL,'2018-11-10 22:09:34',3),(525,'Hitimana Emile',NULL,NULL,NULL,'2018-11-10 22:10:12',NULL,'DESKTOP-BCD89LB/192.168.42.201',NULL,'2018-11-10 22:10:22',1),(526,'Emma Sibo',NULL,NULL,NULL,'2018-11-10 22:24:44',NULL,'DESKTOP-BCD89LB/192.168.42.201',NULL,'2018-11-10 22:25:01',3),(527,'Emma Sibo',NULL,NULL,NULL,'2018-11-10 22:37:44',NULL,'DESKTOP-BCD89LB/192.168.42.201',NULL,'2018-11-10 22:37:57',3),(528,'Hitimana Emile',NULL,NULL,NULL,'2018-11-10 22:49:27',NULL,'DESKTOP-BCD89LB/192.168.42.201',NULL,'2018-11-10 22:49:44',1),(529,'Emma Sibo',NULL,NULL,NULL,'2018-11-10 22:51:01',NULL,'DESKTOP-BCD89LB/192.168.42.201',NULL,'2018-11-10 22:51:15',3),(530,'Emma Sibo',NULL,NULL,NULL,'2018-11-10 23:15:37',NULL,'DESKTOP-BCD89LB/192.168.42.201',NULL,'2018-11-10 23:16:04',3),(531,'Hitimana Emile',NULL,NULL,NULL,'2018-11-10 23:17:14',NULL,'DESKTOP-BCD89LB/192.168.42.201',NULL,'2018-11-10 23:17:23',1),(532,'Emma Sibo',NULL,NULL,NULL,'2018-11-10 23:18:20',NULL,'DESKTOP-BCD89LB/192.168.42.201',NULL,'2018-11-10 23:18:31',3),(533,'Emma Sibo',NULL,NULL,NULL,'2018-11-10 23:26:32',NULL,'DESKTOP-BCD89LB/192.168.42.201',NULL,'2018-11-10 23:26:47',3),(534,'Emma Sibo',NULL,NULL,NULL,'2018-11-10 23:32:45',NULL,'DESKTOP-BCD89LB/192.168.42.201',NULL,'2018-11-10 23:33:06',3),(535,'Hitimana Emile',NULL,NULL,NULL,'2018-11-10 23:35:22',NULL,'DESKTOP-BCD89LB/192.168.42.201',NULL,'2018-11-10 23:35:31',1),(536,'Emma Sibo',NULL,NULL,NULL,'2018-11-10 23:43:02',NULL,'DESKTOP-BCD89LB/192.168.42.201',NULL,'2018-11-10 23:44:10',3),(537,'Hitimana Emile',NULL,NULL,NULL,'2018-11-11 08:51:44',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 08:52:43',1),(538,'Emma Sibo',NULL,NULL,NULL,'2018-11-11 09:52:30',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 09:59:47',3),(539,'Hitimana Emile',NULL,NULL,NULL,'2018-11-11 10:15:39',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 10:15:50',1),(540,'Emma Sibo',NULL,NULL,NULL,'2018-11-11 10:18:17',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 10:18:25',3),(541,'Hitimana Emile',NULL,NULL,NULL,'2018-11-11 10:19:11',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 10:19:18',1),(542,'Hitimana Emile',NULL,NULL,NULL,'2018-11-11 10:34:29',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 10:35:21',1),(543,'Hitimana Emile',NULL,NULL,NULL,'2018-11-11 10:50:05',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 10:50:47',1),(544,'Hitimana Emile',NULL,NULL,NULL,'2018-11-11 11:04:54',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 11:05:12',1),(545,'Hitimana Emile',NULL,NULL,NULL,'2018-11-11 11:18:17',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 11:18:32',1),(546,'Hitimana Emile',NULL,NULL,NULL,'2018-11-11 11:20:06',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 11:20:14',1),(547,'Hitimana Emile',NULL,NULL,NULL,'2018-11-11 12:01:22',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 12:01:52',1),(548,'Hitimana Emile',NULL,NULL,NULL,'2018-11-11 12:09:07',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 12:09:24',1),(549,'Emma Sibo',NULL,NULL,NULL,'2018-11-11 12:58:41',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 12:58:55',3),(550,'Hitimana Emile',NULL,NULL,NULL,'2018-11-11 12:59:06',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 12:59:13',1),(551,'Hitimana Emile',NULL,NULL,NULL,'2018-11-11 13:08:50',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 13:14:46',1),(552,'Emma Sibo',NULL,NULL,NULL,'2018-11-11 14:09:00',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 14:09:20',3),(553,'Hitimana Emile',NULL,NULL,NULL,'2018-11-11 14:29:12',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 14:29:20',1),(554,'Hitimana Emile',NULL,NULL,NULL,'2018-11-11 14:30:40',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 14:31:09',1),(555,'Emma Sibo',NULL,NULL,NULL,'2018-11-11 14:31:55',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 14:32:02',3),(556,'Hitimana Emile',NULL,NULL,NULL,'2018-11-11 14:43:36',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 14:45:03',1),(557,'Emma Sibo',NULL,NULL,NULL,'2018-11-11 14:46:00',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 14:46:09',3),(558,'Hitimana Emile',NULL,NULL,NULL,'2018-11-11 15:00:24',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 15:00:56',1),(559,'Emma Sibo',NULL,NULL,NULL,'2018-11-11 15:01:42',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 15:01:50',3),(560,'Emma Sibos',NULL,NULL,NULL,'2018-11-11 15:04:04',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 15:04:11',3),(561,'Hitimana Emile',NULL,NULL,NULL,'2018-11-11 15:04:25',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 15:04:46',1),(562,'Hitimana Emile',NULL,NULL,NULL,'2018-11-11 15:09:04',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 15:09:23',1),(563,'Ngango Junior',NULL,NULL,NULL,'2018-11-11 15:10:17',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 15:10:27',17),(564,'Ngango Junior',NULL,NULL,NULL,'2018-11-11 15:11:35',NULL,'DESKTOP-BCD89LB/192.168.1.137',NULL,'2018-11-11 15:11:55',17),(565,'Emma Sibo',NULL,NULL,NULL,'2018-11-11 23:42:05',NULL,'DESKTOP-BCD89LB/192.168.42.77',NULL,'2018-11-11 23:46:19',3),(566,'Ngango Junior',NULL,NULL,NULL,'2018-11-11 23:46:26',NULL,'DESKTOP-BCD89LB/192.168.42.77',NULL,'2018-11-11 23:46:36',17),(567,'Emma Sibo',NULL,NULL,NULL,'2018-11-12 00:01:01',NULL,'DESKTOP-BCD89LB/192.168.42.77',NULL,'2018-11-12 00:01:25',3),(568,'Emma Sibo',NULL,NULL,NULL,'2018-11-12 08:45:13',NULL,'DESKTOP-BCD89LB/192.168.42.65',NULL,'2018-11-12 08:45:33',3),(569,'Emma Sibo',NULL,NULL,NULL,'2018-11-12 08:49:11',NULL,'DESKTOP-BCD89LB/192.168.42.65',NULL,'2018-11-12 08:49:18',3),(570,'Emma Sibo',NULL,NULL,NULL,'2018-11-12 08:53:44',NULL,'DESKTOP-BCD89LB/192.168.42.65',NULL,'2018-11-12 08:53:58',3),(571,'Emma Sibo',NULL,NULL,NULL,'2018-11-12 09:10:28',NULL,'DESKTOP-BCD89LB/192.168.42.65',NULL,'2018-11-12 09:10:38',3),(572,'Emma Sibo',NULL,NULL,NULL,'2018-11-12 09:26:14',NULL,'DESKTOP-BCD89LB/192.168.42.235',NULL,'2018-11-12 09:26:20',3),(573,'Ngango Junior',NULL,NULL,NULL,'2018-11-12 09:28:22',NULL,'DESKTOP-BCD89LB/192.168.42.235',NULL,'2018-11-12 09:28:35',17),(574,'Ngango Junior',NULL,NULL,NULL,'2018-11-12 09:30:09',NULL,'DESKTOP-BCD89LB/192.168.42.235',NULL,'2018-11-12 09:30:19',17),(575,'Hitimana Emile',NULL,NULL,NULL,'2018-11-12 09:30:26',NULL,'DESKTOP-BCD89LB/192.168.42.235',NULL,'2018-11-12 09:30:33',1),(576,'Ngango Junior',NULL,NULL,NULL,'2018-11-12 09:32:26',NULL,'DESKTOP-BCD89LB/192.168.42.235',NULL,'2018-11-12 09:33:00',17),(577,'Emma Sibo',NULL,NULL,NULL,'2018-11-12 09:37:35',NULL,'DESKTOP-BCD89LB/192.168.42.235',NULL,'2018-11-12 09:38:20',3),(578,'Ngango Junior',NULL,NULL,NULL,'2018-11-12 09:38:30',NULL,'DESKTOP-BCD89LB/192.168.42.235',NULL,'2018-11-12 09:38:48',17),(579,'Ngango Junior',NULL,NULL,NULL,'2018-11-12 09:40:12',NULL,'DESKTOP-BCD89LB/192.168.42.235',NULL,'2018-11-12 09:40:57',17),(580,'Ngango Junior',NULL,NULL,NULL,'2018-11-12 09:43:52',NULL,'DESKTOP-BCD89LB/192.168.42.235',NULL,'2018-11-12 09:44:22',17),(581,'Nshuti Emma',NULL,NULL,NULL,'2018-11-12 09:44:59',NULL,'DESKTOP-BCD89LB/192.168.42.235',NULL,'2018-11-12 09:45:17',18),(582,'Nshuti Emma',NULL,NULL,NULL,'2018-11-12 09:58:54',NULL,'DESKTOP-BCD89LB/192.168.42.235',NULL,'2018-11-12 09:59:15',18),(583,'Nshuti Emma',NULL,NULL,NULL,'2018-11-12 10:10:44',NULL,'DESKTOP-BCD89LB/192.168.42.235',NULL,'2018-11-12 10:10:56',18),(584,'Nshuti Emma',NULL,NULL,NULL,'2018-11-12 10:15:25',NULL,'DESKTOP-BCD89LB/192.168.42.235',NULL,'2018-11-12 10:15:38',18),(585,'Emma Sibo',NULL,NULL,NULL,'2018-11-12 11:06:51',NULL,'DESKTOP-BCD89LB/192.168.42.235',NULL,'2018-11-12 11:07:12',3),(586,'Ngango Junior',NULL,NULL,NULL,'2018-11-12 21:46:01',NULL,'DESKTOP-BCD89LB/192.168.42.243',NULL,'2018-11-12 21:46:28',17),(587,'Ngango Junior',NULL,NULL,NULL,'2018-11-12 21:49:27',NULL,'DESKTOP-BCD89LB/192.168.42.243',NULL,'2018-11-12 21:50:40',17),(588,'Ngango Junior',NULL,NULL,NULL,'2018-11-12 22:14:15',NULL,'DESKTOP-BCD89LB/192.168.42.243',NULL,'2018-11-12 22:14:45',17),(589,'Ngango Junior',NULL,NULL,NULL,'2018-11-12 22:39:16',NULL,'DESKTOP-BCD89LB/192.168.42.243',NULL,'2018-11-12 22:39:46',17),(590,'Ngango Junior',NULL,NULL,NULL,'2018-11-12 23:01:58',NULL,'DESKTOP-BCD89LB/192.168.42.243',NULL,'2018-11-12 23:03:39',17),(591,'Nshuti Emma',NULL,NULL,NULL,'2018-11-12 23:20:32',NULL,'DESKTOP-BCD89LB/192.168.42.243',NULL,'2018-11-12 23:21:24',18),(592,'Nshuti Emma',NULL,NULL,NULL,'2018-11-12 23:21:42',NULL,'DESKTOP-BCD89LB/192.168.42.243',NULL,'2018-11-12 23:21:58',18),(593,'Ngango Junior',NULL,NULL,NULL,'2018-11-12 23:22:37',NULL,'DESKTOP-BCD89LB/192.168.42.243',NULL,'2018-11-12 23:22:53',17),(594,'Ngango Junior',NULL,NULL,NULL,'2018-11-12 23:38:56',NULL,'DESKTOP-BCD89LB/192.168.42.243',NULL,'2018-11-12 23:39:35',17),(595,'Ngango Junior',NULL,NULL,NULL,'2018-11-12 23:50:34',NULL,'DESKTOP-BCD89LB/192.168.42.243',NULL,'2018-11-12 23:51:00',17),(596,'Ngango Junior',NULL,NULL,NULL,'2018-11-13 09:39:43',NULL,'DESKTOP-BCD89LB/192.168.42.29',NULL,'2018-11-13 09:40:04',17),(597,'Ngango Junior',NULL,NULL,NULL,'2018-11-13 09:48:40',NULL,'DESKTOP-BCD89LB/192.168.42.29',NULL,'2018-11-13 09:48:57',17),(598,'Ngango Junior',NULL,NULL,NULL,'2018-11-13 09:59:19',NULL,'DESKTOP-BCD89LB/192.168.42.29',NULL,'2018-11-13 10:00:29',17),(599,'Nshuti Emma',NULL,NULL,NULL,'2018-11-13 10:01:14',NULL,'DESKTOP-BCD89LB/192.168.42.29',NULL,'2018-11-13 10:01:31',18),(600,'Ngango Junior',NULL,NULL,NULL,'2018-11-13 10:03:44',NULL,'DESKTOP-BCD89LB/192.168.42.29',NULL,'2018-11-13 10:04:03',17),(601,'Ngango Junior',NULL,NULL,NULL,'2018-11-13 10:27:55',NULL,'DESKTOP-BCD89LB/192.168.42.29',NULL,'2018-11-13 10:41:23',17),(602,'Ngango Junior',NULL,NULL,NULL,'2018-11-13 10:55:44',NULL,'DESKTOP-BCD89LB/192.168.42.29',NULL,'2018-11-13 10:56:19',17),(603,'Nshuti Emma',NULL,NULL,NULL,'2018-11-13 11:00:57',NULL,'DESKTOP-BCD89LB/192.168.42.29',NULL,'2018-11-13 11:01:14',18),(604,'Nshuti Emma',NULL,NULL,NULL,'2018-11-13 11:21:57',NULL,'DESKTOP-BCD89LB/192.168.42.123',NULL,'2018-11-13 11:22:16',18),(605,'Nshuti Emma',NULL,NULL,NULL,'2018-11-13 11:32:11',NULL,'DESKTOP-BCD89LB/192.168.42.123',NULL,'2018-11-13 11:32:33',18),(606,'Ngango Junior',NULL,NULL,NULL,'2018-11-13 11:32:59',NULL,'DESKTOP-BCD89LB/192.168.42.123',NULL,'2018-11-13 11:33:14',17),(607,'Nshuti Emma',NULL,NULL,NULL,'2018-11-13 11:34:37',NULL,'DESKTOP-BCD89LB/192.168.42.123',NULL,'2018-11-13 11:34:51',18),(608,'Nshuti Emma',NULL,NULL,NULL,'2018-11-13 11:45:27',NULL,'DESKTOP-BCD89LB/192.168.42.123',NULL,'2018-11-13 11:45:50',18),(609,'Ngango Junior',NULL,NULL,NULL,'2018-11-13 11:46:32',NULL,'DESKTOP-BCD89LB/192.168.42.123',NULL,'2018-11-13 11:46:43',17),(610,'Nshuti Emma',NULL,NULL,NULL,'2018-11-13 11:56:20',NULL,'DESKTOP-BCD89LB/192.168.42.123',NULL,'2018-11-13 11:56:47',18),(611,'Hitimana Emile',NULL,NULL,NULL,'2018-11-13 11:57:44',NULL,'DESKTOP-BCD89LB/192.168.42.123',NULL,'2018-11-13 11:58:02',1),(612,'Hitimana Emile',NULL,NULL,NULL,'2018-11-13 12:09:37',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-13 12:09:57',1),(613,'Ngango Junior',NULL,NULL,NULL,'2018-11-13 12:11:22',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-13 12:12:11',17),(614,'Ngango Junior',NULL,NULL,NULL,'2018-11-13 12:39:33',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-13 12:40:00',17),(615,'Ngango Junior',NULL,NULL,NULL,'2018-11-13 12:51:48',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-13 12:52:13',17),(616,'Ngango Junior',NULL,NULL,NULL,'2018-11-13 13:18:31',NULL,'DESKTOP-BCD89LB/192.168.42.49',NULL,'2018-11-13 13:19:13',17),(617,'Ngango Junior',NULL,NULL,NULL,'2018-11-13 13:39:24',NULL,'DESKTOP-BCD89LB/192.168.42.49',NULL,'2018-11-13 13:39:55',17),(618,'Ngango Junior',NULL,NULL,NULL,'2018-11-13 13:46:40',NULL,'DESKTOP-BCD89LB/192.168.42.49',NULL,'2018-11-13 13:47:00',17),(619,'Ngango Junior',NULL,NULL,NULL,'2018-11-13 16:51:20',NULL,'DESKTOP-BCD89LB/192.168.42.136',NULL,'2018-11-13 16:51:38',17),(620,'Ngango Junior',NULL,NULL,NULL,'2018-11-13 16:59:15',NULL,'DESKTOP-BCD89LB/192.168.42.136',NULL,'2018-11-13 16:59:32',17),(621,'Ngango Junior',NULL,NULL,NULL,'2018-11-13 17:39:47',NULL,'DESKTOP-BCD89LB/192.168.42.136',NULL,'2018-11-13 17:39:59',17),(622,'Ngango Junior',NULL,NULL,NULL,'2018-11-13 17:49:03',NULL,'DESKTOP-BCD89LB/192.168.42.136',NULL,'2018-11-13 17:50:08',17),(623,'Ngango Junior',NULL,NULL,NULL,'2018-11-13 18:09:02',NULL,'DESKTOP-BCD89LB/192.168.42.136',NULL,'2018-11-13 18:10:09',17),(624,'Hitimana Emile',NULL,NULL,NULL,'2018-11-13 18:11:11',NULL,'DESKTOP-BCD89LB/192.168.42.136',NULL,'2018-11-13 18:11:19',1),(625,'Ngango Junior',NULL,NULL,NULL,'2018-11-13 18:21:27',NULL,'DESKTOP-BCD89LB/192.168.42.136',NULL,'2018-11-13 18:22:10',17),(626,'Hitimana Emile',NULL,NULL,NULL,'2018-11-13 18:23:57',NULL,'DESKTOP-BCD89LB/192.168.42.136',NULL,'2018-11-13 18:24:05',1),(627,'Ngango Junior',NULL,NULL,NULL,'2018-11-13 18:34:51',NULL,'DESKTOP-BCD89LB/192.168.42.136',NULL,'2018-11-13 18:35:43',17),(628,'Hitimana Emile',NULL,NULL,NULL,'2018-11-13 18:35:54',NULL,'DESKTOP-BCD89LB/192.168.42.136',NULL,'2018-11-13 18:36:10',1),(629,'Hitimana Emile',NULL,NULL,NULL,'2018-11-13 18:39:44',NULL,'DESKTOP-BCD89LB/192.168.42.136',NULL,'2018-11-13 18:41:37',1),(630,'Hitimana Emile',NULL,NULL,NULL,'2018-11-13 18:44:02',NULL,'DESKTOP-BCD89LB/192.168.42.136',NULL,'2018-11-13 18:44:19',1),(631,'Hitimana Emile',NULL,NULL,NULL,'2018-11-13 18:45:34',NULL,'DESKTOP-BCD89LB/192.168.42.136',NULL,'2018-11-13 18:45:45',1),(632,'Hitimana Emile',NULL,NULL,NULL,'2018-11-13 18:47:55',NULL,'DESKTOP-BCD89LB/192.168.42.136',NULL,'2018-11-13 18:49:11',1),(633,'Hitimana Emile',NULL,NULL,NULL,'2018-11-13 18:51:47',NULL,'DESKTOP-BCD89LB/192.168.42.136',NULL,'2018-11-13 18:51:54',1),(634,'Ngango Junior',NULL,NULL,NULL,'2018-11-16 09:50:11',NULL,'DESKTOP-BCD89LB/192.168.42.75',NULL,'2018-11-16 09:51:10',17),(635,'Hitimana Emile',NULL,NULL,NULL,'2018-11-25 13:18:15',NULL,'DESKTOP-BCD89LB/192.168.1.110',NULL,'2018-11-25 13:18:34',1),(636,'Ngango Junior',NULL,NULL,NULL,'2018-11-25 13:22:41',NULL,'DESKTOP-BCD89LB/192.168.1.110',NULL,'2018-11-25 13:23:42',17),(637,'Hitimana Emile',NULL,NULL,NULL,'2018-11-25 13:23:54',NULL,'DESKTOP-BCD89LB/192.168.1.110',NULL,'2018-11-25 13:24:02',1),(638,'Ngango Junior',NULL,NULL,NULL,'2018-11-25 13:28:41',NULL,'DESKTOP-BCD89LB/192.168.1.110',NULL,'2018-11-25 13:28:54',17),(639,'Hitimana Emile',NULL,NULL,NULL,'2018-11-25 13:33:54',NULL,'DESKTOP-BCD89LB/192.168.1.110',NULL,'2018-11-25 13:34:02',1),(640,'Ngango Junior',NULL,NULL,NULL,'2018-11-25 13:35:12',NULL,'DESKTOP-BCD89LB/192.168.1.110',NULL,'2018-11-25 13:35:26',17),(641,'Hitimana Emile',NULL,NULL,NULL,'2018-11-25 13:36:22',NULL,'DESKTOP-BCD89LB/192.168.1.110',NULL,'2018-11-25 13:37:41',1),(642,'Leonard Mbonimpa',NULL,NULL,NULL,'2018-11-25 13:45:33',NULL,'DESKTOP-BCD89LB/192.168.1.110',NULL,'2018-11-25 13:45:51',19),(643,'Hitimana Emile',NULL,NULL,NULL,'2018-11-25 13:51:25',NULL,'DESKTOP-BCD89LB/192.168.1.110',NULL,'2018-11-25 13:51:39',1),(644,'Leonard Mbonimpa',NULL,NULL,NULL,'2018-11-25 13:54:00',NULL,'DESKTOP-BCD89LB/192.168.1.110',NULL,'2018-11-25 13:54:15',19),(645,'Leonard Mbonimpa',NULL,NULL,NULL,'2018-11-25 14:27:44',NULL,'DESKTOP-BCD89LB/192.168.1.110',NULL,'2018-11-25 14:28:13',19),(646,'Ngango Junior',NULL,NULL,NULL,'2018-11-25 14:30:15',NULL,'DESKTOP-BCD89LB/192.168.1.110',NULL,'2018-11-25 14:31:45',17),(647,'Fabrice Ngenzi',NULL,NULL,NULL,'2018-11-25 14:34:43',NULL,'DESKTOP-BCD89LB/192.168.1.110',NULL,'2018-11-25 14:36:19',6),(648,'Fabrice Ngenzi',NULL,NULL,NULL,'2018-11-25 14:39:22',NULL,'DESKTOP-BCD89LB/192.168.1.110',NULL,'2018-11-25 14:39:42',6),(649,'Fabrice Ngenzi',NULL,NULL,NULL,'2018-11-25 14:57:54',NULL,'DESKTOP-BCD89LB/192.168.1.110',NULL,'2018-11-25 14:58:33',6),(650,'Ange Mutesi',NULL,NULL,NULL,'2018-11-25 15:01:14',NULL,'DESKTOP-BCD89LB/192.168.1.110',NULL,'2018-11-25 15:01:26',2),(651,'Fabrice Ngenzi',NULL,NULL,NULL,'2018-11-25 15:05:15',NULL,'DESKTOP-BCD89LB/192.168.1.110',NULL,'2018-11-25 15:05:29',6),(652,'Ange Mutesi',NULL,NULL,NULL,'2018-11-25 15:06:04',NULL,'DESKTOP-BCD89LB/192.168.1.110',NULL,'2018-11-25 15:06:12',2),(653,'Hitimana Emile',NULL,NULL,NULL,'2018-11-27 11:26:08',NULL,'DESKTOP-BCD89LB/192.168.42.19',NULL,'2018-11-27 11:26:25',1),(654,'Hitimana Emile',NULL,NULL,NULL,'2018-11-27 11:27:35',NULL,'DESKTOP-BCD89LB/192.168.42.19',NULL,'2018-11-27 11:27:45',1),(655,'Hitimana Emile',NULL,NULL,NULL,'2018-11-27 11:29:02',NULL,'DESKTOP-BCD89LB/192.168.42.19',NULL,'2018-11-27 11:29:10',1),(656,'Hitimana Emile',NULL,NULL,NULL,'2018-11-27 11:33:59',NULL,'DESKTOP-BCD89LB/192.168.42.19',NULL,'2018-11-27 11:34:15',1),(657,'Hitimana Emile',NULL,NULL,NULL,'2018-11-27 11:35:13',NULL,'DESKTOP-BCD89LB/192.168.42.19',NULL,'2018-11-27 11:35:23',1),(658,'Ngango Junior',NULL,NULL,NULL,'2018-11-30 07:44:42',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-11-30 07:45:04',17),(659,'Ngango Junior',NULL,NULL,NULL,'2018-12-02 12:42:57',NULL,'DESKTOP-BCD89LB/192.168.1.123',NULL,'2018-12-02 12:44:00',17),(660,'Ngango Junior',NULL,NULL,NULL,'2018-12-02 12:44:06',NULL,'DESKTOP-BCD89LB/192.168.1.123',NULL,'2018-12-02 12:45:29',17),(661,'Ngango Junior',NULL,NULL,NULL,'2018-12-02 12:48:08',NULL,'DESKTOP-BCD89LB/192.168.1.123',NULL,'2018-12-02 12:48:22',17),(662,'Ngango Junior',NULL,NULL,NULL,'2018-12-11 15:21:34',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-11 15:22:00',17),(663,'Ngango Junior',NULL,NULL,NULL,'2018-12-11 16:03:02',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-11 16:03:17',17),(664,'Ngango Junior',NULL,NULL,NULL,'2018-12-11 22:15:30',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-12-11 22:18:14',17),(665,'Ngango Junior',NULL,NULL,NULL,'2018-12-11 22:18:34',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-12-11 22:19:19',17),(666,'Ngango Junior',NULL,NULL,NULL,'2018-12-11 22:22:15',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-12-11 22:22:43',17),(667,'Hitimana Emile',NULL,NULL,NULL,'2018-12-11 22:24:06',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-12-11 22:24:16',1),(668,'Hitimana Emile',NULL,NULL,NULL,'2018-12-11 22:27:21',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-12-11 22:27:42',1),(669,'Ngango Junior',NULL,NULL,NULL,'2018-12-11 22:31:03',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-12-11 22:31:43',17),(670,'Ngango Junior',NULL,NULL,NULL,'2018-12-12 11:56:37',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-12 12:09:04',17),(671,'Ngango Junior',NULL,NULL,NULL,'2018-12-12 12:24:21',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-12 12:25:48',17),(672,'Nshuti Patric',NULL,NULL,NULL,'2018-12-12 12:31:07',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-12 12:33:22',8),(673,'Nshuti Patric',NULL,NULL,NULL,'2018-12-12 14:47:01',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-12 14:50:40',8),(674,'Ange Mutesi',NULL,NULL,NULL,'2018-12-12 15:11:38',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-12 15:11:56',2),(675,'Ange Mutesi',NULL,NULL,NULL,'2018-12-12 15:35:17',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-12 15:35:52',2),(676,'Ange Mutesi',NULL,NULL,NULL,'2018-12-13 09:36:34',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-13 09:37:00',2),(677,'Ange Mutesi',NULL,NULL,NULL,'2018-12-13 10:09:54',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-13 10:10:12',2),(678,'Ange Mutesi',NULL,NULL,NULL,'2018-12-13 10:32:28',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-13 10:32:46',2),(679,'Ange Mutesi',NULL,NULL,NULL,'2018-12-13 11:52:05',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-13 11:57:08',2),(680,'Ange Mutesi',NULL,NULL,NULL,'2018-12-13 12:24:07',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-13 12:24:29',2),(681,'Ange Mutesi',NULL,NULL,NULL,'2018-12-13 12:34:36',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-13 12:34:51',2),(682,'Ange Mutesi',NULL,NULL,NULL,'2018-12-13 12:40:34',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-13 12:40:56',2),(683,'Ange Mutesi',NULL,NULL,NULL,'2018-12-13 12:49:58',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-13 12:50:17',2),(684,'Ange Mutesi',NULL,NULL,NULL,'2018-12-13 12:56:20',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-13 12:57:00',2),(685,'Ange Mutesi',NULL,NULL,NULL,'2018-12-13 13:17:15',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-13 13:20:42',2),(686,'Ange Mutesi',NULL,NULL,NULL,'2018-12-13 15:16:29',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-13 15:17:02',2),(687,'Ange Mutesi',NULL,NULL,NULL,'2018-12-13 15:34:05',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-13 15:34:35',2),(688,'Ange Mutesi',NULL,NULL,NULL,'2018-12-13 16:12:15',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-13 16:12:45',2),(689,'Ange Mutesi',NULL,NULL,NULL,'2018-12-13 16:38:45',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-13 16:40:01',2),(690,'Ange Mutesi',NULL,NULL,NULL,'2018-12-13 16:57:33',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-13 16:57:56',2),(691,'Ange Mutesi',NULL,NULL,NULL,'2018-12-13 17:36:40',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-13 17:37:03',2),(692,'Ange Mutesi',NULL,NULL,NULL,'2018-12-13 17:50:33',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-13 17:50:58',2),(693,'Ange Mutesi',NULL,NULL,NULL,'2018-12-13 18:34:46',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-13 18:35:20',2),(694,'Ange Mutesi',NULL,NULL,NULL,'2018-12-13 18:50:49',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-13 18:51:15',2),(695,'Ange Mutesi',NULL,NULL,NULL,'2018-12-13 19:05:03',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-13 19:06:01',2),(696,'Ange Mutesi',NULL,NULL,NULL,'2018-12-14 09:50:17',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-14 09:52:34',2),(697,'Ange Mutesi',NULL,NULL,NULL,'2018-12-14 10:10:22',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-14 10:11:27',2),(698,'Ange Mutesi',NULL,NULL,NULL,'2018-12-14 12:29:01',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-14 12:32:16',2),(699,'Ange Mutesi',NULL,NULL,NULL,'2018-12-14 12:58:54',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-14 12:59:43',2),(700,'Ange Mutesi',NULL,NULL,NULL,'2018-12-14 13:08:16',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-14 13:09:04',2),(701,'Ange Mutesi',NULL,NULL,NULL,'2018-12-14 13:33:05',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-14 13:35:26',2),(702,'Ange Mutesi',NULL,NULL,NULL,'2018-12-14 13:51:20',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-14 13:51:48',2),(703,'Ange Mutesi',NULL,NULL,NULL,'2018-12-14 14:09:18',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-14 14:09:44',2),(704,'Ange Mutesi',NULL,NULL,NULL,'2018-12-14 14:32:08',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-14 14:33:41',2),(705,'Ange Mutesi',NULL,NULL,NULL,'2018-12-14 15:29:25',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-14 15:29:50',2),(706,'Ange Mutesi',NULL,NULL,NULL,'2018-12-14 15:38:00',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-14 15:38:33',2),(707,'Ange Mutesi',NULL,NULL,NULL,'2018-12-14 15:46:27',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-14 15:46:51',2),(708,'Ange Mutesi',NULL,NULL,NULL,'2018-12-16 12:49:55',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-16 12:54:47',2),(709,'Ange Mutesi',NULL,NULL,NULL,'2018-12-16 14:01:49',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-16 14:02:18',2),(710,'Ange Mutesi',NULL,NULL,NULL,'2018-12-16 14:22:16',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-16 14:22:34',2),(711,'Ange Mutesi',NULL,NULL,NULL,'2018-12-16 14:23:57',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-16 14:24:04',2),(712,'Ange Mutesi',NULL,NULL,NULL,'2018-12-16 14:42:17',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-16 14:42:41',2),(713,'Ange Mutesi',NULL,NULL,NULL,'2018-12-16 15:18:01',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-16 15:21:06',2),(714,'Ange Mutesi',NULL,NULL,NULL,'2018-12-16 15:27:47',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-16 15:28:05',2),(715,'Ange Mutesi',NULL,NULL,NULL,'2018-12-16 16:37:48',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-16 16:39:19',2),(716,'Ange Mutesi',NULL,NULL,NULL,'2018-12-16 17:25:20',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-16 17:25:39',2),(717,'Ange Mutesi',NULL,NULL,NULL,'2018-12-16 17:34:22',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-12-16 17:34:44',2),(718,'Ange Mutesi',NULL,NULL,NULL,'2018-12-16 19:36:43',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-12-16 19:37:24',2),(719,'Ange Mutesi',NULL,NULL,NULL,'2018-12-16 20:19:19',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-12-16 20:19:37',2),(720,'Ange Mutesi',NULL,NULL,NULL,'2018-12-16 20:33:41',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-12-16 20:34:09',2),(721,'Ange Mutesi',NULL,NULL,NULL,'2018-12-16 20:48:02',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-12-16 20:48:34',2),(722,'Ange Mutesi',NULL,NULL,NULL,'2018-12-16 21:05:27',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2018-12-16 21:05:52',2),(723,'Ange Mutesi',NULL,NULL,NULL,'2018-12-17 08:20:22',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 08:20:54',2),(724,'Kabera Nepo',NULL,NULL,NULL,'2018-12-17 09:19:48',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 09:28:03',4),(725,'Ange Mutesi',NULL,NULL,NULL,'2018-12-17 10:55:02',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 10:55:21',2),(726,'Ange Mutesi',NULL,NULL,NULL,'2018-12-17 11:06:02',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 11:07:23',2),(727,'Ange Mutesi',NULL,NULL,NULL,'2018-12-17 11:45:10',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 11:45:30',2),(728,'Ange Mutesi',NULL,NULL,NULL,'2018-12-17 11:56:26',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 11:58:26',2),(729,'Ange Mutesi',NULL,NULL,NULL,'2018-12-17 12:10:48',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 12:11:28',2),(730,'Ange Mutesi',NULL,NULL,NULL,'2018-12-17 14:12:09',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 14:16:13',2),(731,'Ange Mutesi',NULL,NULL,NULL,'2018-12-17 15:09:45',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 15:10:05',2),(732,'Ange Mutesi',NULL,NULL,NULL,'2018-12-17 16:41:14',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 16:41:38',2),(733,'Ange Mutesi',NULL,NULL,NULL,'2018-12-17 17:00:38',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 17:00:56',2),(734,'Emma Sibo',NULL,NULL,NULL,'2018-12-17 17:12:11',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 17:13:06',3),(735,'Ngango Junior',NULL,NULL,NULL,'2018-12-17 17:13:15',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 17:13:47',17),(736,'Hitimana Emile',NULL,NULL,NULL,'2018-12-17 17:16:21',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 17:16:31',1),(737,'Ngango Junior',NULL,NULL,NULL,'2018-12-17 17:20:07',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 17:20:20',17),(738,'Kabera Nepo',NULL,NULL,NULL,'2018-12-17 17:24:03',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 17:24:20',4),(739,'Ngango Junior',NULL,NULL,NULL,'2018-12-17 17:28:06',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 17:28:24',17),(740,'Kabera Nepo',NULL,NULL,NULL,'2018-12-17 17:30:08',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 17:30:16',4),(741,'Ngango Junior',NULL,NULL,NULL,'2018-12-17 17:30:23',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 17:30:35',17),(742,'Kabera Nepo',NULL,NULL,NULL,'2018-12-17 17:33:27',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 17:33:41',4),(743,'Kabera Nepo',NULL,NULL,NULL,'2018-12-17 17:36:28',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 17:36:36',4),(744,'Emma Sibo',NULL,NULL,NULL,'2018-12-17 17:38:54',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 17:39:01',3),(745,'Kabera Nepo',NULL,NULL,NULL,'2018-12-17 17:40:35',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 17:40:47',4),(746,'Kabera Nepo',NULL,NULL,NULL,'2018-12-17 17:41:29',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 17:42:15',4),(747,'Kabera Nepo',NULL,NULL,NULL,'2018-12-17 17:58:09',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 17:58:34',4),(748,'Ange Mutesi',NULL,NULL,NULL,'2018-12-17 17:59:38',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 17:59:51',2),(749,'Emma Sibo',NULL,NULL,NULL,'2018-12-17 18:00:04',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 18:00:29',3),(750,'Kabera Nepo',NULL,NULL,NULL,'2018-12-17 18:01:00',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 18:01:16',4),(751,'Emma Sibo',NULL,NULL,NULL,'2018-12-17 18:03:07',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 18:03:15',3),(752,'Hitimana Emile',NULL,NULL,NULL,'2018-12-17 18:09:06',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 18:09:14',1),(753,'Ange Mutesi',NULL,NULL,NULL,'2018-12-17 18:13:27',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 18:13:38',2),(754,'Hitimana Emile',NULL,NULL,NULL,'2018-12-17 18:16:14',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 18:16:32',1),(755,'Ange Mutesi',NULL,NULL,NULL,'2018-12-17 18:18:46',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 18:18:57',2),(756,'Ange Mutesi',NULL,NULL,NULL,'2018-12-17 18:25:23',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-17 18:25:49',2),(757,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 08:34:55',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 08:35:35',2),(758,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 08:45:09',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 08:45:26',2),(759,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 09:18:05',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 09:18:31',2),(760,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 09:48:47',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 09:50:37',2),(761,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 10:04:10',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 10:04:26',2),(762,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 10:44:39',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 10:44:58',2),(763,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 10:56:04',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 10:56:21',2),(764,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 11:12:50',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 11:13:14',2),(765,'Kabera Nepo',NULL,NULL,NULL,'2018-12-18 11:15:04',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 11:15:13',4),(766,'Emma Sibo',NULL,NULL,NULL,'2018-12-18 11:16:35',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 11:16:43',3),(767,'Kabera Nepo',NULL,NULL,NULL,'2018-12-18 11:20:28',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 11:20:42',4),(768,'Kabera Nepo',NULL,NULL,NULL,'2018-12-18 11:34:35',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 11:35:30',4),(769,'Emma Sibo',NULL,NULL,NULL,'2018-12-18 11:42:16',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 11:42:35',3),(770,'Kabera Nepo',NULL,NULL,NULL,'2018-12-18 12:49:26',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 12:51:35',4),(771,'Emma Sibo',NULL,NULL,NULL,'2018-12-18 13:13:31',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 13:14:18',3),(772,'Emma Sibo',NULL,NULL,NULL,'2018-12-18 14:46:07',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 14:46:36',3),(773,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 14:54:43',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 14:54:50',2),(774,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 15:10:43',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 15:11:09',2),(775,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 15:56:01',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 16:03:52',2),(776,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 16:16:18',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 16:16:43',2),(777,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 16:25:12',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 16:25:28',2),(778,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 16:40:53',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 16:41:11',2),(779,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 17:06:08',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 17:06:37',2),(780,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 17:26:24',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 17:28:11',2),(781,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 17:34:49',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 17:35:06',2),(782,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 17:40:09',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 17:40:30',2),(783,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 17:52:55',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 17:53:33',2),(784,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 17:56:30',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 17:56:43',2),(785,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 18:05:58',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 18:06:20',2),(786,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 18:22:43',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 18:23:02',2),(787,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 18:39:35',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 18:40:47',2),(788,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 19:13:40',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 19:15:35',2),(789,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 19:29:46',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 19:30:09',2),(790,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 19:41:26',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 19:42:16',2),(791,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 19:56:18',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 19:57:27',2),(792,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 20:06:12',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 20:06:41',2),(793,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 20:22:50',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 20:23:07',2),(794,'Ange Mutesi',NULL,NULL,NULL,'2018-12-18 20:32:01',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-18 20:32:37',2),(795,'Ange Mutesi',NULL,NULL,NULL,'2018-12-19 08:57:25',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 08:58:46',2),(796,'Ange Mutesi',NULL,NULL,NULL,'2018-12-19 09:03:18',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 09:03:36',2),(797,'Emma Sibo',NULL,NULL,NULL,'2018-12-19 09:05:16',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 09:06:26',3),(798,'Kabera Nepo',NULL,NULL,NULL,'2018-12-19 09:07:06',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 09:07:15',4),(799,'Emma Sibo',NULL,NULL,NULL,'2018-12-19 09:08:02',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 09:08:11',3),(800,'Kabera Nepo',NULL,NULL,NULL,'2018-12-19 09:09:38',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 09:10:16',4),(801,'Emma Sibo',NULL,NULL,NULL,'2018-12-19 09:11:09',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 09:11:22',3),(802,'Kabera Nepo',NULL,NULL,NULL,'2018-12-19 09:12:22',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 09:12:32',4),(803,'Emma Sibo',NULL,NULL,NULL,'2018-12-19 09:13:07',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 09:13:16',3),(804,'Emma Sibo',NULL,NULL,NULL,'2018-12-19 10:08:41',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 10:11:20',3),(805,'Ange Mutesi',NULL,NULL,NULL,'2018-12-19 10:14:28',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 10:14:41',2),(806,'Kabera Nepo',NULL,NULL,NULL,'2018-12-19 10:17:19',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 10:17:29',4),(807,'Emma Sibo',NULL,NULL,NULL,'2018-12-19 10:18:52',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 10:19:04',3),(808,'Emma Sibo',NULL,NULL,NULL,'2018-12-19 10:31:45',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 10:32:02',3),(809,'Ange Mutesi',NULL,NULL,NULL,'2018-12-19 10:33:34',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 10:33:43',2),(810,'Kabera Nepo',NULL,NULL,NULL,'2018-12-19 10:34:44',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 10:34:53',4),(811,'Emma Sibo',NULL,NULL,NULL,'2018-12-19 10:46:03',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 10:46:11',3),(812,'Kabera Nepo',NULL,NULL,NULL,'2018-12-19 10:47:54',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 10:48:05',4),(813,'Emma Sibo',NULL,NULL,NULL,'2018-12-19 10:48:28',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 10:48:36',3),(814,'Emma Sibo',NULL,NULL,NULL,'2018-12-19 11:08:33',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 11:08:52',3),(815,'Emma Sibo',NULL,NULL,NULL,'2018-12-19 11:41:02',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 11:41:15',3),(816,'Kabera Nepo',NULL,NULL,NULL,'2018-12-19 11:43:34',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 11:43:42',4),(817,'Emma Sibo',NULL,NULL,NULL,'2018-12-19 11:43:57',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 11:44:05',3),(818,'Ange Mutesi',NULL,NULL,NULL,'2018-12-19 11:44:33',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 11:44:40',2),(819,'Emma Sibo',NULL,NULL,NULL,'2018-12-19 12:35:49',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 12:36:28',3),(820,'Emma Sibo',NULL,NULL,NULL,'2018-12-19 13:02:21',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 13:02:43',3),(821,'Kabera Nepo',NULL,NULL,NULL,'2018-12-19 13:04:44',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 13:05:15',4),(822,'Emma Sibo',NULL,NULL,NULL,'2018-12-19 13:05:46',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 13:05:56',3),(823,'Kabera Nepo',NULL,NULL,NULL,'2018-12-19 14:49:29',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 14:50:02',4),(824,'Kabera Nepo',NULL,NULL,NULL,'2018-12-19 14:57:41',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 14:57:51',4),(825,'Alain Muhire',NULL,NULL,NULL,'2018-12-19 14:58:51',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 14:59:01',5),(826,'Alain Muhire',NULL,NULL,NULL,'2018-12-19 14:59:36',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 14:59:45',5),(827,'Kabera Nepo',NULL,NULL,NULL,'2018-12-19 15:06:49',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 15:06:57',4),(828,'Alain Muhire',NULL,NULL,NULL,'2018-12-19 15:18:25',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 15:18:34',5),(829,'Kabera Nepo',NULL,NULL,NULL,'2018-12-19 15:24:36',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-19 15:24:46',4),(830,'Emma Sibo',NULL,NULL,NULL,'2018-12-28 09:47:30',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 09:48:49',3),(831,'Ngango Junior',NULL,NULL,NULL,'2018-12-28 09:49:15',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 09:49:39',17),(832,'Ngango Junior',NULL,NULL,NULL,'2018-12-28 10:42:08',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 10:42:45',17),(833,'Ngango Junior',NULL,NULL,NULL,'2018-12-28 10:48:35',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 10:48:49',17),(834,'Ngango Junior',NULL,NULL,NULL,'2018-12-28 10:49:33',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 10:49:45',17),(835,'Ngango Junior',NULL,NULL,NULL,'2018-12-28 11:59:00',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 12:01:40',17),(836,'Hitimana Emile',NULL,NULL,NULL,'2018-12-28 12:07:12',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 12:07:20',1),(837,'Ngango Junior',NULL,NULL,NULL,'2018-12-28 12:10:18',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 12:10:42',17),(838,'Ngango Junior',NULL,NULL,NULL,'2018-12-28 13:31:45',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 13:32:54',17),(839,'Ngango Junior',NULL,NULL,NULL,'2018-12-28 13:45:56',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 13:46:32',17),(840,'Hitimana Emile',NULL,NULL,NULL,'2018-12-28 13:53:34',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 13:53:42',1),(841,'Ngango Junior',NULL,NULL,NULL,'2018-12-28 13:59:43',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 13:59:53',17),(842,'Ngango Junior',NULL,NULL,NULL,'2018-12-28 14:02:00',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 14:02:11',17),(843,'Hitimana Emile',NULL,NULL,NULL,'2018-12-28 14:04:46',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 14:04:54',1),(844,'Ngango Junior',NULL,NULL,NULL,'2018-12-28 14:23:45',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 14:25:20',17),(845,'Ngango Junior',NULL,NULL,NULL,'2018-12-28 14:28:38',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 14:28:55',17),(846,'Ngango Junior',NULL,NULL,NULL,'2018-12-28 14:35:59',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 14:36:27',17),(847,'Ngango Junior',NULL,NULL,NULL,'2018-12-28 14:39:05',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 14:39:20',17),(848,'Ngango Junior',NULL,NULL,NULL,'2018-12-28 14:49:26',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 14:49:44',17),(849,'Ngango Junior',NULL,NULL,NULL,'2018-12-28 15:02:08',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 15:03:00',17),(850,'Ngango Junior',NULL,NULL,NULL,'2018-12-28 15:13:14',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 15:13:37',17),(851,'Ngango Junior',NULL,NULL,NULL,'2018-12-28 15:35:23',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 15:35:45',17),(852,'Ngango Junior',NULL,NULL,NULL,'2018-12-28 16:20:01',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 16:22:14',17),(853,'Hitimana Emile',NULL,NULL,NULL,'2018-12-28 16:26:34',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 16:26:44',1),(854,'Ngango Junior',NULL,NULL,NULL,'2018-12-28 16:30:38',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 16:30:58',17),(855,'Hitimana Emile',NULL,NULL,NULL,'2018-12-28 16:33:17',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 16:33:26',1),(856,'Ngango Junior',NULL,NULL,NULL,'2018-12-28 16:34:14',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 16:34:28',17),(857,'Hitimana Emile',NULL,NULL,NULL,'2018-12-28 16:44:48',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 16:45:08',1),(858,'Ngango Junior',NULL,NULL,NULL,'2018-12-28 16:47:02',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 16:47:41',17),(859,'Hitimana Emile',NULL,NULL,NULL,'2018-12-28 17:02:11',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 17:02:30',1),(860,'Ngango Junior',NULL,NULL,NULL,'2018-12-28 17:06:46',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 17:07:00',17),(861,'Hitimana Emile',NULL,NULL,NULL,'2018-12-28 17:13:10',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 17:13:27',1),(862,'Ngango Junior',NULL,NULL,NULL,'2018-12-28 17:17:44',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-28 17:18:10',17),(863,'Ngango Junior',NULL,NULL,NULL,'2018-12-31 08:52:49',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-31 08:53:37',17),(864,'Ngango Junior',NULL,NULL,NULL,'2018-12-31 09:02:02',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-31 09:02:22',17),(865,'Ngango Junior',NULL,NULL,NULL,'2018-12-31 09:15:54',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-31 09:17:22',17),(866,'Ngango Junior',NULL,NULL,NULL,'2018-12-31 09:23:51',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-31 09:24:10',17),(867,'Ngango Junior',NULL,NULL,NULL,'2018-12-31 09:35:17',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-31 09:35:37',17),(868,'Ngango Junior',NULL,NULL,NULL,'2018-12-31 09:43:29',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-31 09:43:48',17),(869,'Ngango Junior',NULL,NULL,NULL,'2018-12-31 09:54:02',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-31 09:54:27',17),(870,'Ngango Junior',NULL,NULL,NULL,'2018-12-31 10:03:19',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-31 10:03:29',17),(871,'Ngango Junior',NULL,NULL,NULL,'2018-12-31 10:14:11',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-31 10:15:13',17),(872,'Ngango Junior',NULL,NULL,NULL,'2018-12-31 10:36:13',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-31 10:36:34',17),(873,'Ngango Junior',NULL,NULL,NULL,'2018-12-31 10:59:54',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-31 11:01:40',17),(874,'Hitimana Emile',NULL,NULL,NULL,'2018-12-31 11:07:31',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-31 11:14:05',1),(875,'Hitimana Emile',NULL,NULL,NULL,'2018-12-31 11:35:33',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-31 11:36:37',1),(876,'Ngango Junior',NULL,NULL,NULL,'2018-12-31 11:42:38',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-31 11:42:57',17),(877,'Hitimana Emile',NULL,NULL,NULL,'2018-12-31 12:22:14',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-31 12:23:51',1),(878,'Hitimana Emile',NULL,NULL,NULL,'2018-12-31 13:32:39',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-31 13:33:11',1),(879,'Hitimana Emile',NULL,NULL,NULL,'2018-12-31 14:05:10',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-31 14:06:17',1),(880,'Ngango Junior',NULL,NULL,NULL,'2018-12-31 14:45:25',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2018-12-31 14:45:45',17),(881,'Ngango Junior',NULL,NULL,NULL,'2019-01-02 09:02:56',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2019-01-02 09:03:15',17),(882,'Hitimana Emile',NULL,NULL,NULL,'2019-01-02 09:09:41',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2019-01-02 09:09:53',1),(883,'Ange Mutesi',NULL,NULL,NULL,'2019-01-02 09:40:02',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2019-01-02 09:40:36',2),(884,'Ngango Junior',NULL,NULL,NULL,'2019-01-02 09:43:03',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2019-01-02 09:44:09',17),(885,'Ngango Junior',NULL,NULL,NULL,'2019-01-02 10:00:26',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2019-01-02 10:00:36',17),(886,'Ange Mutesi',NULL,NULL,NULL,'2019-01-02 10:00:42',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2019-01-02 10:00:56',2),(887,'Hitimana Emile',NULL,NULL,NULL,'2019-01-02 10:45:46',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2019-01-02 10:47:36',1),(888,'Hitimana Emile',NULL,NULL,NULL,'2019-01-02 10:49:42',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2019-01-02 10:49:53',1),(889,'Hitimana Emile',NULL,NULL,NULL,'2019-01-02 10:51:39',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2019-01-02 10:51:48',1),(890,'Ange Mutesi',NULL,NULL,NULL,'2019-01-02 11:03:57',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2019-01-02 11:04:09',2),(891,'Ange Mutesi',NULL,NULL,NULL,'2019-01-02 11:06:26',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2019-01-02 11:06:54',2),(892,'Ngango Junior',NULL,NULL,NULL,'2019-01-02 12:08:55',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2019-01-02 12:09:27',17),(893,'Ngango Junior',NULL,NULL,NULL,'2019-01-02 12:16:43',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2019-01-02 12:17:58',17),(894,'Ngango Junior',NULL,NULL,NULL,'2019-01-02 12:19:07',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2019-01-02 12:19:17',17),(895,'Ngango Junior',NULL,NULL,NULL,'2019-01-02 12:20:10',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2019-01-02 12:20:22',17),(896,'Hitimana Emile',NULL,NULL,NULL,'2019-01-02 12:24:07',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2019-01-02 12:24:15',1),(897,'Emma Sibo',NULL,NULL,NULL,'2019-01-02 12:25:33',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2019-01-02 12:25:41',3),(898,'Hitimana Emile',NULL,NULL,NULL,'2019-01-02 12:25:50',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2019-01-02 12:25:59',1),(899,'Hitimana Emile',NULL,NULL,NULL,'2019-01-02 12:27:21',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2019-01-02 12:27:30',1),(900,'Ngango Junior',NULL,NULL,NULL,'2019-01-02 12:58:17',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2019-01-02 12:59:40',17),(901,'Ngango Junior',NULL,NULL,NULL,'2019-01-02 13:40:38',NULL,'DESKTOP-BCD89LB/192.168.1.117',NULL,'2019-01-02 13:41:04',17),(902,'Ngango Junior',NULL,NULL,NULL,'2019-01-02 19:51:10',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2019-01-02 19:51:52',17),(903,'Ngango Junior',NULL,NULL,NULL,'2019-01-02 20:57:36',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2019-01-02 20:58:59',17),(904,'Ngango Junior',NULL,NULL,NULL,'2019-01-02 21:20:48',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2019-01-02 21:21:12',17),(905,'Ngango Junior',NULL,NULL,NULL,'2019-01-02 21:20:48',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2019-01-02 21:34:32',17),(906,'Ngango Junior',NULL,NULL,NULL,'2019-01-02 21:47:07',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2019-01-02 21:49:25',17),(907,'Ngango Junior',NULL,NULL,NULL,'2019-01-02 22:03:02',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2019-01-02 22:03:14',17),(908,'Ngango Junior',NULL,NULL,NULL,'2019-01-02 22:22:53',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2019-01-02 22:23:17',17),(909,'Ngango Junior',NULL,NULL,NULL,'2019-01-02 22:47:49',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2019-01-02 22:48:18',17),(910,'Ngango Junior',NULL,NULL,NULL,'2019-01-02 22:57:27',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2019-01-02 22:57:47',17),(911,'Ngango Junior',NULL,NULL,NULL,'2019-01-02 23:21:34',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2019-01-02 23:21:57',17),(912,'Ngango Junior',NULL,NULL,NULL,'2019-01-02 23:30:42',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2019-01-02 23:31:06',17),(913,'Ngango Junior',NULL,NULL,NULL,'2019-01-02 23:40:18',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2019-01-02 23:40:39',17),(914,'Ngango Junior',NULL,NULL,NULL,'2019-01-02 23:47:05',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2019-01-02 23:47:27',17),(915,'Ngango Junior',NULL,NULL,NULL,'2019-01-02 23:52:34',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2019-01-02 23:52:56',17),(916,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 00:00:13',NULL,'DESKTOP-BCD89LB/127.0.0.1',NULL,'2019-01-03 00:00:36',17),(917,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 08:39:52',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-03 08:40:53',17),(918,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 09:54:09',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-03 09:54:52',17),(919,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 10:05:38',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-03 10:09:44',17),(920,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 10:24:48',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-03 10:25:08',17),(921,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 10:33:56',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-03 10:35:06',17),(922,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 11:23:38',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-03 11:23:57',17),(923,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 11:40:42',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-03 11:41:23',17),(924,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 11:53:57',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-03 11:54:56',17),(925,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 12:06:18',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-03 12:10:29',17),(926,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 12:22:58',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-03 12:23:16',17),(927,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 12:33:59',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-03 12:34:20',17),(928,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 12:48:30',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-03 12:50:01',17),(929,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 13:13:18',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-03 13:14:48',17),(930,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 13:34:21',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-03 13:34:42',17),(931,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 14:30:34',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-03 14:33:25',17),(932,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 14:55:44',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-03 14:56:08',17),(933,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 15:05:30',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-03 15:06:41',17),(934,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 15:44:18',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-03 15:44:42',17),(935,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 16:37:04',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-03 16:40:11',17),(936,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 16:37:04',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-03 16:40:44',NULL,17),(937,'Ange Mutesi',NULL,NULL,NULL,'2019-01-03 16:40:45',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-03 16:40:54',2),(938,'Ange Mutesi',NULL,NULL,NULL,'2019-01-03 16:40:45',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-03 16:44:43',NULL,2),(939,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 16:44:43',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-03 16:44:56',17),(940,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 16:44:43',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-03 16:45:52',NULL,17),(941,'Kabera Nepo',NULL,NULL,NULL,'2019-01-03 16:45:53',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-03 16:46:01',4),(942,'Kabera Nepo',NULL,NULL,NULL,'2019-01-03 16:45:53',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-03 16:46:27',NULL,4),(943,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 16:46:28',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-03 16:46:38',17),(944,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 17:06:49',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-03 17:08:39',17),(945,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 17:16:37',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-03 17:17:13',17),(946,'Ngango Junior',NULL,NULL,NULL,'2019-01-03 18:09:33',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-03 18:09:55',17),(947,'Ngango Junior',NULL,NULL,NULL,'2019-01-04 09:15:07',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 09:16:04',17),(948,'Ngango Junior',NULL,NULL,NULL,'2019-01-04 09:15:07',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 09:17:02',NULL,17),(949,'Ngango Junior',NULL,NULL,NULL,'2019-01-04 09:17:03',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 09:17:21',17),(950,'Ngango Junior',NULL,NULL,NULL,'2019-01-04 09:27:31',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 09:27:54',17),(951,'Ngango Junior',NULL,NULL,NULL,'2019-01-04 10:07:04',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 10:07:59',17),(952,'Ngango Junior',NULL,NULL,NULL,'2019-01-04 10:24:11',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 10:24:32',17),(953,'Ngango Junior',NULL,NULL,NULL,'2019-01-04 10:55:23',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 10:55:50',17),(954,'Ngango Junior',NULL,NULL,NULL,'2019-01-04 10:55:23',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 10:58:27',NULL,17),(955,'Ngango Junior',NULL,NULL,NULL,'2019-01-04 10:58:29',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 10:59:19',17),(956,'Ngango Junior',NULL,NULL,NULL,'2019-01-04 10:58:29',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 11:02:28',NULL,17),(957,'Ngango Junior',NULL,NULL,NULL,'2019-01-04 11:02:30',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 11:04:05',17),(958,'Ngango Junior',NULL,NULL,NULL,'2019-01-04 11:02:30',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 11:06:22',NULL,17),(959,'Hitimana Emile',NULL,NULL,NULL,'2019-01-04 11:06:23',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 11:06:31',1),(960,'Ngango Junior',NULL,NULL,NULL,'2019-01-04 11:47:21',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 11:48:04',17),(961,'Ngango Junior',NULL,NULL,NULL,'2019-01-04 11:47:21',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 11:52:05',NULL,17),(962,'Hitimana Emile',NULL,NULL,NULL,'2019-01-04 11:52:06',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 11:52:15',1),(963,'Hitimana Emile',NULL,NULL,NULL,'2019-01-04 11:52:06',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 11:52:23',NULL,1),(964,'Ngango Junior',NULL,NULL,NULL,'2019-01-04 11:52:24',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 11:52:35',17),(965,'Ngango Junior',NULL,NULL,NULL,'2019-01-04 11:52:24',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 11:53:33',NULL,17),(966,'Hitimana Emile',NULL,NULL,NULL,'2019-01-04 11:53:34',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 11:53:44',1),(967,'Hitimana Emile',NULL,NULL,NULL,'2019-01-04 11:53:34',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 11:54:09',NULL,1),(968,'Ngango Junior',NULL,NULL,NULL,'2019-01-04 11:54:10',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 11:54:34',17),(969,'Ngango Junior',NULL,NULL,NULL,'2019-01-04 11:54:10',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 11:55:25',NULL,17),(970,'Ngango Junior',NULL,NULL,NULL,'2019-01-04 11:55:26',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 11:55:37',17),(971,'Ngango Junior',NULL,NULL,NULL,'2019-01-04 11:55:26',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 11:58:01',NULL,17),(972,'Hitimana Emile',NULL,NULL,NULL,'2019-01-04 11:58:02',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 11:58:10',1),(973,'Hitimana Emile',NULL,NULL,NULL,'2019-01-04 12:55:46',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 12:56:16',1),(974,'Hitimana Emile',NULL,NULL,NULL,'2019-01-04 13:19:14',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 13:19:30',1),(975,'Ngango Junior',NULL,NULL,NULL,'2019-01-04 13:47:30',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 13:48:24',17),(976,'Hitimana Emile',NULL,NULL,NULL,'2019-01-04 13:57:12',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 13:57:31',1),(977,'Munezero Patric',NULL,NULL,NULL,'2019-01-04 13:57:12',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 13:59:49',22),(978,'Munezero Patric',NULL,NULL,NULL,'2019-01-04 13:57:12',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 13:59:56',NULL,22),(979,'Munezero Patric',NULL,NULL,NULL,'2019-01-04 13:59:57',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 14:00:21',22),(980,'Munezero Patric',NULL,NULL,NULL,'2019-01-04 13:59:57',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 14:02:16',NULL,22),(981,'Hitimana Emile',NULL,NULL,NULL,'2019-01-04 14:02:17',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 14:02:25',1),(982,'Hitimana Emile',NULL,NULL,NULL,'2019-01-04 14:02:17',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 14:08:46',NULL,1),(983,'Munezero Patric',NULL,NULL,NULL,'2019-01-04 14:08:47',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 14:08:58',22),(984,'Munezero Patric',NULL,NULL,NULL,'2019-01-04 14:08:47',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 14:10:47',NULL,22),(985,'Munezero Patric',NULL,NULL,NULL,'2019-01-04 14:10:48',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 14:11:03',22),(986,'Munezero Patric',NULL,NULL,NULL,'2019-01-04 14:10:48',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 14:12:41',NULL,22),(987,'Hitimana Emile',NULL,NULL,NULL,'2019-01-04 14:12:42',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 14:12:49',1),(988,'Hitimana Emile',NULL,NULL,NULL,'2019-01-04 14:12:42',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 14:13:59',NULL,1),(989,'Munezero Patric',NULL,NULL,NULL,'2019-01-04 14:14:00',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 14:14:13',22),(990,'Munezero Patric',NULL,NULL,NULL,'2019-01-04 14:14:00',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 14:35:10',NULL,22),(991,'Kabera Nepo',NULL,NULL,NULL,'2019-01-04 14:35:17',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 14:35:27',4),(992,'Kabera Nepo',NULL,NULL,NULL,'2019-01-04 14:35:17',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 14:40:26',NULL,4),(993,'Munezero Patric',NULL,NULL,NULL,'2019-01-04 14:40:38',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 14:41:02',22),(994,'Munezero Patric',NULL,NULL,NULL,'2019-01-04 14:48:08',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 14:48:28',22),(995,'Munezero Patric',NULL,NULL,NULL,'2019-01-04 14:48:08',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 14:50:28',NULL,22),(996,'Ntwali jean',NULL,NULL,NULL,'2019-01-04 14:50:29',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 14:51:10',23),(997,'Ntwali jean',NULL,NULL,NULL,'2019-01-04 14:50:29',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 14:57:38',NULL,23),(998,'Kabera Nepo',NULL,NULL,NULL,'2019-01-04 14:57:39',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 14:57:49',4),(999,'Kabera Nepo',NULL,NULL,NULL,'2019-01-04 14:57:39',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 14:58:53',NULL,4),(1000,'Munezero Patric',NULL,NULL,NULL,'2019-01-04 14:58:54',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 14:59:12',22),(1001,'Munezero Patric',NULL,NULL,NULL,'2019-01-04 14:58:54',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 15:00:57',NULL,22),(1002,'Ntwali jean',NULL,NULL,NULL,'2019-01-04 15:00:58',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 15:01:16',23),(1003,'Ntwali jean',NULL,NULL,NULL,'2019-01-04 15:18:55',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 15:19:19',23),(1004,'Ntwali jean',NULL,NULL,NULL,'2019-01-04 15:18:55',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 15:35:33',NULL,23),(1005,'Uwera Aline',NULL,NULL,NULL,'2019-01-04 15:35:35',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 15:35:54',24),(1006,'Uwera Aline',NULL,NULL,NULL,'2019-01-04 15:35:35',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 15:37:40',NULL,24),(1007,'Ntwali jean',NULL,NULL,NULL,'2019-01-04 15:37:42',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 15:38:02',23),(1008,'Ntwali jean',NULL,NULL,NULL,'2019-01-04 15:37:42',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 15:38:44',NULL,23),(1009,'Uwera Aline',NULL,NULL,NULL,'2019-01-04 15:38:46',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 15:38:58',24),(1010,'Uwera Aline',NULL,NULL,NULL,'2019-01-04 15:38:46',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 15:42:20',NULL,24),(1011,'Ntwali jean',NULL,NULL,NULL,'2019-01-04 15:42:21',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 15:42:46',23),(1012,'Ntwali jean',NULL,NULL,NULL,'2019-01-04 15:42:21',NULL,'DESKTOP-BCD89LB/192.168.1.118','2019-01-04 15:43:38',NULL,23),(1013,'Uwera Aline',NULL,NULL,NULL,'2019-01-04 15:43:39',NULL,'DESKTOP-BCD89LB/192.168.1.118',NULL,'2019-01-04 15:43:56',24);
/*!40000 ALTER TABLE `loginhistoric` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menuassignment`
--

DROP TABLE IF EXISTS `menuassignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menuassignment` (
  `menuAssgnId` int(11) NOT NULL AUTO_INCREMENT,
  `createdBy` varchar(255) DEFAULT NULL,
  `crtdDtTime` datetime DEFAULT NULL,
  `genericStatus` varchar(255) DEFAULT NULL,
  `optLock` datetime DEFAULT NULL,
  `upDtTime` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `defaultMenuUrl` int(11) DEFAULT NULL,
  `listOfMenu` int(11) DEFAULT NULL,
  `userCategory` int(11) DEFAULT NULL,
  PRIMARY KEY (`menuAssgnId`),
  UNIQUE KEY `listOfMenu` (`listOfMenu`,`userCategory`),
  UNIQUE KEY `defaultMenuUrl` (`defaultMenuUrl`,`userCategory`),
  KEY `FK183F59AC7E35E9B0` (`listOfMenu`),
  KEY `FK183F59AC6C01B37A` (`userCategory`),
  KEY `FK183F59AC8655D62B` (`defaultMenuUrl`),
  CONSTRAINT `FK183F59AC6C01B37A` FOREIGN KEY (`userCategory`) REFERENCES `usercategory` (`userCatid`),
  CONSTRAINT `FK183F59AC7E35E9B0` FOREIGN KEY (`listOfMenu`) REFERENCES `listofmenu` (`menuId`),
  CONSTRAINT `FK183F59AC8655D62B` FOREIGN KEY (`defaultMenuUrl`) REFERENCES `listofmenu` (`menuId`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menuassignment`
--

LOCK TABLES `menuassignment` WRITE;
/*!40000 ALTER TABLE `menuassignment` DISABLE KEYS */;
INSERT INTO `menuassignment` VALUES (1,'admin','2018-10-10 21:32:47','active','2018-10-10 21:32:47','2018-10-10 21:32:47','admin',NULL,1,1),(2,'admin','2018-10-10 21:52:16','active','2018-10-10 21:52:16','2018-10-10 21:52:16','admin',NULL,2,1),(3,'admin','2018-10-10 21:52:55','active','2018-10-10 21:52:55','2018-10-10 21:52:55','admin',NULL,3,1),(4,'admin','2018-10-11 20:19:53','active',NULL,'2018-10-11 20:19:53','admin',NULL,4,3),(5,'admin','2018-10-11 20:24:06','active',NULL,'2018-10-11 20:24:06','admin',NULL,5,1),(6,'admin','2018-10-11 20:25:23','active',NULL,'2018-10-11 20:25:23','admin',NULL,6,3),(7,'admin','2018-10-11 20:25:55','active',NULL,'2018-10-11 20:25:55','admin',NULL,7,3),(8,'admin','2018-10-11 20:26:31','active',NULL,'2018-10-11 20:26:31','admin',NULL,8,3),(9,'admin','2018-10-11 20:27:13','active',NULL,'2018-10-11 20:27:13','admin',NULL,9,3),(10,'admin','2018-10-11 20:27:49','active',NULL,'2018-10-11 20:27:49','admin',NULL,10,2),(11,'admin','2018-10-11 20:29:58','active',NULL,'2018-10-11 20:29:58','admin',NULL,11,4),(12,'admin','2018-10-11 20:30:22','active',NULL,'2018-10-11 20:30:22','admin',NULL,12,4),(13,'admin','2018-10-11 20:30:57','active',NULL,'2018-10-11 20:30:57','admin',NULL,13,4),(14,'admin','2018-10-12 08:45:33','active',NULL,'2018-10-12 08:45:33','admin',NULL,17,4),(15,'admin','2018-10-12 08:48:34','active',NULL,'2018-10-12 08:48:34','admin',NULL,21,2),(16,'admin','2018-10-12 08:49:02','active',NULL,'2018-10-12 08:49:02','admin',NULL,20,3),(17,'admin','2018-10-12 09:15:26','active',NULL,'2018-10-12 09:15:26','admin',NULL,25,3),(18,'admin','2018-10-12 09:26:22','active',NULL,'2018-10-12 09:26:22','admin',NULL,26,1),(19,'admin','2018-10-17 14:58:07','active',NULL,'2018-10-17 14:58:07','admin',NULL,27,3),(20,'admin','2018-10-19 22:56:25','active',NULL,'2018-10-19 22:56:25','admin',NULL,28,4),(21,'admin','2018-10-19 23:30:32','active',NULL,'2018-10-19 23:30:32','admin',NULL,6,1),(23,'admin','2018-10-20 16:43:59','active',NULL,'2018-10-20 16:43:59','admin',NULL,7,1),(24,'admin','2018-10-20 17:31:39','active',NULL,'2018-10-20 17:31:39','admin',NULL,9,1),(25,'admin','2018-10-20 17:31:39','active',NULL,'2018-10-20 17:31:39','admin',NULL,9,2),(26,'admin','2018-10-20 17:31:39','active',NULL,'2018-10-20 17:31:39','admin',NULL,9,4),(27,'admin','2018-10-20 19:52:46','active',NULL,'2018-10-20 19:52:46','admin',NULL,31,1);
/*!40000 ALTER TABLE `menuassignment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menugroup`
--

DROP TABLE IF EXISTS `menugroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menugroup` (
  `menuGroupId` int(11) NOT NULL AUTO_INCREMENT,
  `createdBy` varchar(255) DEFAULT NULL,
  `crtdDtTime` datetime DEFAULT NULL,
  `genericStatus` varchar(255) DEFAULT NULL,
  `optLock` datetime DEFAULT NULL,
  `upDtTime` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `creationDate` datetime DEFAULT NULL,
  `defaulGrouptMenu` varchar(255) DEFAULT NULL,
  `groupMenuCode` varchar(255) DEFAULT NULL,
  `iconImage` varchar(255) DEFAULT NULL,
  `menuColor` varchar(255) DEFAULT NULL,
  `menuGroupName` varchar(255) DEFAULT NULL,
  `userCategory` int(11) DEFAULT NULL,
  PRIMARY KEY (`menuGroupId`),
  UNIQUE KEY `groupMenuCode` (`groupMenuCode`),
  KEY `FK1B1E94406C01B37A` (`userCategory`),
  CONSTRAINT `FK1B1E94406C01B37A` FOREIGN KEY (`userCategory`) REFERENCES `usercategory` (`userCatid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menugroup`
--

LOCK TABLES `menugroup` WRITE;
/*!40000 ALTER TABLE `menugroup` DISABLE KEYS */;
INSERT INTO `menugroup` VALUES (1,'admin','2018-10-10 21:31:55','active','2018-10-10 21:31:55','2018-10-10 21:31:55','admin','2018-10-10 21:31:55','Menus','001',NULL,NULL,'Menu Creation',1),(2,'admin','2018-10-10 21:36:32','active',NULL,'2018-10-10 21:36:32','admin','2018-10-10 21:36:32','Institution Profile','002',NULL,NULL,'Institution Profile',3),(3,'admin','2018-10-10 21:54:06','active',NULL,'2018-10-10 21:54:06','admin','2018-10-10 21:54:06','Institution Management','003',NULL,NULL,'Institution Management',1),(4,'admin','2018-10-10 21:54:06','active',NULL,'2018-10-10 21:54:06','admin','2018-10-10 21:54:06','My Task','004',NULL,NULL,'My Task',3),(5,'admin','2018-10-10 21:54:06','active',NULL,'2018-10-10 21:54:06','admin','2018-10-10 21:54:06','Profile','005',NULL,NULL,'Profile',3),(6,'admin','2018-10-10 21:54:06','active',NULL,'2018-10-10 21:54:06','admin','2018-10-10 21:54:06','My Task','006',NULL,NULL,'My Task',2),(7,'admin','2018-10-10 21:54:06','active',NULL,'2018-10-10 21:54:06','admin','2018-10-10 21:54:06','My Task','007',NULL,NULL,'My Task',4),(8,'admin','2018-10-10 21:54:06','active',NULL,'2018-10-10 21:54:06','admin','2018-10-10 21:54:06','Generate Report','008',NULL,NULL,'Generate Report',4),(9,'admin','2018-10-10 22:37:33','active',NULL,'2018-10-10 22:37:33','admin','2018-10-10 22:37:33','Generate Report','010',NULL,NULL,'Generate Report',2),(10,'admin','2018-10-10 22:37:33','active',NULL,'2018-10-10 22:37:33','admin','2018-10-10 22:37:33','Generate Report','011',NULL,NULL,'Generate Report',3),(11,'admin','2018-10-12 09:16:31','active',NULL,'2018-10-12 09:16:31','admin','2018-10-12 09:16:31','Payment Records','012',NULL,NULL,'Payment Records',1),(12,'admin','2018-10-19 22:45:56','active',NULL,'2018-10-19 22:45:56','admin','2018-10-19 22:45:56','Notification','013',NULL,NULL,'Notification',4),(13,'admin','2018-10-19 23:13:50','active',NULL,'2018-10-19 23:13:50','admin','2018-10-19 23:13:50','User Category','014',NULL,NULL,'User Category',1),(14,'admin','2018-10-19 23:52:38','active',NULL,'2018-10-19 23:52:38','admin','2018-10-19 23:52:38','User Management','015',NULL,NULL,'User Management',1);
/*!40000 ALTER TABLE `menugroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paymentrecords`
--

DROP TABLE IF EXISTS `paymentrecords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paymentrecords` (
  `paymentId` int(11) NOT NULL AUTO_INCREMENT,
  `createdBy` varchar(255) DEFAULT NULL,
  `crtdDtTime` datetime DEFAULT NULL,
  `genericStatus` varchar(255) DEFAULT NULL,
  `optLock` datetime DEFAULT NULL,
  `upDtTime` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `amount` varchar(255) DEFAULT NULL,
  `bankRefernceNo` varchar(255) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `currency` varchar(255) DEFAULT NULL,
  `paymentChanel` varchar(255) DEFAULT NULL,
  `paymentCode` varchar(255) DEFAULT NULL,
  `paymentDate` datetime DEFAULT NULL,
  `paymentExpiretionDate` datetime DEFAULT NULL,
  `paymentStatus` varchar(255) DEFAULT NULL,
  `institution` int(11) DEFAULT NULL,
  `paymentApprovedBy` int(11) DEFAULT NULL,
  PRIMARY KEY (`paymentId`),
  UNIQUE KEY `paymentCode` (`paymentCode`),
  KEY `FKC9751D7CAA7FBEA8` (`institution`),
  KEY `FKC9751D7CAA604F14` (`paymentApprovedBy`),
  CONSTRAINT `FKC9751D7CAA604F14` FOREIGN KEY (`paymentApprovedBy`) REFERENCES `users` (`userId`),
  CONSTRAINT `FKC9751D7CAA7FBEA8` FOREIGN KEY (`institution`) REFERENCES `institution` (`institutionId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paymentrecords`
--

LOCK TABLES `paymentrecords` WRITE;
/*!40000 ALTER TABLE `paymentrecords` DISABLE KEYS */;
INSERT INTO `paymentrecords` VALUES (1,NULL,NULL,'active',NULL,NULL,NULL,'20000','12345BK','Okay','111','111','001',NULL,NULL,'active',NULL,NULL),(2,'admin','2018-10-17 10:37:02','active',NULL,'2018-10-17 10:37:02','admin','30000','12132213','Good','222','111','002',NULL,NULL,'active',1,NULL),(3,'admin','2018-10-18 19:32:25','active',NULL,'2018-10-18 19:32:25','admin','433554','45252','dfasdfas','43535343','453434','232421','2018-10-01 02:00:00','2018-10-18 02:00:00','active',9,NULL);
/*!40000 ALTER TABLE `paymentrecords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `province`
--

DROP TABLE IF EXISTS `province`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `province` (
  `provenceId` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `provenceName_en` varchar(255) DEFAULT NULL,
  `provenceName_fr` varchar(255) DEFAULT NULL,
  `provenceName_rw` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`provenceId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `province`
--

LOCK TABLES `province` WRITE;
/*!40000 ALTER TABLE `province` DISABLE KEYS */;
INSERT INTO `province` VALUES (1,'KGL001','KIGALI CITY','KIGALI CITY','KIGALI CITY'),(2,'STH001 ','SOUTHERN','SOUTHERN','SOUTHERN');
/*!40000 ALTER TABLE `province` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sector`
--

DROP TABLE IF EXISTS `sector`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sector` (
  `sectorId` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `sectorName_en` varchar(255) DEFAULT NULL,
  `sectorName_fr` varchar(255) DEFAULT NULL,
  `sectorName_rw` varchar(255) DEFAULT NULL,
  `distric` int(11) DEFAULT NULL,
  PRIMARY KEY (`sectorId`),
  KEY `FK9360438684A34CFC` (`distric`),
  CONSTRAINT `FK9360438684A34CFC` FOREIGN KEY (`distric`) REFERENCES `district` (`districtId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sector`
--

LOCK TABLES `sector` WRITE;
/*!40000 ALTER TABLE `sector` DISABLE KEYS */;
INSERT INTO `sector` VALUES (1,'KMHSTR02','Kimihurura','Kimihurura','Kimihurura',2),(2,'NYMBSTR02','Nyamirambo','Nyamirambo','Nyamirambo',1);
/*!40000 ALTER TABLE `sector` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `strategicplan`
--

DROP TABLE IF EXISTS `strategicplan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `strategicplan` (
  `planId` int(11) NOT NULL AUTO_INCREMENT,
  `createdBy` varchar(255) DEFAULT NULL,
  `crtdDtTime` datetime DEFAULT NULL,
  `genericStatus` varchar(255) DEFAULT NULL,
  `optLock` datetime DEFAULT NULL,
  `upDtTime` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `details` varchar(255) DEFAULT NULL,
  `recordedDate` datetime DEFAULT NULL,
  `Users` int(11) DEFAULT NULL,
  `dueDate` datetime DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  PRIMARY KEY (`planId`),
  KEY `FKDAE6488937BE5948` (`Users`),
  CONSTRAINT `FKDAE6488937BE5948` FOREIGN KEY (`Users`) REFERENCES `users` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `strategicplan`
--

LOCK TABLES `strategicplan` WRITE;
/*!40000 ALTER TABLE `strategicplan` DISABLE KEYS */;
INSERT INTO `strategicplan` VALUES (1,'Kabera Nepo','2018-10-27 20:44:37','desactive',NULL,'2018-10-27 20:44:37','Kabera Nepo','Commercial  court building construction at Nyamirambo','2018-10-27 20:44:37',4,'2018-11-27 02:00:00','2018-11-27 02:00:00','2018-10-27 02:00:00'),(2,'Kabera Nepo','2018-10-30 21:24:06','desactive',NULL,'2018-10-30 21:24:06','Kabera Nepo','Road construction in Nyamirambo sector for 100 km','2018-10-30 21:24:06',4,'2018-11-30 02:00:00','2018-11-30 02:00:00','2018-10-30 02:00:00'),(3,'Fabrice Ngenzi','2018-11-25 14:36:31','desactive',NULL,'2018-11-25 14:36:31','Fabrice Ngenzi','Road construction for kigali-rulindo street','2018-11-25 14:36:31',6,'2019-11-30 02:00:00','2019-11-30 02:00:00','2018-11-25 02:00:00'),(4,'Fabrice Ngenzi','2018-11-25 14:41:51','desactive',NULL,'2018-11-25 14:41:51','Fabrice Ngenzi','Commercial building in Nyamirambo sector','2018-11-25 14:41:51',6,'2019-11-30 02:00:00','2019-11-30 02:00:00','2018-11-25 02:00:00'),(5,'Kabera Nepo','2019-01-04 14:35:54','active',NULL,'2019-01-04 14:35:54','Kabera Nepo','Developing Company\'s Icumbi Application ','2019-01-04 14:35:54',4,'2019-06-30 02:00:00','2019-06-30 02:00:00','2019-01-04 02:00:00');
/*!40000 ALTER TABLE `strategicplan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `taskId` int(11) NOT NULL AUTO_INCREMENT,
  `createdBy` varchar(255) DEFAULT NULL,
  `crtdDtTime` datetime DEFAULT NULL,
  `genericStatus` varchar(255) DEFAULT NULL,
  `optLock` datetime DEFAULT NULL,
  `upDtTime` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `dueDate` datetime DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `taskName` varchar(255) DEFAULT NULL,
  `parentTask` int(11) DEFAULT NULL,
  `strategicPlan` int(11) DEFAULT NULL,
  `board` int(11) DEFAULT NULL,
  `taskStatus` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`taskId`),
  KEY `FK27A9A5D2054A7C` (`parentTask`),
  KEY `FK27A9A53D79426A` (`strategicPlan`),
  KEY `FK27A9A535A31B04` (`board`),
  CONSTRAINT `FK27A9A535A31B04` FOREIGN KEY (`board`) REFERENCES `board` (`boardId`),
  CONSTRAINT `FK27A9A53D79426A` FOREIGN KEY (`strategicPlan`) REFERENCES `strategicplan` (`planId`),
  CONSTRAINT `FK27A9A5D2054A7C` FOREIGN KEY (`parentTask`) REFERENCES `task` (`taskId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (1,'Kabera Nepo','2018-10-30 22:40:50','active',NULL,'2018-10-30 22:40:50','Kabera Nepo','Road preparation  and clearing','2018-10-31 02:00:00','2018-10-31 02:00:00','2018-10-30 02:00:00','C',NULL,2,NULL,NULL),(2,'Fabrice Ngenzi','2018-11-25 14:59:08','active',NULL,'2018-11-25 14:59:08','Fabrice Ngenzi','vfp project user interface','2018-12-26 02:00:00','2018-12-26 02:00:00','2018-11-25 02:00:00','Creating UserFront',NULL,4,NULL,NULL),(3,'Kabera Nepo','2018-12-19 14:51:02','active',NULL,'2018-12-19 14:51:02','Kabera Nepo','House building','2018-12-31 02:00:00','2018-12-31 02:00:00','2018-12-19 02:00:00','Building a house for five room',NULL,4,NULL,NULL),(4,'Ntwali jean','2019-01-04 15:06:27','active',NULL,'2019-01-04 15:06:27','Ntwali jean','Designing all  user interfaces forms','2019-02-28 02:00:00','2019-02-28 02:00:00','2019-01-04 02:00:00','Users front end interfaces',NULL,5,NULL,NULL);
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taskassignment`
--

DROP TABLE IF EXISTS `taskassignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `taskassignment` (
  `taskAssignmentId` int(11) NOT NULL AUTO_INCREMENT,
  `createdBy` varchar(255) DEFAULT NULL,
  `crtdDtTime` datetime DEFAULT NULL,
  `genericStatus` varchar(255) DEFAULT NULL,
  `optLock` datetime DEFAULT NULL,
  `upDtTime` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `task` int(11) DEFAULT NULL,
  `user` int(11) DEFAULT NULL,
  PRIMARY KEY (`taskAssignmentId`),
  UNIQUE KEY `user` (`user`,`task`),
  KEY `FKE0CD0252E0C2A6D2` (`task`),
  KEY `FKE0CD0252314EB70B` (`user`),
  CONSTRAINT `FKE0CD0252314EB70B` FOREIGN KEY (`user`) REFERENCES `users` (`userId`),
  CONSTRAINT `FKE0CD0252E0C2A6D2` FOREIGN KEY (`task`) REFERENCES `task` (`taskId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taskassignment`
--

LOCK TABLES `taskassignment` WRITE;
/*!40000 ALTER TABLE `taskassignment` DISABLE KEYS */;
INSERT INTO `taskassignment` VALUES (1,'Fabrice Ngenzi','2018-11-25 15:00:11','active',NULL,'2018-11-25 15:00:11','Fabrice Ngenzi',2,2),(2,'Kabera Nepo','2018-12-17 17:38:00','active',NULL,'2018-12-17 17:38:00','Kabera Nepo',1,3),(3,'Kabera Nepo','2018-12-19 14:54:52','active',NULL,'2018-12-19 14:54:52','Kabera Nepo',3,5),(4,'Ntwali jean','2019-01-04 15:34:52','active',NULL,'2019-01-04 15:34:52','Ntwali jean',4,24);
/*!40000 ALTER TABLE `taskassignment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taskcomment`
--

DROP TABLE IF EXISTS `taskcomment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `taskcomment` (
  `commentTaskId` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdBy` varchar(255) DEFAULT NULL,
  `crtdDtTime` datetime DEFAULT NULL,
  `genericStatus` varchar(255) DEFAULT NULL,
  `optLock` datetime DEFAULT NULL,
  `upDtTime` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `documents` int(11) DEFAULT NULL,
  `task` int(11) DEFAULT NULL,
  `comment` int(11) DEFAULT NULL,
  PRIMARY KEY (`commentTaskId`),
  KEY `FKC98A8FAE0C2A6D2` (`task`),
  KEY `FKC98A8FAC3FA104F` (`documents`),
  KEY `FKC98A8FAC462ABF6` (`comment`),
  CONSTRAINT `FKC98A8FAC3FA104F` FOREIGN KEY (`documents`) REFERENCES `comment` (`commentId`),
  CONSTRAINT `FKC98A8FAC462ABF6` FOREIGN KEY (`comment`) REFERENCES `comment` (`commentId`),
  CONSTRAINT `FKC98A8FAE0C2A6D2` FOREIGN KEY (`task`) REFERENCES `task` (`taskId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taskcomment`
--

LOCK TABLES `taskcomment` WRITE;
/*!40000 ALTER TABLE `taskcomment` DISABLE KEYS */;
/*!40000 ALTER TABLE `taskcomment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uploadingactivity`
--

DROP TABLE IF EXISTS `uploadingactivity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uploadingactivity` (
  `upLoadActId` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdBy` varchar(255) DEFAULT NULL,
  `crtdDtTime` datetime DEFAULT NULL,
  `genericStatus` varchar(255) DEFAULT NULL,
  `optLock` datetime DEFAULT NULL,
  `upDtTime` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `activity` int(11) DEFAULT NULL,
  `documents` int(11) DEFAULT NULL,
  PRIMARY KEY (`upLoadActId`),
  KEY `FK30804504A5AFDE6` (`activity`),
  KEY `FK3080450591A86A8` (`documents`),
  CONSTRAINT `FK30804504A5AFDE6` FOREIGN KEY (`activity`) REFERENCES `activity` (`ACTIVITY_ID`),
  CONSTRAINT `FK3080450591A86A8` FOREIGN KEY (`documents`) REFERENCES `documents` (`DocId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uploadingactivity`
--

LOCK TABLES `uploadingactivity` WRITE;
/*!40000 ALTER TABLE `uploadingactivity` DISABLE KEYS */;
INSERT INTO `uploadingactivity` VALUES (1,'ange','2018-12-17 12:11:49','active',NULL,NULL,NULL,1,86),(2,'ange','2018-12-17 14:20:21','active',NULL,NULL,NULL,1,87),(3,'ange','2018-12-17 15:10:30','active',NULL,NULL,NULL,1,88),(7,'rep','2018-12-19 09:08:39','active',NULL,NULL,NULL,5,95),(8,'rep','2018-12-19 13:03:42','active',NULL,NULL,NULL,7,96),(9,'alain','2018-12-19 15:22:10','active',NULL,NULL,NULL,9,97),(10,'Aline','2019-01-04 15:39:28','active',NULL,NULL,NULL,14,106);
/*!40000 ALTER TABLE `uploadingactivity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uploadingfiles`
--

DROP TABLE IF EXISTS `uploadingfiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uploadingfiles` (
  `upLoadId` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdBy` varchar(255) DEFAULT NULL,
  `crtdDtTime` datetime DEFAULT NULL,
  `genericStatus` varchar(255) DEFAULT NULL,
  `optLock` datetime DEFAULT NULL,
  `upDtTime` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `documents` int(11) DEFAULT NULL,
  `user` int(11) DEFAULT NULL,
  PRIMARY KEY (`upLoadId`),
  KEY `FKAAB918D6591A86A8` (`documents`),
  KEY `FKAAB918D6314EB70B` (`user`),
  CONSTRAINT `FKAAB918D6314EB70B` FOREIGN KEY (`user`) REFERENCES `users` (`userId`),
  CONSTRAINT `FKAAB918D6591A86A8` FOREIGN KEY (`documents`) REFERENCES `documents` (`DocId`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uploadingfiles`
--

LOCK TABLES `uploadingfiles` WRITE;
/*!40000 ALTER TABLE `uploadingfiles` DISABLE KEYS */;
INSERT INTO `uploadingfiles` VALUES (21,NULL,'2018-11-05 10:43:39','active',NULL,NULL,NULL,29,3),(22,NULL,'2018-11-05 10:45:56','active',NULL,NULL,NULL,30,3),(23,NULL,'2018-11-05 11:39:50','active',NULL,NULL,NULL,31,3),(24,NULL,'2018-11-05 11:53:47','active',NULL,NULL,NULL,32,3),(27,NULL,'2018-11-05 11:58:43','active',NULL,NULL,NULL,35,3),(28,NULL,'2018-11-06 16:11:17','active',NULL,NULL,NULL,36,3),(29,NULL,'2018-11-06 16:39:06','active',NULL,NULL,NULL,37,3),(30,NULL,'2018-11-08 19:21:17','active',NULL,NULL,NULL,38,4),(33,NULL,'2018-11-12 00:02:48','active',NULL,NULL,NULL,42,3),(34,NULL,'2018-11-12 00:04:47','active',NULL,NULL,NULL,43,3),(35,NULL,'2018-11-12 08:46:40','active',NULL,NULL,NULL,44,3),(36,NULL,'2018-11-12 08:49:59','active',NULL,NULL,NULL,45,3),(37,NULL,'2018-11-12 08:54:28','active',NULL,NULL,NULL,46,3),(38,NULL,'2018-11-12 08:57:45','active',NULL,NULL,NULL,47,3),(39,NULL,'2018-11-12 09:00:52','active',NULL,NULL,NULL,48,3),(40,NULL,'2018-11-12 09:09:22','active',NULL,NULL,NULL,49,3),(41,NULL,'2018-11-12 09:11:20','active',NULL,NULL,NULL,50,3),(42,NULL,'2018-11-12 09:12:17','active',NULL,NULL,NULL,51,3),(43,NULL,'2018-11-12 09:24:08','active',NULL,NULL,NULL,52,3),(44,NULL,'2018-11-12 09:25:32','active',NULL,NULL,NULL,53,3),(45,NULL,'2018-11-12 09:26:54','active',NULL,NULL,NULL,54,3),(48,NULL,'2018-11-12 09:45:58','active',NULL,NULL,NULL,57,18),(49,NULL,'2018-11-12 09:47:07','active',NULL,NULL,NULL,58,18),(50,NULL,'2018-11-12 09:54:00','active',NULL,NULL,NULL,59,18),(51,NULL,'2018-11-12 09:59:48','active',NULL,NULL,NULL,60,18),(52,NULL,'2018-11-12 10:01:04','active',NULL,NULL,NULL,61,18),(53,NULL,'2018-11-12 10:01:29','active',NULL,NULL,NULL,62,18),(54,NULL,'2018-11-12 10:04:11','active',NULL,NULL,NULL,63,18),(55,NULL,'2018-11-12 10:04:57','active',NULL,NULL,NULL,64,18),(56,NULL,'2018-11-12 10:06:19','active',NULL,NULL,NULL,65,18),(57,NULL,'2018-11-12 10:07:19','active',NULL,NULL,NULL,66,18),(58,NULL,'2018-11-12 10:08:51','active',NULL,NULL,NULL,67,18),(59,NULL,'2018-11-12 10:10:29','active',NULL,NULL,NULL,68,18),(60,NULL,'2018-11-12 10:11:31','active',NULL,NULL,NULL,69,18),(61,NULL,'2018-11-12 10:15:14','active',NULL,NULL,NULL,70,18),(68,NULL,'2018-11-25 14:39:14','active',NULL,NULL,NULL,77,6),(75,NULL,'2018-12-13 12:34:17','active',NULL,NULL,NULL,83,2),(76,NULL,'2018-12-16 14:23:46','active',NULL,NULL,NULL,84,2),(77,NULL,'2018-12-17 17:46:26','active',NULL,NULL,NULL,89,4),(79,NULL,'2018-12-18 11:19:20','active',NULL,NULL,NULL,93,3),(82,NULL,'2019-01-02 11:06:13','active',NULL,NULL,NULL,100,2),(84,NULL,'2019-01-02 12:19:54','active',NULL,NULL,NULL,102,17),(85,NULL,'2019-01-02 12:26:51','active',NULL,NULL,NULL,103,1);
/*!40000 ALTER TABLE `uploadingfiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uploadingstrategicplan`
--

DROP TABLE IF EXISTS `uploadingstrategicplan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uploadingstrategicplan` (
  `upLoadPlanId` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdBy` varchar(255) DEFAULT NULL,
  `crtdDtTime` datetime DEFAULT NULL,
  `genericStatus` varchar(255) DEFAULT NULL,
  `optLock` datetime DEFAULT NULL,
  `upDtTime` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `documents` int(11) DEFAULT NULL,
  `strategicPlan` int(11) DEFAULT NULL,
  PRIMARY KEY (`upLoadPlanId`),
  KEY `FKEC58A808591A86A8` (`documents`),
  KEY `FKEC58A8083D79426A` (`strategicPlan`),
  CONSTRAINT `FKEC58A8083D79426A` FOREIGN KEY (`strategicPlan`) REFERENCES `strategicplan` (`planId`),
  CONSTRAINT `FKEC58A808591A86A8` FOREIGN KEY (`documents`) REFERENCES `documents` (`DocId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uploadingstrategicplan`
--

LOCK TABLES `uploadingstrategicplan` WRITE;
/*!40000 ALTER TABLE `uploadingstrategicplan` DISABLE KEYS */;
INSERT INTO `uploadingstrategicplan` VALUES (1,'super','2018-11-08 19:33:08','active',NULL,NULL,NULL,39,2),(2,'ngenzi','2018-11-25 14:43:47','active',NULL,NULL,NULL,78,4),(3,'super','2019-01-04 14:38:05','active',NULL,NULL,NULL,105,5);
/*!40000 ALTER TABLE `uploadingstrategicplan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uploadingtask`
--

DROP TABLE IF EXISTS `uploadingtask`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uploadingtask` (
  `upLoadTaskId` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdBy` varchar(255) DEFAULT NULL,
  `crtdDtTime` datetime DEFAULT NULL,
  `genericStatus` varchar(255) DEFAULT NULL,
  `optLock` datetime DEFAULT NULL,
  `upDtTime` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `Task` int(11) DEFAULT NULL,
  `documents` int(11) DEFAULT NULL,
  PRIMARY KEY (`upLoadTaskId`),
  KEY `FK81670F46E0C2A6D2` (`Task`),
  KEY `FK81670F46591A86A8` (`documents`),
  CONSTRAINT `FK81670F46591A86A8` FOREIGN KEY (`documents`) REFERENCES `documents` (`DocId`),
  CONSTRAINT `FK81670F46E0C2A6D2` FOREIGN KEY (`Task`) REFERENCES `task` (`taskId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uploadingtask`
--

LOCK TABLES `uploadingtask` WRITE;
/*!40000 ALTER TABLE `uploadingtask` DISABLE KEYS */;
/*!40000 ALTER TABLE `uploadingtask` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usercategory`
--

DROP TABLE IF EXISTS `usercategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usercategory` (
  `userCatid` int(11) NOT NULL AUTO_INCREMENT,
  `usercategoryName` varchar(255) DEFAULT NULL,
  `createdBy` varchar(255) DEFAULT NULL,
  `crtdDtTime` datetime DEFAULT NULL,
  `genericStatus` varchar(255) DEFAULT NULL,
  `optLock` datetime DEFAULT NULL,
  `upDtTime` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userCatid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usercategory`
--

LOCK TABLES `usercategory` WRITE;
/*!40000 ALTER TABLE `usercategory` DISABLE KEYS */;
INSERT INTO `usercategory` VALUES (1,'Super Admin','rep','2018-11-03 20:33:12','active','2018-11-03 20:33:33','2018-11-03 20:33:58','rep','active'),(2,'Staff','rep','2018-11-03 20:33:12','active','2018-11-03 20:33:33','2018-11-10 22:14:10','admin','active'),(3,'Institution Representative','rep','2018-11-03 20:33:12','active','2018-11-03 20:33:33','2018-11-11 12:59:30','admin','active'),(4,'Superviser','rep','2018-11-03 20:33:12','active','2018-11-03 20:33:33','2018-11-09 10:36:04','admin','active'),(5,'Human Ressource','rep','2018-11-03 20:33:12','active','2018-11-03 20:33:33','2018-11-11 10:36:30','admin','active'),(6,'Social Affair','admin','2018-11-11 12:09:54','active',NULL,'2018-11-11 12:59:30','admin','active'),(7,'Hr','admin','2018-11-11 13:15:16','active',NULL,NULL,'admin','active'),(8,'MD','admin','2018-11-27 11:35:45','active',NULL,'2018-11-27 11:36:06','admin','active');
/*!40000 ALTER TABLE `usercategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `createdBy` varchar(255) DEFAULT NULL,
  `crtdDtTime` datetime DEFAULT NULL,
  `genericStatus` varchar(255) DEFAULT NULL,
  `optLock` datetime DEFAULT NULL,
  `upDtTime` datetime DEFAULT NULL,
  `updatedBy` varchar(255) DEFAULT NULL,
  `DateOfBirth` datetime DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `fname` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `lname` varchar(255) DEFAULT NULL,
  `loginStatus` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `viewId` varchar(255) DEFAULT NULL,
  `viewName` varchar(255) DEFAULT NULL,
  `board` int(11) DEFAULT NULL,
  `userCategory` int(11) DEFAULT NULL,
  `village` int(11) DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `viewId` (`viewId`),
  KEY `FK4E39DE86C01B37A` (`userCategory`),
  KEY `FK4E39DE889F81890` (`village`),
  KEY `FK4E39DE835A31B04` (`board`),
  CONSTRAINT `FK4E39DE835A31B04` FOREIGN KEY (`board`) REFERENCES `board` (`boardId`),
  CONSTRAINT `FK4E39DE86C01B37A` FOREIGN KEY (`userCategory`) REFERENCES `usercategory` (`userCatid`),
  CONSTRAINT `FK4E39DE889F81890` FOREIGN KEY (`village`) REFERENCES `village` (`villageId`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,NULL,'2018-10-10 21:31:13','active',NULL,'2019-01-04 11:05:23','Tres','1993-05-15 00:00:00','Kigali','2018-09-02 00:00:00','Hitimana','Male','8fbeb555-e38f-43a6-9b7c-2addd984b6dc.logos1.png','Emile','offline','active','admin','21232f297a57a5a743894a0e4a801fc3',4,1,NULL),(2,NULL,'2018-10-10 21:31:13','active',NULL,NULL,NULL,'1992-10-24 00:00:00','Kigali','2018-09-16 00:00:00','Ange','Female','b2a15d29-8a5b-4f32-83a6-4fa45c1fcc91.AmagajuLogo.jpg','Mutesi','offline','active','ange','1253208465b1efa876f982d8a9e73eef',3,2,NULL),(3,NULL,NULL,'active',NULL,'2019-01-02 21:28:18','Tres','1992-12-24 00:00:00','Kigali','2018-09-21 00:00:00','Emma','Male','d0399c5f-c452-45f7-b2ec-144652740706.MarinesLogo.png','Sibo','online','desactive','rep','21232f297a57a5a743894a0e4a801fc3',1,2,NULL),(4,NULL,'2018-10-10 21:31:13','active',NULL,'2019-01-02 22:57:59','Tres','1993-05-15 00:00:00','Kigali','2018-09-29 00:00:00','Kabera','Male','de1a9403-82e2-415b-9f1d-8e82eb624340.empty.png','Nepo','offline','active','super','21232f297a57a5a743894a0e4a801fc3',4,4,NULL),(5,NULL,'2018-10-17 14:32:32','active',NULL,'2019-01-04 10:57:11','Tres','1993-05-15 00:00:00','Kigali','2018-10-01 00:00:00','Alain','Male','us.png','Muhire','online','active','alain','21232f297a57a5a743894a0e4a801fc3',1,6,NULL),(6,NULL,'2018-10-17 14:32:32','active',NULL,'2018-11-13 17:50:20','Tres','1993-05-15 00:00:00','Kigali','2018-10-05 00:00:00','Fabrice','Male','d0d805cc-7be1-4a19-ba83-f8df38cd0cf2.us.png','Ngenzi','online','active','ngenzi','21232f297a57a5a743894a0e4a801fc3',2,4,NULL),(7,NULL,'2018-10-17 14:32:32','active',NULL,'2018-11-13 18:00:51','Tres','1993-05-15 00:00:00','Kigali','2018-10-10 00:00:00','Alice','Male','us.png','Gihozo','offline','desactive','alice','21232f297a57a5a743894a0e4a801fc3',2,4,NULL),(8,'admin','2018-11-04 15:19:20','active',NULL,'2018-11-13 17:14:03','Tres','1992-10-28 02:00:00','Kigali','2018-11-04 15:19:20','Nshuti','Male','us.png','Patric','online','active','staff','1253208465b1efa876f982d8a9e73eef',1,2,NULL),(9,'rep','2018-11-05 14:20:58','active',NULL,NULL,'rep','1994-10-31 02:00:00','Muhima','2018-11-05 14:20:58','Muhire','Male','us.png','Egide','offline','desactive','Egide','c50ffba3a09d23796872330243603a04',3,5,NULL),(10,'rep','2018-11-05 14:57:38','active',NULL,'2018-11-13 12:53:08','Tres','1994-10-25 02:00:00','Kabuga','2018-11-05 14:57:38','Muteteri','Female','us.png','Jeane','offline','active','Jeane','df19608192d398b5a2d1bc03d56958a6',2,2,NULL),(11,'rep','2018-11-05 16:19:10','active',NULL,'2018-11-13 17:40:09','Tres','1997-11-25 02:00:00','Gicumbi','2018-11-05 16:19:10','Cyiza','Female','us.png','Anne','offline','active','Cyiza','64362938eadbd14d52be96c80f2f7eb5',3,2,NULL),(12,'admin','2018-11-06 10:15:06','active',NULL,'2019-01-04 10:29:19','Tres','1993-10-21 02:00:00','Nyarugenge','2018-11-06 10:15:06','Karangwa','Male','us.png','Emile','offline','active','Emile','810d68f9cf23facc72f3ac4402b3d5a7',1,8,2),(13,'rep','2018-11-06 15:29:41','active',NULL,NULL,'rep','1998-10-21 02:00:00','Kabale','2018-11-06 15:29:41','Gasana','Male','us.png','Eric','offline','desactive','Eric','ccf9b7d86475bdf0c1d5003e109ee217',3,5,NULL),(14,'rep','2018-11-07 12:35:53','active',NULL,NULL,'rep','1989-10-23 02:00:00','Kabare','2018-11-07 12:35:53','Uwineza','Female','us.png','Solange','offline','desactive','Soso','88918a7a9f84c0fec891760d1a60496f',3,5,NULL),(15,'rep','2018-11-07 12:48:27','active',NULL,'2019-01-04 10:29:19','Tres','1997-10-08 02:00:00','Gasabo','2018-11-07 12:48:27','Musoni','Male','us.png','Smith','offline','desactive','Smith','15e584de4f69cecbe79380932d4cc324',3,5,2),(16,'rep','2018-11-08 20:56:43','active',NULL,NULL,'rep','1990-10-16 02:00:00','Remera','2018-11-06 00:00:00','Rugimbana','Male','us.png','Theogene','offline','desactive','Theo ','8e77ad2625d905953bceb5b7656e30d1',3,2,NULL),(17,'admin','2018-11-11 15:05:15','active',NULL,'2018-11-11 15:09:28','admin','1994-11-23 02:00:00','Kanombe','2018-11-11 15:05:15','Ngango','Male','ff6f2422-8711-4ce7-80fe-1f047ff5944b.player2.png','Junior','online','active','Tres','cd92a53a212d054eaf49f188a57dae7a',1,3,1),(18,'Tres','2018-11-12 09:41:08','active',NULL,'2018-11-13 12:53:08','Tres','1999-11-30 02:00:00','Gasabo','2018-11-12 09:41:08','Nshuti','Male','343e7679-680e-48fb-a612-2f4133b2d662.us.png','Patric','online','desactive','Emmzzo','cd92a53a212d054eaf49f188a57dae7a',2,4,NULL),(19,'admin','2018-11-25 13:38:43','active',NULL,'2018-11-25 13:43:38','admin','1993-11-30 02:00:00','Kigoma','2018-11-25 13:38:43','Leonard','Male','us.png','Mbonimpa','online','active','Leonard','04e88d67c1bfbaa4c589b316ccb5d43a',4,3,NULL),(20,'admin','2018-12-31 11:37:03','active',NULL,NULL,'admin','1998-12-30 02:00:00','Kigali','2018-12-31 11:37:03','Belly','Male','us.png','Muvunyi','offline','desactive','Belly','8b5073fb50816c304ce9f9a7f46f80c5',2,3,NULL),(21,'Tres','2018-12-31 11:43:23','active',NULL,NULL,'Tres','1994-04-19 02:00:00','Kimironko','2018-12-31 11:43:23','Umutoni','Female','us.png','Aurore','offline','desactive','Aurore','8b5073fb50816c304ce9f9a7f46f80c5',3,2,NULL),(22,'admin','2019-01-04 13:22:21','active',NULL,'2019-01-04 13:57:53','admin','1984-01-24 02:00:00','Kimironko','2019-01-04 13:22:21','Munezero','Male','us.png','Patric','offline','active','sda','acebc17ed9da92437712c253fe7bfb69',NULL,3,NULL),(23,'sda','2019-01-04 14:23:42','active',NULL,'2019-01-04 14:48:41','sda','1985-01-30 02:00:00','Kabare street','2019-01-03 00:00:00','Ntwali','Male','us.png','jean','offline','active','Ntwali','de608f10407de85ec90b54b9ca120508',6,4,NULL),(24,'sda','2019-01-04 14:23:42','active',NULL,'2019-01-04 14:48:41','sda','1999-01-13 02:00:00','Kabare Street','2019-01-03 00:00:00','Uwera','Female','us.png','Aline','online','active','Aline','0fd2b42c2b7836da3fbeb1a113fbc712',6,2,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `village`
--

DROP TABLE IF EXISTS `village`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `village` (
  `villageId` int(11) NOT NULL AUTO_INCREMENT,
  `Code` varchar(255) DEFAULT NULL,
  `villageName_en` varchar(255) DEFAULT NULL,
  `villageName_fr` varchar(255) DEFAULT NULL,
  `villageName_rw` varchar(255) DEFAULT NULL,
  `cell` int(11) DEFAULT NULL,
  PRIMARY KEY (`villageId`),
  KEY `FK7EA93C8CE0B34E8C` (`cell`),
  CONSTRAINT `FK7EA93C8CE0B34E8C` FOREIGN KEY (`cell`) REFERENCES `cell` (`cellId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `village`
--

LOCK TABLES `village` WRITE;
/*!40000 ALTER TABLE `village` DISABLE KEYS */;
INSERT INTO `village` VALUES (1,'UMWZSTR02','Umwezi','Umwezi','Umwezi',1),(2,'UBWZSTR02','Ubwiza','Ubwiza','Ubwiza',2);
/*!40000 ALTER TABLE `village` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-04 15:54:41
