package gui;
/**
 * Interface til at hŒndtere Observer-pattern
 * @author Rasmus Cederdorff
 *
 */
public interface Observer {
	/**
	 * Metoden kaldes af klasser der implementerer Subject
	 * S¿rger for at der opdateres det ¿nskede
	 */
	public void update();
}
