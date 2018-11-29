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
	@Nullable
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
		this.setLoginToken(loginToken);
		this.setGodfather(godfather);
	}

	/**
	 * Returns {@link #loginToken}<br>
	 * Might be <code>null</code>, if {@link #removeSecret()} was called.
	 *
	 * @return the loginToken
	 */
	@Nullable
	public String getLoginToken() {
		return this.loginToken;
	}

	/**
	 * Sets the {@link #loginToken}.
	 *
	 * @param loginToken the loginToken to set
	 */
	private void setLoginToken(@Nullable String loginToken) {
		this.loginToken = loginToken;
	}

	/**
	 * Returns the {@link #godfather}, if {@link Trainee} already selected one.<br>
	 * Might be <code>null</code>, if {@link Trainee} didn't select a
	 * {@link Godfather}.
	 *
	 * @return the {@link #godfather}
	 */
	@Nullable
	public Godfather getGodfather() {
		return this.godfather;
	}

	/**
	 * Sets the {@link #godfather}.
	 *
	 * @param godfather the godfather to set
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
