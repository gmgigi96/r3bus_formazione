package org.bitbucket.r3bus.model;

import org.bitbucket.r3bus.service.AllievoService;
import org.bitbucket.r3bus.service.CentroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Azienda {

	private String nome;

	@Autowired
	private AllievoService allievi;
	@Autowired
	private CentroService centri;

	// gestione allievo
	public Allievo getAllievo(String codiceFiscale) {
		return allievi.findByCodiceFiscale(codiceFiscale);
	}

	public void eliminaAllievo(Allievo allievo) {
		this.allievi.deleteAllievoByCodiceFiscale(allievo.getCodiceFiscale());
	}

	public Allievo salvaAllievo(Allievo allievo) {
		return this.allievi.save(allievo);
	}

	// gestione centro
	public Centro getCentro(Long codiceCentro) {
		return this.centri.findbyId(codiceCentro);
	}

	public Centro salvaCentro(Centro centro) {
		return this.centri.save(centro);
	}
}
