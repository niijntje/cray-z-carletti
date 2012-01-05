/**
 * SERVICE
 */
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
import model.Palle;
import model.Produkttype;
import model.Toerring;
import dao.DAO;
import dao.JpaDao;
import dao.ListDao;

/**
 * Klassen strukturerer al information, der skal vises af gui og delegerer operationer på model- og dao-
 * laget nedefter. 
 * 
 * @author Rasmus Cederdorff: Singleton og hvor det er angivet
 * @author Rita Holst Jacobsen: hvor det er angivet
 * 
 */

public class Service {
	private static Service uniqueInstance;
	private DAO dao = ListDao.getListDao();	//<-- Udkommenter den version af dao,
	//	 private DAO dao = JpaDao.getDao();		//<-- der ikke skal benyttes.

	private boolean testMode;

	/**
	 * @author Rasmus Cederdorff 
	 */
	private Service() {
		this.setTestMode(false);
	}

	/**
	 * Singleton
	 * @return En unik instans af Service.
	 * 
	 * @author Rasmus Cederdorff 
	 */
	public static Service getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new Service();
		}
		return uniqueInstance;
	}

	public boolean isTestMode() {
		return testMode;
	}

	/**
	 * Testmode vil sige, at handlinger på mellemvarer betragtes som
	 * gyldige/ugyldige uafhængigt af om evt. tidsfrister er overholdt
	 * 
	 * @param testMode
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public void setTestMode(boolean testMode) {
		this.testMode = testMode;
		for (Mellemvare m : dao.mellemvarer()) {
			m.setTestMode(testMode);
		}
	}

	/*----------Centrale operationer--------------*/

	/**
	 * Igangsætter næste delbehandling for en eller alle varer på en given palle, hvis de opfylder
	 * betingelserne for at kunne gå videre til næste delbehandling
	 * 
	 * @param mellemvare	Hvis null betyder det at alle varer på palle ønskes behandlet
	 * @param palle
	 * @param delbehandlingsType
	 * @param nyPalle	Hvis mellemvare != null, skal nyPalle != null, da behandling af en delmængde som
	 * 					udgangspunkt fører til at denne flyttes til en ny palle.
	 * @param nyMellemlagerPlads Hvis palle, hhv. nyPalle ikke allerede er placeret på mellemvarelageret, bør
	 * 					nyMellemlagerPlads != null, men er den null, forhindrer det ikke at mellemvarerne
	 * 					behandles - de vil blot (i systemet) forblive på den samme palle, uanset dens 
	 * 					fysiske placering.
	 * 
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
			if (behandledeVarer.size() == palle.getMellemvarer().size()) { // Hele pallen er behandlet, så 
				//pallen følger med
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
	 *	Igangsætter næste delbehandling for en delmængde eller alle varer på en given palle, hvis de opfylder
	 * betingelserne for at kunne gå videre til næste delbehandling

	 * @param produkttype
	 * @param delbehandling
	 * @param palle
	 * @param delbehandlingsType
	 * @param nyPalle 
	 * 				Krav: forskellig fra null, hvis kun en delmængde af alle mellemvarer behandles
	 * @param nyMellemlagerPlads 
	 * 				Krav: Skal være forskellig fra null hvis delbehandlingstypen er Tørring og nyPalle 
	 * 				er forskellige fra null
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public void sendTilNaesteDelbehandling(Produkttype produkttype, Delbehandling delbehandling, Palle palle,
			DelbehandlingsType delbehandlingsType, Palle nyPalle, MellemlagerPlads nyMellemlagerPlads) {
		boolean gyldig = palle.naesteDelbehandlingGyldig(produkttype, delbehandling, delbehandlingsType);
		if (gyldig) {
			ArrayList<Mellemvare> behandledeVarer = palle.startDelbehandling(produkttype, delbehandling, 
					delbehandlingsType);

			if (behandledeVarer.size() == palle.getMellemvarer().size()) { // Hele pallen er behandlet, så 
				//pallen følger med
				if (delbehandlingsType == DelbehandlingsType.DRAGERING) {
					sendPalleTilDragering(palle);
				} else if (delbehandlingsType == DelbehandlingsType.TOERRING) {
					placerPalleMellemvarelager(palle, nyMellemlagerPlads);
					palle.setDrageringshal(null);
				}
			}

			else { 	// Kun en delmængde af varerne er blevet behandlet, så disse flyttes til en ny palle
				if (nyPalle != null) {
					for (Mellemvare m : behandledeVarer) {
						palle.removeMellemvare(m);
						nyPalle.addMellemvare(m);
					}
					// Hvis nyMellemlagerplads er null, antages at den nye palle allerede er placeret på mellemvarelageret
					if (nyMellemlagerPlads != null && delbehandlingsType == DelbehandlingsType.TOERRING) { 
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
	 * Sender en, ingen eller alle mellemvarer til færdigvarelageret og markerer deres
	 * status som færdig. Hvis alle ønskes behandlet, sker dette kun, hvis alle varer 
	 * på pallen er ens.
	 * 
	 * @param mellemvare Hvis null ønskes alle varer 'behandlet'. Ellers kun den pågældende.
	 * @param palle
	 * 
	 * @author Rita Holst Jacobsen
	 */

	public void sendTilFaerdigvareLager(Mellemvare mellemvare, Palle palle,
			Palle nyPalle) {
		if (naesteDelbehandlingGyldig(mellemvare, null)) {
			ArrayList<Mellemvare> behandledeVarer = palle
					.sendTilFaerdigvareLager(mellemvare);

			if (behandledeVarer.size() == palle.getMellemvarer().size()) { // Hele pallen er blevet behandlet 
				// og flytter med
				palle.placerPalle(null);
				palle.setDrageringshal(null);
			} else if (mellemvare != null) { // Kun en delmængde = én mellemvare er blevet behandlet og denne
				// flyttes fra pallen til en evt. ny palle (varer på færdigvarelageret
				// er ikke nødvendigvis tilknyttet en palle fra systemet
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
	 * Sender en eller flere mellemvarer af angivne type til færdigvarelager.
	 * Fjerner desuden associationen mellem palle og 'behandlede' mellemvarer, hvis disse kun udgør en
	 * delmængde af pallens mellemvarer.
	 * 
	 * @param produkttype
	 * @param delbehandling
	 * @param palle
	 * @param delbehandlingsType
	 * @param nyPalle
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public void sendTilFaerdigvareLager(Produkttype produkttype,
			Delbehandling delbehandling, Palle palle, Palle nyPalle) {
		if (naesteDelbehandlingGyldig(palle, produkttype, delbehandling, null)) {
			ArrayList<Mellemvare> behandledeVarer = palle.sendTilFaerdigvareLager(produkttype, 
					delbehandling);
			if (behandledeVarer.size() == palle.getMellemvarer().size()) { // Hele pallen er blevet behandlet 
				// og flytter med
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
	 * Kasserer en eller alle mellemvarer, på en given palle uanset type
	 * 
	 * @param mellemvare
	 *            Hvis null: Alle mellemvarer kasseres! Ellers kasseres kun den
	 *            angivne mellemvare.
	 * @param palle
	 *           
	 * @author Rita Holst Jacobsen
	 */
	public void kasserMellemvarer(Mellemvare mellemvare, Palle palle) {
		ArrayList<Mellemvare> behandledeVarer = palle
				.kasserMellemvarer(mellemvare);
		// Hvis alle varer på pallen kasseres, skal pallen 'frigives' fra sin hidtidige placering
		if (behandledeVarer.size() == palle.getMellemvarer().size()) {
			palle.placerPalle(null);
			palle.setDrageringshal(null);
		}
		for (Mellemvare m : behandledeVarer) {
			palle.removeMellemvare(m);
		}
	}

	/**
	 * Kasserer en eller flere mellemvarer af angivne type fra en palle. 
	 * Fjerner desuden associationen mellem palle og 'behandlede' mellemvarer.
	 * 
	 * @param produkttype
	 * @param delbehandling
	 * @param palle
	 * 
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

	/*--------------------Input-validering--------------------*/

	/**
	 * Returnerer om en given delbehandling/sending til færdigvarelageret er tilladt for mellemvaren
	 * 
	 * @param m
	 * @param naesteDelbehandlingsType Hvis null: Om mellemvaren må sendes til færdigvarelager
	 * @return
	 * @author Rita Holst Jacobsen
	 */
	public boolean naesteDelbehandlingGyldig(Mellemvare m, DelbehandlingsType naesteDelbehandlingsType) {
		return m.naesteDelbehandlingGyldig(naesteDelbehandlingsType);
	}

	/**
	 * Returnerer om en given delbehandling/sending til færdigvarelageret er tilladt for mellemvarer
	 * på pallen af den angivne produkttype og i gang med den angivne delbehandling. Tjekker for hver
	 * enkelt mellemvare, der opfylder udvælgelseskriteriet, om handlingen er lovlig.
	 * 
	 * @param palle
	 * @param produkttype
	 * @param delbehandling
	 * @param naesteDelbehandlingsType
	 * @return True hvis handlingen er lovlig for samtlige mellemvarer af produkttypen der er i gang
	 * 			med delbehandlingen
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public boolean naesteDelbehandlingGyldig(Palle palle,
			Produkttype produkttype, Delbehandling delbehandling,
			DelbehandlingsType naesteDelbehandlingsType) {
		return palle.naesteDelbehandlingGyldig(produkttype, delbehandling,
				naesteDelbehandlingsType);
	}

	/*--------Strukturering af data til brug for gui----------*/

	/**
	 * Genererer/strukturerer data til brug for generateViewDataMellemlagerOversigt3Tider(), 
	 * generateViewDataDrageringshal() og generateViewDataProdukttypeDelbehandlingAntalTid(),
	 * der alle benyttes af gui.
	 * 
	 * @param palle For hver kombination af produkttype og delbehandling på en
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
	 *         Resterende tid, Resterende tid, Resterende tid (se @kunNaesteTidsfrist)]
	 *         
	 * @author Rita Holst Jacobsen
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
					if (kunNaesteTidsfrist){
						mData[5] = Validering.millisekunderTilVarighedString(m.getResterendeTidTilNaeste());
					}
					else{
						mData[4] = Validering
								.millisekunderTilVarighedString(tider[0]);
						mData[5] = Validering
								.millisekunderTilVarighedString(tider[1]);
						mData[6] = Validering
								.millisekunderTilVarighedString(tider[2]);
					}
				}
				data[i] = mData;
				i++;
			}

		} else {
			data = new Object[0][7];
		}
		return data;
	}

	public Object[][] generateViewDataPalleV(Palle palle,
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
				mData[4] = null;
				mData[5] = null;
				mData[6] = null;
				long[] tider = m.getResterendeTider();
				if (tider.length == 1) {
					mData[5] = new Varighed(tider[0]);
				} 
				else if (tider.length == 3) {
					if (kunNaesteTidsfrist){
						mData[5] = new Varighed(m.getResterendeTidTilNaeste());
					}
					else {
						mData[4] = new Varighed(tider[0]);
						mData[5] = new Varighed(tider[1]);
						mData[6] = new Varighed(tider[2]);
					}
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
	 * Genererer/strukturerer data til brug for MainFrame
	 * 
	 * Hvis der er flere "varetyper" på samme Palle, udskrives en linie pr.
	 * "varetype" (dvs. kombinationen Produkttype, Delbehandling)
	 * 
	 * Er der ikke tilknyttet en {@link Palle} til en given {@link MellemlagerPlads}, 
	 * repræsenteres denne med et Object[] pData, hvor pData[0] er den pågældende 
	 * {@link MellemlagerPlads}, og pData[1]-[7] er null. 
	 * Det er så op til {@link MainFrame}-klassen at håndtere, om oversigten skal vises med 
	 * eller uden tomme pladser.
	 * 
	 * @return Object[][] - Opsummering af mellemlagerets indhold i form af: {@link MellemlagerPlads}, 
	 * 							{@link Palle}, {@link Produkttype}, {@link Delbehandling}, Antal, 
	 * 							Resterende tid til minTid, resterende tid til idealTid, resterende tid til maxTid
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
			Object[] pladsData = new Object[8];
			
			pladsData[0] = mp;
			pladsData[5] = null;
			pladsData[6] = null;
			pladsData[7] = null;
			if (mp.getPalle() == null) { // Tom mellemlagerplads
				
				listData.add(pladsData);
			} else { // Plads med palle
				Palle palle = mp.getPalle();
				if (palle.getMellemvarer().size() == 0) { // Tom palle - men
					// skal stadig
					// vises!
					pladsData[1] = palle;
					listData.add(pladsData);
				} 
				else { // Pallen indeholder mellemvarer, og der skal vises en
					// række pr. 'type'
					Object[][] fullPalleData = generateViewDataPalleV(palle,
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
	 * @return Object[][] - Opsummering af drageringshallens indhold i form af: Palle, Produkttype,
	 * 							Delbehandling, Antal, Resterende tid
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
				Object[][] fullPalleData = generateViewDataPalle(palle, false); // <--- false: Kun én resterende 
				//tid vises
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
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public Object[][] generateViewDataProdukttypeDelbehandlingAntalTid(
			Palle palle) {
		Object[][] fullData = generateViewDataPalleV(palle, true);
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

	public Object[][] generateViewDataTestOversigt(Palle palle) {
		Object[][] data;
		if (palle != null && palle.getMellemvarer().size() > 0) {
			HashMap<Mellemvare, Integer> mellemvareAntal = palle
					.getMellemvareAntalMapping();
			data = new Object[mellemvareAntal.size()][4];
			int i = 0;
			for (Mellemvare m : mellemvareAntal.keySet()) {
				Object[] mData = new Object[4];
				mData[0] = m.getProdukttype();
				mData[1] = m.getIgangvaerendeDelbehandling();
				mData[2] = mellemvareAntal.get(m);
				long[] tider = m.getResterendeTider();
				if (tider.length == 1) {
					mData[3] = new Varighed(tider[0]);
				}
				else if (tider.length==3){
					mData[3] = new Varighed(tider[1]);
				}
				data[i] = mData;
				i++;
			}
		} else {
			data = new Object[0][4];
		}
		return data;
	}



	/**
	 * Genererer/strukturerer data til brug for gui.FrameOversigter
	 * 
	 * @return Object[][] - Opsummering af kasserede varer i form af: Mellemvarens bakkestregkode, 
	 * 							Produkttype, MellemvareStatus
	 * 
	 * @author Rasmus Cederdorff
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
	 * Genererer data til oversigten over faerdigvarer (gui.FrameOversigter)
	 * 
	 * @return Object[][] - Opsummering af færdigvarelagerets indhold i form af Mellemvarens bakkestregkode, 
	 * 							Produkttype, MellemvareStatus
	 * 
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
	 * Benyttes af gui.SubFramePalleOversigt til at vise detaljerede
	 * informationer om individuelle bakker på en given palle
	 * 
	 * @param m Den aktuelle mellemvare
	 * @return En beskrivelse af den aktuelle mellemvare
	 * 
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
	 * Benyttes af SubFramePalleOversigt til at vise den aktuelle palles placering.
	 * 
	 * @param palle
	 * @return
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public String getPallePlaceringsString(Palle palle) {
		return palle.getPlaceringsString();
	}

	/*----------CRUD-operationer - oprettelser----------*/

	/**
	 * Opretter og returnerer en ny palle
	 * 
	 * @param stregkode
	 * @return Den nyoprettede palle
	 * 
	 * @author Rasmus Cederdorff 
	 */
	public Palle opretPalle(String stregkode) {
		Palle p = new Palle(stregkode);
		dao.gemPalle(p);
		return p;
	}

	/**
	 * Opretter og returnerer en mellemvare
	 * 
	 * @param bakkestregkode
	 * @param produkttype
	 * @param palle
	 * @return Den nyoprettede mellemvare
	 * 
	 * @author Rasmus Cederdorff 
	 */
	public Mellemvare opretMellemvare(String bakkestregkode, Produkttype produkttype, Palle palle) {
		Mellemvare m = new Mellemvare(bakkestregkode, produkttype, palle);
		dao.gemMellemvare(m);
		if (this.isTestMode()) {
			m.setTestMode(true);
		}
		return m;
	}

	/**
	 * Opretter og returnerer en ny produkttype
	 * 
	 * @param navn
	 * @param beskrivelse
	 * @param behandling
	 * @return Den nyoprettede produkttype
	 * 
	 * @author Rasmus Cederdorff 
	 */
	public Produkttype opretProdukttype(String navn, String beskrivelse, Behandling behandling) {
		Produkttype p = new Produkttype(navn, beskrivelse, behandling);
		dao.gemProdukttype(p);
		return p;
	}

	/**
	 * Opretter og returnerer en ny behandling
	 * 
	 * @param navn
	 * @return Den nyoprettede behandling
	 * 
	 * @author Rasmus Cederdorff 
	 */
	public Behandling opretBehandling(String navn) {
		Behandling b = new Behandling(navn);
		dao.gemBehandling(b);
		return b;
	}

	/**
    * Opretter og returnerer en ny delbehandling af typen dragering og tilføjer den til
    * Behandling b
    * 
    * @param navn
    * @param b	Den behandling som delbehandlingen skal være en del af
    * @param varighed Den tid (i millisekunder) det tager varen at blive drageret
    * @param index
    *            Placering i rækkefølgen af b's delbehandlinger. -1: tilføjes sidst i listen
    *            
    * @return Den nyoprettede Delbehandling af subklassen Dragering
    * 
    * @author Rasmus Cederdorff
    */
   public Delbehandling opretDragering(String navn, Behandling b,
   		long varighed, int index) {
   	Delbehandling d = new Dragering(navn, b, varighed);
   	tilfoejDelbehandling(b, d, index);
   	return d;
   }

	/**
    * Opretter og returnerer en ny delbehandling af typen tørring og tilføjer den til
    * Behandling b
    * 
    * @param navn
    * @param b	Den behandling som delbehandlingen skal være en del af
    * @param minTid Den minimale tørretid i millisekunder
    * @param idealTid Den ideelle tørretid i millisekunder
    * @param maxTid	Den maximale tørretid i millisekunder
    * @param index
    *            Placering i rækkefølgen af b's delbehandlinger. -1: tilføjes sidst i listen
    *            
    * @return Den nyoprettede Delbehandling af subklassen Tørring
    * 
    * @author Rita Holst Jacobsen
    */
   public Delbehandling opretToerring(String navn, Behandling b, long minTid,
   		long idealTid, long maxTid, int index) {
   	Delbehandling d = new Toerring(navn, b, minTid, idealTid, maxTid);
   	tilfoejDelbehandling(b, d, index);
   	return d;
   }

	/**
	 * Tilfoejer en delbehandling til en behandling 
	 * 
	 * @param behandling
	 * Krav: behandling skal eksistere i systemet/databasen
	 * @param delbehandlingNavn 
	 * @param delbehandlingIndex 
	 *            Placering i rækkefølgen af b's delbehandlinger. -1: tilføjes sidst i listen
	 * 
	 * @author Rita Holst Jacobsen 
	 */
	public void tilfoejDelbehandling(Behandling b,
			Delbehandling nyDelbehandling, int delbehandlingIndex)
					throws IndexOutOfBoundsException {
		b.addDelbehandling(nyDelbehandling, delbehandlingIndex);
		opdaterDatabase();
	}

	/**
	 * Opretter og returnerer en ny mellemlagerPlads
	 * 
	 * @param stregkode
	 * @return Den nyoprettede mellemlagerPlads
	 * 
	 * @author Rasmus Cederdorff 
	 */
	public MellemlagerPlads opretMellemlagerPlads(String stregkode) {
		MellemlagerPlads m = new MellemlagerPlads(stregkode);
		dao.gemMellemlagerPlads(m);
		return m;
	}

	

	/*----------CRUD-operationer - Read/gettere----------*/

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
	 * @author Rita Holst Jacobsen
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

	public Mellemvare soegMellemvare(String stregkode){
		return dao.soegMellemvare(stregkode);
	}

	/**
	 * @param palle
	 * @return
	 * @author Rita Holst Jacobsen
	 */
	public String getStregkode(Palle palle) {
		return palle.getStregkode();
	}

	/**
	 * @param stregkode
	 * @return
	 * @author Mads Dahl Jensen
	 */
	public Palle soegPalle(String stregkode) {
		return dao.soegPalle(stregkode);
	}

	/**
	 * @param mellemlagerPlads
	 * @return
	 * @author Rita Holst Jacobsen
	 */
	public String getStregkode(MellemlagerPlads mellemlagerPlads) {
		return mellemlagerPlads.getStregkode();
	}

	/**
	 * @param stregkode
	 * @return
	 * @author Mads Dahl Jensen
	 */
	public MellemlagerPlads soegMellemlagerPlads(String stregkode) {
		return dao.soegMellemlagerPlads(stregkode);
	}

	/**
	 * @param mellemvare
	 * @return
	 * @author Rita Holst Jacobsen
	 */
	public String getStregkode(Mellemvare mellemvare) {
		return mellemvare.getBakkestregkode();
	}

	/**
	 * @param palle
	 * @return
	 * @author Rasmus Cederdorff
	 */
	public ArrayList<Mellemvare> getMellemvarer(Palle palle) {
		return palle.getMellemvarer();
	}

	public ArrayList<Mellemvare> getMellemvarerAfSammeType(Palle p, Produkttype pt, Delbehandling d) {
		return p.getMellemvarerAfSammeType(pt, d);
	}

	/**
	 * @param palle
	 * @return
	 * @author
	 */
	public MellemlagerPlads getMellemlagerPlads(Palle palle) {
		return palle.getPlacering();
	}

	/**
	 * Returnerer om alle varer på pallen er ens, dvs. er af samme produkttype og i gang med samme delbehandling
	 * 
	 * @param palle
	 * @return True hvis alle varer er af samme produkttype og i gang med samme delbehandling.
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public boolean alleVarerErEns(Palle palle) {
		return palle.alleVarerErEns();
	}

	/**
	 * Benyttes af gui til at afgøre, om brugeren skal præsenteres for en dialog, der muliggør placering
	 * på mellemvarelageret.
	 * 
	 * Hvis pallen hverken er tilknyttet drageringshallen, en mellemlagerPlads eller mindst 1 mellemvare,
	 * er den pr. definition ikke i brug, og bør derfor placeres, første gang der kommer varer på den igen.
	 * 
	 * @param palle
	 * @return
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public boolean getPalleIkkeIBrug(Palle palle) {
		return (palle.getPlacering() == null
				&& palle.getDrageringshal() == null && palle.getMellemvarer()
				.size() == 0);
	}

	/*----------CRUD-operationer - opdateringer/settere----------*/

	/**
	 * Benyttes når databasen skal opdateres (kalder begin/commit)
	 * 
	 * @author Rasmus Cederdorff
	 */
	public void opdaterDatabase() {
		dao.opdaterDatabase();
	}

	/**
	 * Placerer en palle på en placering i mellemvarelageret
	 * 
	 * @param palle
	 * @param placering
	 * 
	 * author Rasmus Cederdorff
	 */
	public void placerPalleMellemvarelager(Palle palle,
			MellemlagerPlads placering) {
		palle.placerPalle(placering);
		opdaterDatabase();
	}

	/**
	 * Placerer en palle med mellemvarer i drageringshallen
	 * 
	 * @param palle
	 * 
	 * @author Rasmus Cederdorff
	 */
	public void sendPalleTilDragering(Palle palle) {
		palle.placerPalle(null);
		palle.setDrageringshal(Drageringshal.getInstance());
		opdaterDatabase();
	}


	/**
	 * @param palle
	 * @param nyStregkode
	 * 
	 * @author Rasmus Cederdorff
	 */
	public void redigerPalle(Palle palle, String nyStregkode) {
		palle.setStregkode(nyStregkode);
		opdaterDatabase();
	}

	/**
	 * @param produkttype
	 * @param nyBeskrivelse
	 * @param nyBehandling
	 * 
	 * @author Rasmus Cederdorff
	 */
	public void redigerProdukttype(Produkttype produkttype,
			String nyBeskrivelse, Behandling nyBehandling) {
		produkttype.setBeskrivelse(nyBeskrivelse);
		produkttype.setBehandling(nyBehandling);
		opdaterDatabase();
	}

	/*----------CRUD-operationer - sletninger----------*/

	/**
	 * Sletter en given palle fra databasen
	 * 
	 * @param palle
	 * 
	 * @author Rasmus Cederdorff
	 */
	public void removePalle(Palle palle) {
		dao.removePalle(palle);
	}

	/**
	 * Sletter en given produkttype fra databasen
	 * 
	 * @param produkttype
	 * 
	 * @author Rasmus Cederdorff
	 */
	public void removeProdukttype(Produkttype produkttype) {
		dao.removeProdukttype(produkttype);
	}

	/**
	 * Sletter en given plads på mellemvarelageret
	 * 
	 * @param mellemlagerPlads
	 * 
	 * @author Rasmus Cederdorff
	 */
	public void removeMellemlagerPlads(MellemlagerPlads mellemlagerPlads) {
		dao.removeMellemlagerPlads(mellemlagerPlads);
	}

	/**
	 * Sletter en behandling fra databasen
	 * 
	 * @param behandling
	 * 
	 * @author Rasmus Cederdorff
	 */
	public void removeBehandling(Behandling behandling) {
		dao.removeBehandling(behandling);
	}




}
