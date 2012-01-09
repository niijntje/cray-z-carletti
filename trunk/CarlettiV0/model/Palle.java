/**
 * PALLE
 */
package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import model.Delbehandling.DelbehandlingsType;

/**
 * Klassen repræsenterer en palle, der kan have en eller flere mellemvarer knyttet til sig, og desuden 
 * bevæge sig rundt i produktionen mellem drageringshal, mellemvarelager og færdigvarelager. En palle kan
 * betragtes som en aggregering af mellemvarer, og flere operationer, der kan udføres på Mellemvare, kan
 * også udføres på/via Palle. Det er placeringen af pallen, der afgør hvor en mellemvare befinder sig (på 
 * trods af det paradoksale i at en mellemvare under dragering dermed kan findes på mellemvarelageret).
 * 
 * @author Rita Holst Jacobsen
 * @author Mads Dahl Jensen: Hvor angivet
 * @author Rasmus Cederdorff: JPA
 * 
 */
@Entity
public class Palle {
	@Id
	private String stregkode;
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	private MellemlagerPlads mellemlagerPlads;
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Drageringshal drageringshal;
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	private List<Mellemvare> mellemvarer;

	public Palle() {

	}

	/**
	 * 
	 * @param stregkode
	 * @author Mads Dahl Jensen
	 */
	public Palle(String stregkode) {
		this.stregkode = stregkode;
		this.mellemvarer = new ArrayList<Mellemvare>();
	}

	/**
	 * @param stregkode
	 * @author Mads Dahl Jensen
	 */
	public void setStregkode(String stregkode) {
		this.stregkode = stregkode;
	}

	/**
	 * @return
	 * @author Mads Dahl Jensen
	 */
	public String getStregkode() {
		return stregkode;
	}

	// ------ Associering mellem Palle og Placering ------
	/**
	 * @return
	 * @author Mads Dahl Jensen
	 */
	public MellemlagerPlads getPlacering() {
		return mellemlagerPlads;
	}

	/**
	 * @param placering
	 * @author Mads Dahl Jensen + Rita Holst Jacobsen
	 */
	public void placerPalle(MellemlagerPlads placering) {
		if (this.getPlacering() != null) {
			this.getPlacering().placerPalleUD(null);
		}
		if (placering != null) {
			placering.placerPalleUD(this);
		}
		this.placerPalleUD(placering);
	}

	/**
	 * @param placering
	 * @author Mads Dahl Jensen
	 */
	void placerPalleUD(MellemlagerPlads placering) {
		this.mellemlagerPlads = placering;
	}

	// ------ Associeriong mellem Palle og Dragering ------
	/**
	 * @return
	 * @author Mads Dahl Jensen
	 */
	public Drageringshal getDrageringshal() {
		return drageringshal;
	}

	/**
	 * @param drageringshal
	 * @author Mads Dahl Jensen
	 */
	void setDrageringshalUD(Drageringshal drageringshal) {
		this.drageringshal = drageringshal;
	}

	/**
	 * @param drageringshal
	 * @author Mads Dahl Jensen + Rita Holst Jacobsen
	 */
	public void setDrageringshal(Drageringshal drageringshal) {
		if (drageringshal != null) {
			this.setDrageringshalUD(drageringshal);
			drageringshal.addPalleUD(this);
		} else {
			if (this.getDrageringshal() != null) {
				this.getDrageringshal().removePalleUD(this);
			}
			this.setDrageringshalUD(null);
		}
	}

	// ------- Associering mellem Palle og Mellemvare

	/**
	 * @return
	 * @author Mads Dahl Jensen
	 */
	public ArrayList<Mellemvare> getMellemvarer() {
		return new ArrayList<Mellemvare>(mellemvarer);
	}

	/**
	 * @param mellemvare
	 * @author Mads Dahl Jensen
	 */
	void addMellemvareUD(Mellemvare mellemvare) {
		mellemvarer.add(mellemvare);
	}

