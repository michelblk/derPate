package de.db.derpate.servlet.adminOnly;

import java.io.IOException;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.ContentType;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import de.db.derpate.CSRFForm;
import de.db.derpate.Constants;
import de.db.derpate.Usermode;
import de.db.derpate.model.Admin;
import de.db.derpate.model.Godfather;
import de.db.derpate.model.Job;
import de.db.derpate.model.Location;
import de.db.derpate.persistence.GodfatherDao;
import de.db.derpate.persistence.JobDao;
import de.db.derpate.persistence.LocationDao;
import de.db.derpate.servlet.BaseServlet;
import de.db.derpate.servlet.FilterServlet;
import de.db.derpate.servlet.filter.CSRFServletFilter;
import de.db.derpate.servlet.filter.LoginServletFilter;
import de.db.derpate.util.DateUtil;
import de.db.derpate.util.HashUtil;
import de.db.derpate.util.InputVerifyUtil;
import de.db.derpate.util.NumberUtil;
import de.db.derpate.util.URIParameterEncryptionUtil;

/**
 * This Servlet is used by the {@link Admin} to update a {@link Godfather}s
 * personal data (except password) or to remove the {@link Godfather}<br>
 * Allowed http methods: <code>POST</code>, <code>DELETE</code>
 *
 * @author MichelBlank
 *
 */
@WebServlet("/adminGodfatherUpdate")
public class GodfatherUpdateServlet extends FilterServlet {
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * POST Parameter used for the encrypted identifier
	 *
	 * @see URIParameterEncryptionUtil
	 */
	public static final String PARAMETER_GODFATHER_ID = "godfatherid"; //$NON-NLS-1$
	/**
	 * POST Parameter used for the last name
	 */
	public static final String PARAMETER_GODFATHER_LAST_NAME = "lastname"; //$NON-NLS-1$
	/**
	 * POST Parameter used for the first name
	 */
	public static final String PARAMETER_GODFATHER_FIRST_NAME = "firstname"; //$NON-NLS-1$
	/**
	 * POST Parameter used for the job identifier
	 *
	 * @see URIParameterEncryptionUtil
	 */
	public static final String PARAMETER_GODFATHER_JOB = "job"; //$NON-NLS-1$
	/**
	 * POST Parameter used for the hiring date
	 *
	 * @see BaseServlet#HTML_DATE_FORMAT
	 */
	public static final String PARAMETER_GODFATHER_HIRING_DATE = "hiringdate"; //$NON-NLS-1$
	/**
	 * POST Parameter used for the date of birth
	 *
	 * @see BaseServlet#HTML_DATE_FORMAT
	 */
	public static final String PARAMETER_GODFATHER_BIRTHDAY = "birthday"; //$NON-NLS-1$
	/**
	 * POST Parameter used for the e-mail
	 */
	public static final String PARAMETER_GODFATHER_EMAIL = "email"; //$NON-NLS-1$
	/**
	 * POST Parameter used for the password. Should only be set, if the password
	 * should be overwritten
	 */
	public static final String PARAMETER_GODFATHER_PASSWORD = "password"; //$NON-NLS-1$
	/**
	 * POST Parameter used for the location id (encrypted)
	 */
	public static final String PARAMETER_GODFATHER_LOCATION = "location"; //$NON-NLS-1$
	/**
	 * POST Parameter used for the max number of trainees, the {@link Godfather} is
	 * willing to support
	 */
	public static final String PARAMETER_GODFATHER_MAXTRAINEES = "maxTrainees"; //$NON-NLS-1$
	/**
	 * POST Parameter used for the description, that helps the trainee to pick a
	 * {@link Godfather}
	 */
	public static final String PARAMETER_GODFATHER_DESCRIPTION = "description"; //$NON-NLS-1$
	/**
	 * POST Parameter used for the text, that the trainee can see, once he/she
	 * decided which {@link Godfather} he/she wants to pick
	 */
	public static final String PARAMETER_GODFATHER_PICKTEXT = "picktext"; //$NON-NLS-1$
	/**
	 * HTTP status code, if the id doesn't exist
	 */
	public static final int SC_NOT_FOUND = HttpServletResponse.SC_NOT_FOUND;
	/**
	 * HTTP status code, if action was performed successfully
	 */
	public static final int SC_SUCCESS = HttpServletResponse.SC_OK;
	/**
	 * HTTP status code, if action could not be performed due to a database error
	 */
	public static final int SC_ERROR = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

