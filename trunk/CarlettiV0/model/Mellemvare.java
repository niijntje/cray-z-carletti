/**
 * MELLEMVARE
 */
package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import model.Delbehandling.DelbehandlingsType;

/**
 * Klassen repræsenterer den mængde mellemvarer der findes i én bakke. For hver gang der støbes 
 * en mængde mellemvarer vil der altså blive oprettet flere objekter af Mellemvare. Systemet antager, 
 * at det er muligt at følge hver enkelt bakkefuld hele vejen gennem behandlingsprocessen, uanset om 
 * mellemvarerne i realiteten blandes og fordeles på en ny måde ved hver dragering (det er dog muligt 
 * at ændre den tilknyttede bakke-stregkode).
 * 
 * @author Rasmus Cederdorff: JPA og hvor angivet ved metoderne
 * @author Rita Holst Jacobsen: Hvor angivet ved metoderne
 * 
 */

@Entity
public class Mellemvare {
	@Id
	private String bakkestregkode;
	@ElementCollection
	@CollectionTable(name = "tidspunkter")
	@Temporal(TemporalType.TIME)
	private List<GregorianCalendar> tidspunkter;

//	@Temporal(TemporalType.TIMESTAMP)
	private List<Long> millisekundTider;	
//	@Temporal(TemporalType.TIME)

	@ManyToOne
	private Produkttype produkttype;
	@ManyToOne
	private Delbehandling igangvaerendeDelbehandling;
	@ManyToOne
	private Palle palle;
	@Enumerated(EnumType.STRING)
	private MellemvareStatus status;
	private boolean testMode;

	public Mellemvare() {

	}

	/**
	 * Opretter en instans af Mellemvare og påbegynder den første delbehandling, tilføjer det 
	 * nuværende tidspunkt til behandlingsloggen, dvs. listen tidspunkter,
	 * sætter MellemvareStatus til "under behandling" (UNDERBEHANDLING) og knytter mellemvaren 
	 * til den angivne palle.
	 * 
	 * @param bakkestregkode
	 * @param produkttype
	 * @param palle
	 * 
	 * @author Rasmus Cederdorff
	 */
	public Mellemvare(String bakkestregkode, Produkttype produkttype,
			Palle palle) {
		this.bakkestregkode = bakkestregkode;
		this.produkttype = produkttype;
		this.tidspunkter = new ArrayList<GregorianCalendar>();
		this.millisekundTider = new ArrayList<Long>();
		this.igangvaerendeDelbehandling = this.produkttype.getBehandling()
				.getDelbehandling(0);
		this.status = MellemvareStatus.UNDERBEHANDLING;
		addNuvaerendeTidspunkt();
		setPalle(palle);
	}

	/**
	 * Benyttes KUN af ObjectCreater.createSomeObjects()
	 * 
	 * @param bakkestregkode
	 * @param produkttype
	 * @param palle
	 * @param starttid Kan sættes, da dette gør det noget lettere at teste systemet ;-)
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public Mellemvare(String bakkestregkode, Produkttype produkttype,
			Palle palle, GregorianCalendar starttid) {
		this(bakkestregkode, produkttype, palle);
		this.tidspunkter = new ArrayList<GregorianCalendar>();
		tidspunkter.add(starttid);
		this.setTestMode(true);
	}
	

	/**
	 * Krav: tidspunkter.size() > 0
	 * @param tidspunkter
	 * @param foersteDelbehandling
	 */
	public void addTidspunkter(ArrayList<GregorianCalendar> tidspunkter, Delbehandling foersteDelbehandling) {
		this.igangvaerendeDelbehandling = foersteDelbehandling;
		Iterator<GregorianCalendar> tidspunktIterator = tidspunkter.iterator();
		this.tidspunkter.add(tidspunktIterator.next());
		while (tidspunktIterator.hasNext() && igangvaerendeDelbehandling != null){
			goToNextDelbehandling(tidspunktIterator.next());
		}	
		
	}

	/**
	 * @return
	 * @author Rasmus Cederdorff
	 */
	public String getBakkestregkode() {
		return bakkestregkode;
	}

	/**
	 * @param bakkestregkode
	 * @author Rasmus Cederdorff
	 */
	public void setBakkestregkode(String bakkestregkode) {
		this.bakkestregkode = bakkestregkode;
	}

	/**
	 * @return
	 * @author Rasmus Cederdorff
	 */
	public ArrayList<GregorianCalendar> getTidspunkter() {
		return new ArrayList<GregorianCalendar>(tidspunkter);
	}

