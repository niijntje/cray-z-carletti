/**
 * 
 */
package model;

/**
 * v.0.3
 * @author nijntje
 *
 */
public class Produkttype {
	
	private String navn;
	private String beskrivelse;
	private Behandling behandling;
	
	
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
	public String toString(){
		return this.getNavn();
	}
	
	public String toStringLong(){
		return this.toString()+". "+this.getBeskrivelse();
	}
}
