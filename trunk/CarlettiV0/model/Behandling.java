/**
 * BEHANDLING
 */
package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 * Denne klasse ses som mellemvarens opskrift og indeholder beskrivelser 
 * af mellemvaren samt hvilke delbehandlinger mellemvaren skal igennem. 
 * 
 * @author Rita Holst Jacobsen
 * @author Rasmus Cederdorff: JPA
 * 
 */
@Entity
public class Behandling {
	@Id
	private String navn;
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	private List<Delbehandling> delbehandlinger;

	public Behandling() {

	}

	/**
	 * @param navn
	 */
	public Behandling(String navn) {
		this.navn = navn;
		this.delbehandlinger = new ArrayList<Delbehandling>();
	}

	/** Tilf¿jer nyDelbehandling til listen og opdaterer linket nextDelbehandling fra
	 * den foregŒende delbehandling, sŒ det i stedet peger pŒ nyDelbehandling
	 * @param nyDelbehandling
	 * @param index Hvis index == -1, skal delbehandlingen blot tilf¿jes sidst i listen.
	 * @return
	 */
	public Delbehandling addDelbehandling(Delbehandling nyDelbehandling,int index) throws IndexOutOfBoundsException {

		if (index == -1) {
			if (delbehandlinger.size() > 0) {
				delbehandlinger.get(delbehandlinger.size() - 1).setNextDelbehandling(nyDelbehandling);
			}
			delbehandlinger.add(nyDelbehandling);
		}
		else if (index <= delbehandlinger.size()) {
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

	/**Fjerner delbehandling fra listen og opdaterer linket nextDelbehandling 
	 * fra den foregŒende delbehandling, sŒ det i stedet peger pŒ den efterf¿lgende
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
		delbehandling.setNextDelbehandling(null); 
		delbehandling.setBehandling(null);
		delbehandlinger.remove(delbehandling);
	}

	/**
	 * @param index 
	 * Krav: index >= 0 og index < delbehandlinger.size()
	 * @return
	 */
	public Delbehandling getDelbehandling(int index) {
		return this.delbehandlinger.get(index);
	}

	public List<Delbehandling> getDelbehandlinger() {
		return delbehandlinger;

	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	@Override
	public String toString() {
		return this.navn;
	}

}
