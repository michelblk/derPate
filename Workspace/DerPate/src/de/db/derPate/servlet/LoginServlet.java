package de.db.derPate.servlet;

import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.annotation.Nullable;

import de.db.derPate.Constants.Ui.Inputs;
import de.db.derPate.manager.LoggingManager;
import de.db.derPate.util.InputVerifyUtil;

/**
 * This servlet get's called by the client, when the user tries to log on. It
 * checks the given credentials and redirects to the start page for logged in
 * users.<br>
 * Allowed http methods: <code>POST</code>
 *
 * @author MichelBlank
 *
 */
@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	/**
	 * This method serves the http post-method and receives the credentials provided
	 * by the client, checks them and redirects to the next page (or sends an http
	 * status code, to let the client know, that the login was not successful).
	 */
	@Override
	protected void doPost(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response)
			throws ServletException, IOException {
		if (request == null || response == null) {
			return;
		}

		String username = request.getParameter(Inputs.LOGIN_USERNAME.toString());
		String password = request.getParameter(Inputs.LOGIN_PASSWORD.toString());
		String token = request.getParameter(Inputs.LOGIN_TOKEN.toString());

		if (InputVerifyUtil.isNotEmpty(username) && InputVerifyUtil.isNotEmpty(password)) {
			// username and password detected
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		} else if (InputVerifyUtil.isNotEmpty(token)) {
			// token detected
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		} else {
			// invalid login attempt
			LoggingManager.log(Level.INFO,
					"Client with IP " + request.getRemoteAddr() + " tried to login with no credentials");
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
		return;
	}

}
