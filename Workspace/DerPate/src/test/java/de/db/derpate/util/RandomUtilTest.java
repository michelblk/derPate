package de.db.derpate.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

@SuppressWarnings({ "javadoc" })
public class RandomUtilTest {
	@Test
	public void testGenerateRandomAlphabetic() {
		int length = 20;
		String random = RandomUtil.generateRandomAlphabetic(length);

		assertTrue(random.toUpperCase().equals(random));
		assertTrue(random.length() == length);

		String randomCharacter = RandomUtil.generateRandomAlphabetic(1);
		for (int i = 1; i < 26; i++) { // until every letter was used
			assertTrue(randomCharacter.equals(RandomUtil.generateRandomAlphabetic(1)) == false);
		}
	}
}
