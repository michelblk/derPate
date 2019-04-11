package de.db.derpate.persistence;

import de.db.derpate.model.Admin;

/**
 * Data Access Object providing methods to get {@link Admin} objects out of the
 * Database
 *
 * @author MichelBlank
 *
 */
public class AdminDao extends EmailPasswordLoginUserDao {
	/**
	 * {@link AdminDao} instance
	 */
	private static AdminDao instance;

	/**
	 * Constructor
	 */
	private AdminDao() {
		super(Admin.class);
	}

	/**
	 * Creates {@link AdminDao} instance if none was created before and returns it
	 *
	 * @return instance
	 */
	public static AdminDao getInstance() {
		if (instance == null) {
			instance = new AdminDao();
		}
		return instance;
	}
}
