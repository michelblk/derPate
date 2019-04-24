package de.db.derpate.util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 * This util contains tools for the data access objects
 *
 * @author MichelBlank
 *
 */
public class HibernateUtil {
	private HibernateUtil() {
		// nothing to do
	}

	/**
	 * Connects a old {@link Predicate} with a new {@link Predicate} (AND).
	 *
	 * @param builder          the {@link CriteriaBuilder}
	 * @param currentPredicate the {@link Predicate} the new predicate should be
	 *                         added to
	 * @param predicateToAdd   the new {@link Predicate}
	 * @return the combined {@link Predicate}
	 */
	public static Predicate addPredicate(@NonNull CriteriaBuilder builder, @Nullable Predicate currentPredicate,
			@Nullable Predicate predicateToAdd) {
		if (currentPredicate == null) {
			return predicateToAdd;
		}
		if (predicateToAdd == null) {
			return currentPredicate;
		}
		return builder.and(currentPredicate, predicateToAdd);
	}
}
