package org.bitbucket.r3bus.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResponsabileController {

	@Autowired
	private Rebus rebus;

	@ModelAttribute("currentLearner")
	public Allievo getCurrentLearner() {
		return rebus.getAllievoCorrente();
	}

	@ModelAttribute("username")
	public String getUsername() {
		return rebus.getCentroGestito().getNome();
	}

	@GetMapping("/responsabile/")
	public String defaultOperation() {
		return "redirect:/responsabile/allievo/";
	}

	// gestisci allievo

	@GetMapping("/responsabile/allievo/")
	public String gestisciAllievoForm() {
		if (rebus.allievoInGestione())
			return "redirect:/responsabile/attivita/";
		return "manage_learner";
	}

	// sulle post redirect se successo

	@PostMapping("/responsabile/allievo/")
	public String gestisciAllievo(@Valid @ModelAttribute("taxid") String codiceFiscale, HttpSession session) {
		if (rebus.gestisciAllievo(codiceFiscale))
			return "redirect:/responsabile/attivita/";

		session.setAttribute("taxid", codiceFiscale);
		session.setAttribute("isNew", true);
		return "redirect:/responsabile/allievo/inserisci";
	}

	// inserisci allievo

	@GetMapping("/responsabile/allievo/inserisci")
	public String nuovoAllievoForm(ModelMap model, HttpSession session) {
		String codiceFiscale = (String) session.getAttribute("taxid");
		model.addAttribute("isNew", session.getAttribute("isNew"));
		session.removeAttribute("isNew");
		session.removeAttribute("taxid");

		Allievo allievo = new Allievo();
		model.addAttribute("learner", allievo);
		allievo.setCodiceFiscale(codiceFiscale);

		return "new_learner";
	}

	@PostMapping("/responsabile/allievo/inserisci")
	public String nuovoAllievo(@Valid @ModelAttribute("learner") Allievo learner, BindingResult bindingResult,
			ModelMap model) {
		if (bindingResult.hasErrors())
			return "new_learner";

		rebus.aggiungiAllievo(learner);
		rebus.gestisciAllievo(learner.getCodiceFiscale());
		return "redirect:/responsabile/allievo/";
	}

	// rimuovi allievo

	@GetMapping("/responsabile/allievo/elimina")
	public String rimuoviAllievoForm(ModelMap model) {
		if (!rebus.allievoInGestione())
			return "redirect:/responsabile/allievo/";

		return "delete_learner";
	}

	@PostMapping("/responsabile/allievo/elimina")
	public String rimuoviAllievo() {
		rebus.eliminaAllievo();
		return "redirect:/responsabile/allievo/?message=deleted";
	}

	// gestisci attivita allievo

	@GetMapping("/responsabile/allievo/attivita/")
	public String attivitaAllievo(ModelMap model) {
		if (!rebus.allievoInGestione())
			return "redirect:/responsabile/allievo/";

		model.addAttribute("pageId", "booked_activities");
		Set<Attivita> ls = this.rebus.getAttivitaAllievo();

		model.addAttribute("activityList", ls);
		model.addAttribute("multiSelect", true);
		model.addAttribute("activityActionUrl", "/responsabile/allievo/attivita/annulla");
		return "activity_list";
	}

	@PostMapping("/responsabile/allievo/attivita/annulla")
	public String annullaAttivita(@RequestParam(value = "selection", required = false) List<Long> codiciAttivita,
			ModelMap model) {
		if (codiciAttivita == null)
			return "redirect:/responsabile/allievo/attivita/?message=select&type=warning";
		rebus.annullaPrenotazione(codiciAttivita);
		return "redirect:/responsabile/allievo/attivita/?message=success";
	}

	// lista attivita prenotabili

	@GetMapping("/responsabile/attivita/")
	public String attivitaDisponibili(ModelMap model) {
		Set<Attivita> gestiti = new HashSet<>();
		if (rebus.allievoInGestione()) {
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
	public String prenotaAttivita(@RequestParam(value = "selection", required = false) List<Long> codiciAttivita,
			ModelMap model) {
		if (codiciAttivita == null)
			return "redirect:/responsabile/attivita/?message=select&type=warning";
		rebus.prenotaAttivita(codiciAttivita);
		return "redirect:/responsabile/allievo/attivita/?message=booked";
	}

	// termina gestione

	@GetMapping("/responsabile/allievo/termina-gestione")
	public String terminaGestione() {
		rebus.terminaGestioneAllievo();
		return "redirect:/responsabile/allievo/?message=finished";
	}
}
