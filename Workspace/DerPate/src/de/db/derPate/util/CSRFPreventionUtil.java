package de.db.derPate.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;

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
	public static final String FIELD_NAME = "csrf_token";
	/**
	 * Header that can be used alternatively (or is sent by server, if old one got
	 * invalidated)
	 */
	public static final String HEADER_FIELD = "X-Csrf-Token";
	/**
	 * key, which is used to store the {@link Map} in {@link HttpSession}'s
	 * attributes
	 */
	public static final String SESSION_ATTRIBUTE = "csrfTokenMap";

	/**
	 * This method generates a token and registers it in the user's
	 * {@link HttpSession}.
	 *
	 * @param session   client's {@link HttpSession}
	 * @param form      String identifier used to limit token to this specific form
	 * @param maxTokens int defining how many tokens can be created per user per
	 *                  form
	 * @return random string
	 */
	public static String generateToken(HttpSession session, String form, int maxTokens) {
		LinkedHashMap<String, Long> formTokens = getFormTokens(session, form);

		// check if limit is exceeded and remove oldest token, if necessary
		checkLimit(formTokens, maxTokens);

		// set new token
		String randomToken = generateUniqueToken(formTokens);
		formTokens.put(randomToken, System.currentTimeMillis());

		// save token to session
		saveToSession(session, form, formTokens);

		return randomToken;
	}

	/**
	 * Generates a random {@link Base64} {@link String}
	 *
	 * @param length number of <b>bytes</b> generated
	 * @return random {@link String}
	 */
	private static String generateRandomString(int length) {
		byte[] bytes = new byte[length];
		new Random().nextBytes(bytes);

		return Base64.encodeBase64String(bytes);
	}

	/**
	 * Receives Map of tokens out of a Map of forms stored in the client's
	 * {@link HttpSession}.
	 *
	 * @param session client's {@link HttpSession}
	 * @param form    form identifier
	 * @return a {@link LinkedHashMap} of Tokens generated for the given form and
	 *         user
	 */
	@SuppressWarnings("unchecked")
	private static LinkedHashMap<String, Long> getFormTokens(HttpSession session, String form) {
		LinkedHashMap<String, Long> formTokens = new LinkedHashMap<>();

		// load from session
		Object sessionTokens = session.getAttribute(SESSION_ATTRIBUTE);
		if (sessionTokens != null && sessionTokens instanceof ConcurrentHashMap) {
			LinkedHashMap<String, Long> tmpMap = ((ConcurrentHashMap<String, LinkedHashMap<String, Long>>) sessionTokens)
					.get(form);
			if (tmpMap != null && tmpMap instanceof LinkedHashMap) {
				formTokens = tmpMap;
			}
		}

		return formTokens;
	}

	/**
	 * Sets the session attributes based on the given form
	 *
	 * @param session client's {@link HttpSession}
	 * @param form    form identifier
	 * @param map     {@link LinkedHashMap} to set
	 */
	@SuppressWarnings("unchecked")
	private static void saveToSession(HttpSession session, String form, LinkedHashMap<String, Long> map) {
		ConcurrentHashMap<String, LinkedHashMap<String, Long>> userTokens = new ConcurrentHashMap<>();
		Object sessionTokens = session.getAttribute(SESSION_ATTRIBUTE);
		if (sessionTokens != null && sessionTokens instanceof ConcurrentHashMap) {
			userTokens = (ConcurrentHashMap<String, LinkedHashMap<String, Long>>) sessionTokens;
		}
		userTokens.put(form, map);

		// reset session
		session.setAttribute(SESSION_ATTRIBUTE, userTokens);
	}

	/**
	 * Checks, if the limit is exceeded and removes the oldest entry, if necessary.
	 *
	 * @param map       Map, that should be checked
	 * @param maxTokens the maximum count of entries
	 */
	private static void checkLimit(LinkedHashMap<String, Long> map, int maxTokens) {
		while (map.size() >= maxTokens) {
			kickOldestToken(map);
		}
	}

	/**
	 * Removes the oldest(first) entry in {@link LinkedHashMap}
	 *
	 * @param map {@link LinkedHashMap}
	 */
	private static void kickOldestToken(LinkedHashMap<String, Long> map) {
		map.remove(map.entrySet().iterator().next().getKey()); // removes first in iterator
	}

	/**
	 * Generates a random token using {@link #generateRandomString(int)} and makes
	 * sure, that this token isn't already in use by the same user for the same
	 * form.
	 *
	 * @param map {@link LinkedHashMap}, with token as key
	 * @return random token
	 */
	private static String generateUniqueToken(LinkedHashMap<String, Long> map) {
		boolean foundToken = false;
		String randomToken;
		do {
			randomToken = generateRandomString(20);
			foundToken = !map.containsKey(randomToken);
		} while (!foundToken);
		return randomToken;
	}

	/**
	 * This method checks if the given token was registered in the user's
	 * {@link HttpSession} and if it's not older than
	 * {@value de.db.derPate.Constants.Security#CSRF_TIMEOUT_IN_SECONDS}s
	 *
	 * @param session          client's {@link HttpSession}
	 * @param formidentifier   form identifier
	 * @param token            token given by the user
	 * @param timeoutInSeconds defines how long a token is valid (in seconds)
	 * @return <code>true</code>, if token was registered for the given form
	 *         beforehand and is still valid; <code>false</code>, if token wasn't
	 *         registered or isn't valid anymore due to a timeout
	 */
	@SuppressWarnings("unchecked")
	public static boolean checkToken(HttpSession session, String formidentifier, String token, int timeoutInSeconds) {
		Object sessionTokens = session.getAttribute(SESSION_ATTRIBUTE);
		if (sessionTokens != null && sessionTokens instanceof Map) {
			// tokens found
			Map<String, LinkedHashMap<String, Long>> forms = (Map<String, LinkedHashMap<String, Long>>) sessionTokens;
			LinkedHashMap<String, Long> formTokens = forms.get(formidentifier);
			if (formTokens != null && formTokens.containsKey(token)) {
				// token in list
				return (formTokens.get(token) > System.currentTimeMillis() - (timeoutInSeconds * 1000)); // timed out?
			}
		}
		// no tokens found or token not in list
		return false;
	}

	/**
	 * Invalidates token
	 *
	 * @param session        client's {@link HttpSession}
	 * @param formidentifier form identifier
	 * @param token          token given by the user
	 */
	public static void invalidateToken(HttpSession session, String formidentifier, String token) {
		LinkedHashMap<String, Long> map = getFormTokens(session, formidentifier);
		map.remove(token);
		saveToSession(session, formidentifier, map);
	}

	/**
	 * Checks if token is valid (using
	 * {@link #checkToken(HttpSession, String, String, int)}) and invalidates it
	 * (using {@link #invalidateToken(HttpSession, String, String)}), so the token
	 * can be used just one time.
	 *
	 * @param session          client's {@link HttpSession}
	 * @param formidentifier   form identifier
	 * @param token            token given by the user
	 * @param timeoutInSeconds timeout in seconds
	 * @return <code>true</code>, if token was registered for the given form
	 *         beforehand and is still valid; <code>false</code>, if token wasn't
	 *         registered or isn't valid anymore due to a timeout
	 * @see #checkToken(HttpSession, String, String, int)
	 */
	public static boolean checkAndInvalidateToken(HttpSession session, String formidentifier, String token,
			int timeoutInSeconds) {
		boolean isvalid = checkToken(session, formidentifier, token, timeoutInSeconds);
		invalidateToken(session, formidentifier, token);
		return isvalid;
	}
}
