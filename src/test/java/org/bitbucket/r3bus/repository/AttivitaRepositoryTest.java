package org.bitbucket.r3bus.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.bitbucket.r3bus.model.Attivita;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AttivitaRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private AttivitaRepository attivitaRepository;
	
	private Attivita a1;

	@Before
	public void setUp() throws Exception {
		a1 = new Attivita("attivita1", LocalDateTime.now(), LocalDateTime.now());
		entityManager.persist(a1);
		entityManager.flush();
	}

	@Test
	public void testExistsById_attivitaEsiste() {
		assertTrue(attivitaRepository.existsById(a1.getId()));
	}
	
	@Test
	public void testExistsById_attivitaNonEsiste() {
		assertFalse(attivitaRepository.existsById(a1.getId() - 1));
	}
	
	@Test
	public void testCount_unaAttivita() {
		assertEquals(1, attivitaRepository.count());
	}
	
}
