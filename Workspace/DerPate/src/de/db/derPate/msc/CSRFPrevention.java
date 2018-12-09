package de.db.derPate.msc;

import javax.servlet.http.HttpSession;

import de.db.derPate.Constants;
import de.db.derPate.Userform;
import de.db.derPate.util.CSRFPreventionUtil;

/**
 * Uses {@link Constants.Security} and {@link Userform} to simplify access to
 * the {@link CSRFPreventionUtil}.
 *
 * @author MichelBlank
 * @see CSRFPreventionUtil
 */
public class CSRFPrevention extends CSRFPreventionUtil {
	private static final int TIMEOUT_SEC = Constants.Security.CSRF_TIMEOUT_IN_SECONDS;

	/**
	 * Generates a random token using the {@link CSRFPreventionUtil} and the maximum
	 * number of {@value de.db.derPate.Constants.Security#CSRF_MAX_TOKENS} tokens.
	 *
	 * @param session client's {@link HttpSession}
	 * @param form    form identifier ({@link Userform})
	 * @return random string, that identifies user's form access with the help of
	 *         the {@link HttpSession}
	 */
	public static String generateToken(HttpSession session, Userform form) {
		return CSRFPreventionUtil.generateToken(session, form.toString(), form.getMaxTokens());
	}

	/**
	 * This method checks if the given token was registered in the user's
	 * {@link HttpSession} and if it's not older than
	 * {@value de.db.derPate.Constants.Security#CSRF_TIMEOUT_IN_SECONDS}s
	 *
	 * @param session client's {@link HttpSession}
	 * @param form    form identifier ({@link Userform})
	 * @param token   token received from the client
	 * @return <code>true</code>, if token was registered for the given form
	 *         beforehand and is still valid; <code>false</code>, if token wasn't
	 *         registered or isn't valid anymore due to a timeout
	 */
	public static boolean checkToken(HttpSession session, Userform form, String token) {
		return CSRFPreventionUtil.checkToken(session, form.toString(), token, TIMEOUT_SEC);
	}

	/**
	 * Invalidates token
	 *
	 * @param session        client's {@link HttpSession}
	 * @param formidentifier form identifier ({@link Userform})
	 * @param token          token given by the user
	 */
	public static void invalidateToken(HttpSession session, Userform formidentifier, String token) {
		CSRFPreventionUtil.invalidateToken(session, formidentifier.toString(), token);
	}

	/**
	 * Checks if token is valid (using
	 * {@link #checkToken(HttpSession, String, String, int)}) and invalidates it
	 * (using {@link #invalidateToken(HttpSession, String, String)}), so the token
	 * can be used just one time.
	 *
	 * @param session          client's {@link HttpSession}
	 * @param formidentifier   form identifier ({@link Userform})
	 * @param token            token given by the user
	 * @param timeoutInSeconds timeout in seconds
	 * @return <code>true</code>, if token was registered for the given form
	 *         beforehand and is still valid; <code>false</code>, if token wasn't
	 *         registered or isn't valid anymore due to a timeout
	 * @see #checkToken(HttpSession, String, String, int)
	 */
	public static boolean checkAndInvalidateToken(HttpSession session, Userform formidentifier, String token) {
		return CSRFPreventionUtil.checkAndInvalidateToken(session, formidentifier.toString(), token, TIMEOUT_SEC);
	}
}
