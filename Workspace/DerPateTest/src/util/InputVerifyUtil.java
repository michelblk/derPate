package util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class InputVerifyUtil {

	@Before
	public void init() {

	}

	@Test
	public void testNotEmpty() {
		assertFalse(de.db.derPate.util.InputVerifyUtil.isNotEmpty(null));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isNotEmpty(""));

		assertTrue(de.db.derPate.util.InputVerifyUtil.isNotEmpty("test"));
	}

	@Test
	public void testEmailValidator() {
		assertFalse(de.db.derPate.util.InputVerifyUtil.isEmailAddress(null));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isEmailAddress("test"));
		assertFalse(de.db.derPate.util.InputVerifyUtil.isEmailAddress("test@mustermann."));

		assertTrue(de.db.derPate.util.InputVerifyUtil.isEmailAddress("test@mustermann.de"));
	}
}
