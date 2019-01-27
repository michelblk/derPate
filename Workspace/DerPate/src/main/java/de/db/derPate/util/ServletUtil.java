package de.db.derPate.util;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.annotation.NonNull;

import de.db.derPate.Constants;
import de.db.derPate.manager.LoggingManager;

/**
 * This util offers methods, that can be used for {@link HttpServlet}s.<br>
 *
 * @author MichelBlank
 */
public class ServletUtil {
	/**
	 * Sets default character encoding ({link Constants#CHARSET}) to request and
	 * response.
	 *
	 * @param request  the {@link HttpServletRequest}
	 * @param response the {@link HttpServletResponse}
	 */
	public static void setCharacterEncoding(@NonNull final HttpServletRequest request,
			@NonNull final HttpServletResponse response) {
		try {
			request.setCharacterEncoding(Constants.CHARSET.name());
			response.setCharacterEncoding(Constants.CHARSET.name());
		} catch (UnsupportedEncodingException e) {
			LoggingManager.log(Level.WARNING,
					"Servlet could not set charset " + Constants.CHARSET + ": " + e.getMessage()); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}
}
