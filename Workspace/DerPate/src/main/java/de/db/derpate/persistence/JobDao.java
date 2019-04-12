package de.db.derpate.persistence;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import de.db.derpate.model.Job;

/**
 * Data Access Object providing methods to get {@link Job}s out of the Database
 *
 * @author MichelBlank
 *
 */
public final class JobDao extends IdDao<@NonNull Integer, @Nullable Job> {
	/**
	 * Constructor
	 */
	public JobDao() {
		super();
	}
}
