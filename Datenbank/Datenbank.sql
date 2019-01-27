-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 27. Jan 2019 um 18:07
-- Server-Version: 10.1.37-MariaDB
-- PHP-Version: 7.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
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

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `Id_Admin` int(10) NOT NULL AUTO_INCREMENT,
  `Email` varchar(50) NOT NULL,
  `Password` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`Id_Admin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `godfather`
--

DROP TABLE IF EXISTS `godfather`;
CREATE TABLE IF NOT EXISTS `godfather` (
  `Id_Godfather` int(10) NOT NULL AUTO_INCREMENT,
  `Email` varchar(50) NOT NULL,
  `Password` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `Last_Name` varchar(50) NOT NULL,
  `First_Name` varchar(50) NOT NULL,
  `Id_Location` int(10) NOT NULL,
  `Max_Trainees` int(2) NOT NULL,
  `Description` varchar(5000) NOT NULL,
  `Picture` varchar(20) NOT NULL,
  `Id_Teaching_Type` int(10) NOT NULL,
  `Id_Job` int(10) NOT NULL,
  `Hiring_Date` date NOT NULL,
  `Birthday` date DEFAULT NULL,
  `Pick_Text` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`Id_Godfather`),
  KEY `Id_Location` (`Id_Location`),
  KEY `Id_Teaching_Type` (`Id_Teaching_Type`),
  KEY `Id_Job` (`Id_Job`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `job`
--

DROP TABLE IF EXISTS `job`;
CREATE TABLE IF NOT EXISTS `job` (
  `Id_Job` int(10) NOT NULL AUTO_INCREMENT,
  `Job` varchar(50) NOT NULL,
  PRIMARY KEY (`Id_Job`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `location`
--

DROP TABLE IF EXISTS `location`;
CREATE TABLE IF NOT EXISTS `location` (
  `Id_Location` int(10) NOT NULL AUTO_INCREMENT,
  `Location` varchar(50) NOT NULL,
  PRIMARY KEY (`Id_Location`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `teaching_type`
--

DROP TABLE IF EXISTS `teaching_type`;
CREATE TABLE IF NOT EXISTS `teaching_type` (
  `Id_Teaching_Type` int(10) NOT NULL AUTO_INCREMENT,
  `Teaching_Type` varchar(50) NOT NULL,
  PRIMARY KEY (`Id_Teaching_Type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `trainee`
--

DROP TABLE IF EXISTS `trainee`;
CREATE TABLE IF NOT EXISTS `trainee` (
  `Id_Trainee` int(10) NOT NULL AUTO_INCREMENT,
  `Login_Code` varchar(50) NOT NULL,
  `Id_Godfather` int(10) DEFAULT NULL,
  PRIMARY KEY (`Id_Trainee`),
  KEY `Id_Godfather` (`Id_Godfather`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `godfather`
--
ALTER TABLE `godfather`
  ADD CONSTRAINT `godfather_ibfk_1` FOREIGN KEY (`Id_Location`) REFERENCES `location` (`Id_Location`),
  ADD CONSTRAINT `godfather_ibfk_2` FOREIGN KEY (`Id_Teaching_Type`) REFERENCES `teaching_type` (`Id_Teaching_Type`),
  ADD CONSTRAINT `godfather_ibfk_3` FOREIGN KEY (`Id_Job`) REFERENCES `job` (`Id_Job`);

--
-- Constraints der Tabelle `trainee`
--
ALTER TABLE `trainee`
  ADD CONSTRAINT `trainee_ibfk_1` FOREIGN KEY (`Id_Godfather`) REFERENCES `godfather` (`Id_Godfather`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
