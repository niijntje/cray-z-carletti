/**
 * 
 */
package model;

import java.util.ArrayList;

/**
 * v.0.3
 * 
 * @author nijntje
 * 
 */
public class Behandling {

	private String navn;
	private ArrayList<Delbehandling> delbehandlinger;

	public Behandling(String navn) {
		this.navn = navn;
		this.delbehandlinger = new ArrayList<Delbehandling>();
	}

	/**
	 * 
	 * @param navn
	 * @param index
	 *            - hvis index == -1, skal delbehandlingen blot tilf�jes sidst i
	 *            listen.
	 * @return
	 */
	public Delbehandling addDelbehandling(Delbehandling nyDelbehandling,
			int index) throws IndexOutOfBoundsException {

		// Husk at f� generaliseret denne!

		// nyDelbehandling = new Toerring("Somethingsomething", this, 10000000,
		// 15000000, 20000000);

		if (index == -1) {
			if (delbehandlinger.size() > 0) {
				delbehandlinger.get(delbehandlinger.size() - 1)
						.setNextDelbehandling(nyDelbehandling);
			}
			delbehandlinger.add(nyDelbehandling);
		} else if (index <= delbehandlinger.size()) {
			if (index > 0) {
				delbehandlinger.get(index - 1).setNextDelbehandling(
						nyDelbehandling);
			}
			if (index < delbehandlinger.size()) {
				nyDelbehandling
						.setNextDelbehandling(delbehandlinger.get(index));
			}
			delbehandlinger.add(index, nyDelbehandling);
		}
		return nyDelbehandling;
	}

	/**
	 * @param delbehandling
	 */
	public void removeDelbehandling(Delbehandling delbehandling) {
		int indexOf = delbehandlinger.indexOf(delbehandling);
		if (indexOf > 0) {
			if (indexOf < delbehandlinger.size() - 1) {
				delbehandlinger.get(indexOf - 1).setNextDelbehandling(
						delbehandlinger.get(indexOf + 1));
			} else {
				delbehandlinger.get(indexOf - 1).setNextDelbehandling(null);
			}
		}
		delbehandling.setNextDelbehandling(null); // Er det n�dvendigt at fjerne
													// denne reference, hvis jeg
													// ikke kommer til at bruge
													// delbehandlingen igen?
		delbehandling.setBehandling(null); // Og hvad med denne?
		delbehandlinger.remove(delbehandling);

	}

	/**
	 * @param index
	 *            Krav: index >= 0 og index < delbehandlinger.size()
	 * @return
	 */
	public Delbehandling getDelbehandling(int index) {
		return this.delbehandlinger.get(index);
	}

	public ArrayList<Delbehandling> getDelbehandlinger() {
		return delbehandlinger;

	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

}
