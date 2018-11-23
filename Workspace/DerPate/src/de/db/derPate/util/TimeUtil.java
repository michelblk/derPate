package de.db.derPate.util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import de.db.derPate.Constants;

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
	@NonNull
	public static Date getCurrentTime() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	/**
	 * Converts a {@link Date}-Object to a readable String.
	 *
	 * @param date {@link Date}
	 * @return readable date-{@link String}
	 */
	@Nullable
	public static String dateToReadableString(Date date) {
		DateFormat dateFormat = Constants.General.DATETIMEFORMAT_READABLE;
		return dateFormat.format(date);
		// TODO exceptions?
	}
}
