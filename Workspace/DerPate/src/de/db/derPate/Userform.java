package de.db.derPate;

import org.eclipse.jdt.annotation.NonNull;

import de.db.derPate.servlet.LoginServlet;
import de.db.derPate.servlet.LogoutServlet;

/**
 * Enum used to identify a frontend-form and set's how many tokens are valid at
 * the same time (for example when a user opens a page multiple times)
 *
 * @author MichelBlank
 *
 */
public enum Userform {
	/**
	 * Login for all user types<br>
	 * A token should only be used once (request based)
	 *
	 * @see LoginServlet
	 */
	LOGIN(-1),
	/**
	 * Logout<br>
	 * A token is valid till the session ends (session based)
	 *
	 * @see LogoutServlet
	 */
	LOGOUT();

	private int maxCSRFTokens;
	private boolean requestBased;

	/**
	 * Default: a session based CSRF token
	 */
	private Userform() {
		this.maxCSRFTokens = 1;
		this.requestBased = false;
	}

	/**
	 * If the maximum number of tokens is given, the token is request based.
	 *
	 * @param maxCSRFTokens
	 */
	private Userform(int maxCSRFTokens) {
		if (maxCSRFTokens < 1) {
			maxCSRFTokens = Constants.Security.CSRF_DEFAULT_MAX_TOKENS;
		}
		this.maxCSRFTokens = maxCSRFTokens;
		this.requestBased = true;
	}

	/**
	 * Returns the maximum number of stored tokens
	 *
	 * @return integer max tokens
	 */
	public int getMaxTokens() {
		return this.maxCSRFTokens;
	}

	/**
	 * Returns, if the CSFR token should be request based.
	 *
	 * @return <code>true</code>, if one token should be generated once per request;
	 *         <code>false</code>, if a token is valid the whole session
	 */
	public boolean isRequestBased() {
		return this.requestBased;
	}

	@SuppressWarnings("null")
	@Override
	@NonNull
	public String toString() {
		return super.name();
	}
}
