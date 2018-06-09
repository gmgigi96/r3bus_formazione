package org.bitbucket.r3bus.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DirettoreController {

	// selezione centro

	@GetMapping("/direttore/")
	public String selezioneCentroForm(ModelMap model) {
		model.addAttribute("selectMonth", true);
		return "fragments/layout";
	}

	@PostMapping("/direttore/")
	public String selezioneCentro() {
		// processa dati
		long id = 1;
		LocalDate month = LocalDate.now();
		return "redirect:/direttore/" + id + "/" + month.format(DateTimeFormatter.ofPattern("yyyy-MM"))
				+ "/statistiche/";
	}

	// mostra statistiche

	@GetMapping("/direttore/{id}/{mese}/statistiche/")
	public String statistics(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("selectMonth", true);
		Map<String, Integer> mp = new HashMap<>();
		mp.put("Esercitazione", 23);
		mp.put("Sicurezza sul lavoro", 12);
		mp.put("Spring MVC", 34);
		model.addAttribute("activityMap", mp);
		return "stats";
	}
}
