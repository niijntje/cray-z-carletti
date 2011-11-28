/**
 * 
 */
package model;

import java.util.ArrayList;

/**
 * v.0.3
 * 
 * @author Mads Dahl Jensen
 * 
 */
public class Drageringshal {

	private static Drageringshal drageringshal;
	private ArrayList<Palle> paller;

	private Drageringshal() {
		paller = new ArrayList<Palle>();
	}

	public static Drageringshal getInstance() {
		if (drageringshal == null) {
			drageringshal = new Drageringshal();
		}
		return drageringshal;
	}

	// ------ Associering mellem Palle og Drageringshal------
	public ArrayList<Palle> getPaller() {
		return new ArrayList<Palle>(paller);
	}

	void removePalleUD(Palle palle) {
		paller.remove(palle);
	}

	public void removePalle(Palle palle) {
		palle.setDrageringshalUD(null);
		this.removePalleUD(palle);
	}

	void addPalleUD(Palle palle) {
		paller.add(palle);
	}

	public void addPalle(Palle palle) {
		this.addPalleUD(palle);
		palle.setDrageringshalUD(this);
	}

}
