package de.db.derpate.manager;

import java.util.Arrays;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import de.db.derpate.Constants;
import de.db.derpate.Usermode;
import de.db.derpate.model.Admin;
import de.db.derpate.model.Godfather;
import de.db.derpate.model.LoginUser;
import de.db.derpate.model.Trainee;

/**
 * This class is used to read and write login data (see {@link LoginUser}) to
 * the client's {@link HttpSession}.<br>
 * Pattern: Singleton
 *
 * @author MichelBlank
 * @see LoginUser
 */
public class LoginManager {
	/**
	 * Stores static instance
	 */
	@NonNull
	private static LoginManager instance;
	/**
	 * Key used to store the user in the session
	 */
	@NonNull
	private final String userKey;

	/**
	 * Static constructor
	 */
	static {
		instance = new LoginManager(); // create instance
	}

	/**
	 * Default constructor used for initializing attributes.
	 */
	private LoginManager() {
		this.userKey = "user"; //$NON-NLS-1$
	}

	/**
	 * Returns instance of {@link LoginManager}
	 *
	 * @return {@link LoginManager}
	 */
	@NonNull
	public static LoginManager getInstance() {
		return instance;
	}

	/**
	 * Returns {@link LoginUser} that is connected with this session. May return
	 * <b>null</b>, if user was not logged in.
	 *
	 * @param session Session of which the {@link LoginUser} should be read from
	 * @return {@link LoginUser} or <code>null</code>, if given {@link HttpSession}
	 *         was null or user was not properly logged in
	 */
	@SuppressWarnings({ "null", "unchecked" })
	@Nullable
	public <T extends LoginUser> T getUserBySession(final @Nullable HttpSession session) {
		T user = null;
		if (session != null) {
			try {
				user = (T) session.getAttribute(this.userKey);
			} catch (IllegalStateException | ClassCastException e) {
				LoggingManager.log(Level.INFO, "Could not get user due to an session error: " + e.getMessage()); //$NON-NLS-1$
			}
		}
		return user;
	}

	/**
	 * Connects {@link HttpSession} with {@link LoginUser}<br>
	 * Caution: This will call the {@link LoginUser#removeSecret()}-method.
	 *
	 * @param request {@link HttpServletRequest}
	 * @param user    {@link LoginUser}
	 * @return <code>true</code>, if successful; <code>false</code> if an error
	 *         occurred or given {@link HttpServletRequest} was null
	 */
	public boolean login(@Nullable HttpServletRequest request, @NonNull LoginUser user) {
		boolean success = false;

		if (request != null) {
			try {
				HttpSession session = newSession(request); // creates new session to prevent session hijacking
				if (session != null) {
					user.removeSecret(); // remove password from user to prevent unwanted use
					session.setAttribute(this.userKey, user);
					session.setMaxInactiveInterval(Constants.Login.MAX_INACTIVE_SECONDS);

					success = true;
				}
			} catch (IllegalStateException e) {
				LoggingManager.log(Level.INFO, "Could not login user due to an session error: " + e.getMessage()); //$NON-NLS-1$
			}
		}
		return success;
	}

	/**
	 * Returns current login status
	 *
	 * @param session {@link HttpSession}
	 * @return <code>true</code>, if user is logged in; <code>false</code>, if user
	 *         is not or given {@link HttpSession} was null
	 */
	public boolean isLoggedIn(@Nullable HttpSession session) {
		boolean success = false;
		if (session != null) {
			try {
				Object potentialUser = session.getAttribute(this.userKey);
				success = (potentialUser != null && potentialUser instanceof LoginUser); // user is logged in, when
																							// session contains an user
																							// object
			} catch (IllegalStateException e) {
				LoggingManager.log(Level.INFO, "Could not read login status out of session: " + e.getMessage()); //$NON-NLS-1$
			}
		}
		return success;
	}

	/**
	 * Checks if the user, that is logged in with the given HttpSession, is a user
	 * of the given Usermode.
	 *
	 * @param session  the {@link HttpSession}
	 * @param usermode the allowed {@link Usermode}s
	 * @return <code>true</code>, if the found {@link LoginUser} is a user of the
	 *         given {@link Usermode}; <code>false</code>, if not or no
	 *         {@link Usermode} was set.
	 */
	public boolean isUserOfSessionInUsermode(@Nullable HttpSession session, @Nullable Usermode... usermode) {
		boolean success = false;
		LoginUser user = this.getUserBySession(session);
		if (user != null && usermode != null) {
			success = Arrays.asList(usermode).contains(this.getUsermode(user));
		}
		return success;
	}

	/**
	 * Returns a {@link Usermode}, depending on the given {@link LoginUser}
	 *
	 * @param user the {@link LoginUser}
	 * @return the {@link Usermode}
	 */
	@Nullable
	public Usermode getUsermode(@NonNull LoginUser user) {
		Usermode result = null;

		if (user instanceof Admin) {
			result = Usermode.ADMIN;
		} else if (user instanceof Godfather) {
			result = Usermode.GODFATHER;
		} else if (user instanceof Trainee) {
			result = Usermode.TRAINEE;
		}

		return result;
	}

	/**
	 * Removes connection between {@link HttpSession} and {@link LoginUser}
	 *
	 * @param session HttpSession
	 * @return <code>true</code>, if successful; <code>false</code>, if an error
	 *         occurred or given {@link HttpSession} was null
	 */
	public boolean logout(@Nullable HttpSession session) {
		boolean success = false;
		if (session != null) {
			try {
				session.removeAttribute(this.userKey); // remove user
				session.invalidate(); // destroy session
				success = true;
			} catch (IllegalStateException e) {
				LoggingManager.log(Level.INFO, "Could not logout user due to an session error: " + e.getMessage()); //$NON-NLS-1$
			}
		}
		return success;
	}

	/**
	 * Updates the user connected with the session.<br>
	 * The id and type of user have to be the same!<br>
	 * Caution: This will call the {@link LoginUser#removeSecret()}-method.
	 * 
	 * @param session the {@link HttpSession}
	 * @param user    the {@link LoginUser} to replace
	 */
	public void update(@Nullable HttpSession session, @NonNull final LoginUser user) {
		LoginUser currentUser = this.getUserBySession(session);
		if (session != null && currentUser != null) {
			if (this.getUsermode(user) == this.getUsermode(currentUser) && currentUser.getId() == user.getId()) {
				session.removeAttribute(this.userKey);
				user.removeSecret();
				session.setAttribute(this.userKey, user);
			}
		}
	}

	/**
	 * Destroys old session and creates a new one to prevent session hijacking.<br>
	 * Should be called before every login and after every logout.<br>
	 * Caution: All session attributed will be gone after this method was executed!
	 *
	 * @param request {@link HttpServletRequest}
	 * @return new {@link HttpSession} or <code>null</code>, if given
	 *         {@link HttpServletRequest} was null
	 */
	@Nullable
	private static HttpSession newSession(@Nullable HttpServletRequest request) {
		HttpSession newSession = null;
		if (request != null) {
			HttpSession session = request.getSession(true); // true means, that a new session should be initiated if
															// none was existent
			session.invalidate(); // destroy old session
			newSession = request.getSession(true); // create new session to prevent session hijacking
		}
		return newSession;
	}
}
