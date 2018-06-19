package org.bitbucket.r3bus.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.bitbucket.r3bus.model.Allievo;
import org.bitbucket.r3bus.model.Attivita;
import org.bitbucket.r3bus.model.Centro;
import org.bitbucket.r3bus.model.controller.Rebus;
import org.bitbucket.r3bus.service.AllievoService;
import org.bitbucket.r3bus.service.CentroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@Autowired
	private Rebus rebus;
	@Autowired
	private AllievoService allievoService;
	@Autowired
	private CentroService centroService;

	private final static String authorizationRequestBaseUri = "/oauth2/authorization";

	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;

	@GetMapping("/")
	public String indexPage() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String role = auth.getAuthorities().toArray()[0].toString().toLowerCase();

		if (role.equals("role_user")) {
			// è un utente con il login di google
			return "redirect:/allievo/";
		}

		return "redirect:/" + role + "/";
	}

	@RequestMapping("/loginSuccess")
	public String init() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String role = auth.getAuthorities().toArray()[0].toString().toLowerCase();

		// collega centro corrente
		if (role.equals("responsabile")) {
			String id = auth.getName().split("-")[1];
			System.out.printf("Managing center with id: %s\n", id);
			rebus.setCentroGestito(new Long(id));
		}

		return "redirect:/";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/login")
	public String loginPage(Model model) {
		Map<String, String> oauth2AuthenticationUrls = new HashMap<>();
		Iterable<ClientRegistration> clientRegistrations = null;

		if (clientRegistrationRepository instanceof Iterable) {
			clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
			clientRegistrations.forEach(registration -> oauth2AuthenticationUrls.put(registration.getClientName(),
					authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
		}

		model.addAttribute("urls", oauth2AuthenticationUrls);
		return "login";
	}

	/* ↓↓ mapping di prova (da eliminare) */

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

		return "index";
	}
}
