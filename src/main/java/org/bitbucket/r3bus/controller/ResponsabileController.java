package org.bitbucket.r3bus.controller;

import javax.validation.Valid;

import org.bitbucket.r3bus.model.Allievo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;

@Controller
public class ResponsabileController {

	@GetMapping("/responsabile")
	public String defaultOperation() {
		return "redirect:/responsabile/allievo";
	}

	// gestisci allievo

	@GetMapping("/responsabile/allievo")
	public String gestisciAllievoForm() {
		throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/responsabile/allievo")
	public String gestisciAllievo() {
		// processa dati
		return "redirect:/responsabile/attivita";
	}

	// inserisci allievo

	@GetMapping("/responsabile/allievo/inserisci")
	public String nuovoAllievoForm(ModelMap model) {
		model.addAttribute("learner", new Allievo()); // TOFIX
		return "new_learner";
	}

	@PostMapping("/responsabile/allievo/inserisci")
	public String nuovoAllievo(@Valid @ModelAttribute("learner") Allievo learner, BindingResult bindingResult,
			ModelMap model) {
		if (bindingResult.hasErrors())
			return "new_learner";
		// add to db / call grasp controller
		model.clear();
		return "redirect:/responsabile/allievo";
	}

	// rimuovi allievo

	@GetMapping("/responsabile/allievo/rimuovi")
	public String rimuoviAllievoForm() {
		throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/responsabile/allievo/rimuovi")
	public String rimuoviAllievo() {
		// processa dati
		return "redirect:/responsabile/allievo";
	}

	// gestisci attivita allievo

	@GetMapping("/responsabile/allievo/attivita")
	public String attivitaAllievo() {
		throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
	}
	
	// lista attivita prenotabili
	
	@GetMapping("/responsabile/attivita")
	public String attivitaDisponibili() {
		throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
	}
	
	// prenota attivita
	
	@PostMapping("/responsabile/attivita/prenota")
	public String prenotaAttivita() {
		return "redirect:/responsabile/allievo/attivita";
	}
	
	// termina gestione
	
	@GetMapping("/responsabile/allievo/termina-gestione")
	public String terminaGestione() {
		return "redirect:/responsabile/allievo";
	}
}
