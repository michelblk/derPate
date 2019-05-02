package de.db.derpate.servlet.filter;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.annotation.NonNull;

import de.db.derpate.servlet.FilterServlet;

/**
 * Interface used for ServletFilters, that are used in the
 * {@link FilterServlet}.<br>
 * It cannot be used by the native filter servlet mechanism (for jsp), as it
 * does not extend {@link Filter}.
 *
 * @author MichelBlank
 */
public interface ServletFilter {

	/**
	 * Method, that returns if the filter applies.
	 *
	 * @param req the {@link HttpServletRequest}
	 * @return <code>true</code>, if request is valid;<code>false</code>, if request
	 *         is missing required informations or is not valid
	 */
	boolean filter(@NonNull HttpServletRequest req);

	/**
	 * Returns the default status code ({@value HttpServletResponse#SC_FORBIDDEN}},
	 * which is responded, when a filter forbids a request.<br>
	 * Can be overwritten.
	 *
	 * @return http status code (int)
	 */
	default int getErrorStatusCode() {
		return HttpServletResponse.SC_FORBIDDEN;
	}
}
