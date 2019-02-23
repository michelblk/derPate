package de.db.derPate;

import org.eclipse.jdt.annotation.NonNull;

import de.db.derPate.servlet.LoginServlet;
import de.db.derPate.servlet.LogoutServlet;
import de.db.derPate.servlet.godfatherOnly.GodfatherUpdateServlet;
import de.db.derPate.servlet.traineeOnly.GodfatherSelectServlet;

/**
 * Enum used to identify a frontend-form, that uses csrf tokens. It set's how
 * many tokens are valid at the same time (for example when a user opens a page
 * multiple times)
 *
 * @author MichelBlank
 *
 */
public enum CSRFForm {
	/**
	 * Login for all user types<br>
	 * The token will be valid until the session ends
	 *
	 * @see LoginServlet
	 */
	LOGIN()
	/**
	 * Logout<br>
	 * A token is valid till the session ends (session based)
	 *
	 * @see LogoutServlet
	 */
	,LOGOUT()
	
	/**
	 * Form for Trainee to select a godfather<br>
	 * This token will be valid until the session ends
	 * 
	 * @see GodfatherSelectServlet
	 */
	,TRAINEE_SELECT_GODFATHER()
	
	/**
	 * Form for godfather to updated the data of him/herself.<br>
	 * This token will be valid until the session ends
	 * 
	 * @see GodfatherUpdateServlet
	 */
	,GODFATHER_UPDATE_SELF();

	private int maxCSRFTokens;
	private boolean requestBased;

	/**
	 * Default: a session based CSRF token
	 */
	private CSRFForm() {
		this.maxCSRFTokens = 1;
		this.requestBased = false;
	}

	/**
	 * If the maximum number of tokens is given, the token is request based.
	 *
	 * @param maxCSRFTokens the maximum number of tokens, that can be generated for
	 *                      this form per session
	 */
	private CSRFForm(int maxCSRFTokens) {
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
