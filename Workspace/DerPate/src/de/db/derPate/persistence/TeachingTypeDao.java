package de.db.derPate.persistence;

import de.db.derPate.model.Job;

public class TeachingTypeDao extends IdDao {
	private static TeachingTypeDao instance;

	private TeachingTypeDao() {
		super(Job.class);
	}

	public static TeachingTypeDao getInstance() {
		if (instance == null) {
			instance = new TeachingTypeDao();
		}
		return instance;
	}
}