	/**
	 * @param mellemvare
	 * @author Mads Dahl Jensen
	 */
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
	 * Finder mellemvarer med den angivne produkttype og delbehandling på
	 * pallen, og starter næste delbehandling for de mellemvarer, hvor påbegyndelse af
	 * næste delbehandling er tilladt.
	 * 
	 * @param produkttype
	 * @param delbehandling Den igangværende delbehandling
	 * @param delbehandlingsType DelbehandlingsType for den Delbehandling, der ønskes igangsat
	 * @return
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public ArrayList<Mellemvare> startDelbehandling(Produkttype produkttype, Delbehandling delbehandling, 
			DelbehandlingsType delbehandlingsType) {
		ArrayList<Mellemvare> behandledeMellemvarer = new ArrayList<Mellemvare>();
		for (Mellemvare m : mellemvarer) {
			if (m.erAfSammeType(produkttype, delbehandling) && m.naesteDelbehandlingGyldig(delbehandlingsType)){
				m.goToNextDelbehandling();
				behandledeMellemvarer.add(m);
			}
		}
		return behandledeMellemvarer;
	}

	/**
	 * Starter næste delbehandling af en eller flere mellemvarer.
	 * 
	 * @param mellemvare
	 *            Hvis null, og alle mellemvarer på pallen er af samme type, startes næste delbehandling 
	 *            for alle mellemvarer på pallen. Hvis forskellig fra null startes næste delbehandling 
	 *            kun for den pågældende mellemvare, så fremt denne handling er gyldig.
	 *            
	 * @author Rita Holst Jacobsen
	 */
	public ArrayList<Mellemvare> startDelbehandling(Mellemvare mellemvare,
			DelbehandlingsType delbehandlingsType) {
		ArrayList<Mellemvare> behandledeMellemvarer = new ArrayList<Mellemvare>();
		// Hvis der ikke er angivet en mellemvare, skal alle på pallen være ens
		// (og opfylde de andre betingelser)
		if (mellemvare == null) {
			if (this.alleVarerErEns()) {
				if (this.mellemvarer.get(0).naesteDelbehandlingGyldig(
						delbehandlingsType)) {
					for (Mellemvare m : mellemvarer) {
						m.goToNextDelbehandling();
						behandledeMellemvarer.add(m);
					}
				}
			}
		}

		// Ellers startes næste delbehandling kun for den angivne mellemvare
		else {
			if (mellemvare.naesteDelbehandlingGyldig(delbehandlingsType)) {
				mellemvare.goToNextDelbehandling();
				behandledeMellemvarer.add(mellemvare);
			}
		}
		return behandledeMellemvarer;
	}

