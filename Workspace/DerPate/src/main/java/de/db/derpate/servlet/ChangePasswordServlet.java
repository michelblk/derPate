package de.db.derpate.servlet;

import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.annotation.NonNull;

import de.db.derpate.CSRFForm;
import de.db.derpate.Constants;
import de.db.derpate.Usermode;
import de.db.derpate.manager.LoggingManager;
import de.db.derpate.manager.LoginManager;
import de.db.derpate.model.Admin;
import de.db.derpate.model.EmailPasswordLoginUser;
import de.db.derpate.model.Godfather;
import de.db.derpate.persistence.AdminDao;
import de.db.derpate.persistence.GodfatherDao;
import de.db.derpate.servlet.filter.CSRFServletFilter;
import de.db.derpate.servlet.filter.LoginServletFilter;
import de.db.derpate.util.HashUtil;

/**
 * This {@link HttpServlet} can be used by {@link Godfather Godfathers} and
 * {@link Admin Admins} to change their own password.<br>
 * Allowed methods: <code>POST</code>
 *
 * @author MichelBlank
 *
 */
public class ChangePasswordServlet extends FilterServlet {
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * POST Parameter for the current password
	 */
	public static final String PARAMETER_OLD_PASSWORD = "current-password"; //$NON-NLS-1$
	/**
	 * POST Parameter for the new password
	 */
	public static final String PARAMETER_NEW_PASSWORD = "new-password"; //$NON-NLS-1$
	/**
	 * POST Parameter for the repetition of the new password
	 */
	public static final String PARAMETER_NEW_PASSWORD_2 = "new-password2"; //$NON-NLS-1$
	/**
	 * Http status code returned, if password change was successful
	 */
	public static final int SC_SUCCESS = HttpServletResponse.SC_OK;
	/**
	 * Http status code returned, if an error occurred
	 */
	public static final int SC_ERROR = HttpServletResponse.SC_BAD_REQUEST;

	/**
	 * Constructor
	 */
	public ChangePasswordServlet() {
		super(new LoginServletFilter(Usermode.ADMIN, Usermode.GODFATHER),
				new CSRFServletFilter(CSRFForm.CHANGE_PASSWORD));
	}

	@Override
	protected void onPost(@NonNull HttpServletRequest req, @NonNull HttpServletResponse resp) throws IOException {
		EmailPasswordLoginUser user = LoginManager.getInstance().getUserBySession(req.getSession());
		Usermode usermode = LoginManager.getInstance().getUsermode(user);
		if (user == null || usermode == null) { // this should not happen, as a filter is active
			LoggingManager.log(Level.SEVERE, "SECURITY INCIDENT: The ChangePasswordServlet's LoginFilter failed!"); //$NON-NLS-1$
			return;
		}

		// initialize hash util
		HashUtil hashUtil = Constants.Login.hashUtil;
		byte[] pepper = Constants.Login.hashPepper;
		String seperator = Constants.Login.hashSeparator;

		// receive password
		String oldPassword = req.getParameter(PARAMETER_OLD_PASSWORD);
		String newPassword = req.getParameter(PARAMETER_NEW_PASSWORD);
		String newPassword2 = req.getParameter(PARAMETER_NEW_PASSWORD_2);

		boolean success = true;
		String resultText = "OK"; //$NON-NLS-1$

		// check preconditions
		if (!hashUtil.isEqual(oldPassword, user.getPassword(), pepper, seperator)) {
			success = false;
			resultText = "Das aktuelle Passwort ist falsch"; //$NON-NLS-1$
		} else if (newPassword == null || newPassword2 == null || !newPassword.equals(newPassword2)) {
			success = false;
			resultText = "Passwörter sind leer oder stimmen nicht überein"; //$NON-NLS-1$
		} else if (hashUtil.isEqual(newPassword, user.getPassword(), pepper, seperator)) {
			success = false;
			resultText = "Das Passwort hat sich nicht geändert"; //$NON-NLS-1$
		} else if (newPassword.length() < 8) { // FIXME externalize number
			success = false;
			resultText = "Passwort entspricht nicht den Passwortrichtlinien"; //$NON-NLS-1$
		}

		if (success) {
			// calculate hashed password
			String encryptedPassword = hashUtil.hash(newPassword, Constants.Login.hashSaltLength, pepper, seperator);
			user.setPassword(encryptedPassword);

			// persist new password
			if (usermode == Usermode.ADMIN) {
				AdminDao adminDao = new AdminDao();
				success = adminDao.update((Admin) user);
			} else if (usermode == Usermode.GODFATHER) {
				GodfatherDao godfatherDao = new GodfatherDao();
				success = godfatherDao.update((Godfather) user);
			}

			if (!success) {
				resultText = "Ein Fehler ist aufgetreten"; //$NON-NLS-1$
			}
		}

		// return status
		resp.setStatus(success ? SC_SUCCESS : SC_ERROR);
		resp.getWriter().print(resultText); // $NON-NLS-1$
	}
}
