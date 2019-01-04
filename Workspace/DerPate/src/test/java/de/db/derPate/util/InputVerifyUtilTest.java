package de.db.derPate.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class InputVerifyUtilTest {

	@Test
	public void testNotEmpty() {
		assertFalse(InputVerifyUtil.isNotEmpty(null));
		assertFalse(InputVerifyUtil.isNotEmpty(""));

		assertTrue(InputVerifyUtil.isNotEmpty(" "));
		assertTrue(InputVerifyUtil.isNotEmpty("test"));
	}

	@Test
	public void testNotBlank() {
		assertFalse(InputVerifyUtil.isNotBlank(null));
		assertFalse(InputVerifyUtil.isNotBlank(" "));

		assertTrue(InputVerifyUtil.isNotBlank("test"));
	}

	@Test
	public void testEmailValidator() {
		assertFalse(InputVerifyUtil.isEmailAddress(null));
		assertFalse(InputVerifyUtil.isEmailAddress(" "));
		assertFalse(InputVerifyUtil.isEmailAddress("test"));
		assertFalse(InputVerifyUtil.isEmailAddress("test@mustermann."));
		assertFalse(InputVerifyUtil.isEmailAddress("test@mustermann@de"));
		assertFalse(InputVerifyUtil.isEmailAddress("test@mustermann"));
		assertFalse(InputVerifyUtil.isEmailAddress("\"test\"@mustermann"));

		assertTrue(InputVerifyUtil.isEmailAddress("test@mustermann.de"));
		assertTrue(InputVerifyUtil.isEmailAddress("test2@mustermann.de"));
		assertTrue(InputVerifyUtil.isEmailAddress("test.mustermann@mustermann.de"));
	}

	@Test
	public void testPositiveNumber() {
		assertFalse(InputVerifyUtil.isPositiveNumber(-1f)); // float
		assertFalse(InputVerifyUtil.isPositiveNumber(-1)); // integer
		assertFalse(InputVerifyUtil.isPositiveNumber(-1.3d)); // double

		assertTrue(InputVerifyUtil.isPositiveNumber(0f));
		assertTrue(InputVerifyUtil.isPositiveNumber(1f)); // float
		assertTrue(InputVerifyUtil.isPositiveNumber(1)); // integer
		assertTrue(InputVerifyUtil.isPositiveNumber(1.32)); // double
	}

	@Test
	public void testInteger() {
		assertFalse(InputVerifyUtil.isInteger(null));
		assertFalse(InputVerifyUtil.isInteger(" "));
		assertFalse(InputVerifyUtil.isInteger("abc"));
		assertFalse(InputVerifyUtil.isInteger("1-1"));
		assertFalse(InputVerifyUtil.isInteger("1,1"));
		assertFalse(InputVerifyUtil.isInteger("1.1"));
		assertFalse(InputVerifyUtil.isInteger("10e3"));

		assertTrue(InputVerifyUtil.isInteger("1"));
		assertTrue(InputVerifyUtil.isInteger("-1"));
	}

	@Test
	public void testFloat() {
		assertFalse(InputVerifyUtil.isFloat(null));
		assertFalse(InputVerifyUtil.isFloat(" "));
		assertFalse(InputVerifyUtil.isFloat("abc"));
		assertFalse(InputVerifyUtil.isFloat("1-1"));
		assertFalse(InputVerifyUtil.isFloat("1,1"));

		assertTrue(InputVerifyUtil.isFloat("1"));
		assertTrue(InputVerifyUtil.isFloat("-1"));
		assertTrue(InputVerifyUtil.isFloat("1.1"));
		assertTrue(InputVerifyUtil.isFloat("10e3"));
	}

	@Test
	public void testWord() {
		assertFalse(InputVerifyUtil.isWord(null));
		assertFalse(InputVerifyUtil.isWord(" "));
		assertFalse(InputVerifyUtil.isWord("Example text"));
		assertFalse(InputVerifyUtil.isWord("3xample"));

		assertTrue(InputVerifyUtil.isWord("example"));
		assertTrue(InputVerifyUtil.isWord("examPLE"));
	}

	@Test
	public void testSentence() {
		assertFalse(InputVerifyUtil.isSentence(null));
		assertFalse(InputVerifyUtil.isSentence(" "));
		assertFalse(InputVerifyUtil.isSentence("3xample"));

		assertTrue(InputVerifyUtil.isSentence("Example text"));
		assertTrue(InputVerifyUtil.isSentence("example"));
		assertTrue(InputVerifyUtil.isSentence("examPLE"));
	}

	@Test
	public void testUuid() {
		assertFalse(InputVerifyUtil.isUuid(null));
		assertFalse(InputVerifyUtil.isUuid("4-2-3"));
		assertFalse(InputVerifyUtil.isUuid("550e8404-E29b-11D-a716-446655441234"));
		assertFalse(InputVerifyUtil.isUuid("550e8400-e29b-11d4-a716-446655440000-a716-475"));
		assertFalse(InputVerifyUtil.isUuid("550e8400-e29b-11d4-a716-4466554400000"));

		assertTrue(InputVerifyUtil.isUuid("550e8404-E29b-11D4-a716-446655441234"));
	}
}
