/**
 * OBSERVER
 */
package gui;

/**
 * Interface til at håndtere Observer-pattern
 * @author Rasmus Cederdorff
 *
 */

public interface Observer {
	
	/**
	 * Metoden kaldes af klasser der implementerer Subject
	 * Sørger for at der opdateres det ønskede
	 */
	public void update();
}
