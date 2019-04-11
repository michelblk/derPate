package de.db.derpate.util;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.db.derpate.Constants;
import de.db.derpate.util.SHA256Util;

@SuppressWarnings({ "javadoc", "nls" })
public class SHA256UtilTest {
	private SHA256Util util;
	private int saltPepperLength;
	private byte[] pepper;
	private String input;
	private String hashseperator;

	@Before
	public void init() {
		this.util = SHA256Util.getInstance();
		this.saltPepperLength = Constants.Login.hashSaltLength;
		this.pepper = this.util.getRandomBytes(this.saltPepperLength);
		this.input = "test";
		this.hashseperator = Constants.Login.hashSeparator;
	}

	@Test
	public void testHashesHaveToBeUnequal() {
		String hash1 = this.util.hash(this.input, this.saltPepperLength, this.pepper, this.hashseperator);
		String hash2 = this.util.hash(this.input, this.saltPepperLength, this.pepper, this.hashseperator);
		assertNotEquals(hash1, hash2);
	}

	@Test
	public void testEqual() {
		String hash = this.util.hash(this.input, this.saltPepperLength, this.pepper, this.hashseperator);
		boolean isEqual = this.util.isEqual(this.input, hash, this.pepper, this.hashseperator);
		assertTrue(isEqual);
	}

	@Test
	public void testUnequal() {
		String hash = this.util.hash(this.input, this.saltPepperLength, this.pepper, this.hashseperator);
		boolean isNotEqual = this.util.isEqual(this.input + "?", hash, this.pepper, this.hashseperator);
		assertFalse(isNotEqual);
	}
}
