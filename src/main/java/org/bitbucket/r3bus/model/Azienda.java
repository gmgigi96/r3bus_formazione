package org.bitbucket.r3bus.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

	public Map<String, Set<Attivita>> getAttivitaPrenotateAllievo(Allievo allievo) {
		Map<String, Set<Attivita>> prenotate = new HashMap<>();

		for (Attivita attivita : allievo.getAttivitaPrenotate()) {
			Centro centro = attivita.getCentro();
			Set<Attivita> temp;
			if (prenotate.containsKey(centro.getNome())) {
				temp = prenotate.get(centro.getNome());
			} else {
				temp = new HashSet<>();
			}
			temp.add(attivita);
			prenotate.put(centro.getNome(), temp);
		}

		return prenotate;
	}
}
