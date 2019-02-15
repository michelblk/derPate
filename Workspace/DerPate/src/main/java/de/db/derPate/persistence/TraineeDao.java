package de.db.derPate.persistence;

import java.util.logging.Level;

import org.eclipse.jdt.annotation.NonNull;
import org.hibernate.HibernateException;
import org.hibernate.NaturalIdLoadAccess;
import org.hibernate.Session;
import org.hibernate.Transaction;

import de.db.derPate.manager.LoggingManager;
import de.db.derPate.model.DatabaseEntity;
import de.db.derPate.model.Trainee;
import de.db.derPate.model.Trainee_;

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

	/**
	 * Updates a Trainee
	 *
	 * @param trainee the {@link Trainee}
	 * @return <code>true</code>, if update was successful; <code>false</code>, if
	 *         an error occurred
	 */
	public boolean update(@NonNull Trainee trainee) {
		boolean success = false;
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.update(trainee);
			transaction.commit();
			session.close();
			success = true;
		} catch (@SuppressWarnings("unused") HibernateException e) {
			// error updating entry
		}
		return success;
	}
}
