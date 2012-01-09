/**
 * VALIDERING
 */
package service;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Mads Dahl Jensen: millisekunderTildato(long tid)
 * @author Rita Holst Jacobsen: resten
 */

public class Validering {
	
	/**
	 * @author Mads Dahl Jensen
	 *
	 */
	public static String millisekunderTildato(long tid)
	{
		int dage;
		int timer;
		int minutter;
		
		dage = (int) tid/86400000;
		timer = (int) (tid-(86400000*dage))/3600000;
		minutter = (int) (tid-86400000*dage-3600000*timer)/60000;
		
		return dage + "d " + timer + "t " + minutter + "m";
	}

	
	/**
	 * String-repr¾sentation af varighed, der resulterer i korrekt sortering
	 * 
	 * @param tid Millisekunder
	 * 
	 * @return String-repr¾sentation af varighed formateret som "DDd HHt MMm"
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public static String millisekunderTilVarighedString(long tid) {
		int dage;
		int timer;
		int minutter;

		dage = (int) tid / 86400000;
		String dageString = "";
		if (dage < 10) {
			dageString += "0";
		}
		dageString += dage;

		timer = (int) (tid - (86400000 * dage)) / 3600000;
		String timerString = "";
		if (timer < 10) {
			timerString += "0";
		}
		timerString += timer;

		minutter = (int) (tid - 86400000 * dage - 3600000 * timer) / 60000;
		String minutterString = "";
		if (minutter < 10) {
			minutterString += "0";
		}
		minutterString += minutter;

		return dageString + "d " + timerString + "t " + minutterString + "m";
	}

	/** 
	 * Benyttes af ObjectCreater og SubFrameTilfoejDelbehandling.
	 * 
	 * @param varighed i formatet DD:HH:MM
	 * @return varighed i millisekunder
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public static long varighedStringTilMillisekunder(String varighed) {
		long varighedMillisekunder = 0;
		varighedMillisekunder += Long.parseLong(varighed.substring(0, 2))* 1000 * 60 * 60 * 24; // dage
		varighedMillisekunder += Long.parseLong(varighed.substring(3, 5)) * 1000 * 60 * 60; // timer
		varighedMillisekunder += Long.parseLong(varighed.substring(6, 8)) * 1000 * 60; // minutter
		return varighedMillisekunder;
	}

	public static long varighedStringDDTTMMSSTilMillisekunder(String varighed) {
		long varighedMillisekunder = 0;
		varighedMillisekunder += Long.parseLong(varighed.substring(0, 2))
				* 1000 * 60 * 60 * 24; // dage
		varighedMillisekunder += Long.parseLong(varighed.substring(3, 5)) * 1000 * 60 * 60; // timer
		varighedMillisekunder += Long.parseLong(varighed.substring(6, 8)) * 1000 * 60; // minutter
		varighedMillisekunder += Long.parseLong(varighed.substring(9))*1000;
		return varighedMillisekunder;
	}

	/**
	 * Benyttes af Service.getMellemvareInfo(), samt af Validering.millisekunderTilCalendarString()
	 * 
	 * @param gCal Calendar-objekt
	 * @return calendarString i formatet YYYY-MM-DD-HH:MM
	 * 
	 * @author Rita Holst Jacobsen
	 */
	public static String calendarTilCalendarString(GregorianCalendar gCal) {
		String month = "" + (1 + gCal.get(Calendar.MONTH));
		if (month.length() < 2) {
			month = "0" + month;
		}
		String day = "" + gCal.get(Calendar.DAY_OF_MONTH);
		if (day.length() < 2) {
			day = "0" + day;
		}
		String hour = "" + gCal.get(Calendar.HOUR_OF_DAY);
		if (hour.length() < 2) {
			hour = "0" + hour;
		}
		String minute = "" + gCal.get(Calendar.MINUTE);
		if (minute.length() < 2) {
			minute = "0" + minute;
		}
		return gCal.get(Calendar.YEAR) + "-" + month + "-" + day + "\t" + hour
				+ ":" + minute;
	}	


}