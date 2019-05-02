package de.db.derpate.servlet.filter.validation;

import java.text.Format;
import java.text.ParseException;

/**
 * Filters by a given {@link Format}
 * 
 * @author MichelBlank
 *
 */
public class FormatValidator implements InputValidator {
	private Format format;

	/**
	 * Constructor
	 * 
	 * @param format the {@link Format}
	 */
	public FormatValidator(Format format) {
		this.format = format;
	}

	@Override
	public boolean isValid(String value) {
		boolean valid = false;
		if (value != null) {
			try {
				valid = this.format.parseObject(value) != null;
			} catch (@SuppressWarnings("unused") ParseException e) {
				// not used
			}
		}
		return valid;
	}

}
