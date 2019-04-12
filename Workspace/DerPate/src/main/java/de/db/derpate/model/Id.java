package de.db.derpate.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;

import com.google.gson.annotations.Expose;

/**
 * Abstract class containing an id-integer.
 *
 * @author MichelBlank
 *
 */
@MappedSuperclass
public abstract class Id {
	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	@Expose
	private int id = -1;

	/**
	 * Default constructor used for hibernate
	 */
	Id() {

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
	 * Returns the id
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

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Id) {
			return ((Id) obj).hashCode() == this.hashCode();
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return this.id;
	}
}
