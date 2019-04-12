package de.db.derpate.persistence;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import de.db.derpate.manager.LoggingManager;
import de.db.derpate.model.Id;

/**
 * Data Access Object, that can be used for all models, that extend from
 * {@link Id}
 *
 * @author MichelBlank
 *
 * @param <K> Primary Key Class
 * @param <E> Entity Class
 */
abstract class IdDao<@NonNull K, @Nullable E> implements Dao<K, E> {
	/**
	 * The {@link EntityManager}
	 */
	@PersistenceContext
	protected static final EntityManager entityManager;
	/**
	 * The {@link Class} that the wanted objects are of
	 *
	 * @see de.db.derpate.model
	 */
	@NonNull
	protected final Class<E> entityClass;

	static {
		// FIXME find a way, to make EntityManager not static, while findById returns a
		// valid user (with password)
		entityManager = new EntityManagerFactory().getEntityManager();
	}

	/**
	 * Default constructor for dao objects
	 */
	@SuppressWarnings({ "null", "unchecked" })
	public IdDao() {
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
	}

	/**
	 * Finds object by {@link Id#getId()}
	 *
	 * @param id id
	 * @return object or <code>null</code>, if object was not found
	 */
	@Override
	public E findById(@NonNull K id) {
		E result = entityManager.find(this.entityClass, id);
		entityManager.refresh(result);
		return result;
	}

	@Override
	public List<E> all() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		// Build query
		CriteriaQuery<E> query = builder.createQuery(this.entityClass);
		Root<E> root = query.from(this.entityClass);
		CriteriaQuery<E> all = query.select(root);

		// Execute query and get ResultList
		TypedQuery<E> q = entityManager.createQuery(all);
		List<E> result = q.getResultList();

		if (result == null) {
			result = new ArrayList<>();
		}
		return result;
	}

	@Override
	public void persist(E entity) {
		entityManager.persist(entity);
	}

	@Override
	public boolean update(E entity) {
		boolean success = false;
		EntityTransaction transaction = null;
		try {
			transaction = entityManager.getTransaction();
			transaction.begin();
			entityManager.merge(entity);
			transaction.commit();
			success = true;
		} catch (RollbackException e) {
			LoggingManager.log(Level.INFO, "Error updating DatabaseEntity. Rolling back: " + e.getMessage()); //$NON-NLS-1$
		} catch (IllegalArgumentException | TransactionRequiredException | IllegalStateException e) {
			LoggingManager.log(Level.WARNING, "Error updating DatabaseEntity: " + e.getMessage()); //$NON-NLS-1$
		}
		return success;
	}

	@Override
	public void remove(E entity) {
		entityManager.remove(entity);
	}

}
