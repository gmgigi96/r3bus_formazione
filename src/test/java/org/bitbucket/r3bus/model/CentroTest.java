package org.bitbucket.r3bus.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

public class CentroTest {

	private Centro centro;
	private Attivita a;
	private LocalDateTime now;

	private Centro centroConZeroAttivita;

	@Before
	public void setUp() throws Exception {
		now = LocalDateTime.now();
		centro = new Centro();

		a = new Attivita("sample", now, now.plusHours(1));

		centroConZeroAttivita = new Centro();
	}

	@Test
	public void addAttivita_single() {
		centro.addAttivita("sample", now, now.plusHours(1));
		assertEquals(1, centro.contaAttivita());
	}

	@Test
	public void addAttivita_sequence() {
		centro.addAttivita("sample1", now, now.plusHours(1));

		centro.addAttivita("sample2", now.plusHours(1), now.plusHours(2));

		assertEquals(2, centro.contaAttivita());
	}

	@Test
	public void addAttivita_overlap() {
		centro.addAttivita("attivita1", now, now.plusHours(3));
		centro.addAttivita("attivitaCheNonDovrebbeSvolgersi", now.plusHours(1), now.plusHours(4));
		assertEquals(1, centro.getAttivita().size());
	}

	@Test
	public void addAttivita_stessiOrari() {
		centro.addAttivita("attivita1", now, now.plusHours(3));
		centro.addAttivita("attivitaCheNonDovrebbeSvolgersi", now, now.plusHours(3));
		assertEquals(1, centro.getAttivita().size());
	}

	@Test
	public void addAttivita_sameName() {
		String nomeAttivita = "attivitaConLoStessoNome";
		centro.addAttivita(nomeAttivita, now, now.plusHours(3));
		centro.addAttivita(nomeAttivita, now.plusHours(4), now.plusHours(7));
		assertEquals(2, centro.getAttivita().size());
	}

	@Test
	public void getAttivitaDisponibili_empty() {
		assertEquals(0, centro.getAttivitaDisponibili().size());
	}

	@Test
	public void getAttivitaDisponibili_none() {
		a.setOrarioInizio(now.minusDays(1));
		centro.addAttivita(a);
		assertEquals(0, centro.getAttivitaDisponibili().size());
	}

	@Test
	public void getAttivitaDisponibili_singleton() {
		a.setOrarioInizio(now.plusDays(1));
		centro.addAttivita(a);
		assertEquals(1, centro.getAttivitaDisponibili().size());
	}

	@Test
	public void getNumeroAttivita_zeroAttivita() {
		assertEquals(0, centroConZeroAttivita.getNumeroAttivita(LocalDate.now()));
	}

	@Test
	public void getNumeroAttivita_zeroAttivitaNelGiorno_unaAttivitaUnAltroGiorno() {
		centroConZeroAttivita.addAttivita("attivita1", now.plusDays(1), now.plusDays(1).plusHours(3));
		assertEquals(0, centroConZeroAttivita.getNumeroAttivita(now.toLocalDate()));
	}

	@Test
	public void getNumeroAttivita_unaAttivitaNelGiorno_zeroNegliAltriGiorni() {
		LocalDateTime time = LocalDateTime.of(2018, 6, 6, 10, 0);

		centroConZeroAttivita.addAttivita("attivitaNelGiorno", time.plusHours(1), time.plusHours(3));
		assertEquals(1, centroConZeroAttivita.getNumeroAttivita(time.toLocalDate()));
		assertEquals(0, centroConZeroAttivita.getNumeroAttivita(time.toLocalDate().plusDays(1)));
		assertEquals(0, centroConZeroAttivita.getNumeroAttivita(time.toLocalDate().minusDays(1)));
	}

	@Test
	public void getNumeroAttivita_unaAttivitaNelGiorno_unaIlGiornoPrima_zeroIlGiornoDopo() {
		LocalDateTime time = LocalDateTime.of(2018, 6, 6, 10, 0);

		centroConZeroAttivita.addAttivita("attivitaNelGiorno", time.plusHours(1), time.plusHours(3));
		centroConZeroAttivita.addAttivita("attivitaNelGiornoPrima", time.minusDays(1), time.minusDays(1).plusHours(3));
		assertEquals(1, centroConZeroAttivita.getNumeroAttivita(time.toLocalDate()));
		assertEquals(0, centroConZeroAttivita.getNumeroAttivita(time.toLocalDate().plusDays(1)));
		assertEquals(1, centroConZeroAttivita.getNumeroAttivita(time.toLocalDate().minusDays(1)));
	}

	@Test
	public void getNumeroAttivita_unaAttivitaNelGiorno_unaIlGiornoPrima_unaIlGiornoDopo() {
		LocalDateTime time = LocalDateTime.of(2018, 6, 6, 10, 0);

		centroConZeroAttivita.addAttivita("attivitaNelGiorno", time.plusHours(1), time.plusHours(3));
		centroConZeroAttivita.addAttivita("attivitaNelGiornoPrima", time.minusDays(1), time.minusDays(1).plusHours(3));
		centroConZeroAttivita.addAttivita("attivitaNelGiornoDopo", time.plusDays(1), time.plusDays(1).plusHours(3));
		assertEquals(1, centroConZeroAttivita.getNumeroAttivita(time.toLocalDate()));
		assertEquals(1, centroConZeroAttivita.getNumeroAttivita(time.toLocalDate().plusDays(1)));
		assertEquals(1, centroConZeroAttivita.getNumeroAttivita(time.toLocalDate().minusDays(1)));
	}

}
