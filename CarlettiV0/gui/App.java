package gui;

import java.util.GregorianCalendar;

import model.Delbehandling;
import model.Delbehandling.DelbehandlingsType;
import model.Behandling;
import model.Mellemvare;
import model.Palle;
import model.Produkttype;
import service.ObjectCreater;
import service.Service;
import service.Validering;
import dao.ListDao;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		ObjectCreater.getInstance().createSomeObjects();
		MainFrame.getInstance().update();
		MainFrame.getInstance().setVisible(true);
		Service.getInstance().setTestMode(true);
		
		Palle palle1 = Service.getInstance().opretPalle("234");
		Behandling behandling1 = Service.getInstance().opretBehandling("Behandling A");
		Delbehandling delbehandling1 = Service.getInstance().opretToerring("T¿rring 1", behandling1, Validering.varighedStringTilMillisekunder("02-01:00"), Validering.varighedStringTilMillisekunder("02-03:30"), Validering.varighedStringTilMillisekunder("02-07:00"), -1);
		Delbehandling delbehandling2 = Service.getInstance().opretDragering("Dragering 1", behandling1, Validering.varighedStringTilMillisekunder("00-04:00"), -1);
		Produkttype produkttype1 = Service.getInstance().opretProdukttype("Produkttype X", "X's beskrivelse", behandling1);
		Mellemvare mellemvare1 = Service.getInstance().opretMellemvare("123", produkttype1, palle1);
//		GregorianCalendar starttid = new GregorianCalendar();
//		starttid.setTimeInMillis(System.currentTimeMillis()-Validering.varighedStringTilMillisekunder("02-04:00"));	//Starttid s¾ttes til 
//		mellemvare1.addTidspunkt(starttid);
//		System.out.println(mellemvare1.getIgangvaerendeDelbehandling()+" "+mellemvare1.getTidspunkter().get(0).getTimeInMillis());
//
//		System.out.println(mellemvare1.getIgangvaerendeDelbehandling()+" "+mellemvare1.getTidspunkter().get(1).getTimeInMillis());
//		System.out.println(Service.getInstance().naesteDelbehandlingGyldig(mellemvare1, DelbehandlingsType.DRAGERING));
//		
		System.out.println(mellemvare1.erAfSammeType(null, null));
		
//		System.out.println("MELLEMVARER:");
//		for (Mellemvare m : ListDao.getListDao().mellemvarer()){
//			System.out.println(m);
//			Delbehandling d = m.getIgangvaerendeDelbehandling();
//			System.out.println("Dragering: " +m.naesteDelbehandlingGyldig(DelbehandlingsType.DRAGERING)+" "+d+" "+d.getDelbehandlingstype()+" "+d.getNextDelbehandling());
//			System.out.println("T¿rring: " +m.naesteDelbehandlingGyldig(DelbehandlingsType.TOERRING));
//			System.out.println("F¾rdigvarelager: "+m.naesteDelbehandlingGyldig(null));
//			System.out.println();
//		}
//		System.out.println();
//		System.out.println("PALLER:");
//		for (Palle p : ListDao.getListDao().paller()){
//			System.out.println("Palle: "+p+" "+p.getMellemvarer().size()+" stk.");
//			System.out.println("Dragering: " +p.naesteDelbehandlingGyldig(null, null ,DelbehandlingsType.DRAGERING));
//			System.out.println("T¿rring: " +p.naesteDelbehandlingGyldig(null, null, DelbehandlingsType.TOERRING));
//			System.out.println("F¾rdigvarelager: "+p.naesteDelbehandlingGyldig(null, null, null));
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
