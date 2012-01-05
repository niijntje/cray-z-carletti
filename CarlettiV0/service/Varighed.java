/**
 * VARIGHED
 */
package service;

import java.util.Comparator;

/**
 * PŒbegyndt og delvist taget i brug, da der var planer om at implementere vores egen klasse til hŒndtering 
 * af varighed til afl¿sning for millisekunder (long) og med mulighed for korrekt sortering samt visning 
 * af varighed efter eget valg i gui.
 * 
 * @author Rita Holst Jacobsen
 *
 */
public class Varighed implements Comparable<Varighed>{

	private int dage;
	private int timer;
	private int minutter;

	private long varighedMillisekunder;

	public Varighed(long millisekunder){
		this.setVarighedMillisekunder(millisekunder);
		this.setDage((int) getVarighedMillisekunder() / 86400000);
		this.setTimer((int) (getVarighedMillisekunder() - (86400000 * dage)) / 3600000);
		this.setMinutter((int) (getVarighedMillisekunder() - 86400000 * dage - 3600000 * timer) / 60000);
	}

	public Varighed(int dage, int timer, int minutter){
		this.setDage(dage);
		this.setTimer(timer);
		this.setMinutter(minutter);
		this.setVarighedMillisekunder(dage*24*60*60*1000+timer*60*60*1000+minutter*60*1000);
	}

	/**
	 * @param ddttmm Varighed i formatet dd-tt:mm
	 */
	public Varighed(String ddttmm){
		this(Validering.varighedStringTilMillisekunder(ddttmm));
	}

	/**
	 * @param varighedMillisekunder
	 * @return
	 */
	public String getVarighedDDTTMM() {
		String varighed = "";
		if (dage > 0){
			varighed += dage+ " d. ";
		}
		if (timer > 0 || dage > 0){
			varighed += timer + " t. ";
		}
		varighed += minutter + " m.";
		return varighed ;
	}

	public static String getVarighedDagTimeSekundFormateret(long millisekunder){
		Varighed v = new Varighed (millisekunder);
		return v.getVarighedDDTTMM();
	}

	public int getDage() {
		return dage;
	}

	public void setDage(int dage) {
		int diff = dage - this.dage;
		this.dage = dage;
		this.varighedMillisekunder += diff*24*60*60*1000;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public int getMinutter() {
		return minutter;
	}

	public void setMinutter(int minutter) {
		this.minutter = minutter;
	}

	public long getVarighedMillisekunder() {
		return varighedMillisekunder;
	}

	public Long getVarighedMillisekunderLong() {
		return (Long) getVarighedMillisekunder();
	}

	public void setVarighedMillisekunder(long varighedMillisekunder) {
		this.varighedMillisekunder = varighedMillisekunder;
		this.setDage((int) getVarighedMillisekunder() / 86400000);
		this.setTimer((int) (getVarighedMillisekunder() - (86400000 * dage)) / 3600000);
		this.setMinutter((int) (getVarighedMillisekunder() - 86400000 * dage - 3600000 * timer) / 60000);

	}

	@Override
	public int compareTo(Varighed v) {
		if (v.equals(null)){
			return 1;
		}
		else{
			return v.getVarighedMillisekunderLong().compareTo(this.getVarighedMillisekunderLong());
		}
	}

	@Override
	public String toString(){
		return this.getVarighedDDTTMM();
	}


}
