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
-- Table structure for table `alan`
--

DROP TABLE IF EXISTS `alan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alan` (
  `alanNo` int NOT NULL,
  `sahipID` int NOT NULL,
  `alanTuru` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`alanNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alan`
--

LOCK TABLES `alan` WRITE;
/*!40000 ALTER TABLE `alan` DISABLE KEYS */;
INSERT INTO `alan` VALUES (1,0,'market'),(2,0,'emlak'),(3,0,'magaza'),(4,1,'market'),(5,1,'Arsa'),(6,0,'Arsa'),(7,0,'Arsa'),(8,0,'Arsa'),(9,0,'Arsa'),(10,0,'Arsa'),(11,0,'Arsa'),(12,0,'Arsa'),(13,0,'Arsa'),(14,0,'Arsa'),(15,0,'Arsa'),(16,0,'Arsa'),(17,0,'Arsa'),(18,0,'Arsa'),(19,0,'Arsa'),(20,0,'Arsa'),(21,0,'Arsa'),(22,0,'Arsa'),(23,0,'Arsa'),(24,0,'Arsa'),(25,0,'Arsa'),(26,0,'Arsa'),(27,0,'Arsa'),(28,0,'Arsa'),(29,0,'Arsa'),(30,0,'Arsa'),(31,0,'Arsa'),(32,0,'Arsa'),(33,0,'Arsa'),(34,0,'Arsa'),(35,0,'Arsa'),(36,0,'Arsa'),(37,0,'Arsa'),(38,0,'Arsa'),(39,0,'Arsa'),(40,0,'Arsa'),(41,0,'Arsa'),(42,0,'Arsa'),(43,0,'Arsa'),(44,0,'Arsa'),(45,0,'Arsa'),(46,0,'Arsa'),(47,0,'Arsa'),(48,0,'Arsa'),(49,0,'Arsa'),(50,0,'Arsa'),(51,0,'Arsa'),(52,0,'Arsa'),(53,0,'Arsa'),(54,0,'Arsa'),(55,0,'Arsa'),(56,0,'Arsa'),(57,0,'Arsa'),(58,0,'Arsa'),(59,0,'Arsa'),(60,0,'Arsa'),(61,0,'Arsa'),(62,0,'Arsa'),(63,0,'Arsa'),(64,0,'Arsa');
/*!40000 ALTER TABLE `alan` ENABLE KEYS */;
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