	private final GodfatherDao godfatherDao = new GodfatherDao();
	private final LocationDao locationDao = new LocationDao();
	private final JobDao jobDao = new JobDao();

	/**
	 * Default constructor initializing the {@link FilterServlet}
	 */
	public GodfatherUpdateServlet() {
		super(new LoginServletFilter(Usermode.ADMIN), new CSRFServletFilter(CSRFForm.ADMIN_UPDATE_GODFATHER));
	}

	@Override
	protected void onPost(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		Integer godfatherId = URIParameterEncryptionUtil.decryptToInteger(req.getParameter(PARAMETER_GODFATHER_ID));
		Godfather godfather = this.godfatherDao.findById(godfatherId);
		if (godfather != null) {
			JsonArray response = new JsonArray();

			// Build response: name of a valid parameter will be returned
			if (updateLastName(req.getParameter(PARAMETER_GODFATHER_LAST_NAME), godfather)) {
				response.add(PARAMETER_GODFATHER_LAST_NAME);
			}
			if (updateFirstName(req.getParameter(PARAMETER_GODFATHER_FIRST_NAME), godfather)) {
				response.add(PARAMETER_GODFATHER_FIRST_NAME);
			}
			if (updateJob(
					this.jobDao.findById(
							URIParameterEncryptionUtil.decryptToInteger(req.getParameter(PARAMETER_GODFATHER_JOB))),
					godfather)) {
				response.add(PARAMETER_GODFATHER_JOB);
			}
			if (updateHiringDate(
					DateUtil.parseDate(req.getParameter(PARAMETER_GODFATHER_HIRING_DATE), HTML_DATE_FORMAT),
					godfather)) {
				response.add(PARAMETER_GODFATHER_HIRING_DATE);
			}
			if (updateBirthday(DateUtil.parseDate(req.getParameter(PARAMETER_GODFATHER_BIRTHDAY), HTML_DATE_FORMAT),
					godfather)) {
				response.add(PARAMETER_GODFATHER_BIRTHDAY);
			}
			if (updateEmail(req.getParameter(PARAMETER_GODFATHER_EMAIL), godfather)) {
				response.add(PARAMETER_GODFATHER_EMAIL);
			}
			if (overwritePassword(req.getParameter(PARAMETER_GODFATHER_PASSWORD), godfather)) {
				response.add(PARAMETER_GODFATHER_PASSWORD);
			}
			if (updateLocation(this.locationDao.findById(
					URIParameterEncryptionUtil.decryptToInteger(req.getParameter(PARAMETER_GODFATHER_LOCATION))),
					godfather)) {
				response.add(PARAMETER_GODFATHER_LOCATION);
			}
			if (updateMaxTrainees(req.getParameter(PARAMETER_GODFATHER_MAXTRAINEES), godfather)) {
				response.add(PARAMETER_GODFATHER_MAXTRAINEES);
			}
			if (resetDescription(req.getParameter(PARAMETER_GODFATHER_DESCRIPTION), godfather)) {
				response.add(PARAMETER_GODFATHER_DESCRIPTION);
			}
			if (resetPickText(req.getParameter(PARAMETER_GODFATHER_PICKTEXT), godfather)) {
				response.add(PARAMETER_GODFATHER_PICKTEXT);
			}

			if (this.godfatherDao.update(godfather)) {
				resp.setStatus(SC_SUCCESS);
				resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
				resp.getWriter().print(new Gson().toJson(response));
				return;
			}
			// if a database error occurred
			resp.setStatus(SC_ERROR);
			return;
		}
		resp.sendError(SC_NOT_FOUND);
	}

	@Override
	protected void onDelete(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		Integer godfatherId = URIParameterEncryptionUtil.decryptToInteger(req.getParameter(PARAMETER_GODFATHER_ID));
		Godfather godfather = this.godfatherDao.findById(godfatherId);
		if (godfather != null) {
			boolean success = this.godfatherDao.remove(godfather);

			resp.setStatus(success ? SC_SUCCESS : SC_ERROR);
			return;
		}
		resp.sendError(SC_NOT_FOUND);
	}

