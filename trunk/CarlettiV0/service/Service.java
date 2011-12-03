package service;

import gui.MainFrame;

import java.util.ArrayList;
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
	//			 private DAO dao = JpaDao.getDao();

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

	public boolean alleVarerErEns(Palle palle){
		return palle.alleVarerErEns();
	}

	/**
	 * @param produkttype
	 * @param delbehandling
	 * @param palle
	 * @param helePallen
	 * @param alleAfSammeType
	 */
	public void sendTilNaesteDelbehandling(Mellemvare mellemvare, Palle palle, DelbehandlingsType delbehandlingsType, Palle nyPalle){
		ArrayList<Mellemvare> behandledeVarer = palle.startDelbehandling(mellemvare, delbehandlingsType);
		if (mellemvare == null && behandledeVarer.size()==palle.getMellemvarer().size()){
			if (delbehandlingsType == DelbehandlingsType.DRAGERING){
				sendPalleTilDragering(palle);
			}
			else if (delbehandlingsType == DelbehandlingsType.TOERRING){
				palle.setDrageringshal(null);
			}
		}
		else if (mellemvare != null && nyPalle != null){
			palle.removeMellemvare(mellemvare);
			nyPalle.addMellemvare(mellemvare);
			if (delbehandlingsType == DelbehandlingsType.DRAGERING){
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
	public void sendTilNaesteDelbehandling(Produkttype produkttype, Delbehandling delbehandling, Palle palle, DelbehandlingsType delbehandlingsType, Palle nyPalle){
		ArrayList<Mellemvare> behandledeVarer = palle.startDelbehandling(produkttype, delbehandling, delbehandlingsType);
		if (behandledeVarer.size() != palle.getMellemvarer().size()){
			for (Mellemvare m : behandledeVarer){
				if (nyPalle != null){
					palle.removeMellemvare(m);
					nyPalle.addMellemvare(m);
				}
			}
		}
		else {
			if (delbehandlingsType == DelbehandlingsType.DRAGERING){
				sendPalleTilDragering(palle);
			}
			else if (delbehandlingsType == DelbehandlingsType.TOERRING){
				placerPalleMellemvarelager(palle, null);
			}
		}
	}

	/**
	 * @param mellemvare
	 * @param palle
	 */
	public void sendTilFærdigvareLager(Mellemvare mellemvare, Palle palle){
		ArrayList<Mellemvare> behandledeVarer = palle.sendTilFaerdigvareLager(mellemvare);
		if (mellemvare != null){
			mellemvare.setStatus(MellemvareStatus.FAERDIG);
			palle.removeMellemvare(mellemvare);
		}
		else if (behandledeVarer.size()==palle.getMellemvarer().size()){
			palle.placerPalle(null);
			palle.setDrageringshal(null);
		}
	}


	/**
	 * @param produkttype
	 * @param delbehandling
	 * @param palle
	 * @param delbehandlingsType
	 * @param nyPalle
	 */
	public void sendTilFærdigvareLager(Produkttype produkttype, Delbehandling delbehandling, Palle palle, Palle nyPalle){
		ArrayList<Mellemvare> behandledeVarer = palle.sendTilFaerdigvareLager(produkttype, delbehandling);
		if (behandledeVarer.size() == palle.getMellemvarer().size()){
			palle.placerPalle(null);
			palle.setDrageringshal(null);
		}
		else {
			for (Mellemvare m : behandledeVarer){
				m.setStatus(MellemvareStatus.FAERDIG);
				palle.removeMellemvare(m);
				if (nyPalle!=null){
					nyPalle.addMellemvare(m);
					nyPalle.placerPalle(null);
					nyPalle.setDrageringshal(null);
				}
			}
		}
	}

	/**
	 * @param mellemvare
	 * @param palle
	 */
	public void kasserMellemvarer(Mellemvare mellemvare, Palle palle){
		ArrayList<Mellemvare> behandledeVarer = palle.kasserMellemvarer(mellemvare);
		//Hvis alle varer på pallen kasseres, skal pallen 'frigives' fra sin hidtidige placering
		if (behandledeVarer.size()==palle.getMellemvarer().size()) {
			palle.placerPalle(null);
			palle.setDrageringshal(null);
		}
		for (Mellemvare m : behandledeVarer){
			m.setStatus(MellemvareStatus.KASSERET);
			palle.removeMellemvare(m);
		}
	}

	/**
	 * @param produkttype
	 * @param delbehandling
	 * @param palle
	 */
	public void kasserMellemvarer(Produkttype produkttype, Delbehandling delbehandling, Palle palle){
		ArrayList<Mellemvare> behandledeVarer = palle.kasserMellemvare(produkttype, delbehandling);
		if (behandledeVarer.size()==palle.getMellemvarer().size()){
			palle.placerPalle(null);
			palle.setDrageringshal(null);
		}
		for (Mellemvare m : behandledeVarer){
			m.setStatus(MellemvareStatus.KASSERET);
			palle.removeMellemvare(m);
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
	 * Sletter en given produkttype fra databasen
	 * @param produkttype
	 */
	public void removeProdukttype(Produkttype produkttype){
		dao.removeProdukttype(produkttype);
	}
	/**
	 * Sletter en given plads på mellemvarelageret
	 * @param mellemlagerPlads
	 */
	public void removeMellemlagerPlads(MellemlagerPlads mellemlagerPlads){
		dao.removeMellemlagerPlads(mellemlagerPlads);
	}
	/**
	 * Sletter en behandling fra databasen
	 * @param behandling
	 */
	public void removeBehandling(Behandling behandling){
		dao.removeBehandling(behandling);
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

	public ArrayList<Behandling> getBehandlinger(){
		return new ArrayList<Behandling>(dao.behandlinger());
	}

	public ArrayList<Mellemvare>getFaerdigvarer(){
		return new ArrayList<Mellemvare>(dao.faerdigvarer());
	}

	public ArrayList<Mellemvare>getKasserede(){
		return new ArrayList<Mellemvare>(dao.kasseredeVarer());
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

	public void redigerPalle(Palle palle, String nyStregkode){
		palle.setStregkode(nyStregkode);
		opdaterDatabase();
	}

	public void redigerProdukttype(Produkttype produkttype, String nyBeskrivelse, Behandling nyBehandling){
		produkttype.setBeskrivelse(nyBeskrivelse);
		produkttype.setBehandling(nyBehandling);
		opdaterDatabase();
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
					System.out.println(mData[i][1]);
					if(mData[i][0] != null && mData[i][1] == DelbehandlingsType.TOERRING){
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

	public Object[][] generateViewDataMellemlagerOversigt3Tider(){
		//Data gemmes i første omgang i en ArrayList, da størrelsen af data afhænger af 
		//hvor mange forskellige typer mellemvarer der findes på hver enkelt palle.
		ArrayList<Object[]> listData = new ArrayList<Object[]>();
		for (MellemlagerPlads mp : getPladser()){
			Object[] pladsData = new Object[8];
			pladsData[0] = mp;
			if (mp.getPalle() == null){
				listData.add(pladsData);
			}
			else {
				Palle palle = mp.getPalle();
				Object[][] palleData = null;
				if (palle.getMellemvarer().size()>0){
					HashMap<Mellemvare, Integer> mellemvareAntal = palle
							.getMellemvareAntalMapping();
					palleData = new Object[8][mellemvareAntal.size()];
					int i = 0;

					for (Mellemvare m : mellemvareAntal.keySet()) {
						Object[] mData = new Object[8];
						mData[0] = mp;
						mData[1] = palle;
						mData[2] = m.getProdukttype();
						mData[3] = m.getIgangvaerendeDelbehandling();
						mData[4] = mellemvareAntal.get(m);
						long[] tider = m.getResterendeTider();
						if (tider.length==3){
							mData[5] = Validering.millisekunderTilVarighedString(tider[0]);
							mData[6] = Validering.millisekunderTilVarighedString(tider[1]);
							mData[7] = Validering.millisekunderTilVarighedString(tider[2]);
						}
						else mData[6] =Validering.millisekunderTilVarighedString(tider[0]);
						palleData[i] = mData;
						i++;
					}
				}
			}

		}
		Object[][] data = new Object[listData.size()][8];
		int i = 0;
		for(Object[] pladsDataArray : listData){
			data[i] = pladsDataArray;
			i++;
		}
		return data;
	}

	/**
	 * genererer et array til visning i SubFrameDrageringshalOversigt
	 */

	public Object[][] generateViewDataDrageringshal()
	{
		ArrayList<Palle> paller = Drageringshal.getInstance().getPaller();
		Object[][] data = new Object[paller.size()][2];
		for (int i = 0; i < paller.size(); i++)
		{
			Palle palle = paller.get(i);
			for(int j = 0; j < palle.getMellemvarer().size(); j++){
				data[i][0] = palle;
				data[i][1] = palle.getMellemvarer().get(j).getProdukttype();
				data[i][2] = Validering.millisekunderTilVarighedString(palle.getMellemvarer().get(j).getResterendeTidTilNaeste());
			}


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
		Object[][] data = null;
		if (palle.getMellemvarer().size()>0){
			HashMap<Mellemvare, Integer> mellemvareAntal = palle
					.getMellemvareAntalMapping();
			data = new Object[4][mellemvareAntal.size()];
			int i = 0;

			for (Mellemvare m : mellemvareAntal.keySet()) {
				Object[] mData = new Object[4];
				mData[0] = m.getProdukttype();
				mData[1] = m.getIgangvaerendeDelbehandling();
				mData[2] = mellemvareAntal.get(m);
				mData[3] = Validering.millisekunderTilVarighedString(m
						.getResterendeTidTilNaeste());
				data[i] = mData;
				i++;
			}
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
		String infoString = "";
		if (m!=null){
			long[] tider = m.getResterendeTider();
			infoString = "#" + m.toString() + "\t"
					+ m.getIgangvaerendeDelbehandling() + "\n"
					+ "\nNæste delbehandling om:\n";
			for (int i = 0; i < tider.length; i++) {
				infoString += Validering.millisekunderTilVarighedString(tider[i]);
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
		}
		return infoString;
	}
	/**
	 * Benyttes når databasen skal opdateres (kalder begin/commit)
	 */
	public void opdaterDatabase(){
		dao.opdaterDatabase();
	}

	public boolean naesteBehandlingGyldig(Mellemvare m, DelbehandlingsType delbehandlingsType) {
		return m.naesteBehandlingGyldig(delbehandlingsType);
	}

	/**
	 * Returnerer om alle eller en delmængde af mellemvarerne på @palle er klar til en given handling (næste delbehandling eller færdigvarelageret)
	 * Hvis både @produkttype og @delbehandling er forskellige fra null, returneres om delmængde er klar. Ellers om alle er klar.
	 * @param palle	Krav: Forskellig fra null
	 * @param produkttype	Hvis null returneres om alle på pallen er klar
	 * @param delbehandling	Hvis null returneres om alle på pallen er klar. 
	 * @param delbehandlingsType	Den handling, der spørges til. Kan være hhv. Dragering, Tørring og Færdigvarelager (null)
	 * @return om alle/en delmængde er klar til næste (be)handling
	 */
	public boolean naesteBehandlingGyldig(Palle palle, Produkttype produkttype, Delbehandling delbehandling, DelbehandlingsType delbehandlingsType){
		boolean gyldig = true;
		if (produkttype==null || delbehandling==null){	//Hvis produkttype og delbehandling er ukendt
			if (palle.alleVarerErEns()){				//skal alle mellemvarer på pallen være ens OG klar til næste delbehandling/færdigvarelager
				for (Mellemvare m : palle.getMellemvarer()){
					if (!m.naesteBehandlingGyldig(delbehandlingsType)){
						gyldig = false;
					}
				}
			}
		}
		else {		//Hvis produkttype og delbehandling derimod er kendt, 
					//returneres kun om produkter med _disse_ egenskaber er klar til næste delbehandling/færdigvarelager
			for (Mellemvare m : palle.getMellemvarerAfSammeType(produkttype, delbehandling)){
				if (!m.naesteBehandlingGyldig(delbehandlingsType)){
					gyldig = false;
				}

			}
		}
		return gyldig;
	}

	public String getPallePlaceringsString(Palle palle) {
		return palle.getPlaceringsString();
	}

	public boolean getPalleIkkeIBrug(Palle palle) {
		return (palle.getPlacering()==null && palle.getDrageringshal()==null&&palle.getMellemvarer().size()==0);
	}

	public Object[][] generateViewDataKasseredeVarer() {
		// TODO Auto-generated method stub
		return null;
	}

}
