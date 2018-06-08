package org.bitbucket.r3bus.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.bitbucket.r3bus.model.Allievo;
import org.bitbucket.r3bus.model.Attivita;
import org.bitbucket.r3bus.model.Centro;
import org.bitbucket.r3bus.service.AllievoService;
import org.bitbucket.r3bus.service.CentroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@Autowired
	private AllievoService allievoService;
	@Autowired
	private CentroService centroService;

	@GetMapping("/")
	public String indexPage() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String role = auth.getAuthorities().toArray()[0].toString().toLowerCase();

		return "redirect:/" + role;
	}

	@RequestMapping("/loginSuccess")
	public String init() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String role = auth.getAuthorities().toArray()[0].toString().toLowerCase();

		// collega centro corrente
		if (role.equals("responsabile")) {
			String id = auth.getName().split("-")[1];
			System.out.printf("Managing center with id: %s\n", id);
			// rebus.setCentroGestito(id);
		}

		return "redirect:/";
	}

	@GetMapping("/login")
	public String loginPage() {
		return "login";
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
		Attivita attivita2 = new Attivita("seconda attivita", LocalDateTime.now().plusHours(10),
				LocalDateTime.now().plusHours(11));

		// creazione centro
		Centro centro = new Centro();
		centro.setCapienza(100);
		centro.setNome("primo Centro");

		centro = centroService.save(centro);

		allievoService.save(allievo);
		allievo.prenotaAttivita(attivita);
		centro.addAttivita(attivita);
		centro.addAttivita(attivita2);
		centroService.flush();

		return "index";
	}
}
