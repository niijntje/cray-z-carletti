/**
 * DRAGERING
 */
package model;

import java.util.GregorianCalendar;

import javax.persistence.Entity;

import service.Varighed;

/**
 * Denne klasse implementerer den abstrakte klasse Delbehandling og repr�senterer et trin i
 * en behandling/opskrift, hvor mellemvaren drageres.
 * 
 * @author Rita Holst Jacobsen
 * @author Rasmus Cederdorff: JPA
 * 
 */

@Entity
public class Dragering extends Delbehandling {
	private long varighed;

	public Dragering() {
		// Constructor - JPA
	}

	/**
	 * @param navn
	 * @param behandling
	 */
	public Dragering(String navn, Behandling behandling, long varighed) {
		super(navn, behandling, DelbehandlingsType.DRAGERING);
		this.varighed = varighed;
	}

	public long getVarighed() {
		return varighed;
	}

	public void setVarighed(long varighed) {
		this.varighed = varighed;
	}

	/* (non-Javadoc)
	 * @see model.Delbehandling#getResterendeTider(java.util.GregorianCalendar)
	 * Returnerer et long[] af l�ngden 1, svarende til varigheden af den dragering,
	 * der repr�senteres minus den tid der er g�et siden startTid.
	 */
	@Override
	public long[] getResterendeTider(GregorianCalendar startTid)
			throws RuntimeException {
		long tidSidenStart = System.currentTimeMillis()
				- startTid.getTimeInMillis();
		if (tidSidenStart < 0) {
			throw new RuntimeException(
					"startTid er ikke indtruffet endnu! Angiv en startTid f�r systemets nuv�rende tid.");
		}
		long[] tid = new long[1];
		if (tidSidenStart < this.getVarighed()) {
			tid[0] = this.getVarighed() - tidSidenStart;
		} else {
			tid[0] = 0;
		}
		return tid;
	}

	/* (non-Javadoc)
	 * @see model.Delbehandling#getResterendeTidTilNaeste(java.util.GregorianCalendar)
	 * Da der kun findes �n varighed/tidsfrist af en dragering, returneres altid et positivt
	 * antal millisekunder f�r varigheden er g�et og et negativt efter.
	 */
	@Override
	public long getResterendeTidTilNaeste(GregorianCalendar startTid)
			throws RuntimeException {
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
		return super.toString()+ "\t Varighed: "
				+ Varighed.getVarighedDagTimeSekundFormateret(varighed);
	}

	/* (non-Javadoc)
	 * @see model.Delbehandling#indenforTilladtBehandlingstid(java.util.GregorianCalendar)
	 * I Dragering-klassen tolkes tilladt behandlingstid som et vilk�rligt tidspunkt efter
	 * at drageringens varighed er g�et.
	 */
	@Override
	public boolean indenforTilladtBehandlingstid(GregorianCalendar startTid) {
		long tidGaaet = System.currentTimeMillis() - startTid.getTimeInMillis();
		return tidGaaet > this.varighed;
	}

}
