package org.bitbucket.r3bus.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AttivitaTest {

	private Attivita attivitaConZeroPrenotati;
	private Attivita attivitaConUnPrenotato;
	private LocalDateTime time;
	private Allievo allievoInserito;
	private Allievo allievoNonAncoraInserito;

	@Autowired
	private TestEntityManager entityManager;

	@Before
	public void setUp() throws Exception {
		time = LocalDateTime.of(2018, 10, 10, 10, 0);
		attivitaConZeroPrenotati = new Attivita("attivitaConZeroPrenotati", time, time.plusHours(3));

		allievoInserito = new Allievo("mario", "rossi", "mario.rossi@bianco.verde", "0123456789",
				LocalDate.of(1985, 10, 10), "Colorandia", "cf100000000");
		allievoNonAncoraInserito = new Allievo("paolo", "rossi", "paolo.rossi@bianco.verde", "0321456789",
				LocalDate.of(1975, 9, 5), "Colorandia", "cf200000000");

		entityManager.clear();
		entityManager.persist(allievoInserito);
		entityManager.persist(allievoNonAncoraInserito);
		entityManager.flush();

		attivitaConUnPrenotato = new Attivita("attivitaConUnprenotato", time, time.plusHours(3));
		attivitaConUnPrenotato.prenota(allievoInserito);

		entityManager.persist(attivitaConZeroPrenotati);
		entityManager.persist(attivitaConUnPrenotato);
		entityManager.flush();
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
		assertEquals(2, attivitaConUnPrenotato.getNumeroAllieviPrenotati());
	}

	@Test
	public void testPrenota_unAllievoPrenotato_prenotazioneStessoAllievo() {
		attivitaConUnPrenotato.prenota(allievoInserito);
		assertEquals(1, attivitaConUnPrenotato.getNumeroAllieviPrenotati());
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
		LocalDateTime nuovoInizio = LocalDateTime.now();
		LocalDateTime nuovaFine = nuovoInizio.plusHours(3);
		String nuovoNome = "nuovoNome";

		attivitaConZeroPrenotati.aggiornaParametri(nuovoNome, nuovoInizio, nuovaFine);
		assertEquals(nuovoNome, attivitaConZeroPrenotati.getNome());
		assertEquals(nuovoInizio, attivitaConZeroPrenotati.getOrarioInizio());
		assertEquals(nuovaFine, attivitaConZeroPrenotati.getOrarioFine());
	}

	@Test
	public void testOverlap_inizioInMezzoAdOrariAttivita_fineDopoFineAttivita() {
		LocalDateTime time = LocalDateTime.of(2018, 10, 10, 10, 10);
		Attivita attivita = new Attivita("nome", time, time.plusHours(3));
		assertTrue(attivita.overlap(time.plusHours(1), time.plusHours(4)));
	}

	@Test
	public void testOverlap_inizioInMezzoAdOrariAttivita_fineInMezzoAdOrariAttivita() {
		LocalDateTime time = LocalDateTime.of(2018, 10, 10, 10, 10);
		LocalDateTime fine = time.plusHours(3);
		Attivita attivita = new Attivita("nome", time, fine);
		assertTrue(attivita.overlap(time.plusHours(1), fine.minusHours(2)));
	}

	@Test
	public void testOverlap_fineInMezzoAdOrariAttivita_inizioPrimaInizioAttivita() {
		LocalDateTime time = LocalDateTime.of(2018, 10, 10, 10, 10);
		Attivita attivita = new Attivita("nome", time, time.plusHours(3));
		assertTrue(attivita.overlap(time.minusHours(1), time.plusHours(2)));
	}

	@Test
	public void testOverlap_stessiOrari() {
		LocalDateTime time = LocalDateTime.of(2018, 10, 10, 10, 10);
		LocalDateTime fine = time.plusHours(3);
		Attivita attivita = new Attivita("nome", time, fine);
		assertTrue(attivita.overlap(time, fine));
	}

	@Test
	public void testOverlap_inizioUgualeFineAttivita() {
		LocalDateTime time = LocalDateTime.of(2018, 10, 10, 10, 10);
		LocalDateTime fine = time.plusHours(3);
		Attivita attivita = new Attivita("nome", time, fine);
		assertFalse(attivita.overlap(fine, fine.plusHours(3)));
	}

	@Test
	public void testOverlap_inizioUgualeInizioAttivita_fineInMezzoAdOrariAttivita() {
		LocalDateTime time = LocalDateTime.of(2018, 10, 10, 10, 10);
		LocalDateTime fine = time.plusHours(3);
		Attivita attivita = new Attivita("nome", time, fine);
		assertTrue(attivita.overlap(time, fine.minusHours(1)));
	}

	@Test
	public void testOverlap_inizioInMezzoAdOrariAttivita_fineUgualeFineAttivita() {
		LocalDateTime time = LocalDateTime.of(2018, 10, 10, 10, 10);
		LocalDateTime fine = time.plusHours(3);
		Attivita attivita = new Attivita("nome", time, fine);
		assertTrue(attivita.overlap(time.plusHours(1), fine));
	}

}