	/**
	 * Sender en eller flere mellemvarer af angivne type til færdigvarelager. Det er op til Service
	 * at fjerne associationen mellem palle og 'behandlede' mellemvarer.
	 * 
	 * @param produkttype
	 * @param delbehandling
	 * @return De mellemvarer, der er blevet 'behandlet', dvs. sendt til færdigvarelageret.
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public ArrayList<Mellemvare> sendTilFaerdigvareLager(
			Produkttype produkttype, Delbehandling delbehandling) {
		ArrayList<Mellemvare> behandledeMellemvarer = new ArrayList<Mellemvare>();
		for (Mellemvare m : mellemvarer) {
			if (m.erAfSammeType(produkttype, delbehandling)
					&& m.naesteDelbehandlingGyldig(null)) {
				m.setIgangvaerendeDelbehandling(null);
				m.setStatus(MellemvareStatus.FAERDIG);
				m.addNuvaerendeTidspunkt();
				behandledeMellemvarer.add(m);
			}
		}
		return behandledeMellemvarer;
	}

	/**
	 * Sender en, ingen eller alle mellemvarer til færdigvarelageret og markerer deres
	 * status som færdig. Hvis alle ønskes behandlet, sker dette kun, hvis alle varer 
	 * på pallen er ens.
	 * 
	 * @param mellemvare Hvis null ønskes alle varer 'behandlet'. Ellers kun den pågældende.
	 * @return De mellemvarer, der er blevet 'behandlet', dvs. sendt til færdigvarelageret.
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public ArrayList<Mellemvare> sendTilFaerdigvareLager(Mellemvare mellemvare) {
		ArrayList<Mellemvare> behandledeMellemvarer = new ArrayList<Mellemvare>();
		// Hvis der ikke er angivet en mellemvare, skal alle på pallen være ens
		if (mellemvare == null && this.alleVarerErEns()) {
			for (Mellemvare m : mellemvarer) {
				if (m.naesteDelbehandlingGyldig(null)) {
					m.faerdig();
				}
			}
		}
		// Ellers sendes kun den angivne mellemvare til færdigvarelager
		else {
			mellemvare.faerdig();
		}
		return behandledeMellemvarer;
	}

	/**
	 * Kasserer en eller flere mellemvarer af angivne type. Det er op til Service
	 * at fjerne associationen mellem palle og 'behandlede' mellemvarer.
	 * 
	 * @param produkttype
	 * @param delbehandling
	 * @return De mellemvarer, der er blevet 'behandlet', dvs. kasseret.
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public ArrayList<Mellemvare> kasserMellemvarer(Produkttype produkttype, Delbehandling delbehandling) {
		ArrayList<Mellemvare> behandledeMellemvarer = new ArrayList<Mellemvare>();
		for (Mellemvare m : mellemvarer) {
			if (m.erAfSammeType(produkttype, delbehandling)) {
				m.kasser();
				behandledeMellemvarer.add(m);
			}
		}
		return behandledeMellemvarer;
	}

	/**
	 * Kasserer en eller alle mellemvarer, uanset type
	 * 
	 * @param mellemvare
	 *            Hvis null: Alle mellemvarer kasseres! Ellers kasseres kun den
	 *            angivne mellemvare.
	 *            
	 * @author Rita Holst Jacobsen
	 */
	public ArrayList<Mellemvare> kasserMellemvarer(Mellemvare mellemvare) {
		ArrayList<Mellemvare> behandledeMellemvarer = new ArrayList<Mellemvare>();
		if (mellemvare == null) {
			for (Mellemvare m : mellemvarer) {
				m.kasser();
				behandledeMellemvarer.add(m);
			}
		} else {
			mellemvare.kasser();
			behandledeMellemvarer.add(mellemvare);
		}
		return behandledeMellemvarer;

	}

