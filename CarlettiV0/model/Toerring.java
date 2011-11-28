/**
 * 
 */
package model;

import java.util.GregorianCalendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * v.0.3
 * 
 * @author nijntje
 * 
 */
@Entity
public class Toerring extends Delbehandling {	
	private long minVarighed;
	private long idealVarighed;
	private long maxVarighed;

	public Toerring(String navn, Behandling behandling, long minVarighed,
			long idealVarighed, long maxVarighed) {
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
	public long[] getResterendeTider(GregorianCalendar startTid)
			throws RuntimeException {

		long tidSidenStart = System.currentTimeMillis()
				- startTid.getTimeInMillis();
		if (tidSidenStart < 0) {
			throw new RuntimeException(
					"startTid er ikke indtruffet endnu! Angiv en startTid f¿r systemets nuv¾rende tid.");
		}
		long[] tider = new long[3];

		if (tidSidenStart < this.getMinVarighed()) {
			tider[0] = this.getMinVarighed() - tidSidenStart;
		} else {
			tider[0] = 0;
		}
		if (tidSidenStart < this.getIdealVarighed()) {
			tider[1] = this.getIdealVarighed() - tidSidenStart;
		} else {
			tider[1] = 0;
		}
		if (tidSidenStart < this.getMaxVarighed()) {
			tider[2] = this.getMaxVarighed() - tidSidenStart;
		} else {
			tider[2] = 0;
		}
		return tider;
	}

	@Override
	public long getResterendeTidTilNaeste(GregorianCalendar startTid)
			throws RuntimeException {
		long[] tider = this.getResterendeTider(startTid);

		long tid = tider[0];
		if (tider[0] == 0) {
			tid = tider[1];
		}
		if (tider[1] == 0) {
			tid = tider[2];
		}
		if (tider[2] == 0) {
			long tidSidenStart = System.currentTimeMillis()
					- startTid.getTimeInMillis();
			tid = this.getMaxVarighed() - tidSidenStart;
		}
		return tid;
	}

	@Override
	public String toStringLong() {
		return super.toString() + "\t Varighed minimum "
				+ Varighed.getVarighedDagTimeSekundFormateret(minVarighed)
				+ "\t Ideel varighed: "
				+ Varighed.getVarighedDagTimeSekundFormateret(idealVarighed)
				+ "\t Varighed min. "
				+ Varighed.getVarighedDagTimeSekundFormateret(maxVarighed);
	}

}
