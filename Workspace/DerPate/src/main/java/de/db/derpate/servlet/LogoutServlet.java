package de.db.derpate.servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jdt.annotation.NonNull;

import de.db.derpate.CSRFForm;
import de.db.derpate.manager.LoginManager;
import de.db.derpate.model.LoginUser;
import de.db.derpate.servlet.filter.CSRFServletFilter;
import de.db.derpate.servlet.filter.LoginServletFilter;

/**
 * This {@link HttpServlet} is used to remove the relation between the client's
 * {@link HttpSession} and the logged in {@link LoginUser}.<br>
 * Allowed http methods: <code>GET</code>
 *
 * @author MichelBlank
 * @see LoginManager
 */
@WebServlet("/logout")
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
		HttpSession session = request.getSession();

		// logout
		LoginManager.getInstance().logout(session);

		// redirect back to start
		response.sendRedirect(request.getContextPath());
	}
}
