/**
 *
 */
package de.db.derpate.servlet.filter.validation;

import java.util.regex.Pattern;

/**
 * This {@link InputValidator} checks if a pattern can be applied
 *
 * @author MichelBlank
 *
 */
public class PatternValidator implements InputValidator {
	private Pattern pattern;

	/**
	 * Constructor
	 *
	 * @param pattern the pattern the input must match
	 */
	public PatternValidator(Pattern pattern) {
		this.pattern = pattern;
	}

	/**
	 * @see InputValidator#isValid(java.lang.String)
	 */
	@Override
	public boolean isValid(String value) {
		return this.pattern.matcher(value).find();
	}

}
