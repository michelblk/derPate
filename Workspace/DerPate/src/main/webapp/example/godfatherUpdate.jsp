<%@page contentType="text/html" pageEncoding="UTF-8"
		import="de.db.derpate.model.Godfather"
		import="de.db.derpate.manager.LoginManager"
		import="de.db.derpate.servlet.godfatherOnly.GodfatherUpdateServlet"
		import="de.db.derpate.Constants"
		import="de.db.derpate.util.CSRFPreventionUtil"
		import="de.db.derpate.CSRFForm"
		import="de.db.derpate.persistence.LocationDao"
		import="java.util.List"
		import="de.db.derpate.model.Location"
		import="de.db.derpate.util.URIParameterEncryptionUtil"
		import="de.db.derpate.model.Job"
		import="de.db.derpate.persistence.JobDao"
		import="de.db.derpate.persistence.GodfatherDao" %>
<%	
LocationDao locationDao = new LocationDao();
GodfatherDao godfatherDao = new GodfatherDao();	

//FIXME find better solution
Godfather sessionGodfather = LoginManager.getInstance().getUserBySession(session);
if(sessionGodfather == null) return;
Godfather godfather = godfatherDao.findById(sessionGodfather.getId()); // always use up to date godfather, as maxTrainees will be displayed
if(godfather == null) return;
LoginManager.getInstance().update(session, godfather); // update session
%>
<!DOCTYPE html>
<html>
	<head>
		<title>Eigene Daten ändern</title>
		<meta charset="<%= Constants.CHARSET.name() %>" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link rel="stylesheet" type="text/css" href="https://getbootstrap.com/docs/4.2/dist/css/bootstrap.min.css" />
		<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
		<script src="https://getbootstrap.com/docs/4.0/assets/js/vendor/popper.min.js"></script>
		<script src="https://getbootstrap.com/docs/4.2/dist/js/bootstrap.min.js"></script>
		<style>
		button[type="submit"] > .spinner-border {
			display: none;
		}
		button[type="submit"]:disabled > .spinner-border {
			display: inherit;
		}
		
		</style>
	</head>
	<body>
		<div class="container">
			<div class="form-group text-center">
				<form id="updating-form" method="POST" action="../godfatherUpdate" accept-charset="<%= Constants.CHARSET %>">
					<div class="form-group">
						<label>E-Mail Adresse</label>
						<input type="email" class="form-control" name="<%= GodfatherUpdateServlet.PARAMETER_EMAIL %>" placeholder="max.mustermann@deutschebahn.com" value="<%= godfather.getEmail() %>" required />
						<div class="invalid-feedback">Eine E-Mail muss folgendes Format haben: example@example.com</div>
					</div>
					<div class="form-group">
						<label>Erste Tätigkeitsstätte</label>
						<select class="form-control" name="<%= GodfatherUpdateServlet.PARAMETER_LOCATION %>">
							<%
								List<Location> locations = locationDao.all();
												for(Location location : locations) {
							%>
								<option value="<%=URIParameterEncryptionUtil.encrypt(location.getId()) %>" <% if(location.equals(godfather.getLocation())){%>selected<% } %>>
									<%= location.getName() %>
								</option>
							<% } %>
						</select>
					</div>
					<div class="form-group">
						<label>Maximale Nachwuchskräfte</label>
						<input type="number" class="form-control" name="<%= GodfatherUpdateServlet.PARAMETER_MAXTRAINEES %>" placeholder="z. B. 2" min="<%= godfather.getCurrentNumberTrainees() %>" max="<%= Constants.Godfather.MAX_TRAINEES %>" value="<%= godfather.getMaxTrainees() %>" required />
						<div class="invalid-feedback">Bitte Zahl zwischen <%= godfather.getCurrentNumberTrainees() %> (Anzahl der aktuell zu betreuenden Nachwuchskr&auml;fte) und 10 eingeben!</div>
					</div>
					<div class="form-group">
						<label>Beschreibung</label>
						<textarea class="form-control" name="<%= GodfatherUpdateServlet.PARAMETER_DESCRIPTION %>" rows="3" placeholder="Hallo, ich bin ..."><%= godfather.getDescription() %></textarea>
						<div class="invalid-feedback">Hier stimmt etwas nicht. Maximal 500 Zeichen sind erlaubt. Unicodeblock Smileys sind nicht zulässig.</div>
					</div>
					<div class="form-group">
						<label>Text, der der Nachwuchskraft nach der Anmeldung angezeigt wird</label>
						<textarea class="form-control" name="<%= GodfatherUpdateServlet.PARAMETER_PICKTEXT %>" rows="3" placeholder="Ich freue mich, dass Du dich für mich entschieden hast! ..." ><%= godfather.getPickText() %></textarea>
						<div class="invalid-feedback">Hier stimmt etwas nicht. Maximal 500 Zeichen sind erlaubt. Unicodeblock Smileys sind nicht zulässig.</div>
					</div>
					<input type="hidden" name="<%= CSRFPreventionUtil.FIELD_NAME %>" value="<%= CSRFPreventionUtil.generateToken(session, CSRFForm.GODFATHER_UPDATE_SELF) %>" />
					<button type="submit" class="btn btn-secondary">
						<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
						Daten &auml;ndern
					</button>
				</form>
			</div>
		</div>
		<script>
			$(document).ready(function () {				
				$("#updating-form").submit(function (e) {
					e.preventDefault();
					var $form = $(this);
					
					indicateLoading($form, true);
					
					$.ajax({
						url: $form.attr("action"),
						method: $form.attr("method"),
						data: $form.serialize(),
						cache: false,
						dataType: "json",
						complete: function(e, text) {
							indicateLoading($form, false);
							
							if(e.status == <%= GodfatherUpdateServlet.SC_SUCCESS %>) {
								processResponse(e.responseText);
							}else
							if(e.status == <%= GodfatherUpdateServlet.SC_ERROR %>) {
								alert("Error");
								$("#updating-form [name]").addClass("is-invalid");
							}else{
								alert("Unknown error");
							}
						}
					});
					
					return false;
				});
			});
			
			function processResponse(data) {
				var updatedFields = $.parseJSON(data);
				
				$("#updating-form [name]").removeClass("is-invalid").removeClass("is-valid");
				$.each(updatedFields, function (key, value) {
					var isvalid = value["<%= GodfatherUpdateServlet.JSON_OUTPUT_VALID %>"];
					var newvalue = value["<%=GodfatherUpdateServlet.JSON_OUTPUT_VALUE%>"];
					
					if(isvalid) {
						$("#updating-form [name='" + key + "']").addClass("is-valid");
						if(newvalue != null) {
							$("#updating-form [name='" + key + "']").val(newvalue);
						}
					}else{
						$("#updating-form [name='" + key + "']").addClass("is-invalid");
					}
				});
			}
			
			function indicateLoading(form, bool) {
				var buttons = $(form).find("button[type='submit']");
				$(buttons).prop("disabled", bool);
			}
		</script>
	</body>
</html>