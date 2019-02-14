package de.db.derPate.util;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

@SuppressWarnings({ "javadoc" })
public class TimeUtilTest {
	@Test
	public void getCurrentTime() {
		long expected = new Date().getTime();
		long actual = TimeUtil.getCurrentTime().getTime();

		boolean same = expected < actual + 2000 && expected > actual - 2000; // two seconds tolerance
		assertTrue(same);
	}

	@Test
	public void dateToReadableString() {
		Locale locale = Locale.GERMANY;
		Date date = new Date(1548694489000L);

		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, locale);
		String expected = dateFormat.format(date);

		String actual = TimeUtil.dateToReadableString(date, locale);

		assertEquals(expected, actual);
	}
}
