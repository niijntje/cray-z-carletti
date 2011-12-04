package gui;

import model.Delbehandling;
import model.Delbehandling.DelbehandlingsType;
import model.Mellemvare;
import model.Palle;
import service.ObjectCreater;
import dao.ListDao;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ObjectCreater.getInstance().createSomeObjects();
		MainFrame.getInstance().update();
		MainFrame.getInstance().setVisible(true);
		
		
//		System.out.println("MELLEMVARER:");
//		for (Mellemvare m : ListDao.getListDao().mellemvarer()){
//			System.out.println(m);
//			Delbehandling d = m.getIgangvaerendeDelbehandling();
//			System.out.println("Dragering: " +m.naesteDelbehandlingGyldig(DelbehandlingsType.DRAGERING)+" "+d+" "+d.getDelbehandlingstype()+" "+d.getNextDelbehandling());
//			System.out.println("T�rring: " +m.naesteDelbehandlingGyldig(DelbehandlingsType.TOERRING));
//			System.out.println("F�rdigvarelager: "+m.naesteDelbehandlingGyldig(null));
//			System.out.println();
//		}
//		System.out.println();
//		System.out.println("PALLER:");
//		for (Palle p : ListDao.getListDao().paller()){
//			System.out.println("Palle: "+p+" "+p.getMellemvarer().size()+" stk.");
//			System.out.println("Dragering: " +p.naesteDelbehandlingGyldig(null, null ,DelbehandlingsType.DRAGERING));
//			System.out.println("T�rring: " +p.naesteDelbehandlingGyldig(null, null, DelbehandlingsType.TOERRING));
//			System.out.println("F�rdigvarelager: "+p.naesteDelbehandlingGyldig(null, null, null));
//			System.out.println();
//		}
//		ObjectCreater.getInstance().udskrivMellemlagerPladser();


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

//		 String[][] testArray = new String[5][4];
//		 System.out.println(Arrays.toString(testArray[4]));
//		 String[][] testArray2 = {
//		 {"a", "b", "c", "d"},
//		 {"e", "f", "g", "h"},
//		 {"i", "j", "k", "l"},
//		 {"m", "n", "o", "p"},
//		 {"q", "r", "s", "t"}
//		 };
//		 testArray = testArray2;
//		
//		 System.out.println(testArray[0][3]);
//		 System.out.println(testArray[4][0]);
//		 System.out.println(Arrays.toString(testArray[2]));

	}

}
