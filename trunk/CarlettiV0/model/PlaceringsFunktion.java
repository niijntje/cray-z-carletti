/**
 * 
 */
package model;

import java.util.ArrayList;

/**
 * @author nijntje
 *
 */
public interface PlaceringsFunktion {
	
	public void placer(Placerbar noget);
	public void fjern(Placerbar noget);
	public boolean erOptaget();
	
	public void setTilladte(ArrayList<Class<Placerbar>> tilladtePlacerbarTyper);
	public ArrayList<Class<Placerbar>> getTilladte();
}
