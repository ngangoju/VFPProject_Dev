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
  PRIMARY KEY (`ACTIVITY_ID`),
  KEY `FKA126572FE0C2A6D2` (`task`),
  CONSTRAINT `FKA126572FE0C2A6D2` FOREIGN KEY (`task`) REFERENCES `task` (`taskId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
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
  PRIMARY KEY (`boardId`),
  UNIQUE KEY `boardName` (`boardName`),
  KEY `FK3D5FEC6AA7FBEA8` (`institution`),
  KEY `FK3D5FEC635A31B04` (`board`),
  CONSTRAINT `FK3D5FEC635A31B04` FOREIGN KEY (`board`) REFERENCES `board` (`boardId`),
  CONSTRAINT `FK3D5FEC6AA7FBEA8` FOREIGN KEY (`institution`) REFERENCES `institution` (`institutionId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
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
  PRIMARY KEY (`contactId`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `phone` (`phone`),
  KEY `FK9BEFBC00314EB70B` (`user`),
  CONSTRAINT `FK9BEFBC00314EB70B` FOREIGN KEY (`user`) REFERENCES `users` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
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
INSERT INTO `country` VALUES (1,'AFR01','RWANDA','RWANDA','RWANDA'),(2,'AFR02','UGANDA','UGANDA','UGANDA'),(3,'AFR03','TANZANIA','TANZANIE','TANZANIYA');
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
  PRIMARY KEY (`institutionId`),
  KEY `FK98934D98C5457E64` (`country`),
  KEY `FK98934D98AA7FBEA8` (`institution`),
  KEY `FK98934D985DA6EDA4` (`institutionRepresenative_userId`),
  KEY `FK98934D9889F81890` (`village`),
  CONSTRAINT `FK98934D985DA6EDA4` FOREIGN KEY (`institutionRepresenative_userId`) REFERENCES `users` (`userId`),
  CONSTRAINT `FK98934D9889F81890` FOREIGN KEY (`village`) REFERENCES `village` (`villageId`),
  CONSTRAINT `FK98934D98AA7FBEA8` FOREIGN KEY (`institution`) REFERENCES `institution` (`institutionId`),
  CONSTRAINT `FK98934D98C5457E64` FOREIGN KEY (`country`) REFERENCES `country` (`countryId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `institution`
--

LOCK TABLES `institution` WRITE;
/*!40000 ALTER TABLE `institution` DISABLE KEYS */;
INSERT INTO `institution` VALUES (1,'admin',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Tres',NULL,NULL,NULL,NULL,NULL,NULL),(2,'rep','2018-10-17 14:34:29','active',NULL,'2018-10-17 14:34:29','rep',NULL,NULL,NULL,'2018-10-17 14:34:29',NULL,1,NULL,3,NULL),(3,'rep','2018-10-17 14:34:29','active',NULL,'2018-10-17 14:34:29','rep',NULL,NULL,NULL,'2018-10-17 14:34:29',NULL,1,NULL,3,NULL),(4,'rep','2018-10-17 14:34:29','active',NULL,'2018-10-17 14:34:29','rep','Kigali',NULL,'BK','2018-10-17 14:34:29','Private',1,NULL,3,1),(5,'rep','2018-10-17 14:34:29','active',NULL,'2018-10-17 14:34:29','rep',NULL,NULL,NULL,'2018-10-17 14:34:29',NULL,2,NULL,3,1),(6,'rep','2018-10-17 14:34:29','active',NULL,'2018-10-17 14:34:29','rep','Kampali',NULL,'Pepsi','2018-10-17 14:34:29','Private',2,NULL,3,1),(7,'rep','2018-10-17 14:34:29','active',NULL,'2018-10-17 14:34:29','rep',NULL,NULL,NULL,'2018-10-17 14:34:29',NULL,1,NULL,3,1),(8,'rep','2018-10-17 14:34:29','active',NULL,'2018-10-17 14:34:29','rep',NULL,NULL,NULL,'2018-10-17 14:34:29',NULL,1,NULL,3,1),(9,'rep','2018-10-17 14:34:29','active',NULL,'2018-10-17 14:34:29','rep','Kigali',NULL,'Innovis Telocom','2018-10-17 14:34:29','Private',1,NULL,3,1);
/*!40000 ALTER TABLE `institution` ENABLE KEYS */;
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
  PRIMARY KEY (`instRegReqstId`),
  KEY `FK2EDAB2DEAA7FBEA8` (`institution`),
  CONSTRAINT `FK2EDAB2DEAA7FBEA8` FOREIGN KEY (`institution`) REFERENCES `institution` (`institutionId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `institutionregistrationrequest`
--

LOCK TABLES `institutionregistrationrequest` WRITE;
/*!40000 ALTER TABLE `institutionregistrationrequest` DISABLE KEYS */;
INSERT INTO `institutionregistrationrequest` VALUES (1,'rep','2018-10-17 14:34:29','active',NULL,'2018-10-17 14:34:29','rep','2018-10-17 14:34:29','pending',NULL,2),(2,'rep','2018-10-17 14:34:29','active',NULL,'2018-10-17 14:34:29','rep','2018-10-17 14:34:29','pending',NULL,3),(3,'rep','2018-10-17 14:34:29','active',NULL,'2018-10-17 14:34:29','rep','2018-10-17 14:34:29','pending',NULL,4),(4,'rep','2018-10-17 14:34:29','active',NULL,'2018-10-17 14:34:29','rep','2018-10-17 14:34:29','pending',NULL,5),(5,'rep','2018-10-17 14:34:29','active',NULL,'2018-10-17 14:34:29','rep','2018-10-17 14:34:29','pending',NULL,6),(6,'rep','2018-10-17 14:34:29','active',NULL,'2018-10-17 14:34:29','rep','2018-10-17 14:34:29','pending',NULL,7),(7,'rep','2018-10-17 14:34:29','active',NULL,'2018-10-17 14:34:29','rep','2018-10-17 14:34:29','pending',NULL,8),(8,'rep','2018-10-17 14:34:29','active',NULL,'2018-10-17 14:34:29','rep','2018-10-17 14:34:29','pending',NULL,9);
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
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;
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
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loginhistoric`
--

LOCK TABLES `loginhistoric` WRITE;
/*!40000 ALTER TABLE `loginhistoric` DISABLE KEYS */;
INSERT INTO `loginhistoric` VALUES (1,'Mugabo Jean',NULL,NULL,NULL,'2018-10-10 21:35:50',NULL,'Emile-PC/127.0.0.1',NULL,'2018-10-10 21:36:06',1),(2,'Mugabo Jean',NULL,NULL,NULL,'2018-10-10 21:53:11',NULL,'Emile-PC/127.0.0.1',NULL,'2018-10-10 21:53:21',1),(3,'Mugabo Jean',NULL,NULL,NULL,'2018-10-10 22:13:27',NULL,'Emile-PC/127.0.0.1',NULL,'2018-10-10 22:13:37',1),(4,'Mugabo Jean',NULL,NULL,NULL,'2018-10-11 20:18:50',NULL,'Emile-PC/127.0.0.1',NULL,'2018-10-11 20:19:36',1),(5,'Mugabo Jean',NULL,NULL,NULL,'2018-10-12 08:37:05',NULL,'Emile-PC/172.27.242.184',NULL,'2018-10-12 08:41:29',1),(6,'Mugabo Jean',NULL,NULL,NULL,'2018-10-12 09:24:55',NULL,'Emile-PC/172.27.242.184',NULL,'2018-10-12 09:25:29',1),(7,'Mugabo Jean',NULL,NULL,NULL,'2018-10-12 09:27:48',NULL,'Emile-PC/172.27.242.184',NULL,'2018-10-12 09:27:56',1),(8,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-12 09:29:10',NULL,'Emile-PC/172.27.242.184',NULL,'2018-10-12 09:29:20',3),(9,'Mugabo Jean',NULL,NULL,NULL,'2018-10-13 13:26:34',NULL,'Emile-PC/172.27.242.184',NULL,'2018-10-13 13:32:38',1),(10,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-13 13:33:06',NULL,'Emile-PC/172.27.242.184',NULL,'2018-10-13 13:33:18',3),(11,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-13 13:40:18',NULL,'Emile-PC/172.27.242.184',NULL,'2018-10-13 13:40:27',3),(12,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-13 13:41:11',NULL,'Emile-PC/172.27.242.184',NULL,'2018-10-13 13:41:30',3),(13,'Ange Mutesi',NULL,NULL,NULL,'2018-10-13 13:52:29',NULL,'Emile-PC/172.27.242.184',NULL,'2018-10-13 13:53:18',2),(14,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-13 13:53:44',NULL,'Emile-PC/172.27.242.184',NULL,'2018-10-13 13:59:30',3),(15,'Kabera Nepo',NULL,NULL,NULL,'2018-10-13 14:00:40',NULL,'Emile-PC/172.27.242.184',NULL,'2018-10-13 14:00:56',4),(16,'Mugabo Jean',NULL,NULL,NULL,'2018-10-16 13:15:37',NULL,'Emile-PC/192.168.1.102',NULL,'2018-10-16 13:16:08',1),(17,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-16 13:19:53',NULL,'Emile-PC/192.168.1.102',NULL,'2018-10-16 13:20:07',3),(18,'Mugabo Jean',NULL,NULL,NULL,'2018-10-16 13:29:15',NULL,'Emile-PC/192.168.1.102',NULL,'2018-10-16 13:29:25',1),(19,'Mugabo Jean',NULL,NULL,NULL,'2018-10-16 13:44:20',NULL,'Emile-PC/127.0.0.1',NULL,'2018-10-16 13:44:29',1),(20,'Mugabo Jean',NULL,NULL,NULL,'2018-10-16 14:23:51',NULL,'Emile-PC/192.168.1.102',NULL,'2018-10-16 14:28:12',1),(21,'Mugabo Jean',NULL,NULL,NULL,'2018-10-17 09:08:52',NULL,'Emile-PC/127.0.0.1',NULL,'2018-10-17 09:09:21',1),(22,'Mugabo Jean',NULL,NULL,NULL,'2018-10-17 09:08:52',NULL,'Emile-PC/127.0.0.1',NULL,'2018-10-17 09:10:12',1),(23,'Mugabo Jean',NULL,NULL,NULL,'2018-10-17 09:32:50',NULL,'Emile-PC/127.0.0.1',NULL,'2018-10-17 09:33:06',1),(24,'Mugabo Jean',NULL,NULL,NULL,'2018-10-17 09:44:07',NULL,'Emile-PC/127.0.0.1',NULL,'2018-10-17 09:44:22',1),(25,'Mugabo Jean',NULL,NULL,NULL,'2018-10-17 10:18:56',NULL,'Emile-PC/127.0.0.1',NULL,'2018-10-17 10:19:10',1),(26,'Mugabo Jean',NULL,NULL,NULL,'2018-10-17 10:27:28',NULL,'Emile-PC/127.0.0.1',NULL,'2018-10-17 10:27:50',1),(27,'Mugabo Jean',NULL,NULL,NULL,'2018-10-17 10:32:12',NULL,'Emile-PC/127.0.0.1',NULL,'2018-10-17 10:32:22',1),(28,'Mugabo Jean',NULL,NULL,NULL,'2018-10-17 10:34:49',NULL,'Emile-PC/127.0.0.1',NULL,'2018-10-17 10:34:58',1),(29,'Mugabo Jean',NULL,NULL,NULL,'2018-10-17 10:51:37',NULL,'Emile-PC/127.0.0.1',NULL,'2018-10-17 10:51:50',1),(30,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-17 13:00:37',NULL,'Emile-PC/192.168.1.100',NULL,'2018-10-17 13:00:56',3),(31,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-17 13:00:37',NULL,'Emile-PC/192.168.1.100',NULL,'2018-10-17 13:01:17',3),(32,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-17 13:05:00',NULL,'Emile-PC/192.168.1.100',NULL,'2018-10-17 13:05:14',3),(33,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-17 13:05:00',NULL,'Emile-PC/192.168.1.100',NULL,'2018-10-17 13:06:33',3),(34,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-17 13:05:00',NULL,'Emile-PC/192.168.1.100',NULL,'2018-10-17 13:06:55',3),(35,'Mugabo Jean',NULL,NULL,NULL,'2018-10-17 13:08:40',NULL,'Emile-PC/192.168.1.100',NULL,'2018-10-17 13:09:09',1),(36,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-17 13:09:20',NULL,'Emile-PC/192.168.1.100',NULL,'2018-10-17 13:09:29',3),(37,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-17 13:09:50',NULL,'Emile-PC/192.168.1.100',NULL,'2018-10-17 13:10:06',3),(38,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-17 13:17:21',NULL,'Emile-PC/192.168.1.100',NULL,'2018-10-17 13:17:30',3),(39,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-17 13:19:22',NULL,'Emile-PC/192.168.1.100',NULL,'2018-10-17 13:19:30',3),(40,'Kabera Nepo',NULL,NULL,NULL,'2018-10-17 13:20:21',NULL,'Emile-PC/192.168.1.100',NULL,'2018-10-17 13:20:31',4),(41,'Ange Mutesi',NULL,NULL,NULL,'2018-10-17 13:21:36',NULL,'Emile-PC/192.168.1.100',NULL,'2018-10-17 13:21:49',2),(42,'Mugabo Jean',NULL,NULL,NULL,'2018-10-17 14:04:43',NULL,'Emile-PC/192.168.1.100',NULL,'2018-10-17 14:04:52',1),(43,'Mugabo Jean',NULL,NULL,NULL,'2018-10-17 14:06:59',NULL,'Emile-PC/192.168.1.100',NULL,'2018-10-17 14:07:10',1),(44,'Mugabo Jean',NULL,NULL,NULL,'2018-10-17 14:13:06',NULL,'Emile-PC/192.168.1.100',NULL,'2018-10-17 14:13:14',1),(45,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-17 14:13:43',NULL,'Emile-PC/192.168.1.100',NULL,'2018-10-17 14:13:54',3),(46,'Kabera Nepo',NULL,NULL,NULL,'2018-10-17 14:14:13',NULL,'Emile-PC/192.168.1.100',NULL,'2018-10-17 14:14:24',4),(47,'Ange Mutesi',NULL,NULL,NULL,'2018-10-17 14:14:31',NULL,'Emile-PC/192.168.1.100',NULL,'2018-10-17 14:14:45',2),(48,'Kabera Nepo',NULL,NULL,NULL,'2018-10-17 14:21:43',NULL,'Emile-PC/192.168.1.100',NULL,'2018-10-17 14:24:12',4),(49,'Mugabo Jean',NULL,NULL,NULL,'2018-10-17 14:33:00',NULL,'Emile-PC/192.168.1.100',NULL,'2018-10-17 14:33:23',1),(50,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-17 14:34:03',NULL,'Emile-PC/192.168.1.100',NULL,'2018-10-17 14:34:15',3),(51,'Mugabo Jean',NULL,NULL,NULL,'2018-10-17 14:54:02',NULL,'Emile-PC/192.168.1.100',NULL,'2018-10-17 14:54:13',1),(52,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-17 14:59:34',NULL,'Emile-PC/192.168.1.100',NULL,'2018-10-17 14:59:49',3),(53,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-17 15:01:48',NULL,'Emile-PC/192.168.1.100',NULL,'2018-10-17 15:01:56',3),(54,'Ange Mutesi',NULL,NULL,NULL,'2018-10-17 15:10:30',NULL,'Emile-PC/192.168.1.100',NULL,'2018-10-17 15:10:46',2),(55,'Mugabo Jean',NULL,NULL,NULL,'2018-10-18 11:55:40',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-18 11:56:16',1),(56,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-18 12:20:50',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-18 12:21:00',3),(57,'Mugabo Jean',NULL,NULL,NULL,'2018-10-18 12:30:10',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-18 12:30:20',1),(58,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-18 12:50:40',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-18 12:50:49',3),(59,'Mugabo Jean',NULL,NULL,NULL,'2018-10-18 13:12:16',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-18 13:12:51',1),(60,'Mugabo Jean',NULL,NULL,NULL,'2018-10-18 13:25:37',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-18 13:25:45',1),(61,'Mugabo Jean',NULL,NULL,NULL,'2018-10-18 13:27:40',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-18 13:28:19',1),(62,'Mugabo Jean',NULL,NULL,NULL,'2018-10-18 13:39:06',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-18 13:39:44',1),(63,'Mugabo Jean',NULL,NULL,NULL,'2018-10-18 13:43:03',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-18 13:43:34',1),(64,'Mugabo Jean',NULL,NULL,NULL,'2018-10-18 13:46:11',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-18 13:46:31',1),(65,'Ange Mutesi',NULL,NULL,NULL,'2018-10-18 13:47:06',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-18 13:47:20',2),(66,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-18 13:47:47',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-18 13:48:01',3),(67,'Mugabo Jean',NULL,NULL,NULL,'2018-10-18 14:14:47',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-18 14:16:11',1),(68,'Mugabo Jean',NULL,NULL,NULL,'2018-10-18 14:24:21',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-18 14:24:44',1),(69,'Mugabo Jean',NULL,NULL,NULL,'2018-10-18 14:56:27',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-18 14:56:48',1),(70,'Mugabo Jean',NULL,NULL,NULL,'2018-10-18 15:00:57',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-18 15:01:27',1),(71,'Mugabo Jean',NULL,NULL,NULL,'2018-10-18 19:16:25',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-18 19:17:58',1),(72,'Mugabo Jean',NULL,NULL,NULL,'2018-10-18 19:31:07',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-18 19:31:30',1),(73,'Mugabo Jean',NULL,NULL,NULL,'2018-10-18 19:46:17',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-18 19:47:09',1),(74,'Mugabo Jean',NULL,NULL,NULL,'2018-10-19 11:49:02',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-19 11:49:37',1),(75,'Mugabo Jean',NULL,NULL,NULL,'2018-10-19 11:56:23',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-19 11:56:32',1),(76,'Mugabo Jean',NULL,NULL,NULL,'2018-10-19 11:57:55',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-19 11:58:03',1),(77,'Mugabo Jean',NULL,NULL,NULL,'2018-10-19 11:58:54',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-19 11:59:04',1),(78,'Mugabo Jean',NULL,NULL,NULL,'2018-10-19 12:01:14',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-19 12:01:23',1),(79,'Mugabo Jean',NULL,NULL,NULL,'2018-10-19 12:03:09',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-19 12:03:18',1),(80,'Mugabo Jean',NULL,NULL,NULL,'2018-10-19 12:04:59',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-19 12:05:07',1),(81,'Mugabo Jean',NULL,NULL,NULL,'2018-10-19 12:06:17',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-19 12:06:25',1),(82,'Mugabo Jean',NULL,NULL,NULL,'2018-10-19 12:07:28',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-19 12:07:38',1),(83,'Mugabo Jean',NULL,NULL,NULL,'2018-10-19 12:10:58',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-19 12:11:07',1),(84,'Mugabo Jean',NULL,NULL,NULL,'2018-10-19 12:54:13',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-19 12:54:28',1),(85,'Mugabo Jean',NULL,NULL,NULL,'2018-10-19 12:58:43',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-19 13:03:58',1),(86,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-19 13:28:46',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-19 13:28:59',3),(87,'Mugabo Jean',NULL,NULL,NULL,'2018-10-19 13:32:59',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-19 13:33:26',1),(88,'Mugabo Jean',NULL,NULL,NULL,'2018-10-19 15:22:51',NULL,'Emile-PC/192.168.1.108',NULL,'2018-10-19 15:23:07',1),(89,'Mugabo Jean',NULL,NULL,NULL,'2018-10-19 22:24:00',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-19 22:24:59',1),(90,'Mugabo Jean',NULL,NULL,NULL,'2018-10-19 22:37:20',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-19 22:41:07',1),(91,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-19 23:06:34',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-19 23:06:48',3),(92,'Kabera Nepo',NULL,NULL,NULL,'2018-10-19 23:08:20',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-19 23:08:40',4),(93,'Mugabo Jean',NULL,NULL,NULL,'2018-10-19 23:12:47',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-19 23:13:10',1),(94,'Mugabo Jean',NULL,NULL,NULL,'2018-10-19 23:25:10',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-19 23:25:21',1),(95,'Mugabo Jean',NULL,NULL,NULL,'2018-10-19 23:25:39',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-19 23:30:04',1),(96,'Mugabo Jean',NULL,NULL,NULL,'2018-10-19 23:32:23',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-19 23:32:37',1),(97,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-19 23:33:20',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-19 23:37:21',3),(98,'Mugabo Jean',NULL,NULL,NULL,'2018-10-19 23:50:50',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-19 23:51:04',1),(99,'Mugabo Jean',NULL,NULL,NULL,'2018-10-20 09:09:07',NULL,'Emile-PC/127.0.0.1',NULL,'2018-10-20 09:09:40',1),(100,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-20 09:17:25',NULL,'Emile-PC/127.0.0.1',NULL,'2018-10-20 09:17:34',3),(101,'Kabera Nepo',NULL,NULL,NULL,'2018-10-20 09:21:16',NULL,'Emile-PC/127.0.0.1',NULL,'2018-10-20 09:21:28',4),(102,'Ange Mutesi',NULL,NULL,NULL,'2018-10-20 09:24:03',NULL,'Emile-PC/127.0.0.1',NULL,'2018-10-20 09:24:34',2),(103,'Mugabo Jean',NULL,NULL,NULL,'2018-10-20 11:33:39',NULL,'Emile-PC/127.0.0.1',NULL,'2018-10-20 11:33:54',1),(104,'Mugabo Jean',NULL,NULL,NULL,'2018-10-20 11:46:53',NULL,'Emile-PC/127.0.0.1',NULL,'2018-10-20 11:47:11',1),(105,'Mugabo Jean',NULL,NULL,NULL,'2018-10-20 11:48:56',NULL,'Emile-PC/127.0.0.1',NULL,'2018-10-20 11:49:12',1),(106,'Mugabo Jean',NULL,NULL,NULL,'2018-10-20 11:59:06',NULL,'Emile-PC/127.0.0.1',NULL,'2018-10-20 11:59:18',1),(107,'Mugabo Jean',NULL,NULL,NULL,'2018-10-20 12:03:51',NULL,'Emile-PC/127.0.0.1',NULL,'2018-10-20 12:04:03',1),(108,'Mugabo Jean',NULL,NULL,NULL,'2018-10-20 16:09:34',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-20 16:38:57',1),(109,'Mugabo Jean',NULL,NULL,NULL,'2018-10-20 16:50:45',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-20 16:52:51',1),(110,'Mugabo Jean',NULL,NULL,NULL,'2018-10-20 17:27:22',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-20 17:30:59',1),(111,'Mugabo Jean',NULL,NULL,NULL,'2018-10-20 17:34:40',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-20 17:34:56',1),(112,'Hitimana Emile',NULL,NULL,NULL,'2018-10-20 17:38:57',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-20 17:39:24',1),(113,'Hitimana Emile',NULL,NULL,NULL,'2018-10-20 17:40:36',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-20 17:40:50',1),(114,'Ange Mutesi',NULL,NULL,NULL,'2018-10-20 17:41:47',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-20 17:41:58',2),(115,'Ange Mutesi',NULL,NULL,NULL,'2018-10-20 17:43:17',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-20 17:43:28',2),(116,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-20 18:20:25',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-20 18:21:03',3),(117,'Ange Mutesi',NULL,NULL,NULL,'2018-10-20 18:23:34',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-20 18:23:45',2),(118,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-20 18:25:34',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-20 18:28:14',3),(119,'Kabera Nepo',NULL,NULL,NULL,'2018-10-20 18:29:51',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-20 18:30:01',4),(120,'Patrick Nshuti',NULL,NULL,NULL,'2018-10-20 18:36:03',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-20 18:36:16',3),(121,'Hitimana Emile',NULL,NULL,NULL,'2018-10-20 18:39:30',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-20 18:39:43',1),(122,'Hitimana Emile',NULL,NULL,NULL,'2018-10-20 18:54:49',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-20 18:55:05',1),(123,'Kabera Nepo',NULL,NULL,NULL,'2018-10-20 18:56:18',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-20 18:56:29',4),(124,'Ange Mutesi',NULL,NULL,NULL,'2018-10-20 19:17:29',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-20 19:17:47',2),(125,'Hitimana Emile',NULL,NULL,NULL,'2018-10-20 19:25:05',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-20 19:25:20',1),(126,'Hitimana Emile',NULL,NULL,NULL,'2018-10-20 19:51:42',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-20 19:52:04',1),(127,'Hitimana Emile',NULL,NULL,NULL,'2018-10-20 19:53:11',NULL,'Emile-PC/172.27.242.44',NULL,'2018-10-20 19:53:20',1);
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
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
  PRIMARY KEY (`planId`),
  KEY `FKDAE6488937BE5948` (`Users`),
  CONSTRAINT `FKDAE6488937BE5948` FOREIGN KEY (`Users`) REFERENCES `users` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `strategicplan`
--

LOCK TABLES `strategicplan` WRITE;
/*!40000 ALTER TABLE `strategicplan` DISABLE KEYS */;
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
  PRIMARY KEY (`taskId`),
  KEY `FK27A9A5D2054A7C` (`parentTask`),
  KEY `FK27A9A53D79426A` (`strategicPlan`),
  CONSTRAINT `FK27A9A53D79426A` FOREIGN KEY (`strategicPlan`) REFERENCES `strategicplan` (`planId`),
  CONSTRAINT `FK27A9A5D2054A7C` FOREIGN KEY (`parentTask`) REFERENCES `task` (`taskId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
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
  PRIMARY KEY (`userCatid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usercategory`
--

LOCK TABLES `usercategory` WRITE;
/*!40000 ALTER TABLE `usercategory` DISABLE KEYS */;
INSERT INTO `usercategory` VALUES (1,'Super Admin'),(2,'Staff'),(3,'Institution Representative'),(4,'Superviser');
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,NULL,'2018-10-10 21:31:13','active',NULL,NULL,NULL,NULL,'Kigali',NULL,'Hitimana',NULL,'20601_417632151640039_14662353_n.jpeg.jpg','Emile','online','active','admin','21232f297a57a5a743894a0e4a801fc3',NULL,1,NULL),(2,NULL,'2018-10-10 21:31:13','active',NULL,NULL,NULL,NULL,'Kigali',NULL,'Ange',NULL,'10277601_652445698158682_3875396045293399308_n.jpg','Mutesi','online','active','staff','21232f297a57a5a743894a0e4a801fc3',NULL,2,NULL),(3,NULL,'2018-10-10 21:31:13','active',NULL,NULL,NULL,NULL,'Kigali',NULL,'Patrick',NULL,'us.png','Nshuti','online','active','rep','21232f297a57a5a743894a0e4a801fc3',NULL,3,NULL),(4,NULL,'2018-10-10 21:31:13','active',NULL,NULL,NULL,NULL,'Kigali',NULL,'Kabera',NULL,'us.png','Nepo','online','active','super','21232f297a57a5a743894a0e4a801fc3',NULL,4,NULL),(5,NULL,'2018-10-17 14:32:32','active',NULL,NULL,NULL,NULL,'Kigali',NULL,'Alain',NULL,'us.png','Muhire','offline','active','alain','21232f297a57a5a743894a0e4a801fc3',NULL,2,NULL),(6,NULL,'2018-10-17 14:32:32','active',NULL,NULL,NULL,NULL,'Kigali',NULL,'Bosco',NULL,'us.png','Ngenzi','offline','active','ngenzi','21232f297a57a5a743894a0e4a801fc3',NULL,3,NULL),(7,NULL,'2018-10-17 14:32:32','active',NULL,NULL,NULL,NULL,'Kigali',NULL,'Alice',NULL,'us.png','Gihozo','offline','active','alice','21232f297a57a5a743894a0e4a801fc3',NULL,4,NULL);
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

-- Dump completed on 2018-10-20 19:55:19
