package de.db.derPate.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;

import org.eclipse.jdt.annotation.Nullable;

/**
 * Used for all {@link DatabaseEntity}s, that have a numeric id field
 *
 * @author MichelBlank
 *
 */
@MappedSuperclass
public abstract class Id extends DatabaseEntity {
	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * Constructor used for database connection
	 *
	 * @param id id
	 */
	Id(@Nullable Integer id) {
		this.id = id;
	}

	/**
	 * Constructor
	 *
	 * @param id id
	 */
	public Id(int id) {
		this.setId(id);
	}

	/**
	 * Returns the database {@link #id}
	 *
	 * @return id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Sets the {@link #id}
	 *
	 * @param id the id to set
	 */
	private void setId(int id) {
		this.id = id;
	}
}
