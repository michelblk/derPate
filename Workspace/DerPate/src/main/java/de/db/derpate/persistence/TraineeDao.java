package de.db.derpate.persistence;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import de.db.derpate.model.Trainee;
import de.db.derpate.model.Trainee_;

/**
 * Data Access Object providing methods to get {@link Trainee}s out of the
 * Database
 *
 * @author MichelBlank
 *
 */
public final class TraineeDao extends IdDao<@NonNull Integer, @Nullable Trainee> {
	/**
	 * Constructor
	 */
	public TraineeDao() {
		super();
	}

	/**
	 * Returns the Trainee with the given token.
	 *
	 * @param token the Login token
	 * @return the {@link Trainee} or <code>null</code>, if no {@link Trainee} was
	 *         found with the given token
	 */
	public Trainee byToken(String token) {
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Trainee> query = builder.createQuery(this.entityClass);

		Root<Trainee> root = query.from(this.entityClass);
		Predicate predicate = builder.like(root.get(Trainee_.LOGIN_TOKEN), token);

		query.select(root).where(predicate);
		TypedQuery<Trainee> q = this.entityManager.createQuery(query);
		List<Trainee> results = q.getResultList();

		if (results.isEmpty() || results.size() > 1) {
			return null;
		}
		return results.get(0);
	}
}
