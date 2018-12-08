package de.db.derPate.persistence;

import de.db.derPate.model.Godfather;

public class GodfatherDao extends EmailPasswordLoginUserDao {
	private static GodfatherDao instance;

	private GodfatherDao() {
		super(Godfather.class);
	}

	public static GodfatherDao getInstance() {
		if (instance == null) {
			instance = new GodfatherDao();
		}
		return instance;
	}
}
