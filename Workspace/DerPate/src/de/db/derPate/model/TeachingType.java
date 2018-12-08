package de.db.derPate.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.eclipse.jdt.annotation.NonNull;

/**
 * This dataclass contains field to hold information about the teaching types
 *
 * @author MichelBlank
 *
 */
@Entity
@Table(name = "Teaching_Type")
@AttributeOverride(name = "id", column = @Column(name = "Id_Teaching_Type"))
public class TeachingType extends Id {
	@NonNull
	@Column(name = "Teaching_Type")
	private String teachingType = "";

	/**
	 * Constructor used for database connection. Id will be set to null!
	 */
	TeachingType() {
		super(null);
	}

	/**
	 * Constructor
	 *
	 * @param id           id
	 * @param teachingType name of teaching type
	 */
	public TeachingType(int id, @NonNull String teachingType) {
		super(id);
		this.setTeachingType(this.teachingType);
	}

	/**
	 * Returns {@link #teachingType}
	 *
	 * @return name of teaching type
	 */
	@NonNull
	public String getTeachingType() {
		return this.teachingType;
	}

	/**
	 * Sets {@link #teachingType}
	 *
	 * @param teachingType name of teaching type
	 */
	private void setTeachingType(@NonNull String teachingType) {
		this.teachingType = teachingType;
	}
}
