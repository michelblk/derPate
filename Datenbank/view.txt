CREATE VIEW v_Godfather 
AS SELECT Id_Godfather, 
	g.Email, 
	g.Password, 
	g.Last_Name, 
	g.First_Name, 
	g.Id_Location, 
	g.Description, 
	g.Picture, 
	g.Id_Teaching_Type, 
	g.Id_Job, 
	g.Hiring_Date, 
	g.Birthday, 
	g.Pick_Text
FROM Godfather g;

CREATE VIEW v_Login_Trainee
AS SELECT t.Id_Trainee,
	t.Login_Code,
	t.Id_Godfather
FROM Trainee t;

CREATE VIEW v_Login_Admin
AS SELECT a.Id_Admin,
	a.Username,
	a.Password
FROM Admin a;

CREATE VIEW v_Login_Godfather
AS SELECT g.Id_Godfather,
	g.Email,
	g.Password
FROM Godfather g;