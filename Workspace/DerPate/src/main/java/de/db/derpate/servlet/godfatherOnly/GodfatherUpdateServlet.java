package de.db.derpate.servlet.godfatherOnly;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.entity.ContentType;
import org.eclipse.jdt.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import de.db.derpate.CSRFForm;
import de.db.derpate.Constants;
import de.db.derpate.Usermode;
import de.db.derpate.manager.LoginManager;
import de.db.derpate.model.Godfather;
import de.db.derpate.model.Location;
import de.db.derpate.persistence.GodfatherDao;
import de.db.derpate.persistence.LocationDao;
import de.db.derpate.servlet.FilterServlet;
import de.db.derpate.servlet.filter.CSRFServletFilter;
import de.db.derpate.servlet.filter.LoginServletFilter;
import de.db.derpate.util.CSRFPreventionUtil;
import de.db.derpate.util.InputVerifyUtil;
import de.db.derpate.util.NumberUtil;
import de.db.derpate.util.URIParameterEncryptionUtil;

/**
 * Servlet that enables a godfather him/herselfs email-address, location,
 * maxTrainees, description and picktext<br>
 * This Servlet is protected by a {@link CSRFPreventionUtil CSRFToken}
 *
 * @author MichelBlank
 *
 */
@WebServlet("/godfatherUpdate")
public class GodfatherUpdateServlet extends FilterServlet {
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;
	private final GodfatherDao godfatherDao = new GodfatherDao();
	private final LocationDao locationDao = new LocationDao();

	/**
	 * POST Parameter used for the e-mail
	 */
	public static final String PARAMETER_EMAIL = "email"; //$NON-NLS-1$
	/**
	 * POST Parameter used for the location id (encrypted)
	 */
	public static final String PARAMETER_LOCATION = "location"; //$NON-NLS-1$
	/**
	 * POST Parameter used for the max number of trainees, the godfather is willing
	 * to support
	 */
	public static final String PARAMETER_MAXTRAINEES = "maxTrainees"; //$NON-NLS-1$
	/**
	 * POST Parameter used for the description, that helps the trainee to pick a
	 * godfather
	 */
	public static final String PARAMETER_DESCRIPTION = "description"; //$NON-NLS-1$
	/**
	 * POST Parameter used for the text, that the trainee can see, once he/she
	 * decided which godfather he/she wants to pick
	 */
	public static final String PARAMETER_PICKTEXT = "picktext"; //$NON-NLS-1$
	/**
	 * Http Status Code when database success
	 */
	public static final int SC_SUCCESS = HttpServletResponse.SC_OK;
	/**
	 * Http Status Code when database error
	 */
	public static final int SC_ERROR = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
	/**
	 * JSON element name for the current database value
	 */
	public static final String JSON_OUTPUT_VALUE = "value"; //$NON-NLS-1$
	/**
	 * JSON element name for the validity of the input the user provided
	 */
	public static final String JSON_OUTPUT_VALID = "valid"; //$NON-NLS-1$

	/**
	 * Constructor initializing the {@link LoginServletFilter} (godfather only) and
	 * {@link CSRFServletFilter}
	 */
	public GodfatherUpdateServlet() {
		super(new LoginServletFilter(Usermode.GODFATHER), new CSRFServletFilter(CSRFForm.GODFATHER_UPDATE_SELF));
	}

	/**
	 * Handles POST requests and responds with a JSON String of the input field
	 * names that were changed
	 */
	@Override
	protected void onPost(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		// retrieve and parse data
		String email = req.getParameter(PARAMETER_EMAIL);
		String locationId = req.getParameter(PARAMETER_LOCATION);
		String maxTrainees = req.getParameter(PARAMETER_MAXTRAINEES);
		String description = req.getParameter(PARAMETER_DESCRIPTION);
		String pickText = req.getParameter(PARAMETER_PICKTEXT);

		HashMap<String, SimpleEntry<String, Boolean>> jsonOutput = new HashMap<>(); // key: input name, value: new
																					// value, boolean (success or error)

		HttpSession session = req.getSession();
		Godfather loggedInUser = LoginManager.getInstance().getUserBySession(session);
		if (loggedInUser == null) {
			resp.sendError(SC_ERROR);
			return;
		}

		// get godfather out of the database, as the user could be logged in twice and
		// we don't want to revert other changes, that were made
		Godfather godfatherToUpdate = this.godfatherDao.findById(loggedInUser.getId());
		if (godfatherToUpdate == null) {
			resp.sendError(SC_ERROR);
			return;
		}

		// check fields, update godfather object and push informations to output
		checkEmail(email, godfatherToUpdate, jsonOutput);
		this.checkLocation(locationId, godfatherToUpdate, jsonOutput);
		checkMaxTrainees(maxTrainees, godfatherToUpdate, jsonOutput);
		checkDescription(description, godfatherToUpdate, jsonOutput);
		checkPickText(pickText, godfatherToUpdate, jsonOutput);

		// Update database
		boolean dbUpdateSuccess = this.godfatherDao.update(godfatherToUpdate); // update database

		if (dbUpdateSuccess) {
			LoginManager.getInstance().update(session, godfatherToUpdate); // update session

			resp.setStatus(SC_SUCCESS);
			resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
			resp.getWriter().print(toJson(jsonOutput));
		} else {
			resp.sendError(SC_ERROR);
		}

		return;
	}

