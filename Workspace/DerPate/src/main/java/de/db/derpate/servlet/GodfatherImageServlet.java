package de.db.derpate.servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.ContentType;
import org.eclipse.jdt.annotation.NonNull;

import de.db.derpate.manager.ImageManager;
import de.db.derpate.model.Godfather;
import de.db.derpate.servlet.filter.LoginServletFilter;
import de.db.derpate.servlet.filter.ParameterValidationFilter;
import de.db.derpate.util.URIParameterEncryptionUtil;

/**
 * This {@link HttpServlet} is available for Trainees only and is used to get
 * the image of a {@link Godfather} (set by {@link Godfather}).<br>
 * If no image is found (because the {@link Godfather} hasn't set one), error
 * {@link HttpServletResponse#SC_NOT_FOUND 404} is returned.
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

	/**
	 * Constructor initializing the access filter
	 */
	public GodfatherImageServlet() {
		super(new LoginServletFilter(), new ParameterValidationFilter(ServletParameter.GODFATHER_ID));
	}

	@Override
	protected void onGet(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		Integer decryptedUserid = URIParameterEncryptionUtil
				.decryptToInteger(req.getParameter(ServletParameter.GODFATHER_ID.toString()));
		if (decryptedUserid != null) {
			// try to find image on file system

			BufferedImage image = ImageManager.getInstance().readGodfatherImage(decryptedUserid);
			if (image != null) {
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				ImageIO.write(image, ImageManager.FORMAT_NAME, stream);
				stream.close();

				resp.setContentType(ContentType.IMAGE_PNG.getMimeType());
				resp.setContentLength(stream.size());
				OutputStream out = resp.getOutputStream();
				out.write(stream.toByteArray());
				out.close();
				return;
			}
		}
		resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
	}
}
