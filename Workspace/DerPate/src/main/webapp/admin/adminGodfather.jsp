<%@page contentType="text/html" pageEncoding="UTF-8"
	import="de.db.derpate.CSRFForm"
	import="de.db.derpate.servlet.traineeOnly.GodfatherSelectServlet"
	import="de.db.derpate.util.CSRFPreventionUtil"
	import="de.db.derpate.persistence.LocationDao"
	import="de.db.derpate.model.Location"
	import="de.db.derpate.model.TeachingType"
	import="de.db.derpate.persistence.GodfatherDao"
	import="de.db.derpate.model.Job" 
	import="java.util.List" 
	import="de.db.derpate.model.Godfather"
	import="de.db.derpate.util.URIParameterEncryptionUtil"
	import="de.db.derpate.persistence.JobDao"
	import="de.db.derpate.servlet.adminOnly.GodfatherUpdateServlet"
	import="de.db.derpate.util.ServletUtil"
%>
<%
	GodfatherDao godfatherDao = new GodfatherDao();
	LocationDao locationDao = new LocationDao();
	JobDao jobDao = new JobDao();
	
%>
<jsp:include page="/WEB-INF/include/header.jsp" />
<link rel="stylesheet" type="text/css" href="include/css/admin.css" />
<script type="text/javascript" src="include/js/admin.js"></script>