	private static String toJson(@NonNull HashMap<String, SimpleEntry<String, Boolean>> map) {
		JsonObject obj = new JsonObject();

		for (Entry<String, SimpleEntry<String, Boolean>> entry : map.entrySet()) {
			JsonObject value = new JsonObject();
			value.addProperty(JSON_OUTPUT_VALUE, entry.getValue().getKey());
			value.addProperty(JSON_OUTPUT_VALID, entry.getValue().getValue());

			obj.add(entry.getKey(), value);
		}

		return new Gson().toJson(obj);
	}

	private static void checkEmail(String email, Godfather outGodfather,
			HashMap<String, SimpleEntry<String, Boolean>> outJsonMap) {
		if (email != null) {
			@SuppressWarnings("null") // lowerEmail cannot be null, as email is not null
			@NonNull
			String lowerEmail = email.trim().toLowerCase();
			String oldMail = outGodfather.getEmail();

			boolean valid = InputVerifyUtil.isEmailAddress(lowerEmail);
			if (valid) {
				outGodfather.setEmail(lowerEmail);
			}

			if (!valid || !lowerEmail.equalsIgnoreCase(oldMail)) {
				// if not valid or isn't same as before, send feedback
				outJsonMap.put(PARAMETER_EMAIL, new SimpleEntry<>(lowerEmail, valid));
			}
		}
	}

	private void checkLocation(String encryptedLocation, Godfather outGodfather,
			HashMap<String, SimpleEntry<String, Boolean>> outJsonMap) {
		if (encryptedLocation != null) {
			Integer locationId = URIParameterEncryptionUtil.decryptToInteger(encryptedLocation);
			Location location = null;
			Location oldLocation = null;
			if (locationId != null) {
				location = this.locationDao.findById(locationId);
				oldLocation = outGodfather.getLocation();

				if (location != null) {
					outGodfather.setLocation(location);
				}
			}

			if (locationId == null || location == null || !location.equals(oldLocation)) {
				outJsonMap.put(PARAMETER_LOCATION, new SimpleEntry<>(null, location != null));
			}
		}
	}

	private static void checkMaxTrainees(String maxTraineesString, Godfather outGodfather,
			HashMap<String, SimpleEntry<String, Boolean>> outJsonMap) {
		Integer maxTrainees = NumberUtil.parseInteger(maxTraineesString);
		int oldMaxTrainees = outGodfather.getMaxTrainees();
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
		if (!valid || (maxTrainees != null && !maxTrainees.equals(oldMaxTrainees))) {
			outJsonMap.put(PARAMETER_MAXTRAINEES,
					new SimpleEntry<>(maxTrainees == null ? null : Integer.toString(maxTrainees), valid));
		}
	}

	private static void checkDescription(String description, Godfather outGodfather,
			HashMap<String, SimpleEntry<String, Boolean>> outJsonMap) {
		if (description != null) { // is allowed to be empty
			String oldDescription = outGodfather.getDescription();

			description = description.trim();
			outGodfather.setDescription(description);

			if (!description.equals(oldDescription)) {
				outJsonMap.put(PARAMETER_DESCRIPTION, new SimpleEntry<>(description, true));
			}
		}
	}

	private static void checkPickText(String pickText, Godfather outGodfather,
			HashMap<String, SimpleEntry<String, Boolean>> outJsonMap) {
		if (pickText != null) { // is allowed to be empty
			String oldPickText = outGodfather.getPickText();

			pickText = pickText.trim();
			outGodfather.setPickText(pickText);

			if (!pickText.equals(oldPickText)) {
				outJsonMap.put(PARAMETER_PICKTEXT, new SimpleEntry<>(pickText, true));
			}
		}
	}
}
