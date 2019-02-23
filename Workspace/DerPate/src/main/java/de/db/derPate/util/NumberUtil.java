package de.db.derPate.util;

import org.eclipse.jdt.annotation.Nullable;

/**
 * Util providing methods, that are useful when working with integers.
 *
 * @author MichelBlank
 * @see InputVerifyUtil
 */
public class NumberUtil {
	/**
	 * Parsing an integer and returning <code>null</code>, if the number format is
	 * wrong
	 *
	 * @param integer the {@link String} to parse to an integer
	 * @return the integer or <code>null</code>, if {@link String} could not be
	 *         parsed
	 */
	@Nullable
	public static Integer parseInteger(@Nullable String integer) {
		Integer result = null;

		if (integer != null) {
			try {
				result = Integer.parseInt(integer);
			} catch (@SuppressWarnings("unused") NumberFormatException e) {
				// nothing to do
			}
		}

		return result;
	}
}
