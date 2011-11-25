package gui;

import java.util.Arrays;

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
		
		MellemlagerPlads pl1 = new MellemlagerPlads("100100101");
		MellemlagerPlads pl2 = new MellemlagerPlads("100100102");
		
		Palle pa1 = new Palle("20000001");
		Palle pa2 = new Palle("20000002");
		
		Behandling b3 = new Behandling("B3 - MŒnegrus og lakridspinde. Kernest¿rrelse 2.5-3.5");
		
		Delbehandling d1 = new Toerring("T¿rring 1a", b3,100000000, 150000000, 200000000);
		b3.addDelbehandling(d1, -1);
		
		Produkttype pt1 = new Produkttype("Lakridspinde 1", "Gr¿nne lakridspinde med kernest¿rrelse 3.\nOpskrift: B3", b3);
		Produkttype pt2 = new Produkttype("MŒnegrus 2B", "Rester fra lakridspinde med varierende kernest¿rrelse.\nOpskrift: B3", b3);
		
		
		Mellemvare m1 = new Mellemvare("300000001", pt1, pa1);
		Mellemvare m2 = new Mellemvare("300000002", pt1, pa1);
		Mellemvare m3 = new Mellemvare("300000003", pt2, pa1);
		Mellemvare m4 = new Mellemvare("300000004", pt1, pa1);
		Mellemvare m5 = new Mellemvare("300000005", pt1, pa1);
		Mellemvare m6 = new Mellemvare("300000006", pt2, pa1);
		Mellemvare m7 = new Mellemvare("300000007", pt1, pa1);
		pa1.addMellemvare(m1);
		pa1.addMellemvare(m2);
		pa1.addMellemvare(m3);
		pa1.addMellemvare(m4);
		pa1.addMellemvare(m5);
		pa1.addMellemvare(m6);
		pa1.addMellemvare(m7);
		
		pa1.placerPalle(pl2);
	
		Service.getInstance().generateInfoProduktDelbehandlingAntal(pa1);
//		SubFramePalleOversigt subFramePalleOversigt = new SubFramePalleOversigt(mainFrame, pa1);
//		subFramePalleOversigt.setVisible(true);
		
		String[][] testArray = {
				{"a", "b", "c", "d"},
				{"e", "f", "g", "h"},
				{"i", "j", "k", "l"},
				{"m", "n", "o", "p"}				
		};
		
		System.out.println(testArray[0][3]);
		System.out.println(testArray[3][0]);
		System.out.println(Arrays.toString(testArray[2]));
		
	}

}
