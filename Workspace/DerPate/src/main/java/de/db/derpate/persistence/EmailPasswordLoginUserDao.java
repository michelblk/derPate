package de.db.derpate.persistence;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import de.db.derpate.model.EmailPasswordLoginUser;
import de.db.derpate.model.EmailPasswordLoginUser_;

/**
 * Abstract Data Access Object, that can be used for all objects, that extend
 * {@link EmailPasswordLoginUser}, to get their id, email and password
 *
 * @author MichelBlank
 *
 * @param <K> Primary Key Class
 * @param <E> Entity Class
 */
abstract class EmailPasswordLoginUserDao<@NonNull K, @Nullable E> extends IdDao<K, E> {
	/**
	 * Finds {@link EmailPasswordLoginUser} by email
	 *
	 * @param email {@link String} of the email address
	 * @return Object that is or extends from {@link EmailPasswordLoginUser} or
	 *         <code>null</code>, if user was not found
	 */
	@Nullable
	public E findByEmail(@NonNull String email) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<E> query = builder.createQuery(this.entityClass);

		Root<E> root = query.from(this.entityClass);
		Predicate predicate = builder.like(root.get(EmailPasswordLoginUser_.EMAIL), email);

		query.select(root).where(predicate);
		TypedQuery<E> q = entityManager.createQuery(query);
		List<E> results = q.getResultList();

		if (results.isEmpty() || results.size() > 1) {
			return null;
		}
		return results.get(0);
	}
}
