package de.db.derPate.persistence;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.db.derPate.model.DatabaseEntity;
import de.db.derPate.util.HibernateSessionFactoryUtil;

/**
 * This abstract class is used a a base class for all database objects. It
 * stores the default {@link SessionFactory} (@see
 * {@link HibernateSessionFactoryUtil#getSessionFactory()}
 *
 * @author MichelBlank
 *
 */
abstract class Dao {
	/**
	 * The default {@link SessionFactory}
	 */
	protected static final SessionFactory sessionFactory;
	/**
	 * The {@link Class} that the wanted objects are of
	 *
	 * @see de.db.derPate.model
	 */
	@NonNull
	protected final Class<? extends DatabaseEntity> cls;

	static {
		sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
	}

	/**
	 * Default constructor for dao objects
	 *
	 * @param cls {@link Class} that the future objects should be of and that the
	 *            data is stored in (in the database)
	 */
	public Dao(@NonNull Class<? extends DatabaseEntity> cls) {
		this.cls = cls;
	}

	/**
	 * Returns a {@link List} of {@link #cls}-objects, stored in the Table used for
	 * {@link #cls}-objects
	 *
	 * @param <T> type
	 * @return {@link List} of objects
	 */
	public <T extends DatabaseEntity> List<T> list() {
		Session session = sessionFactory.openSession();
		List<T> list = session.createQuery("FROM " + this.cls.getName()).list();
		session.close();
		return list;
	}
}
