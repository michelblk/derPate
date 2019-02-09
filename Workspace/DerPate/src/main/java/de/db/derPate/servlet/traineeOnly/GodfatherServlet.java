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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.db.derPate.Usermode;
import de.db.derPate.manager.LoggingManager;
import de.db.derPate.manager.LoginManager;
import de.db.derPate.model.Godfather;
import de.db.derPate.model.Trainee;
import de.db.derPate.model.typeAdapter.DateTypeAdapter;
import de.db.derPate.persistence.GodfatherDao;
import de.db.derPate.servlet.FilterServlet;
import de.db.derPate.servlet.filter.LoginServletFilter;
import de.db.derPate.util.InputVerifyUtil;
import de.db.derPate.util.URIParameterEncryptionUtil;

/**
 * @author MichelBlank
 *
 */
@WebServlet("/godfather")
public class GodfatherServlet extends FilterServlet {
	/**
	 * Http parameter used to specify a godfather id (encrypted)
	 */
	public static final String FILTER_PARAM_GODFAHTER_ID = "id"; //$NON-NLS-1$
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
	 * Default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Gson object to transform objects to json
	 */
	protected Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
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
		Trainee trainee = (Trainee) LoginManager.getInstance().getUserBySession(this.session);
		if (trainee == null) {
			LoggingManager.log(Level.WARNING,
					"Trainee could call GodfatherServlet without permission, as Filter failed! Request stopped."); //$NON-NLS-1$
			return;
		}

		if (trainee.getGodfather() != null) {
			// look for my godfather, as user has already selected a godfather permantently
			Godfather godfather = trainee.getGodfather();
			resp.getWriter().print(this.gson.toJson(godfather));
			return;
		}
		// no godfather selected -> proceed

		String byId = req.getParameter(FILTER_PARAM_GODFAHTER_ID); // specific godfather was requested
		if (this.session != null && byId != null && InputVerifyUtil.isNotBlank(byId)) { // check, if a specific
																						// godfather was requested
			// decrypt the godfahter id
			String godfatherId = URIParameterEncryptionUtil.decrypt(byId, this.session, trainee); // decrypt id
			if (godfatherId != null && InputVerifyUtil.isInteger(godfatherId)) { // check if id is valid
				int id = Integer.parseInt(godfatherId);

				Godfather godfather = GodfatherDao.getInstance().byId(id); // get godfather out of database
				resp.getWriter().print(this.gson.toJson(godfather)); // print result as JSON
				return;
			}
			resp.sendError(HttpServletResponse.SC_NOT_FOUND); // id was invalid -> 404 Not Found
		} else { // no id was given -> list available godfathers
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

			// FIXME security: remove email, picktext, etc;
			List<Godfather> all = GodfatherDao.getInstance().filterAvailable(wantedDecrypedLocations,
					wantedDecrypedJobs, wantedDecrypedTeachingTypes, wantedDecrypedEducationalYears);

			resp.getWriter().print(this.gson.toJson(all));
			return;
		}
	}

	@Override
	protected void onPost(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		super.onPost(req, resp); // method not supported
	}
}
