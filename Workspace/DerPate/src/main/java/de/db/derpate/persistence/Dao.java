package de.db.derpate.persistence;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Interface each data access object should implement, including methods to find
 * an entry by id, list all entities, persist/update/remove an entity.
 *
 * @author MichelBlank
 *
 * @param <K> Primary Key Class
 * @param <E> Entity Class
 */
public interface Dao<@NonNull K, @Nullable E> {
	/**
	 * Finds entity with the given id (primary key)
	 *
	 * @param id the primary identifier
	 * @return the entity or <code>null</code>, if entity was not found
	 */
	@Nullable
	E findById(@NonNull K id);

	/**
	 * Returns a list of all persisted entities
	 *
	 * @return a {@link List} of all entities
	 */
	@NonNull
	List<E> all();

	/**
	 * Saves a given entity to the database.<br>
	 * Make sure, that there is no entity with the given entity's id persisted.
	 *
	 * @param entity the entity to persist
	 */
	void persist(@NonNull E entity);

	/**
	 * Updates an already existing entity
	 *
	 * @param entity the entity to update
	 * @return <code>true</code>, if update was successfully performed;
	 *         <code>false</code>, if an error occurred while updating
	 */
	boolean update(@NonNull E entity);

	/**
	 * Removes a persisted entity
	 *
	 * @param entity the entity to remove
	 */
	void remove(@NonNull E entity);
}