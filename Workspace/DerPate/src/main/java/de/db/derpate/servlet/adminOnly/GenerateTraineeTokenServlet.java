package de.db.derpate.servlet.adminOnly;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.ContentType;
import org.eclipse.jdt.annotation.NonNull;

import de.db.derpate.CSRFForm;
import de.db.derpate.Constants;
import de.db.derpate.Usermode;
import de.db.derpate.model.Admin;
import de.db.derpate.model.Trainee;
import de.db.derpate.persistence.TraineeDao;
import de.db.derpate.servlet.FilterServlet;
import de.db.derpate.servlet.filter.CSRFServletFilter;
import de.db.derpate.servlet.filter.LoginServletFilter;
import de.db.derpate.util.RandomUtil;

/**
 * This {@link HttpServlet} may be used by a {@link Admin} to generate new
 * Tokens, that can be used by {@link Trainee}s to login<br>
 * Allowed http methods: <code>GET</code>
 *
 * @author MichelBlank
 *
 */
@WebServlet("/adminGenerateTraineeToken")
public class GenerateTraineeTokenServlet extends FilterServlet {
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	private TraineeDao traineeDao;

	/**
	 * Default constructor initializing the {@link FilterServlet}
	 */
	public GenerateTraineeTokenServlet() {
		super(new LoginServletFilter(Usermode.ADMIN), new CSRFServletFilter(CSRFForm.ADMIN_ADD_TRAINEE));
		this.traineeDao = new TraineeDao();
	}

	@Override
	protected void onGet(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		@NonNull
		String token;

		do {
			token = RandomUtil.generateRandomAlphabetic(Constants.Trainee.TRAINEE_TOKEN_LENGTH);
		} while (this.traineeDao.findByToken(token) != null);

		Trainee newTrainee = new Trainee(token);
		this.traineeDao.persist(newTrainee);

		resp.setContentType(ContentType.TEXT_PLAIN.getMimeType());
		resp.getWriter().print(token);
	}
}