	/**
	 * @return
	 * @author Rasmus Cederdorff
	 */
	public Delbehandling getIgangvaerendeDelbehandling() {
		return igangvaerendeDelbehandling;
	}

	/**
	 * Tilføjer et tidspunkt til listen tidspunkter med følgende parametre:
	 * 
	 * @param year
	 * @param month
	 * @param dayOfMonth
	 * @param hourOfDay
	 * @param minute
	 * 
	 * @author Rasmus Cederdorff
	 */
	public void addTidspunkt(int year, int month, int dayOfMonth, int hourOfDay, int minute) {
		GregorianCalendar calendar = new GregorianCalendar(year, month - 1, dayOfMonth, hourOfDay, minute);
		tidspunkter.add(calendar);
	}

	/**
	 * Tilføjer systemets nuværende tidspunkt til listen tidspunkter
	 * 
	 * @author Rasmus Cederdorff
	 */
	public void addNuvaerendeTidspunkt() {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(System.currentTimeMillis());
		this.tidspunkter.add(calendar);
		this.millisekundTider.add(calendar.getTimeInMillis());
	}

	/**
	 * Igangsætter næste delbehandling
	 * 
	 * @author Rasmus Cederdorff
	 */
	public void goToNextDelbehandling() {
		this.igangvaerendeDelbehandling = igangvaerendeDelbehandling
				.getNextDelbehandling();
		this.addNuvaerendeTidspunkt();
	}
	
	/**
	 * Krav: Der findes en igangværende delbehandling
	 * @param tidspunkt
	 */
	public void goToNextDelbehandling(GregorianCalendar tidspunkt) {
		this.igangvaerendeDelbehandling = igangvaerendeDelbehandling.getNextDelbehandling();
		this.addTidspunkt(tidspunkt);
	}

	/**
	 * @return
	 * @author Rasmus Cederdorff
	 */
	public Produkttype getProdukttype() {
		return produkttype;
	}

	/**
	 * @param produkttype
	 * @author Rasmus Cederdorff
	 */
	public void setProdukttype(Produkttype produkttype) {
		this.produkttype = produkttype;
	}

	/**
	 * @return
	 * @author Rasmus Cederdorff
	 */
	public Palle getPalle() {
		return palle;
	}

	/**
	 * @param palle
	 * @author Rasmus Cederdorff
	 */
	public void setPalleUD(Palle palle) {
		if (this.palle != null) {
			this.palle.removeMellemvareUD(this);
		}
		this.palle = palle;
	}

	/**
	 * @param palle
	 * @author Rasmus Cederdorff
	 */
	public void setPalle(Palle palle) {
		this.setPalleUD(palle);
		palle.addMellemvareUD(this);
	}

