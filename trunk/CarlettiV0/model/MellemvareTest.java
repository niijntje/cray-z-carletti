/**
 * 
 */
package model;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import model.Delbehandling.DelbehandlingsType;

import org.junit.Before;
import org.junit.Test;

import service.Service;
import service.Validering;

/**
 * @author nijntje
 *
 */
public class MellemvareTest {
	
	private Mellemvare mellemvare1, mellemvare2, mellemvare3, mellemvare4, mellemvare5, mellemvare6;
	private Mellemvare mellemvare7;
	private Mellemvare mellemvare11;
	private Produkttype produkttype11;
	private Produkttype produkttype12;
	private Delbehandling delbehandling11;
	private Delbehandling delbehandling12;
	private Mellemvare mellemvare15;
	private Mellemvare mellemvare17;
	private Produkttype produkttype17;
	private Produkttype produkttype18;
	private Delbehandling delbehandling17;
	private Delbehandling delbehandling18;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		//----------Test#1-----------//
		Palle palle1 = Service.getInstance().opretPalle("234");
		Behandling behandling1 = Service.getInstance().opretBehandling("Behandling A");
		Delbehandling delbehandling1 = Service.getInstance().opretToerring("T¿rring 1", behandling1, Validering.varighedStringTilMillisekunder("02-01:00"), Validering.varighedStringTilMillisekunder("02-03:30"), Validering.varighedStringTilMillisekunder("02-07:00"), -1);
		Delbehandling delbehandling2 = Service.getInstance().opretDragering("Dragering 1", behandling1, Validering.varighedStringTilMillisekunder("00-04:00"), -1);
		Produkttype produkttype1 = Service.getInstance().opretProdukttype("Produkttype X", "X's beskrivelse", behandling1);
		mellemvare1 = Service.getInstance().opretMellemvare("123", produkttype1, palle1);
		GregorianCalendar starttid1 = new GregorianCalendar();
		starttid1.setTimeInMillis(System.currentTimeMillis()-Validering.varighedStringTilMillisekunder("02-04:00"));	//Starttid s¾ttes til 2 d., 4 t. f¿r nuv¾rende systemtid
		mellemvare1.addTidspunkt(starttid1); //<--Dette 'snyder' systemet, da det aldrig benytter andet end sidste tidspunkt i listen
		//----------Test#2-----------//
		mellemvare2 = Service.getInstance().opretMellemvare("123", produkttype1, palle1);
		GregorianCalendar starttid2 = new GregorianCalendar();
		starttid2.setTimeInMillis(System.currentTimeMillis()-Validering.varighedStringTilMillisekunder("02-00:00"));	

		//----------Test#3-----------//
		mellemvare3 = Service.getInstance().opretMellemvare("123", produkttype1, palle1);
		GregorianCalendar starttid3 = new GregorianCalendar();
		starttid2.setTimeInMillis(System.currentTimeMillis()-Validering.varighedStringTilMillisekunder("03-00:00"));	

		//----------Test#4-----------//
		Behandling behandling4 = Service.getInstance().opretBehandling("Behandling A");
		Delbehandling delbehandling3 = Service.getInstance().opretDragering("Dragering 4", behandling4, Validering.varighedStringTilMillisekunder("00-04:00"), -1);
		Delbehandling delbehandling4 = Service.getInstance().opretToerring("T¿rring 4", behandling4, Validering.varighedStringTilMillisekunder("02-01:00"), Validering.varighedStringTilMillisekunder("02-03:30"), Validering.varighedStringTilMillisekunder("02-07:00"), -1);
		Produkttype produkttype4 = Service.getInstance().opretProdukttype("Produkttype X", "X's beskrivelse", behandling4);
		mellemvare4 = Service.getInstance().opretMellemvare("456", produkttype4, palle1);
		GregorianCalendar starttid4 = new GregorianCalendar();
		starttid4.setTimeInMillis(System.currentTimeMillis()-Validering.varighedStringTilMillisekunder("00-04:10"));
		mellemvare4.addTidspunkt(starttid4);
		
		//----------Test#5-----------//
		mellemvare5 = Service.getInstance().opretMellemvare("456", produkttype4, palle1);
		mellemvare5.goToNextDelbehandling();	//<--SŒ mellemvaren er nu i gang med sidste delbehandling i behandlingen
		GregorianCalendar starttid5 = new GregorianCalendar();
		starttid5.setTimeInMillis(System.currentTimeMillis()-Validering.varighedStringTilMillisekunder("02-04:00"));
		mellemvare5.addTidspunkt(starttid5);
		
		//----------Test#6-----------//
		mellemvare6 = Service.getInstance().opretMellemvare("123", produkttype1, palle1);
		GregorianCalendar starttid6 = new GregorianCalendar();
		starttid6.setTimeInMillis(System.currentTimeMillis()-Validering.varighedStringTilMillisekunder("02-04:00"));	
		mellemvare6.addTidspunkt(starttid1);
		
