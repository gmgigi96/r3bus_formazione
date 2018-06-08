package org.bitbucket.r3bus.model.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.bitbucket.r3bus.model.Allievo;
import org.bitbucket.r3bus.model.Attivita;
import org.bitbucket.r3bus.model.Azienda;
import org.bitbucket.r3bus.model.Centro;
import org.junit.Before;
import org.junit.Test;

public class StatisticheControllerTest {

	private Azienda azienda;
	private Centro centroConZeroAttivita;
	private Centro centroConUnAttivita;
	private Centro centroConDueAttivitaStessoGiorno;
	private LocalDateTime now = LocalDateTime.now();
	private StatisticheController statisticheController1;

	@Before
	public void setUp() throws Exception {
		azienda = new Azienda();
		centroConZeroAttivita = new Centro();
		centroConZeroAttivita.setCapienza(1);
		centroConUnAttivita = new Centro();
		centroConDueAttivitaStessoGiorno = new Centro();
		azienda.addCentro(centroConUnAttivita);

		centroConUnAttivita.addAttivita("attivita1", now, now.plusHours(3));

		centroConDueAttivitaStessoGiorno.addAttivita("attivita1", now, now.plusHours(3));
		centroConDueAttivitaStessoGiorno.addAttivita("attivita2", now.plusHours(4), now.plusHours(7));

		statisticheController1 = new StatisticheController(azienda);
	}

	@Test
	public void testGetNumeroAttivitaGiornaliere_unaSolaAttivitaInUnCentro() {
		statisticheController1.setCentro(centroConUnAttivita);
		LocalDate ieri = now.minusDays(1).toLocalDate();
		LocalDate oggi = now.toLocalDate();
		LocalDate domani = now.plusDays(1).toLocalDate();
		statisticheController1.setIntervallo(ieri, domani);
		Map<LocalDate, Long> mappa = statisticheController1.getNumeroAttivitaGiornaliere();

		assertEquals(3, mappa.size());
		assertEquals(new Long(0), mappa.get(ieri));
		assertEquals(new Long(1), mappa.get(oggi));
		assertEquals(new Long(0), mappa.get(domani));
	}

	@Test
	public void testGetNumeroAttivitaGiornaliere_dueAttivitaNelloStessoGiorno() {
		statisticheController1.setCentro(centroConDueAttivitaStessoGiorno);
		
		LocalDate ieri = now.minusDays(1).toLocalDate();
		LocalDate oggi = now.toLocalDate();
		LocalDate domani = now.plusDays(1).toLocalDate();
		statisticheController1.setIntervallo(ieri, domani);
		Map<LocalDate, Long> mappa = statisticheController1.getNumeroAttivitaGiornaliere();

		assertEquals(3, mappa.size());
		assertEquals(new Long(0), mappa.get(ieri));
		assertEquals(new Long(2), mappa.get(oggi));
		assertEquals(new Long(0), mappa.get(domani));
	}

	@Test
	public void testGetNumeroAttivitaGiornaliere_unAttivitaInUnGiorno_intervalloNonConGiorno() {
		statisticheController1.setCentro(centroConDueAttivitaStessoGiorno);
		
		LocalDate domani = now.plusDays(1).toLocalDate();
		LocalDate dopodomani = now.plusDays(2).toLocalDate();
		statisticheController1.setIntervallo(domani, dopodomani);
		Map<LocalDate, Long> mappa = statisticheController1.getNumeroAttivitaGiornaliere();

		assertEquals(2, mappa.size());
		assertEquals(new Long(0), mappa.get(domani));
		assertEquals(new Long(0), mappa.get(dopodomani));
	}

	@Test
	public void testGetElencoAttivita_centroConNessunaAttivita() {
		LocalDate inizio = LocalDate.MIN;
		LocalDate fine = LocalDate.MAX;

		statisticheController1.setCentro(centroConZeroAttivita);
		statisticheController1.setIntervallo(inizio, fine);

		assertTrue(statisticheController1.getElencoAttivita().isEmpty());
	}

	@Test
	public void testGetElencoAttivita_centroConUnaAttivita_zeroPartecipanti() {
		LocalDate inizio = LocalDate.MIN;
		LocalDate fine = LocalDate.MAX;

		LocalDateTime now = LocalDateTime.now();

		Centro centroConUnAttivita_zeroPartecipanti = new Centro();
		Attivita attivitaZeroPartecipanti = new Attivita("attivitaZeroPartecipanti", now, now.plusHours(1));
		centroConUnAttivita_zeroPartecipanti.addAttivita(attivitaZeroPartecipanti);
		
		statisticheController1.setCentro(centroConUnAttivita_zeroPartecipanti);
		statisticheController1.setIntervallo(inizio, fine);

		Map<String, Integer> res = statisticheController1.getElencoAttivita();
		assertEquals(new Integer(0), res.get(attivitaZeroPartecipanti.toString()));
		assertEquals(1, res.values().size());
	}

