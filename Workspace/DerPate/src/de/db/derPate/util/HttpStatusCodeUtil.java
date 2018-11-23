package de.db.derPate.util;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.annotation.NonNull;

/**
 * This util provides methods that deal with http status codes
 *
 * @author MichelBlank
 * @see HttpServletResponse#getStatus()
 */
public class HttpStatusCodeUtil {
	/**
	 * Returns a description for a given http status code
	 *
	 * @param code Http response Code
	 * @return Description
	 */
	@NonNull
	public static String codeToReadableString(int code) {
		String result = null;

		// TODO find a better place and support multi language?
		switch (code) {
		case 400:
			result = "Nicht angemeldet.";
			break;
		case 401:
			result = "Nicht";
			break;
		case 403:
			result = "Nicht berechtigt.";
			break;
		case 404:
			result = "Seite nicht gefunden.";
			break;
		case 405:
			result = "Methode nicht untersützt.";
			break;
		case 500:
			result = "Internet Fehler.";
			break;
		default:
			result = "Error " + Integer.toString(code);
		}

		return result;
	}

}
