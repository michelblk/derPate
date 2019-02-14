<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	
	import="de.db.derPate.util.CSRFPreventionUtil"
	import="de.db.derPate.manager.LoginManager"
	import="de.db.derPate.servlet.LoginServlet"
	import="de.db.derPate.servlet.filter.CSRFServletFilter"
	import="de.db.derPate.CSRFForm"
	import="de.db.derPate.util.URIParameterEncryptionUtil"
%>
<%
	boolean loginFailed = (request.getParameter("login") != null
			&& request.getParameter("login").equals("failed"));
%>


<jsp:include page="WEB-INF/include/header.jsp" />


<body>
	<h2 id="pate">Der Pate</h2>
	<div class="container">
		<div class="row">
			<div class="col-sm-6 col-sm-offset-3 col-md-4 col-md-offset-4" id="login-centeredContainer">
		
				<form method="post" action="login" id="login" >
				<input type="hidden" id="csrftoken" name="<%= CSRFPreventionUtil.FIELD_NAME %>" value="<%= CSRFPreventionUtil.generateToken(session, CSRFForm.LOGIN) %>" />
					<div class="form-signin <%if (loginFailed) {%>has-error<%}%>">
						<input type="text" class="form-control" name="<%= LoginServlet.INPUT_FIELD_EMAIL %>"
							placeholder="Benutzername" required autofocus /> <input
							type="password" class="form-control" name="<%=LoginServlet.INPUT_FIELD_PASSWORD %>"
							placeholder="Passwort" required />
						<%
							if (loginFailed) {
						%>
						<div class="alert alert-danger" role="alert">"Hinweis: Der
							Benutzername oder das Password ist nicht gültig!"</div>
						<%
							}
						%>
						<button class="btn btn-lg btn-primary btn-block" type="submit"
							value="Login">Login</button>
					</div>
				</form>
		</div>
	</div>
<script>
			$(document).ready(function () {
				$("#login").submit(function (e) {
					var form = $(this);
					e.preventDefault();
					$.ajax({
						url: $(form).attr("action"),
						method: $(form).attr("method"),
						data: $(form).serialize(),
						cache: false,
						complete: function(e, text) {
							if(e.status === <%= LoginServlet.SC_LOGIN_SUCCESS %>) {
								// success
								alert("Successfully logged in");
								location.reload();// just an example
							}else
							if(e.status === <%= Integer.toString((new CSRFServletFilter(CSRFForm.LOGIN)).getErrorStatusCode()) %>) {
								// CSRF token gone
								alert("Please try again");
								// reload to generate a new token
								location.reload();
							}else
							if(e.status === <%= LoginServlet.SC_LOGIN_INCOMPLETE %>) {
								// logindata was not correctly submitted
								alert("You have to fill in all required fields");
							}else
							if(e.status === <%= LoginServlet.SC_LOGIN_ERROR %>) {
								// login failed
								alert("Login failed");
							}else
							if(e.status === <%= LoginServlet.SC_ALREADY_LOGGED_IN %>) {
								// already logged in
								alert("Already logged in");	
							}else{
								// unknown
								alert("Unknown error");
							}
							var newToken = e.getResponseHeader("<%= CSRFPreventionUtil.HEADER_FIELD %>");
							if(newToken != null && newToken.length > 0) {
								$(form).find("#csrftoken").val(newToken);
							}
						}
					});
					return false;
				});
			});
		</script>
</body>
<jsp:include page="WEB-INF/include/footer.jsp" />

<!-- 
Admin und Pate Login, der Azubi toke


token  -->