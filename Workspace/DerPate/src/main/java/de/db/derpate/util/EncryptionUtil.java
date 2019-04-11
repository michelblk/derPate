package de.db.derpate.util;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * This interface should be used for all encryption utils, so that they can be
 * replaced easily.
 *
 * @author MichelBlank
 *
 */
public interface EncryptionUtil {
	/**
	 * Encrypts the given {@link String} and returns the result
	 *
	 * @param stringToEncrypt the {@link String} to encrypt
	 * @return the encrypted {@link String}
	 */
	@NonNull
	public String encrypt(@NonNull String stringToEncrypt);

	/**
	 * Decrypts the given {@link String} and returns the result or
	 * <code>null</code>, if decryption failed to to a wrong password or salt
	 *
	 * @param stringToDecrypt the {@link String} to decrypt
	 * @return the decrypted {@link String} or <code>null</code>, if the decryption
	 *         failed
	 */
	@Nullable
	public String decrypt(@NonNull String stringToDecrypt);
}
