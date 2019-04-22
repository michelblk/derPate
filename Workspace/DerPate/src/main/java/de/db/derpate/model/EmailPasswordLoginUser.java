package de.db.derpate.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.hibernate.annotations.NaturalId;

import com.google.gson.annotations.Expose;

/**
 * Abstract class that is used by all classes, that are using email and password
 * as login credentials.
 *
 * @author MichelBlank
 * @see LoginUser
 */
@MappedSuperclass
public abstract class EmailPasswordLoginUser extends LoginUser {
	/**
	 * default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	@NonNull
	@NaturalId(mutable = true)
	@Column(nullable = false)
	@Expose
	private String email;
	@Nullable
	@Column(nullable = false)
	@Expose(serialize = false, deserialize = false)
	private String password = null;

	/**
	 * Default constructor used for hibernate
	 */
	public EmailPasswordLoginUser() {
		super();
		this.email = ""; //$NON-NLS-1$
	}

	/**
	 * Constructor used, when object should not be used for login
	 *
	 * @param id    id
	 * @param email email
	 */
	public EmailPasswordLoginUser(int id, @NonNull String email) {
		super(id);
		this.email = email;
	}

	/**
	 * Constructor used, when object is used for login
	 *
	 * @param id       id
	 * @param email    email
	 * @param password hashed password
	 */
	public EmailPasswordLoginUser(int id, @NonNull String email, @Nullable String password) {
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
}
