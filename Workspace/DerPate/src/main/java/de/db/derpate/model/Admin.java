package de.db.derpate.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.servlet.http.HttpSession;

import org.eclipse.jdt.annotation.NonNull;

import de.db.derpate.manager.LoginManager;

/**
 * This dataclass is used to store {@link Admin}s login credentials and to make
 * the {@link Admin} recognized as such, as the {@link LoginUser} stored in the
 * user's {@link HttpSession} (see {@link LoginManager}) can be typecasted back.
 *
 * @author MichelBlank
 *
 */
@Entity
@Table(name = "admin")
@AttributeOverride(name = "id", column = @Column(name = "Id_Admin"))
@AttributeOverride(name = "email", column = @Column(name = "Email"))
@AttributeOverride(name = "password", column = @Column(name = "Password"))
public class Admin extends EmailPasswordLoginUser {
	/**
	 * default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor used for hibernate
	 */
	Admin() {
		super();
	}

	/**
	 * Constructor
	 *
	 * @param id    Database id
	 * @param email Email
	 */
	public Admin(int id, @NonNull String email) {
		super(id, email);
	}
}
