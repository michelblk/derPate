package de.db.derPate.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;

import org.apache.commons.text.StringEscapeUtils;
import org.eclipse.jdt.annotation.Nullable;

import de.db.derPate.manager.LoggingManager;

/**
 * This util escapes and unescapes {@link String}s for HTML and JSON.<br>
 * It uses the {@link StringEscapeUtils}.
 *
 * @author MichelBlank
 *
 */
public class StringEscapeUtil {
	private static final String URL_CHARSET = "UTF-8";

	/**
	 * Escapes the characters in a String using HTML entities.<br>
	 * For example:<br>
	 * <code>"max" & "mustermann"</code><br>
	 * becomes:<br>
	 * <code>{@literal &quot;max&quot; &amp; &quot;mustermann&quot;}</code>
	 *
	 * @param string the {@link String} to escape
	 * @return the escaped {@link String} or <code>null</code>, if string was null
	 *         beforehand
	 * @see StringEscapeUtils#escapeHtml4(String)
	 */
	@Nullable
	public static String escapeHtml(@Nullable String string) {
		String result = null;
		if (string != null) {
			result = StringEscapeUtils.escapeHtml4(string);
		}
		return result;
	}

	/**
	 * Unescapes a {@link String} containing entity escapes to a string containing
	 * the actual Unicode characters corresponding to the escapes.<br>
	 * For example:<br>
	 * <code>{@literal &quot;max&quot; &amp; &quot;mustermann&quot;}</code><br>
	 * becomes:<br>
	 * <code>"max" & "mustermann"</code><br>
	 * If an entity is unrecognized, it is left alone, and inserted verbatim into
	 * the result string.
	 *
	 * @param html the escaped {@link String}
	 * @return unescaped html
	 * @see StringEscapeUtils#unescapeHtml4(String)
	 */
	@Nullable
	public static String unescapeHtml(@Nullable String html) {
		String result = null;
		if (html != null) {
			result = StringEscapeUtils.unescapeHtml4(html);
		}
		return result;
	}

	/**
	 * Escapes the characters in a String using Json String rules.<br>
	 * Escapes any values it finds into their Json String form. Deals correctly with
	 * quotes and control-chars (tab, backslash, character return, etc.).<br>
	 * For example:<br>
	 * <code>{@literal He didn't say "Hello!"}</code><br>
	 * becomes:<br>
	 * <code>{@literal He didn't say \"Hello!\"}</code>
	 *
	 * @param string {@link String} to escape values in
	 * @return {@link String} with escaped values or <code>null</code>, if string
	 *         was null beforehand
	 * @see StringEscapeUtils#escapeJson(String)
	 */
	@Nullable
	public static String escapeJson(@Nullable String string) {
		String result = null;
		if (string != null) {
			result = StringEscapeUtils.escapeJson(string);
		}
		return result;
	}

	/**
	 * Unescapes any Json literals found in the String.
	 *
	 * For example, it will turn a sequence of '\' and 'n' into a newline character,
	 * unless the '\' is preceded by another '\'.
	 *
	 * @param json the {@link String} to unescape
	 * @return unescaped {@link String} or <code>null</code>, if string was null
	 *         beforehand
	 * @see StringEscapeUtils#unescapeJson(String)
	 */
	@Nullable
	public static String unescapeJson(@Nullable String json) {
		String result = null;
		if (json != null) {
			result = StringEscapeUtils.unescapeJson(json);
		}
		return result;
	}

	/**
	 * Escapes characters for use in a URL
	 *
	 * @param url the string
	 * @return the translated string or <code>null</code>, if url was null or
	 *         charset {@value #URL_CHARSET} was not found
	 */
	@Nullable
	public static String toURL(@Nullable String url) {
		String result = null;
		if (url != null) {
			try {
				result = URLEncoder.encode(url, URL_CHARSET);
			} catch (UnsupportedEncodingException e) {
				LoggingManager.log(Level.SEVERE, "StringEscapeUtil could not find charset " + URL_CHARSET);
			}
		}
		return result;
	}
}
