package gui;

import model.Behandling;
import service.ObjectCreater;
import service.Service;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		SubFrameAdminPalle sub1 = new SubFrameAdminPalle();
//		sub1.setVisible(true);
//		SubFrameAdminProdukttype sub2 = new SubFrameAdminProdukttype();
//		sub2.setVisible(true);
//		SubFrameAdminMellemlagerPlads sub3	= new SubFrameAdminMellemlagerPlads();
//		sub3.setVisible(true);
		ObjectCreater.getInstance().createSomeObjects();
//		MainFrameTabelVersion mainframe= new MainFrameTabelVersion();
//		mainframe.setVisible(true);
		SubFrameAdminBehandling subbe = new SubFrameAdminBehandling();
		subbe.setVisible(true);
		Behandling behandling = Service.getInstance().opretBehandling("BehandlingRasmus");

		
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
//		// SubFrameTilfoejMellemvarer subframeMellemvarer = new
		// SubFrameTilfoejMellemvarer(mainFrame);
		// subframeMellemvarer.setVisible(true);
		// SubFramePlacerPalle subframePlacerPalle = new
		// SubFramePlacerPalle(mainFrame);
		// subframePlacerPalle.setVisible(true);

		//		Service.getInstance().opretPalle("0000011");
		//		MainFrame mainFrame = new MainFrame();
		////		mainFrame.setVisible(true);
		//		// mainFrame.setVisible(true);
		//		// SubFrameTilfoejMellemvarer subframeMellemvarer = new
		//		// SubFrameTilfoejMellemvarer(mainFrame);
		//		// subframeMellemvarer.setVisible(true);
		//		// SubFramePlacerPalle subframePlacerPalle = new
		//		// SubFramePlacerPalle(mainFrame);
		//		// subframePlacerPalle.setVisible(true);
		//

		//
//				SubFramePalleOversigt subFramePalleOversigt = new SubFramePalleOversigt(
//						mainFrame, Service.getInstance().soegPalle("20000001"));
//				subFramePalleOversigt.setVisible(true);

		//		ObjectCreater.getInstance().createSomeObjects();

		//		SubFramePalleOversigt subFramePalleOversigt = new SubFramePalleOversigt(
		//				mainFrame, Service.getInstance().soegPalle("20000001"));
		//		subFramePalleOversigt.setVisible(true);

		// Service.getInstance().generateViewDataProduktDelbehandlingAntal(pa1);

		// String[][] testArray = {
		// {"a", "b", "c", "d"},
		// {"e", "f", "g", "h"},
		// {"i", "j", "k", "l"},
		// {"m", "n", "o", "p"}
		// };
		//
		// System.out.println(testArray[0][3]);
		// System.out.println(testArray[3][0]);
		// System.out.println(Arrays.toString(testArray[2]));

	}

}
