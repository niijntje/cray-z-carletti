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
public class Dragering extends Delbehandling {
	
	private long varighed;

	/**
	 * @param navn
	 * @param behandling
	 */
	public Dragering(String navn, Behandling behandling) {
		super(navn, behandling);
		// TODO Auto-generated constructor stub
	}

	public long getVarighed() {
		return varighed;
	}

	public void setVarighed(long varighed) {
		this.varighed = varighed;
	}

	@Override
	public long getResterendeTid(GregorianCalendar startTid) throws RuntimeException {

		return this.getVarighed();
	}

}
