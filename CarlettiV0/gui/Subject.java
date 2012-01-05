/**
 * SUBJECT
 */
package gui;

/**
 * Interface til at håndtere Observer-pattern
 * @author Rasmus Cederdorff
 *
 */

public interface Subject {
	
	public void registerObserver(Observer o);

	public void removeObserver(Observer o);

	public void notifyObservers();
}
