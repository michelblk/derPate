package de.db.derpate.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import de.db.derpate.servlet.filter.ServletFilter;

/**
 * This handler manages {@link ServletFilter}s and checks, if a request applies
 * to all filters.
 *
 * @author MichelBlank
 * @see de.db.derpate.servlet.filter
 */
public class FilterServletHandler {
	/**
	 * Filters set by constructor
	 */
	@NonNull
	private ServletFilter[] filters;

	/**
	 * Constructor, that expects {@link ServletFilter}s, that get stored and used
	 * later, when handling a request
	 *
	 * @param filters array of {@link ServletFilter}s. May not be <code>null</code>.
	 */
	public FilterServletHandler(@NonNull ServletFilter... filters) {
		this.filters = filters;
	}

	/**
	 * Checks, if all filters apply and returns the filter, that was failing.
	 *
	 * @param req the {@link HttpServletRequest} that should be checked
	 * @return the {@link ServletFilter} that makes the request failing or
	 *         <code>null</code>, if all filters apply
	 */
	@Nullable
	public ServletFilter isValid(@NonNull HttpServletRequest req) {
		for (ServletFilter filter : this.filters) {
			if (!filter.filter(req)) {
				return filter;
			}
		}
		return null;
	}

	/**
	 * Uses the filters to check, if a request was valid and responds with an error
	 * (set by {@link ServletFilter#getErrorStatusCode()}), if one of the filters
	 * forbids request.
	 *
	 * @param req  {@link HttpServletRequest}
	 * @param resp {@link HttpServletResponse}
	 * @return <code>true</code>, if all filters applied; <code>false</code>, if at
	 *         least one filter forbids the request
	 * @throws IOException if an input or output exception occurs, while sending
	 *                     error
	 */
	public boolean handleFilter(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		ServletFilter filter = this.isValid(req);
		if (filter != null) {
			resp.sendError(filter.getErrorStatusCode());
			return false;
		}
		return true;
	}
}
