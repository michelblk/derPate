package de.db.derPate.servlet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import de.db.derPate.manager.LoginManager;
import de.db.derPate.servlet.FilterServlet;

/**
 * This filter checks, if a client is logged in.<br>
 * It can be used with the {@link FilterServlet}, as well as a native
 * {@link Filter}.<br>
 * USAGE: SERVLETS, JSP
 *
 * @author MichelBlank
 * @see LoginManager
 */
public class LoginServletFilter implements ServletFilter, Filter {
	/**
	 * Default constructor
	 */
	public LoginServletFilter() {
		// no need to initialize anything
	}

	/**
	 * Method used by the {@link FilterServlet} to make sure, a servlet only gets
	 * used by a logged in user.
	 *
	 * @return <code>true</code>, if user is logged in; <code>false</code>, if user
	 *         was not logged in
	 */
	@Override
	public boolean filter(@NonNull HttpServletRequest req) {
		return LoginManager.getInstance().isLoggedIn(req.getSession());
	}

	/**
	 * Method used for native filtering.<br>
	 * It sends an error to the client, if client is not logged in
	 * ({@link #getErrorStatusCode()}).<br>
	 * If client is logged in, the next filter in the filter chain get's called.
	 */
	@Override
	public void doFilter(@Nullable ServletRequest request, @Nullable ServletResponse response,
			@Nullable FilterChain chain) throws IOException, ServletException {
		if (request != null && response != null && chain != null) {
			if (this.filter((HttpServletRequest) request)) {
				((HttpServletResponse) response).sendError(this.getErrorStatusCode());
			} else {
				chain.doFilter(request, response);
			}
		}
	}
}
