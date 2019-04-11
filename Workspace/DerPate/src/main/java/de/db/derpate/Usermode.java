/**
 *
 */
package de.db.derpate;

import de.db.derpate.model.Admin;
import de.db.derpate.model.Godfather;
import de.db.derpate.model.Trainee;

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
