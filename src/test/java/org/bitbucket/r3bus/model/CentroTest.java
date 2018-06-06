package org.bitbucket.r3bus.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.bitbucket.r3bus.service.CentroService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

public class CentroTest {
	
	private Centro centro;
	private Attivita a;
	private LocalDateTime now;

	@Before
	public void setUp() throws Exception {
		now = LocalDateTime.now();
		centro = new Centro();

		a = new Attivita("sample", now, now.plusHours(1));
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
