<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" 
	import="de.db.derpate.manager.LoginManager"
	import="de.db.derpate.model.LoginUser"
	import="de.db.derpate.Usermode"
	import="de.db.derpate.model.Trainee"%>
<%
LoginUser user = LoginManager.getInstance().getUserBySession(session);
Usermode usermode = LoginManager.getInstance().getUsermode(user);
%>
<!DOCTYPE html>
<html lang="de">
	<head>
		<title>DerPate</title>
		<meta charset="utf-8">
		<base href="<%=request.getContextPath() %>/">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link rel="shortcut icon" type="image/x-icon" href="include/images/favicon.ico"  />
		<link rel="stylesheet" type="text/css" href="include/bootstrap/css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="include/css/footer.css" />
		<link rel="stylesheet" type="text/css" href="include/css/styles.css" />
		<link rel="stylesheet" type="text/css" href="include/css/menuebar.css" />
		<script src="include/bootstrap/js/jquery-3.3.1.min.js"></script>
		<script src="include/bootstrap/js/popper.min.js"></script>
		<script src="include/bootstrap/js/bootstrap.min.js"></script>
	</head>
	<body>
	<nav class="navbar navbar-expand-sm menuebar">
		<div class="container-fluid">
			<ul class="text-center">
				<% if(usermode == Usermode.ADMIN) { %>
					<li>
						<a class="nav-link" href="admin/adminGodfather.jsp">Patenverwaltung</a>
					 </li>
					 <li>
						<a class="nav-link" href="admin/adminAddGodfather.jsp">Paten hinzufügen</a>
					 </li>
					 <li>
					 	<a class="nav-link" href="admin/adminTrainee.jsp">Nachwuchskr&auml;fteverwaltung</a>
					 </li>
				<% } else if (usermode == Usermode.GODFATHER) { %>
					<li>
						<a class="nav-link" href="godfather/godfatherUpdate.jsp">Profil</a>
					</li>
				<% } else if (usermode == Usermode.TRAINEE) { 
					if(user != null && ((Trainee)user).getGodfather() == null) {
				%>
					<li>
						<a class="nav-link" href="trainee/welcome.jsp">Willkommen</a>
					</li>
					<li>
						<a class="nav-link" href="trainee/filter.jsp">Paten w&auml;hlen</a>
					</li>
				<% 	}
				   }%>
			</ul>
			<a href="redirect">
				<img src="include/images/db_logo.svg" class="float-right image">
			</a>	
		</div>
	</nav>
	