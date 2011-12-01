/**
 * 
 */
package model;

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
	@OneToOne
	private Palle palle;

	public MellemlagerPlads() {

	}

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
			this.palle = palle;
	}

	public void placerPalle(Palle palle) {
		if (this.palle.getPlacering()!=null){
			this.palle.getPlacering().placerPalleUD(null);
		}
		this.placerPalleUD(palle);
		this.palle.placerPalleUD(this);
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
