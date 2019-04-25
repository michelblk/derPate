<%@page
	contentType="text/html" pageEncoding="UTF-8"
	import="de.db.derpate.CSRFForm"
	import="de.db.derpate.servlet.traineeOnly.GodfatherFilterServlet"
	import="de.db.derpate.servlet.traineeOnly.GodfatherSelectServlet"
	import="de.db.derpate.util.CSRFPreventionUtil"
	import="de.db.derpate.persistence.LocationDao"
	import="de.db.derpate.model.Location"
	import="de.db.derpate.util.URIParameterEncryptionUtil"
	import="de.db.derpate.persistence.TeachingTypeDao"
	import="de.db.derpate.model.TeachingType"
	import="de.db.derpate.persistence.JobDao"
	import="org.eclipse.jdt.annotation.NonNull"
	import="de.db.derpate.persistence.GodfatherDao"
	import="de.db.derpate.model.Job"
	import="de.db.derpate.Constants"
	import="java.util.List"
%>
<%
	LocationDao locationDao = new LocationDao();
	TeachingTypeDao teachingTypeDao = new TeachingTypeDao();
	JobDao jobDao = new JobDao();
	GodfatherDao godfatherDao = new GodfatherDao();
%>

<jsp:include page="/WEB-INF/include/header.jsp" />	
	<nav class="navbar navbar-expand-sm menuebar">
		<div class="container-fluid">
			<ul class="text-center">
				<li>
					<a class="nav-link" href="admin/adminGodfather.jsp">Paten</a>
				 </li>
				<li>
					<a class="nav-link" href="admin/adminWelcome.jsp">Tokengenerator</a>
				</li>
			</ul>
		</div>
	</nav>
	
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
									List<Location> locations = locationDao.all();
									for(Location location : locations) {
								%>
									<li>
										<input type="checkbox" name="<%=GodfatherFilterServlet.FILTER_PARAM_LOCATION%>" value="<%=URIParameterEncryptionUtil.encrypt(location.getId())%>" />
										<%=location.getName()%>
									</li>
								<%
									}
								%>
							</ul>
						</div>
						
						<div class="btn-group" role="group">
							<button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								Ausbildungsform
							</button>
							<ul class="dropdown-menu">
								<%
									List<TeachingType> teachingTypes = teachingTypeDao.all();
									for(TeachingType teachingType : teachingTypes) {
								%>
									<li>
										<input type="checkbox" name="<%=GodfatherFilterServlet.FILTER_PARAM_TEACHING_TYPE%>" value="<%=URIParameterEncryptionUtil.encrypt(teachingType.getId())%>" />
										<%=teachingType.getName()%>
									</li>
								<%
									}
								%>
							</ul>
						</div>
						
						<div class="btn-group" role="group">
							<button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								Beruf
							</button>
							<ul class="dropdown-menu">
								<%
									List<Job> jobs = jobDao.all();
									for(Job job : jobs) {
								%>
									<li>
										<input type="checkbox" name="<%=GodfatherFilterServlet.FILTER_PARAM_JOB%>" value="<%=URIParameterEncryptionUtil.encrypt(job.getId())%>" />
										<%=job.getName()%>
									</li>
								<%
									}
								%>
							</ul>
						</div>
						
						<div class="btn-group" role="group">
							<button type="button" class="btn btn-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								Lehrjahr
							</button>
							<ul class="dropdown-menu">
								<%
									List<@NonNull Integer> educationalYears = godfatherDao.getEducationalYears();
									for(@NonNull Integer year : educationalYears) {
								%>
									<li>
										<input type="checkbox" name="<%=GodfatherFilterServlet.FILTER_PARAM_EDUCATIONAL_YEAR%>" value="<%= URIParameterEncryptionUtil.encrypt(year) %>" />
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
							<div class="godfather-card-age">Alter</div>
							<blockquote class="blockquote text-center">
								<p class="godfather-card-description mb-0 text-small"></p>
								<footer class="blockquote-footer godfather-card-firstname"></footer>
							</blockquote>
						</div>
					</div>
					<div class="card-footer"> 
						<form class="godfahter-card-select-form" action="../godfatherSelect" method="POST">
							<input type="hidden" class="godfather-card-select-csrf" name="<%= CSRFPreventionUtil.FIELD_NAME %>" value="<%= CSRFPreventionUtil.generateToken(session, CSRFForm.TRAINEE_SELECT_GODFATHER) %>" />
							<input type="hidden" class="godfather-card-select-id" name="<%= GodfatherSelectServlet.PARAM_GODFAHTER_ID %>" value="">
							<input type="submit" class="btn" value="DELETE">
						</form>
					</div>
				</div>
				<!-- show all -->
				<div id="async-results">
				
				</div>
			</div>
		</div>

<jsp:include page="/WEB-INF/include/footer.jsp" />