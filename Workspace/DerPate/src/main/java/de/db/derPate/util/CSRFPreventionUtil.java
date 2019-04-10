package de.db.derPate.util;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import de.db.derPate.CSRFForm;

/**
 * This util should be used to prevent cross-site request forgery. It creates
 * random tokens, which can be used in hidden fields in a form. The backend then
 * checks if the token is valid. That way it is safe to say, that the user
 * filled out the form and did not just used the backend interface.
 *
 * @author MichelBlank
 */
public final class CSRFPreventionUtil {
	/**
	 * frontend html field name for use in an hidden input field
	 */
	public static final String FIELD_NAME = "csrf_token"; //$NON-NLS-1$
	/**
	 * Token length in bytes
	 */
	public static final int TOKEN_LENGTH = 20;
	/**
	 * Header that can be used alternatively (or is sent by server, if old one got
	 * invalidated)
	 */
	public static final String HEADER_FIELD = "X-Csrf-Token"; //$NON-NLS-1$
	/**
	 * Http Status Code sent, when token was invalid
	 */
	public static final int SC_INVALID_TOKEN = HttpServletResponse.SC_GONE;
	/**
	 * key, which is used to store the {@link Map} in {@link HttpSession}'s
	 * attributes
	 */
	public static final String SESSION_ATTRIBUTE_PREFIX = "csrfTokenList_"; //$NON-NLS-1$

	private CSRFPreventionUtil() {
		// nothing to do
	}

	/**
	 * This method generates a token and registers it in the user's
	 * {@link HttpSession}.<br>
	 * The session should not be null, as the token cannot be persisted without
	 *
	 * @param session client's {@link HttpSession}
	 * @param form    {@link CSRFForm} used to limit token to this specific form
	 * @return random string
	 */
	@NonNull
	public static String generateToken(@Nullable final HttpSession session, @NonNull final CSRFForm form) {
		@Nullable
		String formToken = null;
		if (session != null) {
			formToken = getFormTokenFromSession(session, form);
		}

		String returnValue;
		if (formToken == null) { // if no token for this form was set, yet
			// set new token
			returnValue = generateRandomString(TOKEN_LENGTH);

			// save token to session
			if (session != null) {
				saveToSession(session, form, returnValue);
			}
		} else {
			returnValue = formToken;
		}

		return returnValue;
	}

	/**
	 * Automatically escapes characters for use as a uri paramter.
	 *
	 * @param session client's {@link HttpSession}
	 * @param form    {@link CSRFForm} used to limit token to this specific form
	 * @return random string
	 * @see #generateToken(HttpSession, CSRFForm)
	 */
	public static String generateTokenForGETParameter(@Nullable final HttpSession session,
			@NonNull final CSRFForm form) {
		return StringEscapeUtil.encodeURL(generateToken(session, form));
	}

	/**
	 * Generates a random {@link Base64} {@link String}
	 *
	 * @param length number of <b>bytes</b> generated
	 * @return random {@link String}
	 */
	@SuppressWarnings("null")
	@NonNull
	private static String generateRandomString(final int length) {
		byte[] bytes = new byte[length];
		new Random().nextBytes(bytes);

		return Base64.getEncoder().encodeToString(bytes);
	}

	/**
	 * Returns the name of the session attribute (prefix
	 * ({@value #SESSION_ATTRIBUTE_PREFIX}) + {@link CSRFForm#toString()}).
	 *
	 * @param form the {@link CSRFForm}
	 * @return name
	 */
	private static String getSessionAttributeName(@NonNull final CSRFForm form) {
		return SESSION_ATTRIBUTE_PREFIX + form.toString();
	}

	/**
	 * Returns the CSRF-Token for a given form and session
	 *
	 * @param session client's {@link HttpSession}
	 * @param form    {@link CSRFForm}
	 * @return a Token generated for the given form and user or <code>null</code>,
	 *         if no token was generated yet
	 */
	@Nullable
	private static String getFormTokenFromSession(@NonNull final HttpSession session, @NonNull final CSRFForm form) {
		String formToken = null;
		// load from session
		Object sessionTokens = session.getAttribute(getSessionAttributeName(form));
		if (sessionTokens instanceof String) {
			formToken = (String) sessionTokens;
		}

		return formToken;
	}

	/**
	 * Sets the session attributes based on the given form
	 *
	 * @param session client's {@link HttpSession}
	 * @param form    {@link CSRFForm}
	 * @param list    {@link ArrayList} to set
	 */
	private static void saveToSession(@NonNull final HttpSession session, @NonNull final CSRFForm form,
			@NonNull final String token) {
		// reset session
		session.setAttribute(getSessionAttributeName(form), token);
	}

	/**
	 * This method checks if the given token was registered in the user's
	 * {@link HttpSession}
	 *
	 * @param session client's {@link HttpSession}
	 * @param form    {@link CSRFForm}
	 * @param token   token given by the user
	 * @return <code>true</code>, if token was registered for the given form
	 *         beforehand; <code>false</code>, if token wasn't registered
	 */
	public static boolean checkToken(@NonNull final HttpSession session, @NonNull final CSRFForm form,
			@NonNull final String token) {
		return token.equals(getFormTokenFromSession(session, form));
	}

	/**
	 * Attaches a http header to the {@link HttpServletResponse}, that includes a
	 * new valid csrf token<br>
	 * <b>Should be used, when a valid token was sent beforehand!</b>
	 *
	 * @param session  the {@link HttpSession}
	 * @param response the {@link HttpServletResponse}
	 * @param form     the {@link CSRFForm}
	 */
	public static void attachNewTokenToHttpResponse(@NonNull final HttpSession session,
			@NonNull final HttpServletResponse response, @NonNull final CSRFForm form) {
		response.setHeader(HEADER_FIELD, generateToken(session, form));
	}
}
