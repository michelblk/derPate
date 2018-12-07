package de.db.derPate;

import de.db.derPate.model.Admin;
import de.db.derPate.model.Godfather;
import de.db.derPate.model.Trainee;
import de.db.derPate.util.CSRFPreventionUtil;
import de.db.derPate.util.HashUtil;
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
	 * This class contains all static attributes that have influence about the
	 * security of this application.
	 */
	public static final class Security {
		/**
		 * The number of tokens that can be generated per session per form, used in the
		 * {@link CSRFPreventionUtil}. If the number of tokens is exceeded, the oldest
		 * one will be invalidated.
		 */
		public static final int CSRF_MAX_TOKENS = 10;
		/**
		 * The time in seconds, a csrf prevention token is valid. This has to be greater
		 * that the time, the user can be expected to fill in a form.
		 */
		public static final int CSRF_TIMEOUT_IN_SECONDS = 10 * 60;
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
		 * Seperator used to split hash and salt
		 */
		public static final String hashSeperator = ".";

		/**
		 * Length (in bytes) of the salt to use
		 */
		public static final int hashSaltLength = 64;

		/**
		 * max time the user stays logged in, while inactive
		 */
		public static final int MAX_INACTIVE_SECONDS = 600;
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
			LOGIN_EMAIL("email"),
			/**
			 * Inputfield used by {@link Admin}s and {@link Godfather}s to provide their
			 * password
			 */
			LOGIN_PASSWORD("password"),
			/**
			 * Inputfield used by {@link Trainee}s to provide their token
			 */
			LOGIN_TOKEN("token");

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
