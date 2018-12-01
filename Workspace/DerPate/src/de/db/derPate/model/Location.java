package de.db.derPate.model;

import org.eclipse.jdt.annotation.NonNull;

/**
 * This dataclass contains informations regarding a location
 *
 * @author MichelBlank
 *
 */
public class Location extends Id {
	@NonNull
	private String location = "";

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