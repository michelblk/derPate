package de.db.derPate.servlet;

import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.annotation.Nullable;

import de.db.derPate.Constants;
import de.db.derPate.Constants.Ui.Inputs;
import de.db.derPate.Userform;
import de.db.derPate.manager.LoggingManager;
import de.db.derPate.manager.LoginManager;
import de.db.derPate.model.Admin;
import de.db.derPate.model.Godfather;
import de.db.derPate.model.Trainee;
import de.db.derPate.msc.CSRFPrevention;
import de.db.derPate.persistence.AdminDao;
import de.db.derPate.persistence.GodfatherDao;
import de.db.derPate.persistence.TraineeDao;
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
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
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

		String csrfToken = request.getParameter(CSRFPrevention.FIELD_NAME);
		String email = request.getParameter(Inputs.LOGIN_EMAIL.toString());
		String password = request.getParameter(Inputs.LOGIN_PASSWORD.toString());
		String token = request.getParameter(Inputs.LOGIN_TOKEN.toString());

		if (!CSRFPrevention.checkAndInvalidateToken(request.getSession(), Userform.LOGIN, csrfToken)) {
			response.sendError(HttpServletResponse.SC_GONE);
			return;
		}

		if (email != null && InputVerifyUtil.isNotBlank(email) && InputVerifyUtil.isNotBlank(password)) {
			Admin admin = AdminDao.getInstance().byEmail(email);
			Godfather godfather = null;

			if (admin != null) {
				String dbPassword = admin.getPassword();
				if (Constants.Login.hashUtil.isEqual(password, dbPassword, Constants.Login.hashPepper,
						Constants.Login.hashSeperator)) {
					LoginManager.getInstance().login(request, admin);
					response.setStatus(HttpServletResponse.SC_NO_CONTENT);
					return;
				}
			} else {
				godfather = GodfatherDao.getInstance().byEmail(email);
				if (godfather != null) {
					String dbPassword = godfather.getPassword();
					if (Constants.Login.hashUtil.isEqual(password, dbPassword, Constants.Login.hashPepper,
							Constants.Login.hashSeperator)) {
						LoginManager.getInstance().login(request, godfather);
						response.setStatus(HttpServletResponse.SC_NO_CONTENT);
						return;
					}
				}
			}
		} else if (InputVerifyUtil.isNotBlank(token)) {
			// token detected
			Trainee trainee = TraineeDao.getInstance().byToken(token);
			if (trainee != null) {
				LoginManager.getInstance().login(request, trainee);
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
				return;
			}
		} else {
			// invalid login attempt
			LoggingManager.log(Level.INFO,
					"Client with IP " + request.getRemoteAddr() + " tried to login with no credentials");
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}

		// no valid login, but valid token. So request can be trusted again
		response.setHeader(CSRFPrevention.HEADER_FIELD,
				CSRFPrevention.generateToken(request.getSession(), Userform.LOGIN));
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		return;
	}

}
