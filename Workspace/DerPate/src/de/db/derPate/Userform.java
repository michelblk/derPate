package de.db.derPate;

import org.eclipse.jdt.annotation.Nullable;

/**
 * Enum used to identify a frontend-form and set's how many tokens are valid at
 * the same time (for example when a user opens a page multiple times)
 *
 * @author MichelBlank
 *
 */
public enum Userform {
	LOGIN(1);

	private int maxCSRFTokens;

	private Userform(@Nullable Integer maxCSRFTokens) {
		if (maxCSRFTokens == null) {
			maxCSRFTokens = Constants.Security.CSRF_DEFAULT_MAX_TOKENS;
		} else if (maxCSRFTokens < 1) {
			maxCSRFTokens = 1;
		}
		this.maxCSRFTokens = maxCSRFTokens;
	}

	public int getMaxTokens() {
		return this.maxCSRFTokens;
	}
}
