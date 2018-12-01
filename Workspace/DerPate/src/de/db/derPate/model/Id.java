package de.db.derPate.model;

/**
 * Used for all {@link DatabaseEntity}s, that have a numeric id field
 *
 * @author MichelBlank
 *
 */
public abstract class Id extends DatabaseEntity {
	private int id;

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
