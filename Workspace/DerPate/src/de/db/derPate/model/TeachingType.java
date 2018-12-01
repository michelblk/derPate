package de.db.derPate.model;

import org.eclipse.jdt.annotation.NonNull;

/**
 * This dataclass contains field to hold information about the teaching types
 *
 * @author MichelBlank
 *
 */
public class TeachingType extends Id {
	@NonNull
	private String techingType = "";

	/**
	 * Constructor
	 *
	 * @param id           id
	 * @param teachingType name of teaching type
	 */
	public TeachingType(int id, @NonNull String teachingType) {
		super(id);
		this.setTechingType(this.techingType);
	}

	/**
	 * Returns {@link #techingType}
	 *
	 * @return name of teaching type
	 */
	@NonNull
	public String getTechingType() {
		return this.techingType;
	}

	/**
	 * Sets {@link #techingType}
	 *
	 * @param teachingType name of teaching type
	 */
	private void setTechingType(@NonNull String teachingType) {
		this.techingType = teachingType;
	}
}
