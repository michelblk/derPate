/**
 *
 */
package de.db.derPate;

import de.db.derPate.model.Admin;
import de.db.derPate.model.Godfather;
import de.db.derPate.model.Trainee;

/**
 * This enum contains the three user-types to easily determine which user is
 * logged in.
 *
 * @author MichelBlank
 *
 */
public enum Usermode {
	/**
	 * @see Admin
	 */
	ADMIN,
	/**
	 * @see Godfather
	 */
	GODFATHER,
	/**
	 * @see Trainee
	 */
	TRAINEE;
}
