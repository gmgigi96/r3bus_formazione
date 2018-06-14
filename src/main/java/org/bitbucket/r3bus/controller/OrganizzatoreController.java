package org.bitbucket.r3bus.controller;

import java.util.Set;

import javax.validation.Valid;

import org.bitbucket.r3bus.model.Attivita;
import org.bitbucket.r3bus.model.controller.Rebus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrganizzatoreController {

	@Autowired
	private Rebus rebus;

	// selezione centro

	@GetMapping("/organizzatore/")
	public String selezioneCentroForm(ModelMap model) {
		model.addAttribute("pageId", "select_center");
		return "message";
	}

	@PostMapping("/organizzatore/")
	public String selezioneCentro() {
		// processa dati
		long centroID = 1;
		return "redirect:/organizzatore/" + centroID + "/attivita/";
	}

	// lista attivita

	@GetMapping("/organizzatore/{centroID}/attivita/")
	public String listaAttivita(@PathVariable("centroID") Long centroID, ModelMap model) {
		rebus.setCentroGestito(centroID);
		Set<Attivita> attivita = rebus.getAttivitaDisponibili();
		model.addAttribute("activityList", attivita);
		// model.addAttribute("multiSelect", true);
		model.addAttribute("editActivity", true);
		model.addAttribute("pageId", "managed_activities");
		model.addAttribute("newActivity", true);
		return "activity_list";
	}

	// inserisci attivita

	@GetMapping("/organizzatore/{centroID}/attivita/inserisci")
	public String nuovaAttivitaForm(@PathVariable("centroID") Long centroID, ModelMap model) {
		model.addAttribute("showBackButton", true);
		model.addAttribute("backUrl", "./");
		model.addAttribute("pageId", "new_activity");
		model.addAttribute("activity", new Attivita()); // TODEL
		return "activity_form";
	}

	@PostMapping("/organizzatore/{centroID}/attivita/inserisci")
	public String nuovaAttivita(@PathVariable("centroID") Long centroID,
			@Valid @ModelAttribute("activity") Attivita activity, BindingResult bindingResult, ModelMap model) {
		model.addAttribute("showBackButton", true);
		model.addAttribute("backUrl", "./");
		model.addAttribute("pageId", "new_activity");
		if (bindingResult.hasErrors())
			return "activity_form";
		rebus.creaNuovaAttivita(centroID, activity);
		model.clear();
		return "redirect:/organizzatore/" + centroID + "/attivita/";
	}

	// modifica attivita

	@GetMapping("/organizzatore/{centroID}/attivita/{id}/modifica")
	public String modificaAttivitaForm(@PathVariable("centroID") Long centroID, @PathVariable("id") Long id,
			ModelMap model) {
		model.addAttribute("showBackButton", true);
		model.addAttribute("backUrl", "./../");
		// find activity and bind to model
		model.addAttribute("pageId", "edit_activity");
		model.addAttribute("activity", new Attivita()); // TODEL
		return "activity_form";
	}

	@PostMapping("/organizzatore/{centroID}/attivita/{id}/modifica")
	public String modificaAttivita(@Valid @ModelAttribute("activity") Attivita activity,
			@PathVariable("centroID") Long centroID, @PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("showBackButton", true);
		model.addAttribute("backUrl", "./../");
		// processa dati
		rebus.modificaAttivita(centroID, id, activity.getNome(), activity.getOrarioInizio(), activity.getOrarioFine()); // TOFIX
		return "redirect:/organizzatore/" + centroID + "/attivita/";
	}
}
