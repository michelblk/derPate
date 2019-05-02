package de.db.derpate.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

/**
 * Baseclass of all classes, that can store data, which you can use to log on.
 *
 * @author MichelBlank
 *
 */
@MappedSuperclass
public abstract class LoginUser extends Id implements Serializable {
	/**
	 * Default serial version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor used for hibernate
	 */
	LoginUser() {
		super();
	}

	/**
	 * Constructor
	 *
	 * @param id id
	 */
	public LoginUser(int id) {
		super(id);
	}
}
