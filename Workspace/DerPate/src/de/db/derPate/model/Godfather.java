package de.db.derPate.model;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import de.db.derPate.manager.LoginManager;

/**
 * This dataclass is used for the godfather login and profile edit and for
 * displaying all godfathers, when logged in as a trainee.
 *
 * @author MichelBlank
 */
public class Godfather extends UsernamePasswordLogin {
	@Nullable
	private GodfatherInformation information;

	/**
	 * Constructor used by godfather login
	 *
	 * @param id          Database id of {@link Godfather}
	 * @param email       Email
	 * @param password    Password
	 * @param information {@link GodfatherInformation}-Object, which is filled with
	 *                    firstname, lastname, location, etc.<b>Can be null</b>
	 *
	 * @see LoginManager
	 */
	public Godfather(int id, @NonNull String email, @NonNull String password,
			@Nullable GodfatherInformation information) {
		this(id, email, information);
		super.setPassword(password);
	}

	/**
	 * Constructor used to store the data for the list of {@link Godfather}s
	 *
	 * @param id          Id from database
	 * @param email       Email address
	 * @param information {@link GodfatherInformation}-Object
	 */
	public Godfather(int id, @NonNull String email, @NonNull GodfatherInformation information) {
		super(id);
		super.setEmail(email);
		this.setInformation(information);
	}

	/**
	 * Returns further informations in a {@link GodfatherInformation}-Object about
	 * this {@link Godfather}.<br>
	 * Might be <b>null</b>, if this object was intended for login uses only.
	 *
	 * @return {@link GodfatherInformation} or <b>null</b>
	 */
	@Nullable
	public GodfatherInformation getInformation() {
		return this.information;
	}

	/**
	 * Sets the {@link GodfatherInformation} of this object.
	 *
	 * @param information {@link GodfatherInformation}-Object to be set.
	 */
	public void setInformation(@Nullable GodfatherInformation information) {
		this.information = information;
	}
}
