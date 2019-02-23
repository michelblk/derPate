package de.db.derPate.model;

import java.sql.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.NaturalId;

import com.google.gson.annotations.Expose;

import de.db.derPate.manager.LoginManager;

/**
 * This dataclass is used for the godfather login and profile edit and for
 * displaying all godfathers, when logged in as a trainee.
 *
 * @author MichelBlank
 */
@Entity
@Table(name = "Godfather")
@AttributeOverride(name = "id", column = @Column(name = "Id_Godfather"))
@AttributeOverride(name = "email", column = @Column(name = "Email"))
@AttributeOverride(name = "password", column = @Column(name = "Password"))
public class Godfather extends EmailPasswordLoginUser {
	@Nullable
	@Column(name = "Last_Name", nullable = false)
	@Expose
	private String lastName;
	@Nullable
	@Column(name = "First_Name", nullable = false)
	@Expose
	private String firstName;
	@Nullable
	@ManyToOne(optional = false)
	@JoinColumn(name = "Id_Location", nullable = false)
	@Expose
	private Location location;
	@Column(name = "Max_Trainees", nullable = false)
	@Expose(deserialize = false, serialize = false)
	private int maxTrainees;
	@Formula(value = "(SELECT COUNT(*) FROM Trainee t WHERE t.Id_Godfather = Id_Godfather)")
	@Expose(deserialize = false, serialize = false)
	private int currentTrainees;
	@Transient
	@Expose
	private boolean hasFreeTraineeSlots;
	@Nullable
	@Column(name = "Description", nullable = true)
	@Expose
	private String description;
	@Nullable
	@ManyToOne(optional = false)
	@JoinColumn(name = "Id_Job", nullable = false)
	@Expose
	private Job job;
	@Nullable
	@Column(name = "Hiring_Date", nullable = false)
	@Expose(deserialize = false, serialize = false)
	private Date hiringDate;
	@Formula(value = "YEAR_DIFF(Hiring_Date) + 1") // TODO might cause problems, when year changes
													// on Hiring_Date
	@Expose
	private int educationalYear;
	@Nullable
	@Column(name = "Birthday", nullable = true)
	@Expose(deserialize = false, serialize = false)
	private Date birthday;
	@Nullable
	@Column(name = "Pick_Text", nullable = true)
	@Expose
	private String pickText;

	/**
	 * Default constructor used for hibernate
	 */
	Godfather() {
		super();
	}

	/**
	 * Constructor used by godfather login
	 *
	 * @param id       Database id of {@link Godfather}
	 * @param email    Email
	 * @param password Password
	 *
	 * @see LoginManager
	 */
	public Godfather(int id, @NonNull String email, @NonNull String password) {
		super(id, email, password);
	}

	/**
	 * Constructor used to store the data for the list of {@link Godfather}s
	 *
	 * @param id          Id from database
	 * @param email       Email address
	 * @param lastName    Lastname
	 * @param firstName   Firstname
	 * @param location    Location
	 * @param maxTrainees Maximum number of accepted trainees
	 * @param description Description
	 * @param job         Job
	 * @param hiringDate  Hiring date
	 * @param birthday    Birthday
	 * @param pickText    Text to display, after {@link Trainee} selected a
	 *                    {@link Godfather}
	 */
	public Godfather(int id, @NonNull String email, @NonNull final String lastName, @NonNull final String firstName,
			@NonNull final Location location, int maxTrainees, @Nullable final String description,
			@NonNull final Job job, @NonNull final Date hiringDate, @Nullable Date birthday,
			@Nullable String pickText) {
		super(id, email);
		this.setLastName(lastName);
		this.setFirstName(firstName);
		this.setLocation(location);
		this.setDescription(description);
		this.setJob(job);
		this.setHiringDate(hiringDate);
		this.setBirthday(birthday);
		this.setPickText(pickText);

		this.hasFreeTraineeSlots = this.getMaxTrainees() > this.getCurrentNumberTrainees();
	}

	/**
	 * @see de.db.derPate.model.EmailPasswordLoginUser#getEmail()
	 */
	@Override
	@NaturalId(mutable = true)
	public @NonNull String getEmail() {
		return super.getEmail();
	}

	/**
	 * @see de.db.derPate.model.EmailPasswordLoginUser#getPassword()
	 */
	@Override
	public @Nullable String getPassword() {
		return super.getPassword();
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
	public void setDescription(@Nullable String description) {
		this.description = description;
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
	 * Returns how many trainees selected this godfather
	 *
	 * @return the currentTrainees
	 */
	public int getCurrentNumberTrainees() {
		return this.currentTrainees;
	}

	/**
	 * @return <code>true</code>, if the godfather can be in charge of at least one
	 *         more trainee;<code>false</code>, if all slots are assigned to a
	 *         trainee
	 */
	public boolean hasFreeTraineeSlots() {
		return this.hasFreeTraineeSlots;
	}

	/**
	 * Returns in which educational year the godfather is
	 *
	 * @return the educationalYear
	 */
	public int getEducationalYear() {
		return this.educationalYear;
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
