package de.db.derpate.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import de.db.derpate.Constants;
import de.db.derpate.model.LoginUser;

/**
 * This util encrypts URI parameters to prevent the user from guessing the
 * values.
 *
 * @author MichelBlank
 *
 */
public final class URIParameterEncryptionUtil {
	private static EncryptionUtil defaultEncrypter;
	@NonNull
	private static final String DEFAULT_PASSWORD = Constants.Security.ENCRYPTION_AES256_PASSWORD;
	@NonNull
	private static final String DEFAULT_SALT = Constants.Security.ENCRYPTION_AES256_SALT;

	static {
		defaultEncrypter = new AES256EncryptionUtil(DEFAULT_PASSWORD, DEFAULT_SALT);
	}

	private URIParameterEncryptionUtil() {
		// nothing to do
	}

	/**
	 * Returns a hexadecimal representation of the (with global password and salt)
	 * AES256 encrypted value.<br>
	 * This encryption method should be used, when links are visible to the user.
	 * The user may copy this link and use it some time later.
	 *
	 * @param parameterValue the value of the parameter to encrypt
	 * @return the encrypted value (hex {@link String})
	 */
	@NonNull
	public static String encrypt(@NonNull String parameterValue) {
		return defaultEncrypter.encrypt(parameterValue);
	}

	/**
	 * Returns a hexadecimal representation of the (with global password and salt)
	 * AES256 encrypted int value.<br>
	 * This encryption method should be used, when links are visible to the user.
	 * The user may copy this link and use it some time later.
	 *
	 * @param parameterValue the int value of the parameter to encrypt
	 * @return the encrypted value (hex {@link String})
	 */
	@SuppressWarnings("null")
	@NonNull
	public static String encrypt(int parameterValue) {
		return defaultEncrypter.encrypt(Integer.toString(parameterValue));
	}

	/**
	 * This static method encrypts the given parameterValue with the
	 * {@link HttpSession#getId()} as the password and {@link LoginUser#getId()} as
	 * salt (appending a "0" to the salt, if the number of characters is
	 * uneven).<br>
	 * As a salt, the {@link #DEFAULT_SALT} will be used. If a {@link LoginUser} is
	 * set, the id will be appended to the salt.
	 *
	 * @param parameterValue the value of the parameter to encrypt
	 * @param session        the {@link HttpSession} the client is using
	 * @param user           the {@link LoginUser}.
	 * @return the encrypted value
	 */
	@SuppressWarnings("null")
	@NonNull
	public static String encrypt(@NonNull String parameterValue, @NonNull final HttpSession session,
			@Nullable LoginUser user) {
		String encryptionPassword = session.getId();
		String salt = DEFAULT_SALT;
		if (user != null) {
			salt += Integer.toHexString(user.getId());
		}

		if (salt.length() % 2 != 0) {
			// if salt has not a even number of characters, make it even by appending a "0"
			// (see AES256EncryptionUtil#encrypt)
			salt = "0" + salt; //$NON-NLS-1$
		}

		return AES256EncryptionUtil.encrypt(encryptionPassword, salt, parameterValue);
	}

	/**
	 * Returns the decrypted AES256 value (represented in a base64 format). It must
	 * have been encrypted with the global password and salt.
	 *
	 * @param parameterValue the received value of the parameter
	 * @return the decrypted value or <code>null</code>, if parameterValue is
	 *         <code>null</code> or value could not get decrypted
	 * @see #encrypt(String)
	 */
	@Nullable
	public static String decrypt(@Nullable String parameterValue) {
		if (parameterValue == null) {
			return null;
		}
		return defaultEncrypter.decrypt(parameterValue);
	}

	/**
	 * Takes a Array of encrypted AES256 values and returns those, who could get
	 * decrypted.<br>
	 * Useful for the evaluation of http parameters (e.g. for checkboxes).<br>
	 * Returns <code>null</code>, if parameterValues was null or no value could get
	 * decrypted.
	 *
	 * @param parameterValues the received values
	 * @return a {@link List} of the decryped values ({@link String}s or
	 *         <code>null</code>, if parameter could not get decrypted), or
	 *         <code>null</code>, if parameterValues was null
	 */
	@Nullable
	public static List<@Nullable String> decrypt(@Nullable String[] parameterValues) {
		List<@Nullable String> decryptedValues = new ArrayList<>();

		if (parameterValues != null) {
			for (String parameterValue : parameterValues) {
				String decryptedValue = decrypt(parameterValue);
				if (decryptedValue != null) {
					decryptedValues.add(decryptedValue);
				}
			}
		}

		return decryptedValues.size() > 0 ? decryptedValues : null;
	}

	/**
	 * Decryptes the given {@link String}, checks if the result is an integer and
	 * parses it. If given {@link String} is <code>null</code> or not a number,
	 * <code>null</code> is returned
	 *
	 * @param parameterValue received value
	 * @return decrypted integer or <code>null</code>, if parameterValue was null or
	 *         not an encrypted integer
	 */
	@Nullable
	public static Integer decryptToInteger(@Nullable String parameterValue) {
		Integer result = null;

		if (parameterValue != null) {
			String decrypted = decrypt(parameterValue);
			result = NumberUtil.parseInteger(decrypted);
		}

		return result;
	}

	/**
	 * This static method decrypts the given parameterValue (should be hex) with the
	 * given {@link HttpSession#getId()} and the id of the {@link LoginUser}, if
	 * set.<br>
	 * As a salt, the {@link #DEFAULT_SALT} will be used. If a {@link LoginUser} is
	 * set, the id will be appended to the salt.
	 *
	 * @param parameterValue
	 * @param session
	 * @param user
	 * @return the decrypted value
	 */
	@SuppressWarnings("null")
	@Nullable
	public static String decrypt(@NonNull String parameterValue, @NonNull final HttpSession session,
			@Nullable LoginUser user) {
		String encryptionPassword = session.getId();
		String salt = DEFAULT_SALT;
		if (user != null) {
			salt += Integer.toHexString(user.getId());
		}

		if (salt.length() % 2 != 0) {
			// if salt has not a even number of characters, make it even by appending a "0"
			// (see AES256EncryptionUtil#encrypt)
			salt = "0" + salt; //$NON-NLS-1$
		}

		return AES256EncryptionUtil.decrypt(encryptionPassword, salt, parameterValue);
	}
}
