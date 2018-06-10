package org.bitbucket.r3bus.model.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.bitbucket.r3bus.model.Allievo;
import org.bitbucket.r3bus.model.Attivita;
import org.bitbucket.r3bus.model.Centro;
import org.junit.Before;
import org.junit.Test;

public class StatisticheControllerTest {

	private Centro centroConZeroAttivita;
	private Centro centroConUnAttivita;
	private Centro centroConDueAttivitaStessoGiorno;
	private LocalDateTime now = LocalDateTime.now().withHour(12);
	private StatisticheController statisticheController1;

	@Before
	public void setUp() throws Exception {
		centroConZeroAttivita = new Centro("centroConZeroAttivita", 1);
		centroConUnAttivita = new Centro("centroConUnAttivita", 11);
		centroConDueAttivitaStessoGiorno = new Centro("centroConDueAttivitaStessoGiorno", 100);

		centroConUnAttivita.addAttivita("attivita1", now, now.plusHours(3));

		centroConDueAttivitaStessoGiorno.addAttivita("attivita1", now, now.plusHours(3));
		centroConDueAttivitaStessoGiorno.addAttivita("attivita2", now.plusHours(4), now.plusHours(7));

		statisticheController1 = new StatisticheController();
	}

	@Test
	public void testGetNumeroAttivitaGiornaliere_unaSolaAttivitaInUnCentro() {
		LocalDate ieri = now.minusDays(1).toLocalDate();
		LocalDate domani = now.plusDays(1).toLocalDate();
		List<Number> mappa = statisticheController1.getNumeroAttivitaGiornaliere(centroConUnAttivita, ieri, domani);

		assertEquals(3, mappa.size());
		assertEquals(0L, mappa.get(0));
		assertEquals(1L, mappa.get(1));
		assertEquals(0L, mappa.get(2));
	}

	@Test
	public void testGetNumeroAttivitaGiornaliere_dueAttivitaNelloStessoGiorno() {		
		LocalDate ieri = now.minusDays(1).toLocalDate();
		LocalDate domani = now.plusDays(1).toLocalDate();
		List<Number> mappa = statisticheController1.getNumeroAttivitaGiornaliere(centroConDueAttivitaStessoGiorno, ieri, domani);

		assertEquals(3, mappa.size());
		assertEquals(0L, mappa.get(0));
		assertEquals(2L, mappa.get(1));
		assertEquals(0L, mappa.get(2));
	}

	@Test
	public void testGetNumeroAttivitaGiornaliere_unAttivitaInUnGiorno_intervalloNonConGiorno() {
		LocalDate domani = now.plusDays(1).toLocalDate();
		LocalDate dopodomani = now.plusDays(2).toLocalDate();
		
		List<Number> mappa = statisticheController1.getNumeroAttivitaGiornaliere(centroConDueAttivitaStessoGiorno, domani, dopodomani);

		assertEquals(2, mappa.size());
		assertEquals(0L, mappa.get(0));
		assertEquals(0L, mappa.get(1));
	}

	@Test
	public void testGetElencoAttivita_centroConNessunaAttivita() {
		LocalDate inizio = LocalDate.MIN;
		LocalDate fine = LocalDate.MAX;


		assertTrue(statisticheController1.getElencoAttivita(centroConZeroAttivita, inizio, fine).isEmpty());
	}

	@Test
	public void testGetElencoAttivita_centroConUnaAttivita_zeroPartecipanti() {
		LocalDate inizio = LocalDate.MIN;
		LocalDate fine = LocalDate.MAX;

		LocalDateTime now = LocalDateTime.now();

		Centro centroConUnAttivita_zeroPartecipanti = new Centro("centroConUnAttivita_zeroPartecipanti", 10);
		Attivita attivitaZeroPartecipanti = new Attivita("attivitaZeroPartecipanti", now, now.plusHours(1));
		centroConUnAttivita_zeroPartecipanti.addAttivita(attivitaZeroPartecipanti);

		Map<String, Number> res = statisticheController1.getElencoAttivita(centroConUnAttivita_zeroPartecipanti, inizio, fine);
		assertEquals(0, res.get(attivitaZeroPartecipanti.toString()));
		assertEquals(1, res.values().size());
	}

