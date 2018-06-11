package org.bitbucket.r3bus.controller;

import java.time.LocalDate;

import org.bitbucket.r3bus.model.controller.Rebus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DirettoreController {

	@Autowired
	private Rebus rebus;
	
	// selezione centro

	@GetMapping("/direttore/")
	public String selezioneCentroForm(ModelMap model) {
		model.addAttribute("selectMonth", true);
		model.addAttribute("centerActionUrl", "/direttore/");
		// model.addAttribute("centerMap", rebus.getMappaCentri());
		return "fragments/layout"; // TODO: cambiare, usare una vista vuota (da fare)
	}

	@PostMapping("/direttore/")
	public String selezioneCentro(@ModelAttribute("centerId") Long id, @ModelAttribute("date") String month) {
		return "redirect:/direttore/" + id + "/" + month + "/statistiche/";
	}

	// mostra statistiche

	@GetMapping("/direttore/{id}/{mese}/statistiche/")
	public String statistics(@PathVariable("id") Long id, @PathVariable("mese") String month, 
			ModelMap model) { 
		LocalDate inizio = LocalDate.parse(month + "-01");
		LocalDate fine = inizio.withDayOfMonth(inizio.lengthOfMonth());
		
		model.addAttribute("selectMonth", true);
		model.addAttribute("centerActionUrl", "/direttore/");
		// model.addAttribute("centerMap", rebus.getMappaCentri());
		model.addAttribute("activityMap", rebus.getElencoAttivita(id, inizio, fine));
		
		return "stats";
	}
}
