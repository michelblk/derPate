package de.db.derpate.util;

import java.util.regex.Pattern;

import org.eclipse.jdt.annotation.Nullable;

/**
 * This util can be used to verify inputs, especially when the data is given by
 * the frontend.
 *
 * @author MichelBlank
 *
 */
public final class InputVerifyUtil {
	/**
	 * Pattern validating a email address.<br>
	 * Example: max.mustermann@subdomain.domain.org
	 */
	public static final Pattern EMAIL_PATTERN = Pattern.compile("^(\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b)$", //$NON-NLS-1$
			Pattern.CASE_INSENSITIVE);
	/**
	 * Pattern used to check if string is just one single word
	 */
	public static final Pattern WORD_PATTERN = Pattern.compile("^([a-zäöüß]*)$", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$
	/**
	 * Pattern used to check if string is just one single word
	 */
	public static final Pattern SENTENCE_PATTERN = Pattern.compile("^([a-zäöüß\\s]*)$", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$
	/**
	 * Pattern to validate a Universally Unique Identifier.<br>
	 * A uuid may follow the following pattern: xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
	 * (x standing for hexadecimal characters)
	 */
	public static final Pattern UUID_PATTERN = Pattern.compile(
			"^(\\b[0-9a-f]{8}\\b-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-\\b[0-9a-f]{12}\\b)$", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$

	private InputVerifyUtil() {
		// nothing to do
	}

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
	 * Returns, whether a {@link String} is not null and not blank. Returns true, if
	 * string is not empty and contains not only white spaces.
	 *
	 * @param string {@link String} that should be checked
	 * @return <code>true</code>, if the given String is not null and not blank;
	 *         <code>false</code>, otherwise.
	 */
	public static boolean isNotBlank(@Nullable String string) {
		return string != null && !string.trim().isEmpty();
	}

	/**
	 * Returns, whether a {@link String} has at least the given number of characters
	 * in it (leading and trailing spaces excluded).
	 *
	 * @param string    {@link String} that should be checked
	 * @param minLength the minimum number of characters
	 * @return <code>true</code>, if the given String has at least the given number
	 *         of characters; <code>false</code>, otherwise or if String is
	 *         <code>null</code>.
	 */
	public static boolean hasAtLeastXCharacters(@Nullable String string, int minLength) {
		return string != null && string.trim().length() >= minLength;
	}

	/**
	 * Checks if given email address is valid (see {@link #EMAIL_PATTERN})
	 *
	 * @param email Email address to check
	 * @return <code>true</code>, if given email address is not empty and applies to
	 *         pattern;<code>false</code>, otherwise.
	 */
	public static boolean isEmailAddress(@Nullable String email) {
		return (isNotBlank(email) && EMAIL_PATTERN.matcher(email).find());
	}

	/**
	 * Returns if float is greater than or equal to 0.
	 *
	 * @param flt float
	 * @return <code>true</code>, if float is {@literal >= 0};<code>false</code>, if
	 *         not.
	 */
	public static boolean isPositiveNumber(float flt) {
		return flt >= 0;
	}

	/**
	 * Returns if double is greater than or equal to 0.
	 *
	 * @param dbl double
	 * @return <code>true</code>, if double is {@literal >= 0}; <code>false</code>,
	 *         if not.
	 */
	public static boolean isPositiveNumber(double dbl) {
		return dbl >= 0;
	}

	/**
	 * Checks if a string can be parsed into an integer.
	 *
	 * @param string {@link String}
	 * @return <code>true</code>, if {@link String} can be parsed into an int using
	 *         {@link Integer#parseInt(String)}; <code>false</code>, if not
	 */
	public static boolean isInteger(@Nullable String string) {
		boolean isInteger = false;
		if (string != null && isNotBlank(string)) {
			for (int i = 0; i < string.length(); i++) {
				if (i == 0 && (string.charAt(0) == '-' || string.charAt(0) == '+')) {
					// negative numbers
					if (string.length() == 1) {
						// int cannot only be "-"
						break;
					}
				} else if (!Character.isDigit(string.charAt(i))) {
					// no digit found
					isInteger = false;
					break;
				}
				isInteger = true;
			}
		}
		return isInteger;
	}

	/**
	 * Checks if a string can be parsed into a float.
	 *
	 * @param string {@link String}
	 * @return <code>true</code>, if {@link String} can be parsed into a float using
	 *         {@link Float#parseFloat(String)}; <code>false</code>, if not
	 */
	public static boolean isFloat(@Nullable String string) {
		boolean isFloat = false;
		if (isNotBlank(string)) {
			try {
				Float.parseFloat(string);
				isFloat = true;
			} catch (@SuppressWarnings("unused") NumberFormatException e) {
				// nothing to do
			}
		}
		return isFloat;
	}

	/**
	 * Checks if string is just one single word, that does only contain characters
	 * from A to Z and ä, ö, ü, ß (not case sensitive). {@link String} may not be
	 * blank.
	 *
	 * @param string {@link String}
	 * @return <code>true</code>, if string is just one single word;
	 *         <code>false</code>, if not.
	 */
	public static boolean isWord(@Nullable String string) {
		return (isNotBlank(string) && WORD_PATTERN.matcher(string).find());
	}

	/**
	 * Method that checks if a given {@link String} does not contain any other
	 * characters than A-Z and ä, ö, ü, ß and spaces (not case sensitive).
	 *
	 * @param string {@link String}
	 * @return <code>true</code>, if string contains just words and spaces;
	 *         <code>false</code>, if not.
	 */
	public static boolean isSentence(@Nullable String string) {
		return (isNotBlank(string) && SENTENCE_PATTERN.matcher(string).find());
	}

	/**
	 * Checks if string is in uuid-pattern ({@link #UUID_PATTERN} hexadezimal).
	 *
	 * @param string {@link String}
	 * @return <code>true</code>, if sting is a valid uuid; <code>false</code>, if
	 *         string does not apply to the uuid-pattern.
	 */
	public static boolean isUuid(@Nullable String string) {
		return (isNotBlank(string) && UUID_PATTERN.matcher(string).find());
	}
}
