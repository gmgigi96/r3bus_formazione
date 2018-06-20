package org.bitbucket.r3bus.controller;

import org.bitbucket.r3bus.model.Allievo;
import org.bitbucket.r3bus.model.controller.Rebus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class AllievoController {

	@Autowired
	private Rebus rebus;

	@GetMapping("/allievo/")
	public String controllaAllievo(@SessionAttribute("allievo") Allievo allievo, Model model) {
		DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		model.addAttribute("username", principal.getFullName());
		model.addAttribute("activities", rebus.getAttivitaPrenotateAllievo(allievo));

		return "all_bookings";
	}
}
