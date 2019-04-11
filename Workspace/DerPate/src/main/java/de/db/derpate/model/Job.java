package de.db.derpate.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.google.gson.annotations.Expose;

/**
 * This dataclass contains informations regarding the different job types (for
 * example "Auszubildender")
 *
 * @author MichelBlank
 *
 */
@Entity
@Table(name = "Job")
@AttributeOverride(name = "id", column = @Column(name = "Id_Job"))
public class Job extends Id implements HasName {
	@NonNull
	@Column(name = "Job", nullable = false)
	@Expose
	private String job;
	@ManyToOne(optional = false)
	@JoinColumn(name = "Teaching_Type", nullable = false)
	@Expose
	private TeachingType teachingType;

	/**
	 * Default constructor used for hibernate
	 */
	Job() {
		super();
		this.job = ""; //$NON-NLS-1$
	}

	/**
	 * Constructor
	 *
	 * @param id           Id
	 * @param job          Job name
	 * @param teachingType Teaching Type
	 */
	public Job(int id, @NonNull String job, @NonNull TeachingType teachingType) {
		super(id);
		this.job = job;
		this.teachingType = teachingType;
	}

	/**
	 * Returns job name
	 *
	 * @return job name
	 */
	@NonNull
	@Override
	public String getName() {
		return this.job;
	}

	/**
	 * Sets job name
	 *
	 * @param job job name
	 */
	public void setJob(@NonNull String job) {
		this.job = job;
	}

	/**
	 * Returns TeachingType
	 *
	 * @return the techingType
	 */
	@Nullable
	public TeachingType getTeachingType() {
		return this.teachingType;
	}

	/**
	 * Sets TeachingType
	 *
	 * @param teachingType the techingType to set
	 */
	public void setTeachingType(@NonNull TeachingType teachingType) {
		this.teachingType = teachingType;
	}

	@Override
	public String toString() {
		return this.getName();
	}
}
