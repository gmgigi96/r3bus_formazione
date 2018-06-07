package org.bitbucket.r3bus.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

public class AttivitaTest {

	private Attivita attivitaConZeroPrenotati;
	private Attivita attivitaConUnPrenotato;
	private LocalDateTime time;
	private Allievo allievoInserito;
	private Allievo allievoNonAncoraInserito;

	@Before
	public void setUp() throws Exception {
		time = LocalDateTime.of(2018, 10, 10, 10, 0);
		attivitaConZeroPrenotati = new Attivita("attivitaConZeroPrenotati", time, time.plusHours(3));

		allievoInserito = new Allievo("mario", "rossi", "mario.rossi@bianco.verde", "0123456789", LocalDate.of(1985, 10, 10),
				"Colorandia", "cf1");
		allievoNonAncoraInserito = new Allievo("paolo", "rossi", "paolo.rossi@bianco.verde", "0321456789", LocalDate.of(1975, 9, 5),
				"Colorandia", "cf2");
		
		attivitaConUnPrenotato = new Attivita("attivitaConUnprenotato", time, time.plusHours(3));
		attivitaConUnPrenotato.prenota(allievoInserito);
	}

	@Test
	public void testPrenota_nessunAllievoPrenotato() {
		assertEquals(0, attivitaConZeroPrenotati.getNumeroAllieviPrenotati());
		attivitaConZeroPrenotati.prenota(allievoInserito);
		assertEquals(1, attivitaConZeroPrenotati.getNumeroAllieviPrenotati());
	}
	
	@Test
	public void testPrenota_unAllievoPrenotato_prenotazioneAllievoDiverso() {
		attivitaConUnPrenotato.prenota(allievoNonAncoraInserito);
		assertEquals(2, attivitaConZeroPrenotati.getNumeroAllieviPrenotati());
	}
	
	@Test
	public void testPrenota_unAllievoPrenotato_prenotazioneStessoAllievo() {
		attivitaConUnPrenotato.prenota(allievoInserito);
		assertEquals(0, attivitaConZeroPrenotati.getNumeroAllieviPrenotati());
	}

	@Test
	public void testAnnullaPrenotazione_utentePrenotato() {
		attivitaConUnPrenotato.annullaPrenotazione(allievoInserito);
		assertEquals(0, attivitaConUnPrenotato.getNumeroAllieviPrenotati());
	}
	
	@Test
	public void testAnnullaPrenotazione_utenteNonPrenotato() {
		attivitaConUnPrenotato.annullaPrenotazione(allievoNonAncoraInserito);
		assertEquals(1, attivitaConUnPrenotato.getNumeroAllieviPrenotati());
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
