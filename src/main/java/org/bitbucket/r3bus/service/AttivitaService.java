package org.bitbucket.r3bus.service;

import java.util.Optional;

import org.bitbucket.r3bus.model.Attivita;
import org.bitbucket.r3bus.repository.AttivitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AttivitaService {
	
	@Autowired
	AttivitaRepository attivitaRepository;
	
	public Iterable<Attivita> findAll() {
        return this.attivitaRepository.findAll();
    }

    
    public void save(final Attivita attivita) {
        this.attivitaRepository.save(attivita);
    }

	public Attivita findbyId(Long id) {
		Optional<Attivita> attivita =  this.attivitaRepository.findById(id);
		
		if(attivita.isPresent()) {
			return attivita.get();
			
		}
		return null;
	}
	
	public void flush() {
		this.attivitaRepository.flush();
	}
}
