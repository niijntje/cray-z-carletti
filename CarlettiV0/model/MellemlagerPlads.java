/**
 * 
 */
package model;

import model.Palle;

/**
 * v.0.3
 * 
 * @author Mads Dahl Jensen
 * 
 */
public class MellemlagerPlads {

	private String stregkode;
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

	void placerPalleUD(Palle palle) {
		this.palle = palle;
	}

	public void placerPalle(Palle palle) {
		this.placerPalleUD(palle);
		palle.placerPalleUD(this);
	}

	// ---------------------------------------------------------

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
