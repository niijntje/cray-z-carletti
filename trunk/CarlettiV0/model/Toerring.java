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
public class Toerring extends Delbehandling {
	
	private long minVarighed;
	private long idealVarighed;
	private long maxVarighed;



	public Toerring(String navn, Behandling behandling, long minVarighed, long idealVarighed, long maxVarighed) {
		super(navn, behandling);
		this.setMinVarighed(minVarighed);
		this.setIdealVarighed(idealVarighed);
		this.setMaxVarighed(maxVarighed);
	}



	public long getMinVarighed() {
		return minVarighed;
	}

	public void setMinVarighed(long minVarighed) {
		this.minVarighed = minVarighed;
	}

	public long getIdealVarighed() {
		return idealVarighed;
	}

	public void setIdealVarighed(long idealVarighed) {
		this.idealVarighed = idealVarighed;
	}

	public long getMaxVarighed() {
		return maxVarighed;
	}

	public void setMaxVarighed(long maxVarighed) {
		this.maxVarighed = maxVarighed;
	}

	@Override
	public long getResterendeTid(GregorianCalendar startTid) {
		long tidSidenStart = startTid.getTimeInMillis()-System.currentTimeMillis();
		if (tidSidenStart < 0){
			throw new RuntimeException("startTid er ikke indtruffet endnu! Angiv en startTid f¿r systemets nuv¾rende tid.");
		}
		long resterendeTid = 0;

		if (tidSidenStart < this.getMinVarighed()){
			resterendeTid = this.getMinVarighed()-tidSidenStart;
		}
		else if (tidSidenStart < this.getIdealVarighed()){
			resterendeTid = this.getIdealVarighed()-tidSidenStart;
		}
		else if (tidSidenStart < this.getMaxVarighed()){
			resterendeTid = this.getMaxVarighed()-tidSidenStart;
		}
		else resterendeTid = this.getMaxVarighed()-tidSidenStart; //Jeg ved godt at de sidste to else'er er ens, men sidste returnerer en negativ v¾rdi
		return resterendeTid;
	}



}
