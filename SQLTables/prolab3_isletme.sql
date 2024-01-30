-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: prolab3
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `isletme`
--

DROP TABLE IF EXISTS `isletme`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `isletme` (
  `alanNo` int NOT NULL,
  `isletmeTuru` varchar(255) DEFAULT NULL,
  `yoneticiUcret` varchar(255) DEFAULT NULL,
  `kullaniciUcret` varchar(255) DEFAULT NULL,
  `seviye` int DEFAULT NULL,
  `kapasite` int DEFAULT NULL,
  `calisanSayisi` int DEFAULT NULL,
  `yiyecekUcret` int DEFAULT NULL,
  `esyaUcret` int DEFAULT NULL,
  `ilanVerildi` int DEFAULT NULL,
  `ilanGun` int DEFAULT NULL,
  PRIMARY KEY (`alanNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `isletme`
--

LOCK TABLES `isletme` WRITE;
/*!40000 ALTER TABLE `isletme` DISABLE KEYS */;
INSERT INTO `isletme` VALUES (1,'market','500','0',100,3,0,100,0,1,20),(2,'emlak','500','0',100,3,1,0,0,1,10),(3,'magaza','500','0',100,3,0,0,100,0,0),(4,'market','0','200',0,3,0,35,0,0,0);
/*!40000 ALTER TABLE `isletme` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-28 20:22:53
