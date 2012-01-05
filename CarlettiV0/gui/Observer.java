/**
 * OBSERVER
 */
package gui;

/**
 * Interface til at h�ndtere Observer-pattern
 * @author Rasmus Cederdorff
 *
 */

public interface Observer {
	
	/**
	 * Metoden kaldes af klasser der implementerer Subject
	 * S�rger for at der opdateres det �nskede
	 */
	public void update();
}
