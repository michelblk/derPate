package de.db.derPate.persistence;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.hibernate.Session;

import de.db.derPate.model.Godfather;

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

	public static List<Godfather> filterAvailable(@Nullable List<String> location, @Nullable List<String> jobs,
			@Nullable List<String> teachingType, @Nullable List<String> educationalYear) {
		List<Godfather> result = new ArrayList<>();
		List<String> where = new ArrayList<>();

		if (location != null && location.size() > 0) {
			where.add("Id_Location IN (" + String.join(", ", location) + ")");
		}
		if (jobs != null && jobs.size() > 0) {
			where.add("Id_Job IN (" + String.join(", ", jobs) + ")");
		}
		if (teachingType != null && teachingType.size() > 0) {
			where.add("Id_Teaching_Type IN (" + String.join(", ", teachingType) + ")");
		}
		if (educationalYear != null && educationalYear.size() > 0) {
			where.add("TIMESTAMPDIFF(YEAR, Hiring_Date, CURDATE()) IN (" + String.join(", ", educationalYear) + ")");
		}

		Session session = sessionFactory.openSession();
		result = session.createQuery("FROM Godfather" + (where.size() > 0 ? " WHERE " : "") + String.join(" AND ", //$NON-NLS-1$
				where.toArray(new String[0]))).list();
		session.close();

		return result;
	}

	public static List<@NonNull Integer> getEducationalYears() {
		List<@NonNull Integer> result = new ArrayList<>();

		Session session = sessionFactory.openSession();
		List<@NonNull BigInteger> sqlResult = session
				.createSQLQuery("SELECT DISTINCT TIMESTAMPDIFF(YEAR, Hiring_Date, CURDATE()) AS 'year' FROM Godfather")
				.list();
		session.close();

		for (@NonNull
		BigInteger year : sqlResult) {
			result.add(year.intValue());
		}

		return result;
	}
}
