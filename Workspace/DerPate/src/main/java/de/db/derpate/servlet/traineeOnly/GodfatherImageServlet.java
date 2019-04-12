package de.db.derpate.servlet.traineeOnly;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.ContentType;
import org.eclipse.jdt.annotation.NonNull;

import de.db.derpate.Usermode;
import de.db.derpate.model.Godfather;
import de.db.derpate.persistence.GodfatherDao;
import de.db.derpate.servlet.FilterServlet;
import de.db.derpate.servlet.filter.LoginServletFilter;
import de.db.derpate.util.InputVerifyUtil;
import de.db.derpate.util.URIParameterEncryptionUtil;

/**
 * This servlet is available for Trainees only and is used to get the image of a
 * Godfather (set by Godfather).<br>
 * If no image is found (becuase the Godfather hasn't set one), a default
 * picture is shown.
 *
 * @author MichelBlank
 *
 */
@WebServlet("/godfatherImage")
public class GodfatherImageServlet extends FilterServlet {
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;
	private final GodfatherDao godfatherDao = new GodfatherDao();

	/**
	 * The URI parameter name that should contain the
	 * {@link URIParameterEncryptionUtil encrypted} id of the Godfather
	 */
	public static final String PARAMETER_ID = "id"; //$NON-NLS-1$

	/**
	 * Constructor initializing the access filter
	 */
	public GodfatherImageServlet() {
		super(new LoginServletFilter(Usermode.TRAINEE));
	}

	@Override
	protected void onGet(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		String encryptedUserid = req.getParameter(PARAMETER_ID);
		if (encryptedUserid != null) {
			String decryptedUserid = URIParameterEncryptionUtil.decrypt(encryptedUserid);
			if (decryptedUserid != null && InputVerifyUtil.isInteger(decryptedUserid)) {
				int userid = Integer.parseInt(decryptedUserid);
				Godfather godfather = this.godfatherDao.findById(userid);
				if (godfather != null) {
					// valid user -> try to find image on file system

					// TODO use imageutil
					resp.setContentType(ContentType.IMAGE_PNG.getMimeType()); // TODO may need correction
					resp.setContentLength(0); // TODO fill with correct size

					return;
				}
			}
		}
		resp.sendError(HttpServletResponse.SC_NOT_FOUND);
	}
}
