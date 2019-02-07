package de.db.derPate.servlet.traineeOnly;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.ContentType;
import org.eclipse.jdt.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.db.derPate.Usermode;
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

	@Override
	protected void onGet(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());

		Trainee trainee = (Trainee) LoginManager.getInstance().getUserBySession(this.session);
		if (trainee == null) {
			return;
		}

		String byId = req.getParameter(FILTER_PARAM_GODFAHTER_ID);
		if (trainee.getGodfather() == null) { // only show other godfathers, if none was selected, yet
			if (this.session != null && byId != null && InputVerifyUtil.isNotBlank(byId)) {
				@SuppressWarnings("null") // suspress warning, that session might be null
				// decrypt the godfahter id
				String godfatherId = URIParameterEncryptionUtil.decrypt(byId, this.session, trainee);
				if (godfatherId != null && InputVerifyUtil.isInteger(godfatherId)) {
					int id = Integer.parseInt(godfatherId);

					Godfather godfather = GodfatherDao.getInstance().byId(id);
					resp.getWriter().print(this.gson.toJson(godfather));
					return;
				}
				resp.sendError(HttpServletResponse.SC_NOT_FOUND); // id was invalid -> 404 Not Found
			} else { // no id was given -> list godfathers
				String[] wantedEncryptedLocations = req.getParameterValues(FILTER_PARAM_LOCATION);
				String[] wantedEncryptedJobs = req.getParameterValues(FILTER_PARAM_JOB);
				String[] wantedEncryptedTeachingTypes = req.getParameterValues(FILTER_PARAM_TEACHING_TYPE);
				String[] wantedEncryptedEducationalYears = req.getParameterValues(FILTER_PARAM_EDUCATIONAL_YEAR);

				// decryption
				List<String> wantedDecrypedLocations = null;
				List<String> wantedDecrypedJobs = null;
				List<String> wantedDecrypedTeachingTypes = null;
				List<String> wantedDecrypedEducationalYears = null;

				if (wantedEncryptedLocations != null && wantedEncryptedLocations.length > 0) {
					wantedDecrypedLocations = URIParameterEncryptionUtil.decrypt(wantedEncryptedLocations);
				}

				if (wantedEncryptedJobs != null && wantedEncryptedJobs.length > 0) {
					wantedDecrypedJobs = URIParameterEncryptionUtil.decrypt(wantedEncryptedJobs);
				}

				if (wantedEncryptedTeachingTypes != null && wantedEncryptedTeachingTypes.length > 0) {
					wantedDecrypedTeachingTypes = URIParameterEncryptionUtil.decrypt(wantedEncryptedTeachingTypes);
				}

				if (wantedEncryptedEducationalYears != null && wantedEncryptedEducationalYears.length > 0) {
					wantedDecrypedEducationalYears = URIParameterEncryptionUtil
							.decrypt(wantedEncryptedEducationalYears);
				}

				// TODO security: remove email, picktext, etc;
				List<Godfather> all = GodfatherDao.filterAvailable(wantedDecrypedLocations, wantedDecrypedJobs,
						wantedDecrypedTeachingTypes, wantedDecrypedEducationalYears);
				resp.getWriter().print(this.gson.toJson(all));
				return;
			}
		} else {
			// look for my godfather, as user has already selected a
			// godfather permantently
			Godfather godfather = trainee.getGodfather();
			resp.getWriter().print(this.gson.toJson(godfather));
			return;
		}
	}

	@Override
	protected void onPost(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		super.onPost(req, resp);
	}
}
