/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


/**
 * v.0.3
 * 
 * @author Mads Dahl Jensen
 * 
 */
@Entity
public class Drageringshal {
	@Id
	@GeneratedValue
	private int id;
	private static Drageringshal drageringshal;
	@OneToMany
	private List<Palle> paller;
	
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
