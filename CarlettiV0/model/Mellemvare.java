package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	private MellemvareStatus status;

	public Mellemvare(){
		
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
	
	/**
	 * Sammenligner to mellemvarer. Disse betragtes som ens, hvis de er af samme produkttype og har samme igangv¾rende delbehandling.
	 * Metoden tager _ikke_ h¿jde for hvor lang tid hver af mellemvarerne har v¾ret i gang med delbehandlingen.
	 * @param mellemvare
	 * @return
	 */
	public boolean erAfSammeType(Mellemvare mellemvare){
		if (mellemvare.getProdukttype()==this.getProdukttype() && mellemvare.getIgangvaerendeDelbehandling()==this.getIgangvaerendeDelbehandling()){
			return true;
		}
		else return false;
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
	 * @param status the status to set
	 */
	public void setStatus(MellemvareStatus status) {
		this.status = status;
	}

}
