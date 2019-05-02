package de.db.derpate.manager;

import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jdt.annotation.Nullable;

import de.db.derpate.util.DateUtil;

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
	private static final String LOGGER_NAME = "derPateLogger"; //$NON-NLS-1$

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
				System.err.println("Could not get java util logger! Logs will be skipped: " + e.getMessage()); //$NON-NLS-1$
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
	public static void log(@Nullable Level level, @Nullable String description) {
		if (description != null) {
			Date currentTime = DateUtil.getCurrentTime();
			String currentTimeString = DateUtil.dateToReadableString(currentTime, Locale.getDefault());
			String output = currentTimeString + ": " + description; //$NON-NLS-1$
			Logger logg = getLogger();
			if (logg != null && level != null) {
				logg.log(level, output);
			}
		}
	}
}
