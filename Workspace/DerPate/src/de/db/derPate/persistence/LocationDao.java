package de.db.derPate.persistence;

import de.db.derPate.model.Location;

public class LocationDao extends IdDao {
	private static LocationDao instance;

	private LocationDao() {
		super(Location.class);
	}

	public static LocationDao getInstance() {
		if (instance == null) {
			instance = new LocationDao();
		}
		return instance;
	}
}
