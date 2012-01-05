/**
 * APP
 */
package gui;

import model.Palle;
import service.ObjectCreater;
import service.Service;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ObjectCreater.getInstance().createSomeObjects();	//<--Genererer data, man kan 'lege' med ;-)
		MainFrame.getInstance().update();
		MainFrame.getInstance().setVisible(true);
//		Service.getInstance().setTestMode(true);		//<--G¿r at det er tilladt at sende mellemvarer videre til
																	// delbehandling selvom tidsfristen ikke er overholdt.
	
		
//		Palle palle = Service.getInstance().soegPalle("00001");
//		FarveTestOversigt farveTestFrame = new FarveTestOversigt(palle);
//		farveTestFrame.setVisible(true);
	}

}
