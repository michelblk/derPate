package de.db.derpate.util;

import java.util.logging.Level;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import de.db.derpate.Constants;
import de.db.derpate.manager.LoggingManager;

/**
 * This util configures the {@link SessionFactory} of Hibernate and stores it
 *
 * @author MichelBlank
 *
 */
public final class HibernateSessionFactoryUtil {
	private static final SessionFactory sessionFactory;
	static {
		try {
			Configuration config = new Configuration().configure();
			config.setProperty("hibernate.connection.url", Constants.Database.URL); //$NON-NLS-1$
			config.setProperty("hibernate.connection.username", Constants.Database.USERNAME); //$NON-NLS-1$
			config.setProperty("hibernate.connection.password", Constants.Database.PASSWORD); //$NON-NLS-1$

			sessionFactory = config.buildSessionFactory();
		} catch (HibernateException e) {
			LoggingManager.log(Level.SEVERE,
					"\n\n-------------Could not create Hibernate session factory:-------------\n--> Check Database: " //$NON-NLS-1$
							+ e.getMessage() + "\n\n"); //$NON-NLS-1$
			throw e; // stop program
		}
	}

	private HibernateSessionFactoryUtil() {
		// nothing to do
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
