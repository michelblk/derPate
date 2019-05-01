package de.db.derpate.servlet;

import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jdt.annotation.NonNull;

import de.db.derpate.CSRFForm;
import de.db.derpate.Constants;
import de.db.derpate.manager.LoggingManager;
import de.db.derpate.manager.LoginManager;
import de.db.derpate.model.Admin;
import de.db.derpate.model.Godfather;
import de.db.derpate.model.Trainee;
import de.db.derpate.persistence.AdminDao;
import de.db.derpate.persistence.GodfatherDao;
import de.db.derpate.persistence.TraineeDao;
import de.db.derpate.servlet.filter.CSRFServletFilter;
import de.db.derpate.util.CSRFPreventionUtil;
import de.db.derpate.util.InputVerifyUtil;

/**
 * This {@link HttpServlet} get's called by the client, when the user tries to
 * log on. It checks the given credentials and redirects to the start page for
 * logged in users.<br>
 * Allowed http methods: <code>POST</code>
 *
 * @author MichelBlank
 * @see LoginManager
 */
@WebServlet("/login")
public class LoginServlet extends FilterServlet {
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;
	private final GodfatherDao godfatherDao = new GodfatherDao();
	private final TraineeDao traineeDao = new TraineeDao();
	private final AdminDao adminDao = new AdminDao();

	/**
	 * Http status code used to indicate a successful login
	 */
	public static final int SC_LOGIN_SUCCESS = HttpServletResponse.SC_OK;
	/**
	 * Http status code used to indicate a bad login
	 */
	public static final int SC_LOGIN_ERROR = HttpServletResponse.SC_FORBIDDEN;
	/**
	 * Http status code used to indicate that not all required fields have been
	 * sent.
	 */
	public static final int SC_LOGIN_INCOMPLETE = HttpServletResponse.SC_BAD_REQUEST;
	/**
	 * Http status codes used to indicate that the session is already registered
	 * with a user and therefore cannot be used by another user.
	 */
	public static final int SC_ALREADY_LOGGED_IN = HttpServletResponse.SC_CONFLICT;

	/**
	 * Input field name for user to submit mail (Godfather / Admin)
	 */
	public static final String INPUT_FIELD_EMAIL = "email"; //$NON-NLS-1$
	/**
	 * Input field name for user to submit password (Godfather / Admin)
	 */
	public static final String INPUT_FIELD_PASSWORD = "password"; //$NON-NLS-1$
	/**
	 * Input field name for user to submit token code (Trainees)
	 */
	public static final String INPUT_FIELD_TOKEN = "token"; //$NON-NLS-1$

	/**
	 * CSRFForm, that this Servlet is for, used for the CSRF Filter and generating a
	 * new token, if username or password was wrong (but csrf token was correct)
	 */
	@NonNull
	public static final CSRFForm USERFORM = CSRFForm.LOGIN;

	/**
	 * Constructor
	 */
	public LoginServlet() {
		super(new CSRFServletFilter(USERFORM));
	}

	/**
	 * This method serves the http post-method and receives the credentials provided
	 * by the client, checks them and redirects to the next page (or sends an http
	 * status code, to let the client know, that the login was not successful).
	 *
	 * @throws IOException if an input or output exception occurs
	 */
	@Override
	protected void onPost(@NonNull final HttpServletRequest request, @NonNull final HttpServletResponse response)
			throws IOException {
		if (LoginManager.getInstance().isLoggedIn(request.getSession())) {
			response.setStatus(SC_ALREADY_LOGGED_IN);
			return;
		}

		String email = request.getParameter(INPUT_FIELD_EMAIL);
		String password = request.getParameter(INPUT_FIELD_PASSWORD);
		String token = request.getParameter(INPUT_FIELD_TOKEN);

		if (email != null && InputVerifyUtil.isNotBlank(email) && password != null
				&& InputVerifyUtil.isNotBlank(password)) {

			// find admin user
			Admin admin = this.adminDao.findByEmail(email);
			if (admin != null) {
				// try to login as admin
				String dbPassword = admin.getPassword();
				if (Constants.Login.hashUtil.isEqual(password, dbPassword, Constants.Login.hashPepper,
						Constants.Login.hashSeparator)) {
					LoginManager.getInstance().login(request, admin);
					response.setStatus(SC_LOGIN_SUCCESS);
					return;
				}
			} else {
				// find godfather
				Godfather godfather = this.godfatherDao.findByEmail(email);
				if (godfather != null) {
					// try to login as godfather
					String dbPassword = godfather.getPassword();
					if (Constants.Login.hashUtil.isEqual(password, dbPassword, Constants.Login.hashPepper,
							Constants.Login.hashSeparator)) {
						LoginManager.getInstance().login(request, godfather);
						response.setStatus(SC_LOGIN_SUCCESS);
						return;
					}
				}
			}
		} else if (token != null && InputVerifyUtil.isNotBlank(token)) {
			// find token
			Trainee trainee = this.traineeDao.findByToken(token);
			if (trainee != null) {
				// no password needed
				LoginManager.getInstance().login(request, trainee);
				response.setStatus(SC_LOGIN_SUCCESS);
				return;
			}
		} else {
			// invalid login attempt
			LoggingManager.log(Level.INFO,
					"Client with IP " + request.getRemoteAddr() + " tried to login with no credentials"); //$NON-NLS-1$ //$NON-NLS-2$
			attachCSRFHeader(request, response); // attach new token
			response.sendError(SC_LOGIN_INCOMPLETE);
			return;
		}

		// send error code, when login was wrong
		attachCSRFHeader(request, response); // attach new token
		response.sendError(SC_LOGIN_ERROR);
	}

	/**
	 * Attaches a header to the given {@link HttpServletResponse}, that the client
	 * can use for a new request
	 *
	 * @param request  the {@link HttpServletRequest} containing the
	 *                 {@link HttpSession} of client
	 * @param response the {@link HttpServletResponse}
	 *
	 * @see CSRFPreventionUtil#attachNewTokenToHttpResponse(HttpSession,
	 *      HttpServletResponse, CSRFForm)
	 */
	private static void attachCSRFHeader(@NonNull final HttpServletRequest request,
			@NonNull final HttpServletResponse response) {
		// no valid login, but valid token. So request can be trusted again
		HttpSession session = request.getSession();
		if (session != null) {
			CSRFPreventionUtil.attachNewTokenToHttpResponse(session, response, USERFORM);
		}
	}

}
