package de.db.derPate.util;

import java.util.Base64;
import java.util.LinkedHashMap;
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
public class CSRFPreventionUtil {
	/**
	 * frontend html field name for use in an hidden input field
	 */
	public static final String FIELD_NAME = "csrf_token"; //$NON-NLS-1$
	/**
	 *
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
	public static final String SESSION_ATTRIBUTE_PREFIX = "csrfTokenMap_"; //$NON-NLS-1$

	/**
	 * This method generates a token and registers it in the user's
	 * {@link HttpSession}.<br>
	 * The session should not be null, as the token cannot be persisted without
	 *
	 * @param session client's {@link HttpSession}
	 * @param form    {@link CSRFForm} used to limit token to this specific form
	 * @return random string
	 */
	@SuppressWarnings("null")
	@NonNull
	public static String generateToken(@Nullable final HttpSession session, @NonNull final CSRFForm form) {
		LinkedHashMap<@NonNull String, @NonNull Long> formTokens;
		if (session != null) {
			formTokens = getFormTokens(session, form);
		} else {
			formTokens = new LinkedHashMap<>();
		}

		String randomToken;
		if (!form.isRequestBased() && formTokens.size() >= 1) { // is session based and was already generated
			// a token per session and form
			randomToken = formTokens.entrySet().iterator().next().getKey(); // return first entry
		} else {
			// a new token for each request
			// check if limit is exceeded and remove oldest token, if necessary
			checkLimit(formTokens, form.getMaxTokens());

			// set new token
			randomToken = generateUniqueToken(formTokens);
			formTokens.put(randomToken, System.currentTimeMillis());

			// save token to session
			if (session != null) {
				saveToSession(session, form, formTokens);
			}
		}
		return randomToken;
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
	 * Receives Map of tokens out of a Map of forms stored in the client's
	 * {@link HttpSession}.
	 *
	 * @param session client's {@link HttpSession}
	 * @param form    {@link CSRFForm}
	 * @return a {@link LinkedHashMap} of Tokens generated for the given form and
	 *         user
	 */
	@SuppressWarnings("unchecked")
	@NonNull
	private static LinkedHashMap<@NonNull String, @NonNull Long> getFormTokens(@NonNull final HttpSession session,
			@NonNull final CSRFForm form) {
		LinkedHashMap<@NonNull String, @NonNull Long> formTokens = new LinkedHashMap<>();

		// load from session
		Object sessionTokens = session.getAttribute(getSessionAttributeName(form));
		if (sessionTokens != null && sessionTokens instanceof LinkedHashMap) {
			formTokens = (LinkedHashMap<@NonNull String, @NonNull Long>) sessionTokens;
		}

		return formTokens;
	}

	/**
	 * Sets the session attributes based on the given form
	 *
	 * @param session client's {@link HttpSession}
	 * @param form    {@link CSRFForm}
	 * @param map     {@link LinkedHashMap} to set
	 */
	private static void saveToSession(@NonNull final HttpSession session, @NonNull final CSRFForm form,
			@NonNull final LinkedHashMap<String, Long> map) {
		// reset session
		session.setAttribute(getSessionAttributeName(form), map);
	}

	/**
	 * Checks, if the limit is exceeded and removes the oldest entry, if necessary.
	 *
	 * @param map       Map, that should be checked
	 * @param maxTokens the maximum count of entries
	 */
	private static void checkLimit(@NonNull final LinkedHashMap<String, Long> map, final int maxTokens) {
		while (map.size() >= maxTokens) {
			kickOldestToken(map);
		}
	}

	/**
	 * Removes the oldest(first) entry in {@link LinkedHashMap}
	 *
	 * @param map {@link LinkedHashMap}
	 */
	private static void kickOldestToken(@NonNull final LinkedHashMap<String, Long> map) {
		map.remove(map.entrySet().iterator().next().getKey()); // removes first in iterator
	}

	/**
	 * Generates a random token using {@link #generateRandomString(int)} and makes
	 * sure, that this token isn't already in use by the same user for the same
	 * form.<br>
	 * The token will have a length of {@value #TOKEN_LENGTH} bytes.
	 *
	 * @param map {@link LinkedHashMap}, with token as key
	 * @return random token
	 */
	@NonNull
	private static String generateUniqueToken(@NonNull final LinkedHashMap<String, Long> map) {
		boolean foundToken = false;
		String randomToken;
		do {
			randomToken = generateRandomString(TOKEN_LENGTH);
			foundToken = !map.containsKey(randomToken);
		} while (!foundToken);
		return randomToken;
	}

	/**
	 * This method checks if the given token was registered in the user's
	 * {@link HttpSession} and if it's not older than the given seconds
	 *
	 * @param session client's {@link HttpSession}
	 * @param form    {@link CSRFForm}
	 * @param token   token given by the user
	 * @return <code>true</code>, if token was registered for the given form
	 *         beforehand and is still valid; <code>false</code>, if token wasn't
	 *         registered or got removed
	 */
	public static boolean checkToken(@NonNull final HttpSession session, @NonNull final CSRFForm form,
			@NonNull final String token) {
		LinkedHashMap<String, Long> formTokens = getFormTokens(session, form);

		boolean isvalid = formTokens.containsKey(token);

		if (isvalid && form.isRequestBased()) {
			invalidateToken(session, form, token);
		}

		return isvalid;
	}

	/**
	 * Invalidates token
	 *
	 * @param session client's {@link HttpSession}
	 * @param form    {@link CSRFForm}
	 * @param token   token given by the user
	 */
	public static void invalidateToken(@NonNull final HttpSession session, @NonNull final CSRFForm form,
			@NonNull final String token) {
		LinkedHashMap<String, Long> map = getFormTokens(session, form);
		map.remove(token);
		saveToSession(session, form, map);
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
