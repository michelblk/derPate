package de.db.derPate.util;

/**
 * This interface provides methods that are normally used with hash algorithms.
 * It ensures, that hash algorithms in the software can be easily replaced, if
 * needed.
 *
 * @author MichelBlank
 *
 */
public interface HashUtil {
	/**
	 * Hashes a String with a given salt and pepper
	 *
	 * @param unhashed      unhashed {@link String}
	 * @param salt          salt to use
	 * @param pepper        pepper to use
	 * @param hashseperator character used to seperate salt and hash
	 * @return hash as {@link String}
	 */
	public String hash(String unhashed, byte[] salt, byte[] pepper, String hashseperator);

	/**
	 * Hashes a String with a given pepper and generates a random salt with the
	 * given length
	 *
	 * @param unhashed      unhashed {@link String}
	 * @param length        length of the salt to be used. Should be at lest 32
	 *                      (Bytes).
	 * @param pepper        pepper to use
	 * @param hashseperator character used to seperate salt and hash
	 * @return hash as {@link String}
	 */
	public String hash(String unhashed, int length, byte[] pepper, String hashseperator);

	/**
	 * @param unhashed      unhashed {@link String}, the hashed {@link String}
	 *                      should be compared to
	 * @param hashed        hashed String with salt
	 * @param pepper        pepper
	 * @param hashseperator character used to seperate salt and hash
	 * @return <code>true</code>, if unhashed {@link String} can be hashed to equal
	 *         the hashed string;<code>false</code>, if unhashed {@link String} is
	 *         not equal to what the hashed string formerly was
	 */
	public boolean isEqual(String unhashed, String hashed, byte[] pepper, String hashseperator);
}
