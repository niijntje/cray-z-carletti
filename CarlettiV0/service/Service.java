package service;

import java.util.ArrayList;

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
	 * @param b
	 * @param delbandlingNavn
	 * @param delbandlingIndex
	 */
	public void tilfoejDelbehandling(Behandling b, String delbandlingNavn,
			int delbandlingIndex) {

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
	 * Placerer en palle på en placering i mellemvarelageret
	 * 
	 * @param palle
	 * @param placering
	 */
	public void placerPalleMellemvarelager(Palle palle,
			MellemlagerPlads placering) {
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
			System.out.println(dao.mellemlagerPladser().get(i));
		}
	}

	public void createSomeObjects() {
		Palle palle1 = opretPalle("00001");
		Palle palle2 = opretPalle("00002");

		MellemlagerPlads mPlads1 = opretMellemlagerPlads("001");
		MellemlagerPlads mPlads2 = opretMellemlagerPlads("002");
		MellemlagerPlads mPlads3 = opretMellemlagerPlads("003");
		MellemlagerPlads mPlads4 = opretMellemlagerPlads("004");
		MellemlagerPlads mPlads5 = opretMellemlagerPlads("005");
		MellemlagerPlads mPlads6 = opretMellemlagerPlads("006");
		MellemlagerPlads mPlads7 = opretMellemlagerPlads("007");
		MellemlagerPlads mPlads8 = opretMellemlagerPlads("008");
		MellemlagerPlads mPlads9 = opretMellemlagerPlads("009");
		MellemlagerPlads mPlads10 = opretMellemlagerPlads("010");

		Behandling b = opretBehandling("Behandling1");
		Delbehandling toerring = new Toerring("Tørring1", b, 12, 15, 20);

		b.addDelbehandling(toerring, 0);

		Produkttype p = opretProdukttype("Lakridspinde", "...", b);
		Produkttype p2 = opretProdukttype("Skumbananer",
				"Dejlig skum overtrukket af chokolade", b);
		opretMellemvare("01", p, palle2);
		placerPalleMellemvarelager(palle2, mPlads1);
		opretMellemvare("02", p2, palle1);
		placerPalleMellemvarelager(palle1, mPlads3);

	}
}
