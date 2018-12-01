package de.db.derPate.persistence;

import de.db.derPate.model.Admin;

/**
 * Admin database object used to execute requests to the database
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
