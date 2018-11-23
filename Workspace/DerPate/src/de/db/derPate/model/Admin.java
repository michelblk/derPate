package de.db.derPate.model;

import javax.servlet.http.HttpSession;

import de.db.derPate.manager.LoginManager;

/**
 * This dataclass is used to store {@link Admin}s login credentials and to make
 * the {@link Admin} recognized as such, as the {@link LoginUser} stored in the
 * user's {@link HttpSession} (see {@link LoginManager}) can be typecasted back.
 *
 * @author MichelBlank
 *
 */
public class Admin extends UsernamePasswordLogin {
	/**
	 * Default constructor
	 * 
	 * @param id Database id
	 */
	public Admin(int id) {
		super(id);
	}
}
