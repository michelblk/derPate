package de.db.derPate.util;

import org.apache.commons.text.StringEscapeUtils;
import org.eclipse.jdt.annotation.Nullable;

/**
 * This util escapes and unescapes {@link String}s for HTML and JSON.<br>
 * It uses the {@link StringEscapeUtils}.
 *
 * @author MichelBlank
 *
 */
public class StringEscapeUtil {

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
	 * Unescapes a string containing entity escapes to a string containing the
	 * actual Unicode characters corresponding to the escapes.<br>
	 * For example:<br>
	 * <code>{@literal &quot;max&quot; &amp; &quot;mustermann&quot;}</code><br>
	 * becomes:<br>
	 * <code>"max" & "mustermann"</code><br>
	 * If an entity is unrecognized, it is left alone, and inserted verbatim into
	 * the result string.
	 *
	 * @param html
	 * @return
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
	 * @param json the {@link} String to unescape
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
}
