package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
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
 * v.0.3
 * 
 * @author nijntje
 * 
 */
@Entity
public class Mellemvare {
	@Id
	private String bakkestregkode;
	@ElementCollection
	@CollectionTable(name = "tidspunkter")
	@Temporal(TemporalType.TIMESTAMP)
	private List<GregorianCalendar> tidspunkter;
	@ManyToOne
	private Produkttype produkttype;
	@ManyToOne
	private Delbehandling igangvaerendeDelbehandling;
	@ManyToOne
	private Palle palle;
	@Enumerated(EnumType.STRING)
	private MellemvareStatus status;
	private boolean testMode;

	/**
	 * @return the testMode
	 */
	public boolean isTestMode() {
		return testMode;
	}

	/**
	 * @param testMode
	 *            the testMode to set
	 */
	public void setTestMode(boolean testMode) {
		this.testMode = testMode;
	}

	public Mellemvare() {

	}

	public Mellemvare(String bakkestregkode, Produkttype produkttype,
			Palle palle) {
		this.bakkestregkode = bakkestregkode;
		this.produkttype = produkttype;
		this.tidspunkter = new ArrayList<GregorianCalendar>();
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
	 * @param starttid
	 */
	public Mellemvare(String bakkestregkode, Produkttype produkttype,
			Palle palle, GregorianCalendar starttid) {
		this.bakkestregkode = bakkestregkode;
		this.produkttype = produkttype;
		this.tidspunkter = new ArrayList<GregorianCalendar>();
		this.igangvaerendeDelbehandling = this.produkttype.getBehandling()
				.getDelbehandling(0);
		this.status = MellemvareStatus.UNDERBEHANDLING;
		setPalle(palle);

		addTidspunkt(starttid);
		this.setTestMode(true);
	}

	public String getBakkestregkode() {
		return bakkestregkode;
	}

	public void setBakkestregkode(String bakkestregkode) {
		this.bakkestregkode = bakkestregkode;
	}

	public ArrayList<GregorianCalendar> getTidspunkter() {
		return new ArrayList<GregorianCalendar>(tidspunkter);
	}

	public Delbehandling getIgangvaerendeDelbehandling() {
		return igangvaerendeDelbehandling;
	}

	/**
	 * Tilf¿jer et tidspunkt til listen tidspunkter med f¿lgende parametre:
	 * 
	 * @param year
	 * @param month
	 * @param dayOfMonth
	 * @param hourOfDay
	 * @param minute
	 */
	public void addTidspunkt(int year, int month, int dayOfMonth,
			int hourOfDay, int minute) {
		GregorianCalendar calendar = new GregorianCalendar(year, month - 1,
				dayOfMonth, hourOfDay, minute);
		tidspunkter.add(calendar);
	}

	/**
	 * Benyttes af Service.createSomeObjects()
	 * 
	 * @param gCal
	 */
	public void addTidspunkt(GregorianCalendar gCal) {
		tidspunkter.add(gCal);
	}

	/**
	 * Tilf¿jer systemets nuv¾rende tidspunkt til listen tidspunkter
	 */
	public void addNuvaerendeTidspunkt() {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(System.currentTimeMillis());
		this.tidspunkter.add(calendar);
	}

	public void goToNextDelbehandling() {
		this.igangvaerendeDelbehandling = igangvaerendeDelbehandling
				.getNextDelbehandling();
		this.addNuvaerendeTidspunkt();
	}

	public Produkttype getProdukttype() {
		return produkttype;
	}

	public void setProdukttype(Produkttype produkttype) {
		this.produkttype = produkttype;
	}

	public Palle getPalle() {
		return palle;
	}

	public void setPalleUD(Palle palle) {
		if (this.palle != null) {
			this.palle.removeMellemvareUD(this);
		}
		this.palle = palle;
	}

	public void setPalle(Palle palle) {
		this.setPalleUD(palle);
		palle.addMellemvareUD(this);
	}

	public long[] getResterendeTider() {
		long[] tider = new long[0];
		if (this.getStatus() == MellemvareStatus.UNDERBEHANDLING) {
			tider = this.getIgangvaerendeDelbehandling().getResterendeTider(
					getTidspunkter().get(tidspunkter.size() - 1));
		}
		return tider;
	}

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
	 * produkttype og har samme igangv¾rende delbehandling. Metoden tager _ikke_
	 * h¿jde for hvor lang tid hver af mellemvarerne har v¾ret i gang med
	 * delbehandlingen.
	 * 
	 * @param mellemvare
	 * @return
	 */
	public boolean erAfSammeType(Mellemvare mellemvare) {
		return this.erAfSammeType(mellemvare.getProdukttype(),
				mellemvare.getIgangvaerendeDelbehandling());
	}

	public boolean erAfSammeType(Produkttype produkttype,
			Delbehandling delbehandling) {
		if (produkttype == this.getProdukttype()
				&& delbehandling == this.getIgangvaerendeDelbehandling()) {
			return true;
		} else
			return false;
	}

	/**
	 * @param delbehandling
	 *            . Hvis null, skal den igangv¾rende delbehandling v¾re den
	 *            sidste i behandlingens delbehandlingsliste. Ellers skal
	 *            delbehandling v¾re af samme klasse som den n¾ste i
	 *            behandlingens delbehandlingsliste.
	 * @return
	 */
	public boolean naesteDelbehandlingGyldig(
			DelbehandlingsType potentielNaesteDelbehandlingsType) {
		boolean gyldig = false;
		if (igangvaerendeDelbehandling != null) {
			gyldig = igangvaerendeDelbehandling
					.naesteDelbehandlingGyldig(potentielNaesteDelbehandlingsType);
		}
		if (!isTestMode()) {
			gyldig = gyldig && indenforTilladtBehandlingstid();
		}
		return gyldig;
	}

	private boolean indenforTilladtBehandlingstid() {
		return igangvaerendeDelbehandling
				.indenforTilladtBehandlingstid(getSidsteStarttid());
	}

	private GregorianCalendar getSidsteStarttid() {
		return getTidspunkter().get(tidspunkter.size() - 1);
	}

	@Override
	public String toString() {
		return this.getBakkestregkode() + "\t" + this.getProdukttype();
	}

	/**
	 * Benyttes af ObjectCreater.createSomeObjects()
	 * 
	 * @param nextDelbehandling
	 */
	public void setIgangvaerendeDelbehandling(Delbehandling nyDelbehandling) {
		this.igangvaerendeDelbehandling = nyDelbehandling;

	}

	/**
	 * @return the status
	 */
	public MellemvareStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(MellemvareStatus status) {
		this.status = status;
	}

}
