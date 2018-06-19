package org.bitbucket.r3bus.controller;

import java.util.HashMap;

import org.bitbucket.r3bus.model.controller.Rebus;
import org.bitbucket.r3bus.service.AllievoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AllievoController {
	
	@Autowired
	private Rebus rebus;
	
	@Autowired
	private AllievoService allievoService;
	
	@GetMapping("/allievo/")
	public String controllaAllievo(Model model) {
		model.addAttribute("managingLearner", true);
		model.addAttribute("pageId", "booked_activities");
//		Allievo v = this.allievoService.findByEmail(email);
//		Set<Attivita> prenotate = this.rebus.getAttivitaAllievo(v);
		
		model.addAttribute("activities", new HashMap<>());

		return "all_bookings";
	}
}
