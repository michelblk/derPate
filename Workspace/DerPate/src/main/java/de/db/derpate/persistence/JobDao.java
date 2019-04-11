package de.db.derpate.persistence;

import de.db.derpate.model.Job;

/**
 * Data Access Object providing methods to get {@link Job}s out of the Database
 *
 * @author MichelBlank
 *
 */
public class JobDao extends IdDao {
	private static JobDao instance;

	private JobDao() {
		super(Job.class);
	}

	/**
	 * Returns current instance
	 *
	 * @return instance
	 */
	public static JobDao getInstance() {
		if (instance == null) {
			instance = new JobDao();
		}
		return instance;
	}
}
