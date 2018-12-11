package de.db.derPate.persistence;

import de.db.derPate.model.Job;
import de.db.derPate.model.TeachingType;

/**
 * Database object providing methods to get {@link Job}s out of the Database
 *
 * @author MichelBlank
 *
 */
public class JobDao extends IdDao {
	private static JobDao instance;

	private JobDao() {
		super(TeachingType.class);
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
