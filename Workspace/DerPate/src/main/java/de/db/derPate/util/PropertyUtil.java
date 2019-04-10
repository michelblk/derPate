package de.db.derPate.util;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import de.db.derPate.manager.LoggingManager;

/**
 * This util offers methods to read out of an ".properties" file stored in the
 * {@literal /resource} folder.<br>
 * Files have to conform Java's {@link Properties} format.
 *
 * @author MichelBlank
 * @see Properties
 */
public final class PropertyUtil {
	@NonNull
	private Properties properties = new Properties();
	/**
	 * Filename, stored for logging
	 */
	@NonNull
	private String file;

	/**
	 * Constructor reading file and loading it into a {@link Properties} object.<br>
	 * Caution: <b>May throw an IOException</b>, if file was not found in resource
	 * folder
	 *
	 * @param filename Filename without ".properties" extension
	 */
	public PropertyUtil(@NonNull String filename) {
		this.file = filename + ".properties"; //$NON-NLS-1$
		InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(this.file); // $NON-NLS-1$
		try {
			this.properties.load(inStream);
		} catch (Exception e) {
			LoggingManager.log(Level.WARNING, "properties file not found (" + this.file + "): " + e.getMessage()); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Returns the complete {@link Properties} object, which was filled with the
	 * informations found in the file, this object was instantiated with
	 *
	 * @return {@link Properties} of file, the object was instantiated with
	 */
	@NonNull
	public Properties getProperties() {
		return this.properties;
	}

	/**
	 * Reading property value as String.<br>
	 * Throws a {@link RuntimeException}, if key was not found! Therefore, should
	 * only be used, when value is mandatory for application
	 *
	 * @param key Key
	 * @return value if property was not found
	 * @throws RuntimeException if key was not found
	 * @see Properties#getProperty(String)
	 */
	@NonNull
	public String getProperty(@NonNull String key) throws RuntimeException {
		String result = this.getProperty(key, null);
		if (result == null) {
			LoggingManager.log(Level.SEVERE, "Application stopped, as mandatory property was not set: " + key); //$NON-NLS-1$
			throw new RuntimeException("Property " + key + " of file " + this.file + " was not found"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}

		return result;
	}

	/**
	 * Reading property value as String and returns a given {@link String}, if key
	 * was not found
	 *
	 * @param key          Key
	 * @param defaultValue default value
	 * @return value or given {@link String}, if property was not found.
	 * @see Properties#getProperty(String, String)
	 */
	@Nullable
	public String getProperty(@NonNull String key, @Nullable String defaultValue) {
		String value = this.properties.getProperty(key);
		return (value != null ? value : defaultValue);
	}

	/**
	 * Read property value and try to convert it to an integer. If value could not
	 * be converted, the given default value is returned.
	 *
	 * @param key          Key
	 * @param defaultValue default value
	 * @return value as integer or the default value, if value could not be
	 *         converted to an integer
	 */
	public int getIntProperty(@NonNull String key, int defaultValue) {
		String value = this.getProperty(key);
		return (InputVerifyUtil.isInteger(value) ? Integer.parseInt(value) : defaultValue);
	}
}
