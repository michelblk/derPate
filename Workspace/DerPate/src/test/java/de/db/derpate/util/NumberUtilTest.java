package de.db.derpate.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

@SuppressWarnings({ "javadoc", "nls" })
public class NumberUtilTest {
	@Test
	public void testParseInteger() {
		assertFalse(NumberUtil.parseInteger("1") == null);
		assertFalse(NumberUtil.parseInteger("1") != 1);

		assertTrue(NumberUtil.parseInteger("1") == 1);
		assertTrue(NumberUtil.parseInteger("01") == 1);
		assertTrue(NumberUtil.parseInteger(null) == null);
		assertTrue(NumberUtil.parseInteger("a") == null);
		assertTrue(NumberUtil.parseInteger("1a") == null);
		assertTrue(NumberUtil.parseInteger("2.2") == null);
		assertTrue(NumberUtil.parseInteger("2,2") == null);
	}
}
