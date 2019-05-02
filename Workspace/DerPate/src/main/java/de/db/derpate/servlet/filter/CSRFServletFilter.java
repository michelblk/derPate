package de.db.derpate.servlet.filter;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import de.db.derpate.CSRFForm;
import de.db.derpate.servlet.FilterServlet;
import de.db.derpate.util.CSRFPreventionUtil;

/**
 * This filter can be used to make sure, that a request contains a valid csrf
 * token.<br>
 * It can only be used by the {@link FilterServlet} and cannot be used by the
 * native filter servlet mechanism, as it does not implement {@link Filter}.<br>
 * USAGE: SERVLET/REST only
 *
 * @author MichelBlank
 */
public class CSRFServletFilter implements ServletFilter {
	/**
	 * {@link CSRFForm} the CSRF check is for
	 */
	@NonNull
	private CSRFForm csrfForm;

	/**
	 * Constructor
	 *
	 * @param csrfForm {@link CSRFForm} the token is valid for
	 */
	public CSRFServletFilter(@NonNull CSRFForm csrfForm) {
		this.csrfForm = csrfForm;
	}

	/**
	 * Checks (and invalidates, if necessary) a CSRF token provided by a header or
	 * parameter in the {@link HttpServletRequest}.
	 *
	 * @param req the {@link HttpServletRequest}
	 * @return <code>true</code>, if a valid token was found; <code>false</code>, if
	 *         no valid token was found
	 */
	@Override
	public boolean filter(@NonNull HttpServletRequest req) {
		String csrfToken = getCSRFToken(req);
		HttpSession session = req.getSession();

		return (csrfToken != null && session != null
				&& CSRFPreventionUtil.checkToken(session, this.csrfForm, csrfToken));
	}

	@Override
	public int getErrorStatusCode() {
		return CSRFPreventionUtil.SC_INVALID_TOKEN;
	}

	/**
	 * Returns the CSRF token provided by the {@link HttpServletRequest}s
	 * HTTP-Header or Parameter.
	 *
	 * @param request the {@link HttpServletRequest}
	 * @return the CSRF token or <code>null</code>, if no token was found
	 */
	@Nullable
	private static String getCSRFToken(@NonNull HttpServletRequest request) {
		String header = request.getHeader(CSRFPreventionUtil.HEADER_FIELD);
		String attribute = request.getParameter(CSRFPreventionUtil.FIELD_NAME); // automatically unescaped characters

		return (header != null ? header : attribute);
	}

}
