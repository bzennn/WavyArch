package xyz.bzennn.wavyarch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User Sign Up controller
 *
 * @author bzennn
 * @version 1.0
 */
@Controller
@RequestMapping("signup")
public class SignUpController {

	@RequestMapping(method = RequestMethod.GET)
	public String showSignUpPage() {
		return "signup";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String handleSignUp(String login, String password, String passwordRepeat) {
		
		return "redirect:/";
	}
}
