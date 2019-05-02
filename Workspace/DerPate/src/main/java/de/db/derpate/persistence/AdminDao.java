package de.db.derpate.persistence;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import de.db.derpate.model.Admin;

/**
 * Data Access Object providing methods to get {@link Admin} objects out of the
 * Database
 *
 * @author MichelBlank
 *
 */
public final class AdminDao extends EmailPasswordLoginUserDao<@NonNull Integer, @Nullable Admin> {
	/**
	 * Constructor
	 */
	public AdminDao() {
		super();
	}
}
