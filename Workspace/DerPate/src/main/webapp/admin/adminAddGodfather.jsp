<%@page import="de.db.derpate.servlet.ServletParameter"%>
<%@ page contentType="text/html" pageEncoding="UTF-8" 
import="de.db.derpate.persistence.JobDao"
 import="de.db.derpate.model.TeachingType"
 import="de.db.derpate.model.Job"
 import="de.db.derpate.util.URIParameterEncryptionUtil"
 import="de.db.derpate.model.Location"
 import="de.db.derpate.persistence.LocationDao"
 import="de.db.derpate.CSRFForm"
 import="de.db.derpate.util.CSRFPreventionUtil"
 import="de.db.derpate.servlet.adminOnly.GodfatherUpdateServlet" %>
  <jsp:include page="/WEB-INF/include/header.jsp" />
<link rel="stylesheet" type="text/css" href="include/css/admin.css" />
<script type="text/javascript" src="include/js/admin.js"></script>


<div class="container">

<form action="adminGodfatherAdd" method="POST">
	<input type="text" name="<%= ServletParameter.GODFATHER_FIRST_NAME%>">
	<input type="text" name="<%= ServletParameter.GODFATHER_LAST_NAME %>">
	<input type="email" name="<%= ServletParameter.GODFATHER_EMAIL %>">
	<input type="password" name="<%= ServletParameter.GODFATHER_PASSWORD %>">
	<select class="form-control" name="<%= ServletParameter.GODFATHER_LOCATION_ID %>" required>
		<%
			LocationDao locationDao = new LocationDao();
			for(Location oneLocation : locationDao.all()){
				if(oneLocation != null){
					%>
						<option value="<%=URIParameterEncryptionUtil.encrypt(oneLocation.getId()) %>"><%=oneLocation.getName()%></option>
					<%
				}
			}
		%>
	</select>
	<select class="form-control" name="<%= ServletParameter.GODFATHER_JOB_ID%>" required>
	<%
		JobDao jobDao = new JobDao();
		for(Job oneJob : jobDao.all()){
			if(oneJob != null){
				TeachingType teachingType = oneJob.getTeachingType();
				out.print("<option value=\"" + URIParameterEncryptionUtil.encrypt(oneJob.getId()) + "\">" + oneJob.getName() + (teachingType!=null ? " (" + teachingType.getName() + ")": "")+ "</option>"); 
			}
		}
	%>
	</select>
	<input type="date" name="<%= ServletParameter.GODFATHER_HIRING_DATE %>">
	<input type="hidden" name="<%= CSRFPreventionUtil.FIELD_NAME %>" value="<%= CSRFPreventionUtil.generateToken(session, CSRFForm.ADMIN_ADD_GODFATHER) %>">
	<input type="submit" value="Hinzuf&uuml;gen">
</form>
</div>
<jsp:include page="/WEB-INF/include/footer.jsp" />