package de.db.derPate.model;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import de.db.derPate.manager.LoginManager;

/**
 * Class that holds all the informations of an trainee.
 *
 * @author MichelBlank
 * @see LoginUser
 */
public class Trainee extends LoginUser {
	@NonNull
	private String loginToken;
	@Nullable
	private Godfather godfather = null;

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
		this.loginToken = null;
	}
}
