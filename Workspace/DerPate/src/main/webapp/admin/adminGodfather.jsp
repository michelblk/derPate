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
%>

<%
	GodfatherDao godfatherDao = new GodfatherDao();
	LocationDao locationDao = new LocationDao();
	JobDao jobDao = new JobDao();
	
%>

<jsp:include page="/WEB-INF/include/header.jsp" />
<link rel="stylesheet" type="text/css" href="include/css/admin.css" />
<<script type="text/javascript" src="include/js/admin.js"></script>

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
				Job job = godfather.getJob();
				Location location = godfather.getLocation();
		%>

		<div class="godfather-card card">
			<div class="card-img-top godfather-card-image"></div>
			<div class="card-body">
				<div class="card-text">
					<div class="row">
						<div class="form-group col-md-6 row">
							<label class="col-md-4 col-form-label">Vorname</label>
							<div class="col-md-8">
								<input type="text" class="form-control"
									value="<%=godfather.getFirstName()%>">
							</div>
						</div>
						<div class="form-group col-md-6 row">
							<label class="col-md-4 col-form-label">Nachname</label>
							<div class="col-md-8">
								<input type="text" class="form-control"
									value="<%=godfather.getLastName()%>">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-6 row">
							<label class="col-md-4 col-form-label">Geburtstag</label>
							<div class="col-md-8">
								<input type="date" class="form-control"
									value="<%=godfather.getBirthday()!=null ? godfather.getBirthday() : ""%>">
							</div>
						</div>
						<div class="form-group col-md-6 row">
							<label class="col-md-4 col-form-label">Email</label>
							<div class="col-md-8">
								<input type="email" class="form-control"
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
								<select class="form-control">
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
								<input type="date" class="form-control"
									value="<%=godfather.getHiringDate()%>">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-6 row">
							<label class="col-md-4 col-form-label">Job</label>
							<div class="col-md-8">
								<select class="form-control">
									<%
										for(Job oneJob : jobDao.all()){
											if(oneJob != null){
												TeachingType teachingType = oneJob.getTeachingType();
												out.print("<option value=\"" + URIParameterEncryptionUtil.encrypt(oneJob.getId()) + "\" " + (oneJob.equals(location) ? "selected" : "") + ">" + oneJob.getName() + (teachingType!=null ? " (" + teachingType.getName() + ")": "")+ "</option>"); 
											}
										}
									%>
								</select>
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
								<input type="text" class="form-control"
									value="<%=godfather.getMaxTrainees()%>">
							</div>
						</div>
						<div class="form-group col-md-6 row">
							<label class="col-md-4 col-form-label">Akt. Anzahl Nachwuchskräfte</label>
							<div class="col-md-8">
								<input type="text" class="form-control"
									value="<%=godfather.getCurrentNumberTrainees()%>">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-6 row">
							<label class="col-md-4 col-form-label">Kurze Beschreibung</label>
							<div class="col-md-8">
								<span><%=godfather.getDescription()%></span>"
							</div>
						</div>
						<div class="form-group col-md-6 row">
							<label class="col-md-4 col-form-label">Beschreibung nach Auswahl</label>
							<div class="col-md-8">
								<span> <%=godfather.getPickText()%></span>
							</div>
						</div>
					</div>
					<form onSubmit="return checkPassword(this)">
						<div class="row">
							<div class="form-group col-md-6 row">
								<label class="col-md-4 col-form-label">Neues Password</label>
								<div class="col-md-8">
									<input name="password" type="password" id="password">
								</div>
							</div>
							<div class="form-group col-md-6 row">
								<label class="col-md-4 col-form-label">Password wiederholen</label>
								<div class="col-md-8">
									<input  name="password_confirm" type="password" id="password_confirm" oninput="check(this)">
								</div>
							</div>
						</div>
						<div>
							<input type="submit" value="chech password">
						</div>
					</form>
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
					<input type="submit" class="btn delete" value="DELETE">
					<input type="submit" class="btn update" value="UPDATE">
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