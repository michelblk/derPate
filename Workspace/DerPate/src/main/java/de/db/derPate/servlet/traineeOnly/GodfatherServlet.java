package de.db.derPate.servlet.traineeOnly;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.ContentType;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import de.db.derPate.Usermode;
import de.db.derPate.manager.LoggingManager;
import de.db.derPate.manager.LoginManager;
import de.db.derPate.model.Godfather;
import de.db.derPate.model.HasName;
import de.db.derPate.model.Job;
import de.db.derPate.model.Trainee;
import de.db.derPate.model.typeAdapter.DateTypeAdapter;
import de.db.derPate.persistence.GodfatherDao;
import de.db.derPate.servlet.FilterServlet;
import de.db.derPate.servlet.filter.LoginServletFilter;
import de.db.derPate.util.TimeUtil;
import de.db.derPate.util.URIParameterEncryptionUtil;

/**
 * This servlet is only available for Trainees and is used to filter available
 * godfathers or - if the trainee already selected a godfahter - get information
 * of their selected godfather.
 *
 * @author MichelBlank
 *
 */
@WebServlet("/godfather")
public class GodfatherServlet extends FilterServlet {
	/**
	 * Default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Http parameter used to specify a location id, to filter for
	 */
	public static final String FILTER_PARAM_LOCATION = "location"; //$NON-NLS-1$
	/**
	 * Http parameter used to specify a job id, to filter for
	 */
	public static final String FILTER_PARAM_JOB = "job"; //$NON-NLS-1$
	/**
	 * Http parameter used to specify a teaching type id, to filter for
	 */
	public static final String FILTER_PARAM_TEACHING_TYPE = "teachingType"; //$NON-NLS-1$
	/**
	 * Http parameter used to specify the year (e.g. 2), the Godfather is hired
	 */
	public static final String FILTER_PARAM_EDUCATIONAL_YEAR = "eduYear"; //$NON-NLS-1$

	/**
	 * Json element name for godfather ID (encrypted)
	 */
	public static final String JSON_OUTPUT_ID = "id"; //$NON-NLS-1$
	/**
	 * Json element name for the first name of the godfather
	 */
	public static final String JSON_OUTPUT_FIRST_NAME = "firstname"; //$NON-NLS-1$
	/**
	 * Json element name for the last name of the godfather
	 */
	public static final String JSON_OUTPUT_LAST_NAME = "lastname"; //$NON-NLS-1$
	/**
	 * Json element name for the location name
	 */
	public static final String JSON_OUTPUT_LOCATION_NAME = "location"; //$NON-NLS-1$
	/**
	 * Json element name for the job name
	 */
	public static final String JSON_OUTPUT_JOB_NAME = "job"; //$NON-NLS-1$
	/**
	 * Json element name for the name of the teaching type (e.g. "Ausbildung")
	 */
	public static final String JSON_OUTPUT_TEACHING_TYPE_NAME = "teachingtype"; //$NON-NLS-1$
	/**
	 * Json element name for the educational year (e.g. 1, 2, 3, ...)
	 */
	public static final String JSON_OUTPUT_EDUCATIONAL_YEAR = "educationalyear"; //$NON-NLS-1$
	/**
	 * Json element name for godfathers age
	 */
	public static final String JSON_OUTPUT_AGE = "age"; //$NON-NLS-1$
	/**
	 * Json element name for the description of the godfather
	 */
	public static final String JSON_OUTPUT_DESCRIPTION = "description"; //$NON-NLS-1$
	/**
	 * Json element name for the email of the godfather
	 */
	public static final String JSON_OUTPUT_EMAIL = "email"; //$NON-NLS-1$
	/**
	 * Json element name for the text to show when the godfather was picked
	 */
	public static final String JSON_OUTPUT_PICKTEXT = "picktext"; //$NON-NLS-1$

	/**
	 * The Gson object to transform objects to json
	 */
	protected static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
			.registerTypeAdapter(Date.class, new DateTypeAdapter()).create();

	/**
	 * Constructor
	 */
	public GodfatherServlet() {
		super(new LoginServletFilter(Usermode.TRAINEE));
	}

	@SuppressWarnings("null") // Suppress warning, that session or wantedEncryptedLocation,... might be null
	@Override
	protected void onGet(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());

		// get logged in trainee
		Trainee trainee = LoginManager.getInstance().getUserBySession(this.session);
		if (trainee == null) {
			LoggingManager.log(Level.WARNING,
					"Trainee could call GodfatherServlet without permission, as Filter failed! Request stopped."); //$NON-NLS-1$
			return;
		}

		if (trainee.getGodfather() != null) {
			// look for my godfather, as user has already selected a godfather permanently
			Godfather godfather = trainee.getGodfather();
			resp.getWriter().print(toJson(godfather, true).toString());
			return;
		}

