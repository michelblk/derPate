package de.db.derPate.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.hibernate.annotations.NaturalId;

import com.google.gson.annotations.Expose;

import de.db.derPate.manager.LoginManager;

/**
 * Class that holds all the informations of an trainee.
 *
 * @author MichelBlank
 * @see LoginUser
 */
@Entity
@Table(name = "Trainee")
@AttributeOverride(name = "id", column = @Column(name = "Id_Trainee"))
public class Trainee extends LoginUser {
	@Nullable
	@Column(name = "Login_Code", nullable = false)
	@NaturalId(mutable = true)
	@Expose
	private String loginToken;
	@Nullable
	@ManyToOne
	@JoinColumn(name = "Id_Godfather", nullable = true)
	@Expose
	private Godfather godfather = null;

	/**
	 * Default constructor used for hibernate
	 */
	Trainee() {
		super();
	}

	/**
	 * Constructor that sets the loginToken and the {@link Godfather}.
	 *
	 * @param id         Database id
	 * @param loginToken LoginToken
	 * @param godfather  Godfather, if trainee already selected one
	 */
	public Trainee(int id, @NonNull String loginToken, @Nullable Godfather godfather) {
		super(id);
		this.loginToken = loginToken;
		this.setGodfather(godfather);
	}

	/**
	 * Returns the loginToken<br>
	 * Might be <code>null</code>, if {@link #removeSecret()} was called.
	 *
	 * @return the loginToken
	 */
	@Nullable
	public String getLoginToken() {
		return this.loginToken;
	}

	/**
	 * Sets the login token
	 *
	 * @param loginToken the loginToken to set
	 */
	public void setLoginToken(@Nullable String loginToken) {
		this.loginToken = loginToken;
	}

	/**
	 * Returns the {@link Godfather}, if {@link Trainee} already selected one.<br>
	 * Might be <code>null</code>, if {@link Trainee} didn't select a
	 * {@link Godfather}.
	 *
	 * @return the {@link Godfather}
	 */
	@Nullable
	public Godfather getGodfather() {
		return this.godfather;
	}

	/**
	 * Sets the {@link Godfather}.
	 *
	 * @param godfather the {@link Godfather} to set
	 */
	public void setGodfather(@Nullable Godfather godfather) {
		this.godfather = godfather;
	}

	/**
	 * Removes the loginToken from this object, to ensure that this may not be
	 * printed to the user accidentally.
	 *
	 * @see LoginManager#login(javax.servlet.http.HttpServletRequest, LoginUser)
	 */
	@Override
	public void removeSecret() {
		this.setLoginToken(null);
	}
}
