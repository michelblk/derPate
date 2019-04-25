package de.db.derpate;

import org.eclipse.jdt.annotation.NonNull;

import de.db.derpate.model.Admin;
import de.db.derpate.model.Godfather;
import de.db.derpate.model.Trainee;
import de.db.derpate.servlet.LoginServlet;
import de.db.derpate.servlet.LogoutServlet;
import de.db.derpate.servlet.adminOnly.GenerateTraineeTokenServlet;
import de.db.derpate.servlet.adminOnly.GodfatherAddServlet;
import de.db.derpate.servlet.adminOnly.TraineeRemoveServlet;
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
	
	/**
	 * Form used by {@link Admin}s to create a new {@link Godfather} account
	 * 
	 * @see GenerateTraineeTokenServlet
	 */
	,ADMIN_ADD_GODFATHER
	
	/**
	 * Form used by {@link Admin}s to update {@link Godfather}s info
	 * 
	 * @see de.db.derpate.servlet.adminOnly.GodfatherUpdateServlet
	 */
	,ADMIN_UPDATE_GODFATHER
	
	/**
	 * Form used by {@link Admin}s to create a new {@link Trainee} account
	 * 
	 * @see GodfatherAddServlet
	 */
	,ADMIN_ADD_TRAINEE
	
	/**
	 * Form used by {@link Admin}s to remove a {@link Trainee} or to remove their chosen {@link Godfather}
	 * 
	 * @see TraineeRemoveServlet
	 */
	,ADMIN_UPDATE_TRAINEE;


	@SuppressWarnings("null")
	@Override
	@NonNull
	public String toString() {
		return super.name();
	}
}
