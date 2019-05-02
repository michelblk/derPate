<%@page import="de.db.derpate.persistence.JobDao"%>
<%@page import="de.db.derpate.model.TeachingType"%>
<%@page import="de.db.derpate.model.Job"%>
<%@page import="de.db.derpate.util.URIParameterEncryptionUtil"%>
<%@page import="de.db.derpate.model.Location"%>
<%@page import="de.db.derpate.persistence.LocationDao"%>
<%@page import="de.db.derpate.CSRFForm"%>
<%@page import="de.db.derpate.util.CSRFPreventionUtil"%>
<%@page import="de.db.derpate.servlet.ServletParameterEnum"%>
<form action="../adminGodfatherAdd" method="POST">
	<input type="text" name="<%= ServletParameterEnum.GODFATHER_FIRST_NAME %>">
	<input type="text" name="<%= ServletParameterEnum.GODFATHER_LAST_NAME %>">
	<input type="email" name="<%= ServletParameterEnum.GODFATHER_EMAIL %>">
	<input type="password" name="<%= ServletParameterEnum.GODFATHER_PASSWORD %>">
	<select class="form-control" name="<%= ServletParameterEnum.GODFATHER_LOCATION_ID %>" required>
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
	<select class="form-control" name="<%= ServletParameterEnum.GODFATHER_JOB_ID %>" required>
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
	<input type="date" name="<%= ServletParameterEnum.GODFATHER_HIRING_DATE %>">
	<input type="hidden" name="<%= CSRFPreventionUtil.FIELD_NAME %>" value="<%= CSRFPreventionUtil.generateToken(session, CSRFForm.ADMIN_ADD_GODFATHER) %>">
	<input type="submit" value="Hinzuf&uuml;gen">
</form>