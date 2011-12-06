package gui;

import service.Service;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		ObjectCreater.getInstance().createSomeObjects();
		MainFrame.getInstance().update();
		MainFrame.getInstance().setVisible(true);
		Service.getInstance().setTestMode(true);
	}

}
