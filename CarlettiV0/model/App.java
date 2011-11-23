/**
 * 
 */
package model;

import service.Service;

/**
 * v.0.3
 * @author nijntje
 *
 */
public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Service.getInstance().createSomeObjects();
		Service.getInstance().udskrivMellemlagerPladser();
		
//		MellemlagerPlads pl1 = new MellemlagerPlads("100100101");
//		MellemlagerPlads pl2 = new MellemlagerPlads("100100102");
//		
//		Palle pa1 = new Palle("20000001");
//		Palle pa2 = new Palle("20000002");
//		
//		Behandling b3 = new Behandling("B3 - MŒnegrus og lakridspinde. Kernest¿rrelse 2.5-3.5");
//		
//		Delbehandling d1 = new Toerring("T¿rring 1", b3,100000000, 150000000, 200000000);
//		b3.addDelbehandling(d1, -1);
//		
//		Produkttype pt1 = new Produkttype("Lakridspinde 1", "Gr¿nne lakridspinde med kernest¿rrelse 3.\nOpskrift: B3", b3);
//		
//		
//		Mellemvare m1 = new Mellemvare("300000001", pt1, pa1);
//		Mellemvare m2 = new Mellemvare("300000002", pt1, pa1);
//		Mellemvare m3 = new Mellemvare("300000003", pt1, pa1);
//		Mellemvare m4 = new Mellemvare("300000004", pt1, pa1);
//		Mellemvare m5 = new Mellemvare("300000005", pt1, pa1);
//		Mellemvare m6 = new Mellemvare("300000006", pt1, pa1);
//		Mellemvare m7 = new Mellemvare("300000007", pt1, null);
//		pa1.addMellemvare(m1);
//		pa1.addMellemvare(m2);
//		pa1.addMellemvare(m3);
//		pa1.addMellemvare(m4);
//		pa1.addMellemvare(m5);
//		pa1.addMellemvare(m6);
//		pa1.addMellemvare(m7);
//		
//		pa1.placerPalle(pl2);
//		
//		
//		System.out.println("Paller:");
//		System.out.println(pa1);
//		System.out.println(pa2);
//		System.out.println();
//		
//		System.out.println("Palle "+pa1.toString());
//		System.out.println("Placering: "+pa1.getPlacering()+":\n");
//		for (Mellemvare m : pa1.getMellemvarer()){
//			System.out.println(m.toStringLong());
//		}
//		System.out.println();
//		
//		System.out.println("Hej Rasmus! :-)");
	}

}
