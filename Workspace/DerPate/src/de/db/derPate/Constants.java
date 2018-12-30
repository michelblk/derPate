package de.db.derPate;

import de.db.derPate.model.Admin;
import de.db.derPate.model.Godfather;
import de.db.derPate.model.Trainee;
import de.db.derPate.util.CSRFPreventionUtil;
import de.db.derPate.util.HashUtil;
import de.db.derPate.util.PropertyUtil;
import de.db.derPate.util.SHA256Util;

/**
 * This class contains static attributes to use in fronted as well as in
 * backend, so that a standard is guaranteed.
 *
 * @author MichelBlank
 *
 */
public final class Constants {
	/**
	 * Charset to use for encodings
	 */
	public static final String CHARSET = "UTF-8"; //$NON-NLS-1$

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
		 * The time in seconds, a csrf prevention token is valid. This has to be greater
		 * than the time, the user can be expected to fill in a form.<br>
		 * Default value, if property not found: 600s
		 *
		 * @see CSRFPreventionUtil
		 */
		public static final int CSRF_TIMEOUT_IN_SECONDS = SECURITY_PROPERTIES.getIntProperty("csrf.timeout_in_seconds", //$NON-NLS-1$
				600);
	}

	/**
	 * This class contains all static attributes related to the database
	 */
	public static final class Database {
		/**
		 *
		 */
		public static final String URL = SECRET_PROPERTIES.getProperty("database.url"); //$NON-NLS-1$
		/**
		 *
		 */
		public static final String USERNAME = SECRET_PROPERTIES.getProperty("database.username"); //$NON-NLS-1$
		/**
		 *
		 */
		public static final String PASSWORD = SECRET_PROPERTIES.getProperty("database.password"); //$NON-NLS-1$
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
		public static final String hashSeparator = SECURITY_PROPERTIES.getProperty("encryption.separator"); //$NON-NLS-1$

		/**
		 * Pepper.
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

	/**
	 * This class contains all static attributes that are used in the frontend.
	 * Their values may be used in the backend as well, when processing information
	 * coming from the frontend.
	 *
	 */
	public static final class Ui {
		/**
		 * Contains input field names for frontend use.<br>
		 * Use {@link Inputs#toString()} to get the name.
		 */
		public static enum Inputs {
			/**
			 * Inputfield used by {@link Admin}s and {@link Godfather}s to provide their
			 * email address
			 */
			LOGIN_EMAIL("email"), //$NON-NLS-1$
			/**
			 * Inputfield used by {@link Admin}s and {@link Godfather}s to provide their
			 * password
			 */
			LOGIN_PASSWORD("password"), //$NON-NLS-1$
			/**
			 * Inputfield used by {@link Trainee}s to provide their token
			 */
			LOGIN_TOKEN("token"); //$NON-NLS-1$

			private final String frontendName;

			Inputs(String frontendName) {
				this.frontendName = frontendName;
			}

			/**
			 * Returns the name of the input field for frontend or backend use
			 */
			@Override
			public String toString() {
				return this.frontendName;
			}
		}
	}
}
