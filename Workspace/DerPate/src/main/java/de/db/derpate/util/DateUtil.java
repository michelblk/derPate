package de.db.derpate.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * This util is used to simplify {@link Date} operations
 *
 * @author MichelBlank
 *
 */
public final class DateUtil {
	private DateUtil() {
		// nothing to do
	}

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
	}

	/**
	 * Calculates the difference between a date and now in (full) years.<br>
	 * Can be useful to calculate the age by a birthday.<br>
	 *
	 * @param date A date
	 * @return the number of years in between, <code>null</code>, when date was null
	 */
	@Nullable
	public static Integer getYearDiff(@Nullable Date date) {
		if (date == null) {
			return null;
		}

		Calendar now = Calendar.getInstance();
		Calendar then = Calendar.getInstance();
		then.setTime(date);

		int age = now.get(Calendar.YEAR) - then.get(Calendar.YEAR);
		int currentMonth = now.get(Calendar.MONTH);
		int thenMonth = then.get(Calendar.MONTH);

		if (thenMonth > currentMonth) {
			age--;
		} else if (currentMonth == thenMonth && then.get(Calendar.DAY_OF_MONTH) > now.get(Calendar.DAY_OF_MONTH)) {
			age--;
		}

		return age;
	}

	/**
	 * Parses a {@link String} to the given {@link DateFormat}
	 *
	 * @param dateAsString the {@link Date} as a {@link String}
	 * @param dateFormat   the {@link DateFormat} to use
	 * @return the {@link Date} or <code>null</code>, if date could not be parsed
	 */
	@Nullable
	public static Date parseDate(@Nullable String dateAsString, @NonNull DateFormat dateFormat) {
		Date result = null;

		if (dateAsString != null) {
			try {
				result = dateFormat.parse(dateAsString);
			} catch (@SuppressWarnings("unused") ParseException e) {
				// nothing to do
			}
		}

		return result;
	}
}
