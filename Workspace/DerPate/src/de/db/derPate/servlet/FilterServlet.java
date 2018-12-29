package de.db.derPate.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.annotation.NonNull;

import de.db.derPate.servlet.filter.ServletFilter;

/**
 * This abstract class extends the {@link BaseServlet} and expects
 * {@link ServletFilter}s (constructor), that check, if the request is valid
 * (for example, if the user is logged in).<br>
 * Use {@link BaseServlet} instead, if a servlet should be publicly available
 * without any restriction.
 *
 * @author MichelBlank
 * @see BaseServlet
 */
public abstract class FilterServlet extends BaseServlet {
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Filters set by constructor
	 */
	@NonNull
	private ServletFilter[] filters;

	/**
	 * Constructor, that expects {@link ServletFilter}s, that get stored and used
	 * later, when handling a request
	 *
	 * @param filters array of {@link ServletFilter}s. May not be null.
	 */
	public FilterServlet(@NonNull ServletFilter... filters) {
		this.filters = filters;
	}

	/**
	 * Uses the filters to check, if a request was valid and responds with an error,
	 * if one of the filters forbids request.
	 *
	 * @param req  {@link HttpServletRequest}
	 * @param resp {@link HttpServletResponse}
	 * @return <code>true</code>, if all filters applied; <code>false</code>, if at
	 *         least one filter forbids the request
	 * @throws IOException if an input or output exception occurs, while sending
	 *                     error
	 */
	private boolean handleFilter(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp)
			throws IOException {
		for (ServletFilter filter : this.filters) {
			if (!filter.filter(req)) {
				resp.sendError(filter.getErrorStatusCode());
				return false;
			}
		}
		return true;
	}

	/**
	 * Handles get request. Checks if all filters apply and calls
	 * {@link #onGet(HttpServletRequest, HttpServletResponse)}, if they do.
	 */
	@Override
	protected final void get(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		if (this.handleFilter(req, resp)) {
			this.onGet(req, resp);
		}
	}

	/**
	 * Method, that get's called, after all filters were checked and should handle
	 * get requests.<br>
	 * By default, responds with a "method not allowed" (code
	 * {@value HttpServletResponse#SC_METHOD_NOT_ALLOWED}) http status, when not
	 * overwritten.
	 *
	 * @param req  {@link HttpServletRequest}
	 * @param resp {@link HttpServletResponse}
	 * @throws IOException if an input or output exception occurs, while sending
	 *                     error
	 */
	protected void onGet(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		return;
	}

	/**
	 * Handles post request. Checks if all filters apply and calls
	 * {@link #onPost(HttpServletRequest, HttpServletResponse)}, if they do.
	 */
	@Override
	protected final void post(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		if (!this.handleFilter(req, resp)) {
			return;
		}
		this.onPost(req, resp);
	}

	/**
	 * Method, that get's called, after all filters were checked and should handle
	 * post requests.<br>
	 * By default, responds with a "method not allowed" (code
	 * {@value HttpServletResponse#SC_METHOD_NOT_ALLOWED}) http status, when not
	 * overwritten.
	 *
	 * @param req  {@link HttpServletRequest}
	 * @param resp {@link HttpServletResponse}
	 * @throws IOException if an input or output exception occurs, while sending
	 *                     error
	 */
	protected void onPost(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		return;
	}

}
