/**
 *
 */
package de.db.derpate.servlet.filter.validation;

import de.db.derpate.util.InputVerifyUtil;

/**
 * This {@link InputValidator} can check, if a string has at least a certain
 * length
 *
 * @author MichelBlank
 *
 */
public class LengthValidator implements InputValidator {
	private int length;

	/**
	 * Constructor
	 *
	 * @param length the minimum length (without leading or trailing spaces) of the
	 *               string
	 */
	public LengthValidator(int length) {
		this.length = length;
	}

	/**
	 * @see InputValidator#isValid(java.lang.String)
	 */
	@Override
	public boolean isValid(String value) {
		return InputVerifyUtil.hasAtLeastXCharacters(value, this.length);
	}

}
