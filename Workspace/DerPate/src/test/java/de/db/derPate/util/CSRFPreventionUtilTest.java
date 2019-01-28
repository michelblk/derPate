package de.db.derPate.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;

import de.db.derPate.CSRFForm;

@SuppressWarnings({ "javadoc", "nls" })
public class CSRFPreventionUtilTest {

	private HttpSession session;

	@Before
	public void init() {
		this.session = new DefaultHttpSession().SESSION;
	}

	@Test
	public void testTokenLimit() {
		for (CSRFForm form : CSRFForm.values()) {
			if (!form.isRequestBased()) {
				// this test cannot test tokens without a limit
				continue;
			}

			String firstToken = CSRFPreventionUtil.generateToken(this.session, form);
			try {
				TimeUnit.MILLISECONDS.sleep(1); // wait 1 millisecond, so future tokens will be older
			} catch (InterruptedException e) {
				// do nothing
			}
			for (int i = 0; i < form.getMaxTokens(); i++) {
				CSRFPreventionUtil.generateToken(this.session, form);
			}

			boolean isFirstTokenStillValid = CSRFPreventionUtil.checkToken(this.session, form, firstToken);
			assertFalse(isFirstTokenStillValid);
		}
	}

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
