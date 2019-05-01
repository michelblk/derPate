/**
 *
 */
package de.db.derpate.servlet.godfatherOnly;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.http.entity.ContentType;
import org.eclipse.jdt.annotation.NonNull;

import de.db.derpate.CSRFForm;
import de.db.derpate.Usermode;
import de.db.derpate.manager.ImageManager;
import de.db.derpate.manager.LoginManager;
import de.db.derpate.model.LoginUser;
import de.db.derpate.servlet.FilterServlet;
import de.db.derpate.servlet.ServletParameter;
import de.db.derpate.servlet.filter.CSRFServletFilter;
import de.db.derpate.servlet.filter.LoginServletFilter;

/**
 * {@link HttpServlet} to upload a profile picture<br>
 * Allowed methods: POST
 *
 * @author MichelBlank
 *
 */
@WebServlet("/uploadGodfatherImage")
@MultipartConfig
public class GodfatherUpdateImageServlet extends FilterServlet {
	/**
	 * default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor initializing security filter
	 */
	public GodfatherUpdateImageServlet() {
		super(new LoginServletFilter(Usermode.GODFATHER), new CSRFServletFilter(CSRFForm.GODFATHER_UPDATE_SELF));
	}

	@Override
	protected void onPost(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		boolean success = false;
		LoginUser user = LoginManager.getInstance().getUserBySession(req.getSession());
		if (user == null) {
			return;
		}

		Part filePart;
		try {
			filePart = req.getPart(ServletParameter.GODFATHER_IMAGE.toString());

			int userid = user.getId();
			InputStream fileContent = filePart.getInputStream();
			BufferedImage image = ImageIO.read(fileContent);
			success = ImageManager.getInstance().writeGotfatherImage(userid, image);
		} catch (@SuppressWarnings("unused") ServletException | IOException | IllegalStateException e) {
			// nothing to do
		}

		resp.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST);
		resp.setContentType(ContentType.TEXT_PLAIN.getMimeType());
		resp.getWriter().print(success ? "OK" : "ERR"); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
