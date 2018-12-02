package de.db.derPate.manager;

import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

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
	private static Logger logger;
	private static final String LOGGER_NAME = "derPateLogger";

	/**
	 * Constructor
	 */
	public LoggingManager() {

	}

	/**
	 * Returns the project's default {@link Logger}
	 *
	 * @return {@link Logger}
	 */
	@Nullable
	public static Logger getLogger() {
		if (logger == null) {
			try {
				logger = java.util.logging.Logger.getLogger(LOGGER_NAME);
			} catch (NullPointerException e) {
				System.err.println("Could not get java util logger! Logs will be skipped");
			}
		}
		return logger;
	}

	/**
	 * Prepends the given description with the current date and time and logs it
	 *
	 * @param level       The {@link Level} of the log record
	 * @param description Description for the log record
	 */
	public static void log(@Nullable Level level, @NonNull String description) {
		Date currentTime = TimeUtil.getCurrentTime();
		String currentTimeString = TimeUtil.dateToReadableString(currentTime, Locale.getDefault());
		String output = currentTimeString + ": " + description;
		Logger logg = getLogger();
		if (logg != null && level != null) {
			logg.log(level, output);
		}
	}
}
