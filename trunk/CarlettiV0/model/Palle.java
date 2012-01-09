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
 * Klassen repr�senterer en palle, der kan have en eller flere mellemvarer knyttet til sig, og desuden 
 * bev�ge sig rundt i produktionen mellem drageringshal, mellemvarelager og f�rdigvarelager. En palle kan
 * betragtes som en aggregering af mellemvarer, og flere operationer, der kan udf�res p� Mellemvare, kan
 * ogs� udf�res p�/via Palle. Det er placeringen af pallen, der afg�r hvor en mellemvare befinder sig (p� 
 * trods af det paradoksale i at en mellemvare under dragering dermed kan findes p� mellemvarelageret).
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
	 * Finder mellemvarer med den angivne produkttype og delbehandling p�
	 * pallen, og starter n�ste delbehandling for de mellemvarer, hvor p�begyndelse af
	 * n�ste delbehandling er tilladt.
	 * 
	 * @param produkttype
	 * @param delbehandling Den igangv�rende delbehandling
	 * @param delbehandlingsType DelbehandlingsType for den Delbehandling, der �nskes igangsat
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
	 * Starter n�ste delbehandling af en eller flere mellemvarer.
	 * 
	 * @param mellemvare
	 *            Hvis null, og alle mellemvarer p� pallen er af samme type, startes n�ste delbehandling 
	 *            for alle mellemvarer p� pallen. Hvis forskellig fra null startes n�ste delbehandling 
	 *            kun for den p�g�ldende mellemvare, s� fremt denne handling er gyldig.
	 *            
	 * @author Rita Holst Jacobsen
	 */
	public ArrayList<Mellemvare> startDelbehandling(Mellemvare mellemvare,
			DelbehandlingsType delbehandlingsType) {
		ArrayList<Mellemvare> behandledeMellemvarer = new ArrayList<Mellemvare>();
		// Hvis der ikke er angivet en mellemvare, skal alle p� pallen v�re ens
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

		// Ellers startes n�ste delbehandling kun for den angivne mellemvare
		else {
			if (mellemvare.naesteDelbehandlingGyldig(delbehandlingsType)) {
				mellemvare.goToNextDelbehandling();
				behandledeMellemvarer.add(mellemvare);
			}
		}
		return behandledeMellemvarer;
	}

	/**
	 * Sender en eller flere mellemvarer af angivne type til f�rdigvarelager. Det er op til Service
	 * at fjerne associationen mellem palle og 'behandlede' mellemvarer.
	 * 
	 * @param produkttype
	 * @param delbehandling
	 * @return De mellemvarer, der er blevet 'behandlet', dvs. sendt til f�rdigvarelageret.
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
	 * Sender en, ingen eller alle mellemvarer til f�rdigvarelageret og markerer deres
	 * status som f�rdig. Hvis alle �nskes behandlet, sker dette kun, hvis alle varer 
	 * p� pallen er ens.
	 * 
	 * @param mellemvare Hvis null �nskes alle varer 'behandlet'. Ellers kun den p�g�ldende.
	 * @return De mellemvarer, der er blevet 'behandlet', dvs. sendt til f�rdigvarelageret.
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public ArrayList<Mellemvare> sendTilFaerdigvareLager(Mellemvare mellemvare) {
		ArrayList<Mellemvare> behandledeMellemvarer = new ArrayList<Mellemvare>();
		// Hvis der ikke er angivet en mellemvare, skal alle p� pallen v�re ens
		if (mellemvare == null && this.alleVarerErEns()) {
			for (Mellemvare m : mellemvarer) {
				if (m.naesteDelbehandlingGyldig(null)) {
					m.faerdig();
				}
			}
		}
		// Ellers sendes kun den angivne mellemvare til f�rdigvarelager
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
	 * Tjekker om alle mellemvarer p� pallen er 'ens' - alts� tilh�rer samme
	 * produkttype og er lige langt i behandlingen. Det er ikke nok at tjekke p�
	 * �n af disse egenskaber, da flere produkttyper kan dele samme behandling
	 * men ikke m� blandes sammen og mellemvarer af samme produkttype kan v�re forskellige
	 * steder i processen. Der tjekkes ikke p� tid, bl.a. fordi der uundg�eligt vil v�re
	 * lidt forskel p� registreringstidspunkterne for de enkelte mellemvarer selvom de er
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
	 * @return Alle mellemvarer af produkttypen produkttype, hvis igangv�rendeDelbehandling er delbehandling
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
	 * Returnerer en mapping mellem 'mellemvaretype' og antal, hvor 'mellemvaretype' skal forst�s
	 * som en bestemt kombination af produkttype og igangv�rende delbehandling. 
	 * Mellemvaren der bruges som key er blot den f�rste af sin type i pallens liste af mellemvarer
	 * og udnyttes blot som en bekvem repr�sentation af den p�g�ldende produkttype-delbehandlingskombination.
	 * 
	 * @return Et HashMap med 'repr�sentative mellemvarer' som keys og antal af den samme type som values.
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
	 * Returnerer om alle eller en delm�ngde af mellemvarerne p� pallen er klar
	 * til en given handling (n�ste delbehandling eller f�rdigvarelageret) Hvis
	 * b�de @produkttype og @delbehandling er forskellige fra null, returneres
	 * om delm�ngde er klar. Ellers om alle er klar.
	 * 
	 * @param produkttype
	 *            Hvis null returneres om alle p� pallen er klar
	 * @param delbehandling
	 *            Hvis null returneres om alle p� pallen er klar.
	 * @param naesteDelbehandlingsType
	 *            Den handling, der sp�rges til. Kan v�re hhv. Dragering,
	 *            T�rring og F�rdigvarelager (null)
	 *            
	 * @return om alle/en delm�ngde er klar til n�ste (be)handling
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
			// Hvis b�de produkttype og delbehandling er kendt, returneres kun om produkter der matcher _disse_ 
			//egenskaber er klar til n�ste delbehandling/f�rdigvarelager
			if (produkttype != null && delbehandling != null) { 
				aktuelleMellemvarer = getMellemvarerAfSammeType(produkttype,
						delbehandling);
			} else if (alleVarerErEns()) { 	// Hvis produkttype og/eller delbehandling derimod er ukendt
				// skal alle mellemvarer p� pallen v�re ens
				aktuelleMellemvarer = getMellemvarer();
			}
			if (aktuelleMellemvarer.size() > 0) {
				gyldig = true;
				for (Mellemvare m : aktuelleMellemvarer) {
					// OG klar til n�ste delbehandling/f�rdigvarelager
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
	 * drageringshallen eller p� mellemvarelageret, pr. definition befinder sig p� f�rdigvarelageret.
	 * En palle kan ikke v�re kasseret, men kan godt v�re 'ikke i brug'.
	 * 
	 * @return String-repr�sentation af hvor pallen befinder sig.
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
			placeringsString += "F�rdigvarelager\n";
		} else {
			placeringsString += "Ikke i brug\n";
		}
		return placeringsString;
	}

}
