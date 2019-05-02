package de.db.derpate.persistence;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import de.db.derpate.model.TeachingType;

/**
 * Data Access Object providing methods to get {@link TeachingType}s out of the
 * Database
 *
 * @author MichelBlank
 *
 */
public final class TeachingTypeDao extends IdDao<@NonNull Integer, @Nullable TeachingType> {
	/**
	 * Constructor
	 */
	public TeachingTypeDao() {
		super();
	}
}
