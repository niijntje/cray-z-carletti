/**
 * VARIGHED
 */
package service;

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
	
	/**
	 * @param varighedMillisekunder
	 * @return
	 */
	public String getVarighedDDTTMM() {
		return dage + " d. " + timer + " t. " + minutter + " m.";
	}
	
	public static String getVarighedDagTimeSekundFormateret(long millisekunder){
		Varighed v = new Varighed (millisekunder);
		return v.getVarighedDDTTMM();
	}

	public int getDage() {
	   return dage;
   }

	public void setDage(int dage) {
	   this.dage = dage;
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
   }

	@Override
   public int compareTo(Varighed v) {
	   return this.getVarighedMillisekunderLong().compareTo(v.getVarighedMillisekunderLong());
   }
	
	@Override
	public String toString(){
		return this.getVarighedDDTTMM();
	}
}
