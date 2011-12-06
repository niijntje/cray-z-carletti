/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import model.Delbehandling.DelbehandlingsType;

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
	/**
	 * 
	 * @param stregkode
	 */
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
	 * @param placering
	 *     
	 */
	public void placerPalle(MellemlagerPlads placering) {
		if (this.getPlacering()!=null) {
			this.getPlacering().placerPalleUD(null);
		}
		if (placering != null){
			placering.placerPalleUD(this);
		}
		this.placerPalleUD(placering);
	}

	/**
	 * 
	 * @param placering
	 */
	void placerPalleUD(MellemlagerPlads placering) {
		this.mellemlagerPlads = placering;
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
			if (this.getDrageringshal()!=null){
				this.getDrageringshal().removePalleUD(this);
			}
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
	public ArrayList<Mellemvare> startDelbehandling(Produkttype produkttype, Delbehandling delbehandling, DelbehandlingsType delbehandlingsType){
		ArrayList<Mellemvare> behandledeMellemvarer = new ArrayList<Mellemvare>();
		for (Mellemvare m : mellemvarer) {
			if (m.erAfSammeType(produkttype, delbehandling) && m.naesteDelbehandlingGyldig(delbehandlingsType)){
				m.goToNextDelbehandling();
				behandledeMellemvarer.add(m);
			}
		}
		return behandledeMellemvarer;
	}

	/** Starter næste delbehandling af en eller flere mellemvarer.
	 * @param mellemvare. Hvis forskellig fra null og alleAfSammeType er true, startes næste delbehandling for alle mellemvarer på pallen af samme type som mellemvare. Hvis null, og alle mellemvarer er af samme type startes næste delbehandling for alle mellemvarer på pallen. Ellers startes næste delbehandling kun for den enkelte mellemvare.
	 */
	public ArrayList<Mellemvare> startDelbehandling(Mellemvare mellemvare, DelbehandlingsType delbehandlingsType) {
		ArrayList<Mellemvare> behandledeMellemvarer = new ArrayList<Mellemvare>();
		//Hvis der ikke er angivet en mellemvare, skal alle på pallen være ens (og opfylde de andre betingelser)
		if (mellemvare == null){
			if (this.alleVarerErEns()){
				if (this.mellemvarer.get(0).naesteDelbehandlingGyldig(delbehandlingsType)){
					for (Mellemvare m : mellemvarer) {
						m.goToNextDelbehandling();
						behandledeMellemvarer.add(m);
					}
				}
			}
		}

		//Ellers startes næste delbehandling kun for den angivne mellemvare
		else {
			if (mellemvare.naesteDelbehandlingGyldig(delbehandlingsType)){
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
			if (m.erAfSammeType(produkttype, delbehandling) && m.naesteDelbehandlingGyldig(null)){
				m.setIgangvaerendeDelbehandling(null);
				m.setStatus(MellemvareStatus.FAERDIG);
				m.addNuvaerendeTidspunkt();
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
				if (m.naesteDelbehandlingGyldig(null)){
					m.setIgangvaerendeDelbehandling(null);
					m.setStatus(MellemvareStatus.FAERDIG);
					m.addNuvaerendeTidspunkt();
				}
			}
		}
		//Ellers sendes kun den angivne mellemvare til færdigvarelager
		else {
			mellemvare.setIgangvaerendeDelbehandling(null);
			mellemvare.setStatus(MellemvareStatus.FAERDIG);
			mellemvare.addNuvaerendeTidspunkt();
		}
		return behandledeMellemvarer;
	}
	/**
	 * @param produkttype
	 * @param delbehandling
	 * @param alleAfSammeType
	 */
	public ArrayList<Mellemvare> kasserMellemvarer(Produkttype produkttype, Delbehandling delbehandling){
		ArrayList<Mellemvare> behandledeMellemvarer = new ArrayList<Mellemvare>();
		for (Mellemvare m : mellemvarer){
			if(m.erAfSammeType(produkttype, delbehandling)){
				m.setIgangvaerendeDelbehandling(null);
				m.setStatus(MellemvareStatus.KASSERET);
				m.addNuvaerendeTidspunkt();
				behandledeMellemvarer.add(m);
			}

		}
		return behandledeMellemvarer;
	}

	/**
	 * @param mellemvare Hvis null: Alle mellemvarer kasseres! Ellers sendes kun den angivne mellemvare til færdigvarelager

	 */
	public ArrayList<Mellemvare> kasserMellemvarer(Mellemvare mellemvare){
		ArrayList<Mellemvare> behandledeMellemvarer = new ArrayList<Mellemvare>();
		if (mellemvare == null){
			for (Mellemvare m : mellemvarer){
				m.setIgangvaerendeDelbehandling(null);
				m.setStatus(MellemvareStatus.KASSERET);
				m.addNuvaerendeTidspunkt();
				behandledeMellemvarer.add(m);
			}
		}
		else {
			mellemvare.setIgangvaerendeDelbehandling(null);
			mellemvare.setStatus(MellemvareStatus.KASSERET);
			mellemvare.addNuvaerendeTidspunkt();
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

	/**
	 * Returnerer om alle eller en delmængde af mellemvarerne på pallen er klar til en given handling (næste delbehandling eller færdigvarelageret)
	 * Hvis både @produkttype og @delbehandling er forskellige fra null, returneres om delmængde er klar. Ellers om alle er klar.
	 * @param palle	Krav: Forskellig fra null
	 * @param produkttype	Hvis null returneres om alle på pallen er klar
	 * @param delbehandling	Hvis null returneres om alle på pallen er klar. 
	 * @param naesteDelbehandlingsType	Den handling, der spørges til. Kan være hhv. Dragering, Tørring og Færdigvarelager (null)
	 * @return om alle/en delmængde er klar til næste (be)handling
	 */
	public boolean naesteDelbehandlingGyldig(Produkttype produkttype, Delbehandling delbehandling, DelbehandlingsType naesteDelbehandlingsType){
		boolean gyldig = false;
		if (getMellemvarer().size()==0){
			gyldig = false;
		}
		else {
			ArrayList<Mellemvare> aktuelleMellemvarer = new ArrayList<Mellemvare>();
			if (produkttype!=null && delbehandling!=null){	//Hvis både produkttype og delbehandling er kendt, returneres kun om produkter med _disse_ egenskaber er klar til næste delbehandling/færdigvarelager
				aktuelleMellemvarer = getMellemvarerAfSammeType(produkttype, delbehandling);
			}
			else if (alleVarerErEns()){						//Hvis produkttype og/eller delbehandling derimod er ukendt skal alle mellemvarer på pallen være ens
				aktuelleMellemvarer = getMellemvarer();
			}
			if (aktuelleMellemvarer.size()>0){
				gyldig = true;
				for (Mellemvare m : aktuelleMellemvarer){
					if (!m.naesteDelbehandlingGyldig(naesteDelbehandlingsType)){		//OG klar til næste delbehandling/færdigvarelager
						gyldig = false;
					}
				}
			}

		}
		return gyldig;
	}

	@Override
	public String toString() {
		return "#" + this.getStregkode();
	}
	public String getPlaceringsString() {
		String placeringsString = "";
		if (getPlacering() != null){
			placeringsString+="Mellemlager\n#"+getPlacering();
		}
		else if (getDrageringshal() != null){
			placeringsString+="Drageringshal\n";
		}
		else if (getMellemvarer().size()>0){
			placeringsString+="Færdigvarelager\n";
		}
		else {
			placeringsString += "Ikke i brug\n";
		}
		return  placeringsString;
	}

}
