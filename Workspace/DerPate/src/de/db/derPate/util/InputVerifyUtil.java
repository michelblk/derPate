package de.db.derPate.util;

import org.eclipse.jdt.annotation.Nullable;

/**
 * This util can be used to verify inputs, especially when the data is given by
 * the frontend.
 *
 * @author MichelBlank
 *
 */
public class InputVerifyUtil {
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
}
