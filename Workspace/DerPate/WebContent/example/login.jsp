<!DOCTYPE html>
<%@page import="de.db.derPate.util.CSRFPreventionUtil"%>
<%@page import="de.db.derPate.manager.LoginManager"%>
<%@page import="de.db.derPate.servlet.LoginServlet"%>
<%@page import="de.db.derPate.Userform"%>
<%@page import="de.db.derPate.msc.CSRFPrevention"%>
<%@page import="de.db.derPate.Constants"%>
<html>
	<head>
		<title>Login Example</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
		<script>
			$(document).ready(function () {
				$("form").submit(function (e) {
					var form = $(this);
					e.preventDefault();
					$.ajax({
						url: $(form).attr("action"),
						method: $(form).attr("method"),
						data: $(form).serialize(),
						complete: function(e, text) {
							if(e.status === <%= LoginServlet.SC_LOGIN_SUCCESS %>) {
								// success
								alert("Successfully logged in");
								location.reload();// just an example
							}else
							if(e.status === <%= CSRFPreventionUtil.SC_INVALID_TOKEN %>) {
								// CSRF token was gone
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
							}else{
								// unknown
								alert("Unknown error");
							}
							var newToken = e.getResponseHeader("<%= CSRFPreventionUtil.HEADER_FIELD %>");
							if(newToken != null && newToken.length > 0) {
								$(form).find("input[type='hidden'][name = '<%= CSRFPreventionUtil.FIELD_NAME %>']").val(newToken);
							}
						}
					});
					return false;
				});
			});
		</script>
	</head>
	<body>
		<div class="container">
			<div class="form-group">
				<form method="POST" action="../login">
					<h3>Nachwuchskraft</h3>
					<div class="input-group">
						<label for="input_token">Benutzerkennung</label>
						<input id="input_token" name="<%= Constants.Ui.Inputs.LOGIN_TOKEN %>" type="text" value="" placeholder="z.B. abc-efg-hij" />
					</div>
					<h3>Pate / Admin</h3>
					<div class="input-group">
						<label for="input_email">E-Mail</label>
						<input id="input_email" name="<%= Constants.Ui.Inputs.LOGIN_EMAIL %>" type="text" value="" placeholder="E-Mail" />
					</div>
					<div class="input-group">
						<label for="input_password">Passwort</label>
						<input id="input_password" name="<%= Constants.Ui.Inputs.LOGIN_PASSWORD %>" type="password" value="" placeholder="Passwort" />
					</div>
					<div class="input-group">
						<input type="hidden" name="<%= CSRFPreventionUtil.FIELD_NAME %>" value="<%= CSRFPrevention.generateToken(session, Userform.LOGIN) %>" />
						<input type="submit" value="Anmelden" />
					</div>
				</form>
			</div>
			<% if(LoginManager.getInstance().isLoggedIn(session)) {
				response.getWriter().append("<a href=\"../logout?"+CSRFPreventionUtil.FIELD_NAME + "=" + CSRFPrevention.generateToken(session, Userform.LOGOUT) +"\">Logout</a>");
			} %>
		</div>
	</body>
</html>