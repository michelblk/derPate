<%@page contentType="text/html" pageEncoding="UTF-8"
	import="de.db.derpate.CSRFForm"
	import="de.db.derpate.model.LoginUser"
	import="de.db.derpate.model.Trainee"
	import="de.db.derpate.manager.LoginManager"
	import="de.db.derpate.servlet.GodfatherFilterServlet"
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
	import="java.util.List"%>

<%
	LoginUser user = LoginManager.getInstance().getUserBySession(session);
	if(!(user instanceof Trainee) || ((Trainee)user).getGodfather() != null) {
		response.sendRedirect("../redirect");
		return;
	}

	LocationDao locationDao = new LocationDao();
	TeachingTypeDao teachingTypeDao = new TeachingTypeDao();
	JobDao jobDao = new JobDao();
	GodfatherDao godfatherDao = new GodfatherDao();
%>
<%-- TODO selectionstatus check --%>
<jsp:include page="/WEB-INF/include/header.jsp" />
<link rel="stylesheet" type="text/css" href="include/css/filter.css" />
<script src="include/js/filter.js"></script>



<div class="container">
	<div class="form-group text-center">
		<form id="filtering-form" class="filtermenue">
			<div class="btn-group" role="group">
				<div class="btn-group" role="group">
					<button type="button" class="btn btn-db btn-secondary dropdown-toggle"
						data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						Standort</button>
					<ul class="dropdown-menu">
						<%
							List<Location> locations = locationDao.all();
							for (Location location : locations) {
						%>
						<li><input type="checkbox"
							name="<%=GodfatherFilterServlet.FILTER_PARAM_LOCATION%>"
							value="<%=URIParameterEncryptionUtil.encrypt(location.getId())%>" />
							<%=location.getName()%></li>
						<%
							}
						%>
					</ul>
				</div>

				<div class="btn-group" role="group">
					<button type="button" class="btn btn-db btn-secondary dropdown-toggle"
						data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						Ausbildungsform</button>
					<ul class="dropdown-menu">
						<%
							List<TeachingType> teachingTypes = teachingTypeDao.all();
							for (TeachingType teachingType : teachingTypes) {
						%>
						<li><input type="checkbox"
							name="<%=GodfatherFilterServlet.FILTER_PARAM_TEACHING_TYPE%>"
							value="<%=URIParameterEncryptionUtil.encrypt(teachingType.getId())%>" />
							<%=teachingType.getName()%></li>
						<%
							}
						%>
					</ul>
				</div>

				<div class="btn-group" role="group">
					<button type="button" class="btn btn-db btn-secondary dropdown-toggle"
						data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						Beruf</button>
					<ul class="dropdown-menu">
						<%
							List<Job> jobs = jobDao.all();
							for (Job job : jobs) {
						%>
						<li><input type="checkbox"
							name="<%=GodfatherFilterServlet.FILTER_PARAM_JOB%>"
							value="<%=URIParameterEncryptionUtil.encrypt(job.getId())%>" />
							<%=job.getName()%></li>
						<%
							}
						%>
					</ul>
					<div class="btn-group" role="group">
						<button type="button" class="btn btn-db btn-secondary dropdown-toggle"
							data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							Lehrjahr</button>
						<ul class="dropdown-menu">
							<%
								List<@NonNull Integer> educationalYears = godfatherDao.getEducationalYears();
								for(@NonNull Integer year : educationalYears) { %>
							<li><input type="checkbox"
								name="<%= GodfatherFilterServlet.FILTER_PARAM_EDUCATIONAL_YEAR %>"
								value="<%= URIParameterEncryptionUtil.encrypt(year) %>" /> <%= year %>
							</li>
							<% } %>
						</ul>
					</div>
				</div>
				<input class="btn btn-db btn-secondary" id="filtering-form-submit"
					type="submit" value="Suchen" />
			</div>
		</form>
			</div>
	<div id="results">
		<div class="godfather-card card default" id="godfahter-card-default" >
			<div class="card-img-top godfather-card-image"></div>
			<div class="card-body">
				<h5 class="card-title godfather-card-firstname">Vorname</h5>
				<div class="card-text">
					<div class="godfather-card-location">Ort</div>
					<div class="godfather-card-teachingType">Ausbildung/Duales
						Studium</div>
					<div class="godfather-card-job">Beruf</div>
					<input type="hidden" class="godfather-card-educationalyear">
					<input type="hidden" class="godfather-card-age">
					<input type="hidden" class="godfather-card-description">
					<button class="btn btn-db more-info-godfather-button" >Mehr anzeigen</button>
				</div>
			</div>
		</div>
		<!-- show all -->
		<div id="async-results"></div>
		<div class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true" id="more-info-godfather">
  			<div class="modal-dialog modal-lg modal-dialog-centered">
    	 <div class="modal-content">
      <div class="modal-header">
       	 <h5 class="modal-title" id="exampleModalLongTitle">Mehr Informationen</h5>
        	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <table class="table">
  <tbody>
    <tr>
      <th>Name</th>
      <td class="godfather-card-firstname"></td>
    </tr>
      <tr>
      <th>Alter</th>
      <td class="godfather-card-age"></td>
    </tr>
    <tr>
      <th>Tätigkeitsstelle</th>
      <td class="godfather-card-location"></td>
    </tr>
        <tr>
      <th> Ausbildungsart</th>
      <td class="godfather-card-teachingType"></td>
    </tr>
        <tr>
      <th>Job</th>
      <td class ="godfather-card-job"></td>
    </tr>
        <tr>
      <th>Ausbildungsjahr</th>
      <td class="godfather-card-educationalyear"></td>
    </tr>
    
        <tr>
      <th>Beschreibung</th>
      <td class="godfather-card-description"></td>
    </tr>
    </tbody>
    </table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-outline-db" data-dismiss="modal">Schließen</button>
						<form class="godfahter-card-select-form" action="godfatherSelect" method="POST">
							<input type="hidden" class="godfather-card-select-csrf" name="<%= CSRFPreventionUtil.FIELD_NAME %>" value="<%= CSRFPreventionUtil.generateToken(session, CSRFForm.TRAINEE_SELECT_GODFATHER) %>" />
							<input type="hidden" class="godfather-card-select-id" name="<%= GodfatherSelectServlet.PARAM_GODFAHTER_ID %>" value="">
							<input type="submit" class="btn btn-db godfather-card-select-btn" value="Als Paten ausw&auml;hlen"/>
						</form>
      </div>
    </div>
  </div>
</div>
	</div>
</div>

<jsp:include page="/WEB-INF/include/footer.jsp" />
