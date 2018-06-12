package org.bitbucket.r3bus.repository;

import org.bitbucket.r3bus.model.Allievo;
import org.springframework.data.repository.CrudRepository;

public interface AllievoRepository extends CrudRepository<Allievo, Long>{
	
	public Allievo findByCodiceFiscale(String codiceFiscale);
	
	public void deleteByCodiceFiscale(String codiceFiscale);
}
