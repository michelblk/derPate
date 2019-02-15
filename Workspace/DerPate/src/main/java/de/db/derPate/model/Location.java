package de.db.derPate.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.eclipse.jdt.annotation.NonNull;

import com.google.gson.annotations.Expose;

/**
 * This dataclass contains informations regarding a location
 *
 * @author MichelBlank
 *
 */
@Entity
@Table(name = "Location")
@AttributeOverride(name = "id", column = @Column(name = "Id_Location"))
public class Location extends Id implements HasName {
	@NonNull
	@Column(name = "Location", nullable = false)
	@Expose
	private String location;

	/**
	 * Default constructor used for hibernate
	 */
	Location() {
		super();
		this.location = ""; //$NON-NLS-1$
	}

	/**
	 * Constructor
	 *
	 * @param id       id
	 * @param location location name (city)
	 */
	public Location(int id, @NonNull String location) {
		super(id);
		this.location = location;
	}

	/**
	 * Returns location
	 *
	 * @return {@link Location}
	 */
	@NonNull
	@Override
	public String getName() {
		return this.location;
	}
}