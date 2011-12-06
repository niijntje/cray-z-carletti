/**
 * 
 */
package model;

import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public abstract class Delbehandling {
	/**
	 * @author nijntje
	 *
	 */
	public enum DelbehandlingsType {
		DRAGERING, TOERRING
	}

	@Id
	@GeneratedValue
	private int id;
	@Enumerated(EnumType.STRING)
	private DelbehandlingsType delbehandlingstype;
	private Behandling behandling;
	private String navn;
	@ManyToOne
	private Delbehandling nextDelbehandling;

	public Delbehandling(){
		// Constructor uden parameter - JPA
	}
	
	public Delbehandling(String navn, Behandling behandling, DelbehandlingsType delbehandlingsType){
		this.delbehandlingstype = delbehandlingsType;
		this.setNavn(navn);
		this.behandling = behandling;
	}

	/**
	 * @return the delbehandlingstype
	 */
	public DelbehandlingsType getDelbehandlingstype() {
		return delbehandlingstype;
	}

	/**
	 * @param delbehandlingstype the delbehandlingstype to set
	 */
	public void setDelbehandlingstype(DelbehandlingsType delbehandlingstype) {
		this.delbehandlingstype = delbehandlingstype;
	}



	public Delbehandling getNextDelbehandling() {
		return nextDelbehandling;
	}

	public void setNextDelbehandling(Delbehandling nextDelbehandling) {
		this.nextDelbehandling = nextDelbehandling;
	}

	public Behandling getBehandling() {
		return behandling;
	}

	public void setBehandling(Behandling behandling) {
		this.behandling = behandling;
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	/**
	 * @param startTid Krav: startTid < System.currentTimeMillis() (Ellers kastes exception)
	 * @return resterende tider for hhv. minTid, idealTid og maxTid. Er tiden passeret, returneres tiden 0, modsat i getResterendeTidTilNaeste(),
	 * hvor der returneres en negativ tid, hvis maxTid er overskredet.
	 */
	public abstract long[] getResterendeTider(GregorianCalendar startTid)
			throws RuntimeException;

	/**
	 * @param startTid
	 *            Krav: startTid < System.currentTimeMillis() (Ellers kastes
	 *            exception)
	 * @return resterende tid. Hvis negativ er tiden overskredet. Alt efter
	 *         typen af delbehandling kan det betyde at varen skal kasseres
	 *         eller at den er f¾rdigbehandlet.
	 * @throws RuntimeException
	 */
	public abstract long getResterendeTidTilNaeste(GregorianCalendar startTid)
			throws RuntimeException;

	public boolean naesteDelbehandlingGyldig(DelbehandlingsType potentielNaesteDelbehandlingsType){
		if (potentielNaesteDelbehandlingsType==null){
			return this.getNextDelbehandling()==null;
		}
		else {
			if (getNextDelbehandling() != null){
				return this.getNextDelbehandling().getDelbehandlingstype()==potentielNaesteDelbehandlingsType;
			}
			else {
				return false;
			}
		}
	}

	public abstract boolean indenforTilladtBehandlingstid(GregorianCalendar startTid);

	@Override
	public String toString() {
		return getNavn();
	}

	public abstract String toStringLong();


}
