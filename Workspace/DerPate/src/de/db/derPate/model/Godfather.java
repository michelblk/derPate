package de.db.derPate.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.hibernate.annotations.NaturalId;

import de.db.derPate.manager.LoginManager;

/**
 * This dataclass is used for the godfather login and profile edit and for
 * displaying all godfathers, when logged in as a trainee.
 *
 * @author MichelBlank
 */
@Entity
@Table(name = "Godfather")
@AttributeOverride(name = "id", column = @Column(name = "Id_Godfather"))
public class Godfather extends EmailPasswordLoginUser {
	@Nullable
	private GodfatherInformation information;

	/**
	 * Default constructor used for hibernate
	 */
	Godfather() {
		super();
	}

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
	public Godfather(int id, @NonNull String email, @Nullable GodfatherInformation information) {
		super(id, email);
		this.setInformation(information);
	}

	/**
	 * @see de.db.derPate.model.EmailPasswordLoginUser#getEmail()
	 */
	@Override
	@NaturalId(mutable = true)
	@Column(name = "Email", nullable = false, unique = true)
	public @NonNull String getEmail() {
		return super.getEmail();
	}

	/**
	 * @see de.db.derPate.model.EmailPasswordLoginUser#getPassword()
	 */
	@Override
	@Column(name = "Password", nullable = false)
	public @Nullable String getPassword() {
		return super.getPassword();
	}

	/**
	 * Returns further informations in a {@link GodfatherInformation}-Object about
	 * this {@link Godfather}.<br>
	 * Might be <b>null</b>, if this object was intended for login uses only.
	 *
	 * @return {@link GodfatherInformation} or <b>null</b>
	 */
	@Nullable
	@Embedded
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
