CREATE DATABASE derPate;

CREATE TABLE Admin (
  Id_Admin int(10) NOT NULL AUTO_INCREMENT,
  Username varchar(50) NOT NULL,
  Password varchar(500) NOT NULL,
  PRIMARY KEY (Id_ADMIN)  
);

CREATE TABLE Location (
  Id_Location int(10) NOT NULL AUTO_INCREMENT,
  Location varchar(50) NOT NULL,
  PRIMARY KEY (Id_Location)  
);

CREATE TABLE Teaching_Type (
  Id_Teaching_Type int(10) NOT NULL AUTO_INCREMENT,
  Teaching_Type varchar(50) NOT NULL,
  PRIMARY KEY (Id_Teaching_Type)  
);

CREATE TABLE Job (
  Id_Job int(10) NOT NULL AUTO_INCREMENT,
  Job varchar(50) NOT NULL,
  PRIMARY KEY (Id_Job)  
);

CREATE TABLE Godfather (
  Id_Godfather int(10) NOT NULL AUTO_INCREMENT,
  Email varchar(50) NOT NULL,
  Password varchar(500) NOT NULL,
  Last_Name varchar(50) NOT NULL,
  First_Name varchar(50) NOT NULL,
  Id_Location int(10) NOT NULL,
  Description varchar(5000) NOT NULL, 
  Picture varchar(20) NOT NULL,
  Id_Teaching_Type int(10) NOT NULL,
  Id_Job int(10) NOT NULL,
  Hiring_Date date NOT NULL,
  Birthday date,
  Pick_Text varchar(500),
  
  PRIMARY KEY (Id_Godfather),
  FOREIGN KEY (Id_Location) REFERENCES Location(Id_Location),
  FOREIGN KEY (Id_Teaching_Type) REFERENCES Teaching_Type(Id_Teaching_Type),
  FOREIGN KEY (Id_Job) REFERENCES Job(Id_Job)
);

CREATE TABLE Trainee (
  Id_Trainee int (10) NOT NULL AUTO_INCREMENT,
  Login_Code varchar(50) NOT NULL,
  Id_Godfather int(10),
  
  PRIMARY KEY (Id_Trainee),
  FOREIGN KEY (Id_Godfather) REFERENCES Godfather(Id_Godfather)
);