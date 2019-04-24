package de.db.derpate;

import org.eclipse.jdt.annotation.NonNull;

import de.db.derpate.servlet.LoginServlet;
import de.db.derpate.servlet.LogoutServlet;
import de.db.derpate.servlet.godfatherOnly.GodfatherUpdateServlet;
import de.db.derpate.servlet.traineeOnly.GodfatherSelectServlet;

/**
 * Enum used to identify a frontend-form, that uses tokens, to protect against Cross-Site-Request-Forgery (CSRF).
 *
 * @author MichelBlank
 *
 */
public enum CSRFForm {
	/**
	 * Login for all user types
	 *
	 * @see LoginServlet
	 */
	LOGIN
	/**
	 * Logout
	 *
	 * @see LogoutServlet
	 */
	,LOGOUT
	
	/**
	 * Form for Trainee to choose a godfather permanently
	 * 
	 * @see GodfatherSelectServlet
	 */
	,TRAINEE_SELECT_GODFATHER
	
	/**
	 * Form for godfather to update their own data
	 * 
	 * @see GodfatherUpdateServlet
	 */
	,GODFATHER_UPDATE_SELF
	
	,ADMIN_ADD_GODFATHER
	
	,ADMIN_UPDATE_GODFATHER
	
	,ADMIN_ADD_TRAINEE
	
	,ADMIN_UPDATE_TRAINEE;


	@SuppressWarnings("null")
	@Override
	@NonNull
	public String toString() {
		return super.name();
	}
}
