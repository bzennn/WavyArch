package xyz.bzennn.wavyarch.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import xyz.bzennn.wavyarch.data.model.Account;
import xyz.bzennn.wavyarch.service.AccountService;
import xyz.bzennn.wavyarch.service.model.WavyArchUserDetails;

/**
 * Advice Controller that adds {@link Account} object to other controllers {@link Model} if user is authenticated 
 *
 * @author bzennn
 * @version 1.0
 */
@ControllerAdvice
public class UserDetailsAdvice {
	
	@Autowired
	private AccountService accountService;
	
	@ModelAttribute
	public void addUserAttribute(HttpServletRequest request, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null) {
			Object principal = auth.getPrincipal();
			if (principal != null && principal instanceof WavyArchUserDetails) {
				Account account = ((WavyArchUserDetails) principal).getAccount();
				accountService.refresh(account);
				
				model.addAttribute("user", account);
			}
		}
	}
	
}
