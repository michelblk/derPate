package de.db.derpate.servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.annotation.NonNull;

import de.db.derpate.Usermode;
import de.db.derpate.manager.LoginManager;
import de.db.derpate.model.LoginUser;

/**
 * This {@link HttpServlet} redirects the client to the default welcome page<br>
 * Allowed methods: <code>GET</code>
 *
 * @author MichelBlank
 *
 */
@WebServlet("/home")
public class RedirectToHomeServlet extends BaseServlet {
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void get(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		LoginUser user = LoginManager.getInstance().getUserBySession(req.getSession());
		Usermode mode = LoginManager.getInstance().getUsermode(user);

		String target = req.getContextPath() + "/"; //$NON-NLS-1$

		if (mode != null) { // user is logged in
			switch (mode) {
			case ADMIN:
				target += "admin/adminWelcome.jsp"; //$NON-NLS-1$
				break;
			case GODFATHER:
				target += "godfather/godfatherUpdate.jsp"; //$NON-NLS-1$
				break;
			case TRAINEE:
				target += "trainee/filter.jsp"; //$NON-NLS-1$
				break;
			}
		}

		resp.sendRedirect(target);
	}
}
