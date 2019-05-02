package de.db.derpate.servlet.adminOnly;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.annotation.NonNull;

import de.db.derpate.CSRFForm;
import de.db.derpate.Usermode;
import de.db.derpate.model.Godfather;
import de.db.derpate.model.Trainee;
import de.db.derpate.persistence.TraineeDao;
import de.db.derpate.servlet.FilterServlet;
import de.db.derpate.servlet.filter.CSRFServletFilter;
import de.db.derpate.servlet.filter.LoginServletFilter;
import de.db.derpate.util.URIParameterEncryptionUtil;

/**
 * This servlet can be used to remove a {@link Trainee}s {@link Godfather}
 * (POST) or to delete the {@link Trainee}s account itself (DELETE).<br>
 * Allowed http methods: <code>POST</code>, <code>DELETE</code>
 *
 * @author MichelBlank
 *
 */
@WebServlet("/adminTraineeUpdate")
public class TraineeRemoveServlet extends FilterServlet {
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * the parameter used to send the encrypted trainee token
	 *
	 * @see URIParameterEncryptionUtil
	 */
	public static final String PARAMETER_TRAINEE_TOKEN = "trainee"; //$NON-NLS-1$
	/**
	 * HTTP status code, if the token doesn't exist
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

	/**
	 * Default constructor initializing the {@link FilterServlet}
	 */
	public TraineeRemoveServlet() {
		super(new LoginServletFilter(Usermode.ADMIN), new CSRFServletFilter(CSRFForm.ADMIN_UPDATE_TRAINEE));
	}

	@Override
	protected void onPost(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		TraineeDao traineeDao = new TraineeDao();
		Trainee trainee = findTrainee(req, traineeDao);
		if (trainee != null) {
			trainee.setGodfather(null);
			boolean success = traineeDao.update(trainee);

			resp.setStatus(success ? SC_SUCCESS : SC_ERROR);
			return;
		}
		resp.setStatus(SC_NOT_FOUND);
	}

	@Override
	protected void onDelete(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		TraineeDao traineeDao = new TraineeDao();
		Trainee trainee = findTrainee(req, traineeDao);
		if (trainee != null) {
			boolean success = traineeDao.remove(trainee);

			resp.setStatus(success ? SC_SUCCESS : SC_ERROR);
			return;
		}
		resp.setStatus(SC_NOT_FOUND);
	}

	private static Trainee findTrainee(HttpServletRequest req, @NonNull TraineeDao traineeDao) {
		Trainee trainee = null;

		String encryptedToken = req.getParameter(PARAMETER_TRAINEE_TOKEN);
		String token = URIParameterEncryptionUtil.decrypt(encryptedToken);

		if (token != null) {
			trainee = traineeDao.findByToken(token);
		}

		return trainee;
	}
}
