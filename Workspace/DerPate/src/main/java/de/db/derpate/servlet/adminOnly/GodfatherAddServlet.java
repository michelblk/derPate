package de.db.derpate.servlet.adminOnly;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.util.logging.Level;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.ContentType;
import org.eclipse.jdt.annotation.NonNull;

import com.google.gson.JsonObject;

import de.db.derpate.CSRFForm;
import de.db.derpate.Constants;
import de.db.derpate.Usermode;
import de.db.derpate.manager.LoggingManager;
import de.db.derpate.model.Admin;
import de.db.derpate.model.Godfather;
import de.db.derpate.model.Job;
import de.db.derpate.model.Location;
import de.db.derpate.persistence.GodfatherDao;
import de.db.derpate.persistence.JobDao;
import de.db.derpate.persistence.LocationDao;
import de.db.derpate.servlet.FilterServlet;
import de.db.derpate.servlet.ServletParameter;
import de.db.derpate.servlet.filter.CSRFServletFilter;
import de.db.derpate.servlet.filter.LoginServletFilter;
import de.db.derpate.servlet.filter.ParameterValidationFilter;
import de.db.derpate.util.HashUtil;
import de.db.derpate.util.URIParameterEncryptionUtil;

/**
 * This {@link HttpServlet} can be used by {@link Admin Admins} to add a
 * {@link Godfather} account.
 *
 * @author MichelBlank
 *
 */
@WebServlet("/adminGodfatherAdd")
public class GodfatherAddServlet extends FilterServlet {
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor initializing the {@link FilterServlet}
	 */
	public GodfatherAddServlet() {
		super(new LoginServletFilter(Usermode.ADMIN), new CSRFServletFilter(CSRFForm.ADMIN_ADD_GODFATHER),
				new ParameterValidationFilter(ServletParameter.GODFATHER_EMAIL,
						ServletParameter.GODFATHER_PASSWORD, ServletParameter.GODFATHER_LAST_NAME,
						ServletParameter.GODFATHER_FIRST_NAME, ServletParameter.GODFATHER_LOCATION_ID,
						ServletParameter.GODFATHER_JOB_ID, ServletParameter.GODFATHER_HIRING_DATE));
	}

	@Override
	protected void onPost(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		GodfatherDao godfatherDao = new GodfatherDao();

		@NonNull
		String email = getParameter(req, ServletParameter.GODFATHER_EMAIL);
		@NonNull
		String password = getParameter(req, ServletParameter.GODFATHER_PASSWORD);
		@NonNull
		String lastName = getParameter(req, ServletParameter.GODFATHER_LAST_NAME);
		@NonNull
		String firstName = getParameter(req, ServletParameter.GODFATHER_FIRST_NAME);
		@NonNull
		String locationId = getParameter(req, ServletParameter.GODFATHER_LOCATION_ID);
		@NonNull
		String jobId = getParameter(req, ServletParameter.GODFATHER_JOB_ID);
		@NonNull
		String hiringDate = getParameter(req, ServletParameter.GODFATHER_HIRING_DATE);

		if (godfatherDao.findByEmail(email) == null) {
			Godfather newGodfather = new Godfather();
			newGodfather.setEmail(email);

			HashUtil hashUtil = Constants.Login.hashUtil;
			String encryptedPassword = hashUtil.hash(password, Constants.Login.hashSaltLength,
					Constants.Login.hashPepper, Constants.Login.hashSeparator);
			newGodfather.setPassword(encryptedPassword);

			newGodfather.setLastName(lastName);
			newGodfather.setFirstName(firstName);
			Location location = new LocationDao().findById(URIParameterEncryptionUtil.decryptToInteger(locationId));
			if (location == null) {
				LoggingManager.log(Level.SEVERE, "InputValidationFilter failed! Location not found!"); //$NON-NLS-1$
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			newGodfather.setLocation(location);

			Job job = new JobDao().findById(URIParameterEncryptionUtil.decryptToInteger(jobId));
			if (job == null) {
				LoggingManager.log(Level.SEVERE, "InputValidationFilter failed! Job not found!"); //$NON-NLS-1$
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			newGodfather.setJob(job);
			try {
				newGodfather.setHiringDate(new Date(HTML_DATE_FORMAT.parse(hiringDate).getTime()));
			} catch (ParseException e) {
				LoggingManager.log(Level.SEVERE,
						"InputValidationFilter failed! HiringDate could not be set: " + e.getMessage()); //$NON-NLS-1$
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}

			boolean databaseSuccess = godfatherDao.persist(newGodfather);
			if (databaseSuccess) {
				resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
				resp.getWriter().print(printId(newGodfather.getId())); // $NON-NLS-1$
				return;
			}
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		resp.setStatus(HttpServletResponse.SC_CONFLICT);
	}

	@NonNull
	private static String getParameter(HttpServletRequest req, ServletParameter parameter) {
		String result = req.getParameter(parameter.toString());
		if (result == null) {
			LoggingManager.log(Level.SEVERE,
					"GodfatherAddServlet: InputValidationFilter failed! " + parameter.toString() + " not found!"); //$NON-NLS-1$ //$NON-NLS-2$
			throw new RuntimeException("GodfatherAddServlet: InputValidationFilter failed!"); //$NON-NLS-1$
		}
		return result;
	}

	private static String printId(int id) {
		String encrypedId = URIParameterEncryptionUtil.encrypt(id);

		JsonObject object = new JsonObject();
		object.addProperty("id", encrypedId); //$NON-NLS-1$
		return object.toString();
	}
}
