package de.db.derpate.persistence;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import de.db.derpate.model.Location;

/**
 * Data Access Object object providing methods to get {@link Location}s out of
 * the Database
 *
 * @author MichelBlank
 *
 */
public final class LocationDao extends IdDao<@NonNull Integer, @Nullable Location> {
	/**
	 * Constructor
	 */
	public LocationDao() {
		super();
	}
}
