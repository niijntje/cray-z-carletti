package service;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Validering {
	/**
	 * @author Mads Dahl Jensen
	 * 
	 */
	public static String millisekunderTildato(long tid) {
		int dage;
		int timer;
		int minutter;

		dage = (int) tid / 86400000;
		timer = (int) (tid - (86400000 * dage)) / 3600000;
		minutter = (int) (tid - 86400000 * dage - 3600000 * timer) / 60000;

		return dage + "d " + timer + "t " + minutter + "m";
	}

	/**
	 * @param varighed
	 *            i formatet DD:HH:MM
	 * @return	varighed i millisekunder
	 */
	public static long varighedStringTilLong(String varighed) {
		long varighedMillisekunder = 0;
		varighedMillisekunder += Long.parseLong(varighed.substring(0, 2))
				* 1000 * 60 * 60 * 24; // dage
		varighedMillisekunder += Long.parseLong(varighed.substring(3, 5)) * 1000 * 60 * 60; // timer
		varighedMillisekunder += Long.parseLong(varighed.substring(6, 8)) * 1000 * 60; // minutter
		return varighedMillisekunder;
	}

	/**
	 * @param Calendar
	 *            -objekt
	 * @return calendarString i formatet YYYY-MM-DD-HH:MM
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

	/**
	 * @param millisekunder
	 * @return calendarString i formatet YYYY-MM-DD-HH:MM
	 * @author Rita Holst Jacobsen
	 */
	public static String millisekunderTilCalendarString(long millisekunder) {
		GregorianCalendar gCal = new GregorianCalendar();
		gCal.setTimeInMillis(millisekunder);
		return calendarTilCalendarString(gCal);
	}

	/**
	 * @param calendarString
	 *            i formatet YYYY-MM-DD-HH:MM
	 * @return
	 * @author Rita Holst Jacobsen
	 */
	public static GregorianCalendar stringTilCalendar(String cS) {
		GregorianCalendar gCal = new GregorianCalendar();
		gCal.set(Integer.parseInt(cS.substring(0, 3)),
				Integer.parseInt(cS.substring(5, 7)) - 1,
				Integer.parseInt(cS.substring(8, 10)),
				Integer.parseInt(cS.substring(11, 13)),
				Integer.parseInt(cS.substring(14, 16)));
		return gCal;
	}

}