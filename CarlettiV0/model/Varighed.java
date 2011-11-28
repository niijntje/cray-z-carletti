/**
 * 
 */
package model;

/**
 * @author nijntje
 *
 */
public class Varighed {
	
	/**
	 * @param varighedMillisekunder
	 * @return
	 */
	public static String getVarighedDagTimeSekundFormateret(long varighedMillisekunder) {
		int dage;
		int timer;
		int minutter;

		dage = (int) varighedMillisekunder / 86400000;
		timer = (int) (varighedMillisekunder - (86400000 * dage)) / 3600000;
		minutter = (int) (varighedMillisekunder - 86400000 * dage - 3600000 * timer) / 60000;

		return dage + " d. " + timer + " t. " + minutter + " m.";
	}


}
