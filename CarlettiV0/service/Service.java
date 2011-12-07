package service;

import gui.MainFrame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import model.Behandling;
import model.Delbehandling;
import model.Delbehandling.DelbehandlingsType;
import model.Dragering;
import model.Drageringshal;
import model.MellemlagerPlads;
import model.Mellemvare;
import model.MellemvareStatus;
import model.Palle;
import model.Produkttype;
import model.Toerring;
import dao.DAO;
import dao.JpaDao;
import dao.ListDao;

/**
 * 
 * @author Rita Holst Jacobsen: hvor det er angivet
 * @author Rasmus Cederdorff: Singleton og hvor det er angivet
 *
 */
public class Service {
	private static Service uniqueInstance;
	private DAO dao = ListDao.getListDao();
	// private DAO dao = JpaDao.getDao();

	private boolean testMode;

	private Service() {
		this.setTestMode(false);
	}

	public static Service getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new Service();
		}
		return uniqueInstance;
	}

	/**
	 * Opretter en ny palle og gemmer den i databasen
	 * 
	 * @param stregkode
	 * @author Rasmus Cederdorff
	 */
	public Palle opretPalle(String stregkode) {
		Palle p = new Palle(stregkode);
		dao.gemPalle(p);
		return p;
	}

	/**
	 * Opretter og returnerer mellemvare
	 * 
	 * @param bakkestregkode
	 * @param produkttype
	 * @param palle
	 * @return
	 * @author Rasmus Cederdorff
	 */
	public Mellemvare opretMellemvare(String bakkestregkode,
			Produkttype produkttype, Palle palle) {
		Mellemvare m = new Mellemvare(bakkestregkode, produkttype, palle);
		palle.addMellemvare(m);
		dao.gemMellemvare(m);
		if (this.isTestMode()) {
			m.setTestMode(true);
		}
		return m;
	}

	/**
	 * Opretter ny produkttype
	 * 
	 * @param navn
	 * @param beskrivelse
	 * @param behandling
	 * @return
	 * @author Rasmus Cederdorff
	 */
	public Produkttype opretProdukttype(String navn, String beskrivelse,
			Behandling behandling) {
		Produkttype p = new Produkttype(navn, beskrivelse, behandling);
		dao.gemProdukttype(p);
		return p;
	}

	/**
	 * Opretter ny behandling
	 * 
	 * @param navn
	 * @return
	 * @author Rasmus Cederdorff
	 */
	public Behandling opretBehandling(String navn) {
		Behandling b = new Behandling(navn);
		dao.gemBehandling(b);
		return b;
	}

	/**
	 * Tilfoejer en delbehandling til en behandling Krav: Behandling skal
	 * eksistere i databasen
	 * 
	 * @param behandling
	 * @param delbandlingNavn
	 * @param delbandlingIndex
	 * @author Rita Holst Jacobsen
	 */
	public void tilfoejDelbehandling(Behandling b,
			Delbehandling nyDelbehandling, int delbehandlingIndex)
					throws IndexOutOfBoundsException {
		b.addDelbehandling(nyDelbehandling, delbehandlingIndex);
		opdaterDatabase();
	}

	/**
	 * Opretter ny mellemlagerPlads
	 * 
	 * @param stregkode
	 * @return
	 * @author Rasmus Cederdorff
	 */
	public MellemlagerPlads opretMellemlagerPlads(String stregkode) {
		MellemlagerPlads m = new MellemlagerPlads(stregkode);
		dao.gemMellemlagerPlads(m);
		return m;
	}

	/**
	 * Opretter en ny Delbehandling af typen Toerring og tilføjer den til
	 * Behandling b
	 * 
	 * @param navn
	 * @param b
	 * @param minTid
	 * @param idealTid
	 * @param maxTid
	 * @param index
	 *            . Placering i rækkefølgen af b's delbehandlinger. -1: tilføjes
	 *            sidst i listen
	 * @return
	 * @ Rita Holst Jacobsen
	 */
	public Delbehandling opretToerring(String navn, Behandling b, long minTid,
			long idealTid, long maxTid, int index) {
		Delbehandling d = new Toerring(navn, b, minTid, idealTid, maxTid);
		tilfoejDelbehandling(b, d, index);
		return d;
	}
	
	/**
	 * 
	 * @param navn
	 * @param b
	 * @param varighed
	 * @param index
	 * @return
	 * @author Rasmus Cederdorff
	 */
	public Delbehandling opretDragering(String navn, Behandling b,
			long varighed, int index) {
		Delbehandling d = new Dragering(navn, b, varighed);
		tilfoejDelbehandling(b, d, index);
		return d;
	}

	/**
	 * Placerer en palle på en placering i mellemvarelageret
	 * 
	 * @param palle
	 * @param placering
	 * @author Rasmus Cederdorff
	 */
	public void placerPalleMellemvarelager(Palle palle,
			MellemlagerPlads placering) {
		palle.placerPalle(placering);
		opdaterDatabase();

	}

	/**
	 * Returnerer true hvis alle varerne på en palle er ens
	 * @param palle
	 * @return
	 * @author Rita Holst Jacobsen
	 */
	public boolean alleVarerErEns(Palle palle) {
		return palle.alleVarerErEns();
	}

	/**
	 * @param produkttype
	 * @param delbehandling
	 * @param palle
	 * @param helePallen
	 * @param alleAfSammeType
	 * @author Rita Holst Jacobsen
	 */
	public void sendTilNaesteDelbehandling(Mellemvare mellemvare, Palle palle,
			DelbehandlingsType delbehandlingsType, Palle nyPalle,
			MellemlagerPlads nyMellemlagerPlads) {
		boolean gyldig = false;
		if (mellemvare == null) {
			gyldig = palle.naesteDelbehandlingGyldig(null, null,
					delbehandlingsType);
		} else {
			gyldig = mellemvare.naesteDelbehandlingGyldig(delbehandlingsType);
		}

		if (gyldig) {
			ArrayList<Mellemvare> behandledeVarer = palle.startDelbehandling(
					mellemvare, delbehandlingsType);
			if (behandledeVarer.size() == palle.getMellemvarer().size()) { // Hele
				// pallen
				// er
				// behandlet,
				// så
				// pallen
				// følger
				// med
				if (delbehandlingsType == DelbehandlingsType.DRAGERING) {
					sendPalleTilDragering(palle);
				} else if (delbehandlingsType == DelbehandlingsType.TOERRING) {
					palle.setDrageringshal(null);
					placerPalleMellemvarelager(palle, nyMellemlagerPlads);
				}
			} else if (mellemvare != null && nyPalle != null) { // Kun en
				// delmængde af
				// varerne er
				// blevet
				// behandlet, så
				// disse flyttes
				// til en ny
				// palle
				palle.removeMellemvare(mellemvare);
				nyPalle.addMellemvare(mellemvare);
				if (delbehandlingsType == DelbehandlingsType.DRAGERING) {
					sendPalleTilDragering(nyPalle);
				} else if (delbehandlingsType == DelbehandlingsType.TOERRING) {
					placerPalleMellemvarelager(nyPalle, nyMellemlagerPlads);
				}
			}
		}
	}

	/**
	 * @param produkttype
	 * @param delbehandling
	 * @param palle
	 * @param delbehandlingsType
	 * @param nyPalle Krav: forskellig fra null, hvis kun en delmængde af alle mellemvarer behandles
	 * @param nyMellemlagerPlads Krav: Skal være forskellig fra null hvis delbehandlingstypen er 
	 * Tørring og nyPalle er forskellige fra null
	 * @author Rita Holst Jacobsen
	 */
	public void sendTilNaesteDelbehandling(Produkttype produkttype,
			Delbehandling delbehandling, Palle palle,
			DelbehandlingsType delbehandlingsType, Palle nyPalle,
			MellemlagerPlads nyMellemlagerPlads) {
		boolean gyldig = palle.naesteDelbehandlingGyldig(produkttype,
				delbehandling, delbehandlingsType);
		if (gyldig) {
			ArrayList<Mellemvare> behandledeVarer = palle.startDelbehandling(
					produkttype, delbehandling, delbehandlingsType);

			if (behandledeVarer.size() == palle.getMellemvarer().size()) { // Hele
				// pallen
				// er
				// behandlet,
				// så
				// pallen
				// følger
				// med
				if (delbehandlingsType == DelbehandlingsType.DRAGERING) {
					sendPalleTilDragering(palle);
				} else if (delbehandlingsType == DelbehandlingsType.TOERRING) {
					placerPalleMellemvarelager(palle, nyMellemlagerPlads);
					palle.setDrageringshal(null);
				}
			}

			else { // Kun en delmængde af varerne er blevet behandlet, så disse
				// flyttes til en ny palle
				if (nyPalle != null) {
					for (Mellemvare m : behandledeVarer) {
						palle.removeMellemvare(m);
						nyPalle.addMellemvare(m);
					}
					if (nyMellemlagerPlads != null
							&& delbehandlingsType == DelbehandlingsType.TOERRING) { // Hvis
						// nyMellemlagerplads
						// er
						// null,
						// antages
						// at
						// den
						// nye
						// palle
						// allerede
						// er
						// placeret
						// på
						// mellemvarelageret
						placerPalleMellemvarelager(nyPalle, nyMellemlagerPlads);
						nyPalle.setDrageringshal(null);
					}

					else if (delbehandlingsType == DelbehandlingsType.DRAGERING) {
						sendPalleTilDragering(nyPalle);
					}
				}
			}
		}
	}

	/**
	 * @param mellemvare
	 * @param palle
	 * @author Rita Holst Jacobsen
	 */
	public void sendTilFaerdigvareLager(Mellemvare mellemvare, Palle palle,
			Palle nyPalle) {
		if (naesteDelbehandlingGyldig(mellemvare, null)) {
			ArrayList<Mellemvare> behandledeVarer = palle
					.sendTilFaerdigvareLager(mellemvare);

			if (behandledeVarer.size() == palle.getMellemvarer().size()) { // Hele
				// pallen
				// er
				// blevet
				// behandlet
				// og
				// flytter
				// med
				palle.placerPalle(null);
				palle.setDrageringshal(null);
			} else if (mellemvare != null) { // Kun en delmængde = én mellemvare
				// er blevet behandlet og denne
				// flyttes fra pallen til en
				// evt. ny palle
				palle.removeMellemvare(mellemvare);
				if (nyPalle != null) {
					nyPalle.addMellemvare(mellemvare);
					nyPalle.placerPalle(null);
					nyPalle.setDrageringshal(null);
				}
			}
		}
	}

	/**
	 * @param produkttype
	 * @param delbehandling
	 * @param palle
	 * @param delbehandlingsType
	 * @param nyPalle
	 * @author Rita Holst Jacobsen
	 */
	public void sendTilFaerdigvareLager(Produkttype produkttype,
			Delbehandling delbehandling, Palle palle, Palle nyPalle) {
		if (naesteDelbehandlingGyldig(palle, produkttype, delbehandling, null)) {

			ArrayList<Mellemvare> behandledeVarer = palle
					.sendTilFaerdigvareLager(produkttype, delbehandling);
			if (behandledeVarer.size() == palle.getMellemvarer().size()) { // Hele
				// pallen
				// er
				// blevet
				// behandlet
				// og
				// flytter
				// med
				palle.placerPalle(null);
				palle.setDrageringshal(null);
			} else { // Kun en delmængde af pallens mellemvarer er blevet
				// behandlet og disse flyttes fra pallen
				for (Mellemvare m : behandledeVarer) {
					palle.removeMellemvare(m);
					if (nyPalle != null) {
						nyPalle.addMellemvare(m);
						nyPalle.placerPalle(null);
						nyPalle.setDrageringshal(null);
					}
				}
			}
		}
	}

	/**
	 * @param mellemvare
	 *            Hvis null kasseres alle varer på pallen
	 * @param palle
	 * @author Rita Holst Jacobsen
	 */
	public void kasserMellemvarer(Mellemvare mellemvare, Palle palle) {
		ArrayList<Mellemvare> behandledeVarer = palle
				.kasserMellemvarer(mellemvare);
		// Hvis alle varer på pallen kasseres, skal pallen 'frigives' fra sin
		// hidtidige placering
		if (behandledeVarer.size() == palle.getMellemvarer().size()) {
			palle.placerPalle(null);
			palle.setDrageringshal(null);
		}
		for (Mellemvare m : behandledeVarer) {
			palle.removeMellemvare(m);
		}
	}

	/**
	 * @param produkttype
	 * @param delbehandling
	 * @param palle
	 * @author Rita Holst Jacobsen
	 */
	public void kasserMellemvarer(Produkttype produkttype,
			Delbehandling delbehandling, Palle palle) {
		ArrayList<Mellemvare> behandledeVarer = palle.kasserMellemvarer(
				produkttype, delbehandling);
		// Hvis alle varer på pallen kasseres, skal pallen 'frigives' fra sin
		// hidtidige placering
		if (behandledeVarer.size() == palle.getMellemvarer().size()) {
			palle.placerPalle(null);
			palle.setDrageringshal(null);
		}
		for (Mellemvare m : behandledeVarer) {
			palle.removeMellemvare(m);
		}
	}

	/**
	 * Sender en palle med mellemvarer til dragering
	 * 
	 * @param palle
	 * @author Rasmus Cederdorff
	 */
	public void sendPalleTilDragering(Palle palle) {
		palle.placerPalle(null);
		palle.setDrageringshal(Drageringshal.getInstance());
		opdaterDatabase();
	}

	/**
	 * Sletter en given palle fra databasen
	 * 
	 * @param palle
	 * @author Rasmus Cederdorff
	 */
	public void removePalle(Palle palle) {
		dao.removePalle(palle);
	}

	/**
	 * Sletter en given produkttype fra databasen
	 * 
	 * @param produkttype
	 * @author Rasmus Cederdorff
	 */
	public void removeProdukttype(Produkttype produkttype) {
		dao.removeProdukttype(produkttype);
	}

	/**
	 * Sletter en given plads på mellemvarelageret
	 * 
	 * @param mellemlagerPlads
	 * @author Rasmus Cederdorff
	 */
	public void removeMellemlagerPlads(MellemlagerPlads mellemlagerPlads) {
		dao.removeMellemlagerPlads(mellemlagerPlads);
	}

	/**
	 * Sletter en behandling fra databasen
	 * 
	 * @param behandling
	 * @author Rasmus Cederdorff
	 */
	public void removeBehandling(Behandling behandling) {
		dao.removeBehandling(behandling);
	}

	/**
	 * Returnerer en liste med alle mellemlagerpladserne
	 * 
	 * @return
	 * @author Rasmus Cederdorff
	 */
	public ArrayList<MellemlagerPlads> visOversigtOverMellemvarelager() {
		return new ArrayList<MellemlagerPlads>(dao.mellemlagerPladser());
	}

	/**
	 * Returnerer en liste med alle paller
	 * 
	 * @return
	 * @author Rasmus Cederdorff
	 */
	public ArrayList<Palle> getPaller() {
		return new ArrayList<Palle>(dao.paller());
	}

	/**
	 * Returnerer en liste med alle mellemlagerpladser
	 * 
	 * @return
	 * @author Rasmus Cederdorff
	 */
	public ArrayList<MellemlagerPlads> getPladser() {
		return new ArrayList<MellemlagerPlads>(dao.mellemlagerPladser());
	}

	/**
	 * Returnerer en liste med alle produkttyper
	 * 
	 * @return
	 * @author Rasmus Cederdorff
	 */
	public ArrayList<Produkttype> getProdukttyper() {
		return new ArrayList<Produkttype>(dao.produkttyper());
	}

	/**
	 * Returnerer en liste med alle mellemvarer
	 * 
	 * @return
	 * @author Rasmus Cederdorff
	 */
	public ArrayList<Mellemvare> getMellemvarer() {
		return new ArrayList<Mellemvare>(dao.mellemvarer());
	}

	/**
	 * Returnerer en liste med alle behandlinger
	 * 
	 * @return
	 * @author Rasmus Cederdorff
	 */
	public ArrayList<Behandling> getBehandlinger() {
		return new ArrayList<Behandling>(dao.behandlinger());
	}

	/**
	 * Returnerer en liste med alle færdigvarer
	 * 
	 * @return
	 * @author Rasmus Cederdorff
	 */
	public ArrayList<Mellemvare> getFaerdigvarer() {
		return new ArrayList<Mellemvare>(dao.faerdigvarer());
	}

	/**
	 * Returnerer en liste med alle kasserede varer
	 * 
	 * @return
	 * @author Rasmus Cederdorff
	 */
	public ArrayList<Mellemvare> getKasserede() {
		return new ArrayList<Mellemvare>(dao.kasseredeVarer());
	}

	/**
	 * Returnerer en liste med alle mellemvarer som er under behandling
	 * 
	 * @return
	 * @author Rasmus Cederdorff
	 */
	public ArrayList<Mellemvare> getVarerUnderBehandling() {
		return new ArrayList<Mellemvare>(dao.varerUnderBehandling());
	}
	/**
	 * 
	 * @param palle
	 * @return
	 * @author Rita Holst Jacobsen
	 */
	public String getStregkode(Palle palle) {
		return palle.getStregkode();
	}
	
	/**
	 * 
	 * @param mellemvare
	 * @return
	 * @author Rita Holst Jacobsen
	 */
	public String getStregkode(Mellemvare mellemvare) {
		return mellemvare.getBakkestregkode();
	}

	/**
	 * 
	 * @param mellemlagerPlads
	 * @return
	 * @author Rita Holst Jacobsen
	 */
	public String getStregkode(MellemlagerPlads mellemlagerPlads) {
		return mellemlagerPlads.getStregkode();
	}

	/**
	 * 
	 * @param palle
	 * @return
	 * @author Rita Holst Jacobsen
	 */
	public ArrayList<Mellemvare> getMellemvarer(Palle palle) {
		return palle.getMellemvarer();
	}
	/**
	 * 
	 * @param stregkode
	 * @return
	 * @author Mads Dahl Jensen
	 */
	public Palle soegPalle(String stregkode) {
		return dao.soegPalle(stregkode);
	}
	
	/**
	 * 
	 * @param stregkode
	 * @return
	 * @author Mads Dahl Jensen
	 */
	public MellemlagerPlads soegMellemlagerPlads(String stregkode) {
		return dao.soegMellemlagerPlads(stregkode);
	}
	
	/**
	 * 
	 * @param palle
	 * @param nyStregkode
	 * @author Rasmus Cederdorff
	 */
	public void redigerPalle(Palle palle, String nyStregkode) {
		palle.setStregkode(nyStregkode);
		opdaterDatabase();
	}
	/**
	 * 
	 * @param produkttype
	 * @param nyBeskrivelse
	 * @param nyBehandling
	 * @author Rasmus Cederdorff
	 */
	public void redigerProdukttype(Produkttype produkttype,
			String nyBeskrivelse, Behandling nyBehandling) {
		produkttype.setBeskrivelse(nyBeskrivelse);
		produkttype.setBehandling(nyBehandling);
		opdaterDatabase();
	}

	/**
	 * Genererer data til brug for MainFrame - Dog ikke anvendt i den endelige
	 * version af systemet, da vi traf beslutning om at alle tre resterende
	 * tider skulle vises
	 * 
	 * Object[][] - Opsummering af mellemlagerets indhold i form af:
	 * {@link MellemlagerPlads}, {@link Palle}, {@link Produkttype},
	 * {@link Delbehandling}, Antal, Resterende tid
	 * 
	 * Hvis der er flere "varetyper" på samme Palle, udskrives en linie pr.
	 * "varetype" (dvs. kombinationen Produkttype, Delbehandling)
	 * 
	 * Er der ikke tilknyttet en {@link Palle} til en givet
	 * {@link MellemlagerPlads}, repræsenteres denne med et Object[] pData, hvor
	 * pData[0] er den pågældende {@link MellemlagerPlads}, og pData[1]-[5] er
	 * null. Det er så op til {@link MainFrame}-klassen at håndtere, om
	 * oversigten skal vises med eller uden tomme pladser.
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public Object[][] generateViewDataMellemlagerOversigt() {
		// Data gemmes i første omgang i en ArrayList, da størrelsen af data
		// afhænger af
		// hvor mange forskellige typer mellemvarer der findes på hver enkelt
		// palle.

		ArrayList<Object[]> listData = new ArrayList<Object[]>();
		for (MellemlagerPlads mp : getPladser()) {
			Object[] pladsData = new Object[6];
			pladsData[0] = mp;
			if (mp.getPalle() == null) { // Tom mellemlagerplads
				listData.add(pladsData);
			} else { // Plads med palle
				Palle palle = mp.getPalle();
				if (palle.getMellemvarer().size() == 0) { // Tom palle - men
					// skal stadig
					// vises!
					pladsData[1] = palle;
					listData.add(pladsData);
				} else { // Pallen indeholder mellemvarer, og der skal vises en
					// række pr. 'type'
					Object[][] fullPalleData = generateViewDataPalle(palle,
							false); // <--- false: Kun én resterende tid vises
					for (int i = 0; i < fullPalleData.length; i++) {
						Object[] mellemvareData = new Object[6];
						mellemvareData[0] = mp; // Mellemlagerplads
						mellemvareData[1] = fullPalleData[i][0]; // Palle
						mellemvareData[2] = fullPalleData[i][1]; // Produkttype
						mellemvareData[3] = fullPalleData[i][2]; // Delbehandling
						mellemvareData[4] = fullPalleData[i][3]; // Antal
						mellemvareData[5] = fullPalleData[i][5]; // Resterende
						// tid til
						// næste
						// tidsfrist
						listData.add(mellemvareData);
					}
				}
			}
		}
		Object[][] data = new Object[listData.size()][6];
		int i = 0;
		for (Object[] pladsDataArray : listData) {
			data[i] = pladsDataArray;
			i++;
		}
		return data;
	}

	/**
	 * Genererer data til brug for MainFrame
	 * 
	 * Object[][] - Opsummering af mellemlagerets indhold i form af:
	 * {@link MellemlagerPlads}, {@link Palle}, {@link Produkttype},
	 * {@link Delbehandling}, Antal, Resterende tid til minTid, resterende tid
	 * til idealTid, resterende tid til maxTid
	 * 
	 * Hvis der er flere "varetyper" på samme Palle, udskrives en linie pr.
	 * "varetype" (dvs. kombinationen Produkttype, Delbehandling)
	 * 
	 * Er der ikke tilknyttet en {@link Palle} til en givet
	 * {@link MellemlagerPlads}, repræsenteres denne med et Object[] pData, hvor
	 * pData[0] er den pågældende {@link MellemlagerPlads}, og pData[1]-[7] er
	 * null. Det er så op til {@link MainFrame}-klassen at håndtere, om
	 * oversigten skal vises med eller uden tomme pladser.
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public Object[][] generateViewDataMellemlagerOversigt3Tider() {
		// Data gemmes i første omgang i en ArrayList, da størrelsen af data
		// afhænger af
		// hvor mange forskellige typer mellemvarer der findes på hver enkelt
		// palle.
		ArrayList<Object[]> listData = new ArrayList<Object[]>();
		for (MellemlagerPlads mp : getPladser()) {
			Object[] pladsData = new Object[6];
			pladsData[0] = mp;
			if (mp.getPalle() == null) { // Tom mellemlagerplads
				listData.add(pladsData);
			} else { // Plads med palle
				Palle palle = mp.getPalle();
				if (palle.getMellemvarer().size() == 0) { // Tom palle - men
					// skal stadig
					// vises!
					pladsData[1] = palle;
					listData.add(pladsData);
				} else { // Pallen indeholder mellemvarer, og der skal vises en
					// række pr. 'type'
					Object[][] fullPalleData = generateViewDataPalle(palle,
							false); // <--- false: Kun én resterende tid vises
					for (int i = 0; i < fullPalleData.length; i++) {
						Object[] mellemvareData = new Object[8];
						mellemvareData[0] = mp; // Mellemlagerplads
						mellemvareData[1] = fullPalleData[i][0]; // Palle
						mellemvareData[2] = fullPalleData[i][1]; // Produkttype
						mellemvareData[3] = fullPalleData[i][2]; // Delbehandling
						mellemvareData[4] = fullPalleData[i][3]; // Antal
						mellemvareData[5] = fullPalleData[i][4]; // Resterende
						// tid til
						// minTid
						mellemvareData[6] = fullPalleData[i][5]; // Resterende
						// tid til
						// idealTid
						mellemvareData[7] = fullPalleData[i][6]; // Resterende
						// tid til
						// maxTid
						listData.add(mellemvareData);
					}
				}
			}
		}
		Object[][] data = new Object[listData.size()][8];
		int i = 0;
		for (Object[] pladsDataArray : listData) {
			data[i] = pladsDataArray;
			i++;
		}
		return data;
	}

	/**
	 * Genererer data til visning i SubFrameDrageringshalOversigt
	 * 
	 * @author Rasmus Cederdorff og Rita Holst Jacobsen
	 */

	public Object[][] generateViewDataDrageringshal() {
		// Data gemmes i første omgang i en ArrayList, da størrelsen af
		// dataafhænger af
		// hvor mange forskellige typer mellemvarer der findes på hver enkelt
		// palle.

		ArrayList<Object[]> listData = new ArrayList<Object[]>();
		ArrayList<Palle> paller = Drageringshal.getInstance().getPaller();

		for (Palle palle : paller) {
			Object[] palleData = new Object[5];
			palleData[0] = palle;

			if (palle.getMellemvarer().size() == 0) {
				listData.add(palleData);
			} else { // Pallen indeholder mellemvarer, og der skal vises en
				// række pr. 'type'
				Object[][] fullPalleData = generateViewDataPalle(palle, false); // <---
				// false:
				// Kun
				// én
				// resterende
				// tid
				// vises
				for (int i = 0; i < fullPalleData.length; i++) {
					Object[] mellemvareData = new Object[5];
					mellemvareData[0] = fullPalleData[i][0]; // Palle
					mellemvareData[1] = fullPalleData[i][1]; // Produkttype
					mellemvareData[2] = fullPalleData[i][2]; // Delbehandling
					mellemvareData[3] = fullPalleData[i][3]; // Antal
					mellemvareData[4] = fullPalleData[i][5]; // Resterende tid
					// til næste
					// tidsfrist
					listData.add(mellemvareData);
				}
			}
		}

		Object[][] data = new Object[listData.size()][5];
		int i = 0;
		for (Object[] pladsDataArray : listData) {
			data[i] = pladsDataArray;
			i++;
		}
		return data;
	}

	/**
	 * Genererer/strukturerer data til brug for SubFramePalleOversigt (via
	 * generateViewDataPalle())
	 * 
	 * @param palle
	 * @return Object[][] - Opsummering af pallens indhold i form af: Palle,
	 *         Produkttype, Delbehandling (igangværende), antal af denne
	 *         kombination på pallen og resterende tid til _næste_ tidsfrist for
	 *         samme.
	 * @author Rita Holst Jacobsen
	 */
	public Object[][] generateViewDataProdukttypeDelbehandlingAntalTid(
			Palle palle) {
		Object[][] fullData = generateViewDataPalle(palle, true);
		Object[][] oversigtsData;
		if (fullData.length > 0) {
			oversigtsData = new Object[fullData.length][4];
			for (int i = 0; i < fullData.length; i++) {
				Object[] mData = new Object[4];
				mData[0] = fullData[i][1]; // Produkttype
				mData[1] = fullData[i][2]; // Delbehandling
				mData[2] = fullData[i][3]; // Antal
				mData[3] = fullData[i][5]; // Tid (resterende tid til næste
				// tidsfrist)
				oversigtsData[i] = mData;
			}
		} else {
			oversigtsData = new Object[0][4];
		}
		return oversigtsData;
	}
	/**
	 * Genererer data til oversigten over kasserede varer
	 * @return
	 */
	public Object[][] generateViewDataKasseredeVarer() {
		ArrayList<Mellemvare> kasseredeVarer = getKasserede();
		Object[][] data = new Object[kasseredeVarer.size()][3];
		if (kasseredeVarer.size() > 0) {
			for (int i = 0; i < kasseredeVarer.size(); i++) {
				data[i][0] = kasseredeVarer.get(i).getBakkestregkode();
				data[i][1] = kasseredeVarer.get(i).getProdukttype();
				data[i][2] = kasseredeVarer.get(i).getStatus();

			}
		}

		return data;
	}

	/**
	 * Genererer data til oversigten over faerdigvarer
	 * 
	 * @return
	 * @author Rasmus Cederdorff
	 */
	public Object[][] generateViewFaerdigvarer() {
		ArrayList<Mellemvare> faerdigvarer = getFaerdigvarer();
		Object[][] data = new Object[faerdigvarer.size()][3];
		if (faerdigvarer.size() > 0) {
			for (int i = 0; i < faerdigvarer.size(); i++) {
				data[i][0] = faerdigvarer.get(i).getBakkestregkode();
				data[i][1] = faerdigvarer.get(i).getProdukttype();
				data[i][2] = faerdigvarer.get(i).getStatus();
			}
		}

		return data;
	}

	/**
	 * @param palle
	 *            For hver kombination af produkttype og delbehandling på en
	 *            palle returneres et array med al information om mellemvarerne.
	 * @param kunNaesteTidsfrist
	 *            Hvis true vil søjle 5 vise resterende tid til næste tidsfrist,
	 *            uanset om der er tale om min-, max-, ideal-tid eller
	 *            simpelthen varighed (hvis dragering) mens søjle 4 og 6 vil
	 *            være tomme. Hvis false vil søjle 4-5-6 vise hhv. [min-, ideal-
	 *            og max-tid] for tørring, vise [null, varighed og null] for
	 *            dragering, og [null, null og null] for færdige og kasserede
	 *            varer.
	 * @return [Palle, Produkttype, Igangværende Delbehandling, Antal,
	 *         Resterende tid, Resterende tid, Resterende tid (se
	 * @kunNaesteTidsfrist)]
	 */
	public Object[][] generateViewDataPalle(Palle palle,
			boolean kunNaesteTidsfrist) {
		Object[][] data;
		if (palle != null && palle.getMellemvarer().size() > 0) {
			HashMap<Mellemvare, Integer> mellemvareAntal = palle
					.getMellemvareAntalMapping();
			data = new Object[mellemvareAntal.size()][7];

			int i = 0;
			for (Mellemvare m : mellemvareAntal.keySet()) {
				Object[] mData = new Object[7];
				mData[0] = palle;
				mData[1] = m.getProdukttype();
				mData[2] = m.getIgangvaerendeDelbehandling();
				mData[3] = mellemvareAntal.get(m);
				mData[4] = "-";
				mData[5] = "-";
				mData[6] = "-";
				long[] tider = m.getResterendeTider();
				if (tider.length == 1) {
					mData[5] = Validering
							.millisekunderTilVarighedString(tider[0]);
				} else if (tider.length == 3) {
					mData[4] = Validering
							.millisekunderTilVarighedString(tider[0]);
					mData[5] = Validering
							.millisekunderTilVarighedString(tider[1]);
					mData[6] = Validering
							.millisekunderTilVarighedString(tider[2]);
				}
				data[i] = mData;
				i++;
			}

		} else {
			data = new Object[0][7];
		}
		return data;
	}

	/**
	 * Benyttes af gui.SubFramePalleOversigt til at vise detaljerede
	 * informationer om individuelle bakker på en given palle
	 * 
	 * @param m
	 * @return
	 * @author Rita Holst Jacobsen
	 */
	public String getMellemvareInfo(Mellemvare m) {
		String infoString = "";
		if (m != null) {
			long[] tider = m.getResterendeTider();
			infoString = "#" + m.toString() + "\t"
					+ m.getIgangvaerendeDelbehandling() + "\n"
					+ "\nNæste delbehandling om:\n";
			for (int i = 0; i < tider.length; i++) {
				infoString += Validering
						.millisekunderTilVarighedString(tider[i]);
				if (i < tider.length - 1) {
					infoString += " /\t";
				}
			}
			infoString += "\n\nBehandlings-log:\n";
			ArrayList<GregorianCalendar> delbehandlingstider = m
					.getTidspunkter();
			ArrayList<Delbehandling> delbehandlinger = (ArrayList<Delbehandling>) m
					.getProdukttype().getBehandling().getDelbehandlinger();
			for (int i = 0; i < delbehandlingstider.size(); i++) {
				GregorianCalendar c = delbehandlingstider.get(i);
				infoString += Validering.calendarTilCalendarString(c) + "\t"
						+ delbehandlinger.get(i).toString() + "\n";
			}
		}
		return infoString;
	}

	/**
	 * Benyttes når databasen skal opdateres (kalder begin/commit)
	 * @author Rasmus Cederdorff
	 */
	public void opdaterDatabase() {
		dao.opdaterDatabase();
	}

	/**
	 * @deprecated Use
	 *             {@link #naesteDelbehandlingGyldig(Mellemvare,DelbehandlingsType)}
	 *             instead
	 * @author Rita Holst Jacobsen
	 */
	public boolean naesteBehandlingGyldig(Mellemvare m,
			DelbehandlingsType naesteDelbehandlingsType) {
		return naesteDelbehandlingGyldig(m, naesteDelbehandlingsType);
	}

	/**
	 * 
	 * @param m
	 * @param naesteDelbehandlingsType
	 * @return
	 * @author Rita Holst Jacobsen
	 */
	public boolean naesteDelbehandlingGyldig(Mellemvare m,
			DelbehandlingsType naesteDelbehandlingsType) {
		return m.naesteDelbehandlingGyldig(naesteDelbehandlingsType);
	}

	/**
	 * 
	 * @param palle
	 * @param produkttype
	 * @param delbehandling
	 * @param naesteDelbehandlingsType
	 * @return
	 * @author Rita Holst Jacobsen
	 */
	public boolean naesteDelbehandlingGyldig(Palle palle,
			Produkttype produkttype, Delbehandling delbehandling,
			DelbehandlingsType naesteDelbehandlingsType) {
		return palle.naesteDelbehandlingGyldig(produkttype, delbehandling,
				naesteDelbehandlingsType);
	}

	/**
	 * 
	 * @param palle
	 * @return
	 * @author Rita Holst Jacobsen
	 */
	public String getPallePlaceringsString(Palle palle) {
		return palle.getPlaceringsString();
	}

	/**
	 * 
	 * @param palle
	 * @return
	 * @author Rita Holst Jacobsen
	 */
	public boolean getPalleIkkeIBrug(Palle palle) {
		return (palle.getPlacering() == null
				&& palle.getDrageringshal() == null && palle.getMellemvarer()
				.size() == 0);
	}

	/**
	 * 
	 * @param palle
	 * @return
	 * @author Rita Holst Jacobsen
	 */
	public MellemlagerPlads getMellemlagerPlads(Palle palle) {
		return palle.getPlacering();
	}

	/**
	 * 
	 * @return
	 * @author Rita Holst Jacobsen
	 */
	public boolean isTestMode() {
		return testMode;
	}

	/**
	 * Testmode vil sige, at handlinger på mellemvarer betragtes som
	 * gyldige/ugyldige uafhængigt af om evt. tidsfrister er overholdt
	 * 
	 * @param testMode
	 * @author Rita Holst Jacobsen
	 */
	public void setTestMode(boolean testMode) {
		this.testMode = testMode;
		for (Mellemvare m : dao.mellemvarer()) {
			m.setTestMode(testMode);
		}
	}

}
