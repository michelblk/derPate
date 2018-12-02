INSERT INTO `location`(`Location`) VALUES ('Frankfurt am Main');
INSERT INTO `location`(`Location`) VALUES ('Erfurt');
INSERT INTO `location`(`Location`) VALUES ('Berlin');

INSERT INTO `job`(`Job`) VALUES ('Fachinformatiker Anwendungsentwicklung');
INSERT INTO `job`(`Job`) VALUES ('Fachinformatiker Systemintegration');
INSERT INTO `job`(`Job`) VALUES ('System-IT-Kaufmann');
INSERT INTO `job`(`Job`) VALUES ('Informatik');
INSERT INTO `job`(`Job`) VALUES ('Wirtschaftsinformatik');
INSERT INTO `job`(`Job`) VALUES ('Mediendesign');

INSERT INTO `teaching_type`(`Teaching_Type`) VALUES ('Auszubildender');
INSERT INTO `teaching_type`(`Teaching_Type`) VALUES ('Dual-Student');

INSERT INTO `trainee`(`Login_Code`) VALUES ('GeilerStecher');
INSERT INTO `trainee`(`Login_Code`) VALUES ('KrasserBoy');
INSERT INTO `trainee`(`Login_Code`) VALUES ('LouisIsDumm');
INSERT INTO `trainee`(`Login_Code`) VALUES ('DerBootstrapFicka');
INSERT INTO `trainee`(`Login_Code`) VALUES ('UndDerLetzteName');

INSERT INTO `admin`(`Username`, `Password`) VALUES ('Lewis Knoll', 'microPenis');
INSERT INTO `admin`(`Username`, `Password`) VALUES ('Michel Tank', 'alt+f4');
INSERT INTO `admin`(`Username`, `Password`) VALUES ('Lukas Keller', 'admin');
INSERT INTO `admin`(`Username`, `Password`) VALUES ('admin', 'admin');

INSERT INTO `godfather`(`Email`, `Password`, `Last_Name`, `First_Name`, `Id_Location`, `Description`, `Picture`, `Id_Teaching_Type`, `Id_Job`, `Hiring_Date`, `Birthday`, `Pick_Text`) 
	VALUES ('max.mustermann@deutschebahn.com', '', 'Mustermann', 'Max', '2', 'ölkfdasdkjfakjdsf', ' ', '2', '5', '2016-10-15', '1995-11-11', '069 666666');
INSERT INTO `godfather`(`Email`, `Password`, `Last_Name`, `First_Name`, `Id_Location`, `Description`, `Picture`, `Id_Teaching_Type`, `Id_Job`, `Hiring_Date`, `Birthday`, `Pick_Text`) 
	VALUES ('lewis.stoll@deutschebahn.com', '', 'Stoll', 'Lewis', '1', 'ölaskjdfddlsow3kkdskcnm', ' ', '1', '1', '2017-08-15', '1997-06-16', '938 9374939');