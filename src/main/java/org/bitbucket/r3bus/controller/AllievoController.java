package org.bitbucket.r3bus.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bitbucket.r3bus.model.Attivita;
import org.bitbucket.r3bus.model.controller.Rebus;
import org.bitbucket.r3bus.service.AllievoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AllievoController {

	@Autowired
	private Rebus rebus;

	@GetMapping("/allievo/")
	public String controllaAllievo(ModelMap model) {
		// Allievo v = this.allievoService.findByEmail(email);
		// Set<Attivita> prenotate = this.rebus.getAttivitaAllievo(v);

		DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", principal.getFullName());

		// TO REMOVE ↓
		rebus.setCentroGestito(1L);

		Map<String, Set<Attivita>> prenotate = new HashMap<>();
		prenotate.put("Sample Center", rebus.getAttivitaDisponibili());
		prenotate.put("Sample Center 2", rebus.getAttivitaDisponibili());
		prenotate.put("Sample Center 3", rebus.getAttivitaDisponibili());
		prenotate.put("Sample Center 4", rebus.getAttivitaDisponibili());
		prenotate.put("Sample Center 5", rebus.getAttivitaDisponibili());
		prenotate.put("Sample Center 6", rebus.getAttivitaDisponibili());
		prenotate.put("Sample Center 7", rebus.getAttivitaDisponibili());
		prenotate.put("Sample Center 8", rebus.getAttivitaDisponibili());
		prenotate.put("Sample Center 9", rebus.getAttivitaDisponibili());
		prenotate.put("Sample Center 10", rebus.getAttivitaDisponibili());
		prenotate.put("Sample Center 11", rebus.getAttivitaDisponibili());
		prenotate.put("Sample Center 12", rebus.getAttivitaDisponibili());
		prenotate.put("Sample Center 13", rebus.getAttivitaDisponibili());
		prenotate.put("Sample Center 14", rebus.getAttivitaDisponibili());
		prenotate.put("Sample Center 15", rebus.getAttivitaDisponibili());

		model.addAttribute("activities", prenotate);

		return "all_bookings";
	}
}
