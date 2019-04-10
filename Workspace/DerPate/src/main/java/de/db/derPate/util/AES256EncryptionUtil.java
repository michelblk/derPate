package de.db.derPate.util;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

/**
 * This util uses a 256 bit AES algorithm with Galois Counter Mode (GCM)
 * provided by the SpringFramework.
 *
 * @author MichelBlank
 *
 * @see TextEncryptor
 */
public final class AES256EncryptionUtil implements EncryptionUtil {
	private TextEncryptor encryptor;

	/**
	 * Creates a new Instance with the given password and salt.<br>
	 * SpringFramework's {@link BytesEncryptor} says: "The provided salt is expected
	 * to be hex-encoded; it should be random and at least 8 bytes in length. Also
	 * applies a random 16 byte initialization vector to ensure each encrypted
	 * message will be unique. Requires Java 6."<br>
	 * <b>Caution</b>: The salt has to be in hex format and has to have an even
	 * number of characters.
	 *
	 * @param password the password used to generate the encryptor's secret key;
	 *                 should not be shared
	 * @param salt     a hex-encoded, random, site-global salt value to use to
	 *                 generate the key
	 */
	public AES256EncryptionUtil(@NonNull String password, @NonNull String salt) {
		this.encryptor = Encryptors.delux(password, salt);
	}

	/**
	 * Uses the {@link TextEncryptor#encrypt(String)} method to encrypt the given
	 * text.
	 */
	@SuppressWarnings("null")
	@Override
	@NonNull
	public String encrypt(@NonNull String text) {
		return this.encryptor.encrypt(text);
	}

	/**
	 * Encrypts the given text with the given password and salt. Encrypted text is
	 * hex-encoded.<br>
	 * <b>Caution</b>: The salt has to be in hex format and has to have an even
	 * number of characters.
	 *
	 * @param password the password used to generate the encryptor's secret key;
	 *                 should not be shared
	 * @param salt     a hex-encoded, random, site-global salt value to use to
	 *                 generate the key
	 * @param text     the text to encrypt
	 * @return the encrypted text in hexadecimal format
	 *
	 * @see #decrypt(String, String, String)
	 * @see AES256EncryptionUtil#AES256EncryptionUtil(String, String)
	 */
	@NonNull
	public static String encrypt(@NonNull String password, @NonNull String salt, @NonNull String text) {
		return new AES256EncryptionUtil(password, salt).encrypt(text);
	}

	/**
	 * Uses the {@link TextEncryptor#decrypt(String)} method to decrypt the given
	 * encrypted text.
	 */
	@Override
	@Nullable
	public String decrypt(@NonNull String text) {
		try {
			return this.encryptor.decrypt(text);
		} catch (@SuppressWarnings("unused") Exception e) {
			return null;
		}
	}

	/**
	 * Decrypts the given text (has to be hex-coded) with the provided password and
	 * salt.<br>
	 * <b>Caution</b>: The salt has to be in hex format and has to have an even
	 * number of characters.
	 *
	 * @param password      the password used to generate the encryptor's secret
	 *                      key; should not be shared
	 * @param salt          a hex-encoded, random, site-global salt value to use to
	 *                      generate the key
	 * @param encryptedText the encrypted text to decrypt
	 * @return the decrypted text
	 *
	 * @see #encrypt(String, String, String)
	 */
	@Nullable
	public static String decrypt(@NonNull String password, @NonNull String salt, @NonNull String encryptedText) {
		try {
			return Encryptors.delux(password, salt).decrypt(encryptedText);
		} catch (@SuppressWarnings("unused") Exception e) {
			return null;
		}
	}
}
