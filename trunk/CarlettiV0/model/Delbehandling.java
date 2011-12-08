/**
 * DELBEHANDLING
 */
package model;

import java.util.GregorianCalendar;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Denne klasse repr¾senterer et trin i en behandling/opskrift, der kan tilh¿re Žn
 * af en r¾kke foruddefinerede delbehandlingstyper defineret af den implementerende
 * klasse og attributten DelbehandlingsType
 * 
 * @author Rita Holst Jacobsen
 * @author Rasmus Cederdorff: JPA
 * 
 */

@Entity
public abstract class Delbehandling {

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
	@ManyToOne(cascade = CascadeType.ALL)
	private Delbehandling nextDelbehandling;

	public Delbehandling() {
		// Constructor uden parameter - JPA
	}

	/**
	 * @param navn
	 * @param behandling
	 * @param delbehandlingsType fx TOERRING, DRAGERING
	 */
	public Delbehandling(String navn, Behandling behandling, DelbehandlingsType delbehandlingsType) {
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
	 * @param delbehandlingstype
	 *            the delbehandlingstype to set
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
	 * @param startTid Krav: startTid < System.currentTimeMillis() (Ellers kastes
	 * exception)
	 * @return Resterende tider i millisekunder for hver af de tidsfrister, den implementerende
	 * klasse har defineret. Er tiden passeret, returneres tiden 0, modsat i getResterendeTidTilNaeste(), 
	 * hvor der returneres en negativ tid, hvis maxTid er overskredet.
	 * @throws RuntimeException
	 */
	public abstract long[] getResterendeTider(GregorianCalendar startTid) throws RuntimeException;

	/**
	 * @param startTid Krav: startTid < System.currentTimeMillis() (Ellers kastes
	 * exception)
	 * @return resterende tid i millisekunder. Hvis negativ er tiden overskredet. Alt efter typen af 
	 * delbehandling kan det betyde at varen skal kasseres eller at den er f¾rdigbehandlet.
	 * @throws RuntimeException
	 */
	public abstract long getResterendeTidTilNaeste(GregorianCalendar startTid)
			throws RuntimeException;

	/**
	 * @param potentielNaesteDelbehandlingsType. Hvis null: Returnerer om nextDelbehandling er null
	 * @return Om nextDelbehandling er af typen potentielNaesteDelbehandlingsType
	 */
	public boolean naesteDelbehandlingGyldig(
			DelbehandlingsType potentielNaesteDelbehandlingsType) {
		if (potentielNaesteDelbehandlingsType == null) {
			return this.getNextDelbehandling() == null;
		} else {
			if (getNextDelbehandling() != null) {
				return this.getNextDelbehandling().getDelbehandlingstype() == potentielNaesteDelbehandlingsType;
			} else {
				return false;
			}
		}
	}

	/**
	 * @param startTid Starttid for den konkrete delbehandling, der sp¿rges til
	 * @return Om varigheden af en delbehandling startet pŒ det angivne tidspunkt ligger indenfor det 
	 * tilladte tidsrum iflg. den implementerede klasses definition af dette
	 */
	public abstract boolean indenforTilladtBehandlingstid(
			GregorianCalendar startTid);

	@Override
	public String toString() {
		return getNavn();
	}

	/** Benyttes af gui
	 * @return
	 */
	public abstract String toStringLong();

}
