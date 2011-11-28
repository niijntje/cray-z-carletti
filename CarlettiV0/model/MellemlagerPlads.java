/**
 * 
 */
package model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;


/**
 * v.0.3
 * 
 * @author Mads Dahl Jensen
 * 
 */
@Entity
public class MellemlagerPlads {
	@Id 
	private String stregkode;
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private Palle palle;

	public MellemlagerPlads(String stregkode) {
		this.stregkode = stregkode;
	}

	public String getStregkode() {
		return stregkode;
	}

	public void setStregkode(String stregkode) {
		this.stregkode = stregkode;
	}

	// ------ Associering mellem palle og mellemlagerPlads ------
	public Palle getPalle() {
		return palle;
	}

	void placerPalleUD(Palle palle) throws RuntimeException {
		if (this.palle == null) {
			this.palle = palle;
		} else if (palle != this.palle) {
			throw new RuntimeException(
					"Der står allerede en palle på denne plads!");
		}

	}

	public void placerPalle(Palle palle) {
		this.placerPalleUD(palle);
		palle.placerPalleUD(this);
	}

	// ---------------------------------------------------------

	@Override
	public String toString() {
		return this.getStregkode();
	}

	public String toStringLong() {
		String palleString = "";
		if (this.getPalle() != null) {
			palleString = " - " + this.getPalle().toString();
		}
		return this.toString() + palleString;
	}
}
