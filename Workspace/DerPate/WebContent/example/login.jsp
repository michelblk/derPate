<!DOCTYPE html>
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
							if(e.status === 204) {
								// success
								alert("Successfully logged in");
							}else
							if(e.status === 410) {
								// CSRF token was gone
								alert("Please try again");
								// reload to generate a new token
								location.reload();
							}else
							if(e.status === 400) {
								// logindata was not correctly submitted
								alert("You have to fill in all required fields");
							}else
							if(e.status === 403) {
								// login failed
								alert("Login failed");
							}else{
								// unknown
								alert("Unknown error");
							}
							var newToken = e.getResponseHeader("<%= CSRFPrevention.HEADER_FIELD %>");
							if(newToken != null && newToken.length > 0) {
								$(form).find("input[type='hidden'][name = '<%= CSRFPrevention.FIELD_NAME %>']").val(newToken);
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
						<input type="hidden" name="<%= CSRFPrevention.FIELD_NAME %>" value="<%= CSRFPrevention.generateToken(session, Userform.LOGIN) %>" />
						<input type="submit" value="Anmelden" />
					</div>
				</form>
			</div>
		</div>
	</body>
</html>