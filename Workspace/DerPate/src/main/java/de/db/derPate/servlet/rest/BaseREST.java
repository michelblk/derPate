package de.db.derPate.servlet.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.eclipse.jdt.annotation.Nullable;

import de.db.derPate.util.ServletUtil;

/**
 * REST abstract base class, used for all rest classes.<br>
 * Contains the {@link HttpServletRequest} and {@link HttpServletResponse}.<br>
 * It automatically sets the character encoding, if {@link HttpServletRequest}
 * and {@link HttpServletResponse} are not null.
 *
 * @author MichelBlank
 */
@Produces({ MediaType.APPLICATION_JSON })
public abstract class BaseREST implements ContainerResponseFilter {
	/**
	 * the {@link HttpServletRequest}
	 */
	@Context
	@Nullable
	protected HttpServletRequest request;
	/**
	 * the {@link HttpServletResponse}
	 */
	@Context
	@Nullable
	protected HttpServletResponse response;

	/**
	 * Sets the character encoding, if {@link HttpServletRequest} and
	 * {@link HttpServletResponse} are not null.
	 *
	 * @see ServletUtil
	 */
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		if (this.request != null && this.response != null) {
			ServletUtil.setCharacterEncoding(this.request, this.response);
		}
	}
}
