/**
 * 
 */
package model;

import java.util.ArrayList;

/**
 * @author nijntje
 *
 */
public class PlacerEn implements PlaceringsFunktion {

	private Placerbar placeretHer;
	private ArrayList<Class<Placerbar>> tilladtePlacerbare;
	/**
	 * 
	 */
	public PlacerEn() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see model.PlacerNogetHer#placer(model.Placerbar)
	 */
	@Override
	public void placer(Placerbar noget) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see model.PlacerNogetHer#fjern(model.Placerbar)
	 */
	@Override
	public void fjern(Placerbar noget) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see model.PlacerNogetHer#erOptaget()
	 */
	@Override
	public boolean erOptaget() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
   public void setTilladte(ArrayList<Class<Placerbar>> tilladtePlacerbarTyper) {
	   // TODO Auto-generated method stub
	   
   }

	@Override
   public ArrayList<Class<Placerbar>> getTilladte() {
	   // TODO Auto-generated method stub
	   return null;
   }

}
