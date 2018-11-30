package de.db.derPate.util;

import java.util.regex.Pattern;

import org.eclipse.jdt.annotation.Nullable;

/**
 * This util can be used to verify inputs, especially when the data is given by
 * the frontend.
 *
 * @author MichelBlank
 *
 */
public class InputVerifyUtil {
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	/**
	 * Returns, whether a {@link String} is not null and not empty.
	 *
	 * @param string {@link String} that should be checked
	 * @return <code>true</code>, if the given String is not null and not empty;
	 *         <code>false</code>, otherwise.
	 */
	public static boolean isNotEmpty(@Nullable String string) {
		return string != null && !string.isEmpty();
	}

	/**
	 * Checks if given email address is valid (see {@link #EMAIL_PATTERN})
	 * 
	 * @param email Email address to check
	 * @return <code>true</code>, if given email address is not empty and applies to
	 *         pattern;<code>false</code>, otherwise.
	 */
	public static boolean isEmailAddress(@Nullable String email) {
		return (isNotEmpty(email) && EMAIL_PATTERN.matcher(email).find());
	}
}
