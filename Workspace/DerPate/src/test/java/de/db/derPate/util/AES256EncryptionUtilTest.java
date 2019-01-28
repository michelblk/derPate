package de.db.derPate.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings({ "javadoc", "nls" })
public class AES256EncryptionUtilTest {
	private String password;
	private String salt;
	AES256EncryptionUtil util;

	@Before
	public void init() {
		this.password = "testpassword";
		this.salt = "0123456789abcdef";
		this.util = new AES256EncryptionUtil(this.password, this.salt);
	}

	@Test
	public void encrypt() {
		String unencrypedString = "testText"; 
		String encryptedString = this.util.encrypt(unencrypedString);

		assertNotEquals(unencrypedString, encryptedString);
	}

	@Test
	public void decrypt() {
		String unencrypedString = "testText"; 
		String encrypedString = this.util.encrypt(unencrypedString);
		String decryptedString = this.util.decrypt(encrypedString);

		assertEquals(unencrypedString, decryptedString);
	}

	@Test
	public void decryptWithWrongPassword() {
		String unencrypedString = "testText"; 
		String encrypedString = this.util.encrypt(unencrypedString);
		String decryptedString = AES256EncryptionUtil.decrypt(this.password + "0", this.salt, encrypedString); 

		assertNull(decryptedString);
	}
}
