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
	 * @return resterende tider for hhv. minTid, idealTid og maxTid. Er tiden passeret, returneres tiden 0, modsat i getResterendeTidTilNaeste(),
	 * hvor der returneres en negativ tid, hvis maxTid er overskredet.
	 */
	public abstract long[] getResterendeTider(GregorianCalendar startTid) throws RuntimeException;
	
	/**
	 * @param startTid Krav: startTid < System.currentTimeMillis() (Ellers kastes exception)
	 * @return resterende tid. Hvis negativ er tiden overskredet. Alt efter typen af delbehandling kan det betyde at varen skal kasseres eller at den er f¾rdigbehandlet.
	 * @throws RuntimeException
	 */
	public abstract long getResterendeTidTilNaeste(GregorianCalendar startTid) throws RuntimeException;
	
	public String toString(){
		return getNavn();
	}

	public abstract String toStringLong();
	
	public String getVarighedDagTimeSekundFormateret(long varighedMillisekunder) {
		int dage;
		int timer;
		int minutter;
		
		dage = (int) varighedMillisekunder/86400000;
		timer = (int) (varighedMillisekunder-(86400000*dage))/3600000;
		minutter = (int) (varighedMillisekunder-86400000*dage-3600000*timer)/60000;
		
		return dage + " d. " + timer + " t. " + minutter + " m.";
	}
}
