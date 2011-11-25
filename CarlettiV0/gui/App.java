package gui;

import java.util.Arrays;

import dao.ListDao;

import service.Service;
import model.Behandling;
import model.Delbehandling;
import model.MellemlagerPlads;
import model.Mellemvare;
import model.Palle;
import model.Produkttype;
import model.Toerring;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
//		mainFrame.setVisible(true);
//		SubFrameTilfoejMellemvarer subframeMellemvarer = new SubFrameTilfoejMellemvarer(mainFrame);
//		subframeMellemvarer.setVisible(true);
//		SubFramePlacerPalle subframePlacerPalle = new SubFramePlacerPalle(mainFrame);
//		subframePlacerPalle.setVisible(true);
		
		Service.getInstance().createSomeObjects();
		SubFramePalleOversigt subFramePalleOversigt = new SubFramePalleOversigt(mainFrame, Service.getInstance().findPalle("20000001"));
		subFramePalleOversigt.setVisible(true);		
	
//		Service.getInstance().generateViewDataProduktDelbehandlingAntal(pa1);

		
//		String[][] testArray = {
//				{"a", "b", "c", "d"},
//				{"e", "f", "g", "h"},
//				{"i", "j", "k", "l"},
//				{"m", "n", "o", "p"}				
//		};
//		
//		System.out.println(testArray[0][3]);
//		System.out.println(testArray[3][0]);
//		System.out.println(Arrays.toString(testArray[2]));
		
	}

}
