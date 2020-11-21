package xyz.bzennn.wavyarch.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.form.UserSignUpForm;
import xyz.bzennn.wavyarch.service.AccountService;

/**
 * User Sign Up controller
 *
 * @author bzennn
 * @version 1.0
 */
@Controller
@RequestMapping("signup")
public class SignUpController {

	@Autowired
	AccountService accountService;

	@RequestMapping(method = RequestMethod.GET)
	public String showSignUpPage() {
		return "signup";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String handleSignUp(@Valid UserSignUpForm form, BindingResult result, Model model) {
		Account account = form.buildAccount();

		if (result.hasErrors()) {
			model.addAttribute("errors", result.getAllErrors());
			model.addAttribute("formData", form);

			return "signup";
		}

		accountService.save(account);

		return "redirect:/signin";
	}
}
