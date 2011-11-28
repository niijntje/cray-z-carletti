/**
 * 
 */
package service;

import java.util.GregorianCalendar;

import dao.DAO;
import dao.ListDao;

import model.Behandling;
import model.MellemlagerPlads;
import model.Mellemvare;
import model.Palle;
import model.Produkttype;

/**
 * @author nijntje
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
	 * Opretter objekter og tilf퓂er dem til Dao.
	 */
	public void createSomeObjects() {

		Palle pa1 = Service.getInstance().opretPalle("20000001");
		Service.getInstance().opretPalle("20000002");
		Palle palle1 = Service.getInstance().opretPalle("00001");
		Service.getInstance().opretPalle("00002");
		Service.getInstance().opretPalle("00003");

		MellemlagerPlads pl1 = Service.getInstance().opretMellemlagerPlads(
				"100100101");
		Service.getInstance().opretMellemlagerPlads("100100102");
		Service.getInstance().opretMellemlagerPlads("001");
		Service.getInstance().opretMellemlagerPlads("002");
		Service.getInstance().opretMellemlagerPlads("003");
		Service.getInstance().opretMellemlagerPlads("004");
		Service.getInstance().opretMellemlagerPlads("005");
		Service.getInstance().opretMellemlagerPlads("006");
		Service.getInstance().opretMellemlagerPlads("007");
		Service.getInstance().opretMellemlagerPlads("008");
		Service.getInstance().opretMellemlagerPlads("009");
		Service.getInstance().opretMellemlagerPlads("010");

		Long varighed1 = Validering.varighedStringTilLong("02:14:00");
		Long varighed2 = Validering.varighedStringTilLong("02:16:30");
		Long varighed3 = Validering.varighedStringTilLong("02:17:00");
		Long varighed4 = Validering.varighedStringTilLong("02:18:00");

		Long varighed5 = Validering.varighedStringTilLong("01:15:00");
		Long varighed6 = Validering.varighedStringTilLong("01:15:15");
		Long varighed7 = Validering.varighedStringTilLong("01:15:30");
		Long varighed8 = Validering.varighedStringTilLong("01:15:45");

		Long varighed9 = Validering.varighedStringTilLong("00:01:00");
		Long varighed10 = Validering.varighedStringTilLong("00:14:00");
		Long varighed11 = Validering.varighedStringTilLong("00:02:15");
		Long varighed12 = Validering.varighedStringTilLong("00:00:45");

		Behandling b = Service.getInstance().opretBehandling("Behandling1");
		Service.getInstance().opretToerring("T퓊ring1", b, 12 * 60 * 60 * 1000,
				15 * 60 * 60 * 1000, 20 * 60 * 60 * 1000, -1);

		Behandling b3 = Service.getInstance().opretBehandling(
				"B3 - M똭egrus og lakridspinde. Kernest퓊relse 2.5-3.5");
		Service.getInstance().opretToerring("T퓊ring 1a", b3, varighed1,
				varighed2, varighed4, -1);
		Service.getInstance()
				.opretDragering("Dragering 2e", b3, varighed10, -1);
		Service.getInstance().opretToerring("T퓊ring 1b", b3, varighed5,
				varighed6, varighed8, -1);
		Service.getInstance()
				.opretDragering("Dragering 2e", b3, varighed11, -1);
		Service.getInstance().opretToerring("T퓊ring 1c", b3, varighed9,
				varighed10, varighed11, -1);

		Produkttype p = Service.getInstance().opretProdukttype(
				"Lakridspinde 3A",
				"Hvide lakridspinde med kernest퓊relse 2.\nOpskrift: B2", b);
		Produkttype p2 = Service.getInstance().opretProdukttype("Skumbananer",
				"Dejlig skum overtrukket af chokolade", b);
		Produkttype pt1 = Service.getInstance().opretProdukttype(
				"Lakridspinde 1",
				"Gr퓆ne lakridspinde med kernest퓊relse 3.\nOpskrift: B3", b3);
		Produkttype pt2 = Service
				.getInstance()
				.opretProdukttype(
						"M똭egrus 2B",
						"Rester fra lakridspinde med varierende kernest퓊relse.\nOpskrift: B3",
						b3);

		GregorianCalendar dato6 = new GregorianCalendar();
		dato6.setTimeInMillis(System.currentTimeMillis() - (varighed9));

		opretMellemvare("01", p, pa1, dato6);
		opretMellemvare("02", p2, palle1, dato6);
		opretMellemvare("300000001", pt1, pa1, dato6);
		opretMellemvare("300000002", pt1, pa1, dato6);
		opretMellemvare("300000003", pt2, pa1, dato6);
		opretMellemvare("300000004", pt1, pa1, dato6);
		opretMellemvare("300000005", pt1, pa1, dato6);
		opretMellemvare("300000006", pt2, pa1, dato6);
		opretMellemvare("300000007", pt1, pa1, dato6);

		Service.getInstance().placerPalleMellemvarelager(pa1, pl1);

		GregorianCalendar dato7 = new GregorianCalendar();
		dato7.setTimeInMillis(System.currentTimeMillis() - varighed3);
		GregorianCalendar dato8 = new GregorianCalendar();
		dato8.setTimeInMillis(System.currentTimeMillis() - varighed11 * 2);
		GregorianCalendar dato9 = new GregorianCalendar();
		dato9.setTimeInMillis(System.currentTimeMillis() - varighed12);
		GregorianCalendar dato10 = new GregorianCalendar();
		dato10.setTimeInMillis(System.currentTimeMillis() - varighed11);

		for (Mellemvare m : pa1.getMellemvarer()) {
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
				m.setIgangvaerendeDelbehandling(m
						.getIgangvaerendeDelbehandling().getNextDelbehandling());
				m.addTidspunkt(dato9);
			}
		}
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
	
	/**
	 * Udskriver alle mellemlagerPladser, der er gemt i databasen
	 */
	public void udskrivMellemlagerPladser() {

		for (int i = 0; i < dao.mellemlagerPladser().size(); i++) {
			System.out.println(dao.mellemlagerPladser().get(i).toStringLong());
		}
	}


}
