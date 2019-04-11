package de.db.derpate.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import org.eclipse.jdt.annotation.NonNull;
import org.junit.Before;
import org.junit.Test;

import de.db.derpate.util.AES256EncryptionUtil;

@SuppressWarnings({ "javadoc", "nls" })
public class AES256EncryptionUtilTest {
	@NonNull
	private String password = "testpassword";
	@NonNull
	private String salt = "0123456789abcdef";
	AES256EncryptionUtil util;

	@Before
	public void init() {
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
