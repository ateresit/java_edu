-- MySQL dump 10.13  Distrib 8.0.25, for macos11 (x86_64)
--
-- Host: localhost    Database: my_cinema
-- ------------------------------------------------------
-- Server version	8.0.25

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
-- Table structure for table `breake_time`
--

DROP TABLE IF EXISTS `breake_time`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `breake_time` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pause_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `breake_time`
--

LOCK TABLES `breake_time` WRITE;
/*!40000 ALTER TABLE `breake_time` DISABLE KEYS */;
INSERT INTO `breake_time` VALUES (1,'1000-01-01 00:15:00'),(2,'1000-01-01 00:20:00'),(3,'1000-01-01 00:25:00'),(4,'1000-01-01 00:30:00'),(5,'1000-01-01 00:35:00'),(6,'1000-01-01 00:40:00'),(7,'1000-01-01 00:45:00'),(8,'1000-01-01 00:50:00');
/*!40000 ALTER TABLE `breake_time` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `duration` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie`
--

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` VALUES (1,'Star Wars episode 7',120),(2,'Django Unchained',165),(3,'Super Movie',200),(4,'Travelers',60);
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session_price`
--

DROP TABLE IF EXISTS `session_price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `session_price` (
  `id` int NOT NULL AUTO_INCREMENT,
  `session_id` int NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `session_id_UNIQUE` (`session_id`),
  KEY `fk_session_id_idx` (`session_id`),
  CONSTRAINT `fk_session_id` FOREIGN KEY (`session_id`) REFERENCES `sessions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session_price`
--

LOCK TABLES `session_price` WRITE;
/*!40000 ALTER TABLE `session_price` DISABLE KEYS */;
INSERT INTO `session_price` VALUES (1,1,500),(2,2,550),(3,7,800),(4,4,300),(5,5,200),(6,9,750),(7,8,450);
/*!40000 ALTER TABLE `session_price` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessions`
--

DROP TABLE IF EXISTS `sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sessions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `movie_id` int NOT NULL,
  `session_date` datetime NOT NULL,
  `session_start` datetime NOT NULL,
  `session_braketime_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_movie_id_idx` (`movie_id`),
  KEY `fk_braketime_id_idx` (`session_braketime_id`),
  CONSTRAINT `fk_braketime_id` FOREIGN KEY (`session_braketime_id`) REFERENCES `breake_time` (`id`),
  CONSTRAINT `fk_movie_id` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessions`
--

LOCK TABLES `sessions` WRITE;
/*!40000 ALTER TABLE `sessions` DISABLE KEYS */;
INSERT INTO `sessions` VALUES (1,1,'2022-01-31 00:00:00','2022-01-31 10:00:00',3),(2,1,'2022-01-31 00:00:00','2022-01-31 10:30:00',5),(3,1,'2022-01-31 00:00:00','2022-01-31 16:00:00',6),(4,2,'2022-01-31 00:00:00','2022-01-31 11:00:00',6),(5,2,'2022-01-31 00:00:00','2022-01-31 13:30:00',4),(6,3,'2022-01-31 00:00:00','2022-01-31 17:00:00',2),(7,1,'2022-01-31 00:00:00','2022-01-31 19:00:00',2),(8,4,'2022-01-31 00:00:00','2022-01-31 12:15:00',7),(9,4,'2022-01-31 00:00:00','2022-01-31 16:15:00',8);
/*!40000 ALTER TABLE `sessions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tickets` (
  `id` int NOT NULL AUTO_INCREMENT,
  `session_price_id` int NOT NULL,
  `ticket_number` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ticket_number_UNIQUE` (`ticket_number`),
  KEY `fk_session_price_id_idx` (`session_price_id`),
  CONSTRAINT `fk_session_price_id` FOREIGN KEY (`session_price_id`) REFERENCES `session_price` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tickets`
--

LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` VALUES (1,1,12345),(2,1,22345),(3,2,333),(4,1,4343),(5,2,34344),(6,3,5454),(7,2,232323),(8,4,111),(9,6,56565);
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-01 18:25:45
