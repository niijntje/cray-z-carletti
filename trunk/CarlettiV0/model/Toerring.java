/**
 * TOERRING
 */
package model;

import java.util.GregorianCalendar;

import javax.persistence.Entity;

import service.Varighed;

/**
 * Denne klasse implementerer den abstrakte klasse Delbehandling og repræsenterer et trin i
 * en behandling/opskrift, hvor mellemvaren tørres på mellemvarelageret.
 * 
 * @author Rita Holst Jacobsen
 * @author Rasmus Cederdorff: JPA
 * 
 */

@Entity
public class Toerring extends Delbehandling {
	private long minVarighed;
	private long idealVarighed;
	private long maxVarighed;
	
	private Varighed vMinVarighed;
	private Varighed vIdealVarighed;
	private Varighed vMaxVarighed;

	public Toerring() {
		// Constructor - JPA
	}

	/**
	 * @param navn
	 * @param behandling
	 * @param minVarighed
	 * @param idealVarighed
	 * @param maxVarighed
	 */
	public Toerring(String navn, Behandling behandling, long minVarighed,
			long idealVarighed, long maxVarighed) {
		super(navn, behandling, DelbehandlingsType.TOERRING);
		this.setMinVarighed(minVarighed);
		this.setIdealVarighed(idealVarighed);
		this.setMaxVarighed(maxVarighed);
		this.vMinVarighed = new Varighed(minVarighed);
		this.vIdealVarighed = new Varighed(idealVarighed);
		this.vMaxVarighed = new Varighed(maxVarighed);
	}
	
	public Toerring(String navn, Behandling behandling, Varighed minVarighed,
			Varighed idealVarighed, Varighed maxVarighed) {
		super(navn, behandling, DelbehandlingsType.TOERRING);
		this.vMinVarighed = minVarighed;
		this.vIdealVarighed = idealVarighed;
		this.vMaxVarighed = maxVarighed;
		this.setMinVarighed(minVarighed.getVarighedMillisekunder());
		this.setIdealVarighed(idealVarighed.getVarighedMillisekunder());
		this.setMaxVarighed(maxVarighed.getVarighedMillisekunder());
	}
	
	public Toerring(String navn, Behandling behandling, String minVarighed,
			String idealVarighed, String maxVarighed) {
		super(navn, behandling, DelbehandlingsType.TOERRING);
		this.vMinVarighed = new Varighed(minVarighed);
		this.vIdealVarighed = new Varighed(idealVarighed);
		this.vMaxVarighed = new Varighed(maxVarighed);
		this.setMinVarighed(vMinVarighed.getVarighedMillisekunder());
		this.setIdealVarighed(vIdealVarighed.getVarighedMillisekunder());
		this.setMaxVarighed(vMaxVarighed.getVarighedMillisekunder());
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

	/* (non-Javadoc)
	 * @see model.Delbehandling#getResterendeTider(java.util.GregorianCalendar)
	 * Returnerer et long[] af længden 3, svarende til den resterende tid til tidsfristerne
	 * min-tid, ideal-tid og max-tid for den igangværende tørring.
	 */
	@Override
	public long[] getResterendeTider(GregorianCalendar startTid)
			throws RuntimeException {

		long tidSidenStart = System.currentTimeMillis()
				- startTid.getTimeInMillis();
		if (tidSidenStart < 0) {
			throw new RuntimeException(
					"startTid er ikke indtruffet endnu! Angiv en startTid før systemets nuværende tid.");
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

	/* (non-Javadoc)
	 * @see model.Delbehandling#getResterendeTidTilNaeste(java.util.GregorianCalendar)
	 * Returnerer den resterende tid i millisekunder for hhv. minTid, idealTid og maxTid.
	 * Er tidsfristen overskredet, returneres 0 - dog ikke hvis max-tid er overskredet - da
	 * returneres et negativt antal millisekunder svarende til den tid der er gået siden
	 * tidsfristen blev overskredet.
	 */
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

	/* (non-Javadoc)
	 * @see model.Delbehandling#indenforTilladtBehandlingstid(java.util.GregorianCalendar)
	 * For en tørring er den tilladte behandlingstid defineret som tidsrummet efter min-tid
	 * er gået, men før max-tid er gået.
	 */
	@Override
	public boolean indenforTilladtBehandlingstid(GregorianCalendar startTid) {
		long[] restTider = this.getResterendeTider(startTid);
		return (restTider[0] == 0 && restTider[2] != 0);
	}

}
