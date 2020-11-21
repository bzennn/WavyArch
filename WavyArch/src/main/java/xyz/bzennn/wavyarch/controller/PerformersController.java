package xyz.bzennn.wavyarch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/performers")
public class PerformersController {

	@RequestMapping(method = RequestMethod.GET)
	public String showPerformersPage() {
		return "performers";
	}
	
}
