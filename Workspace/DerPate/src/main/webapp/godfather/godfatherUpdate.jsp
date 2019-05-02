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
	import="de.db.derpate.manager.LoginManager"
	import="de.db.derpate.servlet.godfatherOnly.GodfatherUpdateServlet"
	import="de.db.derpate.servlet.ServletParameter"
	
%>

<%
	LocationDao locationDao = new LocationDao();
	JobDao jobDao = new JobDao();
	Godfather godfather = LoginManager.getInstance().getUserBySession(session);

	
	
%>

<jsp:include page="/WEB-INF/include/header.jsp" />
<link rel="stylesheet" type="text/css" href="include/css/button.css" />
<script type="text/javascript" src="include/js/godfather.js"></script>


<div class="container">
	<div id="results">
		<%
		if (godfather != null){
			Job job = godfather.getJob();
			Location location = godfather.getLocation();
		%>

		<div class="godfather-card card">
			<div class="card-body">
				<div class="card-text">
					<form>
						<div class="row">
							<div class="form-group col-md-6 row">
								<label class="col-md-4 col-form-label">Vorname</label>
								<div class="col-md-8">
									<span><%=godfather.getFirstName()%></span>
								</div>
							</div>
							<div class="form-group col-md-6 row">
								<label class="col-md-4 col-form-label">Nachname</label>
								<div class="col-md-8">
									<span><%=godfather.getLastName()%></span>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group col-md-6 row">
								<label class="col-md-4 col-form-label">Geburtstag</label>
								<div class="col-md-8">
									<span><%=godfather.getBirthday()!=null ? godfather.getBirthday() : ""%></span>
								</div>
							</div>
							<div class="form-group col-md-6 row">
								<label class="col-md-4 col-form-label">Email</label>
								<div class="col-md-8">
									<input type="email" name="<%=GodfatherUpdateServlet.PARAMETER_EMAIL %>" class="form-control"
										value="<%=godfather.getEmail()%>">
								</div>
							</div>
						</div>
						<%
							if (location != null) {
						%>
						<div class="row">
							<div class="form-group col-md-6 row">
								<label class="col-md-4 col-form-label">Standort</label>
								<div class="col-md-8">
									<select class="form-control" name="<%= GodfatherUpdateServlet.PARAMETER_LOCATION %>">
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
							<%
								if (job != null) {
							%>
							<div class="form-group col-md-6 row">
								<label class="col-md-4 col-form-label">Einstellungsdatum</label>
								<div class="col-md-8">
									<span><%=godfather.getHiringDate()%></span>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group col-md-6 row">
								<label class="col-md-4 col-form-label">Job</label>
								<div class="col-md-8">
									<%
										Job godfatherJob = godfather.getJob();
										if(godfatherJob != null) {
											TeachingType teachingType = godfatherJob.getTeachingType();

									%>
											<span><%=godfatherJob.getName() + (teachingType!=null ? " (" + teachingType.getName() + ")": "") %></span>	
									<%		
										}
									%>
								</div>
							</div>
							<%
								}
							%>
							<div class="form-group col-md-6 row">
								<label class="col-md-4 col-form-label">Ausbildungsjahr</label>
								<div class="col-md-8">
									<span><%=godfather.getEducationalYear()%></span>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group col-md-6 row">
								<label class="col-md-4 col-form-label">Max. Anzahl Nachwuchskräfte</label>
								<div class="col-md-8">
									<input type="text" class="form-control" name="<%= GodfatherUpdateServlet.PARAMETER_MAXTRAINEES%>"
										value="<%=godfather.getMaxTrainees()%>">
								</div>
							</div>
							<div class="form-group col-md-6 row">
								<label class="col-md-4 col-form-label">Akt. Anzahl Nachwuchskräfte</label>
								<div class="col-md-8">
									<span><%=godfather.getCurrentNumberTrainees()%></span>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group col-md-6 row">
								<label class="col-md-4 col-form-label">Kurze Beschreibung</label>
								<div class="col-md-8">
									<textarea class="form-control" name="<%= GodfatherUpdateServlet.PARAMETER_DESCRIPTION %>" rows="3" placeholder="Hallo, ich bin ..."><%= godfather.getDescription() != null ? godfather.getDescription() : "" %></textarea>
								</div>
							</div>
							<div class="form-group col-md-6 row">
								<label class="col-md-4 col-form-label">Beschreibung nach Auswahl</label>
								<div class="col-md-8">
									<textarea class="form-control" name="<%= GodfatherUpdateServlet.PARAMETER_PICKTEXT %>" rows="3" placeholder="Ich freue mich, dass Du dich für mich entschieden hast! ..." ><%= godfather.getPickText() != null ? godfather.getPickText() : "" %></textarea>
								</div>
							</div>
						</div>
					</form>
					<form onSubmit="return checkPassword(this)">
						<div class="row">
							<div class="form-group col-md-6 row">
								<label class="col-md-4 col-form-label">Neues Passwort</label>
								<div class="col-md-8">
									<input name="password" type="password" id="password">
								</div>
							</div>
							<div class="form-group col-md-6 row">
								<label class="col-md-4 col-form-label">Passwort wiederholen</label>
								<div class="col-md-8">
									<input  name="password_confirm" type="password" id="password_confirm" oninput="check(this)">
								</div>
							</div>
						</div>
						<div>
							<input type="submit" value="Passwort ändern">
						</div>
						
						
					</form>
						<div class="row">
							<div class="form-group col-md-6 row">
								<label class="col-md-4 col-form-label">Bild hochladen</label>
								<div class="col-md-8">
									<div class="card-img-top godfather-card-image">
									<form action="uploadGodfatherImage" method="post" enctype="multipart/form-data">
  										<input type="file" name="<%= ServletParameter.GODFATHER_IMAGE %>" accept="image/png, image/jpeg" />
  										<input type="hidden" name="<%= CSRFPreventionUtil.FIELD_NAME %>"  value="<%= CSRFPreventionUtil.generateToken(session, CSRFForm.GODFATHER_UPDATE_SELF) %>">
										<input type="submit"  />
									</form>
									</div>
								</div>
							</div>
						</div>
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
					<input type="submit" class="btn update" value="UPDATE">
				</form>
			</div>
		</div>

		<%

		} else {
			return;
		}
		%>
	</div>
	<!-- show all 
				<div id="async-results">
				
				</div>-->
</div>

<jsp:include page="/WEB-INF/include/footer.jsp" />