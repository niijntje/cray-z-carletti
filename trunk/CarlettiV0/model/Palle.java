/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.HashMap;


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
		if (this.mellemlagerPlads != null){
			mellemlagerPlads.placerPalleUD(null);
		}
		if (this.drageringshal != null){
			drageringshal.removePalleUD(this);
		}
		this.mellemlagerPlads = placering;
	}

	/**
	 * @param placering Krav: Hvis parameteren placering er null, skal den globale attribut mellemlagerPlads være forskellig fra null. 
	 * Omvendt må den globale attribut mellemlagerPlads ikke være sat til andet end null, hvis metoden kaldes med en parameter forskellig fra null.
	 * Med andre ord skal man kende status for Palle inden man kalder denne metode. 
	 * OBS! Dette kan måske blive et problem i det øjeblik man ønsker at flytte
	 * en palle fra én placering til en anden, og ikke direkte fra mellemlager til drageringshal og omvendt.
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
		mellemvare.setPalleUD(this);	//Så mangler det jo altså at blive tjekket, om mellemvare var knyttet til en anden palle i forvejen, 
										//der så skal have removet sit link til mellemvare, for det sørger mellemvare.setPalleUD ikke for...
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
	 * Tjekker om alle mellemvarer på pallen er 'ens' - altså tilhører samme
	 * produkttype og er lige langt i behandlingen. Det er ikke nok at tjekke på
	 * én af disse egenskaber, da flere produkttyper kan dele samme behandling
	 * men ikke må blandes sammen og samme produkttype kan være forskellige
	 * steder i processen. Der tjekkes ikke på tid, da der uundgåeligt vil være
	 * lidt forskel på registreringerne af de enkelte mellemvarer.
	 * 
	 * @return
	 * @author Rita Holst Jacobsen
	 */
	public boolean alleVarerErEns() {	
		HashMap<Produkttype, Delbehandling> hm = new HashMap<Produkttype, Delbehandling>();
		if (mellemvarer.size() > 0) {
			for (Mellemvare m : mellemvarer) {
				if (!(hm.containsKey(m.getProdukttype()) && hm.get(m.getProdukttype()) == m.getIgangvaerendeDelbehandling())){
					hm.put(m.getProdukttype(), m.getIgangvaerendeDelbehandling());
				}
			}
		}
		return (hm.size()<=1);
	}
	
	
	/**
	 * @param mellemvare
	 * @return
	 * @author Rita Holst Jacobsen
	 */
	public Integer getAntalAfSammeType(Mellemvare mellemvare){
		Integer antal = 0;
		for (Mellemvare m: mellemvarer){
			if (m.getProdukttype() == mellemvare.getProdukttype() && m.getIgangvaerendeDelbehandling() == mellemvare.getIgangvaerendeDelbehandling()){
				antal++;
			}
		}
		return antal;
	}
	
	/**
	 * @return
	 * @author Rita Holst Jacobsen
	 */
	public HashMap<Mellemvare, Integer> getMellemvareAntalMapping(){
		HashMap<Produkttype, Delbehandling> optaltePDpar = new HashMap<Produkttype, Delbehandling>();
		HashMap<Mellemvare, Integer> ensMellemvareAntalMapping = new HashMap<Mellemvare, Integer>();
		for (Mellemvare m: mellemvarer){
			if (!(optaltePDpar.containsKey(m.getProdukttype()) && optaltePDpar.get(m.getProdukttype())==m.getIgangvaerendeDelbehandling())){
				optaltePDpar.put(m.getProdukttype(), m.getIgangvaerendeDelbehandling());
				ensMellemvareAntalMapping.put(m, getAntalAfSammeType(m));
			}
		}
		return ensMellemvareAntalMapping;
	}
	
	
	
	@Override
	public String toString(){
		return "#"+this.getStregkode();
	}
	
	public String toStringLong(){
		return this.toString() + " - " + this.getMellemvarer().size() + " bk.";
	}

}
