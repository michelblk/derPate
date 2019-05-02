<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" 
	import="de.db.derpate.manager.LoginManager"
	import="de.db.derpate.util.CSRFPreventionUtil"
	import="de.db.derpate.CSRFForm"
%>
		<div class="footer">
			<div class="container-fluid">
				<ul class="text-center">
					<li>
						<a href="imprint.jsp">Impressum</a>
					</li>
					<li>
						<a href="aboutus.jsp">&Uuml;ber uns</a>
					</li>
					<li>
						<a href="privacypolicy.jsp">Datenschutzbestimmungen</a>
					</li>
					<li>
						<a href="contact.jsp">Kontakt</a>
					</li>
					<%
						if(LoginManager.getInstance().isLoggedIn(session)){
							%> 
							<li>
								<a href="logout?<%= CSRFPreventionUtil.FIELD_NAME + "=" + CSRFPreventionUtil.generateTokenForGETParameter(session, CSRFForm.LOGOUT)%>">Logout</a>
							</li>
							<%
						}
					%>
				</ul>
			</div>
		</div>
	</body>
</html>
