package util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class InputVerifyUtil {

	@Test
	public void testNotEmpty() {
		assertFalse(de.db.derPate.util.InputVerifyUtil.isNotEmpty(null));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isNotEmpty(""));

		assertTrue(de.db.derPate.util.InputVerifyUtil.isNotEmpty(" "));
		assertTrue(de.db.derPate.util.InputVerifyUtil.isNotEmpty("test"));
	}

	@Test
	public void testNotBlank() {
		assertFalse(de.db.derPate.util.InputVerifyUtil.isNotBlank(null));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isNotBlank(" "));

		assertTrue(de.db.derPate.util.InputVerifyUtil.isNotBlank("test"));
	}

	@Test
	public void testEmailValidator() {
		assertFalse(de.db.derPate.util.InputVerifyUtil.isEmailAddress(null));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isEmailAddress(" "));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isEmailAddress("test"));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isEmailAddress("test@mustermann."));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isEmailAddress("test@mustermann@de"));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isEmailAddress("test@mustermann"));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isEmailAddress("\"test\"@mustermann"));

		assertTrue(de.db.derPate.util.InputVerifyUtil.isEmailAddress("test@mustermann.de"));
		assertTrue(de.db.derPate.util.InputVerifyUtil.isEmailAddress("test2@mustermann.de"));
		assertTrue(de.db.derPate.util.InputVerifyUtil.isEmailAddress("test.mustermann@mustermann.de"));
	}

	@Test
	public void testPositiveNumber() {
		assertFalse(de.db.derPate.util.InputVerifyUtil.isPositiveNumber(-1f)); // float
		assertFalse(de.db.derPate.util.InputVerifyUtil.isPositiveNumber(-1)); // integer
		assertFalse(de.db.derPate.util.InputVerifyUtil.isPositiveNumber(-1.3d)); // double

		assertTrue(de.db.derPate.util.InputVerifyUtil.isPositiveNumber(0f));
		assertTrue(de.db.derPate.util.InputVerifyUtil.isPositiveNumber(1f)); // float
		assertTrue(de.db.derPate.util.InputVerifyUtil.isPositiveNumber(1)); // integer
		assertTrue(de.db.derPate.util.InputVerifyUtil.isPositiveNumber(1.32)); // double
	}

	@Test
	public void testInteger() {
		assertFalse(de.db.derPate.util.InputVerifyUtil.isInteger("null"));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isInteger(" "));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isInteger("abc"));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isInteger("1-1"));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isInteger("1,1"));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isInteger("1.1"));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isInteger("10e3"));

		assertTrue(de.db.derPate.util.InputVerifyUtil.isInteger("1"));
		assertTrue(de.db.derPate.util.InputVerifyUtil.isInteger("-1"));
	}

	@Test
	public void testFloat() {
		assertFalse(de.db.derPate.util.InputVerifyUtil.isFloat(null));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isFloat(" "));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isFloat("abc"));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isFloat("1-1"));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isFloat("1,1"));

		assertTrue(de.db.derPate.util.InputVerifyUtil.isFloat("1"));
		assertTrue(de.db.derPate.util.InputVerifyUtil.isFloat("-1"));
		assertTrue(de.db.derPate.util.InputVerifyUtil.isFloat("1.1"));
		assertTrue(de.db.derPate.util.InputVerifyUtil.isFloat("10e3"));
	}

	@Test
	public void testWord() {
		assertFalse(de.db.derPate.util.InputVerifyUtil.isWord(null));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isWord(" "));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isWord("Example text"));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isWord("3xample"));

		assertTrue(de.db.derPate.util.InputVerifyUtil.isWord("example"));
		assertTrue(de.db.derPate.util.InputVerifyUtil.isWord("examPLE"));
	}

	@Test
	public void testSentence() {
		assertFalse(de.db.derPate.util.InputVerifyUtil.isSentence(null));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isSentence(" "));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isSentence("3xample"));

		assertTrue(de.db.derPate.util.InputVerifyUtil.isSentence("Example text"));
		assertTrue(de.db.derPate.util.InputVerifyUtil.isSentence("example"));
		assertTrue(de.db.derPate.util.InputVerifyUtil.isSentence("examPLE"));
	}

	@Test
	public void testUuid() {
		assertFalse(de.db.derPate.util.InputVerifyUtil.isUuid(null));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isUuid("4-2-3"));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isUuid("550e8404-E29b-11D-a716-446655441234"));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isUuid("550e8400-e29b-11d4-a716-446655440000-a716-475"));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isUuid("550e8400-e29b-11d4-a716-4466554400000"));

		assertTrue(de.db.derPate.util.InputVerifyUtil.isUuid("550e8404-E29b-11D4-a716-446655441234"));
	}
}
