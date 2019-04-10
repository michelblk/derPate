package de.db.derPate;

import org.eclipse.jdt.annotation.NonNull;

import de.db.derPate.servlet.LoginServlet;
import de.db.derPate.servlet.LogoutServlet;
import de.db.derPate.servlet.godfatherOnly.GodfatherUpdateServlet;
import de.db.derPate.servlet.traineeOnly.GodfatherSelectServlet;

/**
 * Enum used to identify a frontend-form, that uses csrf tokens. It set's how
 * many tokens are valid at the same time (for example when a user opens a page
 * multiple times)
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
	 * Form for Trainee to select a godfather
	 * 
	 * @see GodfatherSelectServlet
	 */
	,TRAINEE_SELECT_GODFATHER
	
	/**
	 * Form for godfather to updated the data of him/herself
	 * 
	 * @see GodfatherUpdateServlet
	 */
	,GODFATHER_UPDATE_SELF;


	@SuppressWarnings("null")
	@Override
	@NonNull
	public String toString() {
		return super.name();
	}
}
