package de.db.derPate.servlet.rest;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

import org.eclipse.jdt.annotation.NonNull;

import de.db.derPate.handler.FilterServletHandler;
import de.db.derPate.servlet.FilterServlet;
import de.db.derPate.servlet.filter.ServletFilter;

/**
 * REST abstract base class, used when requests to the REST API should be
 * filtered.
 *
 * @author MichelBlank
 * @see FilterServlet
 */
public abstract class FilterREST extends BaseREST implements ContainerRequestFilter {
	/**
	 * {@link FilterServletHandler} holding the filters
	 */
	private FilterServletHandler filterHandler;

	/**
	 * Constructor setting the filters to the {@link FilterServletHandler}
	 *
	 * @param filters the {@link ServletFilter} that have to apply
	 */
	protected FilterREST(@NonNull ServletFilter... filters) {
		this.filterHandler = new FilterServletHandler(filters);
	}

	/**
	 * Filters all requests, that do not apply to the filters, the object was
	 * initialized with.<br>
	 * Aborts the request, if at least one filter does not apply, and sends the http
	 * status code set in {@link ServletFilter#getErrorStatusCode()}.
	 *
	 * @see FilterServletHandler#isValid(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		if (this.request != null && this.response != null) {
			ServletFilter filter = this.filterHandler.isValid(this.request);
			if (filter != null) {
				// request got filtered, so abort it (as response already sent by handleFilter)
				requestContext.abortWith(Response.status(filter.getErrorStatusCode()).build());
			}
		}
	}
}
