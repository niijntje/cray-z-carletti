package gui;

import model.Drageringshal;
import model.MellemlagerPlads;
import model.Palle;
import service.ObjectCreater;
import service.Service;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ObjectCreater.getInstance().createSomeObjects();
		MainFrame.getInstance().update();
		MainFrame.getInstance().setVisible(true);


//		SubFrameAdminPalle sub1 = new SubFrameAdminPalle();
//		sub1.setVisible(true);
//		SubFrameAdminProdukttype sub2 = new SubFrameAdminProdukttype();
//		sub2.setVisible(true);
//		SubFrameAdminMellemlagerPlads sub3	= new SubFrameAdminMellemlagerPlads();
//		sub3.setVisible(true);


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

		//		

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
