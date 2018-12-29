package de.db.derPate.msc;

import javax.servlet.http.HttpSession;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import de.db.derPate.Constants;
import de.db.derPate.Userform;
import de.db.derPate.util.CSRFPreventionUtil;

/**
 * Uses {@link de.db.derPate.Constants.Security} and {@link Userform} to
 * simplify access to the {@link CSRFPreventionUtil}.
 *
 * @author MichelBlank
 * @see CSRFPreventionUtil
 */
public class CSRFPrevention extends CSRFPreventionUtil {
	private static final int TIMEOUT_SEC = Constants.Security.CSRF_TIMEOUT_IN_SECONDS;

	/**
	 * Generates a random token using the {@link CSRFPreventionUtil} and checks if
	 * the {@link Userform} associated max number of tokens limit is exceeded.
	 *
	 * @param session client's {@link HttpSession}
	 * @param form    form identifier ({@link Userform})
	 * @return random string, that identifies user's form access with the help of
	 *         the {@link HttpSession} or an <b>empty String</b>, if session was
	 *         null
	 */
	@NonNull
	public static String generateToken(@Nullable HttpSession session, @NonNull Userform form) {
		if (session == null) {
			return new String();
		}
		return CSRFPreventionUtil.generateToken(session, form.toString(), form.getMaxTokens(), form.isRequestBased());
	}

	/**
	 * This method checks if the given token was registered in the user's
	 * {@link HttpSession} and if it's not older than
	 * {@value de.db.derPate.Constants.Security#CSRF_TIMEOUT_IN_SECONDS}.<br>
	 * If the token should only be used once, it get's invalidated.
	 *
	 * @param session client's {@link HttpSession}
	 * @param form    form identifier ({@link Userform})
	 * @param token   token received from the client
	 * @return <code>true</code>, if token was registered for the given form
	 *         beforehand and is still valid; <code>false</code>, if token wasn't
	 *         registered or isn't valid anymore due to a timeout
	 */
	public static boolean checkToken(@NonNull HttpSession session, @NonNull Userform form, @NonNull String token) {
		boolean result = CSRFPreventionUtil.checkToken(session, form.toString(), token, TIMEOUT_SEC);
		if (result && form.isRequestBased()) {
			invalidateToken(session, form, token);
		}
		return result;
	}

	/**
	 * Invalidates token
	 *
	 * @param session        client's {@link HttpSession}
	 * @param formidentifier form identifier ({@link Userform})
	 * @param token          token given by the user
	 */
	public static void invalidateToken(@NonNull HttpSession session, @NonNull Userform formidentifier,
			@NonNull String token) {
		CSRFPreventionUtil.invalidateToken(session, formidentifier.toString(), token);
	}
}
