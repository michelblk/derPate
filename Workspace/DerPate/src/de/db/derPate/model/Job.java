package de.db.derPate.model;

import org.eclipse.jdt.annotation.NonNull;

/**
 * This dataclass contains informations regarding the different job types (for
 * example "Auszubildender")
 *
 * @author MichelBlank
 *
 */
public class Job {
	private int id;
	@NonNull
	private String job = "";

	/**
	 * Constructor
	 *
	 * @param id  Id
	 * @param job Job name
	 */
	public Job(int id, @NonNull String job) {
		this.setId(id);
		this.setJob(job);
	}

	/**
	 * Returns id
	 *
	 * @return id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Sets id
	 * 
	 * @param id id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns job name
	 * 
	 * @return job name
	 */
	@NonNull
	public String getJob() {
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
}
