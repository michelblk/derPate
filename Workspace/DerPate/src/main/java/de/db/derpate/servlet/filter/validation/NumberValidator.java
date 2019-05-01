/**
 *
 */
package de.db.derpate.servlet.filter.validation;

import de.db.derpate.util.InputVerifyUtil;

/**
 * This {@link InputValidator} checks if a given {@link String} is a number and
 * if it is in a range, if specified
 *
 * @author MichelBlank
 *
 */
public class NumberValidator implements InputValidator {
	private Integer min;
	private Integer max;

	/**
	 * Constructor<br>
	 * Is used, when any integer is allowed
	 */
	public NumberValidator() {
		this.min = null;
		this.max = null;
	}

	/**
	 * Constructor to use, when a minimum and/or maximum should be applied
	 *
	 * @param minimum the minimum number (included)
	 * @param maximum the maximum number (included)
	 */
	public NumberValidator(Integer minimum, Integer maximum) {
		this.min = minimum;
		this.max = maximum;
	}

	/**
	 * @see InputValidator#isValid(java.lang.String)
	 */
	@Override
	public boolean isValid(String value) {
		boolean valid = InputVerifyUtil.isInteger(value);
		if (valid) {
			int integer = Integer.parseInt(value);
			if (this.min != null) {
				valid = integer >= this.min;
			}
			if (valid && this.max != null) {
				valid = integer <= this.max;
			}
		}
		return valid;
	}

}
