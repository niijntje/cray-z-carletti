/**
 * 
 */
package model;

/**
 * @author nijntje
 *
 */
public abstract class Placering {
	
	private PlaceringsFunktion placeringsmaade;
	
	public Placering(PlaceringsFunktion placeringsmaade){
		setPlaceringsmaade(placeringsmaade);
	}
	
	public void setPlaceringsmaade(PlaceringsFunktion placeringsmaade){
		this.placeringsmaade = placeringsmaade;
	}
	public PlaceringsFunktion getPlaceringsmaade(){
		return this.placeringsmaade;
	}

}
