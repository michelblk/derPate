package de.db.derPate.persistence;

import de.db.derPate.model.Godfather;

/**
 * Database object providing methods to get {@link Godfather} objects out of the
 * Database
 *
 * @author MichelBlank
 *
 */
public class GodfatherDao extends EmailPasswordLoginUserDao {
	private static GodfatherDao instance;

	private GodfatherDao() {
		super(Godfather.class);
	}

	/**
	 * Returns current instance
	 *
	 * @return instance
	 */
	public static GodfatherDao getInstance() {
		if (instance == null) {
			instance = new GodfatherDao();
		}
		return instance;
	}
}
