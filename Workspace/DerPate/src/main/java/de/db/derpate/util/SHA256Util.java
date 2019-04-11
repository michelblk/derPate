package de.db.derpate.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

/**
 * This util uses the SHA-265 algorithm to hash data.<br>
 * It implements the methods of {@link HashUtil}.
 *
 * @author MichelBlank
 *
 */
public final class SHA256Util implements HashUtil {
	private static SHA256Util instance;
	private static final String ALGORITHM = "SHA-256"; //$NON-NLS-1$
	private final Random RANDOM = new SecureRandom();
	private final MessageDigest MESSAGEDIGEST;

	/**
	 * Constructor<br>
	 * Caution: This util may throw a {@link RuntimeException}, if the algorithm
	 * wasn't found
	 *
	 * @throws RuntimeException gets thrown, when {@value SHA256Util#ALGORITHM} was
	 *                          not found
	 */
	private SHA256Util() {
		try {
			this.MESSAGEDIGEST = MessageDigest.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * Returns instance of {@link SHA256Util}
	 *
	 * @return instance
	 */
	public static SHA256Util getInstance() {
		if (instance == null) {
			instance = new SHA256Util();
		}
		return instance;
	}

	/**
	 * Generates a random byte array
	 *
	 * @param length Length of array
	 * @return bytes
	 */
	public byte[] getRandomBytes(int length) {
		byte[] bytes = new byte[length];
		this.RANDOM.nextBytes(bytes);
		return bytes;
	}

	/**
	 * Concatenates two byte arrays
	 *
	 * @param bytes1 Byte array
	 * @param bytes2 Byte array
	 * @return concatenated array
	 */
	private static byte[] combineBytes(byte[] bytes1, byte[] bytes2) {
		byte[] combined = new byte[bytes1.length + bytes2.length];

		System.arraycopy(bytes1, 0, combined, 0, bytes1.length);
		System.arraycopy(bytes2, 0, combined, bytes1.length, bytes2.length);

		return combined;
	}

	/**
	 * Converts bytes array to Base64 {@link String}
	 *
	 * @param bytes byte array
	 * @return {@link String} representing bytes
	 */
	private static String bytesToBase64(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}

	/**
	 * Converts a Base64 {@link String} to bytes array
	 *
	 * @param base64 base64 string
	 * @return bytes
	 */
	private static byte[] base64ToBytes(String base64) {
		return Base64.getDecoder().decode(base64);
	}

	/**
	 * Combines hash and salt to {@link String}
	 *
	 * @param hash          hash
	 * @param salt          salt
	 * @param hashseperator character used to seperate salt and hash
	 * @return String in format "hash.salt"
	 */
	private static String hashToBase64(String hash, String salt, String hashseperator) {
		StringBuilder sb = new StringBuilder();
		sb.append(salt);
		sb.append(hashseperator);
		sb.append(hash);
		return sb.toString();
	}

	@Override
	public String hash(String unhashed, int saltlength, byte[] pepper, String hashseperator) {
		return this.hash(unhashed, this.getRandomBytes(saltlength), pepper, hashseperator);
	}

	@Override
	public String hash(String unhashed, byte[] saltBytes, byte[] pepper, String hashseperator) {
		byte[] unhashedBytes = unhashed.getBytes();
		String salt = bytesToBase64(saltBytes);

		this.MESSAGEDIGEST.reset();
		this.MESSAGEDIGEST.update(saltBytes);

		byte[] unhashedWithPepper = combineBytes(unhashedBytes, pepper); // add pepper to input
		byte[] unhashedWithPepperAndSalt = combineBytes(unhashedWithPepper, saltBytes);
		byte[] hashedBytes = this.MESSAGEDIGEST.digest(unhashedWithPepperAndSalt); // hash
		String hash = bytesToBase64(hashedBytes);

		return hashToBase64(hash, salt, hashseperator);
	}

	@Override
	public boolean isEqual(final String unhashedString, final String completeHash, byte[] pepper,
			String hashseperator) {
		String[] saltHash = completeHash.split("\\" + hashseperator); //$NON-NLS-1$
		if (saltHash.length == 2) {
			byte[] salt = base64ToBytes(saltHash[0]);

			String compareWithHash = this.hash(unhashedString, salt, pepper, hashseperator);

			return compareWithHash.equals(completeHash);
		}
		return false;
	}
}
