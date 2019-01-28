package de.db.derPate.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

@SuppressWarnings({ "javadoc", "nls" })
public class TimeUtilTest {
	@Test
	public void getCurrentTime() {
		long expected = new Date().getTime();
		long actual = TimeUtil.getCurrentTime().getTime();

		boolean same = expected < actual + 1000 && expected > actual - 1000; // one second tolerance
		assertTrue(same);
	}

	@Test
	public void dateToReadableString() {
		Locale locale = Locale.GERMANY;
		Date date = new Date(1548694489000L);
		String expected = "28.01.2019, 17:54:49";

		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, locale);
		String actual = dateFormat.format(date);

		assertEquals(expected, actual);
	}
}
