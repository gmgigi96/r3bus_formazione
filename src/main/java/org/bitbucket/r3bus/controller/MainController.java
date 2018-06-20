package org.bitbucket.r3bus.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.bitbucket.r3bus.model.Allievo;
import org.bitbucket.r3bus.model.controller.Rebus;
import org.bitbucket.r3bus.service.AllievoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
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

	private final static String authorizationRequestBaseUri = "/oauth2/authorization";

	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;

	@GetMapping("/")
	public String indexPage() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String role = auth.getAuthorities().toArray()[0].toString().toLowerCase();

		if (role.equals("role_user")) // TODO: find a way to change the
										// authority
			role = "allievo";

		return "redirect:/" + role + "/";
	}

	@RequestMapping("/loginSuccess")
	public String init(HttpSession session) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String role = auth.getAuthorities().toArray()[0].toString().toLowerCase();

		// login con oauth
		if (auth instanceof OAuth2AuthenticationToken) {
			DefaultOidcUser principal = (DefaultOidcUser) auth.getPrincipal();
			String email = principal.getEmail();
			Allievo allievo = this.allievoService.findByEmail(email);
			if (allievo != null) {
				session.setAttribute("allievo", allievo);
				return "redirect:/allievo/"; // TODO: fix this, set authorities
												// to contain "ALLIEVO"
			}

			auth.setAuthenticated(false);
			return "redirect:/login?oautherror";
		}

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
}
