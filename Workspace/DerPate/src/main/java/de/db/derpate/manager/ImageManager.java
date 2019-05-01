package de.db.derpate.manager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import de.db.derpate.Constants;
import de.db.derpate.servlet.GodfatherImageServlet;
import de.db.derpate.servlet.godfatherOnly.GodfatherUpdateImageServlet;

/**
 * The {@link ImageManager} is able to read and write godfather images from the
 * default data source.<br>
 * Pattern: SINGLETON
 *
 * @author MichelBlank
 * @see GodfatherImageServlet
 * @see GodfatherUpdateImageServlet
 */
public class ImageManager {
	private static ImageManager instance;

	/**
	 * The {@link ImageIO} format name
	 */
	public static final String FORMAT_NAME = "png";

	private ImageManager() {
		// not used
	}

	/**
	 * Returns the {@link ImageManager} instance
	 *
	 * @return the {@link ImageManager}
	 */
	@SuppressWarnings("null")
	@NonNull
	public static ImageManager getInstance() {
		if (instance == null) {
			instance = new ImageManager();
		}
		return instance;
	}

	/**
	 * Reads a godfather image
	 *
	 * @param userid the decrypted userid
	 * @return the {@link BufferedImage} or <code>null</code>, if user did not
	 *         upload an image
	 */
	@Nullable
	public BufferedImage readGodfatherImage(int userid) {
		BufferedImage img = null;
		try {
			File file = new File(Constants.DATA_PATH + "\\" + userid);
			if (file.exists()) {
				img = ImageIO.read(file);
			}
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	/**
	 * Writes a godfather image to the filesystem
	 *
	 * @param userid the decrypted userid
	 * @param image  the {@link BufferedImage}
	 * @return <code>true</code>, if image was successfully written;
	 *         <code>false</code>, otherwise
	 */
	public synchronized boolean writeGotfatherImage(int userid, BufferedImage image) {
		boolean success = false;
		try {
			File file = new File(Constants.DATA_PATH + "\\" + userid); //$NON-NLS-1$
			if (!file.exists()) {
				file.createNewFile();
			}
			success = ImageIO.write(image, FORMAT_NAME, file); // $NON-NLS-1$
		} catch (@SuppressWarnings("unused") IllegalArgumentException | IOException e) {
			// success should already be false
		}
		return success;
	}
}