	@Test
	public void testGetElencoAttivita_centroConUnaAttivita_unPartecipante() {
		LocalDate inizio = LocalDate.MIN;
		LocalDate fine = LocalDate.MAX;

		LocalDateTime now = LocalDateTime.now();

		Centro centroConUnAttivita_unPartecipante = new Centro("centroConUnAttivita_unPartecipante", 100);
		Attivita attivitaConUnPartecipante = new Attivita("attivitaConUnPartecipante", now, now.plusHours(3));
		attivitaConUnPartecipante.prenota(new Allievo());

		centroConUnAttivita_unPartecipante.addAttivita(attivitaConUnPartecipante);

		Map<String, Number> res = statisticheController1.getElencoAttivita(centroConUnAttivita_unPartecipante, inizio, fine);
		assertEquals(1, res.get(attivitaConUnPartecipante.toString()));
		assertEquals(1, res.values().size());
	}

	@Test
	public void testGetElencoAttivita_centroConUnaAttivita_zeroPartecipanti_intervalloNonComprendenteAttivita() {
		LocalDateTime t = LocalDateTime.of(2018, 10, 10, 10, 0);

		Centro centroConUnAttivita_zeroPartecipanti = new Centro("centroConUnAttivita_zeroPartecipanti", 100);
		Attivita attivitaConZeroPartecipanti = new Attivita("attivitaConZeroPartecipanti", t, t.plusHours(3));

		centroConUnAttivita_zeroPartecipanti.addAttivita(attivitaConZeroPartecipanti);

		assertTrue(statisticheController1.getElencoAttivita(centroConUnAttivita_zeroPartecipanti, t.toLocalDate().minusDays(10), t.toLocalDate().minusDays(1)).isEmpty());
	}

	@Test
	public void testGetMediaPrenotati_centroConZeroAttivita() {
		LocalDate inizio = LocalDate.of(2018, 10, 10);
		LocalDate fine = inizio.plusDays(1);

		List<Number> res = statisticheController1.getMediaPrenotati(centroConZeroAttivita, inizio, fine);

		assertEquals(2, res.size());
		assertEquals(0f, res.get(0));
		assertEquals(0f, res.get(1));
	}

	@Test
	public void testGetMediaPrenotati_centroConUnaAttivita_zeroPartecipanti() {
		LocalDateTime inizio = LocalDateTime.of(2018, 10, 10, 10, 0);
		LocalDateTime fine = inizio.plusHours(3);

		Centro centroConUnAttivita_zeroPartecipanti = new Centro("centroConUnAttivita_zeroPartecipanti", 100);
		centroConUnAttivita_zeroPartecipanti.setCapienza(1);
		centroConUnAttivita_zeroPartecipanti.addAttivita("attivitaConZeroPartecipanti", inizio, fine);

		List<Number> res = statisticheController1.getMediaPrenotati(centroConUnAttivita_zeroPartecipanti,inizio.toLocalDate().minusDays(1), fine.toLocalDate().plusDays(1));

		assertEquals(3, res.size());
		assertEquals(0f, res.get(0));
		assertEquals(0f, res.get(1));
		assertEquals(0f, res.get(2));
	}

	@Test
	public void testGetMediaPrenotati_centroConUnaAttivita_unPartecipante() {
		LocalDateTime inizio = LocalDateTime.of(2018, 10, 10, 10, 0);
		LocalDateTime fine = inizio.plusHours(3);

		Centro centroConUnAttivita_unPartecipante = new Centro("centroConUnAttivita_unPartecipante", 100);
		centroConUnAttivita_unPartecipante.setCapienza(1);
		Attivita attivitaConUnPartecipante = new Attivita("attivitaConUnPartecipante", inizio, fine);
		attivitaConUnPartecipante.prenota(new Allievo());
		centroConUnAttivita_unPartecipante.addAttivita(attivitaConUnPartecipante);

		List<Number> res = statisticheController1.getMediaPrenotati(centroConUnAttivita_unPartecipante,inizio.toLocalDate().minusDays(1), fine.toLocalDate().plusDays(1));

		assertEquals(3, res.size());
		assertEquals(0f, res.get(0));
		assertEquals(1f, res.get(1));
		assertEquals(0f, res.get(2));
	}

}
