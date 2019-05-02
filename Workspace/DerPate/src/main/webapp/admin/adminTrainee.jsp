<%@page contentType="text/html" pageEncoding="UTF-8"
	import="de.db.derpate.persistence.TraineeDao"
	import="de.db.derpate.util.CSRFPreventionUtil"
	import="de.db.derpate.CSRFForm"
	import="de.db.derpate.servlet.adminOnly.TraineeRemoveServlet"
	import="de.db.derpate.util.URIParameterEncryptionUtil"
	import="java.util.List"
	import="de.db.derpate.model.Godfather"
	import="de.db.derpate.model.Trainee" %>
<jsp:include page="/WEB-INF/include/header.jsp" />
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">

<div class="container text-center">
	<a class="btn btn-db generate-token" href="adminGenerateTraineeToken?<%= CSRFPreventionUtil.FIELD_NAME %>=<%= CSRFPreventionUtil.generateTokenForGETParameter(session, CSRFForm.ADMIN_ADD_TRAINEE) %>">
		Token generieren
	</a>
	<table class="table table-hover">
		<thead>
			<tr>
				<th>
					Token
				</th>
				<th>
					Pate
				</th>
				<th>
					Aktionen
				</th>
			</tr>
		</thead>
		<%
		TraineeDao traineeDao = new TraineeDao();
		List<Trainee> trainees = traineeDao.all();
		for(Trainee trainee : trainees) {
		%>
			<tr>
				<td>
					<%= trainee.getLoginToken() %>
				</td>
				<td>
					<% Godfather godfather = trainee.getGodfather();
					if(godfather != null) {
						out.print(godfather.getFirstName() + " " + godfather.getLastName());
					}else{
						out.print("- nicht gew&auml;hlt -");
					}
					%>
				</td>
				<td>
					<a class="btn btn-db reset-godfather" data-token="<%= URIParameterEncryptionUtil.encrypt(trainee.getLoginToken()) %>">
						<i class="fas fa-sync"></i>
					</a>
					<a class="btn btn-db remove-token" data-token="<%= URIParameterEncryptionUtil.encrypt(trainee.getLoginToken()) %>">
						<i class="fas fa-trash"></i>
					</a>
				</td>
			</tr>
		<%
		}
		%>
	</table>
	<script>
	$(document).ready(function () {
		$(".remove-token").click(function(e) {
			e.preventDefault();
			
			var token = $(this).attr("data-token");
			var row = $(this).parents("tr");
			
			$.ajax({
				url: "adminTraineeUpdate?<%= TraineeRemoveServlet.PARAMETER_TRAINEE_TOKEN %>="+token,
				method: "DELETE",
				headers: {"<%= CSRFPreventionUtil.HEADER_FIELD %>": "<%= CSRFPreventionUtil.generateToken(session, CSRFForm.ADMIN_UPDATE_TRAINEE) %>"},
				success: function () {
					$(row).remove();
				},
				error: function() {
					alert("Ein Fehler ist aufgetreten!");
				}
			});
			return false;
		});
		
		$(".reset-godfather").click(function(e) {
e.preventDefault();
			
			var token = $(this).attr("data-token");
			
			$.ajax({
				url: "adminTraineeUpdate?<%= TraineeRemoveServlet.PARAMETER_TRAINEE_TOKEN %>="+token,
				method: "POST",
				headers: {"<%= CSRFPreventionUtil.HEADER_FIELD %>": "<%= CSRFPreventionUtil.generateToken(session, CSRFForm.ADMIN_UPDATE_TRAINEE) %>"},
				success: function () {
					location.reload(true);
				},
				error: function() {
					alert("Ein Fehler ist aufgetreten!");
				}
			});
			return false;
		});
	});
	</script>
</div>
<jsp:include page="/WEB-INF/include/footer.jsp" />