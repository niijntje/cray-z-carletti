/**
 * 
 */
package model;

import java.util.GregorianCalendar;

import javax.persistence.Entity;

/**
 * v.0.3
 * 
 * @author nijntje
 * 
 */
@Entity
public class Dragering extends Delbehandling {
	private long varighed;

	public Dragering(){
		super(DelbehandlingsType.DRAGERING);
	}
	/**
	 * @param navn
	 * @param behandling
	 */
	public Dragering(String navn, Behandling behandling, long varighed) {
		super(navn, behandling);
		this.varighed = varighed;
	}

	public long getVarighed() {
		return varighed;
	}

	public void setVarighed(long varighed) {
		this.varighed = varighed;
	}

	@Override
	public long[] getResterendeTider(GregorianCalendar startTid) throws RuntimeException {
		long tidSidenStart = System.currentTimeMillis()
				- startTid.getTimeInMillis();
		if (tidSidenStart < 0) {
			throw new RuntimeException(
					"startTid er ikke indtruffet endnu! Angiv en startTid f¿r systemets nuv¾rende tid.");
		}
		long[] tid = new long[1];
		if (tidSidenStart < this.getVarighed()){
			tid[0] = this.getVarighed()-tidSidenStart;
		}
		else {
			tid[0] = 0;
		}
		return tid;
	}

	@Override
	public long getResterendeTidTilNaeste(GregorianCalendar startTid) throws RuntimeException {
		long tid = getResterendeTider(startTid)[0];
		if (tid == 0) {
			long tidSidenStart = System.currentTimeMillis()
					- startTid.getTimeInMillis();
			tid = this.getVarighed() - tidSidenStart;
		}
		return tid;
	}

	@Override
	public String toStringLong() {
		return super.toString() + "\t Varighed: "
				+ Varighed.getVarighedDagTimeSekundFormateret(varighed);
	}
	@Override
	public boolean indenforTilladtBehandlingstid(GregorianCalendar startTid) {
		long tidGaaet = System.currentTimeMillis()-startTid.getTimeInMillis();
		return tidGaaet>this.varighed;
	}

}
