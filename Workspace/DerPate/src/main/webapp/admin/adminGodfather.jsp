<%@page contentType="text/html" pageEncoding="UTF-8"
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
	import="de.db.derpate.model.Job" import="de.db.derpate.Constants"
	import="java.util.List" import="de.db.derpate.model.Godfather"
	import="de.db.derpate.persistence.GodfatherDao"%>
<%
	GodfatherDao godfatherDao = new GodfatherDao();
%>

<jsp:include page="/WEB-INF/include/header.jsp" />
<script src="filter.js"></script>
<nav class="navbar navbar-expand-sm menuebar">
	<div class="container-fluid">
		<ul class="text-center">
			<li><a class="nav-link" href="admin/adminGodfather.jsp">Paten</a>
			</li>
			<li><a class="nav-link" href="admin/adminWelcome.jsp">Tokengenerator</a>
			</li>
		</ul>
	</div>
</nav>

<div class="container">
	<div id="results">
		<%
			List<Godfather> godfathers = godfatherDao.all();
			for (Godfather godfather : godfathers) {
		%>

		<div class="godfather-card card">
			<div class="card-img-top godfather-card-image"></div>
			<div class="card-body">
				<h5 class="card-title godfather-card-firstname"><%=godfather.getFirstName()%></h5>
				<div class="card-text">
					<div class="godfather-card-location">Ort</div>
					<div class="godfather-card-teachingType">Ausbildung/Duales
						Studium</div>
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
				<form class="godfahter-card-select-form" action="../godfatherSelect"
					method="POST">
					<input type="hidden" class="godfather-card-select-csrf"
						name="<%=CSRFPreventionUtil.FIELD_NAME%>"
						value="<%=CSRFPreventionUtil.generateToken(session, CSRFForm.TRAINEE_SELECT_GODFATHER)%>" />
					<input type="hidden" class="godfather-card-select-id"
						name="<%=GodfatherSelectServlet.PARAM_GODFAHTER_ID%>" value="">
					<input type="submit" class="btn" value="DELETE">
				</form>
			</div>
		</div>

		<%
			}
		%>
	</div>
	<!-- show all 
				<div id="async-results">
				
				</div>-->
</div>

<jsp:include page="/WEB-INF/include/footer.jsp" />