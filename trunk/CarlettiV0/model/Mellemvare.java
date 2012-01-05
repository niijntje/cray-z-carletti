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
 * Klassen repr�senterer den m�ngde mellemvarer der findes i �n bakke. For hver gang der st�bes 
 * en m�ngde mellemvarer vil der alts� blive oprettet flere objekter af Mellemvare. Systemet antager, 
 * at det er muligt at f�lge hver enkelt bakkefuld hele vejen gennem behandlingsprocessen, uanset om 
 * mellemvarerne i realiteten blandes og fordeles p� en ny m�de ved hver dragering (det er dog muligt 
 * at �ndre den tilknyttede bakke-stregkode).
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
	 * Opretter en instans af Mellemvare og p�begynder den f�rste delbehandling, tilf�jer det 
	 * nuv�rende tidspunkt til behandlingsloggen, dvs. listen tidspunkter,
	 * s�tter MellemvareStatus til "under behandling" (UNDERBEHANDLING) og knytter mellemvaren 
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
	 * @param starttid Kan s�ttes, da dette g�r det noget lettere at teste systemet ;-)
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
	 * Tilf�jer et tidspunkt til listen tidspunkter med f�lgende parametre:
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
	 * Tilf�jer systemets nuv�rende tidspunkt til listen tidspunkter
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
	 * Igangs�tter n�ste delbehandling
	 * 
	 * @author Rasmus Cederdorff
	 */
	public void goToNextDelbehandling() {
		this.igangvaerendeDelbehandling = igangvaerendeDelbehandling
				.getNextDelbehandling();
		this.addNuvaerendeTidspunkt();
	}
	
	/**
	 * Krav: Der findes en igangv�rende delbehandling
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
	 * @return Array med resterende tid i millisekunder til de(n) n�ste tidsfrist(er)
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
	 * @return Resterende tid i millisekunder til n�ste tidsfrist er n�et
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
	 * produkttype og har samme igangv�rende delbehandling. Metoden tager _ikke_
	 * h�jde for hvor lang tid hver af mellemvarerne har v�ret i gang med
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
	 * produkttype og igangv�rende delbehandling.
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
	 * Tjekker om en given delbehandlingstype er gyldig som n�ste delbehandling for den 
	 * p�g�ldende mellemvare, samt om behandlingstiden for den igangv�rende delbehandling
	 * ligger indenfor den tilladte behandlingstid defineret af Delbehandling.
	 * @param delbehandling
	 *            . Hvis null, skal den igangv�rende delbehandling v�re den
	 *            sidste i behandlingens delbehandlingsliste. Ellers skal
	 *            delbehandling v�re af samme klasse som den n�ste i
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
	 * @return Om behandlingstiden har varet l�nge nok/ikke for l�nge, og alts� dermed om mellemvaren
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
	 * @return Det seneste tidspunkt i tidspunkter - dvs. det tidspunkt, den igangv�rende
	 * delbehandling p�begyndtes.
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
	 * Testmode betyder, at naesteDelbehandlingGyldig() ikke tager h�jde for
	 * om tidsfristen er overholdt, men udelukkende kigger p� om den foresl�ede
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
	 * Testmode betyder, at naesteDelbehandlingGyldig() ikke tager h�jde for
	 * om tidsfristen er overholdt, men udelukkende kigger p� om den foresl�ede
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
