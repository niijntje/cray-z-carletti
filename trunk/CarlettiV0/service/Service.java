package service;

import gui.MainFrame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import model.Behandling;
import model.Delbehandling;
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
 * 
 * @author Cederdorff 
 * 
 */
/**
 * @author nijntje
 *
 */
public class Service {
	private static Service uniqueInstance;
	private DAO dao = ListDao.getListDao();
	//		 private DAO dao = JpaDao.getDao();

	private Service() {

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
	 */
	public Mellemvare opretMellemvare(String bakkestregkode,
			Produkttype produkttype, Palle palle) {
		Mellemvare m = new Mellemvare(bakkestregkode, produkttype, palle);
		palle.addMellemvare(m);
		dao.gemMellemvare(m);
		return m;
	}

	/**
	 * Opretter ny produkttype
	 * 
	 * @param navn
	 * @param beskrivelse
	 * @param behandling
	 * @return
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
	 */
	public void tilfoejDelbehandling(Behandling b,
			Delbehandling nyDelbehandling, int delbehandlingIndex) {
		b.addDelbehandling(nyDelbehandling, delbehandlingIndex);
	}

	/**
	 * Opretter ny mellemlagerPlads
	 * 
	 * @param stregkode
	 * @return
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
	 */
	public Delbehandling opretToerring(String navn, Behandling b, long minTid,
			long idealTid, long maxTid, int index) {
		Delbehandling d = new Toerring(navn, b, minTid, idealTid, maxTid);
		tilfoejDelbehandling(b, d, -1);
		// dao.gemDelbehandling(d);
		return d;
	}

	public Delbehandling opretDragering(String navn, Behandling b,
			long varighed, int index) {
		Delbehandling d = new Dragering(navn, b, varighed);
		tilfoejDelbehandling(b, d, -1);
		// dao.gemDelbehandling(d);
		return d;
	}

	/**
	 * Placerer en palle på en placering i mellemvarelageret
	 * 
	 * @param palle
	 * @param placering
	 */
	public void placerPalleMellemvarelager(Palle palle,
			MellemlagerPlads placering) {
		palle.placerPalle(placering);
		opdaterDatabase();

	}

	/**
	 * @param produkttype
	 * @param delbehandling
	 * @param palle
	 * @param helePallen
	 * @param alleAfSammeType
	 */
	public void sendTilNaesteDelbehandling(Mellemvare mellemvare, Palle palle, Class delbehandlingsType, Palle nyPalle){
		ArrayList<Mellemvare> behandledeVarer = palle.startDelbehandling(mellemvare, delbehandlingsType);
		if (mellemvare == null && behandledeVarer.size()==palle.getMellemvarer().size()){
			if (delbehandlingsType == Dragering.class){
				sendPalleTilDragering(palle);
			}
			else if (delbehandlingsType == Toerring.class){
				palle.setDrageringshal(null);
			}
		}
		else if (mellemvare != null && nyPalle != null){
			palle.removeMellemvare(mellemvare);
			nyPalle.addMellemvare(mellemvare);
			if (delbehandlingsType == Dragering.class){
				sendPalleTilDragering(nyPalle);
			}
		}
	}

	/**
	 * @param produkttype
	 * @param delbehandling
	 * @param palle
	 * @param delbehandlingsType
	 * @param nyPalle 
	 */
	public void sendTilNaesteDelbehandling(Produkttype produkttype, Delbehandling delbehandling, Palle palle, Class delbehandlingsType, Palle nyPalle){
		ArrayList<Mellemvare> behandledeVarer = palle.startDelbehandling(produkttype, delbehandling, delbehandlingsType);
		if (behandledeVarer.size() != palle.getMellemvarer().size()){
			for (Mellemvare m : behandledeVarer){
				if (nyPalle != null){
					palle.removeMellemvare(m);
					nyPalle.addMellemvare(m);
				}
			}
		}
	}
	
	public void sendTilFærdigvareLager(Mellemvare mellemvare, Palle palle){
		ArrayList<Mellemvare> behandledeVarer = palle.sendTilFaerdigvareLager(mellemvare);
		if (mellemvare != null){
			palle.removeMellemvare(mellemvare);
			//OPDATER DAO???
//			dao.removeMellemvare(mellemvare);
//			dao.addFaerdigvare(mellemvare);
		}
		else {
			palle.placerPalle(null);
			palle.setDrageringshal(null);
		}
	}
	


	/**
	 * Sender en palle med mellemvarer til dragering
	 * 
	 * @param palle
	 */
	public void sendPalleTilDragering(Palle palle) {
		palle.placerPalle(null);
		palle.setDrageringshal(Drageringshal.getInstance());
		opdaterDatabase();
	}
	/**
	 * Sletter en given palle fra databasen
	 * @param palle
	 */
	public void removePalle(Palle palle){
		dao.removePalle(palle);
	}


	/**
	 * Returnerer en liste med alle mellemlagerpladserne
	 * 
	 * @return
	 */
	public ArrayList<MellemlagerPlads> visOversigtOverMellemvarelager() {
		return new ArrayList<MellemlagerPlads>(dao.mellemlagerPladser());
	}


	public ArrayList<Palle> getPaller() {
		return new ArrayList<Palle>(dao.paller());
	}

	public ArrayList<MellemlagerPlads> getPladser() {
		return new ArrayList<MellemlagerPlads>(dao.mellemlagerPladser());
	}

	public ArrayList<Produkttype> getProdukttyper() {
		return new ArrayList<Produkttype>(dao.produkttyper());
	}

	public ArrayList<Mellemvare> getMellemvarer() {
		return new ArrayList<Mellemvare>(dao.mellemvarer());
	}

	public String getStregkode(Palle palle) {
		return palle.getStregkode();
	}

	public String getStregkode(Mellemvare mellemvare) {
		return mellemvare.getBakkestregkode();
	}

	public String getStregkode(MellemlagerPlads mellemlagerPlads) {
		return mellemlagerPlads.getStregkode();
	}

	public ArrayList<Mellemvare> getMellemvarer(Palle palle) {
		return palle.getMellemvarer();
	}

	public Palle soegPalle(String stregkode) {
		return dao.soegPalle(stregkode);
	}

	public MellemlagerPlads soegMellemlagerPlads(String stregkode) {
		return dao.soegMellemlagerPlads(stregkode);
	}

	/** Genererer data til brug for MainFrame
	 * 
	 * Object[][] - Opsummering af mellemlagerets indhold i form af:
	 * {@link MellemlagerPlads}, {@link Palle}, {@link Produkttype}, {@link Delbehandling}, Antal, Resterende tid
	 * 
	 * Hvis der er flere "varetyper" på samme Palle, udskrives en linie pr. "varetype" (dvs. kombinationen Produkttype, Delbehandling)
	 * 
	 * Er der ikke tilknyttet en {@link Palle} til en givet {@link MellemlagerPlads}, repræsenteres denne med et Object[] pData, hvor pData[0]
	 * er den pågældende {@link MellemlagerPlads}, og pData[1]-[5] er null. Det er så op til {@link MainFrame}-klassen at håndtere, om oversigten
	 * skal vises med eller uden tomme pladser.
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public Object[][] generateViewDataMellemlagerOversigt(){
		//Data gemmes i første omgang i en ArrayList, da størrelsen af data afhænger af 
		//hvor mange forskellige typer mellemvarer der findes på hver enkelt palle.
		ArrayList<Object[]> listData = new ArrayList<Object[]>();
		for (MellemlagerPlads mp : getPladser()){
			Object[] pladsData = new Object[6];
			pladsData[0] = mp;
			if (mp.getPalle() == null){
				listData.add(pladsData);
			}
			else {
				Object[][] mData = generateViewDataProdukttypeDelbehandlingAntalTid(mp.getPalle());
				for (int i = 0; i < mData.length; i++){
					pladsData = new Object[6];
					pladsData[0] = mp;
					if(mData[i][0] != null){
						pladsData[1] = mp.getPalle();
						pladsData[2] = mData[i][0];
						pladsData[3] = mData[i][1];
						pladsData[4] = mData[i][2];
						pladsData[5] = mData[i][3];
						listData.add(pladsData);
					}
				}
			}			
		}
		Object[][] data = new Object[listData.size()][6];
		int i = 0;
		for(Object[] oa : listData){
			data[i] = oa;
			i++;
		}
		return data;
	}

	/**
	 * Genererer data til brug for SubFramePalleOversigt
	 * 
	 * @param palle
	 * @return Object[][] - Opsummering af pallens indhold i form af:
	 *         Produkttype, Delbehandling (igangværende), antal af denne
	 *         kombination på pallen og resterende tid for samme.
	 * @author Rita Holst Jacobsen
	 */
	public Object[][] generateViewDataProdukttypeDelbehandlingAntalTid(
			Palle palle) {
		HashMap<Mellemvare, Integer> mellemvareAntal = palle
				.getMellemvareAntalMapping();
		Object[][] data = new Object[4][mellemvareAntal.size()];
		int i = 0;
		for (Mellemvare m : mellemvareAntal.keySet()) {
			Object[] mData = new Object[4];
			mData[0] = m.getProdukttype();
			mData[1] = m.getIgangvaerendeDelbehandling();
			mData[2] = mellemvareAntal.get(m);
			mData[3] = Validering.millisekunderTildato(m
					.getResterendeTidTilNaeste());
			data[i] = mData;
			i++;
		}
		return data;
	}

	/**
	 * Benyttes af gui.SubFramePalleOversigt til at vise detaljerede
	 * informationer om individuelle bakker på en given palle
	 * 
	 * @param m
	 * @return
	 */
	public String getMellemvareInfo(Mellemvare m) {
		long[] tider = m.getResterendeTider();
		String infoString = "#" + m.toString() + "\t"
				+ m.getIgangvaerendeDelbehandling() + "\n"
				+ "\nNæste delbehandling om:\n";
		for (int i = 0; i < tider.length; i++) {
			infoString += Validering.millisekunderTildato(tider[i]);
			if (i < tider.length - 1) {
				infoString += " /\t";
			}
		}
		infoString += "\n\nBehandlings-log:\n";
		ArrayList<GregorianCalendar> delbehandlingstider = m.getTidspunkter();
		ArrayList<Delbehandling> delbehandlinger = m.getProdukttype()
				.getBehandling().getDelbehandlinger();
		for (int i = 0; i < delbehandlingstider.size(); i++) {
			GregorianCalendar c = delbehandlingstider.get(i);
			infoString += Validering.calendarTilCalendarString(c) + "\t"
					+ delbehandlinger.get(i).toString() + "\n";
		}
		return infoString;
	}
	/**
	 * Benyttes når databasen skal opdateres (kalder begin/commit)
	 */
	public void opdaterDatabase(){
		dao.opdaterDatabase();
	}

}
