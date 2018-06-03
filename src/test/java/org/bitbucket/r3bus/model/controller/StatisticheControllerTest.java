package org.bitbucket.r3bus.model.controller;

import static org.junit.Assert.fail;

import org.bitbucket.r3bus.model.Azienda;
import org.bitbucket.r3bus.model.Centro;
import org.junit.Before;
import org.junit.Test;

public class StatisticheControllerTest {
	
	private Azienda aziendaConUnCentro;
	private Centro centro;
	
	@Before
	public void setUp() throws Exception {
		aziendaConUnCentro = new Azienda();		
		centro = new Centro();
		aziendaConUnCentro.addCentro(centro);
	}

	@Test
	public void testGetNumeroAttivitaGiornaliere() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetElencoAttivita() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMediaPrenotati() {
		fail("Not yet implemented");
	}

}
