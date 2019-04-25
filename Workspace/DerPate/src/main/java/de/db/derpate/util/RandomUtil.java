package de.db.derpate.util;

import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.eclipse.jdt.annotation.NonNull;

/**
 * This util may be used to generate random data
 *
 * @author MichelBlank
 */
public class RandomUtil {
	private static final Random RANDOM = new SecureRandom();

	private RandomUtil() {
		// nothing to do
	}

	/**
	 * Generates a random byte array
	 *
	 * @param length Length of array
	 * @return bytes
	 */
	public static byte[] generateRandomBytes(int length) {
		byte[] bytes = new byte[length];
		RANDOM.nextBytes(bytes);
		return bytes;
	}

	/**
	 * Creates a random alphabetic {@link String} (upper case) whose length is the
	 * number of characters specified.<br>
	 * Characters will be chosen from the set of Latin alphabetic characters (A-Z).
	 *
	 * @param length the length of random string to create
	 * @return the random string
	 * @see RandomStringUtils#randomAlphabetic(int)
	 */
	@SuppressWarnings("null")
	@NonNull
	public static String generateRandomAlphabetic(int length) {
		return RandomStringUtils.randomAlphabetic(length).toUpperCase();
	}
}
