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

	/**
	 * Calculates the difference between a date and now in (full) years.<br>
	 * Can be useful to calculate the age by a birthday.<br>
	 * 
	 * @param date A date
	 * @return the number of years in between, <code>null</code>, when date was null
	 */
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
		} else if (currentMonth == thenMonth) {
			if (then.get(Calendar.DAY_OF_MONTH) > now.get(Calendar.DAY_OF_MONTH)) {
				age--;
			}
		}

		return age;
	}
}
