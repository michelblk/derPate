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
	import="de.db.derpate.model.Job" 
	import="de.db.derpate.Constants"
	import="java.util.List"%>

<%
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
		<form id="filtering-form">
			<div class="btn-group" role="group">
				<div class="btn-group" role="group">
					<button type="button" class="btn btn-secondary dropdown-toggle"
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
					<button type="button" class="btn btn-secondary dropdown-toggle"
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
					<button type="button" class="btn btn-secondary dropdown-toggle"
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
						<button type="button" class="btn btn-secondary dropdown-toggle"
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
				<input class="btn btn-secondary" id="filtering-form-submit"
					type="submit" value="Suchen" />
			</div>
		</form>
			</div>
	<div id="results">
		<div class="godfather-card card default" id="godfahter-card-default" data-description="">
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
					<button class="btn btn-db more-info-godfather-button" >Show more</button>
				</div>
			</div>
		</div>
		<!-- show all -->
		<div id="async-results"></div>
		<div class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true" id="more-info-godfather">
  			<div class="modal-dialog modal-lg modal-dialog-centered">
    	 <div class="modal-content">
      <div class="modal-header">
       	 <h5 class="modal-title" id="exampleModalLongTitle">Modal title</h5>
        	<button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <table class="table">
  <tbody>
    <tr>
      <th scope="row">Name</th>
      <td>xx</td>

    </tr>
    <tr>
      <th scope="row">Tätigkeitsstelle</th>
      <td>xx</td>
    </tr>
        <tr>
      <th scope="row"> Ausbildungart</th>
      <td>xx</td>
    </tr>
        <tr>
      <th scope="row">Job</th>
      <td>xx</td>
    </tr>
        <tr>
      <th scope="row">Ausbildungsjahr</th>
      <td>xx</td>
    </tr>
        <tr>
      <th scope="row">Alter</th>
      <td>xx</td>
    </tr>
        <tr>
      <th scope="row">Beschreibung</th>
      <td>xx</td>
    </tr>
    </tbody>
    </table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>
	</div>
</div>

<jsp:include page="/WEB-INF/include/footer.jsp" />
