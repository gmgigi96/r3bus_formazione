package org.bitbucket.r3bus.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;

@Controller
public class DirettoreController {

	// selezione centro
	
	@GetMapping("/direttore")
	public String selezioneCentroForm() {
		throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/direttore")
	public String selezioneCentro() {
		// processa dati
		long id = 1;
		return "redirect:/direttore/" + id + "/statistiche";
	}
	
	// mostra statistiche
	
	@GetMapping("/direttore/{id}/statistiche")
	public String statistics(@PathVariable("id") Long id) {
		throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
	}
}
