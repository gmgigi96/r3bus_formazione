package org.bitbucket.r3bus.service;

import java.util.Optional;

import org.bitbucket.r3bus.model.Allievo;
import org.bitbucket.r3bus.repository.AllievoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AllievoService{
	@Autowired
	AllievoRepository  allievoRepository;
	
	public Iterable<Allievo> findAll() {
        return this.allievoRepository.findAll();
    }

    
    public void add(final Allievo allievo) {
        this.allievoRepository.save(allievo);
    }

	public Allievo findbyId(Long id) {
		Optional<Allievo> allievo =  this.allievoRepository.findById(id);
		
		if(allievo.isPresent()) {
			return allievo.get();
			
		}
		return null;
	}
}
