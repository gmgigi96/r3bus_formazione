package org.bitbucket.r3bus.model;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class CentroTest {

	private Centro centro;
	private Attivita a;
	
	@Before
	public void setUp() throws Exception {
		centro = new Centro();
		a = new Attivita();
		a.setCodice(new Random().nextInt(1000));
	}

	@Test
	public void getAttivitaDisponibili_empty() {
		assertEquals(centro.getAttivitaDisponibili().size(), 0);
	}
	
	@Test
	public void getAttivitaDisponibili_none() {
		a.setData(LocalDateTime.now().minusDays(1));
		centro.addAttivita(a);
		assertEquals(centro.getAttivitaDisponibili().size(), 0);
	}
	
	@Test
	public void getAttivitaDisponibili_singleton() {
		a.setData(LocalDateTime.now().plusDays(1));
		centro.addAttivita(a);
		assertEquals(centro.getAttivitaDisponibili().size(), 1);
	}

}
