package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
	private List<GregorianCalendar> tidspunkter;
	@ManyToOne
	private Produkttype produkttype;
	@ManyToOne
	private Delbehandling igangvaerendeDelbehandling;
	@ManyToOne
	private Palle palle;

	public Mellemvare(String bakkestregkode, Produkttype produkttype,
			Palle palle) {
		this.bakkestregkode = bakkestregkode;
		this.produkttype = produkttype;
		this.tidspunkter = new ArrayList<GregorianCalendar>();
		this.igangvaerendeDelbehandling = this.produkttype.getBehandling()
				.getDelbehandling(0);
		addNuvaerendeTidspunkt();
		setPalle(palle);
	}

	/**
	 * Benyttes af Service.createSomeObjects()
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
		addTidspunkt(starttid);
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
	 * Tilf�jer et tidspunkt til listen tidspunkter med f�lgende parametre:
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
	 * Tilf�jer systemets nuv�rende tidspunkter til listen tidspunkter
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
		this.palle = palle;
	}

	public void setPalle(Palle palle) {
		this.setPalleUD(palle);
		palle.addMellemvareUD(this);
	}

	public long[] getResterendeTider() {
		return this.getIgangvaerendeDelbehandling().getResterendeTider(
				getTidspunkter().get(tidspunkter.size() - 1));
	}

	public long getResterendeTidTilNaeste() {
		return this.getIgangvaerendeDelbehandling().getResterendeTidTilNaeste(
				getTidspunkter().get(tidspunkter.size() - 1));
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
	public void setIgangvaerendeDelbehandling(Delbehandling nextDelbehandling) {
		this.igangvaerendeDelbehandling = nextDelbehandling;

	}

}