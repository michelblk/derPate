<%@page import="de.db.derpate.model.Godfather"%>
<%@page import="de.db.derpate.model.Trainee"%>
<%@page import="de.db.derpate.model.LoginUser"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"
	import="de.db.derpate.CSRFForm"
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
	import="java.util.List"
	import="de.db.derpate.manager.LoginManager"
	import="de.db.derpate.util.DateUtil"
%>
<%
	LoginUser user = LoginManager.getInstance().getUserBySession(session);
	Trainee trainee = (Trainee) user;
	if(trainee == null) {
		return;
	}
	Godfather godfather = trainee.getGodfather();
	if(godfather == null) {
		return;
	}
%>
<jsp:include page="/WEB-INF/include/header.jsp" />
		<div class="container">
			<div id="results">
				<div class="godfather-card card default" id ="godfahter-card-default">
					<div class="card-img-top godfather-card-image"></div>
					<div class="card-body">
						<table class="table">
       	 <h3 class="modal-title" id="exampleModalLongTitle">Mehr Informationen</h3>
  <tbody>
    <tr>
      <th>Vorname</th>
      <td class="godfather-card-firstname"><%= godfather.getFirstName() %></td>
    </tr>
    <% Location location = godfather.getLocation(); 
    if(location != null) {%>
      <tr>
      <th>Location</th>
      <td class="godfather-card-location"><%= location.getName() %></td>
    </tr>
    <% } 
    Job job = godfather.getJob();
    if(job != null) {
    	TeachingType teachingType = job.getTeachingType();
    	if(teachingType != null) {
    %>
    <tr>
      <th>Ausbildung/Duales Studium</th>
      <td class="godfather-card-teachingType"><%= teachingType.getName() %></td>
    </tr>
    <% } %>
    <tr>
      <th>Beruf</th>
      <td class="godfather-card-job"><%= job.getName() %></td>
    </tr>
    <% } %>
        <tr>
      <th>Lehrjahr</th>
      <td class ="godfather-card-year"><%= godfather.getEducationalYear() %></td>
    </tr>
        <tr>
      <th>Alter</th>
      <td class="godfather-card-educationalyear"><%= DateUtil.getYearDiff(godfather.getBirthday())%></td>
    </tr>
    
        <tr>
      <th>Beschreibung</th>
      <td class="godfather-card-description"><%= godfather.getDescription() %></td>
    </tr>
    </tbody>
    </table>
      </div>
		</div>
			</div>
				<!-- show all -->
				<div id="async-results">		
				</div>
			</div>
<jsp:include page="/WEB-INF/include/footer.jsp" />