package de.db.derPate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.http.HttpSession;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.hibernate.annotations.NaturalId;

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
public class Admin extends EmailPasswordLoginUser {

	/**
	 * Default constructor used by Hibernate
	 */
	private Admin() {
		super(-1, "");
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

	/**
	 * @see LoginUser#getId()
	 */
	@Override
	@Id
	@Column(name = "Id_Admin")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return super.getId();
	}

	/**
	 * @see EmailPasswordLoginUser#getEmail()
	 */
	@Override
	@NaturalId(mutable = true)
	@Column(name = "Username", nullable = false, unique = true)
	public @NonNull String getEmail() {
		return super.getEmail();
	}

	/**
	 * @see EmailPasswordLoginUser#getPassword()
	 */
	@Override
	@Column(name = "Password")
	public @Nullable String getPassword() {
		return super.getPassword();
	}
}
