package xyz.bzennn.wavyarch.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xyz.bzennn.wavyarch.form.UserSignInForm;

/**
 * User Sign In controller
 *
 * @author bzennn
 * @version 1.0
 */
@Controller
@RequestMapping("signin")
public class SignInController {
	private static Logger log = LogManager.getLogger(SignInController.class);
	
	@Autowired
	private AuthenticationManager authManager;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showSignUpPage() {
		return "signin";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String handleSignIn(@Valid UserSignInForm form, Errors errors, Model model, HttpServletRequest request) {
		
		if (errors.getAllErrors() != null && !errors.getAllErrors().isEmpty()) {
			model.addAttribute("errors", errors.getAllErrors());
			model.addAttribute("formData", form);
			
			return "signin";
		}
		
		UsernamePasswordAuthenticationToken authReq =
				new UsernamePasswordAuthenticationToken(form.getLogin(), form.getPassword());
		
		try {
			Authentication auth = authManager.authenticate(authReq);
			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(auth);
			HttpSession httpSession = request.getSession(true);
			httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
		} catch (Exception e) {
			log.error("Authentication error!", e);
		}	
		
		return "redirect:/";
	}
	
}
