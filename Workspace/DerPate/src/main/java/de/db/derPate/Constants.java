package de.db.derPate;

import java.nio.charset.Charset;
import java.util.logging.Level;

import org.eclipse.jdt.annotation.NonNull;

import de.db.derPate.manager.LoggingManager;
import de.db.derPate.util.CSRFPreventionUtil;
import de.db.derPate.util.HashUtil;
import de.db.derPate.util.PropertyUtil;
import de.db.derPate.util.SHA256Util;

/**
 * This class contains static attributes to use in fronted as well as in
 * backend, so that a standard is guaranteed.
 *
 * @author MichelBlank
 * @see PropertyUtil
 */
public final class Constants {
	/**
	 * Properties containing information regarding general aspects of this
	 * application
	 */
	public static final PropertyUtil APPLICATION_PROPERTIES = new PropertyUtil("app"); //$NON-NLS-1$
	/**
	 * Properties containing information regarding this application's safety
	 */
	public static final PropertyUtil SECURITY_PROPERTIES = new PropertyUtil("security"); //$NON-NLS-1$
	/**
	 * Properties containing information, that should not be publicly available
	 * (e.g. server-tokens).
	 */
	public static final PropertyUtil SECRET_PROPERTIES = new PropertyUtil("secret"); //$NON-NLS-1$

	/**
	 * Charset to use for encodings
	 */
	public static final Charset CHARSET;

	static {
		String charset_string = APPLICATION_PROPERTIES.getProperty("app.charset", "UTF-8"); //$NON-NLS-1$ //$NON-NLS-2$
		Charset charset;
		try {
			charset = Charset.forName(charset_string);
		} catch (IllegalArgumentException e) {
			charset = Charset.defaultCharset();
			LoggingManager.log(Level.WARNING, "Charset " + charset_string //$NON-NLS-1$
					+ " cannot be used. Switching to default charset (" + charset.name() + ")."); //$NON-NLS-1$ //$NON-NLS-2$
		}
		CHARSET = charset;
	}

	/**
	 * This class contains all static attributes that have influence about the
	 * security of this application.
	 */
	public static final class Security {
		/**
		 * The number of tokens that can be generated per session per form, used in the
		 * {@link CSRFPreventionUtil}. If the number of tokens is exceeded, the oldest
		 * one will be invalidated.<br>
		 * Default value, if property not found: 10
		 *
		 * @see CSRFPreventionUtil
		 */
		public static final int CSRF_DEFAULT_MAX_TOKENS = SECURITY_PROPERTIES.getIntProperty("csrf.default_max_tokens", //$NON-NLS-1$
				10);

		/**
		 * The password used for the default AES256 encryption.
		 */
		@NonNull
		public static final String ENCRYPTION_AES256_PASSWORD = SECRET_PROPERTIES
				.getProperty("encryption.aes256.password"); //$NON-NLS-1$

		/**
		 * The salt used for the default AES256 encryption.
		 */
		@NonNull
		public static final String ENCRYPTION_AES256_SALT = SECRET_PROPERTIES.getProperty("encryption.aes256.salt"); //$NON-NLS-1$

	}

	/**
	 * This class contains all static attributes related to the database
	 */
	public static final class Database {
		/**
		 * The URL to the Database (e.g. jdbc:mysql://localhost/database)
		 */
		public static final String URL = SECRET_PROPERTIES.getProperty("database.url", //$NON-NLS-1$
				"jdbc:mysql://localhost/derpate"); //$NON-NLS-1$
		/**
		 * The username used for the database connection
		 */
		public static final String USERNAME = SECRET_PROPERTIES.getProperty("database.username", //$NON-NLS-1$
				"jdbc:mysql://localhost/root"); //$NON-NLS-1$
		/**
		 * The password
		 */
		public static final String PASSWORD = SECRET_PROPERTIES.getProperty("database.password", ""); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This class contains all static attributes related with the login.
	 */
	public static final class Login {
		/**
		 * hash algorithm
		 */
		public static final HashUtil hashUtil = SHA256Util.getInstance();

		/**
		 * Separator used to split hash and salt
		 */
		public static final String hashSeparator = SECURITY_PROPERTIES.getProperty("encryption.separator", "."); //$NON-NLS-1$ //$NON-NLS-2$

		/**
		 * Pepper<br>
		 * <b>Has to be set in secret.properties!</b> Will cause a RuntimeException
		 * otherwise.
		 */
		public static final byte[] hashPepper = SECRET_PROPERTIES.getProperty("encryption.pepper").getBytes(); //$NON-NLS-1$

		/**
		 * Length (in bytes) of the salt to use<br>
		 * Default value, if property not found: 64
		 */
		public static final int hashSaltLength = SECURITY_PROPERTIES.getIntProperty("encryption.salt_length", 64); //$NON-NLS-1$

		/**
		 * max time the user stays logged in, while inactive<br>
		 * Default value, if property not found: 600
		 */
		public static final int MAX_INACTIVE_SECONDS = SECURITY_PROPERTIES.getIntProperty("login.timeout", 600); //$NON-NLS-1$
	}
}
