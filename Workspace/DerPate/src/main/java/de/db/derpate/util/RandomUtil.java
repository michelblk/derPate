package de.db.derpate.util;

import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

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
	 * Creates a random string whose length is the number of characters
	 * specified.<br>
	 * Characters will be chosen from the set of Latin alphabetic characters (a-z,
	 * A-Z).
	 *
	 * @param length the length of random string to create
	 * @return the random string
	 */
	public static String generateRandomString(int length) {
		return RandomStringUtils.randomAlphabetic(length);
	}
}
