package de.db.derPate.util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * This util is used to simplify date operations
 *
 * @author MichelBlank
 *
 */
public class TimeUtil {
	/**
	 * Returns current date and time in a {@link Date}-Object
	 *
	 * @return {@link Date}
	 */
	@SuppressWarnings("null")
	@NonNull
	public static Date getCurrentTime() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	/**
	 * Converts a {@link Date}-Object to a readable String.
	 *
	 * @param date   {@link Date}
	 * @param locale {@link Locale}, which determines the date and time format
	 * @return readable date-{@link String}
	 */
	@Nullable
	public static String dateToReadableString(Date date, Locale locale) {
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, locale);
		return dateFormat.format(date);
		// TODO exceptions?
	}
}
