package de.db.derPate.msc;

import javax.servlet.http.HttpSession;

import de.db.derPate.Constants;
import de.db.derPate.util.CSRFPreventionUtil;

/**
 * Uses {@link de.db.derPate.Constants.Security} to
 * 
 * @author MichelBlank
 * @see CSRFPreventionUtil
 */
public class CSRFPrevention {
	/**
	 * Generates a random token using the {@link CSRFPreventionUtil} and the maximum
	 * number of {@value de.db.derPate.Constants.Security#CSRF_MAX_TOKENS} tokens.
	 *
	 * @param session client's {@link HttpSession}
	 * @param form    form identifier
	 * @return random string, that identifies user's form access with the help of
	 *         the {@link HttpSession}
	 */
	public static String generateToken(HttpSession session, String form) {
		return CSRFPreventionUtil.generateToken(session, form, Constants.Security.CSRF_MAX_TOKENS);
	}

	/**
	 * This method checks if the given token was registered in the user's
	 * {@link HttpSession} and if it's not older than
	 * {@value de.db.derPate.Constants.Security#CSRF_TIMEOUT_IN_SECONDS}s
	 *
	 * @param session client's {@link HttpSession}
	 * @param form    form identifier
	 * @param token   token received from the client
	 * @return <code>true</code>, if token was registered for the given form
	 *         beforehand and is still valid; <code>false</code>, if token wasn't
	 *         registered or isn't valid anymore due to a timeout
	 */
	public static boolean checkToken(HttpSession session, String form, String token) {
		return CSRFPreventionUtil.checkToken(session, form, token, Constants.Security.CSRF_TIMEOUT_IN_SECONDS);
	}
}
