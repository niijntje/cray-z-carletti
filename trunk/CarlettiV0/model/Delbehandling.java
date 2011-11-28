/**
 * 
 */
package model;

import java.util.GregorianCalendar;

import javax.persistence.ManyToOne;

/**
 * v.0.3
 * 
 * @author nijntje
 * 
 */
/**
 * @author nijntje
 *
 */
/**
 * @author nijntje
 *
 */
public abstract class Delbehandling {
	@ManyToOne
	private Behandling behandling;
	private String navn;
	@ManyToOne
	private Delbehandling nextDelbehandling;
	
	public Delbehandling(String navn, Behandling behandling){
		this.setNavn(navn);
		this.behandling = behandling;
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

	@Override
	public String toString() {
		return getNavn();
	}

	public abstract String toStringLong();

	
}
