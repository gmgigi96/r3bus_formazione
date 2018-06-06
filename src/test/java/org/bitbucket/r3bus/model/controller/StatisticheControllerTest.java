package org.bitbucket.r3bus.model.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import java.util.List;

import org.bitbucket.r3bus.model.Azienda;
import org.bitbucket.r3bus.model.Centro;
import org.junit.Before;
import org.junit.Test;

public class StatisticheControllerTest {

	private Azienda azienda;
	private Centro centroConUnAttivita;
	private Centro centroConDueAttivitaStessoGiorno;
	private LocalDateTime now = LocalDateTime.now();
	private StatisticheController statisticheController1;

	@Before
	public void setUp() throws Exception {
		azienda = new Azienda();
		centroConUnAttivita = new Centro();
		centroConDueAttivitaStessoGiorno = new Centro();
		azienda.addCentro(centroConUnAttivita);

		centroConUnAttivita.addAttivita("attivita1", now, now.plusHours(3));

		centroConDueAttivitaStessoGiorno.addAttivita("attivita2", now, now.plusHours(3));
		centroConDueAttivitaStessoGiorno.addAttivita("attivita2", now.plusHours(4), now.plusHours(7));

		statisticheController1 = new StatisticheController(azienda);
	}

	@Test
	public void testGetNumeroAttivitaGiornaliere_unaSolaAttivitaInUnCentro() {
		statisticheController1.setCentro(centroConUnAttivita);
		statisticheController1.setIntervallo(now.minusDays(1).toLocalDate(), now.plusDays(1).toLocalDate());
		List<Long> lista = statisticheController1.getNumeroAttivitaGiornaliere();

		assertEquals(3, lista.size());
		assertEquals(new Long(0), lista.get(0));
		assertEquals(new Long(1), lista.get(1));
		assertEquals(new Long(0), lista.get(2));
	}

	@Test
	public void testGetNumeroAttivitaGiornaliere_dueAttivitaNelloStessoGiorno() {
		statisticheController1.setCentro(centroConDueAttivitaStessoGiorno);
		statisticheController1.setIntervallo(now.minusDays(1).toLocalDate(), now.plusDays(1).toLocalDate());
		List<Long> lista = statisticheController1.getNumeroAttivitaGiornaliere();

		assertEquals(3, lista.size());
		assertEquals(new Long(0), lista.get(0));
		assertEquals(new Long(2), lista.get(1));
		assertEquals(new Long(0), lista.get(2));
	}

	@Test
	public void testGetNumeroAttivitaGiornaliere_unAttivitaInUnGiorno_intervalloNonConGiorno() {
		statisticheController1.setCentro(centroConDueAttivitaStessoGiorno);
		statisticheController1.setIntervallo(now.plusDays(1).toLocalDate(), now.plusDays(2).toLocalDate());
		List<Long> lista = statisticheController1.getNumeroAttivitaGiornaliere();

		assertEquals(2, lista.size());
		assertEquals(new Long(0), lista.get(0));
		assertEquals(new Long(0), lista.get(1));
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
