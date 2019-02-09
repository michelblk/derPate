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

--
-- TRUNCATE Tabelle vor dem Einfügen `admin`
--

TRUNCATE TABLE `admin`;
--
-- Daten für Tabelle `admin`
--

INSERT INTO `admin` (`Id_Admin`, `Email`, `Password`) VALUES
(1, 'admin@deutschebahn.com', 'XzH4VPVLC/c3vRnhJ8vx9CYRJhdz4pacYuX6+knyDxzhLQ5HB1gCEfEGlzkxX0fOn5tZ8P3Y3MRmvwbr3OJNgA==.uBfDxuLMQskQSK6HqgVN0dnryJqLLL2yRsgSiEvlstQ=');

--
-- TRUNCATE Tabelle vor dem Einfügen `godfather`
--

TRUNCATE TABLE `godfather`;
--
-- Daten für Tabelle `godfather`
--

INSERT INTO `godfather` (`Id_Godfather`, `Email`, `Password`, `Last_Name`, `First_Name`, `Id_Location`, `Max_Trainees`, `Description`, `Picture`, `Id_Job`, `Hiring_Date`, `Birthday`, `Pick_Text`) VALUES
(1, 'max.mustermann@deutschebahn.com', 'nb1nCk5vdbTSUKMqEht/TjVbpDKXlesDhUlxJMvK2PqLtYeso2avUmZITgfs4KH0jH4MAsNJKyV56E9Oy7oAvA==.R9nAAviFiQyLJP7efpyjYUJRJ/aVAa59d8tmmKJx2iY=', 'Mustermann', 'Max', 2, 1, 'Hallo,\r\nich bin Max und beantworte gern Deine Fragen rund um das Thema Duales Studium bei der Deutschen Bahn.', ' ', 5, '2016-10-15', '1995-11-01', 'Ich freue mich, dass Du dich für mich entschieden hast.\r\nDu kannst mich gern über die oben stehende E-Mail-Adresse oder unter der Telefonnummer 069 666666 kontaktieren.'),
(2, 'erika.mustermann@deutschebahn.com', 'IHmWGugFPGLwjUaWuE9Yqo2N1R4I/59nt9kApo4vDj5ZSklUggSvLNsSrtJRuVO25z4Obvf9V7JTsB4C0Aboyw==.QUWO8LhBpJegQXoHxh4a6Zr1f0qgBvBeRmzEjy5jn1s=', 'Mustermann', 'Erika', 1, 1, 'Hallo,\r\nich heiße Erika und bin Auszubildende Fachinformatikerin für Anwendungsentwicklung.\r\nIch freue mich, dass Du dich für die DB entschieden hast.', ' ', 1, '2017-08-15', '1997-06-16', 'Danke, dass Du dich für mich entschieden hast.\r\nHier ist meine Telefonnummer 069 666666.'),
(3, 'erik.mustermann@deutschebahn.com', 'nb1nCk5vdbTSUKMqEht/TjVbpDKXlesDhUlxJMvK2PqLtYeso2avUmZITgfs4KH0jH4MAsNJKyV56E9Oy7oAvA==.R9nAAviFiQyLJP7efpyjYUJRJ/aVAa59d8tmmKJx2iY=', 'Mustermann', 'Erik', 3, 3, 'Hallo, ich bin Erik :)', '', 3, '2019-02-01', NULL, NULL);

--
-- TRUNCATE Tabelle vor dem Einfügen `job`
--

TRUNCATE TABLE `job`;
--
-- Daten für Tabelle `job`
--

INSERT INTO `job` (`Id_Job`, `Teaching_type`, `Job`) VALUES
(1, 1, 'Fachinformatiker Anwendungsentwicklung'),
(2, 1, 'Fachinformatiker Systemintegration'),
(3, 1, 'IT-System-Kaufmann'),
(4, 2, 'Informatik'),
(5, 2, 'Wirtschaftsinformatik'),
(6, 2, 'Mediendesign');

--
-- TRUNCATE Tabelle vor dem Einfügen `location`
--

TRUNCATE TABLE `location`;
--
-- Daten für Tabelle `location`
--

INSERT INTO `location` (`Id_Location`, `Location`) VALUES
(1, 'Frankfurt am Main'),
(2, 'Erfurt'),
(3, 'Berlin');

--
-- TRUNCATE Tabelle vor dem Einfügen `teaching_type`
--

TRUNCATE TABLE `teaching_type`;
--
-- Daten für Tabelle `teaching_type`
--

INSERT INTO `teaching_type` (`Id_Teaching_Type`, `Teaching_Type`) VALUES
(1, 'Auszubildender'),
(2, 'Dual-Student');

--
-- TRUNCATE Tabelle vor dem Einfügen `trainee`
--

TRUNCATE TABLE `trainee`;
--
-- Daten für Tabelle `trainee`
--

INSERT INTO `trainee` (`Id_Trainee`, `Login_Code`, `Id_Godfather`) VALUES
(1, 'Trainee1', 2),
(2, 'Trainee2', NULL),
(3, 'Trainee3', NULL),
(4, 'Trainee4', NULL),
(5, 'Trainee5', NULL);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
