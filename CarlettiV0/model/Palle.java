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
	 * @param placering
	 *     
	 */
	public void placerPalle(MellemlagerPlads placering) {
//		System.out.println(""+this.mellemlagerPlads+" "+placering);
		if (this.getPlacering()!=null) {
			this.getPlacering().placerPalleUD(null);
		}
		this.placerPalleUD(placering);
		placering.placerPalleUD(this);
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
