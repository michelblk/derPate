package de.db.derpate.model.typeAdapter;

import java.io.IOException;
import java.sql.Date;
import java.util.logging.Level;

import org.eclipse.jdt.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import de.db.derpate.manager.LoggingManager;

/**
 * This {@link TypeAdapter} used with {@link Gson} for json convertions,
 * converts a {@link Date} to a timestamp.
 *
 * @author MichelBlank
 */
public class DateTypeAdapter extends TypeAdapter<Date> {

	/**
	 * Converts a {@link Date} to timestamp (milliseconds since January 1, 1970,
	 * 00:00:00 GMT as long)
	 *
	 * @see Date#getTime()
	 */
	@Override
	public void write(@Nullable JsonWriter out, @Nullable Date value) throws IOException {
		if (out != null) {
			if (value == null) {
				out.nullValue();
			} else {
				out.value(value.getTime());
			}
		}
	}

	/**
	 * Converts a timestamp (milliseconds since January 1, 1970, 00:00:00 GMT as
	 * long) to a {@link Date}
	 *
	 * @return {@link Date} or <code>null</code>, if timestamp could not be read
	 * @see JsonReader#nextLong()
	 */
	@Override
	@Nullable
	public Date read(@Nullable JsonReader in) throws IOException {
		if (in != null) {
			try {
				return new Date(in.nextLong());
			} catch (IllegalStateException | NumberFormatException e) {
				LoggingManager.log(Level.WARNING, "Could not convert json date to java date: " + e.getMessage()); //$NON-NLS-1$
			}
		}
		return null;
	}

}
