package org.bitbucket.r3bus.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
	public String statistics(@PathVariable("id") Long id, ModelMap model) {
		Map<String,Integer> mp = new HashMap<>();
		mp.put("Esercitazione", 23);
		mp.put("Sicurezza sul lavoro", 12);
		mp.put("Spring MVC", 34);
		model.addAttribute("activityMap", mp);
		return "stats";
	}
}
