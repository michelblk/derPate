package de.db.derPate.util;

import java.util.logging.Level;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import de.db.derPate.manager.LoggingManager;

/**
 * This util configures the {@link SessionFactory} of Hibernate and stores it
 *
 * @author MichelBlank
 *
 */
public class HibernateSessionFactoryUtil {
	private static final SessionFactory sessionFactory;

	static {
		try {
			sessionFactory = new Configuration().configure("/de/db/derPate/resources/hibernate.cfg.xml") //$NON-NLS-1$
					.buildSessionFactory();
		} catch (HibernateException e) {
			LoggingManager.log(Level.SEVERE, "Could not create Hibernate session factory:\n" + e.getMessage()); //$NON-NLS-1$
			throw e; // stop program
		}
	}

	/**
	 * Returns the {@link SessionFactory} of this project
	 *
	 * @return {@link SessionFactory}
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
