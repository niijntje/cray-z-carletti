/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
/**
 *
 */
@Entity
public class Palle {
	@Id
	private String stregkode;
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private MellemlagerPlads mellemlagerPlads;
	@ManyToOne
	private Drageringshal drageringshal;
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private List<Mellemvare> mellemvarer;

	public Palle(){

	}
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

	/**
	 * Meningen med de første 6 linier er at slette den eventuelle hidtidige
	 * dobbeltrettede association mellem Palle og MellemlagerPlads hhv.
	 * Dragering.
	 * 
	 * @param placering
	 */
	void placerPalleUD(MellemlagerPlads placering) {
		if (this.mellemlagerPlads != null) {
			mellemlagerPlads.placerPalleUD(null);
		}
		if (this.drageringshal != null) {
			drageringshal.removePalleUD(this);
		}
		this.mellemlagerPlads = placering;
	}

	/**
	 * @param placering
	 *            Krav: Hvis parameteren placering er null, skal den globale
	 *            attribut mellemlagerPlads være forskellig fra null. Omvendt må
	 *            den globale attribut mellemlagerPlads ikke være sat til andet
	 *            end null, hvis metoden kaldes med en parameter forskellig fra
	 *            null. Med andre ord skal man kende status for Palle inden man
	 *            kalder denne metode. OBS! Dette kan måske blive et problem i
	 *            det øjeblik man ønsker at flytte en palle fra én placering til
	 *            en anden, og ikke direkte fra mellemlager til drageringshal og
	 *            omvendt. Se placerPalleUD, der lige nu håndterer dette - er
	 *            dét den rigtige/bedste løsning??
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
		mellemvare.setPalleUD(this);
	}

	public void removeMellemvare(Mellemvare mellemvare) {
		mellemvare.setPalleUD(null);
		this.removeMellemvareUD(mellemvare);
	}

	/**
	 * Finder en mellemvare med den angivne produkttype og delbehandling på pallen, og overlader til startDelbehandling(Mellemvare mellemvare, Class delbehandlingsType, boolean alleAfSammeType) at starte næste delbehandling for de korrekte varer.
	 * @param produkttype
	 * @param delbehandling
	 * @param delbehandlingsType
	 * @param alleAfSammeType
	 * @return 
	 */
	public ArrayList<Mellemvare> startDelbehandling(Produkttype produkttype, Delbehandling delbehandling, Class delbehandlingsType){
		ArrayList<Mellemvare> behandledeMellemvarer = new ArrayList<Mellemvare>();
		for (Mellemvare m : mellemvarer) {
			if (m.erAfSammeType(produkttype, delbehandling) && m.naesteBehandlingGyldig(delbehandlingsType)){
				m.goToNextDelbehandling();
				behandledeMellemvarer.add(m);
			}
		}
		return behandledeMellemvarer;
	}

	/** Starter næste delbehandling af en eller flere mellemvarer.
	 * @param mellemvare. Hvis forskellig fra null og alleAfSammeType er true, startes næste delbehandling for alle mellemvarer på pallen af samme type som mellemvare. Hvis null, og alle mellemvarer er af samme type startes næste delbehandling for alle mellemvarer på pallen. Ellers startes næste delbehandling kun for den enkelte mellemvare.
	 */
	public ArrayList<Mellemvare> startDelbehandling(Mellemvare mellemvare, Class delbehandlingsType) {
		ArrayList<Mellemvare> behandledeMellemvarer = new ArrayList<Mellemvare>();
		//Hvis der ikke er angivet en mellemvare, skal alle på pallen være ens (og opfylde de andre betingelser)
		if (mellemvare == null && this.alleVarerErEns()){
			if (this.mellemvarer.get(0).naesteBehandlingGyldig(delbehandlingsType)){
				for (Mellemvare m : mellemvarer) {
					m.goToNextDelbehandling();
					behandledeMellemvarer.add(m);
				}
			}
		}

		//Ellers startes næste delbehandling kun for den angivne mellemvare
		else {
			if (mellemvare.naesteBehandlingGyldig(delbehandlingsType)){
				mellemvare.goToNextDelbehandling();
				behandledeMellemvarer.add(mellemvare);
			}
		}
		return behandledeMellemvarer;
	}

	/**Sender en eller flere mellemvarer af angivne type til færdigvarelager.
	 * @param produkttype
	 * @param delbehandling
	 * @return
	 */
	public ArrayList<Mellemvare> sendTilFaerdigvareLager(Produkttype produkttype, Delbehandling delbehandling){
		ArrayList<Mellemvare> behandledeMellemvarer = new ArrayList<Mellemvare>();
		for (Mellemvare m : mellemvarer) {
			if (m.erAfSammeType(produkttype, delbehandling) && m.naesteBehandlingGyldig(null)){
				m.setIgangvaerendeDelbehandling(null);
				m.setStatus(MellemvareStatus.FAERDIG);
				behandledeMellemvarer.add(m);
			}
		}
		return behandledeMellemvarer;
	}


