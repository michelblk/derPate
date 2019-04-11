package de.db.derpate.servlet.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import de.db.derpate.Usermode;
import de.db.derpate.manager.LoginManager;
import de.db.derpate.servlet.FilterServlet;

/**
 * This filter checks, if a client is logged in.<br>
 * It can be used with the {@link FilterServlet}, as well as a native
 * {@link Filter}.<br>
 * USAGE: SERVLET/REST, JSP/via WEB.XML
 *
 * @author MichelBlank
 * @see LoginManager
 */
public class LoginServletFilter implements ServletFilter, Filter {
	/**
	 * The {@link Usermode}s that are allowed to be able to open the site. Might be
	 * null, if no special {@link Usermode} is required.
	 */
	@Nullable
	private Usermode[] requiredUsermode;

	/**
	 * Default constructor<br>
	 * Used, when all users are allowed
	 */
	public LoginServletFilter() {
		// no need to initialize anything
	}

	/**
	 * Constructor used, when a specific {@link Usermode} is required
	 *
	 * @param requiredUsermode {@link Usermode}, that are allowed
	 */
	public LoginServletFilter(@Nullable Usermode... requiredUsermode) {
		this.requiredUsermode = requiredUsermode;
	}

	/**
	 * Method used by the {@link FilterServlet} to make sure, a servlet only gets
	 * used by a logged in user and the correct {@link Usermode} (if it was set by
	 * constructor).<br>
	 * If no usermode was set, all logged in users are allowed.
	 *
	 * @return <code>true</code>, if user is logged in and has the required
	 *         {@link Usermode} (if it was set by constructor); <code>false</code>,
	 *         if user was not logged in or is not permitted
	 */
	@Override
	public boolean filter(@NonNull HttpServletRequest req) {
		boolean isLoggedIn = LoginManager.getInstance().isLoggedIn(req.getSession());
		boolean result = isLoggedIn;
		if (result == true && this.requiredUsermode != null) {
			result = LoginManager.getInstance().isUserOfSessionInUsermode(req.getSession(), this.requiredUsermode);
		}

		return result;
	}

	/**
	 * Method used for native filtering (jsp).<br>
	 * It sends an error to the client, if client is not logged in
	 * ({@link #getErrorStatusCode()}).<br>
	 * If client is logged in, the next filter in the filter chain get's called.
	 */
	@Override
	public void doFilter(@Nullable ServletRequest request, @Nullable ServletResponse response,
			@Nullable FilterChain chain) throws IOException, ServletException {
		if (request != null && response != null && chain != null) {
			if (!this.filter((HttpServletRequest) request)) {
				((HttpServletResponse) response).sendError(this.getErrorStatusCode());
			} else {
				chain.doFilter(request, response);
			}
		}
	}

	@SuppressWarnings("null")
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		if (filterConfig != null) {
			String usermodes = filterConfig.getInitParameter("usermode"); //$NON-NLS-1$
			String[] usermode = usermodes.split(","); //$NON-NLS-1$
			ArrayList<Usermode> wantedUsermodes = new ArrayList<>();
			for (String mode : usermode) {
				Usermode modeToAdd = Usermode.valueOf(mode);
				if (modeToAdd != null) {
					wantedUsermodes.add(modeToAdd);
				}
			}
			this.requiredUsermode = wantedUsermodes.toArray(new Usermode[0]);
		}
	}

	@Override
	public void destroy() {
		// not useds
	}
}
