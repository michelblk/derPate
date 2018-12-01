package de.db.derPate.model;

/**
 * Baseclass of all classes, that can store data, which you can use to log on.
 *
 * @author MichelBlank
 *
 */
public abstract class LoginUser extends Id {
	/**
	 * Constructor
	 * 
	 * @param id id
	 */
	public LoginUser(int id) {
		super(id);
	}

	/**
	 * Is used to remove password or login-token, so that it cannot be read out of
	 * the session afterwards.<br>
	 * Caution: You may have to get the data again, when you want to check whether a
	 * password is correct or wrong.
	 */
	public abstract void removeSecret();
}
