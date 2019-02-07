<%@page
	contentType="text/html" pageEncoding="UTF-8"
	import="de.db.derPate.servlet.traineeOnly.GodfatherServlet"
	import="de.db.derPate.persistence.LocationDao"
	import="de.db.derPate.model.Location"
	import="de.db.derPate.util.URIParameterEncryptionUtil"
	import="de.db.derPate.persistence.TeachingTypeDao"
	import="de.db.derPate.model.TeachingType"
	import="de.db.derPate.persistence.JobDao"
	import="org.eclipse.jdt.annotation.NonNull"
	import="de.db.derPate.persistence.GodfatherDao"
	import="de.db.derPate.model.Job"
	import="de.db.derPate.Constants"
	import="java.util.List"
%>
<%-- TODO loginstatus check --%>
<!DOCTYPE html>
<html>
	<head>
		<title>Filter Example</title>
		<meta charset="<%= Constants.CHARSET.name() %>" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link rel="stylesheet" type="text/css" href="https://getbootstrap.com/docs/4.0/dist/css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="filter.css" />
		<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
		<script src="https://getbootstrap.com/docs/4.0/assets/js/vendor/popper.min.js"></script>
		<script src="https://getbootstrap.com/docs/4.0/dist/js/bootstrap.min.js"></script>
		<script src="filter.js"></script>
	</head>
	<body>
		<div class="container">
			<div class="form-group text-center">
				<form id="filtering-form">
					<div class="btn-group" role="group">
						<div class="btn-group" role="group">
							<button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								Standort
							</button>
							<ul class="dropdown-menu">
								<%
								List<Location> locations = LocationDao.getInstance().list();
								for(Location location : locations) { %>
									<li>
										<input type="checkbox" name="<%= GodfatherServlet.FILTER_PARAM_LOCATION %>" value="<%= URIParameterEncryptionUtil.encrypt(location.getId()) %>" />
										<%= location.getLocation() %>
									</li>
								<% } %>
							</ul>
						</div>
						
						<div class="btn-group" role="group">
							<button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								Ausbildungsform
							</button>
							<ul class="dropdown-menu">
								<%
								List<TeachingType> teachingTypes = TeachingTypeDao.getInstance().list();
								for(TeachingType teachingType : teachingTypes) { %>
									<li>
										<input type="checkbox" name="<%= GodfatherServlet.FILTER_PARAM_TEACHING_TYPE %>" value="<%= URIParameterEncryptionUtil.encrypt(teachingType.getId()) %>" />
										<%= teachingType.getTeachingType() %>
									</li>
								<% } %>
							</ul>
						</div>
						
						<div class="btn-group" role="group">
							<button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								Beruf
							</button>
							<ul class="dropdown-menu">
								<%
								List<Job> jobs = JobDao.getInstance().list();
								for(Job job : jobs) { %>
									<li>
										<input type="checkbox" name="<%= GodfatherServlet.FILTER_PARAM_JOB %>" value="<%= URIParameterEncryptionUtil.encrypt(job.getId()) %>" />
										<%= job.getJob() %>
									</li>
								<% } %>
							</ul>
						</div>
						
						<div class="btn-group" role="group">
							<button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								Lehrjahr
							</button>
							<ul class="dropdown-menu">
								<%
								List<@NonNull Integer> educationalYears = GodfatherDao.getEducationalYears();
								for(@NonNull Integer year : educationalYears) { %>
									<li>
										<input type="checkbox" name="<%= GodfatherServlet.FILTER_PARAM_EDUCATIONAL_YEAR %>" value="<%= URIParameterEncryptionUtil.encrypt(year) %>" />
										<%= year %>
									</li>
								<% } %>
							</ul>
						</div>
						
						<input class="btn btn-secondary" id="filtering-form-submit" type="submit" value="Suchen" />
					</div>
				</form>
			</div>
			<div id="results">
				<div class="godfather-card card default" id ="godfahter-card-default">
					<div class="card-img-top godfather-card-image"></div>
					<div class="card-body">
						<h5 class="card-title godfather-card-firstname">Vorname</h5>
						<div class="card-text">
							<div class="godfather-card-location">Ort</div>
							<div class="godfather-card-teachingType">Ausbildung/Duales Studium</div>
							<div class="godfather-card-job">Beruf</div>
							<div class="godfather-card-year">Lehrjahr</div>
							<blockquote class="blockquote text-center">
								<p class="godfather-card-description mb-0 text-small"></p>
								<footer class="blockquote-footer godfather-card-firstname"></footer>
							</blockquote>
						</div>
					</div>
				</div>
				<!-- show all -->
				<div id="async-results">
				
				</div>
			</div>
		</div>
	</body>
</html>