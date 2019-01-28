package de.db.derPate.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.eclipse.jdt.annotation.NonNull;
import org.junit.Before;
import org.junit.Test;

import de.db.derPate.model.LoginUser;
import de.db.derPate.model.Trainee;

@SuppressWarnings({ "javadoc", "nls" })
public class URIParamterEncryptionUtilTest {
	@NonNull
	private HttpSession session = new DefaultHttpSession().SESSION;
	@NonNull
	private HttpSession secondSession = new DefaultHttpSession().SESSION;
	@NonNull
	private LoginUser user = new Trainee(Math.abs(new Random().nextInt()), "exampleToken", null); 

	@Before
	public void init() {
		// nothing to do
	}

	@Test
	public void encryptDefault() {
		String unencrypedString = "test"; 
		String encryptedString = URIParameterEncryptionUtil.encrypt(unencrypedString);

		assertNotEquals(unencrypedString, encryptedString);
	}

	@Test
	public void decryptDefault() {
		String unencrypedString = "testText"; 
		String encrypedString = URIParameterEncryptionUtil.encrypt(unencrypedString);
		String decryptedString = URIParameterEncryptionUtil.decrypt(encrypedString);

		assertEquals(unencrypedString, decryptedString);
	}

	@Test
	public void encryptCustom() {
		String unencrypedString = "test"; 
		String encryptedString = URIParameterEncryptionUtil.encrypt(unencrypedString, this.session, this.user);

		assertNotEquals(unencrypedString, encryptedString);
	}

	@Test
	public void decryptCustom() {
		String unencrypedString = "testText"; 
		String encrypedString = URIParameterEncryptionUtil.encrypt(unencrypedString, this.session, this.user);
		String decryptedString = URIParameterEncryptionUtil.decrypt(encrypedString, this.session, this.user);

		assertEquals(unencrypedString, decryptedString);
	}

	@Test
	public void decryptCustomWithWrongPassword() {
		String unencrypedString = "testText"; 
		String encrypedString = URIParameterEncryptionUtil.encrypt(unencrypedString, this.session, this.user);
		String decryptedString = URIParameterEncryptionUtil.decrypt(encrypedString, this.secondSession, this.user);

		assertNull(decryptedString);
	}
}
