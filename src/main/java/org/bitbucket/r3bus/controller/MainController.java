package org.bitbucket.r3bus.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.Valid;

import org.bitbucket.r3bus.model.Allievo;
import org.bitbucket.r3bus.model.Attivita;
import org.bitbucket.r3bus.model.Centro;
import org.bitbucket.r3bus.service.AllievoService;
import org.bitbucket.r3bus.service.AttivitaService;
import org.bitbucket.r3bus.service.CentroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@Autowired
	private AllievoService allievoService;
	@Autowired
	private AttivitaService attivitaService;
	@Autowired
	private CentroService centroService;

	@GetMapping("/")
	public String indexPage() {
		return "index";
	}

	@RequestMapping("/login")
	public String contactPage() {
		return "login";
	}

	@GetMapping("/allievo/nuovo")
	public String newLearnerForm(ModelMap model) {
		model.addAttribute("learner", new Allievo()); // TOFIX
		return "new_learner";
	}

	@PostMapping("/allievo/nuovo")
	public String saveNewLearner(@Valid @ModelAttribute("learner") Allievo learner, BindingResult bindingResult,
			ModelMap model) {
		if (bindingResult.hasErrors())
			return "new_learner";
		// add to db / call grasp controller
		model.clear();
		return "redirect:/";
	}

	/* ↓↓ mapping di prova (da eliminare) */

	@GetMapping("/about")
	public String aboutPage() {
		return "about";
	}

	@GetMapping("/portfolio")
	public String portfoliotPage() {
		return "portfolio";
	}

	@GetMapping("/aggiungi")
	public String aggiungiPage() {
		// crezione allievo
		Allievo allievo = new Allievo();
		allievo.setCodiceFiscale("codiceFiscale");
		allievo.setCognome("Elsayed");
		allievo.setDataNascita(LocalDate.now());
		allievo.setEmail("email@email.com");
		allievo.setLuogoNascita("Anzio");
		allievo.setNome("Omar");
		allievo.setTelefono("000000");
		// creazione attivita
		Attivita attivita = new Attivita("prima attivita", LocalDateTime.now(), LocalDateTime.now().plusHours(10));
		attivita.prenota(allievo);
		// creazione centro
		Centro centro = new Centro();
		centro.setCapienza(100);
		centro.setNome("primo Centro");

		allievoService.add(allievo);
		attivitaService.add(attivita);
		centroService.add(centro);
		return "index";
	}
}
