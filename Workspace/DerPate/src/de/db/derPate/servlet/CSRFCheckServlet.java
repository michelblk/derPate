/**
 *
 */
package de.db.derPate.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import de.db.derPate.Userform;
import de.db.derPate.msc.CSRFPrevention;
import de.db.derPate.util.CSRFPreventionUtil;

/**
 * @author MichelBlank
 *
 */
public abstract class CSRFCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * {@link Userform} the CSRF check is for
	 */
	@NonNull
	protected Userform userform;

	/**
	 * Constructor
	 *
	 * @param userform {@link Userform} used to generate tokens
	 */
	public CSRFCheckServlet(@NonNull Userform userform) {
		super();
		this.userform = userform;
	}

	/**
	 * Returns the CSRF token provided by the {@link HttpServletRequest}s
	 * HTTP-Header or Parameter.
	 *
	 * @param request the {@link HttpServletRequest}
	 * @return the CSRF token or <code>null</code>, if no token was found
	 */
	@Nullable
	protected String getCSRFToken(@NonNull HttpServletRequest request) {
		String header = request.getHeader(CSRFPreventionUtil.HEADER_FIELD);
		String attribute = request.getParameter(CSRFPreventionUtil.FIELD_NAME);

		return (header != null ? header : attribute);
	}

	/**
	 * Checks (and invalidates, if necessary) a CSRF token provided by a header or
	 * parameter in the {@link HttpServletRequest}.
	 *
	 * @param request the {@link HttpServletRequest}
	 * @return <code>true</code>, if a valid token was found; <code>false</code>, if
	 *         no valid token was found
	 */
	protected boolean checkCSRFToken(@NonNull HttpServletRequest request) {
		String csrfToken = this.getCSRFToken(request);
		HttpSession session = request.getSession();

		return (csrfToken != null && session != null && CSRFPrevention.checkToken(session, this.userform, csrfToken));
	}

	/**
	 * Checks and invalidates a CSRF token provided by a header or parameter in the
	 * {@link HttpServletRequest} and set's the status code to
	 * {@value CSRFPreventionUtil#SC_INVALID_TOKEN}, if no valid token was found.
	 *
	 * @param request  the {@link HttpServletRequest}
	 * @param response the {@link HttpServletResponse}
	 * @return <code>true</code>, if a valid token was found and no status code
	 *         needed to be set; <code>false</code>, if no valid token was found and
	 *         the http status code was set to
	 *         {@value CSRFPreventionUtil#SC_INVALID_TOKEN}
	 */
	protected boolean handleCSRFToken(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response) {
		boolean hasToken = this.checkCSRFToken(request);
		if (!hasToken) {
			response.setStatus(CSRFPreventionUtil.SC_INVALID_TOKEN);
		}
		return hasToken;
	}

	/**
	 * Uses the http header "{@value CSRFPreventionUtil#HEADER_FIELD}" to give the
	 * client a new valid CSRF token, after the old one was invalidated.
	 *
	 * @param session  the {@link HttpSession}
	 * @param response the {@link HttpServletResponse}
	 */
	protected void respondWithNewToken(@NonNull HttpSession session, @NonNull HttpServletResponse response) {
		response.setHeader(CSRFPreventionUtil.HEADER_FIELD, CSRFPrevention.generateToken(session, this.userform));
	}
}
