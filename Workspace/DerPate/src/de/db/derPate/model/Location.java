package de.db.derPate.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.eclipse.jdt.annotation.NonNull;

/**
 * This dataclass contains informations regarding a location
 *
 * @author MichelBlank
 *
 */
@Entity
@Table(name = "Location")
@AttributeOverride(name = "id", column = @Column(name = "Id_Location"))
public class Location extends Id {
	@NonNull
	@Column(name = "Location")
	private String location = "";

	/**
	 * Constructor used for database connection. Id will be set to null!
	 */
	Location() {
		super(null);
	}

	/**
	 * Constructor
	 *
	 * @param id       id
	 * @param location location name (city)
	 */
	public Location(int id, @NonNull String location) {
		super(id);
		this.setLocation(location);
	}

	/**
	 * Returns location
	 *
	 * @return {@link #location}
	 */
	@NonNull
	public String getLocation() {
		return this.location;
	}

	/**
	 * Sets {@link #location}
	 *
	 * @param location location (city)
	 */
	private void setLocation(@NonNull String location) {
		this.location = location;
	}
}