	/**
	 * Tjekker om alle mellemvarer på pallen er 'ens' - altså tilhører samme
	 * produkttype og er lige langt i behandlingen. Det er ikke nok at tjekke på
	 * én af disse egenskaber, da flere produkttyper kan dele samme behandling
	 * men ikke må blandes sammen og mellemvarer af samme produkttype kan være forskellige
	 * steder i processen. Der tjekkes ikke på tid, bl.a. fordi der uundgåeligt vil være
	 * lidt forskel på registreringstidspunkterne for de enkelte mellemvarer selvom de er
	 * produceret 'samtidig'.
	 * 
	 * @return Om alle varer er ens.
	 * 
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
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public ArrayList<Mellemvare> getMellemvarerAfSammeType(Mellemvare mellemvare) {
		return getMellemvarerAfSammeType(mellemvare.getProdukttype(),
				mellemvare.getIgangvaerendeDelbehandling());
	}

	/**
	 * @param produkttype
	 * @param delbehandling
	 * @return Alle mellemvarer af produkttypen produkttype, hvis igangværendeDelbehandling er delbehandling
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public ArrayList<Mellemvare> getMellemvarerAfSammeType(Produkttype produkttype, Delbehandling delbehandling){
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
	 * Benyttes af Service.generateViewData(Palle palle).
	 * 
	 * Returnerer en mapping mellem 'mellemvaretype' og antal, hvor 'mellemvaretype' skal forstås
	 * som en bestemt kombination af produkttype og igangværende delbehandling. 
	 * Mellemvaren der bruges som key er blot den første af sin type i pallens liste af mellemvarer
	 * og udnyttes blot som en bekvem repræsentation af den pågældende produkttype-delbehandlingskombination.
	 * 
	 * @return Et HashMap med 'repræsentative mellemvarer' som keys og antal af den samme type som values.
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public HashMap<Mellemvare, Integer> getMellemvareAntalMapping() {
		HashMap<Produkttype, Delbehandling> optaltePDpar = new HashMap<Produkttype, Delbehandling>();	
		HashMap<Mellemvare, Integer> ensMellemvareAntalMapping = new HashMap<Mellemvare, Integer>();
		for (Mellemvare m : mellemvarer) {
			if (!(optaltePDpar.containsKey(m.getProdukttype()) && optaltePDpar 
					.get(m.getProdukttype()) == m	//<-- Hovsa - hverken delbehandling eller produkttype er jo unikke identifiers for par af samme!!
					.getIgangvaerendeDelbehandling())) {
				optaltePDpar.put(m.getProdukttype(),
						m.getIgangvaerendeDelbehandling());
				ensMellemvareAntalMapping.put(m, getAntalAfSammeType(m));
			}
		}
		return ensMellemvareAntalMapping;
	}

	public HashMap<Mellemvare, Integer> getMellemvareAntalMappingKORREKT() {
		HashSet<Mellemvare> mellemvareSet = new HashSet<Mellemvare>(getMellemvarer());
		HashMap<Mellemvare, Integer> ensMellemvareAntalMapping = new HashMap<Mellemvare, Integer>();
		while (!mellemvareSet.isEmpty()){
			Mellemvare m = mellemvareSet.iterator().next();
			ArrayList<Mellemvare> ensMellemvarer = getMellemvarerAfSammeType(m);
			ensMellemvareAntalMapping.put(m, ensMellemvarer.size());
			mellemvareSet.removeAll(ensMellemvarer);
		}
		return ensMellemvareAntalMapping;
	}




	/**
	 * Returnerer om alle eller en delmængde af mellemvarerne på pallen er klar
	 * til en given handling (næste delbehandling eller færdigvarelageret) Hvis
	 * både @produkttype og @delbehandling er forskellige fra null, returneres
	 * om delmængde er klar. Ellers om alle er klar.
	 * 
	 * @param produkttype
	 *            Hvis null returneres om alle på pallen er klar
	 * @param delbehandling
	 *            Hvis null returneres om alle på pallen er klar.
	 * @param naesteDelbehandlingsType
	 *            Den handling, der spørges til. Kan være hhv. Dragering,
	 *            Tørring og Færdigvarelager (null)
	 *            
	 * @return om alle/en delmængde er klar til næste (be)handling
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public boolean naesteDelbehandlingGyldig(Produkttype produkttype,
			Delbehandling delbehandling,
			DelbehandlingsType naesteDelbehandlingsType) {
		boolean gyldig = false;
		if (getMellemvarer().size() == 0) {
			gyldig = false;
		} else {
			ArrayList<Mellemvare> aktuelleMellemvarer = new ArrayList<Mellemvare>();
			// Hvis både produkttype og delbehandling er kendt, returneres kun om produkter der matcher _disse_ 
			//egenskaber er klar til næste delbehandling/færdigvarelager
			if (produkttype != null && delbehandling != null) { 
				aktuelleMellemvarer = getMellemvarerAfSammeType(produkttype,
						delbehandling);
			} else if (alleVarerErEns()) { 	// Hvis produkttype og/eller delbehandling derimod er ukendt
				// skal alle mellemvarer på pallen være ens
				aktuelleMellemvarer = getMellemvarer();
			}
			if (aktuelleMellemvarer.size() > 0) {
				gyldig = true;
				for (Mellemvare m : aktuelleMellemvarer) {
					// OG klar til næste delbehandling/færdigvarelager
					if (!m.naesteDelbehandlingGyldig(naesteDelbehandlingsType)) { 
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

	/**
	 * Benyttes via Service af SubFramePalleOversigt til at vise, hvor en palle befinder sig.
	 * 
	 * Her ses, at en palle, der stadig har mellemvarer knyttet til sig, men hverken er placeret i
	 * drageringshallen eller på mellemvarelageret, pr. definition befinder sig på færdigvarelageret.
	 * En palle kan ikke være kasseret, men kan godt være 'ikke i brug'.
	 * 
	 * @return String-repræsentation af hvor pallen befinder sig.
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public String getPlaceringsString() {
		String placeringsString = "";
		if (getPlacering() != null) {
			placeringsString += "Mellemlager\n#" + getPlacering();
		} else if (getDrageringshal() != null) {
			placeringsString += "Drageringshal\n";
		} else if (getMellemvarer().size() > 0) {
			placeringsString += "Færdigvarelager\n";
		} else {
			placeringsString += "Ikke i brug\n";
		}
		return placeringsString;
	}

}
