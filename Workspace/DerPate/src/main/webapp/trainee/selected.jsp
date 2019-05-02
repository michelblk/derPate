<%@page import="de.db.derpate.util.ServletUtil"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"
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
	import="de.db.derpate.manager.LoginManager"
	import="de.db.derpate.util.DateUtil"
	import="de.db.derpate.model.LoginUser"
	import="de.db.derpate.model.Trainee"
	import="de.db.derpate.model.Godfather"%>
<%
	LoginUser user = LoginManager.getInstance().getUserBySession(session);
	Trainee trainee = (Trainee) user; // current user
	if (trainee == null) {
		return; // is not possible, as filter is applied to this folder
	}
	Godfather godfather = trainee.getGodfather(); // selected godfather
	if (godfather == null) {
		response.sendRedirect("../redirect");
		return;
	}
%>
<jsp:include page="/WEB-INF/include/header.jsp" />
<link rel="stylesheet" type="text/css" href="include/css/trainee.css">
<div class="container">
	<div class="col-lg-6 offset-lg-3 col-12">
		<div class="card">
			<div class="card-heading">
				<!-- Background color -->
			</div>
			<div class="avatar">
				<div class="img" style="background-image: url('godfatherImage?godfather_id=<%=URIParameterEncryptionUtil.encrypt(godfather.getId())%>');"></div>
			</div>
			<div class="info">
				<div class="name">
					<%=godfather.getFirstName()%> <%=godfather.getLastName()%>
					<% if(godfather.getBirthday() != null) { %>
					<div class="small"><%= DateUtil.getYearDiff(godfather.getBirthday()) %> Jahre alt</div>
					<% } %>
				</div>
				<%
					Job job = godfather.getJob();
					if (job != null) {
						TeachingType teachingType = job.getTeachingType();
						if (teachingType != null) {
					%>
				<div class="small"><%= teachingType.getName() %></div>
				<% } %>
				<div class="small"><%= job.getName() %></div>
				<% } 
				Location location = godfather.getLocation();
				if (location != null) {
				%>
				<div class="small"><%= location.getName() %></div>
				<% } %>
				<div class="small"><%= godfather.getEducationalYear() %>. Lehrjahr</div>
			</div>
			<div class="description">
				<%=ServletUtil.replaceNullWithEmptyString(godfather.getPickText())%>
			</div>
			<div class="contact mt-3">
				<a href="mailto:<%= godfather.getEmail() %>">
					Klicke hier, um mir eine E-Mail zu schreiben
				</a>
			</div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/include/footer.jsp" />