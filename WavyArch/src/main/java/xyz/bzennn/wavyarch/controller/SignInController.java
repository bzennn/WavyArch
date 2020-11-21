package xyz.bzennn.wavyarch.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xyz.bzennn.wavyarch.form.UserSignInForm;
import xyz.bzennn.wavyarch.util.AuthenticationUtils;

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
	private AuthenticationUtils authenticationUtils;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showSignUpPage() {
		return "signin";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String handleSignIn(@Valid UserSignInForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
		if (result.hasErrors()) {
			model.addAttribute("errors", result.getAllErrors());
			model.addAttribute("formData", form);
			
			return "signin";
		}
		
		try {
			Authentication auth = authenticationUtils.authenticate(request, form.getLogin(), form.getPassword());
			if (auth != null) {
				authenticationUtils.rememberMe(request, response, auth);
			}
		} catch (Exception e) {
			log.error("Authentication error!", e);
		}	
		
		return "redirect:/";
	}
	
}
