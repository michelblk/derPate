package de.db.derPate.model;

import java.sql.Date;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * This dataclass is used to hold all informations used for displaying a list of
 * {@link Godfather}s to the trainee. The {@link Godfather} itself may change
 * it's values.
 *
 * @author MichelBlank
 * @see Godfather
 */
public class GodfatherInformation {
	@Nullable
	private String lastName;
	@Nullable
	private String firstName;
	@Nullable
	private Location location;
	@Nullable
	private String description;
	// @Nullable
	// TODO picture
	@Nullable
	private TeachingType teachingType;
	@Nullable
	private Job job;
	@Nullable
	private Date hiringDate;
	@Nullable
	private Date birthday;
	@Nullable
	private String pickText;

	/**
	 * Constructor
	 *
	 * @param lastName    Lastname
	 * @param firstName   Firstname
	 * @param location    Location
	 * @param description Description
	 * @param techingType Teching type
	 * @param job         Job
	 * @param hiringDate  Hiring date
	 * @param birthday    Birthday
	 * @param pickText    Text to display, after {@link Trainee} selected a
	 *                    {@link Godfather}
	 */
	public GodfatherInformation(@NonNull final String lastName, @NonNull final String firstName,
			@NonNull final Location location, @NonNull final String description,
			@NonNull final TeachingType techingType, @NonNull final Job job, @NonNull final Date hiringDate,
			@Nullable Date birthday, @Nullable String pickText) {
		this.setLastName(lastName);
		this.setFirstName(firstName);
		this.setLocation(location);
		this.setDescription(description);
		this.setTeachingType(techingType);
		this.setJob(job);
		this.setHiringDate(hiringDate);
		this.setBirthday(birthday);
		this.setPickText(pickText);
	}

	/**
	 * Returns lastname of Godfather
	 *
	 * @return the lastName
	 */
	@Nullable
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Sets the lastname of the Godfather
	 *
	 * @param lastName the lastName to set
	 */
	public void setLastName(@NonNull String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns firstname of Godfather
	 *
	 * @return the firstName
	 */
	@Nullable
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Sets firstname
	 *
	 * @param firstName the firstName to set
	 */
	public void setFirstName(@NonNull String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns location
	 *
	 * @return the location
	 */
	@Nullable
	public Location getLocation() {
		return this.location;
	}

	/**
	 * Sets location
	 *
	 * @param location the location to set
	 */
	public void setLocation(@NonNull Location location) {
		this.location = location;
	}

	/**
	 * Returns description
	 *
	 * @return the description
	 */
	@Nullable
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets description
	 *
	 * @param description the description to set
	 */
	public void setDescription(@NonNull String description) {
		this.description = description;
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

	/**
	 * Returns Job
	 *
	 * @return the job
	 */
	@Nullable
	public Job getJob() {
		return this.job;
	}

	/**
	 * Sets Job
	 *
	 * @param job the job to set
	 */
	public void setJob(@NonNull Job job) {
		this.job = job;
	}

	/**
	 * Returns hiring date
	 *
	 * @return the hiringDate
	 */
	@Nullable
	public Date getHiringDate() {
		return this.hiringDate;
	}

	/**
	 * Sets hiring date
	 *
	 * @param hiringDate the hiringDate to set
	 */
	public void setHiringDate(@NonNull Date hiringDate) {
		this.hiringDate = hiringDate;
	}

	/**
	 * Returns birthday
	 *
	 * @return the birthday
	 */
	@Nullable
	public Date getBirthday() {
		return this.birthday;
	}

	/**
	 * Sets birthday
	 *
	 * @param birthday the birthday to set
	 */
	public void setBirthday(@Nullable Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * Set text, that will be displayed, when the {@link Trainee} selected a
	 * {@link Godfather}
	 *
	 * @return the pickText
	 */
	@Nullable
	public String getPickText() {
		return this.pickText;
	}

	/**
	 * Returns text, that get's displayed, when the {@link Trainee} selected a
	 * {@link Godfather}
	 *
	 * @param pickText the pickText to set
	 */
	public void setPickText(@Nullable String pickText) {
		this.pickText = pickText;
	}
}
