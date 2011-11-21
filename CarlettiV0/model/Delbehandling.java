/**
 * 
 */
package model;

import java.util.GregorianCalendar;

/**
 * v.0.3
 * @author nijntje
 *
 */
public abstract class Delbehandling {
	
	private Behandling behandling;
	private String navn;
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
	 * @return resterende tid. Hvis negativ er tiden overskredet. Alt efter typen af delbehandling kan det betyde at varen skal kasseres
	 */
	public abstract long getResterendeTid(GregorianCalendar startTid) throws RuntimeException;
	
	public String toString(){
		return getNavn();
	}

}