		// no godfather selected -> proceed
		// list available godfathers
		String[] wantedEncryptedLocations = req.getParameterValues(FILTER_PARAM_LOCATION);
		String[] wantedEncryptedJobs = req.getParameterValues(FILTER_PARAM_JOB);
		String[] wantedEncryptedTeachingTypes = req.getParameterValues(FILTER_PARAM_TEACHING_TYPE);
		String[] wantedEncryptedEducationalYears = req.getParameterValues(FILTER_PARAM_EDUCATIONAL_YEAR);

		// decryption
		List<String> wantedDecrypedLocations = null;
		List<String> wantedDecrypedJobs = null;
		List<String> wantedDecrypedTeachingTypes = null;
		List<String> wantedDecrypedEducationalYears = null;

		// check if it is filtered by location
		if (wantedEncryptedLocations != null && wantedEncryptedLocations.length > 0) {
			wantedDecrypedLocations = URIParameterEncryptionUtil.decrypt(wantedEncryptedLocations);
		}

		// check if it is filtered by job
		if (wantedEncryptedJobs != null && wantedEncryptedJobs.length > 0) {
			wantedDecrypedJobs = URIParameterEncryptionUtil.decrypt(wantedEncryptedJobs);
		}

		// check if it is filtered by teaching type
		if (wantedEncryptedTeachingTypes != null && wantedEncryptedTeachingTypes.length > 0) {
			wantedDecrypedTeachingTypes = URIParameterEncryptionUtil.decrypt(wantedEncryptedTeachingTypes);
		}

		// check if it is filtered by educational year
		if (wantedEncryptedEducationalYears != null && wantedEncryptedEducationalYears.length > 0) {
			wantedDecrypedEducationalYears = URIParameterEncryptionUtil.decrypt(wantedEncryptedEducationalYears);
		}

		List<Godfather> all = GodfatherDao.getInstance().filterAvailable(wantedDecrypedLocations, wantedDecrypedJobs,
				wantedDecrypedTeachingTypes, wantedDecrypedEducationalYears);

		resp.getWriter().print(toJson(all, false).toString());

		return;
	}

	@Override
	protected void onPost(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		super.onPost(req, resp); // unsupported method
	}

	/**
	 * Reads the informations of a godfather and puts them into a JsonObject.<br>
	 * The encrypted id, the first name, location name, teaching type name, job
	 * name, educational year and description is added to the JsonObject. If
	 * selected is <code>true</code>, the last name, eMail and picktext will be
	 * added too.
	 *
	 * @param godfather the {@link Godfather} to add
	 * @param more      if the trainee has already commited to this godfather
	 * @return a {@link JsonObject} containing the informations
	 */
	@NonNull
	public static JsonObject toJson(@Nullable Godfather godfather, boolean more) {
		JsonObject object = new JsonObject();
		if (godfather == null) {
			return object;
		}

		object.addProperty(JSON_OUTPUT_ID, URIParameterEncryptionUtil.encrypt(godfather.getId()));
		object.addProperty(JSON_OUTPUT_FIRST_NAME, godfather.getFirstName());
		object.addProperty(JSON_OUTPUT_LOCATION_NAME, getNameOutOfGodfather(godfather.getLocation()));

		Job job = godfather.getJob();
		object.addProperty(JSON_OUTPUT_TEACHING_TYPE_NAME,
				getNameOutOfGodfather(job != null ? job.getTeachingType() : null));
		object.addProperty(JSON_OUTPUT_JOB_NAME, getNameOutOfGodfather(job));
		object.addProperty(JSON_OUTPUT_EDUCATIONAL_YEAR, godfather.getEducationalYear());
		object.addProperty(JSON_OUTPUT_AGE, TimeUtil.getYearDiff(godfather.getBirthday()));
		object.addProperty(JSON_OUTPUT_DESCRIPTION, godfather.getDescription());

		if (more) {
			// when user is selected, show more informations
			object.addProperty(JSON_OUTPUT_LAST_NAME, godfather.getLastName());
			object.addProperty(JSON_OUTPUT_EMAIL, godfather.getEmail());
			object.addProperty(JSON_OUTPUT_PICKTEXT, godfather.getPickText());
		}

		return object;
	}

	/**
	 * Uses the {@link #toJson(Godfather, boolean)} method to create a
	 * {@link JsonArray}, that may be used to create a json string of godfather
	 * informations
	 *
	 * @param list     a {@link List} (can be null) containing non null
	 *                 {@link Godfathers}
	 * @param selected if more information should be shown
	 * @return a {@link JsonArray} containg informations
	 */
	@NonNull
	private static JsonArray toJson(@Nullable List<@NonNull Godfather> list, boolean more) {
		JsonArray resultArray = new JsonArray();
		if (list == null) {
			return resultArray;
		}

		for (@NonNull
		Godfather godfather : list) {
			JsonObject object = toJson(godfather, more);
			resultArray.add(object);
		}

		return resultArray;
	}

	@Nullable
	private static String getNameOutOfGodfather(@Nullable HasName kv) {
		return kv != null ? kv.getName() : null;
	}

}
