package de.db.derpate.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpSession;

import org.eclipse.jdt.annotation.NonNull;
import org.junit.Test;

import de.db.derpate.CSRFForm;
import de.db.derpate.util.CSRFPreventionUtil;

@SuppressWarnings({ "javadoc", "nls" })
public class CSRFPreventionUtilTest {

	@NonNull
	private HttpSession session = new DefaultHttpSession().SESSION;

	@Test
	public void testValidity() {
		for (CSRFForm form : CSRFForm.values()) {
			String token = CSRFPreventionUtil.generateToken(this.session, form);
			boolean hasToBeTrue = CSRFPreventionUtil.checkToken(this.session, form, token);
			assertTrue(hasToBeTrue);
		}
	}

	@Test
	public void testInvalidity() {
		for (CSRFForm form : CSRFForm.values()) {
			String token = "test";
			boolean hasToBeFalse = CSRFPreventionUtil.checkToken(this.session, form, token);
			assertFalse(hasToBeFalse);
		}
	}
}