	@Test
	public void testGetElencoAttivita_centroConUnaAttivita_unPartecipante() {
		LocalDate inizio = LocalDate.MIN;
		LocalDate fine = LocalDate.MAX;

		LocalDateTime now = LocalDateTime.now();

		Centro centroConUnAttivita_unPartecipante = new Centro();
		Attivita attivitaConUnPartecipante = new Attivita("attivitaConUnPartecipante", now, now.plusHours(3));
		attivitaConUnPartecipante.prenota(new Allievo());

		centroConUnAttivita_unPartecipante.addAttivita(attivitaConUnPartecipante);

		statisticheController1.setCentro(centroConUnAttivita_unPartecipante);
		statisticheController1.setIntervallo(inizio, fine);

		Map<String, Integer> res = statisticheController1.getElencoAttivita();
		assertEquals(new Integer(1), res.get(attivitaConUnPartecipante.toString()));
		assertEquals(1, res.values().size());
	}

	@Test
	public void testGetElencoAttivita_centroConUnaAttivita_zeroPartecipanti_intervalloNonComprendenteAttivita() {
		LocalDateTime t = LocalDateTime.of(2018, 10, 10, 10, 0);

		Centro centroConUnAttivita_zeroPartecipanti = new Centro();
		Attivita attivitaConZeroPartecipanti = new Attivita("attivitaConZeroPartecipanti", t, t.plusHours(3));

		centroConUnAttivita_zeroPartecipanti.addAttivita(attivitaConZeroPartecipanti);

		statisticheController1.setCentro(centroConUnAttivita_zeroPartecipanti);
		statisticheController1.setIntervallo(t.toLocalDate().minusDays(10), t.toLocalDate().minusDays(1));

		assertTrue(statisticheController1.getElencoAttivita().isEmpty());
	}

	@Test
	public void testGetMediaPrenotati_centroConZeroAttivita() {
		LocalDate inizio = LocalDate.of(2018, 10, 10);
		LocalDate fine = inizio.plusDays(1);

		statisticheController1.setCentro(centroConZeroAttivita);
		statisticheController1.setIntervallo(inizio, fine);

		List<Float> res = statisticheController1.getMediaPrenotati();

		assertEquals(2, res.size());
		assertEquals(new Float(0), res.get(0));
		assertEquals(new Float(0), res.get(1));
	}

	@Test
	public void testGetMediaPrenotati_centroConUnaAttivita_zeroPartecipanti() {
		LocalDateTime inizio = LocalDateTime.of(2018, 10, 10, 10, 0);
		LocalDateTime fine = inizio.plusHours(3);

		Centro centroConUnAttivita_zeroPartecipanti = new Centro();
		centroConUnAttivita_zeroPartecipanti.setCapienza(1);
		centroConUnAttivita_zeroPartecipanti.addAttivita("attivitaConZeroPartecipanti", inizio, fine);

		statisticheController1.setCentro(centroConUnAttivita_zeroPartecipanti);
		statisticheController1.setIntervallo(inizio.toLocalDate().minusDays(1), fine.toLocalDate().plusDays(1));

		List<Float> res = statisticheController1.getMediaPrenotati();

		assertEquals(3, res.size());
		assertEquals(new Float(0), res.get(0));
		assertEquals(new Float(0), res.get(1));
		assertEquals(new Float(0), res.get(2));
	}

	@Test
	public void testGetMediaPrenotati_centroConUnaAttivita_unPartecipante() {
		LocalDateTime inizio = LocalDateTime.of(2018, 10, 10, 10, 0);
		LocalDateTime fine = inizio.plusHours(3);

		Centro centroConUnAttivita_unPartecipante = new Centro();
		centroConUnAttivita_unPartecipante.setCapienza(1);
		Attivita attivitaConUnPartecipante = new Attivita("attivitaConUnPartecipante", inizio, fine);
		attivitaConUnPartecipante.prenota(new Allievo());
		centroConUnAttivita_unPartecipante.addAttivita(attivitaConUnPartecipante);

		statisticheController1.setCentro(centroConUnAttivita_unPartecipante);
		statisticheController1.setIntervallo(inizio.toLocalDate().minusDays(1), fine.toLocalDate().plusDays(1));

		List<Float> res = statisticheController1.getMediaPrenotati();

		assertEquals(3, res.size());
		assertEquals(new Float(0), res.get(0));
		assertEquals(new Float(1), res.get(1));
		assertEquals(new Float(0), res.get(2));
	}

}
