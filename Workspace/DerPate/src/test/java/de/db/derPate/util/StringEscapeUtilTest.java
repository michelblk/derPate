package de.db.derPate.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

@SuppressWarnings({ "javadoc", "nls" })
public class StringEscapeUtilTest {
	@Test
	public void escapeHtml() {
		String testString = "\"max\" &amp; \"mustermann\""; 
		String expected = "&quot;max&quot; &amp;amp; &quot;mustermann&quot;"; 
		String actual = StringEscapeUtil.escapeHtml(testString);

		assertEquals(expected, actual);
	}

	@Test
	public void unescapeHtml() {
		String testString = "&quot;max&quot; &amp;amp; &quot;mustermann&quot;"; 
		String expected = "\"max\" &amp; \"mustermann\""; 
		String actual = StringEscapeUtil.unescapeHtml(testString);

		assertEquals(expected, actual);
	}

	@Test
	public void encodeURL() {
		String testString = "http://deutschebahn.com/test string & string/"; 
		String expected = "http%3A%2F%2Fdeutschebahn.com%2Ftest+string+%26+string%2F"; 
		String actual = StringEscapeUtil.encodeURL(testString);

		assertEquals(expected, actual);
	}

	@Test
	public void decodeURL() {
		String testString = "http%3A%2F%2Fdeutschebahn.com%2Ftest+string+%26+string%2F"; 
		String expected = "http://deutschebahn.com/test string & string/"; 
		String actual = StringEscapeUtil.decodeURL(testString);

		assertEquals(expected, actual);
	}
}
