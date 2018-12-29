package de.db.derPate.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import de.db.derPate.Constants;
import de.db.derPate.manager.LoggingManager;

/**
 * This abstract {@link HttpServlet} should be used for all created servlets, as
 * it sets the character encoding and stops request, if request or response is
 * null.<br>
 * <b>Supported Methods: GET, POST</b>
 *
 * @author MichelBlank
 *
 */
public abstract class BaseServlet extends HttpServlet {
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Sets default character encoding ({@value Constants#CHARSET}) to request and
	 * response
	 *
	 * @param request  {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 */
	protected static void setCharacterEncoding(@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response) {
		try {
			request.setCharacterEncoding(Constants.CHARSET);
			response.setCharacterEncoding(Constants.CHARSET);
		} catch (UnsupportedEncodingException e) {
			LoggingManager.log(Level.WARNING,
					"Servlet could not set charset " + Constants.CHARSET + ": " + e.getMessage()); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Handles get request, sets character encoding and calls custom get method
	 * ({@link #get(HttpServletRequest, HttpServletResponse)}
	 */
	@Override
	protected final void doGet(@Nullable HttpServletRequest req, @Nullable HttpServletResponse resp)
			throws ServletException, IOException {
		if (req != null && resp != null) {
			setCharacterEncoding(req, resp);
			this.get(req, resp);
		}
	}

	/**
	 * This method handles get requests, after it was initiated by
	 * {@link #doGet(HttpServletRequest, HttpServletResponse)}.<br>
	 * By default, it sends a "method not allowed" (code
	 * {@value HttpServletResponse#SC_METHOD_NOT_ALLOWED}) http error as response,
	 * if this method does not get overwritten by subclass.
	 *
	 * @param req  {@link HttpServletRequest}
	 * @param resp {@link HttpServletResponse}
	 * @throws IOException if an input or output exception occurs
	 */
	protected void get(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * Handles post request, sets character encoding and calls custom post method
	 * ({@link #post(HttpServletRequest, HttpServletResponse)}
	 */
	@Override
	protected final void doPost(@Nullable HttpServletRequest req, @Nullable HttpServletResponse resp)
			throws ServletException, IOException {
		if (req != null && resp != null) {
			setCharacterEncoding(req, resp);
			this.post(req, resp);
		}
	}

	/**
	 * This method handles post requests, after it was initiated by
	 * {@link #doPost(HttpServletRequest, HttpServletResponse)}.<br>
	 * By default, it sends a "method not allowed" (code
	 * {@value HttpServletResponse#SC_METHOD_NOT_ALLOWED}) http error as response,
	 * if this method does not get overwritten by subclass.
	 *
	 * @param req  {@link HttpServletRequest}
	 * @param resp {@link HttpServletResponse}
	 * @throws IOException if an input or output exception occurs
	 */
	protected void post(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

}