		//----------Test#7-----------//
		mellemvare7 = Service.getInstance().opretMellemvare("123", produkttype1, palle1);
		mellemvare7.goToNextDelbehandling();
		mellemvare7.goToNextDelbehandling();
		GregorianCalendar starttid7 = new GregorianCalendar();
		starttid7.setTimeInMillis(System.currentTimeMillis()-Validering.varighedStringTilMillisekunder("02-04:00"));	
		mellemvare7.addTidspunkt(starttid1);
		
		
		//----------Test#8-----------//
		
		//----------Test#9-----------//
					
		//----------Test#10----------//
		
		//----------Test11-12-13-14-16-----------//
		Behandling behandling11 = Service.getInstance().opretBehandling("B11");
		produkttype11 = Service.getInstance().opretProdukttype("Lakridspinde", "", behandling11);
		produkttype12 = Service.getInstance().opretProdukttype("Chokoladecrunch", "", behandling11);
		delbehandling11 = Service.getInstance().opretDragering("Dragering1", behandling11, 123214, -1);
		delbehandling12 = Service.getInstance().opretToerring("T¿rring1", behandling11, 12132132,231232123,243123122, -1);
		mellemvare11 = Service.getInstance().opretMellemvare("123", produkttype11, palle1);
		
		//----------Test#15-----------//
		mellemvare15 = Service.getInstance().opretMellemvare("231223", produkttype11, palle1);
		mellemvare15.setIgangvaerendeDelbehandling(null);
					
		//----------Test#17----------//
		Behandling behandling17 = Service.getInstance().opretBehandling("B11");
		produkttype17 = Service.getInstance().opretProdukttype("Lakridspinde", "", behandling11);
		produkttype18 = Service.getInstance().opretProdukttype("Chokoladecrunch", "", behandling11);
		delbehandling17 = Service.getInstance().opretDragering("Dragering1", behandling11, 123214, -1);
		delbehandling18 = Service.getInstance().opretToerring("T¿rring1", behandling11, 12132132,231232123,243123122, -1);
		mellemvare17 = Service.getInstance().opretMellemvare("123", produkttype11, palle1);
		mellemvare17.setIgangvaerendeDelbehandling(null);
		System.out.println(mellemvare17.getIgangvaerendeDelbehandling());
		mellemvare17.setProdukttype(null);
	}

	/**
	 * Test method for {@link model.Mellemvare#naesteDelbehandlingGyldig(model.Delbehandling.DelbehandlingsType)}.
	 */
	@Test
	public final void testNaesteDelbehandlingGyldig() {
		//----------Test#1-----------//
		assertTrue(Service.getInstance().naesteDelbehandlingGyldig(mellemvare1, DelbehandlingsType.DRAGERING));
		//----------Test#2-----------//
		assertFalse(Service.getInstance().naesteDelbehandlingGyldig(mellemvare2, DelbehandlingsType.DRAGERING));
		//----------Test#3-----------//
		assertFalse(Service.getInstance().naesteDelbehandlingGyldig(mellemvare3, DelbehandlingsType.DRAGERING));
		//----------Test#4-----------//
		assertTrue(Service.getInstance().naesteDelbehandlingGyldig(mellemvare4, DelbehandlingsType.TOERRING));
		//----------Test#5-----------//
		assertTrue(Service.getInstance().naesteDelbehandlingGyldig(mellemvare5, null));
		//----------Test#6-----------//
		assertFalse(Service.getInstance().naesteDelbehandlingGyldig(mellemvare6, DelbehandlingsType.TOERRING));		
		//----------Test#7-----------//
		assertFalse(Service.getInstance().naesteDelbehandlingGyldig(mellemvare7, DelbehandlingsType.DRAGERING));
		//----------Test#------------//
		
		//----------Test#------------//
		
		//----------Test#-------------//
		
		//----------Test#11-----------//
		assertTrue(mellemvare11.erAfSammeType(produkttype11, delbehandling11));
		
		//----------Test#12-----------//
		assertFalse(mellemvare11.erAfSammeType(produkttype11, delbehandling12));
		
		//----------Test#13-----------//
		assertFalse(mellemvare11.erAfSammeType(produkttype12, delbehandling12));
		//----------Test#14-----------//
		assertFalse(mellemvare11.erAfSammeType(produkttype12, delbehandling11));
		//----------Test#15-----------//
		assertTrue(mellemvare15.erAfSammeType(produkttype11, null));
		//----------Test#16-----------//
		assertFalse(mellemvare11.erAfSammeType(produkttype12, delbehandling12));
		//----------Test#17-----------//
		assertTrue(mellemvare17.erAfSammeType(null, null));
	}

}
