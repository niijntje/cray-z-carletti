package service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;

import model.Behandling;
import model.Delbehandling;
import model.Drageringshal;
import model.MellemlagerPlads;
import model.Mellemvare;
import model.Palle;
import model.Produkttype;
import model.Toerring;
import dao.DAO;
import dao.ListDao;

/**
 * 
 * @author Cederdorff
 * 
 */
public class Service {
	private static Service uniqueInstance;
	private DAO dao = ListDao.getListDao();

	// private DAO dao = JpaDao.getJpaDao();

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
			Delbehandling nyDelbehandling, int delbandlingIndex) {
		b.addDelbehandling(nyDelbehandling, delbandlingIndex);
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
	 * Opretter en ny Delbehandling af typen Toerring og tilføjer den til Behandling b
	 * @param navn
	 * @param b
	 * @param minTid
	 * @param idealTid
	 * @param maxTid
	 * @param index. Placering i rækkefølgen af b's delbehandlinger. -1: tilføjes sidst i listen
	 * @return
	 */
	private Delbehandling opretToerring(String navn, Behandling b, int minTid,
			int idealTid, int maxTid, int index) {
		Delbehandling d = new Toerring(navn, b, minTid, idealTid, maxTid);
		tilfoejDelbehandling(b, d, -1);
		dao.gemDelbehandling(d);
		return d;
	}

	/**
	 * Placerer en palle på en placering i mellemvarelageret
	 * 
	 * @param palle
	 * @param placering
	 */
	public void placerPalleMellemvarelager(Palle palle, MellemlagerPlads placering) {
		palle.placerPalle(placering);
		palle.setDrageringshal(Drageringshal.getInstance());
	}

	/**
	 * Sender en palle med mellemvarer til dragering
	 * 
	 * @param palle
	 */
	public void sendPalleTilDragering(Palle palle) {
		palle.placerPalle(null);
		palle.setDrageringshal(Drageringshal.getInstance());

	}

	/**
	 * Returnerer en liste med alle mellemlagerpladserne
	 * 
	 * @return
	 */
	public ArrayList<MellemlagerPlads> visOversigtOverMellemvarelager() {
		return new ArrayList<MellemlagerPlads>(dao.mellemlagerPladser());
	}

	/**
	 * Udskriver alle mellemlagerPladser, der er gemt i databasen
	 */
	public void udskrivMellemlagerPladser() {

		for (int i = 0; i < dao.mellemlagerPladser().size(); i++) {
			System.out.println(dao.mellemlagerPladser().get(i).toStringLong());
		}
	}
	
	public ArrayList<Palle> getPaller(){
		return new ArrayList<Palle>(dao.paller());
	}
	
	public ArrayList<MellemlagerPlads> getPladser(){
		return new ArrayList<MellemlagerPlads>(dao.mellemlagerPladser());
	}
	
	public ArrayList<Produkttype>getProdukttyper(){
		return new ArrayList<Produkttype>(dao.produkttyper());
	}



	public String getStregkode(Palle palle) {
		return palle.getStregkode();
	}
	
	public Palle findPalle(String stregkode){
		ArrayList<Palle> paller = ListDao.getListDao().getPaller();
		Palle p = null;
		boolean found = false;
		int i= 0;
		while (!found){
			Palle p1 = paller.get(i);
			if (p1.getStregkode().equals(stregkode)){
				p = p1;
				found = true;
			}
			i++;
		}
		return p;
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

	/**
	 * Genererer data til brug for SubFramePalleOversigt
	 * @param palle
	 * @return Object[][] - Opsummering af pallens indhold i form af: Produkttype, Delbehandling (igangværende), antal af denne kombination på pallen og resterende tid for samme.
	 * @author Rita Holst Jacobsen
	 */
	public Object[][] generateViewDataProdukttypeDelbehandlingAntalTid(Palle palle) {
		HashMap<Mellemvare, Integer> mellemvareAntal = palle.getMellemvareAntalMapping();
		Object[][] data = new Object[4][mellemvareAntal.size()];
		int i = 0;
		for (Mellemvare m : mellemvareAntal.keySet()){
			Object[] mData = new Object[4];
			mData[0] = m.getProdukttype();
			mData[1] = m.getIgangvaerendeDelbehandling();
			mData[2] = mellemvareAntal.get(m);
			mData[3] = Validering.millisekunderTildato(m.getResterendeTidTilNaeste());
			data[i] = mData;
			i++;
		}
		return data;
	}
	
	
	
	/**
	 * Opretter objekter og tilføjer dem til Dao.
	 */
	public void createSomeObjects() {
		
		Palle pa1 = opretPalle("20000001");
		opretPalle("20000002");
		Palle palle1 = opretPalle("00001");
		opretPalle("00002");
		opretPalle("00003");

		MellemlagerPlads pl1 = opretMellemlagerPlads("100100101");
		opretMellemlagerPlads("100100102");
		opretMellemlagerPlads("001");
		opretMellemlagerPlads("002");
		opretMellemlagerPlads("003");
		opretMellemlagerPlads("004");
		opretMellemlagerPlads("005");
		opretMellemlagerPlads("006");
		opretMellemlagerPlads("007");
		opretMellemlagerPlads("008");
		opretMellemlagerPlads("009");
		opretMellemlagerPlads("010");

		Behandling b = opretBehandling("Behandling1");
		opretToerring("Tørring1", b, 12, 15, 20, -1);
		Behandling b3 = opretBehandling("B3 - Månegrus og lakridspinde. Kernestørrelse 2.5-3.5");
		opretToerring("Tørring 1a", b3,100000000, 150000000, 200000000, -1);

		Produkttype p = opretProdukttype("Lakridspinde 3A", "Hvide lakridspinde med kernestørrelse 2.\nOpskrift: B2", b);
		Produkttype p2 = opretProdukttype("Skumbananer", "Dejlig skum overtrukket af chokolade", b);
		Produkttype pt1 = opretProdukttype("Lakridspinde 1", "Grønne lakridspinde med kernestørrelse 3.\nOpskrift: B3", b3);
		Produkttype pt2 = opretProdukttype("Månegrus 2B", "Rester fra lakridspinde med varierende kernestørrelse.\nOpskrift: B3", b3);
				
		opretMellemvare("01", p, pa1);
		opretMellemvare("02", p2, palle1);
		opretMellemvare("300000001", pt1, pa1);
		opretMellemvare("300000002", pt1, pa1);
		opretMellemvare("300000003", pt2, pa1);
		opretMellemvare("300000004", pt1, pa1);
		opretMellemvare("300000005", pt1, pa1);
		opretMellemvare("300000006", pt2, pa1);
		opretMellemvare("300000007", pt1, pa1);
		
		placerPalleMellemvarelager(pa1, pl1);
//		placerPalleMellemvarelager(pa1, mPlads1);
	}

	public String getMellemvareInfo(Mellemvare m) {
		long[] tider = m.getResterendeTider();
		String infoString = m.toString() + m.getIgangvaerendeDelbehandling()+"\n"
				+ "Behandlings-log:\n";
		ArrayList<GregorianCalendar> delbehandlingstider = m.getTidspunkter();
		ArrayList<Delbehandling> delbehandlinger = m.getProdukttype().getBehandling().getDelbehandlinger();
 		for (int i = 0; i<delbehandlingstider.size(); i++){
 			GregorianCalendar c = delbehandlingstider.get(i);
 			
 			infoString+= c.YEAR+"-"+c.MONTH+"-"+c.DATE
 					+c.HOUR_OF_DAY+":"+c.MINUTE
 					+": "+delbehandlinger.get(i).toString();
		}
		

//		+ Validering.millisekunderTildato(m.getResterendeTidTilNaeste());
		return infoString;
	}


}
