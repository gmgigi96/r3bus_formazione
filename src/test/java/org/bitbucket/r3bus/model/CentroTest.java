package org.bitbucket.r3bus.model;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class CentroTest {

	private Centro centro;
	private Attivita a;
	private LocalDateTime now;

	@Before
	public void setUp() throws Exception {
		now = LocalDateTime.now();
		centro = new Centro();
		a = new Attivita("sample", now, now.plusHours(1));
		a.setCodice(new Random().nextInt(1000));
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
		// TODO
	}
	
	@Test
	public void addAttivita_sameName() {
		// TODO
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
}
