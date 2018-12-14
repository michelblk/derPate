package de.db.derPate.persistence;

import java.util.logging.Level;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.hibernate.NaturalIdLoadAccess;
import org.hibernate.Session;

import de.db.derPate.manager.LoggingManager;
import de.db.derPate.model.DatabaseEntity;
import de.db.derPate.model.EmailPasswordLoginUser;

/**
 * Abstract dao, that can be used for all objects, that extend
 * {@link EmailPasswordLoginUser}, to get their id, email and password
 *
 * @author MichelBlank
 *
 */
abstract class EmailPasswordLoginUserDao extends IdDao {

	/**
	 * Constructor
	 *
	 * @param cls {@link Class} that the future objects should be of and that the
	 *            data is stored in (in the database)
	 */
	public EmailPasswordLoginUserDao(@NonNull final Class<? extends EmailPasswordLoginUser> cls) {
		super(cls);
	}

	/**
	 * Finds {@link EmailPasswordLoginUser} by email
	 *
	 * @param email {@link String} of the email address
	 * @param       <T> type
	 * @return Object that is or extends from {@link EmailPasswordLoginUser} or
	 *         <code>null</code>, if user was not found
	 */
	@SuppressWarnings("unchecked")
	@Nullable
	public <@Nullable T extends EmailPasswordLoginUser> T byEmail(@NonNull String email) {
		T result = null;
		try {
			Session session = sessionFactory.openSession();

			NaturalIdLoadAccess<? extends DatabaseEntity> loader = session.byNaturalId(this.cls).with(LockOptions.READ);
			loader = loader.using("email", email); // TODO find better way //$NON-NLS-1$
			DatabaseEntity entity = loader.load();
			result = (T) entity;

			session.close();
		} catch (HibernateException e) {
			LoggingManager.log(Level.WARNING, "An error occurred while finding user by email:\n" + e.getMessage()); //$NON-NLS-1$
		}

		return result;
	}
}
