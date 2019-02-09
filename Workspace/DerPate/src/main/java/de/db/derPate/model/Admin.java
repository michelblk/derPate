package de.db.derPate.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.servlet.http.HttpSession;

import org.eclipse.jdt.annotation.NonNull;

import annotations.de.db.derPate.model.EmailPasswordLoginUser_;
import annotations.de.db.derPate.model.Id_;
import de.db.derPate.manager.LoginManager;

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
@AttributeOverride(name = Id_.ID, column = @Column(name = "Id_Admin"))
@AttributeOverride(name = EmailPasswordLoginUser_.EMAIL, column = @Column(name = "Email"))
@AttributeOverride(name = EmailPasswordLoginUser_.PASSWORD, column = @Column(name = "Password"))
public class Admin extends EmailPasswordLoginUser {

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
