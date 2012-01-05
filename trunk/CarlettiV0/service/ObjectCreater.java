/**
 * OBJECTCREATER
 */
package service;

import java.util.GregorianCalendar;

import dao.DAO;
import dao.ListDao;

import model.Behandling;
import model.Delbehandling;
import model.MellemlagerPlads;
import model.Mellemvare;
import model.Palle;
import model.Produkttype;

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
	}

	/**
	 * Benyttes af createSomeObjects()
	 * 
	 * @param bakkestregkode
	 * @param produkttype
	 * @param palle
	 * @param dato
	 */
	private void opretMellemvare(String bakkestregkode,
			Produkttype produkttype, Palle palle, GregorianCalendar dato) {
		Mellemvare m = new Mellemvare(bakkestregkode, produkttype, palle, dato);
		palle.addMellemvare(m);
		this.dao.gemMellemvare(m);
	}

}
