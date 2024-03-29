package org.bitbucket.r3bus.controller;

import java.time.LocalDate;
import java.util.Map;

import org.bitbucket.r3bus.model.controller.Rebus;
import org.bitbucket.r3bus.service.CentroService;
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
	private CentroService centroService;

	@Autowired
	private Rebus rebus;

	@ModelAttribute("selectMonth")
	public boolean selectMonth() {
		return true;
	}

	@ModelAttribute("centerActionUrl")
	public String centerActionUrl() {
		return "/direttore/";
	}

	@ModelAttribute("centerMap")
	public Map<Long, String> centerMap() {
		return centroService.getId2Nome();
	}

	// selezione centro

	@GetMapping("/direttore/")
	public String selezioneCentroForm(ModelMap model) {
		model.addAttribute("pageId", "select_stats");
		return "message";
	}

	@PostMapping("/direttore/")
	public String selezioneCentro(@ModelAttribute("centerId") Long id, @ModelAttribute("date") String month) {
		return "redirect:/direttore/" + id + "/" + month + "/statistiche/";
	}

	// mostra statistiche

	@GetMapping("/direttore/{id}/{mese}/statistiche/")
	public String statistics(@PathVariable("id") Long id, @PathVariable("mese") String month, ModelMap model) {
		LocalDate inizio = LocalDate.parse(month + "-01");
		LocalDate fine = inizio.withDayOfMonth(inizio.lengthOfMonth());

		model.addAttribute("currentMonth", month);
		model.addAttribute("currentCenter", id);
		model.addAttribute("centerCapacity", centroService.findbyId(id).getCapienza());
		model.addAttribute("activityMap", rebus.getPrenotazioniPerAttivita(id, inizio, fine));

		return "stats";
	}
}