	private static boolean updateLastName(@Nullable String lastName, @NonNull Godfather outGodfather) {
		boolean valid = false;
		if (lastName != null && InputVerifyUtil.hasAtLeastXCharacters(lastName, 2)) { // FIXME zahl auslagern
			outGodfather.setLastName(lastName);
			valid = true;
		}
		return valid;
	}

	private static boolean updateFirstName(@Nullable String firstName, @NonNull Godfather outGodfather) {
		boolean valid = false;
		if (firstName != null && InputVerifyUtil.hasAtLeastXCharacters(firstName, 2)) { // FIXME zahl auslagern
			outGodfather.setFirstName(firstName);
			valid = true;
		}
		return valid;
	}

	private static boolean updateJob(@Nullable Job job, @NonNull Godfather outGodfather) {
		boolean valid = false;
		if (job != null) {
			outGodfather.setJob(job);
			valid = true;
		}
		return valid;
	}

	private static boolean updateHiringDate(@Nullable Date hiringDate, @NonNull Godfather outGodfather) {
		boolean valid = false;
		Integer diff = DateUtil.getYearDiff(hiringDate);
		if (hiringDate != null && diff != null && diff <= 5 && diff >= 0) { // FIXME zahlen auslagern
			valid = true;
			outGodfather.setHiringDate(new java.sql.Date(hiringDate.getTime())); // TODO use java.time.LOCALDATE instead
																					// of sql.date
		}
		return valid;
	}

	private static boolean updateBirthday(@Nullable Date birthday, @NonNull Godfather outGodfather) {
		boolean valid = false;
		Integer diff = DateUtil.getYearDiff(birthday);
		if (birthday == null) { // caution!
			valid = true;
			outGodfather.setBirthday(null);
		} else if (diff != null && diff >= 10 && diff < 100) { // FIXME zahlen auslagern
			valid = true;
			outGodfather.setBirthday(new java.sql.Date(birthday.getTime()));
		}
		return valid;
	}

	private static boolean updateEmail(@Nullable String email, @NonNull Godfather outGodfather) {
		boolean valid = false;
		if (email != null) {
			@SuppressWarnings("null") // lowerEmail cannot be null, as email is not null
			@NonNull
			String lowerEmail = email.trim().toLowerCase();
			valid = InputVerifyUtil.isEmailAddress(lowerEmail);
			if (valid) {
				outGodfather.setEmail(lowerEmail);
			}
		}
		return valid;
	}

	private static boolean updateLocation(@Nullable Location location, @NonNull Godfather outGodfather) {
		boolean valid = false;
		if (location != null) {
			valid = true;
			outGodfather.setLocation(location);
		}
		return valid;
	}

	private static boolean updateMaxTrainees(String maxTraineesString, Godfather outGodfather) {
		Integer maxTrainees = NumberUtil.parseInteger(maxTraineesString);
		boolean valid = false;
		if (maxTrainees != null) {
			// max trainees has to be between the number of trainees he/she is currently
			// responsible for and the value set in constants
			if (maxTrainees >= outGodfather.getCurrentNumberTrainees()
					&& maxTrainees <= Constants.Godfather.MAX_TRAINEES) {
				valid = true;
				outGodfather.setMaxTrainees(maxTrainees);
			}
		}
		return valid;
	}

	private static boolean resetDescription(String description, Godfather outGodfather) {
		boolean valid = false;
		if (description != null) {
			outGodfather.setDescription(null); // reset description
			valid = true;
		}
		return valid;
	}

	private static boolean resetPickText(String picktext, Godfather outGodfather) {
		boolean valid = false;
		if (picktext != null) {
			outGodfather.setPickText(null); // reset pick text
			valid = true;
		}
		return valid;
	}

	private static boolean overwritePassword(String password, Godfather outGodfather) {
		boolean success = false;
		if (password != null && InputVerifyUtil.isNotBlank(password)) {
			HashUtil hashUtil = Constants.Login.hashUtil;
			String encryptedPassword = hashUtil.hash(password, Constants.Login.hashSaltLength,
					Constants.Login.hashPepper, Constants.Login.hashSeparator);

			outGodfather.setPassword(encryptedPassword);
			success = true;
		}
		return success;
	}
}
