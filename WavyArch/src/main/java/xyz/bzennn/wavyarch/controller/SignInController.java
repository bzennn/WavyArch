package xyz.bzennn.wavyarch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User Sign In controller
 *
 * @author bzennn
 * @version 1.0
 */
@Controller
@RequestMapping("signin")
public class SignInController {

	@RequestMapping(method = RequestMethod.GET)
	public String showSignUpPage() {
		return "signin";
	}
	
}