<div class="container">
	<div id="results" class="row">
			<%
				List<Godfather> godfathers = godfatherDao.all();
				for (Godfather godfather : godfathers) {
					Job job = godfather.getJob();
					Location location = godfather.getLocation();
			%>
			<div class="col-lg-4">
				<div class="godfather-card card">
					<div class="card-header">
						<div class="rounded card-img-top bg-img" style="height: 100px; background-image:url('godfatherImage?godfather_id=<%= URIParameterEncryptionUtil.encrypt(godfather.getId()) %>');"></div>
					</div>	
					<form class="card-body" class="godfather-update-form">
						<div class="card-text">
							<div class="form-group row">
								<label class="col-lg-4 col-form-label">Vorname</label>
								<div class="col-lg-8">
									<input type="text" class="form-control" name="<%= GodfatherUpdateServlet.PARAMETER_GODFATHER_FIRST_NAME %>"
										value="<%=godfather.getFirstName()%>" required autocomplete="off">
								</div>
							</div>
							<div class="form-group row">
								<label class="col-lg-4 col-form-label">Nachname</label>
								<div class="col-lg-8">
									<input type="text" class="form-control" name="<%= GodfatherUpdateServlet.PARAMETER_GODFATHER_LAST_NAME %>"
										value="<%=godfather.getLastName()%>" required autocomplete="off">
								</div>
							</div>
							<div class="form-group row">
								<label class="col-lg-4 col-form-label">Geburtstag</label>
								<div class="col-lg-8">
									<input type="date" class="form-control" name="<%= GodfatherUpdateServlet.PARAMETER_GODFATHER_BIRTHDAY %>"
										value="<%=ServletUtil.replaceNullWithEmptyString(godfather.getBirthday())%>" autocomplete="off">
								</div>
							</div>
							<div class="form-group row">
								<label class="col-lg-4 col-form-label">Email</label>
								<div class="col-lg-8">
									<input type="email" class="form-control" name="<%= GodfatherUpdateServlet.PARAMETER_GODFATHER_EMAIL %>"
										value="<%=godfather.getEmail()%>" required autocomplete="off">
								</div>
							</div>
							<%
								if (location != null) {
							%>
							<div class="form-group row">
								<label class="col-lg-4 col-form-label">Standort</label>
								<div class="col-lg-8">
									<select class="form-control" name="<%= GodfatherUpdateServlet.PARAMETER_GODFATHER_LOCATION %>" required>
										<%
											for(Location oneLocation : locationDao.all()){
												if(oneLocation != null){
													%>
														<option value="<%=URIParameterEncryptionUtil.encrypt(oneLocation.getId()) %>" <%=oneLocation.equals(location) ? "selected" : "" %>><%=oneLocation.getName()%></option>
													<%
												}
											}
										%>
									</select>
								</div>
							</div>
							<%
								}
							%>
							<div class="form-group row">
								<label class="col-lg-4 col-form-label">Einstellungsdatum</label>
								<div class="col-lg-8">
									<input type="date" class="form-control" name="<%= GodfatherUpdateServlet.PARAMETER_GODFATHER_HIRING_DATE %>"
										value="<%=godfather.getHiringDate()%>" required autocomplete="off">
								</div>
							</div>
							<%
								if (job != null) {
							%>
							<div class="form-group row">
								<label class="col-lg-4 col-form-label">Job</label>
								<div class="col-lg-8">
									<select class="form-control" name="<%= GodfatherUpdateServlet.PARAMETER_GODFATHER_JOB %>" required>
										<%
											for(Job oneJob : jobDao.all()){
												if(oneJob != null){
													TeachingType teachingType = oneJob.getTeachingType();
													out.print("<option value=\"" + URIParameterEncryptionUtil.encrypt(oneJob.getId()) + "\" " + (oneJob.equals(godfather.getJob()) ? "selected" : "") + ">" + oneJob.getName() + (teachingType!=null ? " (" + teachingType.getName() + ")": "")+ "</option>"); 
												}
											}
										%>
									</select>
								</div>
							</div>
								<%
									}
								%>
							<div class="form-group row">
								<label class="col-lg-4 col-form-label">Ausbildungsjahr</label>
								<div class="col-lg-8">
									<span><%=godfather.getEducationalYear()%></span>
								</div>
							</div>
							<div class="form-group row">
								<label class="col-lg-4 col-form-label">Max. Anzahl Nachwuchskräfte</label>
								<div class="col-lg-8">
									<input type="number" class="form-control" min="<%=godfather.getCurrentNumberTrainees()%>"
										name="<%= GodfatherUpdateServlet.PARAMETER_GODFATHER_MAXTRAINEES %>"
										value="<%=godfather.getMaxTrainees()%>" required autocomplete="off">
								</div>
							</div>
							<div class="form-group row">
								<label class="col-lg-4 col-form-label">Akt. Anzahl Nachwuchskräfte</label>
								<div class="col-lg-8">
									<span><%=godfather.getCurrentNumberTrainees()%></span>
								</div>
							</div>
							<div class="form-group row">
								<label class="col-lg-4 col-form-label">Kurze Beschreibung</label>
								<div class="col-lg-8">
									<label>
										<input class="removeableText" type="checkbox" name="<%= GodfatherUpdateServlet.PARAMETER_GODFATHER_DESCRIPTION %>" value="1" />
										<span class="removeableText"><%=ServletUtil.replaceNullWithEmptyString(godfather.getDescription())%></span>
									</label>
								</div>
							</div>
							<div class="form-group row">
								<label class="col-lg-4 col-form-label">Beschreibung nach Auswahl</label>
								<div class="col-lg-8">
									<label>
										<input class="removeableText" type="checkbox" name="<%= GodfatherUpdateServlet.PARAMETER_GODFATHER_PICKTEXT %>" value="1" />
										<span class="removeableText"><%=ServletUtil.replaceNullWithEmptyString(godfather.getPickText())%></span>
									</label>
								</div>
							</div>
							<div class="form-group row">
								<label class="col-lg-4 col-form-label">Neues Passwort</label>
								<div class="col-lg-8">
									<input name="<%= GodfatherUpdateServlet.PARAMETER_GODFATHER_PASSWORD %>" type="password" autocomplete="off">
								</div>
							</div>
						</div>				
					</form>
					<div class="card-footer">
						<input class="godfather-csrf" type="hidden" name="<%= CSRFPreventionUtil.FIELD_NAME %>" value="<%= CSRFPreventionUtil.generateToken(session, CSRFForm.ADMIN_UPDATE_GODFATHER) %>">
						<input class="godfather-id" type="hidden" name="<%= GodfatherUpdateServlet.PARAMETER_GODFATHER_ID %>" value="<%= URIParameterEncryptionUtil.encrypt(godfather.getId()) %>" />
						<button class="btn btn-db godfather-update">Senden</button>
						<button class="btn btn-outline-db float-right godfather-remove" <%=godfather.getCurrentNumberTrainees()>0?"disabled":""%>>Benutzer löschen</button>
					</div>
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