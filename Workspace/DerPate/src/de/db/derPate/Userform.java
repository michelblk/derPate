package de.db.derPate;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

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
	 * Login for all user types
	 *
	 * @see LoginServlet
	 */
	LOGIN(1),
	/**
	 * Logout
	 *
	 * @see LogoutServlet
	 */
	LOGOUT(-1);

	private int maxCSRFTokens;

	private Userform(@Nullable Integer maxCSRFTokens) {
		if (maxCSRFTokens == null || maxCSRFTokens < 1) {
			maxCSRFTokens = Constants.Security.CSRF_DEFAULT_MAX_TOKENS;
		}
		this.maxCSRFTokens = maxCSRFTokens;
	}

	/**
	 * Returns the maximum number of stored tokens
	 *
	 * @return integer max tokens
	 */
	public int getMaxTokens() {
		return this.maxCSRFTokens;
	}

	@SuppressWarnings("null")
	@Override
	@NonNull
	public String toString() {
		return super.name();
	}
}
