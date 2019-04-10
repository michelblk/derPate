package de.db.derPate.persistence;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.hibernate.Session;
import org.hibernate.query.Query;

import de.db.derPate.model.Godfather;
import de.db.derPate.model.Godfather_;
import de.db.derPate.model.Id_;
import de.db.derPate.model.Job_;

/**
 * Data Access Object providing methods to get {@link Godfather} objects out of
 * the Database
 *
 * @author MichelBlank
 *
 */
public class GodfatherDao extends EmailPasswordLoginUserDao {
	private static GodfatherDao instance;

	private GodfatherDao() {
		super(Godfather.class);
	}

	/**
	 * Returns current instance
	 *
	 * @return instance
	 */
	public static GodfatherDao getInstance() {
		if (instance == null) {
			instance = new GodfatherDao();
		}
		return instance;
	}

	/**
	 * Filters all Godfathers with the given id's for location, job, teachingType
	 * and educationalYear, that have at least one free slot available for another
	 * trainee. If multiple id's per type (location, job, etc.) are selected, only
	 * one has to be true.
	 *
	 * @param location        the location ids
	 * @param jobs            the job ids
	 * @param teachingType    the teaching type ids
	 * @param educationalYear the number of the educational year (1, 2, 3, ...)
	 * @return a {@link List} of {@link Godfather}s, that the filter applies to
	 */
	@Nullable
	public List<Godfather> filterAvailable(@Nullable List<String> location, @Nullable List<String> jobs,
			@Nullable List<String> teachingType, @Nullable List<String> educationalYear) {

		Session session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<Godfather> query = builder.createQuery(Godfather.class);
		Root<Godfather> root = query.from(Godfather.class);

		query.select(root);
		query.orderBy(builder.asc(root.get(Godfather_.CURRENT_TRAINEES)), builder.asc(root.get(Godfather_.FIRST_NAME)));

		Predicate predicate = builder.lt(root.get(Godfather_.CURRENT_TRAINEES), root.get(Godfather_.MAX_TRAINEES));
		if (location != null && !location.isEmpty()) {
			Predicate clause = root.get(Godfather_.LOCATION).get(Id_.ID).in(location);
			predicate = builder.and(predicate, clause);
		}
		if (jobs != null && !jobs.isEmpty()) {
			Predicate clause = root.get(Godfather_.JOB).get(Id_.ID).in(jobs);
			predicate = builder.and(predicate, clause);
		}
		if (teachingType != null && !teachingType.isEmpty()) {
			Predicate clause = root.get(Godfather_.JOB).get(Job_.TEACHING_TYPE).get(Id_.ID).in(teachingType);
			predicate = builder.and(predicate, clause);
		}
		if (educationalYear != null && !educationalYear.isEmpty()) {
			Predicate clause = root.get(Godfather_.EDUCATIONAL_YEAR).in(educationalYear);
			predicate = builder.and(predicate, clause);
		}

		if (predicate != null) {
			query.where(predicate);
		}

		Query<Godfather> q = session.createQuery(query);

		return q.getResultList();
	}

	/**
	 * Returns a {@link List} of the educational years, the godfathers are in.<br>
	 * For example 1, 2, 3.
	 *
	 * @return a {@link List} of educational years
	 */
	@NonNull
	public static List<@NonNull Integer> getEducationalYears() {
		List<@NonNull Integer> result = new ArrayList<>();

		Session session = sessionFactory.openSession();
		// TODO avoid sql and use a hibernate query instead
		List<@NonNull BigInteger> sqlResult = session
				.createSQLQuery("SELECT DISTINCT YEAR_DIFF(hiring_date) + 1 AS 'year' FROM Godfather") //$NON-NLS-1$
				.list();
		session.close();

		for (@NonNull
		BigInteger year : sqlResult) {
			result.add(year.intValue());
		}

		return result;
	}
}
