package de.db.derPate.persistence;

import java.util.logging.Level;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import de.db.derPate.manager.LoggingManager;
import de.db.derPate.model.Id;

/**
 * Dao, that can be used for all models, that extend from {@link Id}
 *
 * @author MichelBlank
 *
 */
abstract class IdDao extends Dao {

	/**
	 * Constructor
	 *
	 * @param cls {@link Class} that the future objects should be of and that the
	 *            data is stored in (in the database)
	 */
	public IdDao(@NonNull Class<? extends Id> cls) {
		super(cls);
	}

	/**
	 * Finds object by {@link Id#getId()}
	 *
	 * @param id id
	 * @param    <T> type
	 * @return object or <code>null</code>, if object was not found
	 */
	@SuppressWarnings({ "unchecked" })
	@Nullable
	public <@Nullable T> T byId(int id) {
		T result = null;
		try {
			Session session = sessionFactory.openSession();
			result = (T) session.get(this.cls, id);
			session.close();
		} catch (HibernateException e) {
			LoggingManager.log(Level.WARNING, "Could not get database element by id: " + e.getMessage()); //$NON-NLS-1$
		}

		return result;
	}

}
