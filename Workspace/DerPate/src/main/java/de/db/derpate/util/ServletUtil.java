package de.db.derpate.util;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import de.db.derpate.Constants;
import de.db.derpate.manager.LoggingManager;

/**
 * This util offers methods, that can be used for {@link HttpServlet}s.<br>
 *
 * @author MichelBlank
 */
public final class ServletUtil {
	private ServletUtil() {
		// nothing to do
	}

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

	/**
	 * Replaces a null value with an empty {@link String}
	 *
	 * @param string the {@link Object}
	 * @return {@link Object#toString()} or empty {@link String}
	 */
	@SuppressWarnings("null")
	@NonNull
	public static String replaceNullWithEmptyString(@Nullable Object string) {
		return string != null ? string.toString() : ""; //$NON-NLS-1$
	}
}
