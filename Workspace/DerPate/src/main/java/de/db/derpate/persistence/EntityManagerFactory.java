package de.db.derpate.persistence;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.eclipse.jdt.annotation.NonNull;

import de.db.derpate.Constants;

/**
 * This factory uses java's {@link Persistence} class to create an
 * EntityManagerFactory and EntityManager's using the persistence settings file
 * (src/main/resources/META-INF/persistence.xml).<br>
 * Pattern: Singleton
 *
 * @author MichelBlank
 * @see javax.persistence.EntityManagerFactory
 */
public final class EntityManagerFactory {
	@NonNull
	private static EntityManagerFactory instance;
	private final String PERSISTENCE_UNIT_NAME = "de.db.derpate"; //$NON-NLS-1$
	private final javax.persistence.EntityManagerFactory EMF;

	static {
		instance = new EntityManagerFactory();
	}

	/**
	 * Constructor
	 */
	private EntityManagerFactory() {
		this.EMF = Persistence.createEntityManagerFactory(this.PERSISTENCE_UNIT_NAME, buildProperties());
	}

	/**
	 * Returns the {@link EntityManagerFactory}
	 *
	 * @return the {@link EntityManagerFactory}
	 */
	@NonNull
	protected static EntityManagerFactory getInstance() {
		return instance;
	}

	/**
	 * Create a new application-managed EntityManager. This method returns a new
	 * EntityManager instance each time it is invoked. The isOpen method will return
	 * true on the returned instance.
	 *
	 * @return {@link EntityManager} instance
	 * @see javax.persistence.EntityManagerFactory#createEntityManager()
	 */
	protected EntityManager getEntityManager() {
		return this.EMF.createEntityManager();
	}

	/**
	 * Builds a {@link Properties} object containing database settings (such as url,
	 * username and password) defined in {@link Constants.Database}.
	 *
	 * @return the {@link Properties}
	 */
	private static Properties buildProperties() {
		Properties properties = new Properties();

		properties.setProperty("javax.persistence.jdbc.url", Constants.Database.URL); //$NON-NLS-1$
		properties.setProperty("javax.persistence.jdbc.user", Constants.Database.USERNAME); //$NON-NLS-1$
		properties.setProperty("javax.persistence.jdbc.password", Constants.Database.PASSWORD); //$NON-NLS-1$

		return properties;
	}

}