	/** Sender en eller alle mellemvarer til færdigvarelageret og markerer deres status som færdig.
	 * @param mellemvare
	 * @param alleAfSammeType
	 */
	public ArrayList<Mellemvare> sendTilFaerdigvareLager(Mellemvare mellemvare){
		ArrayList<Mellemvare> behandledeMellemvarer = new ArrayList<Mellemvare>();
		//Hvis der ikke er angivet en mellemvare, skal alle på pallen være ens
		if (mellemvare == null && this.alleVarerErEns()){
			for (Mellemvare m : mellemvarer){
				if (m.naesteBehandlingGyldig(null)){
					m.setIgangvaerendeDelbehandling(null);
					m.setStatus(MellemvareStatus.FAERDIG);
				}
			}
		}
		//Ellers sendes kun den angivne mellemvare til færdigvarelager
		else {
			mellemvare.setIgangvaerendeDelbehandling(null);
			mellemvare.setStatus(MellemvareStatus.FAERDIG);
		}
		return behandledeMellemvarer;
	}
	/**
	 * @param produkttype
	 * @param delbehandling
	 * @param alleAfSammeType
	 */
	public ArrayList<Mellemvare> kasserMellemvare(Produkttype produkttype, Delbehandling delbehandling){
		ArrayList<Mellemvare> behandledeMellemvarer = new ArrayList<Mellemvare>();
			for (Mellemvare m : mellemvarer){
				if(m.erAfSammeType(produkttype, delbehandling)){
					m.setIgangvaerendeDelbehandling(null);
					m.setStatus(MellemvareStatus.FAERDIG);
					behandledeMellemvarer.add(m);
				}

			}
		return behandledeMellemvarer;
	}

	/**
	 * @param mellemvare
	 * @param alleAfSammeType
	 */
	public ArrayList<Mellemvare> kasserMellemvarer(Mellemvare mellemvare){
		ArrayList<Mellemvare> behandledeMellemvarer = new ArrayList<Mellemvare>();
		//Hvis der ikke er angivet en mellemvare, skal alle på pallen være ens
		if (mellemvare == null && this.alleVarerErEns()){
			for (Mellemvare m : mellemvarer){
				if (m.naesteBehandlingGyldig(null)){
					m.setIgangvaerendeDelbehandling(null);
					m.setStatus(MellemvareStatus.FAERDIG);
					behandledeMellemvarer.add(m);
				}
			}
		}
		//Ellers sendes kun den angivne mellemvare til færdigvarelager
		else {
			mellemvare.setIgangvaerendeDelbehandling(null);
			mellemvare.setStatus(MellemvareStatus.FAERDIG);
			behandledeMellemvarer.add(mellemvare);
		}
		return behandledeMellemvarer;

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
				if (!(hm.containsKey(m.getProdukttype()) && hm.get(m
						.getProdukttype()) == m.getIgangvaerendeDelbehandling())) {
					hm.put(m.getProdukttype(),
							m.getIgangvaerendeDelbehandling());
				}
			}
		}
		return (hm.size() <= 1);
	}

	/**
	 * @param mellemvare
	 * @return
	 * @author Rita Holst Jacobsen
	 */
	public ArrayList<Mellemvare> getMellemvarerAfSammeType(Mellemvare mellemvare) {
		return getMellemvarerAfSammeType(mellemvare.getProdukttype(), mellemvare.getIgangvaerendeDelbehandling());
	}

	/**
	 * @param produkttype
	 * @param delbehandling
	 * @return
	 * @author Rita Holst Jacobsen
	 */
	public ArrayList<Mellemvare> getMellemvarerAfSammeType(Produkttype produkttype, Delbehandling delbehandling) {
		ArrayList<Mellemvare> ensMellemvarer = new ArrayList<Mellemvare>();
		for (Mellemvare m : this.mellemvarer) {
			if (m.getProdukttype() == produkttype
					&& m.getIgangvaerendeDelbehandling() == delbehandling) {
				ensMellemvarer.add(m);
			}
		}
		return ensMellemvarer;
	}

	/**
	 * @param mellemvare
	 * @return
	 * @author Rita Holst Jacobsen
	 */
	public Integer getAntalAfSammeType(Mellemvare mellemvare) {
		System.out.println(getMellemvarerAfSammeType(mellemvare));
		return getMellemvarerAfSammeType(mellemvare).size();
	}

	/**
	 * @return
	 * @author Rita Holst Jacobsen
	 */
	public HashMap<Mellemvare, Integer> getMellemvareAntalMapping() {
		HashMap<Produkttype, Delbehandling> optaltePDpar = new HashMap<Produkttype, Delbehandling>();
		HashMap<Mellemvare, Integer> ensMellemvareAntalMapping = new HashMap<Mellemvare, Integer>();
		for (Mellemvare m : mellemvarer) {
			if (!(optaltePDpar.containsKey(m.getProdukttype()) && optaltePDpar
					.get(m.getProdukttype()) == m
					.getIgangvaerendeDelbehandling())) {
				optaltePDpar.put(m.getProdukttype(),
						m.getIgangvaerendeDelbehandling());
				ensMellemvareAntalMapping.put(m, getAntalAfSammeType(m));
			}
		}
		return ensMellemvareAntalMapping;
	}

	@Override
	public String toString() {
		return "#" + this.getStregkode();
	}

	//	public String toStringLong() {
	//		return this.toString() + " - " + this.getMellemvarer().size() + " bk.";
	//	}

}
