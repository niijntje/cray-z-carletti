/**
 * 
 */
package model;

import java.util.ArrayList;


/**
 * v.0.3
 * @author Mads Dahl Jensen
 * 
 */
public class Palle {

	private String stregkode;
	private MellemlagerPlads mellemlagerPlads;
	private Drageringshal drageringshal; 
	private ArrayList<Mellemvare> mellemvarer;

	public Palle(String stregkode) {
		this.stregkode = stregkode;
		this.mellemvarer = new ArrayList<Mellemvare>();
	}

	public void setStregkode(String stregkode) {
		this.stregkode = stregkode;
	}

	public String getStregkode() {
		return stregkode;
	}

	// ------ Associering mellem Palle og Placering ------
	public MellemlagerPlads getPlacering() {
		return mellemlagerPlads;
	}

	void placerPalleUD(MellemlagerPlads placering) {
		this.mellemlagerPlads = placering;
	}

	/**
	 * @param placering Krav: Hvis parameteren placering er null, skal den globale attribut mellemlagerPlads v�re forskellig fra null. 
	 * Omvendt m� den globale attribut mellemlagerPlads ikke v�re sat til andet end null, hvis metoden kaldes med en parameter forskellig fra null.
	 * Med andre ord skal man kende status for Palle inden man kalder denne metode. 
	 * OBS! Dette kan m�ske blive et problem i det �jeblik man �nsker at flytte
	 * en palle fra �n placering til en anden, og ikke direkte fra mellemlager til drageringshal og omvendt.
	 */
	public void placerPalle(MellemlagerPlads placering) {
		if (placering != null) {
			this.placerPalleUD(placering);
			placering.placerPalleUD(this);
		} else {
			this.getPlacering().placerPalleUD(null);
			this.placerPalleUD(null);
		}
	}

	// ------ Associeriong mellem Palle og Dragering ------
	public Drageringshal getDrageringshal() {
		return drageringshal;
	}

	void setDrageringshalUD(Drageringshal drageringshal) {
		this.drageringshal = drageringshal;
	}

	public void setDrageringshal(Drageringshal drageringshal) {

		if (drageringshal != null) {
			this.setDrageringshalUD(drageringshal);
			drageringshal.addPalleUD(this);
		} else {
			this.getDrageringshal().removePalleUD(this);
			this.setDrageringshalUD(null);
		}
	}

	// ------- Associering mellem Palle og Mellemvare

	public ArrayList<Mellemvare> getMellemvarer() {
		return new ArrayList<Mellemvare>(mellemvarer);
	}

	void addMellemvareUD(Mellemvare mellemvare) {

		mellemvarer.add(mellemvare);
	}

	void removeMellemvareUD(Mellemvare mellemvare) {
		mellemvarer.remove(mellemvare);
	}

	public void addMellemvare(Mellemvare mellemvare) {
		this.addMellemvareUD(mellemvare);
		mellemvare.setPalleUD(this);	//S� mangler det jo alts� at blive tjekket, om mellemvare var knyttet til en anden palle i forvejen, 
										//der s� skal have removet sit link til mellemvare, for det s�rger mellemvare.setPalleUD ikke for...
	}

	public void removeMellemvare(Mellemvare mellemvare) {
		mellemvare.setPalleUD(null);
		this.removeMellemvareUD(mellemvare);

	}

	public void startDelbehandling() {
		if (alleVarerErEns()) {
			for (Mellemvare m : mellemvarer) {
				m.goToNextDelbehandling();
			}
		}

	}

	/**
	 * Tjekker om alle mellemvarer p� pallen er 'ens' - alts� tilh�rer samme
	 * produkttype og er lige langt i behandlingen. Det er ikke nok at tjekke p�
	 * �n af disse egenskaber, da flere produkttyper kan dele samme behandling
	 * men ikke m� blandes sammen og samme produkttype kan v�re forskellige
	 * steder i processen. Der tjekkes ikke p� tid, da der uundg�eligt vil v�re
	 * lidt forskel p� registreringerne af de enkelte mellemvarer.
	 * 
	 * @return
	 */
	public boolean alleVarerErEns() {	//L�d mere sigende end 'areIdentical()'
		boolean identical = true;
		if (mellemvarer.size() > 0) {
			Produkttype pt = mellemvarer.get(0).getProdukttype();
			Delbehandling db = mellemvarer.get(0)
					.getIgangvaerendeDelbehandling();
			for (Mellemvare m : mellemvarer) {
				if (m.getProdukttype() != pt) {
					identical = false;
				}
				if (m.getIgangvaerendeDelbehandling() != db) {
					identical = false;
				}
			}
		}
		return identical;
	}
	
	public String toString(){
		return "#"+this.getStregkode();
	}
	
	public String toStringLong(){
		return this.toString() + " - " + this.getMellemvarer().size() + " bk.";
	}

}