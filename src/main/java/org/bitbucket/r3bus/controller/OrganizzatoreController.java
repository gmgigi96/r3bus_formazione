package org.bitbucket.r3bus.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.bitbucket.r3bus.model.Attivita;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrganizzatoreController {

	// selezione centro

	@GetMapping("/organizzatore")
	public String selezioneCentroForm() {
		return "fragments/layout";
	}

	@PostMapping("/organizzatore")
	public String selezioneCentro() {
		// processa dati
		long centroID = 1;
		return "redirect:/organizzatore/" + centroID + "/attivita";
	}

	// lista attivita

	@GetMapping("/organizzatore/{centroID}/attivita")
	public String listaAttivita(@PathVariable("centroID") Long centroID, ModelMap model) {
		List<Attivita> ls = new ArrayList<>(3);
		LocalDateTime n = LocalDateTime.now();
		int h = 0;
		ls.add(new Attivita("Esercitazione", n.plusHours(h++), n.plusHours(h++)));
		ls.add(new Attivita("Sicurezza sul lavoro", n.plusHours(h++), n.plusHours(h++)));
		ls.add(new Attivita("VueJS", n.plusHours(h++), n.plusHours(h++)));
		model.addAttribute("activityList", ls);
		// model.addAttribute("multiSelect", true);
		model.addAttribute("editActivity", true);
		model.addAttribute("pageId", "managed_activities");
		return "activity_list";
	}

	// inserisci attivita

	@GetMapping("/organizzatore/{centroID}/attivita/inserisci")
	public String nuovaAttivitaForm(@PathVariable("centroID") Long centroID, ModelMap model) {
		model.addAttribute("pageID", "new_activity");
		model.addAttribute("activity", new Attivita()); // TODEL
		return "activity_form";
	}

	@PostMapping("/organizzatore/{centroID}/attivita/inserisci")
	public String nuovaAttivita(@PathVariable("centroID") Long centroID,
			@Valid @ModelAttribute("activity") Attivita activity, BindingResult bindingResult, ModelMap model) {
		if (bindingResult.hasErrors())
			return "activity_form";
		// controller stuff
		model.clear();
		return "redirect:/organizzatore/" + centroID + "/attivita";
	}

	// modifica attivita

	@GetMapping("/organizzatore/{centroID}/attivita/{id}/modifica")
	public String modificaAttivitaForm(@PathVariable("centroID") Long centroID, @PathVariable("id") Long id,
			ModelMap model) {
		// find activity and bind to model
		model.addAttribute("pageID", "edit_activity");
		model.addAttribute("activity", new Attivita()); // TODEL
		return "activity_form";
	}

	@PostMapping("/organizzatore/{centroID}/attivita/{id}/modifica")
	public String modificaAttivita(@PathVariable("centroID") Long centroID, @PathVariable("id") Long id) {
		// processa dati
		return "redirect:/organizzatore/" + centroID + "/attivita";
	}
}
