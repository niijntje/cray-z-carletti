package Gui;

public class Validering
{
	
	
	public String millisekunderTildato(long tid)
	{
		int dage;
		int timer;
		int minutter;
		
		dage = (int) tid/86400000;
		timer = (int) (tid-(86400000*dage))/3600000;
		minutter = (int) (tid-86400000*dage-3600000*timer)/60000;
		
		return dage + " dage, " + timer + " timer, " + "og " + minutter + " minutter";
	}
	
}