package de.db.derpate.servlet.filter.validation;

import de.db.derpate.persistence.IdDao;
import de.db.derpate.util.URIParameterEncryptionUtil;

/**
 * Validates, that an database id is existent
 *
 * @author MichelBlank
 *
 */
@SuppressWarnings("rawtypes")
public class IdValidator implements InputValidator {
	private IdDao dao;

	/**
	 * Constructor
	 *
	 * @param dao the data access object
	 */
	public IdValidator(IdDao dao) {
		this.dao = dao;
	}

	/**
	 * Checks if the given encrypted database id is valid
	 *
	 * @param value the encrypted database id
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean isValid(String value) {
		Integer id = URIParameterEncryptionUtil.decryptToInteger(value);
		return this.dao.findById(id) != null;
	}

}
