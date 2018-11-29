package de.db.derPate.model;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import de.db.derPate.manager.LoginManager;

/**
 * Abstract class that is used by all classes, that are using email and password
 * as login credentials.
 *
 * @author MichelBlank
 * @see LoginUser
 */
public abstract class UsernamePasswordLogin extends LoginUser {
	@NonNull
	private String email = "";
	@Nullable
	private String password = null;

	public UsernamePasswordLogin(int id, @NonNull String email) {
		super(id);
		this.setEmail(email);
	}

	public UsernamePasswordLogin(int id, @NonNull String email, @Nullable String password) {
		this(id, email);
		this.setPassword(password);
	}

	/**
	 * Returns Email
	 *
	 * @return email address
	 */
	@NonNull
	public String getEmail() {
		return this.email;
	}

	/**
	 * Sets email address.<br>
	 * Email may not be null.
	 *
	 * @param email Email to be set. May not be null.
	 */
	public void setEmail(@NonNull String email) {
		this.email = email;
	}

	/**
	 * Returns hashed password.<br>
	 * Might be <b>null</b>, if this object was intended to only hold data, that
	 * might be displayed.
	 *
	 * @return Password or <b>null</b>
	 */
	@Nullable
	public String getPassword() {
		return this.password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password Password to be set or <b>null</b>, if object should no longer
	 *                 be used for login
	 */
	public void setPassword(@Nullable String password) {
		this.password = password;
	}

	/**
	 * Removes the {@link #password} from this object to ensure, that it cannot be
	 * read afterwards. This method is used by the
	 * {@link LoginManager#login(javax.servlet.http.HttpServletRequest, LoginUser)}.
	 *
	 * @see LoginManager#login(javax.servlet.http.HttpServletRequest, LoginUser)
	 */
	@Override
	public void removeSecret() {
		this.setPassword(null);
	}
}
