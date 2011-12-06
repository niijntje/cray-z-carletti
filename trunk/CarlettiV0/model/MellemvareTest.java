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
	
	private Mellemvare mellemvare1;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		//----------Test#1-----------//
		Palle palle1 = Service.getInstance().opretPalle("234");
		Behandling behandling1 = Service.getInstance().opretBehandling("Behandling A");
		Delbehandling delbehandling1 = Service.getInstance().opretToerring("T¿rring 1", behandling1, Validering.varighedStringTilMillisekunder("02-01:00"), Validering.varighedStringTilMillisekunder("02-03:30"), Validering.varighedStringTilMillisekunder("02-07:00"), -1);
		Produkttype produkttype1 = Service.getInstance().opretProdukttype("Produkttype X", "X's beskrivelse", behandling1);
		mellemvare1 = Service.getInstance().opretMellemvare("123", produkttype1, palle1);
		GregorianCalendar starttid = new GregorianCalendar();
		starttid.setTimeInMillis(System.currentTimeMillis()-Validering.varighedStringTilMillisekunder("02-03:00"));	//Starttid s¾ttes til 
		mellemvare1.addTidspunkt(starttid);
		
		
	}

	/**
	 * Test method for {@link model.Mellemvare#naesteDelbehandlingGyldig(model.Delbehandling.DelbehandlingsType)}.
	 */
	@Test
	public final void testNaesteDelbehandlingGyldig() {
		//----------Test#1-----------//
		assertTrue(Service.getInstance().naesteDelbehandlingGyldig(mellemvare1, DelbehandlingsType.DRAGERING));
		
	}

}
