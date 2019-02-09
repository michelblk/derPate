-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 09. Feb 2019 um 21:45
-- Server-Version: 10.1.21-MariaDB
-- PHP-Version: 7.0.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `derpate`
--
CREATE DATABASE IF NOT EXISTS `derpate` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `derpate`;

DELIMITER $$
--
-- Funktionen
--
DROP FUNCTION IF EXISTS `YEAR_DIFF`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `YEAR_DIFF` (`date` DATE) RETURNS INT(11) RETURN TIMESTAMPDIFF(YEAR, date, CURDATE())$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `Id_Admin` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Email` varchar(50) NOT NULL,
  `Password` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`Id_Admin`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `godfather`
--

DROP TABLE IF EXISTS `godfather`;
CREATE TABLE IF NOT EXISTS `godfather` (
  `Id_Godfather` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Email` varchar(50) NOT NULL,
  `Password` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `Last_Name` varchar(50) NOT NULL,
  `First_Name` varchar(50) NOT NULL,
  `Id_Location` int(10) UNSIGNED NOT NULL,
  `Max_Trainees` int(2) NOT NULL,
  `Description` varchar(5000) NOT NULL,
  `Picture` varchar(20) DEFAULT NULL,
  `Id_Job` int(10) UNSIGNED NOT NULL,
  `Hiring_Date` date NOT NULL,
  `Birthday` date DEFAULT NULL,
  `Pick_Text` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`Id_Godfather`),
  KEY `Id_Location` (`Id_Location`),
  KEY `Id_Job` (`Id_Job`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `job`
--

DROP TABLE IF EXISTS `job`;
CREATE TABLE IF NOT EXISTS `job` (
  `Id_Job` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Teaching_type` int(10) UNSIGNED NOT NULL,
  `Job` varchar(50) NOT NULL,
  PRIMARY KEY (`Id_Job`),
  KEY `job_teachingtypefk_1` (`Teaching_type`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `location`
--

DROP TABLE IF EXISTS `location`;
CREATE TABLE IF NOT EXISTS `location` (
  `Id_Location` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Location` varchar(50) NOT NULL,
  PRIMARY KEY (`Id_Location`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `teaching_type`
--

DROP TABLE IF EXISTS `teaching_type`;
CREATE TABLE IF NOT EXISTS `teaching_type` (
  `Id_Teaching_Type` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Teaching_Type` varchar(50) NOT NULL,
  PRIMARY KEY (`Id_Teaching_Type`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `trainee`
--

DROP TABLE IF EXISTS `trainee`;
CREATE TABLE IF NOT EXISTS `trainee` (
  `Id_Trainee` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Login_Code` varchar(50) NOT NULL,
  `Id_Godfather` int(10) UNSIGNED DEFAULT NULL,
  PRIMARY KEY (`Id_Trainee`),
  KEY `Id_Godfather` (`Id_Godfather`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `godfather`
--
ALTER TABLE `godfather`
  ADD CONSTRAINT `godfather_ibfk_1` FOREIGN KEY (`Id_Location`) REFERENCES `location` (`Id_Location`),
  ADD CONSTRAINT `godfather_ibfk_2` FOREIGN KEY (`Id_Job`) REFERENCES `job` (`Id_Job`);

--
-- Constraints der Tabelle `job`
--
ALTER TABLE `job`
  ADD CONSTRAINT `job_teachingtypefk_1` FOREIGN KEY (`Teaching_type`) REFERENCES `teaching_type` (`Id_Teaching_Type`);

--
-- Constraints der Tabelle `trainee`
--
ALTER TABLE `trainee`
  ADD CONSTRAINT `trainee_ibfk_1` FOREIGN KEY (`Id_Godfather`) REFERENCES `godfather` (`Id_Godfather`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
