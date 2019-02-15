/**
 *
 */
package de.db.derPate.model;

/**
 * This interface can be used for simple database entities, that connect a id
 * with a name
 *
 * @author MichelBlank
 *
 */
public interface HasName {
	/**
	 * Returns the name for the id
	 * 
	 * @return the name
	 */
	public String getName();
}
