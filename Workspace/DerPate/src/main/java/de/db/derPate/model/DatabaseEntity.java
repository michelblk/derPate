package de.db.derPate.model;

import javax.persistence.MappedSuperclass;

/**
 * This abstract class is used by all models, that are persisted in the database
 * and therefore can be used with Dao objects of
 * {@link de.db.derPate.persistence}.
 *
 * @author MichelBlank
 *
 */
@MappedSuperclass
public abstract class DatabaseEntity {
	/* no attributes yet. */
}
