package de.db.derpate.persistence;

import de.db.derpate.model.TeachingType;

/**
 * Data Access Object providing methods to get {@link TeachingType}s out of the
 * Database
 *
 * @author MichelBlank
 *
 */
public class TeachingTypeDao extends IdDao {
	private static TeachingTypeDao instance;

	private TeachingTypeDao() {
		super(TeachingType.class);
	}

	/**
	 * Returns current instance
	 *
	 * @return instance
	 */
	public static TeachingTypeDao getInstance() {
		if (instance == null) {
			instance = new TeachingTypeDao();
		}
		return instance;
	}
}
