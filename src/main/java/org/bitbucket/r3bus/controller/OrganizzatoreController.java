package org.bitbucket.r3bus.controller;

import java.util.Map;
import java.util.SortedSet;

import javax.validation.Valid;

import org.bitbucket.r3bus.model.Attivita;
import org.bitbucket.r3bus.model.controller.Rebus;
import org.bitbucket.r3bus.model.exception.OverlapException;
import org.bitbucket.r3bus.service.CentroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@Autowired
	private CentroService centroService;

	@ModelAttribute("centerMap")
	public Map<Long, String> centerMap() {
		return centroService.getId2Nome();
	}

	// selezione centro

	@GetMapping("/organizzatore/")
	public String selezioneCentroForm(ModelMap model) {
		model.addAttribute("pageId", "select_center");
		return "message";
	}

	@PostMapping("/organizzatore/")
	public String selezioneCentro(@ModelAttribute("centerId") Long centerId, Model model) {
		// processa dati
		return "redirect:/organizzatore/" + centerId + "/attivita/";
	}

	// lista attivita

	@GetMapping("/organizzatore/{centroID}/attivita/")
	public String listaAttivita(@PathVariable("centroID") Long centroID, ModelMap model) {
		rebus.setCentroGestito(centroID);
		SortedSet<Attivita> attivita = rebus.getAttivitaDisponibili();
		model.addAttribute("activityList", attivita);
		// model.addAttribute("multiSelect", true);
		model.addAttribute("editActivity", true);
		model.addAttribute("pageId", "managed_activities");
		model.addAttribute("centerActionUrl", "/organizzatore/");
		model.addAttribute("currentCenter", centroID);
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

		try {
			rebus.creaNuovaAttivita(centroID, activity);
		} catch (OverlapException e) {
			model.addAttribute("overlapping", true);
			return "activity_form";
		}

		model.clear();
		return "redirect:/organizzatore/" + centroID + "/attivita/";
	}

	// modifica attivita

	@GetMapping("/organizzatore/{centroID}/attivita/{id}/modifica")
	public String modificaAttivitaForm(@PathVariable("centroID") Long centroID, @PathVariable("id") Long id,
			ModelMap model) {
		model.addAttribute("showBackButton", true);
		model.addAttribute("backUrl", "./../");
		model.addAttribute("pageId", "edit_activity");
		model.addAttribute("activity", rebus.getAttivita(centroID, id));
		return "activity_form";
	}

	@PostMapping("/organizzatore/{centroID}/attivita/{id}/modifica")
	public String modificaAttivita(@Valid @ModelAttribute("activity") Attivita activity,
			@PathVariable("centroID") Long centroID, @PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("showBackButton", true);
		model.addAttribute("backUrl", "./../");
		model.addAttribute("pageId", "edit_activity");

		// processa dati
		try {
			rebus.modificaAttivita(centroID, id, activity);
		} catch (OverlapException e) {
			model.addAttribute("overlapping", true);
			return "activity_form";
		}
		
		return "redirect:/organizzatore/" + centroID + "/attivita/";
	}
}
