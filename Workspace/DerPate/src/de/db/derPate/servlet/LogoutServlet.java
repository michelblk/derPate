package de.db.derPate.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jdt.annotation.NonNull;

import de.db.derPate.CSRFForm;
import de.db.derPate.manager.LoginManager;
import de.db.derPate.model.LoginUser;
import de.db.derPate.servlet.filter.CSRFServletFilter;
import de.db.derPate.servlet.filter.LoginServletFilter;

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
public class LogoutServlet extends FilterServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public LogoutServlet() {
		super(new CSRFServletFilter(CSRFForm.LOGOUT), new LoginServletFilter());
	}

	/**
	 * This method handles GET-Requests coming from the Client. It removes the
	 * connection between client's {@link HttpSession} and {@link LoginUser}, and
	 * redirects to the start page.
	 */
	@Override
	protected void onGet(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response)
			throws IOException {
		// get data
		HttpSession session = request.getSession(true);

		// logout
		LoginManager.getInstance().logout(session);

		// redirect back to start
		response.sendRedirect(request.getContextPath());
	}
}
