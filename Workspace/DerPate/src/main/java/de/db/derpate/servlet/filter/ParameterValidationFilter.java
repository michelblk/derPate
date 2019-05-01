/**
 *
 */
package de.db.derpate.servlet.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.annotation.NonNull;

import de.db.derpate.servlet.ServletParameter;

/**
 * This filter can be used, if certain parameters are required.
 *
 * @author MichelBlank
 *
 */
public class ParameterValidationFilter implements ServletFilter {
	private ServletParameter[] parameters;

	/**
	 * Constructor
	 *
	 * @param parameters the {@link ServletParameter parameters} that must have
	 *                   a value
	 */
	public ParameterValidationFilter(ServletParameter... parameters) {
		this.parameters = parameters;
	}

	/**
	 * @see de.db.derpate.servlet.filter.ServletFilter#filter(javax.servlet.http.
	 *      HttpServletRequest)
	 */
	@Override
	public boolean filter(@NonNull HttpServletRequest req) {
		if (this.parameters != null) {
			for (ServletParameter parameter : this.parameters) {
				if (!parameter.isValid(req.getParameter(parameter.toString()))) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public int getErrorStatusCode() {
		return HttpServletResponse.SC_BAD_REQUEST;
	}

}
