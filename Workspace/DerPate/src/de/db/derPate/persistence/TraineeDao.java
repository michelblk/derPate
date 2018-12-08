package de.db.derPate.persistence;

import java.util.logging.Level;

import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.hibernate.NaturalIdLoadAccess;
import org.hibernate.Session;

import de.db.derPate.manager.LoggingManager;
import de.db.derPate.model.DatabaseEntity;
import de.db.derPate.model.Trainee;

public class TraineeDao extends IdDao {
	private static TraineeDao instance;

	private TraineeDao() {
		super(Trainee.class);
	}

	public static TraineeDao getInstance() {
		if (instance == null) {
			instance = new TraineeDao();
		}
		return instance;
	}

	public Trainee byToken(String token) {
		Trainee result = null;
		try {
			Session session = sessionFactory.openSession();

			NaturalIdLoadAccess<? extends DatabaseEntity> loader = session.byNaturalId(this.cls).with(LockOptions.READ);
			loader = loader.using("loginToken", token); // TODO find better way
			DatabaseEntity entity = loader.load();
			result = (Trainee) entity;

			session.close();
		} catch (HibernateException e) {
			LoggingManager.log(Level.WARNING, "An error occurred while finding trainee by token:\n" + e.getMessage());
		}

		return result;
	}
}
