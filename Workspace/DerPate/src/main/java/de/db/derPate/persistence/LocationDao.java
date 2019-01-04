package de.db.derPate.persistence;

import de.db.derPate.model.Location;

/**
 * Data Access Object object providing methods to get {@link Location}s out of
 * the Database
 *
 * @author MichelBlank
 *
 */
public class LocationDao extends IdDao {
	private static LocationDao instance;

	private LocationDao() {
		super(Location.class);
	}

	/**
	 * Returns current instance
	 *
	 * @return instance
	 */
	public static LocationDao getInstance() {
		if (instance == null) {
			instance = new LocationDao();
		}
		return instance;
	}
}