	/**
	 * @return Array med resterende tid i millisekunder til de(n) næste tidsfrist(er)
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public long[] getResterendeTider() {
		long[] tider = new long[0];
		if (this.getStatus() == MellemvareStatus.UNDERBEHANDLING) {
			try {
	         tider = this.getIgangvaerendeDelbehandling().getResterendeTider(
	         		getTidspunkter().get(tidspunkter.size() - 1));
         }
         catch (ClassCastException e) {
         	System.out.println("JPA-problemer med cast af String til GregorianCalendar igenigen");
         	System.out.println(getTidspunkter().get(tidspunkter.size() - 1).toString());
//         	try {
//         		String calendarstring = getTidspunkter().get(tidspunkter.size() - 1);
//         	}
//         	catch (Exception f) {
//         		System.out.println(f.getStackTrace());
//         	}
//         	
//	         e.printStackTrace();
         }
		}
		return tider;
	}

	/**
	 * @return Resterende tid i millisekunder til næste tidsfrist er nået
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public long getResterendeTidTilNaeste() {
		long tid = 0;
		if (this.getIgangvaerendeDelbehandling() != null) {
			tid = this.getIgangvaerendeDelbehandling()
					.getResterendeTidTilNaeste(getSidsteStarttid());
		}
		return tid;
	}

	/**
	 * Sammenligner to mellemvarer. Disse betragtes som ens, hvis de er af samme
	 * produkttype og har samme igangværende delbehandling. Metoden tager _ikke_
	 * højde for hvor lang tid hver af mellemvarerne har været i gang med
	 * delbehandlingen.
	 * 
	 * @param mellemvare
	 * @return Om denne Mellemvare (this) er af samme type som mellemvare
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public boolean erAfSammeType(Mellemvare mellemvare) {
		return this.erAfSammeType(mellemvare.getProdukttype(),
				mellemvare.getIgangvaerendeDelbehandling());
	}

	/**
	 * Returnerer om mellemvaren er af en bestemt 'type', defineret som kombinationen af mellemvarens
	 * produkttype og igangværende delbehandling.
	 * @param produkttype
	 * @param delbehandling
	 * @return
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public boolean erAfSammeType(Produkttype produkttype,
			Delbehandling delbehandling) {
		if (produkttype == this.getProdukttype()
				&& delbehandling == this.getIgangvaerendeDelbehandling()) {
			return true;
		} else
			return false;
	}

	/**
	 * Tjekker om en given delbehandlingstype er gyldig som næste delbehandling for den 
	 * pågældende mellemvare, samt om behandlingstiden for den igangværende delbehandling
	 * ligger indenfor den tilladte behandlingstid defineret af Delbehandling.
	 * @param delbehandling
	 *            . Hvis null, skal den igangværende delbehandling være den
	 *            sidste i behandlingens delbehandlingsliste. Ellers skal
	 *            delbehandling være af samme klasse som den næste i
	 *            behandlingens delbehandlingsliste.
	 * @return Om nextDelbehandling er af den angivne delbehandlingstype, samt om 
	 * indenforTilladtBehandlingstid() returnerer true.
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public boolean naesteDelbehandlingGyldig(
			DelbehandlingsType potentielNaesteDelbehandlingsType) {
		boolean gyldig = false;
		if (igangvaerendeDelbehandling != null) {
			gyldig = igangvaerendeDelbehandling.naesteDelbehandlingGyldig(potentielNaesteDelbehandlingsType);
		}
		if (!isTestMode()) {
			gyldig = gyldig && indenforTilladtBehandlingstid();
		}
		return gyldig;
	}

	/**
	 * @return Om behandlingstiden har varet længe nok/ikke for længe, og altså dermed om mellemvaren
	 * er klar til at blive plukket.
	 * 
	 * @author Rita Holst Jacobsen
	 */
	private boolean indenforTilladtBehandlingstid() {
		return igangvaerendeDelbehandling.indenforTilladtBehandlingstid(getSidsteStarttid());
	}

	/** Anvendes internt i klassen af metoderne getResterendeTidTilNaeste() og
	 * indenforTilladtBehandlingstid
	 * 
	 * @return Det seneste tidspunkt i tidspunkter - dvs. det tidspunkt, den igangværende
	 * delbehandling påbegyndtes.
	 * 
 	 * @author Rita Holst Jacobsen
	 */
	private GregorianCalendar getSidsteStarttid() {
		GregorianCalendar sidsteStarttid = new GregorianCalendar();
		try {
			sidsteStarttid = getTidspunkter().get(tidspunkter.size() - 1);
		}
		catch (ClassCastException c){
			System.out.println("JPA-problem med at caste String som GregorianCalendar");
			System.out.println(getTidspunkter().get(tidspunkter.size() - 1).toString());
		}
		return sidsteStarttid;
	}

	@Override
	public String toString() {
		return this.getBakkestregkode() + "\t" + this.getProdukttype();
	}

	/**
	 * Benyttes bl.a. af ObjectCreater.createSomeObjects()
	 * 
	 * @param nextDelbehandling
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public void setIgangvaerendeDelbehandling(Delbehandling nyDelbehandling) {
		this.igangvaerendeDelbehandling = nyDelbehandling;
	}

	/**
	 * @return the status
	 * @author Rasmus Cederdorff
	 */
	public MellemvareStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 * @author Rasmus Cederdorff
	 */
	public void setStatus(MellemvareStatus status) {
		this.status = status;
	}
	
	/**
	 * Testmode betyder, at naesteDelbehandlingGyldig() ikke tager højde for
	 * om tidsfristen er overholdt, men udelukkende kigger på om den foreslåede
	 * delbehandling er af den rigtige type.
	 * 
	 * @return the testMode
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public boolean isTestMode() {
		return testMode;
	}

	/**
	 * Testmode betyder, at naesteDelbehandlingGyldig() ikke tager højde for
	 * om tidsfristen er overholdt, men udelukkende kigger på om den foreslåede
	 * delbehandling er af den rigtige type.
	 * 
	 * @param testMode the testMode to set
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public void setTestMode(boolean testMode) {
		this.testMode = testMode;
	}
	
	/**
	 * Benyttes KUN af Service.createSomeObjects()
	 * 
	 * @param gCal
	 * @author Rasmus Cederdorff
	 */
	public void addTidspunkt(GregorianCalendar gCal) {
		tidspunkter.add(gCal);
	}

}
