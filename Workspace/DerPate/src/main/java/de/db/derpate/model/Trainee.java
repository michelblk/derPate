package de.db.derpate.model;

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
	/**
	 * default serial version UID
	 */
	private static final long serialVersionUID = 1L;
	@NonNull
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
	 * Default constructor used by hibernate
	 */
	@SuppressWarnings("unused")
	private Trainee() {
		super();
		this.loginToken = ""; //$NON-NLS-1$
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
	 * Returns the loginToken
	 *
	 * @return the loginToken
	 */
	@NonNull
	public String getLoginToken() {
		return this.loginToken;
	}

	/**
	 * Sets the login token
	 *
	 * @param loginToken the loginToken to set
	 */
	public void setLoginToken(@NonNull String loginToken) {
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
}
