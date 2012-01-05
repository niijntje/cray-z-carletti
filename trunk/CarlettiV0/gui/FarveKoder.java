/**
 * 
 */
package gui;

import java.awt.Color;

import model.Dragering;
import model.Mellemvare;
import model.Toerring;

/**
 * @author nijntje
 *
 */
public class FarveKoder {
	
	public static Color getFarve(Mellemvare mellemvare){
		Color color = Color.white;
		if (mellemvare.getIgangvaerendeDelbehandling() instanceof Toerring){
			long[] resterendeTider = mellemvare.getResterendeTider();
			if (resterendeTider[0] > 0){

			}
			else if (resterendeTider[1] > 0) {
				color = Color.yellow;
			}
			else if (resterendeTider[2] > 0){
				color = Color.green;
			}
			else {
				color = Color.red;
			}
		}
		else if (mellemvare.getIgangvaerendeDelbehandling() instanceof Dragering){
			if (mellemvare.getResterendeTidTilNaeste() <=0){
				color = Color.green;
			}
		}
		return color;
	}
	
	

}
