package de.db.derpate.persistence;

import java.util.logging.Level;

import org.hibernate.HibernateException;
import org.hibernate.NaturalIdLoadAccess;
import org.hibernate.Session;

import de.db.derpate.model.Trainee_;
import de.db.derpate.manager.LoggingManager;
import de.db.derpate.model.DatabaseEntity;
import de.db.derpate.model.Trainee;

/**
 * Data Access Object providing methods to get {@link Trainee}s out of the
 * Database
 *
 * @author MichelBlank
 *
 */
public class TraineeDao extends IdDao {
	private static TraineeDao instance;

	private TraineeDao() {
		super(Trainee.class);
	}

	/**
	 * Returns current instance
	 *
	 * @return instance
	 */
	public static TraineeDao getInstance() {
		if (instance == null) {
			instance = new TraineeDao();
		}
		return instance;
	}

	/**
	 * Returns the Trainee with the given token.
	 *
	 * @param token the Login token
	 * @return the {@link Trainee} or <code>null</code>, if no {@link Trainee} was
	 *         found with the given token
	 */
	public Trainee byToken(String token) {
		Trainee result = null;
		try {
			Session session = sessionFactory.openSession();

			NaturalIdLoadAccess<? extends DatabaseEntity> loader = session.byNaturalId(this.cls);
			loader = loader.using(Trainee_.LOGIN_TOKEN, token);
			DatabaseEntity entity = loader.load();
			result = (Trainee) entity;

			session.close();
		} catch (HibernateException e) {
			LoggingManager.log(Level.WARNING, "An error occurred while finding trainee by token:\n" + e.getMessage()); //$NON-NLS-1$
		}

		return result;
	}
}
