/**
 * OBJECTCREATER
 */
package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;

import dao.DAO;
import dao.ListDao;

import model.Behandling;
import model.Delbehandling;
import model.Dragering;
import model.MellemlagerPlads;
import model.Mellemvare;
import model.MellemvareStatus;
import model.Palle;
import model.Produkttype;
import model.Toerring;

/**
 * Klassen generer data til brug for testkørsel af systemet og demonstration af den vigtigste funktionalitet.
 * 
 * Data er ikke genereret på en systematisk måde men skrevet ad hoc efterhånden som der var brug for at teste
 * konkrete use cases, hvilket i vores øjne har gjort dem mere 'spændende' ;-)
 * 
 * @author Rita Holst Jacobsen
 * @author Rasmus Cederdorff
 * 
 */
public class ObjectCreater {

	private DAO dao;
	private static ObjectCreater objectCreater;

	private ObjectCreater() {
		dao = ListDao.getListDao();
	}

	public static ObjectCreater getInstance() {
		if (objectCreater == null) {
			objectCreater = new ObjectCreater();
		}
		return objectCreater;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Opretter objekter og tilføjer dem til Dao.
	 */
	public void createSomeObjects() {


		//--------Paller--------//

		for (int i = 1; i <= 100; i++){
			String stregkode = ""+i;
			while (stregkode.length() < 5){
				stregkode = "0"+stregkode;
			}
			Service.getInstance().opretPalle(stregkode);	
		}
		Palle pa01 = Service.getInstance().soegPalle("00001");
		Palle pa02 = Service.getInstance().soegPalle("00002");
		Palle pa03 = Service.getInstance().soegPalle("00003");

		//--------Pladser--------//
		for (int i = 1; i <= 100; i++){
			String stregkode = ""+i;
			while (stregkode.length() < 3){
				stregkode = "0"+stregkode;
			}
			Service.getInstance().opretMellemlagerPlads(stregkode);	
		}
		MellemlagerPlads pl1 = Service.getInstance().soegMellemlagerPlads("001");
		MellemlagerPlads pl2 = Service.getInstance().soegMellemlagerPlads("007");
		MellemlagerPlads pl3 = Service.getInstance().soegMellemlagerPlads("003");

		//--------Varigheder--------//
		Long varighed021400 = Validering.varighedStringTilMillisekunder("02:14:00");
		Long varighed021630 = Validering.varighedStringTilMillisekunder("02:16:30");
		Long varighed3 = Validering.varighedStringTilMillisekunder("02:17:00");
		Long varighed4 = Validering.varighedStringTilMillisekunder("02:18:00");

		Long varighed5 = Validering.varighedStringTilMillisekunder("01:15:00");
		Long varighed6 = Validering.varighedStringTilMillisekunder("01:15:15");
		Long varighed7 = Validering.varighedStringTilMillisekunder("01:15:30");
		Long varighed8 = Validering.varighedStringTilMillisekunder("01:15:45");

		Long varighed9 = Validering.varighedStringTilMillisekunder("00:01:00");
		Long varighed10 = Validering.varighedStringTilMillisekunder("00:14:00");
		Long varighed11 = Validering.varighedStringTilMillisekunder("00:02:15");
		Long varighed12 = Validering.varighedStringTilMillisekunder("00:01:45");

		//--------Behandlinger--------//
		Behandling b1 = Service.getInstance().opretBehandling("Behandling1");
		Behandling b3 = Service.getInstance().opretBehandling("B3 - Månegrus og lakridspinde. Kernestørrelse 2.5-3.5");
		Behandling kortBehandling = Service.getInstance().opretBehandling("Ritas ultrakorte behandling");

		//--------Delbehandlinger--------//
		//Behandling 1//
		Service.getInstance().opretToerring("Tørring1", b1, 12 * 60 * 60 * 1000, 15 * 60 * 60 * 1000, 20 * 60 * 60 * 1000, -1);
		Service.getInstance().opretDragering("Dragering 1b", b1, varighed11, -1);
		//		Service.getInstance().opretToerring("Tørring 2b", b1, minTid, idealTid, maxTid, -1);
		//Behandling 2//
		Service.getInstance().opretToerring("Tørring 1a", b3, varighed021400, varighed021630, varighed4, -1);
		Delbehandling d2 = Service.getInstance().opretDragering("Dragering 2e", b3, varighed10, -1);
		Service.getInstance().opretToerring("Tørring 1b", b3, varighed5, varighed6, varighed8, -1);
		Service.getInstance().opretDragering("Dragering 2e", b3, varighed11, -1);
		Service.getInstance().opretToerring("Tørring 1c", b3, varighed9, varighed10, varighed11, -1);
		Service.getInstance().opretDragering("Dragering 2f", b3, varighed11, -1);

		//--------Produkttyper--------//
		Produkttype p = Service.getInstance().opretProdukttype("Lakridspinde 3A",
				"Hvide lakridspinde med kernestørrelse 2.\nOpskrift: B2", b1);
		Produkttype p2 = Service.getInstance().opretProdukttype("Skumbananer",
				"Dejlig skum overtrukket af chokolade", b1);
		Produkttype pt1 = Service.getInstance().opretProdukttype("Lakridspinde 1",
				"Grønne lakridspinde med kernestørrelse 3.\nOpskrift: B3", b3);
		Produkttype pt2 = Service.getInstance().opretProdukttype("Månegrus 2B",
				"Rester fra lakridspinde med varierende kernestørrelse.\nOpskrift: B3",b3);

		GregorianCalendar dato6 = new GregorianCalendar();
		dato6.setTimeInMillis(System.currentTimeMillis() - (varighed9));

		//--------Mellemvarer--------//

		opretMellemvare("300000001", p, pa01, dato6);
		opretMellemvare("300000002", p2, pa03, dato6);
		opretMellemvare("300000003", pt2, pa01, dato6);
		opretMellemvare("300000004", pt1, pa02, dato6);
		opretMellemvare("300000005", pt1, pa02, dato6);
		opretMellemvare("300000006", pt2, pa01, dato6);
		opretMellemvare("300000007", pt1, pa01, dato6);
		opretMellemvare("300000008", pt1, pa01, dato6);
		opretMellemvare("300000009", pt1, pa01, dato6);

		Mellemvare m1 = pa01.getMellemvarer().get(0);

		Service.getInstance().placerPalleMellemvarelager(pa01, pl1);
		Service.getInstance().placerPalleMellemvarelager(pa02, pl2);
		Service.getInstance().sendPalleTilDragering(pa03);

		GregorianCalendar dato7 = new GregorianCalendar();
		dato7.setTimeInMillis(System.currentTimeMillis() - varighed3);
		GregorianCalendar dato8 = new GregorianCalendar();
		dato8.setTimeInMillis(System.currentTimeMillis() - varighed11 * 2);
		GregorianCalendar dato9 = new GregorianCalendar();
		dato9.setTimeInMillis(System.currentTimeMillis() - varighed12);
		GregorianCalendar dato10 = new GregorianCalendar();
		dato10.setTimeInMillis(System.currentTimeMillis() - varighed11);

		for (Mellemvare m : pa01.getMellemvarer()) {
			if (m.getProdukttype() == pt1) {
				m.setIgangvaerendeDelbehandling(m
						.getIgangvaerendeDelbehandling().getNextDelbehandling());
				m.addTidspunkt(dato7);
				m.setIgangvaerendeDelbehandling(m
						.getIgangvaerendeDelbehandling().getNextDelbehandling());
				m.addTidspunkt(dato8);
				m.setIgangvaerendeDelbehandling(m
						.getIgangvaerendeDelbehandling().getNextDelbehandling());
				m.addTidspunkt(dato9);
				m.setIgangvaerendeDelbehandling(m
						.getIgangvaerendeDelbehandling().getNextDelbehandling());

			} else if (m.getProdukttype() == pt2) {
				m.setIgangvaerendeDelbehandling(m
						.getIgangvaerendeDelbehandling().getNextDelbehandling());
				m.addTidspunkt(dato8);
				m.setIgangvaerendeDelbehandling(m
						.getIgangvaerendeDelbehandling().getNextDelbehandling());
				m.addTidspunkt(dato10);

			}
		}
		m1.setIgangvaerendeDelbehandling(d2);
		Mellemvare m2 = Service.getInstance().soegMellemvare("300000001");
		GregorianCalendar m2Start = m2.getTidspunkter().get(m2.getTidspunkter().size()-1);
		GregorianCalendar nyM2Start = new GregorianCalendar();
		nyM2Start.setTimeInMillis(m2Start.getTimeInMillis()-10*60*60*1000);
		m2.addTidspunkt(nyM2Start);

		Palle pa11 = Service.getInstance().soegPalle("00011");
		Service.getInstance().placerPalleMellemvarelager(pa11, pl3);
		Delbehandling kortDelbehandling = Service.getInstance().opretToerring(
				"Ultrakort tørring", kortBehandling,
				Validering.varighedStringTilMillisekunder("00-00:01"),
				Validering.varighedStringTilMillisekunder("00-00:02"),
				Validering.varighedStringTilMillisekunder("00-00:03"), -1);
		Delbehandling kortDelbehandling2 = Service.getInstance()
				.opretDragering("Ultrakort dragering", kortBehandling,
						Validering.varighedStringTilMillisekunder("00-00:01"),
						-1);
		Delbehandling kortDelbehandling3 = Service.getInstance().opretToerring(
				"Ultrakort tørring", kortBehandling,
				Validering.varighedStringTilMillisekunder("00-00:01"),
				Validering.varighedStringTilMillisekunder("00-00:02"),
				Validering.varighedStringTilMillisekunder("00-00:03"), -1);
		Produkttype hurtigProdukttype = Service.getInstance().opretProdukttype(
				"Hurtige bolsjer", "De her bolsjer er BARE hurtige at lave!",
				kortBehandling);
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(System.currentTimeMillis() - 60000);

		Mellemvare m3 = new Mellemvare("21312", hurtigProdukttype, pa11, cal); // <---Kan
		// ikke
		// kaldes
		// gennem
		// Service,
		// da
		// varianter
		// hvor
		// tidspunkt
		// kan
		// sættes
		// i
		// konstruktoren.
		pa11.addMellemvare(m3);
		dao.gemMellemvare(m3);
		// placerPalleMellemvarelager(pa1, mPlads1);


		Produkttype pr = createBehandlingsdata("B7", "B7 - Wienerlinser", "Wienerlinser 3b", "Wienerlinser, str. 3 - hvide");
		Delbehandling db = pr.getBehandling().getDelbehandling(4);

		createCase("008", "00006", 15, 20, pr, db, "01-23:04:00", "01-23:04:00", "01-23:04:00");
		createCase("071", "00030", 11, 39, pr, db, "01-23:04:00", "01-23:04:00", "01-23:04:00");
		createCase("072", "00007", 16, 55, pr, db, "01-23:04:00", "01-23:04:00", "01-23:04:00");
		createCase("009", "00008", 15, 20, pr, db, "01-23:04:00", "01-23:04:00", "01-23:04:00");
		createCase("010", "00009", 11, 39, pr, db, "01-23:04:00", "01-23:04:00", "01-23:04:00");
		createCase("011", "00010", 16, 55, pr, db, "01-23:04:00", "01-23:04:00", "01-23:04:00");

		createCase("022", "00091", 12, 120, pr, db, "00-00:00:00", "00-00:24:00", "00-00:00:00");
		createCase("021", "00092", 12, 145, pr, db, "00-00:00:00", "00-01:01:00", "00-00:00:00");
		createCase("015", "00093", 11, 160, pr, db, "00-00:00:00", "00-01:32:00", "00-00:00:00");
		createCase("016", "00094", 11, 174, pr, db, "00-00:00:00", "00-02:04:00", "00-00:00:00");
		createCase("017", "00095", 12, 70, pr, db, "00-00:00:00", "00-02:30:00", "00-00:00:00");
		createCase("018", "00096", 12, 84, pr, db, "00-00:00:00", "00-03:00:00", "00-00:00:00");
		createCase("019", "00097", 12, 98, pr, db, "00-00:00:00", "00-03:30:30", "00-00:00:00");
		createCase("020", "00098", 9, 110, pr, db, "00-00:00:00", "00-04:00:00", "00-00:00:00");

		pr = createBehandlingsdata("B4", "B4 - Linser", "Pariserlinser", "...fra Paris!");
		db = pr.getBehandling().getDelbehandling(2);

		createCase("049", "00040", 16, 189, pr, db, "00-00:00:00", "00-00:00:05", "00-00:00:00");
		createCase("050", "00042", 16, 210, pr, db, "00-00:00:00", "00-00:00:07", "00-00:00:00");
		createCase("051", "00047", 16, 231, pr, db, "00-00:00:00", "00-00:00:09", "00-00:00:00");
		createCase("052", "00044", 15, 250, pr, db, "00-00:00:00", "00-00:00:15", "00-00:00:00");
		createCase("053", "00100", 16, 269, pr, db, "00-00:00:00", "00-00:00:16", "00-00:00:00");
		createCase("054", "00071", 10, 299, pr, db, "00-00:00:00", "00-00:00:19", "00-00:00:00");

		createCase("031", "00081", 12, 336, pr, db, "00-00:00:00", "00-00:00:00", "00-00:00:02");
		createCase("036", "00080", 12, 299, pr, db, "00-00:00:00", "00-00:00:00", "00-00:00:03");
		createCase("034", "00077", 12, 310, pr, db, "00-00:00:00", "00-00:00:00", "00-00:00:11");
		createCase("032", "00072", 5, 323, pr, db, "00-00:00:00", "00-00:00:00", "00-00:00:12");
		createCase("035", "00071", 10, 349, pr, db, "00-00:00:00", "00-00:00:00", "00-00:00:13");
		createCase("033", "00074", 4,  367, pr, db, "00-00:00:00", "00-00:00:00", "00-00:00:25");
		pr = createBehandlingsdata("B1A", "B1A - Lakridspinde", "Lakridspinde 1a", "Hvide lakridspinde, kernestr.1");
		db = pr.getBehandling().getDelbehandling(2);
		createCase("033", "00074", 7,  430, pr, db, "00-05:00:00", "00-05:00:00", "00-00:00:00");
		createCase("032", "00072", 5,  437, pr, db, "00-05:00:00", "00-05:00:00", "00-00:00:00");
		
		pr = createBehandlingsdata("B7", "B7 - Lakridspinde", "Orange lakridspinde2", "Orange lakridspinde, kernestr. 2");
		db = pr.getBehandling().getDelbehandling(5);
		createCase(null, "00075", 7,  367, pr, null, "00-00:00:00", "00-00:32:00", "00-00:00:00");
		createCase(null, "00076", 8,  375, pr, null, "00-00:00:00", "00-00:32:00", "00-00:00:00");
		createCase(null, "00050", 3,  384, pr, null, "00-00:00:00", "00-00:32:00", "00-00:00:00");
		createCase(null, "00051", 7,  390, pr, db, "00-00:00:00", "00-00:00:00", "00-00:00:00");
		createCase(null, "00052", 7,  402, pr, db, "00-00:00:00", "00-00:00:00", "00-00:00:00");
		createCase(null, "00053", 7,  411, pr, db, "00-00:00:00", "00-00:00:35", "00-00:00:00");
		createCase(null, "00057", 7,  419, pr, db, "00-00:00:00", "00-00:02:00", "00-00:00:00");

		
	}

	/**
	 * Benyttes af createSomeObjects()
	 * 
	 * @param bakkestregkode
	 * @param produkttype
	 * @param palle
	 * @param dato
	 */
	private Mellemvare opretMellemvare(String bakkestregkode,
			Produkttype produkttype, Palle palle, GregorianCalendar dato) {
		Mellemvare m = new Mellemvare(bakkestregkode, produkttype, palle, dato);
		palle.addMellemvare(m);
		this.dao.gemMellemvare(m);
		return m;
	}

	public Produkttype createBehandlingsdata(String behandlingsstringKort, String behandlingsstringLang, String produkttypestringNavn, String produkttypestringBeskrivelse){
		//--Behandling--/
		Behandling behandling = Service.getInstance().opretBehandling(behandlingsstringLang);
		//--Produkttype--/
		Produkttype produkttype = Service.getInstance().opretProdukttype(produkttypestringNavn, produkttypestringBeskrivelse, behandling);
		//--Delbehandlinger--/
		Delbehandling d1 = new Toerring("Tørring 1-"+behandlingsstringKort, behandling, Validering.varighedStringTilMillisekunder("02-12:00"), Validering.varighedStringTilMillisekunder("02-18:00"), Validering.varighedStringTilMillisekunder("03-00:00"));
		behandling.addDelbehandling(d1, -1);

		Delbehandling d2 = new Dragering("Dragering 1-"+behandlingsstringKort, behandling, new Varighed("00-05:00"));
		behandling.addDelbehandling(d2, -1);

		Delbehandling d3 = new Toerring("Tørring 2-"+behandlingsstringKort, behandling, new Varighed("02-12:00"), new Varighed("02-18:00"), new Varighed("03-00:00"));
		behandling.addDelbehandling(d3, -1);

		Delbehandling d4 = new Dragering("Dragering 2-"+behandlingsstringKort, behandling, new Varighed("00-05:00"));
		behandling.addDelbehandling(d4, -1);

		Delbehandling d5 = new Toerring("Tørring 3-"+behandlingsstringKort, behandling, new Varighed("02-12:00"), new Varighed("02-18:00"), new Varighed("03-00:00"));
		behandling.addDelbehandling(d5, -1);

		Delbehandling d6 = new Dragering("Dragering 3-"+behandlingsstringKort, behandling, new Varighed("00-05:00"));
		behandling.addDelbehandling(d6, -1);

		Delbehandling d7 = new Toerring("Tørring 4-"+behandlingsstringKort, behandling, new Varighed("02-12:00"), new Varighed("02-18:00"), new Varighed("03-00:00"));
		behandling.addDelbehandling(d7, -1);

		return produkttype;
	}

	public void createCase(String mellemlagerplads, String pallestring, int antal, int basisStregkode, Produkttype produkttype, Delbehandling delbehandling, String tidTilMinTidDDTTMMSS, String tidTilIdealTidDDTTMMSS, String tidTilMaxTidDDTTMMSS){
		//---Mellemlagerplads--/
		MellemlagerPlads mellemlagerPlads = Service.getInstance().soegMellemlagerPlads(mellemlagerplads);
		if (mellemlagerPlads==null){
			mellemlagerPlads = new MellemlagerPlads(mellemlagerplads);
		}
		//--Palle--/
		Palle palle = Service.getInstance().soegPalle(pallestring);
		if (palle == null){
			palle = new Palle(pallestring);
		}
		if (delbehandling instanceof Toerring){
			Service.getInstance().placerPalleMellemvarelager(palle, mellemlagerPlads);
		}
		else if (delbehandling instanceof Dragering){
			Service.getInstance().sendPalleTilDragering(palle);
		}

		//Mellemvare-tidspunkter
		ArrayList<Long> varigheder = new ArrayList<Long>();
		Delbehandling d = produkttype.getBehandling().getDelbehandling(0);
		while (d != delbehandling && d != null){
			long varighed = 0;
			if (d instanceof Dragering){
				varighed = ((Dragering) d).getVarighed();
			}
			else if (d instanceof Toerring){
				varighed=((Toerring) d).getIdealVarighed();
			}
			for (int i = 0; i<varigheder.size();i++){
				varigheder.set(i, varigheder.get(i)+varighed);
			}
			varigheder.add(varighed);
			d = d.getNextDelbehandling();
		}
		long varighed = 0;
		if (d != null) {
			if (Validering.varighedStringDDTTMMSSTilMillisekunder(tidTilMinTidDDTTMMSS) > 0 && d instanceof Toerring) {
				varighed = ((Toerring) d).getMinVarighed() - Validering.varighedStringDDTTMMSSTilMillisekunder(tidTilMinTidDDTTMMSS);
			}
			else if (Validering.varighedStringDDTTMMSSTilMillisekunder(tidTilIdealTidDDTTMMSS) > 0) {
				if (d instanceof Toerring) {
					varighed = ((Toerring) d).getIdealVarighed()
							- Validering.varighedStringDDTTMMSSTilMillisekunder(tidTilIdealTidDDTTMMSS);
				}
				else if (d instanceof Dragering) {
					varighed = ((Dragering) d).getVarighed()- Validering.varighedStringDDTTMMSSTilMillisekunder(tidTilIdealTidDDTTMMSS);
				}
			}
			else if (Validering.varighedStringDDTTMMSSTilMillisekunder(tidTilMaxTidDDTTMMSS) > 0 && d instanceof Toerring) {
				varighed = ((Toerring) d).getMaxVarighed() - Validering.varighedStringDDTTMMSSTilMillisekunder(tidTilMaxTidDDTTMMSS);
			}
		}
		else {
			varighed = Validering.varighedStringDDTTMMSSTilMillisekunder(tidTilIdealTidDDTTMMSS);
		}
		for (int i = 0; i<varigheder.size();i++){
			varigheder.set(i, varigheder.get(i)+varighed);
		}
		varigheder.add(varighed);

		ArrayList<GregorianCalendar> tidspunkter = new ArrayList<GregorianCalendar>();
		for (Long v : varigheder){
			GregorianCalendar tidspunkt = new GregorianCalendar();
			tidspunkt.setTimeInMillis(System.currentTimeMillis()-v);
			tidspunkter.add(tidspunkt);
		}

		GregorianCalendar t = tidspunkter.get(0);
		tidspunkter.remove(t);
		//--Mellemvarer--/
		for (int i=1; i<= antal; i++){
			
			String stregkode = ""+(basisStregkode+i);
			while (stregkode.length()<8){
				stregkode = "0"+stregkode;
			}
			stregkode = "3"+stregkode;

			Mellemvare mellemvare = opretMellemvare(stregkode, produkttype, palle, t);
			mellemvare.addTidspunkter(tidspunkter, produkttype.getBehandling().getDelbehandling(1));
		}

		if (delbehandling==null){
			palle.setDrageringshal(null);
			palle.placerPalle(null);
			
		}

	}
}
