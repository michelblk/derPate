<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"
	import="de.db.derpate.util.CSRFPreventionUtil"
	import="de.db.derpate.manager.LoginManager"
	import="de.db.derpate.servlet.LoginServlet"
	import="de.db.derpate.servlet.filter.CSRFServletFilter"
	import="de.db.derpate.CSRFForm"
	import="de.db.derpate.util.URIParameterEncryptionUtil"
%>
<jsp:include page="WEB-INF/include/header.jsp" />
	<link rel="stylesheet" type="text/css" href="include/css/login.css" />
	<div class="container container-middle">
		<div class="col-sm-10 col-md-6 jumbotron" id="login">
			<h1 class="display-1 text-center">Der Pate</h1>
			
			<div class="spinner-border" role="status" id="spinner">
				<span class="sr-only">Loading...</span>
			</div>
					<form method="post" action="login">
					<div class="form-signin">
						<input type="text" class="form-control" id="input_token" name="<%= LoginServlet.INPUT_FIELD_TOKEN %>"  value="" placeholder="Benutzererkennung" required autofocus/>
					</div>
					</form>
					<button class="btn btn-lg btn-primary btn-block" type="submit"
						value="Login">Login</button>
			<form method="post" action="login">
				<input type="hidden" id="csrftoken"
					name="<%=CSRFPreventionUtil.FIELD_NAME%>"
					value="<%=CSRFPreventionUtil.generateToken(session, CSRFForm.LOGIN)%>" />
				<div class="form-signin">
					<input type="text" class="form-control" name="<%=LoginServlet.INPUT_FIELD_EMAIL%>"
						placeholder="Benutzername" required autofocus />
					<input type="password" class="form-control" name="<%=LoginServlet.INPUT_FIELD_PASSWORD%>"
						placeholder="Passwort" required />
				
					<button class="btn btn-lg btn-primary btn-block" type="submit"
						value="Login">Login</button>	
				</div>
			</form>
		</div>
	</div>
	<script>
		$(document).ready(function () {
			$("#login form").submit(function (e) {
				$("#spinner").show();
				var form = $(this);
				e.preventDefault();
				$.ajax({
					url: $(form).attr("action"),
					method: $(form).attr("method"),
					data: $(form).serialize(),
					cache: false,
					complete: function(e, text) {
						$("#spinner").hide();
						if(e.status === <%=LoginServlet.SC_LOGIN_SUCCESS%>) {
							// success
							alert("Successfully logged in");
							location.reload();// just an example
						}else
						if(e.status === <%=Integer.toString((new CSRFServletFilter(CSRFForm.LOGIN)).getErrorStatusCode())%>) {
							// CSRF token gone
							alert("Please try again");
							// reload to generate a new token
							location.reload();
						}else
						if(e.status === <%=LoginServlet.SC_LOGIN_INCOMPLETE%>) {
							// logindata was not correctly submitted
							alert("You have to fill in all required fields");
						}else
						if(e.status === <%=LoginServlet.SC_LOGIN_ERROR%>) {
							// login failed
							alert("Login failed");
						}else
						if(e.status === <%=LoginServlet.SC_ALREADY_LOGGED_IN%>) {
							// already logged in
							alert("Already logged in");	
						}else{
							// unknown
							alert("Unknown error");
						}
						var newToken = e.getResponseHeader("<%=CSRFPreventionUtil.HEADER_FIELD%>");
						if (newToken != null && newToken.length > 0) {
							$(form).find("#csrftoken").val(newToken);
						}
					}
				});
				return false;
			});
		});
	</script>
<jsp:include page="WEB-INF/include/footer.jsp" />