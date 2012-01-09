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
		Service.getInstance().setTestMode(true);		//<--G¿r at det er tilladt at sende mellemvarer videre til
																	// delbehandling selvom tidsfristen ikke er overholdt.

		ObjectCreater.getInstance().createSomeObjects();	//<--Genererer data, man kan 'lege' med ;-)
		
		MainFrame.getInstance().update();
		MainFrame.getInstance().setVisible(true);

	}

}
