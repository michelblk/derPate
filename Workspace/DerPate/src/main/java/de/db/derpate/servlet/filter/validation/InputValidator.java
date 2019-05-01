package de.db.derpate.servlet.filter.validation;

import javax.servlet.http.HttpServlet;

import de.db.derpate.servlet.filter.ParameterValidationFilter;

/**
 * The {@link InputValidator} interface can be used with the
 * {@link ParameterValidationFilter} to protect a {@link HttpServlet} against
 * invalid requests
 *
 * @author MichelBlank
 *
 */
public interface InputValidator {
	/**
	 * Returns if a given {@link String} is valid
	 *
	 * @param value the value
	 * @return <code>true</code>, if the value is valid; <code>false</code>, if not
	 */
	public boolean isValid(String value);
	// think about error message
}
