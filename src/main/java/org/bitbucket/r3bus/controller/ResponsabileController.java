package org.bitbucket.r3bus.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.bitbucket.r3bus.model.Allievo;
import org.bitbucket.r3bus.model.Attivita;
import org.bitbucket.r3bus.model.controller.Rebus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ResponsabileController {

	@Autowired
	private Rebus rebus;

	@GetMapping("/responsabile/")
	public String defaultOperation() {
		return "redirect:/responsabile/allievo/";
	}

	// gestisci allievo

	@GetMapping("/responsabile/allievo/")
	public String gestisciAllievoForm() {
		return "manage_learner";
	}

	// sulle post redirect se successo

	@PostMapping("/responsabile/allievo/")
	public String gestisciAllievo(@Valid @ModelAttribute("taxid") String codiceFiscale) {
		// processa dati
		if (rebus.gestisciAllievo(codiceFiscale))
			return "redirect:/responsabile/attivita/";
		return "manage_learner";
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
		rebus.aggiungiAllievo(learner);
		model.clear();
		return "redirect:/responsabile/allievo/";
	}

	// rimuovi allievo

	@GetMapping("/responsabile/allievo/elimina")
	public String rimuoviAllievoForm(ModelMap model) {
		model.addAttribute("managingLearner", true);
		return "delete_learner";
	}

	@PostMapping("/responsabile/allievo/elimina")
	public String rimuoviAllievo() {
		// processa dati
		rebus.eliminaAllievo();
		return "redirect:/responsabile/allievo/?message=deleted";
	}

	// gestisci attivita allievo

	@GetMapping("/responsabile/allievo/attivita")
	public String attivitaAllievo(ModelMap model) {
		model.addAttribute("pageId", "booked_activities");
		model.addAttribute("managingLearner", true);
		Set<Attivita> ls = this.rebus.getAttivitaAllievo();

		model.addAttribute("activityList", ls);
		return "activity_list";
	}

	// lista attivita prenotabili

	@GetMapping("/responsabile/attivita/")
	public String attivitaDisponibili(ModelMap model) {
		Set<Attivita> gestiti = new HashSet<>();
		if (rebus.allievoInGestione()) {
			model.addAttribute("managingLearner", true);
			model.addAttribute("multiSelect", true);
			gestiti = this.rebus.getAttivitaAllievo();
		}
		model.addAttribute("pageId", "available_activities");
		model.addAttribute("activityActionUrl", "prenota");

		Set<Attivita> ls = this.rebus.getAttivitaDisponibili();
		ls.removeAll(gestiti);

		model.addAttribute("activityList", ls);
		return "activity_list";
	}

	// prenota attivita

	@PostMapping("/responsabile/attivita/prenota")
	public String prenotaAttivita(@ModelAttribute("selection") List<Long> codiciAttivita, ModelMap model) {
		model.addAttribute("managingLearner", true);

		rebus.prenotaAttivita(codiciAttivita);

		return "redirect:/responsabile/allievo/attivita/?message=success";
	}

	// termina gestione

	@GetMapping("/responsabile/allievo/termina-gestione")
	public String terminaGestione() {
		rebus.terminaGestioneAllievo();
		return "redirect:/responsabile/allievo/?message=finished";
	}
}
