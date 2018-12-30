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
public class PropertyUtil {
	@NonNull
	private Properties properties = new Properties();

	/**
	 * Constructor reading file and loading it into a {@link Properties} object.<br>
	 * Caution: <b>May throw an IOException</b>, if file was not found in resource
	 * folder
	 *
	 * @param filename Filename without ".properties" extension
	 */
	public PropertyUtil(@NonNull String filename) {
		String path = filename + ".properties"; //$NON-NLS-1$
		InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(path); // $NON-NLS-1$
		try {
			this.properties.load(inStream);
		} catch (Exception e) {
			LoggingManager.log(Level.SEVERE, "main properties file not found (" + path + "): " + e.getMessage()); //$NON-NLS-1$ //$NON-NLS-2$
			throw new RuntimeException("properties file not found: " + path); //$NON-NLS-1$
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
	 * Reading property value as String and returns an empty {@link String}, if key
	 * was not found
	 *
	 * @param key Key
	 * @return value or an empty string, if property was not found
	 * @see Properties#getProperty(String)
	 */
	@NonNull
	public String getProperty(@NonNull String key) {
		String result = this.getProperty(key, null);
		return (result != null ? result : ""); //$NON-NLS-1$
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
