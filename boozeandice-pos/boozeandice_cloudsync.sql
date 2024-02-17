-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: boozeandice
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `additional_address_details` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `landmark` varchar(255) DEFAULT NULL,
  `postal_zip_code` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'Canda St. Purok 6 Bombon Tabaco City',NULL,NULL,'Honda Services',NULL,NULL,NULL,NULL),(2,'Zone 6 Canda St. Bombon Tabaco City Albay Philippines',NULL,NULL,'Honda Services',NULL,NULL,NULL,NULL),(3,'182 Canda St Bombon Tabaco City',NULL,NULL,'Honda Services',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cash_added`
--

DROP TABLE IF EXISTS `cash_added`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cash_added` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cash` double DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `cash_drawer_id` bigint DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK61q6ujxjptfh0eu4g41xxs6ew` (`cash_drawer_id`),
  KEY `FKjl3h7o89e5vqtsbs4nwxjdlp7` (`created_by`),
  CONSTRAINT `FK61q6ujxjptfh0eu4g41xxs6ew` FOREIGN KEY (`cash_drawer_id`) REFERENCES `cash_drawer` (`id`),
  CONSTRAINT `FKjl3h7o89e5vqtsbs4nwxjdlp7` FOREIGN KEY (`created_by`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cash_added`
--

LOCK TABLES `cash_added` WRITE;
/*!40000 ALTER TABLE `cash_added` DISABLE KEYS */;
/*!40000 ALTER TABLE `cash_added` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cash_drawer`
--

DROP TABLE IF EXISTS `cash_drawer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cash_drawer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `starting_cash` double DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK384ppfbuknuxsoxdl2fkw1c6k` (`created_by`),
  CONSTRAINT `FK384ppfbuknuxsoxdl2fkw1c6k` FOREIGN KEY (`created_by`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cash_drawer`
--

LOCK TABLES `cash_drawer` WRITE;
/*!40000 ALTER TABLE `cash_drawer` DISABLE KEYS */;
INSERT INTO `cash_drawer` VALUES (1,'2023-10-08 16:41:11.914000',1000,1),(2,'2023-10-09 08:16:03.001000',1000,1);
/*!40000 ALTER TABLE `cash_drawer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config`
--

DROP TABLE IF EXISTS `config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config`
--

LOCK TABLES `config` WRITE;
/*!40000 ALTER TABLE `config` DISABLE KEYS */;
INSERT INTO `config` VALUES (1,'remoteAddress','lyndon');
/*!40000 ALTER TABLE `config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email_address` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `img_location` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjfibudqxs2cbr5x7nsu9fq0rw` (`created_by`),
  CONSTRAINT `FKjfibudqxs2cbr5x7nsu9fq0rw` FOREIGN KEY (`created_by`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'bordonada.don22@gmail.com','09605394782',NULL,'Maverick Bordonada','2023-10-09 13:00:37.151000',1);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expenses`
--

DROP TABLE IF EXISTS `expenses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expenses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `expense` double DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `cash_drawer_id` bigint DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmg29qq9u84djeh7gcwwtq5kpm` (`cash_drawer_id`),
  KEY `FK26h2pfy2smvjw49dea2ubrn6j` (`created_by`),
  CONSTRAINT `FK26h2pfy2smvjw49dea2ubrn6j` FOREIGN KEY (`created_by`) REFERENCES `tb_user` (`id`),
  CONSTRAINT `FKmg29qq9u84djeh7gcwwtq5kpm` FOREIGN KEY (`cash_drawer_id`) REFERENCES `cash_drawer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expenses`
--

LOCK TABLES `expenses` WRITE;
/*!40000 ALTER TABLE `expenses` DISABLE KEYS */;
INSERT INTO `expenses` VALUES (1,'2023-10-09 09:09:29.858000',100,'water bill',2,1),(2,'2023-10-09 09:15:56.164000',50,'snack',2,1);
/*!40000 ALTER TABLE `expenses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_position`
--

DROP TABLE IF EXISTS `job_position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_position` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_position`
--

LOCK TABLES `job_position` WRITE;
/*!40000 ALTER TABLE `job_position` DISABLE KEYS */;
INSERT INTO `job_position` VALUES (1,'Responsible for overseeing the daily operations of the store, managing staff, inventory management, customer service, and ensuring sales goals are met.','Store Manager'),(2,'Assists the store manager in various tasks, including inventory management, staff supervision, customer service, and administrative duties.','Assistant Manager'),(3,'Handles customer transactions, operates the cash register, and provides excellent customer service.','Cashier'),(4,'Assists customers in finding products, restocks shelves, and provides product recommendations.','Sales Associate'),(5,'Manages inventory levels, restocks products, receives shipments, and conducts regular inventory checks.','Inventory Clerk'),(6,'Monitors the store for theft and ensures the safety of both customers and staff.','Security Personnel'),(7,'Responsible for delivering orders to customers\' homes or businesses, if the store offers delivery services.','Delivery Driver'),(8,'Prepares and serves alcoholic and non-alcoholic beverages, takes customer orders, interacts with patrons, and maintains the bar area.','Bartender'),(9,'Oversees the bar\'s operations, manages bartenders and staff, ensures compliance with alcohol regulations, and maintains inventory.','Bar Manager'),(10,'Takes orders, serves drinks and food, and provides a positive dining experience to customers.','Server'),(11,'Assists the bartender by restocking supplies, cleaning glasses, clearing tables, and ensuring the bar area is organized.','Barback'),(12,'Takes orders, serves customers, and ensures a high level of customer satisfaction in the dining area.','Waitstaff');
/*!40000 ALTER TABLE `job_position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cost` double NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `img_location` varchar(255) DEFAULT NULL,
  `manufacturer` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `packaging_fee` double DEFAULT NULL,
  `price` double NOT NULL,
  `stock_available` bigint DEFAULT NULL,
  `supplier` varchar(255) DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `product_category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtborqga1vlmpby0kp4690x415` (`created_by`),
  KEY `FKcwclrqu392y86y0pmyrsi649r` (`product_category_id`),
  CONSTRAINT `FKcwclrqu392y86y0pmyrsi649r` FOREIGN KEY (`product_category_id`) REFERENCES `product_category` (`id`),
  CONSTRAINT `FKtborqga1vlmpby0kp4690x415` FOREIGN KEY (`created_by`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,35,'2023-10-08 16:35:15.218000',NULL,'elhombre.jpg',NULL,'El Hombre',NULL,5,50,88,NULL,1,1),(2,45,'2023-10-08 16:35:15.237000',NULL,'josecuervo.jpg',NULL,'Jose Cuervo',NULL,5,74,51,NULL,1,1),(3,22,'2023-10-08 16:35:15.245000',NULL,'patron.jpg',NULL,'Patron',NULL,4,44,45,NULL,1,1),(4,40,'2023-10-08 16:35:15.252000',NULL,'corona_extra.jpg',NULL,'Corona Extra',NULL,3,90,0,NULL,1,2),(5,35,'2023-10-08 16:35:15.258000',NULL,'Red Horse',NULL,'Red Horse',NULL,5,50,0,NULL,1,2),(6,45,'2023-10-08 16:35:15.265000',NULL,'San Miguel Light',NULL,'San Miguel Light',NULL,5,74,0,NULL,1,2),(7,22,'2023-10-08 16:35:15.272000',NULL,'San Miguel Pilsen',NULL,'San Miguel Pilsen',NULL,4,44,0,NULL,1,2),(8,40,'2023-10-08 16:35:15.279000',NULL,'Heneken',NULL,'Heneken',NULL,3,90,0,NULL,1,2),(9,35,'2023-10-08 16:35:15.289000',NULL,'Crazy Carabao',NULL,'Crazy Carabao',NULL,5,50,0,NULL,1,2),(10,45,'2023-10-08 16:35:15.294000',NULL,'Citrun',NULL,'Citrun',NULL,5,74,0,NULL,1,3),(11,22,'2023-10-08 16:35:15.298000',NULL,'Kurant',NULL,'Kurant',NULL,4,44,0,NULL,1,3),(12,40,'2023-10-08 16:35:15.302000',NULL,'Vokda',NULL,'Vokda',NULL,3,90,0,NULL,1,3),(13,35,'2023-10-08 16:35:15.306000',NULL,'J&B',NULL,'J&B',NULL,5,50,0,NULL,1,4),(14,45,'2023-10-08 16:35:15.311000',NULL,'jack_daniel.jpg',NULL,'Jack Daniel',NULL,5,74,0,NULL,1,4),(15,22,'2023-10-08 16:35:15.316000',NULL,'jameson.jpg',NULL,'Jameson',NULL,4,44,0,NULL,1,4),(16,35,'2023-10-08 16:35:15.321000',NULL,'Jeam Beam',NULL,'Jeam Beam',NULL,5,50,0,NULL,1,4),(17,45,'2023-10-08 16:35:15.328000',NULL,'Johnny Walker',NULL,'Johnny Walker',NULL,5,74,0,NULL,1,4),(18,22,'2023-10-08 16:35:15.335000',NULL,'Markers Mark',NULL,'Markers Mark',NULL,4,44,0,NULL,1,4),(19,40,'2023-10-08 16:35:15.341000',NULL,'Tangueray',NULL,'Tangueray',NULL,3,90,0,NULL,1,5),(20,35,'2023-10-08 16:35:15.348000',NULL,'bombay_sapphire.jpg',NULL,'Bombay Sapphire',NULL,5,50,0,NULL,1,5),(21,45,'2023-10-08 16:35:15.354000',NULL,'Gilbeys Gin',NULL,'Gilbeys Gin',NULL,5,74,0,NULL,1,5),(22,22,'2023-10-08 16:35:15.362000',NULL,'Gilbeys Vodka',NULL,'Gilbeys Vodka',NULL,4,44,0,NULL,1,5),(23,40,'2023-10-08 16:35:15.369000',NULL,'Hardy\'s CabSub',NULL,'Hardy\'s CabSub',NULL,3,90,0,NULL,1,6),(24,35,'2023-10-08 16:35:15.379000',NULL,'Hardy\'s Shiraz',NULL,'Hardy\'s Shiraz',NULL,5,50,0,NULL,1,6),(25,45,'2023-10-08 16:35:15.386000',NULL,'Yellow Tail Chardonnay',NULL,'Yellow Tail Chardonnay',NULL,5,74,0,NULL,1,6),(26,22,'2023-10-08 16:35:15.395000',NULL,'Yellow Tail Shiraz',NULL,'Yellow Tail Shiraz',NULL,4,44,0,NULL,1,6),(27,40,'2023-10-08 16:35:15.402000',NULL,'tequila_rose.jpg',NULL,'Tequila Rose',NULL,3,90,0,NULL,1,7),(28,35,'2023-10-08 16:35:15.411000',NULL,'Malibu',NULL,'Malibu',NULL,5,50,0,NULL,1,7),(29,45,'2023-10-08 16:35:15.418000',NULL,'jager_meister.jpg',NULL,'Jager meister',NULL,5,74,0,NULL,1,7),(30,22,'2023-10-08 16:35:15.427000',NULL,'frangelico.jpg',NULL,'Frangelico',NULL,4,44,0,NULL,1,7),(31,22,'2023-10-08 16:35:15.434000',NULL,'Galliano',NULL,'Galliano',NULL,4,44,0,NULL,1,7),(32,45,'2023-10-08 16:35:15.442000',NULL,'Cointreau',NULL,'Cointreau',NULL,5,74,0,NULL,1,7),(33,22,'2023-10-08 16:35:15.449000',NULL,'Camparri',NULL,'Camparri',NULL,4,44,0,NULL,1,7),(34,22,'2023-10-08 16:35:15.459000',NULL,'Amaretto Desarono',NULL,'Amaretto Desarono',NULL,4,44,0,NULL,1,7);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_category`
--

DROP TABLE IF EXISTS `product_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `created_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb03vrefvbdolh4tinqccet7si` (`created_by`),
  CONSTRAINT `FKb03vrefvbdolh4tinqccet7si` FOREIGN KEY (`created_by`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_category`
--

LOCK TABLES `product_category` WRITE;
/*!40000 ALTER TABLE `product_category` DISABLE KEYS */;
INSERT INTO `product_category` VALUES (1,NULL,'Tequila',1),(2,NULL,'Beers',1),(3,NULL,'Absolute',1),(4,NULL,'Whisky',1),(5,NULL,'Gin',1),(6,NULL,'Wine',1),(7,NULL,'Liqueur',1);
/*!40000 ALTER TABLE `product_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_stock`
--

DROP TABLE IF EXISTS `product_stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_stock` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `barcode_digits` bigint DEFAULT NULL,
  `barcode_digitsvii` varchar(255) DEFAULT NULL,
  `barcode_image_location` varchar(255) DEFAULT NULL,
  `cost` double NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `expenses` double DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `purchase_date` datetime(6) NOT NULL,
  `quantity` bigint NOT NULL,
  `created_by` bigint DEFAULT NULL,
  `last_modified_by` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK69tqgsiw92rrrlujgutrxyp5y` (`created_by`),
  KEY `FK3let0kanymt8thon9vf75wfyk` (`last_modified_by`),
  KEY `FKlpu1phje1bb3y9ww8k9fut4gh` (`product_id`),
  CONSTRAINT `FK3let0kanymt8thon9vf75wfyk` FOREIGN KEY (`last_modified_by`) REFERENCES `tb_user` (`id`),
  CONSTRAINT `FK69tqgsiw92rrrlujgutrxyp5y` FOREIGN KEY (`created_by`) REFERENCES `tb_user` (`id`),
  CONSTRAINT `FKlpu1phje1bb3y9ww8k9fut4gh` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_stock`
--

LOCK TABLES `product_stock` WRITE;
/*!40000 ALTER TABLE `product_stock` DISABLE KEYS */;
INSERT INTO `product_stock` VALUES (1,NULL,NULL,NULL,2745,'2023-10-08 16:35:15.467000',NULL,NULL,NULL,'2023-10-08 16:35:15.467000',100,2,NULL,1),(2,NULL,NULL,NULL,2000,'2023-10-08 16:35:15.489000',NULL,NULL,NULL,'2023-10-08 16:35:15.489000',80,2,NULL,2),(3,NULL,NULL,NULL,1894,'2023-10-08 16:35:15.503000',NULL,NULL,NULL,'2023-10-08 16:35:15.503000',74,2,NULL,3),(4,NULL,NULL,NULL,1894,'2023-10-08 16:35:15.518000',NULL,NULL,NULL,'2023-10-08 16:35:15.518000',50,2,NULL,1);
/*!40000 ALTER TABLE `product_stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADMIN'),(2,'SUPERVISOR'),(3,'CASHIER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipment`
--

DROP TABLE IF EXISTS `shipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shipment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `actual_delivery_date` datetime(6) DEFAULT NULL,
  `carrier` varchar(255) DEFAULT NULL,
  `estimated_delivery_date` datetime(6) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `shipment_date` datetime(6) DEFAULT NULL,
  `shipment_status` varchar(255) DEFAULT NULL,
  `shipping_cost` double DEFAULT NULL,
  `shipping_method` varchar(255) DEFAULT NULL,
  `tracking_number` varchar(255) DEFAULT NULL,
  `delivery_driver` bigint DEFAULT NULL,
  `destination_address` bigint DEFAULT NULL,
  `origin_address` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlftdkntf0s5b9erah27tpgy9q` (`delivery_driver`),
  KEY `FKkv9xm8jkjwodxl1fosciu3u4y` (`destination_address`),
  KEY `FK6ta00uj6thi5yt7gixxkicta1` (`origin_address`),
  CONSTRAINT `FK6ta00uj6thi5yt7gixxkicta1` FOREIGN KEY (`origin_address`) REFERENCES `address` (`id`),
  CONSTRAINT `FKkv9xm8jkjwodxl1fosciu3u4y` FOREIGN KEY (`destination_address`) REFERENCES `address` (`id`),
  CONSTRAINT `FKlftdkntf0s5b9erah27tpgy9q` FOREIGN KEY (`delivery_driver`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipment`
--

LOCK TABLES `shipment` WRITE;
/*!40000 ALTER TABLE `shipment` DISABLE KEYS */;
INSERT INTO `shipment` VALUES (1,NULL,'OWN','2023-10-09 00:00:00.000000',NULL,NULL,'DELIVERED',NULL,NULL,NULL,3,1,NULL),(2,NULL,'OWN','2023-10-10 00:00:00.000000',NULL,NULL,'OUT_FOR_DELIVERY',NULL,NULL,NULL,3,2,NULL),(3,NULL,'OWN','2023-10-11 00:00:00.000000',NULL,NULL,'OUT_FOR_DELIVERY',NULL,NULL,NULL,3,3,NULL);
/*!40000 ALTER TABLE `shipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user`
--

DROP TABLE IF EXISTS `tb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `about` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) NOT NULL,
  `fname` varchar(255) NOT NULL,
  `img_location` varchar(255) DEFAULT NULL,
  `last_login_date_time` datetime(6) DEFAULT NULL,
  `lname` varchar(255) NOT NULL,
  `mname` varchar(255) DEFAULT NULL,
  `mobile_number` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `user_status` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `job_position` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKc2nfi6r9gxv99og41n8lgk3hi` (`job_position`),
  CONSTRAINT `FKc2nfi6r9gxv99og41n8lgk3hi` FOREIGN KEY (`job_position`) REFERENCES `job_position` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user`
--

LOCK TABLES `tb_user` WRITE;
/*!40000 ALTER TABLE `tb_user` DISABLE KEYS */;
INSERT INTO `tb_user` VALUES (1,'Application Developer / Full Stack Web Developer / Food Lover / Coffee Lover / Family Guy','2023-10-08 16:35:14.695000','Lyndon','lyndon.jpg',NULL,'Bordonada',NULL,'+639565776738','malcom19',NULL,'lxbordo',NULL),(2,'Grade 3 / Youtuber / Zelda Fanatic','2023-10-08 16:35:14.733000','Malcom','lyndon.jpg',NULL,'Bordonada',NULL,'+639565776738','malcom19',NULL,'mxbordo',NULL),(3,NULL,'2023-10-09 08:21:17.095000','John',NULL,NULL,'Smith','','','test123',NULL,'jsmith',7);
/*!40000 ALTER TABLE `tb_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `time_record`
--

DROP TABLE IF EXISTS `time_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `time_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `time_in` datetime(6) DEFAULT NULL,
  `time_out` datetime(6) DEFAULT NULL,
  `staff_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtn46p4a27j697ftlbdma6i41l` (`staff_id`),
  CONSTRAINT `FKtn46p4a27j697ftlbdma6i41l` FOREIGN KEY (`staff_id`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `time_record`
--

LOCK TABLES `time_record` WRITE;
/*!40000 ALTER TABLE `time_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `time_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `card_brand` varchar(255) DEFAULT NULL,
  `cash_received` double DEFAULT NULL,
  `cost` double DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `invoice_number` varchar(255) DEFAULT NULL,
  `is_delivery` bit(1) DEFAULT NULL,
  `packaging` double DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `payment_reference` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `profit` double DEFAULT NULL,
  `shipping` double DEFAULT NULL,
  `sold_to` varchar(255) DEFAULT NULL,
  `sub_total` double DEFAULT NULL,
  `table_no` varchar(255) DEFAULT NULL,
  `take_out` bit(1) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `transaction_date_time` datetime(6) DEFAULT NULL,
  `transaction_notes` varchar(255) DEFAULT NULL,
  `transaction_status` varchar(255) DEFAULT NULL,
  `transaction_type` varchar(255) DEFAULT NULL,
  `vat_amount` double DEFAULT NULL,
  `vatable_sales` double DEFAULT NULL,
  `cashdrawer_id` bigint DEFAULT NULL,
  `cashier_id` bigint DEFAULT NULL,
  `customer_id` bigint DEFAULT NULL,
  `shipment_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2umrcu6dq8qat6yr10htpty91` (`cashdrawer_id`),
  KEY `FK16kbdj6o8mj59gmgvfjkim3jd` (`cashier_id`),
  KEY `FKnbpjofb5abhjg5hiovi0t3k57` (`customer_id`),
  KEY `FK48ukhnggpu8yfb41xbgw9jb89` (`shipment_id`),
  CONSTRAINT `FK16kbdj6o8mj59gmgvfjkim3jd` FOREIGN KEY (`cashier_id`) REFERENCES `tb_user` (`id`),
  CONSTRAINT `FK2umrcu6dq8qat6yr10htpty91` FOREIGN KEY (`cashdrawer_id`) REFERENCES `cash_drawer` (`id`),
  CONSTRAINT `FK48ukhnggpu8yfb41xbgw9jb89` FOREIGN KEY (`shipment_id`) REFERENCES `shipment` (`id`),
  CONSTRAINT `FKnbpjofb5abhjg5hiovi0t3k57` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1,NULL,396,198,0,'INV202310080001',_binary '\0',0,'CASH',NULL,NULL,198,0,'don',396,'0',_binary '\0',396,'2023-10-08 16:42:59.765000',NULL,'PAID','SALE',47.519999999999996,348.48,1,1,NULL,NULL),(2,NULL,118,67,0,'INV202310080002',_binary '\0',0,'CASH',NULL,NULL,51,0,'lyndon',118,'0',_binary '\0',118,'2023-10-08 16:43:31.737000',NULL,'PAID','SALE',14.16,103.84,1,1,NULL,NULL),(3,NULL,842,535,0,'INV202310080003',_binary '\0',0,'CASH',NULL,NULL,307,0,'carl',842,'0',_binary '\0',842,'2023-10-08 17:08:22.242000',NULL,'PAID','SALE',101.03999999999999,740.96,1,1,NULL,NULL),(4,NULL,44,22,0,'INV202310080004',_binary '',0,'CASH',NULL,NULL,22,0,'lyndon',44,'0',_binary '\0',44,'2023-10-08 17:36:25.840000',NULL,'PAID','SALE',5.279999999999999,38.72,1,1,NULL,NULL),(6,NULL,360,315,90,'INV202310090001',_binary '\0',0,'CASH',NULL,NULL,45,0,'don',360,'0',_binary '\0',360,'2023-10-09 08:17:15.729000',NULL,'PAID','SALE',43.199999999999996,316.8,2,1,NULL,NULL),(7,NULL,950,665,0,'INV202310090002',_binary '',0,'CASH',NULL,NULL,285,0,'carm',950,'0',_binary '\0',950,'2023-10-09 08:19:09.214000',NULL,'PAID','SALE',114,836,2,1,NULL,1),(9,NULL,450,315,0,'INV202310090003',_binary '\0',0,'GCASH','000111222','+639565776738',135,0,'don',450,'0',_binary '\0',450,'2023-10-09 09:18:57.484000',NULL,'PAID','SALE',54,396,2,1,NULL,NULL),(10,NULL,50,35,0,'INV202310090004',_binary '\0',0,'CASH',NULL,NULL,15,0,'don',50,'0',_binary '\0',50,'2023-10-09 09:38:35.355000',NULL,'PAID','SALE',6,44,2,1,NULL,NULL),(12,NULL,74,45,0,'INV202310090005',_binary '\0',0,'CASH',NULL,NULL,29,0,'carmel',74,'Table 1',_binary '\0',74,'2023-10-09 13:44:23.213000',NULL,'PAID','SALE',8.879999999999999,65.12,2,2,NULL,NULL),(13,NULL,74,45,0,'INV202310090005',_binary '\0',0,'CASH',NULL,NULL,29,0,'test',74,'Table 1',_binary '\0',74,'2023-10-09 13:47:09.739000',NULL,'PAID','SALE',8.879999999999999,65.12,2,2,NULL,NULL),(14,'VISA',1512,918,0,'INV202310090007',_binary '',0,'PAYMAYA','000111222',NULL,594,0,'lyndon',1512,'0',_binary '\0',1512,'2023-10-09 14:11:58.085000',NULL,'PAID','SALE',181.44,1330.56,2,1,NULL,2),(15,NULL,50,35,0,'INV202310090008',_binary '',0,'CASH',NULL,NULL,15,0,'Maverick Bordonada',50,'Table 1',_binary '\0',50,'2023-10-09 16:35:01.558000',NULL,'PAID','SALE',6,44,2,1,1,NULL),(16,NULL,450,315,0,'INV202310090009',_binary '',0,'CASH',NULL,NULL,135,0,'Maverick Bordonada',450,'0',_binary '\0',450,'2023-10-09 16:38:10.366000',NULL,'PAID','SALE',54,396,2,1,1,3),(17,NULL,500,198,0,'INV202310090010',_binary '\0',36,'CASH',NULL,NULL,234,0,'lyndon',396,'0',_binary '',432,'2023-10-09 23:55:49.733000',NULL,'PAID','SALE',47.519999999999996,348.48,2,1,NULL,NULL),(18,'VISA',711,405,0,'INV202310090011',_binary '\0',45,'PAYMAYA','000111222',NULL,306,0,'lyndon',666,'Table 1',_binary '',711,'2023-10-09 23:58:59.766000',NULL,'PAID','SALE',79.92,586.08,2,1,NULL,NULL);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction_item`
--

DROP TABLE IF EXISTS `transaction_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `barcode_digits` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `packaging_fee` double DEFAULT NULL,
  `produc_cost` double DEFAULT NULL,
  `product_real_id` bigint DEFAULT NULL,
  `product_price` double DEFAULT NULL,
  `quantity` bigint DEFAULT NULL,
  `product_id` bigint NOT NULL,
  `transaction_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKovhir4crv87qlgshhqbitn4et` (`product_id`),
  KEY `FK1wc2dvhj3oos47in473fqi3q8` (`transaction_id`),
  CONSTRAINT `FK1wc2dvhj3oos47in473fqi3q8` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`id`),
  CONSTRAINT `FKovhir4crv87qlgshhqbitn4et` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_item`
--

LOCK TABLES `transaction_item` WRITE;
/*!40000 ALTER TABLE `transaction_item` DISABLE KEYS */;
INSERT INTO `transaction_item` VALUES (1,NULL,'2023-10-08 16:42:55.116000',NULL,22,3,44,9,3,1),(2,NULL,'2023-10-08 16:43:27.052000',NULL,45,2,74,1,2,2),(3,NULL,'2023-10-08 16:43:27.058000',NULL,22,3,44,1,3,2),(4,NULL,'2023-10-08 17:08:17.746000',NULL,35,1,50,5,1,3),(5,NULL,'2023-10-08 17:08:17.754000',NULL,45,2,74,8,2,3),(6,NULL,'2023-10-08 17:36:06.564000',NULL,22,3,44,1,3,4),(8,NULL,'2023-10-09 08:16:58.218000',NULL,35,1,50,9,1,6),(9,NULL,'2023-10-09 08:18:45.341000',NULL,35,1,50,19,1,7),(11,NULL,'2023-10-09 09:18:33.068000',NULL,35,1,50,9,1,9),(12,NULL,'2023-10-09 09:38:01.977000',NULL,35,1,50,1,1,10),(14,NULL,'2023-10-09 13:41:26.178000',NULL,45,2,74,1,2,12),(15,NULL,'2023-10-09 13:41:27.418000',NULL,45,2,74,1,2,13),(16,NULL,'2023-10-09 14:11:42.273000',NULL,35,1,50,9,1,14),(17,NULL,'2023-10-09 14:11:42.306000',NULL,45,2,74,9,2,14),(18,NULL,'2023-10-09 14:11:42.311000',NULL,22,3,44,9,3,14),(19,NULL,'2023-10-09 16:31:57.510000',NULL,35,1,50,1,1,15),(20,NULL,'2023-10-09 16:36:55.346000',NULL,35,1,50,9,1,16),(21,NULL,'2023-10-09 23:55:30.708000',NULL,22,3,44,9,3,17),(22,NULL,'2023-10-09 23:58:49.930000',NULL,45,2,74,9,2,18);
/*!40000 ALTER TABLE `transaction_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_activity_log`
--

DROP TABLE IF EXISTS `user_activity_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_activity_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `action_made` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKke1yny3u25qdel1f5yby9guop` (`user_id`),
  CONSTRAINT `FKke1yny3u25qdel1f5yby9guop` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_activity_log`
--

LOCK TABLES `user_activity_log` WRITE;
/*!40000 ALTER TABLE `user_activity_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_activity_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK430om9qnxgilp5cvcbeyovi37` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1),(2,3);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-10  0:20:00
