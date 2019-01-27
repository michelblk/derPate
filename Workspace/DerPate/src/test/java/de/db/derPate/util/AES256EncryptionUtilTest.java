package de.db.derPate.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class AES256EncryptionUtilTest {
	private String password;
	private String salt;
	AES256EncryptionUtil util;

	@Before
	public void init() {
		this.password = "testpassword";
		this.salt = "0123456789abcdef";
		this.util = new AES256EncryptionUtil(this.password, this.salt); // $NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void encrypt() {
		String unencrypedString = "testText"; //$NON-NLS-1$
		String encryptedString = this.util.encrypt(unencrypedString);

		assertNotEquals(unencrypedString, encryptedString);
	}

	@Test
	public void decrypt() {
		String unencrypedString = "testText"; //$NON-NLS-1$
		String encrypedString = this.util.encrypt(unencrypedString);
		String decryptedString = this.util.decrypt(encrypedString);

		assertEquals(unencrypedString, decryptedString);
	}

	@Test
	public void decryptWithWrongPassword() {
		String unencrypedString = "testText"; //$NON-NLS-1$
		String encrypedString = this.util.encrypt(unencrypedString);
		String decryptedString = AES256EncryptionUtil.decrypt(this.password + "0", this.salt, encrypedString); //$NON-NLS-1$

		assertNull(decryptedString);
	}
}
