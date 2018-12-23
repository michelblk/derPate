package util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;

public class CSRFPreventionUtil {

	private HttpSession session;
	private String form;
	private int limit;
	private int timeout;

	@Before
	public void init() {
		this.session = new DefaultHttpSession().SESSION;
		this.form = "test";
		this.limit = 5;
		this.timeout = 5;
	}

	@Test
	public void testTokenLimit() {
		String firstToken = de.db.derPate.util.CSRFPreventionUtil.generateToken(this.session, this.form, this.limit,
				true);
		try {
			TimeUnit.MILLISECONDS.sleep(1); // wait 1 millisecond, so future tokens will be older
		} catch (InterruptedException e) {
			// do nothing
		}
		for (int i = 0; i < this.limit; i++) {
			de.db.derPate.util.CSRFPreventionUtil.generateToken(this.session, this.form, this.limit, true);
		}

		boolean isFirstTokenStillValid = de.db.derPate.util.CSRFPreventionUtil.checkToken(this.session, this.form,
				firstToken, this.timeout);
		assertFalse(isFirstTokenStillValid);
	}

	@Test
	public void testValidity() {
		String token = de.db.derPate.util.CSRFPreventionUtil.generateToken(this.session, this.form, this.limit, false);
		boolean hasToBeTrue = de.db.derPate.util.CSRFPreventionUtil.checkToken(this.session, this.form, token,
				this.timeout);
		assertTrue(hasToBeTrue);
	}

	@Test
	public void testInvalidity() {
		String token = "test";
		boolean hasToBeTrue = de.db.derPate.util.CSRFPreventionUtil.checkToken(this.session, this.form, token,
				this.timeout);
		assertFalse(hasToBeTrue);
	}
}
