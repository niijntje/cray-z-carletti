/**
 * PRODUKTTYPE
 */
package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Klassen repræsenterer den produkttype, en mellemvare er af, og udgør således bindeleddet mellem mellemvare
 * og behandling/delbehandlinger. Til en produkttype er knyttet én behandling, der til gengæld godt kan være 
 * tilknyttet flere produkttyper.
 * 
 * @author Rasmus Cederdorff
 */

@Entity
public class Produkttype {
	@Id
	private String navn;
	private String beskrivelse;
	@ManyToOne
	private Behandling behandling;

	public Produkttype() {

	}

	public Produkttype(String navn, String beskrivelse, Behandling behandling) {
		this.navn = navn;
		this.beskrivelse = beskrivelse;
		this.behandling = behandling;
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public String getBeskrivelse() {
		return beskrivelse;
	}

	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse = beskrivelse;
	}

	public Behandling getBehandling() {
		return behandling;
	}

	public void setBehandling(Behandling behandling) {
		this.behandling = behandling;
	}

	@Override
	public String toString() {
		return this.getNavn();
	}

	public String toStringLong() {
		return this.toString() + ". " + this.getBeskrivelse();
	}
}
