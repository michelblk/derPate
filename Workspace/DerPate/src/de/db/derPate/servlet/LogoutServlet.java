package de.db.derPate.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jdt.annotation.Nullable;

import de.db.derPate.Userform;
import de.db.derPate.manager.LoginManager;
import de.db.derPate.model.LoginUser;

/**
 * This servlet is used to remove the relation between the client's
 * {@link HttpSession} and the logged in {@link LoginUser}.<br>
 * Caution: This servlet is even reachable when the user is not even logged
 * in!<br>
 * Allowed http methods: <code>GET</code>
 *
 * @author MichelBlank
 * @see LoginManager
 */
public class LogoutServlet extends CSRFCheckServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public LogoutServlet() {
		super(Userform.LOGOUT);
	}

	/**
	 * This method handles GET-Requests coming from the Client. It removes the
	 * connection between client's {@link HttpSession} and {@link LoginUser}, and
	 * redirects to the start page.
	 */
	@Override
	protected void doGet(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response)
			throws ServletException, IOException {
		if (request == null || response == null) {
			return;
		}
		if (!this.handleCSRFToken(request, response)) {
			return;
		}

		// get data
		HttpSession session = request.getSession(true);

		// logout
		LoginManager.getInstance().logout(session);

		// redirect back to start
		response.sendRedirect(request.getContextPath());
	}

}
