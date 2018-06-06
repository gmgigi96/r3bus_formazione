package org.bitbucket.r3bus.model;

import static org.junit.Assert.fail;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

public class AttivitaTest {
	
	private Attivita attivitaConZeroPrenotati;
	private LocalDateTime time;
	private Allievo allievo1;
	private Allievo allievo2;
	
	@Before
	public void setUp() throws Exception {
		time = LocalDateTime.of(2018, 10, 10, 10, 0);
		attivitaConZeroPrenotati = new Attivita("attivitaConZeroPrenotati", time, time.plusHours(3));
		
		//allievo1 = new Allievo();
	}

	@Test
	public void testPrenota() {
		
	}

	@Test
	public void testAnnullaPrenotazione() {
		fail("Not yet implemented");
	}

	@Test
	public void testAggiornaParametri() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNumeroAllieviPrenotati() {
		fail("Not yet implemented");
	}

	@Test
	public void testOverlap() {
		fail("Not yet implemented");
	}

}
