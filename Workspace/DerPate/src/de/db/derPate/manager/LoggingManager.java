package de.db.derPate.manager;

import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jdt.annotation.NonNull;

import de.db.derPate.util.TimeUtil;

/**
 * This Manager can be used to get the default Logger of this project. It also
 * provides a method that automatically prepends the current time to a new
 * log.<br>
 *
 * @author MichelBlank
 * @see Logger
 */
public class LoggingManager {
	public static Logger logger;
	private static final String LOGGER_NAME = "derPateLogger";

	public LoggingManager() {

	}

	/**
	 * Returns the project's default {@link Logger}
	 *
	 * @return {@link Logger}
	 */
	@NonNull
	public static Logger getLogger() {
		if (logger == null) {
			logger = java.util.logging.Logger.getLogger(LOGGER_NAME);
		}
		return logger;
	}

	/**
	 * Prepends the given description with the current date and time and logs it
	 *
	 * @param level       The {@link Level} of the log record
	 * @param description Description for the log record
	 */
	public static void log(@NonNull Level level, @NonNull String description) {
		Date currentTime = TimeUtil.getCurrentTime();
		String currentTimeString = TimeUtil.dateToReadableString(currentTime, Locale.getDefault());
		String output = currentTimeString + ": " + description;
		getLogger().log(level, output);
	}
}
