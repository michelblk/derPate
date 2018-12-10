package de.db.derPate.model;

import java.sql.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
@Embeddable
@Access(AccessType.FIELD)
public class GodfatherInformation {
	@Nullable
	@Column(name = "Last_Name", nullable = false)
	private String lastName;
	@Nullable
	@Column(name = "First_Name", nullable = false)
	private String firstName;
	@Nullable
	@ManyToOne(optional = false)
	@JoinColumn(name = "Id_Location", nullable = false)
	private Location location;
	@Column(name = "Max_Trainees", nullable = false)
	private int maxTrainees;
	@Nullable
	@Column(name = "Description", nullable = false)
	private String description;
	@Nullable
	@Column(name = "Picture", nullable = true)
	private String picture;
	@Nullable
	@ManyToOne(optional = false)
	@JoinColumn(name = "Id_Teaching_Type", nullable = false)
	private TeachingType teachingType;
	@Nullable
	@ManyToOne(optional = false)
	@JoinColumn(name = "Id_Job", nullable = false)
	private Job job;
	@Nullable
	@Column(name = "Hiring_Date", nullable = false)
	private Date hiringDate;
	@Nullable
	@Column(name = "Birthday", nullable = true)
	private Date birthday;
	@Nullable
	@Column(name = "Pick_Text", nullable = true)
	private String pickText;

	/**
	 * Constructor used for database connection.
	 */
	GodfatherInformation() {

	}

	/**
	 * Constructor
	 *
	 * @param lastName     Lastname
	 * @param firstName    Firstname
	 * @param location     Location
	 * @param description  Description
	 * @param teachingType Teaching type
	 * @param job          Job
	 * @param hiringDate   Hiring date
	 * @param birthday     Birthday
	 * @param pickText     Text to display, after {@link Trainee} selected a
	 *                     {@link Godfather}
	 */
	public GodfatherInformation(@NonNull final String lastName, @NonNull final String firstName,
			@NonNull final Location location, int maxTrainees, @NonNull final String description,
			@Nullable String picture, @NonNull final TeachingType teachingType, @NonNull final Job job,
			@NonNull final Date hiringDate, @Nullable Date birthday, @Nullable String pickText) {
		this.setLastName(lastName);
		this.setFirstName(firstName);
		this.setLocation(location);
		this.setDescription(description);
		this.setPicture(picture);
		this.setTeachingType(teachingType);
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
	 * @return the max number of trainees, the godfather wants to accept.
	 */
	public int getMaxTrainees() {
		return this.maxTrainees;
	}

	/**
	 * @param maxTrainees the max number of trainees, the godfather wants to accept.
	 *                    charge for.
	 */
	public void setMaxTrainees(int maxTrainees) {
		this.maxTrainees = maxTrainees;
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
	 * Returns the path to the picture
	 *
	 * @return the picture path
	 */
	public String getPicture() {
		return this.picture;
	}

	/**
	 * Sets the path to the picture
	 *
	 * @param picture the picture path to set
	 */
	public void setPicture(String picture) {
		this.picture = picture;
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
