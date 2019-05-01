package de.db.derpate.servlet;

import de.db.derpate.model.Godfather;
import de.db.derpate.persistence.GodfatherDao;
import de.db.derpate.persistence.JobDao;
import de.db.derpate.persistence.LocationDao;
import de.db.derpate.servlet.filter.validation.FormatValidator;
import de.db.derpate.servlet.filter.validation.IdValidator;
import de.db.derpate.servlet.filter.validation.InputValidator;
import de.db.derpate.servlet.filter.validation.LengthValidator;
import de.db.derpate.servlet.filter.validation.NumberValidator;
import de.db.derpate.servlet.filter.validation.PatternValidator;
import de.db.derpate.servlet.godfatherOnly.GodfatherUpdateImageServlet;
import de.db.derpate.util.InputVerifyUtil;
import de.db.derpate.util.URIParameterEncryptionUtil;

/**
 * This enum should unify the parameter names. It should be used by the frontend
 * (as "name" for input fields {@literal <input name="ENUM" ...>}) as well as
 * the backend ({@link BaseServlet}).
 *
 * @author MichelBlank
 * @see BaseServlet
 */
public enum ServletParameter {
	/**
	 * @see Godfather#getId()
	 * @see URIParameterEncryptionUtil#encrypt(int)
	 * @see URIParameterEncryptionUtil#decryptToInteger(String)
	 */
	GODFATHER_ID(false, new IdValidator(new GodfatherDao()))
	/**
	 * @see Godfather#getEmail()
	 * @see InputVerifyUtil#isEmailAddress(String)
	 */
	,GODFATHER_EMAIL(false, new PatternValidator(InputVerifyUtil.EMAIL_PATTERN))
	/**
	 * @see Godfather#getPassword()
	 */
	,GODFATHER_PASSWORD(false, new LengthValidator(5))
	/**
	 * @see Godfather#getLastName()
	 */
	,GODFATHER_LAST_NAME(false, new LengthValidator(2))
	/**
	 * @see Godfather#getFirstName()
	 */
	,GODFATHER_FIRST_NAME(false, new LengthValidator(2))
	/**
	 * @see Godfather#getLocation()
	 * @see URIParameterEncryptionUtil#encrypt(int)
	 * @see URIParameterEncryptionUtil#decryptToInteger(String)
	 */
	,GODFATHER_LOCATION_ID(false, new IdValidator(new LocationDao()))
	/**
	 * @see Godfather#getMaxTrainees()
	 */
	,GODFATHER_MAX_TRAINEES(false, new NumberValidator(0, 10))
	/**
	 * @see Godfather#getDescription()
	 */
	,GODFATHER_DESCRIPTION(true, new LengthValidator(5))
	/**
	 * @see Godfather#getJob()
	 * @see URIParameterEncryptionUtil#encrypt(int)
	 * @see URIParameterEncryptionUtil#decryptToInteger(String)
	 */
	,GODFATHER_JOB_ID(false, new IdValidator(new JobDao()))
	/**
	 * @see Godfather#getHiringDate()
	 */
	,GODFATHER_HIRING_DATE(false, new FormatValidator(BaseServlet.HTML_DATE_FORMAT))
	/**
	 * @see Godfather#getBirthday()
	 */
	,GODFATHER_BIRTHDAY(true, new FormatValidator(BaseServlet.HTML_DATE_FORMAT))
	/**
	 * @see Godfather#getPickText()
	 */
	,GODFATHER_PICK_TEXT(true, null)
	/**
	 * @see GodfatherImageServlet
	 * @see GodfatherUpdateImageServlet
	 */
	,GODFATHER_IMAGE(true, null);

	private InputValidator validator;
	private boolean nullable;

	private ServletParameter(boolean nullable, InputValidator validator) {
		this.nullable = nullable;
		this.validator = validator;
	}

	/**
	 * Returns if a given value is a valid value for a field
	 *
	 * @param value the value
	 * @return <code>true</code>, if given value is valid; <code>false</code>, if
	 *         not
	 */
	public boolean isValid(String value) {
		return !(value == null && !this.nullable)
				&& ((this.validator != null && this.validator.isValid(value) || (this.validator == null)));
	}

	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
}
