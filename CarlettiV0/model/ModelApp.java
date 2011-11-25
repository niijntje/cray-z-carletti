/**
 * 
 */
package model;


import java.util.HashMap;
import java.util.HashSet;

import service.Service;
import service.Validering;

/**
 * v.0.3
 * @author nijntje
 *
 */
public class ModelApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		
		MellemlagerPlads pl1 = new MellemlagerPlads("100100101");
		MellemlagerPlads pl2 = new MellemlagerPlads("100100102");
		
		Palle pa1 = new Palle("20000001");
		Palle pa2 = new Palle("20000002");
		
		Behandling b3 = new Behandling("B3 - M�negrus og lakridspinde. Kernest�rrelse 2.5-3.5");
		
		Delbehandling d1 = new Toerring("T�rring 1a", b3,100000000, 150000000, 200000000);
		b3.addDelbehandling(d1, -1);
		
		Produkttype pt1 = new Produkttype("Lakridspinde 1", "Gr�nne lakridspinde med kernest�rrelse 3.\nOpskrift: B3", b3);
		Produkttype pt2 = new Produkttype("M�negrus 2B", "Rester fra lakridspinde med varierende kernest�rrelse.\nOpskrift: B3", b3);
		
		
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
		
		
		System.out.println("Paller:");
		System.out.println(pa1);
		System.out.println(pa2);
		System.out.println();
		
		System.out.println("Palle "+pa1.toString());
		System.out.println("Placering: "+pa1.getPlacering()+":\n");
		for (Mellemvare m : pa1.getMellemvarer()){
			System.out.println(m.toStringLong());
		}
		System.out.println();
		
		HashMap<Mellemvare, Integer> mellemvaretyperMedAntal = pa1.getMellemvareAntalMapping();
		for (Mellemvare m : mellemvaretyperMedAntal.keySet()){
			System.out.println(m.getProdukttype()+"\t"+m.getIgangvaerendeDelbehandling()+ "\t"+mellemvaretyperMedAntal.get(m)+" stk.\t"+Validering.millisekunderTildato(m.getResterendeTidTilNaeste()));
		}
	}

}