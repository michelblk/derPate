package de.db.derpate.servlet.traineeOnly;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jdt.annotation.NonNull;

import de.db.derpate.CSRFForm;
import de.db.derpate.Usermode;
import de.db.derpate.manager.LoginManager;
import de.db.derpate.model.Godfather;
import de.db.derpate.model.Trainee;
import de.db.derpate.persistence.GodfatherDao;
import de.db.derpate.persistence.TraineeDao;
import de.db.derpate.servlet.FilterServlet;
import de.db.derpate.servlet.filter.CSRFServletFilter;
import de.db.derpate.servlet.filter.LoginServletFilter;
import de.db.derpate.util.InputVerifyUtil;
import de.db.derpate.util.URIParameterEncryptionUtil;

/**
 * This servlet is used by trainees to select their godfather.
 *
 * @author MichelBlank
 *
 */
@WebServlet("/godfatherSelect")
public class GodfatherSelectServlet extends FilterServlet {
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Http parameter used to specify a godfather id (encrypted)
	 */
	public static final String PARAM_GODFAHTER_ID = "id"; //$NON-NLS-1$
	/**
	 * This status code is used, when the trainee successfully selected a godfather
	 */
	public static final int SC_SET_GODFATHER_SUCCESS = HttpServletResponse.SC_NO_CONTENT;
	/**
	 * This status code is used, when an error occurred, changing the trainees
	 * godfather
	 */
	public static final int SC_SET_GODFATHER_ERROR = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
	/**
	 * This status code is send, when an invalid request was send.
	 */
	public static final int SC_SET_GODFATHER_INVALID_REQUEST = HttpServletResponse.SC_BAD_REQUEST;

	/**
	 * Constructor initializing access filter
	 */
	public GodfatherSelectServlet() {
		super(new LoginServletFilter(Usermode.TRAINEE), new CSRFServletFilter(CSRFForm.TRAINEE_SELECT_GODFATHER));
	}

	@Override
	protected void onPost(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		// pick a godfather
		String encryptedId = req.getParameter(PARAM_GODFAHTER_ID);
		HttpSession session = req.getSession();
		if (encryptedId != null && session != null) {
			String decryptedId = URIParameterEncryptionUtil.decrypt(encryptedId);
			if (decryptedId != null && InputVerifyUtil.isInteger(decryptedId)) {
				// submitted id is a valid integer
				int id = Integer.parseInt(decryptedId);
				Trainee loggedInTrainee = LoginManager.getInstance().getUserBySession(session);
				Godfather wantedGodfather = GodfatherDao.getInstance().byId(id);

				if (loggedInTrainee != null && wantedGodfather != null
						&& wantedGodfather.getMaxTrainees() > wantedGodfather.getCurrentNumberTrainees()) {
					// Trainee hasn't selected godfather and godfather is available
					Trainee trainee = TraineeDao.getInstance().byId(loggedInTrainee.getId()); // get full object out of
																								// database (with
																								// password)
					if (trainee != null && trainee.getGodfather() == null) {
						trainee.setGodfather(wantedGodfather);
						boolean success = TraineeDao.getInstance().update(trainee); // database update
						if (!success) {
							// error writing to database
							resp.setStatus(SC_SET_GODFATHER_ERROR);
							return;
						}
						// successfully wrote to database
						LoginManager.getInstance().update(session, trainee); // update session
						resp.setStatus(SC_SET_GODFATHER_SUCCESS);
						return;
					}
				}
			}
		}
		resp.sendError(SC_SET_GODFATHER_INVALID_REQUEST);
	}
}
