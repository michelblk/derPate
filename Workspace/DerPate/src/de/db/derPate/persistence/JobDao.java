package de.db.derPate.persistence;

import de.db.derPate.model.TeachingType;

public class JobDao extends IdDao {
	private static JobDao instance;

	private JobDao() {
		super(TeachingType.class);
	}

	public static JobDao getInstance() {
		if (instance == null) {
			instance = new JobDao();
		}
		return instance;
	}
}
