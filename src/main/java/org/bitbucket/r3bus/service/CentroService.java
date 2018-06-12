package org.bitbucket.r3bus.service;

import java.util.Optional;

import org.bitbucket.r3bus.model.Centro;
import org.bitbucket.r3bus.repository.CentroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CentroService {
	@Autowired
	CentroRepository centroRepository;
	
	public Iterable<Centro> findAll() {
        return this.centroRepository.findAll();
    }

    
    public Centro save(final Centro centro) {
        return this.centroRepository.save(centro);
    }

	public Centro findbyId(Long id) {
		Optional<Centro> centro =  this.centroRepository.findById(id);
		
		if(centro.isPresent()) {
			return centro.get();
			
		}
		return null;
	}
}
